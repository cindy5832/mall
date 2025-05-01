package com.demo.mall.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Json {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 如果為空則不輸出
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 對於空的對象轉json不拋出異常
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 禁用序列化日期為timestamps
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 禁用遇到未知屬性拋出異常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 取消對非ASCII的轉碼
        objectMapper.configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), false);
    }

    // 轉json
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("對象轉json錯誤：", e);
        }
        return null;
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        T result = null;
        try {
            result = objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("对象转json錯誤：", e);
        }
        return result;
    }


    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        T result = null;
        try {
            result = objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("Json轉換異常：", e);
        }
        if (result == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(result);
    }

    // json轉換成map
    public static JsonNode paseJsonNode(String json) {
        JsonNode result = null;
        try {
            result = objectMapper.readTree(json);
        } catch (Exception e) {
            log.error("Json轉換異常：", e);
        }
        return result;
    }
}
