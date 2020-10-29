package com.example.upc.redis;

import com.example.upc.dataobject.SysUser;

/**
 * @author zcc
 * @date 2019/5/25 15:51
 */
public class SessionUserKey extends BasePrefix{
    public static final int TOKEN_EXPIRE = 3600*24 * 2;
    private SessionUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static SessionUserKey token = new SessionUserKey(TOKEN_EXPIRE, "tk");
}
