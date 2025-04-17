package com.demo.mall.common.utils;

import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageAdapter {
    private int currentPage;

    private int pageSize;

    public PageAdapter(Page page) {
        int[] startEnd = PageUtil.transToStartEnd((int) page.getCurrent() - 1, (int) page.getSize());
        this.currentPage = startEnd[0];
        this.pageSize = (int) page.getSize();
    }
}
