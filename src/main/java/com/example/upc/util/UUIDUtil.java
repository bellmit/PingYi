package com.example.upc.util;

import java.util.UUID;

/**
 * @author zcc
 * @date 2019/5/25 21:28
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
