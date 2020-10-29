package com.example.upc.common;

import com.alibaba.fastjson.serializer.NameFilter;

/**
 * @author whn
 */
public class UpperCaseNameFilter implements NameFilter {
    public String process(Object object, String name, Object value) {
        if (name.equals("dbid") || name.equals("bizid") || name.equals("bindLiziForm")) {
            return name;
        }
        return name.toUpperCase();
    }
}
