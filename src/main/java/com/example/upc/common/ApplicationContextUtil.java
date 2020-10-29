package com.example.upc.common;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ApplicationContextUtil {
    private static ApplicationContext applicationContext;

    private ApplicationContextUtil() {
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(
            ApplicationContext applicationContext) {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    /**
     * 根据Bean 名称从上下文里获取Bean 对象
     *
     * @param name bean 名称
     * @return bean对象
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 根据Bean Class从上下文里获取Bean 对象
     *
     * @param requiredType bean 类型
     * @return bean对象
     */
    public static Object getBean(Class<?> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static ServletRequestAttributes getServletActionContext() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }
}
