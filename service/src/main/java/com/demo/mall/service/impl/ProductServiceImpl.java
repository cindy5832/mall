package com.demo.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mall.bean.app.dto.ProductDto;
import com.demo.mall.bean.app.dto.TagProductDto;
import com.demo.mall.bean.model.ProdTagReference;
import com.demo.mall.bean.model.Product;
import com.demo.mall.bean.model.Sku;
import com.demo.mall.common.utils.PageParam;
import com.demo.mall.dao.ProdTagReferenceMapper;
import com.demo.mall.dao.ProductMapper;
import com.demo.mall.dao.SkuMapper;
import com.demo.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private ProdTagReferenceMapper prodTagReferenceMapper;

    @Override
    @Cacheable(cacheNames = "product", key = "#prodId")
    public Product getProductByProdId(Long prodId) {
        return productMapper.selectById(prodId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "product", key = "#prodId"),
            @CacheEvict(cacheNames = "skuList", key = "#prodId")
    })
    public void removeProductCacheByProdId(Long prodId) {

    }

    @Override
    @Transactional
    public void saveProduct(Product product) {
        productMapper.insert(product);
        if (CollectionUtil.isNotEmpty(product.getSkuList())) {
            prodTagReferenceMapper.insertBatch(product.getShopId(), product.getProdId(), product.getTagList());
        }
    }


    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "product", key = "#product.prodId"),
            @CacheEvict(cacheNames = "skuList", key = "#product.prodId")
    })
    public void updateProduct(Product product, Product dbProduct) {
        productMapper.updateById(product);
        List<Long> dbSkuIds = dbProduct.getSkuList().stream().map(Sku::getSkuId).toList();

        // 將所有該商品的sku改為已刪除狀態
        skuMapper.deleteByProdId(product.getProdId());

        // 傳入sku列表
        List<Sku> skuList = product.getSkuList();
        if (CollectionUtil.isEmpty(skuList)) {
            return;
        }

        List<Sku> newSkuList = new ArrayList<>();
        for (Sku sku : skuList) {
            sku.setIsDelete(0);
            // 若數據庫原有sku更新，無則插入
            if (dbSkuIds.contains(sku.getSkuId())) {
                skuMapper.updateById(sku);
            } else {
                newSkuList.add(sku);
            }
        }

        // batch insert sku
        if (CollectionUtil.isNotEmpty(newSkuList)) {
            skuMapper.insertBatch(product.getProdId(), newSkuList);
        }

        // 更新分組訊息
        List<Long> tagList = product.getTagList();
        if (CollectionUtil.isNotEmpty(tagList)) {
            prodTagReferenceMapper.delete(new LambdaQueryWrapper<ProdTagReference>()
                    .eq(ProdTagReference::getProdId, product.getProdId()));
            prodTagReferenceMapper.insertBatch(dbProduct.getShopId(), product.getProdId(), tagList);
        }
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "product", key = "#prodId"),
            @CacheEvict(cacheNames = "skuList", key = "#prodId")
    })
    public void removeProductByProdId(Long prodId) {
        Product dbProduct = getProductByProdId(prodId);
        skuMapper.deleteByProdId(prodId);
        // 刪除商品關聯的分組標籤
        prodTagReferenceMapper.delete(new LambdaQueryWrapper<ProdTagReference>()
                .eq(ProdTagReference::getProdId, prodId));
    }

    @Override
    public IPage<ProductDto> pageByCategoryId(PageParam<ProductDto> page, Long categoryId) {
        return productMapper.pageByCategoryId(page, categoryId);
    }

    @Override
    public IPage<ProductDto> pageByPutAwayTime(PageParam<ProductDto> page) {
        return productMapper.pagePutAwayTime(page);
    }

    @Override
    public IPage<ProductDto> pageByTagId(PageParam<ProductDto> page, Long tagId) {
        return productMapper.pageByTagId(page, tagId);
    }

    @Override
    public IPage<ProductDto> moreBuyProdList(PageParam<ProductDto> page) {
        return productMapper.moreBuyProdList(page);
    }

    @Override
    public List<TagProductDto> tagProdList() {
        return productMapper.tagProdList();
    }

    @Override
    public IPage<ProductDto> collectionProds(PageParam page, String userId) {
        return productMapper.collectionProds(page, userId);
    }
}
