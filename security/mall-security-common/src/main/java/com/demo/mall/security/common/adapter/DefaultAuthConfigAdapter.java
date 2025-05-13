package com.demo.mall.security.common.adapter;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class DefaultAuthConfigAdapter implements AuthConfigAdapter {

    public DefaultAuthConfigAdapter() {
        log.info("not implement other AuthConfigAdapter, use DefaultAuthConfigAdapter... all url need auth...");
    }

    @Override
    public List<String> pathPatterns() {
        return Collections.singletonList("/*)");
    }

    @Override
    public List<String> excludePathPatterns() {
        return Collections.emptyList();
    }
}
