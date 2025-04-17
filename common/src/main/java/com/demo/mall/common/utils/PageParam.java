/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.demo.mall.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;

import java.util.List;


@Schema(description = "頁數工具")
@ParameterObject
public class PageParam<T> extends Page<T> {

    @Schema(description = "每頁大小，默認為10")
    private long size = 10;

    @Schema(description = "當前頁數，默認為1")
    private long current = 1;

    // 查詢數據列表
    @Hidden
    private List<T> records;

    // 總數
    @Hidden
    private long total = 0;

    // 是否進行count查詢
    @JsonIgnore
    private boolean isSearchCount = true;

    @JsonIgnore
    private String countId;

    @JsonIgnore
    private Long maxLimit;

    @JsonIgnore
    private boolean optimizeCountSql;

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public Page<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public Page<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @JsonIgnore
    public boolean getSearchCount() {
        if (total < 0) {
            return false;
        }
        return isSearchCount;
    }

    @Override
    public Page<T> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public Page<T> setSize(long size) {
        int maxSize = 100;
        if (size > maxSize) {
            this.size = maxSize;
        } else {
            this.size = size;
        }
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public Page<T> setCurrent(long current) {
        this.current = current;
        return this;
    }
}
