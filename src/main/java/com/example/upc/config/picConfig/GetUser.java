package com.example.upc.config.picConfig;

import com.example.upc.common.ApplicationContextUtil;
import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.dataobject.SysUser;
import com.example.upc.redis.RedisService;
import com.example.upc.redis.SessionUserKey;
import com.example.upc.redis.UserSessionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class GetUser {
    @Autowired
    private RedisTemplate redisTemplate;

    public Integer getUserPlat() {
        ServletRequestAttributes servletRequestAttributes = ApplicationContextUtil.getServletActionContext();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        //获取request里的 token参数的值
        String paramToken = request.getParameter(UserSessionService.COOKIE_NAME_TOKEN);
        //获取request里 cookie的 token值
        String cookieToken = getCookieValue(request, UserSessionService.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            Integer a = 0;
            return a;
        } else {
            //cookieToken和paramToken两者
            String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
            String realKey = SessionUserKey.token.getPrefix() + token;
            if (redisTemplate.opsForValue().get(realKey) == null) {
                throw new BusinessException(EmBusinessError.PLEASE_LOGIN);
            }
            SysUser sysUser = (SysUser) redisTemplate.opsForValue().get(realKey);
            return sysUser.getPlatType();
        }
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
