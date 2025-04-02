package com.anyview.yjy.utils.DataUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Map;

public class ParseData {

    /**
     * 获取请求参数
     * @param request
     * @return
     */
    public static Map<String, Object>getData(HttpServletRequest request){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object>Data = mapper.readValue(
                    // 简单点说就是 Spring Boot 中的 @RequestBody
                    request.getInputStream(),
                    new TypeReference<Map<String, Object>>(){}
                    );
            return Data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
