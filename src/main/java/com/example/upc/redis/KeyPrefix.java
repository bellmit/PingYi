package com.example.upc.redis;

/**
 * @author zcc
 * @date 2019/5/25 15:46
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
