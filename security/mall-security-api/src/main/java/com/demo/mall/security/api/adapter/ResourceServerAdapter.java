package com.demo.mall.security.api.adapter;

import com.demo.mall.security.common.adapter.DefaultAuthConfigAdapter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class ResourceServerAdapter extends DefaultAuthConfigAdapter {

    @Override
    public List<String> pathPatterns() {
        return Collections.singletonList("/p/*");
    }
}
