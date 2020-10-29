package com.example.upc.common;

import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;

public class BooleanValueFilter implements ValueFilter {
    @Override
    public Object process(Object object, String name, Object value) {
        if (value != null && (value.equals("true") || value.equals("false"))) {
            return BooleanUtils.toBooleanObject(ObjectUtils.toString(value));
        }
        return value;
    }
}
