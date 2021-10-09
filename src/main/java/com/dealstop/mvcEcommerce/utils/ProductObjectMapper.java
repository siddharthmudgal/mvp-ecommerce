package com.dealstop.mvcEcommerce.utils;

import com.dealstop.mvcEcommerce.domainobjects.ProductDO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Object mapping helper
 */

public class ProductObjectMapper {

    public static ProductDO verifyAndMap(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, ProductDO.class);
    }
}
