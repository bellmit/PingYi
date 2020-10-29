package com.example.upc.config.picConfig;

import com.example.upc.util.JsonToImageUrl;
import com.sun.deploy.ui.DialogTemplate;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
@Aspect
public class PicMiniAspect {
    @Autowired
    private GetUser getUser;

    @Pointcut("@annotation(com.example.upc.config.picConfig.PicMini)")
    public void annotationPoinCut() {

    }

    @Around("annotationPoinCut()")
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object object = proceedingJoinPoint.proceed();
        if (getUser.getUserPlat() == 1) {
        if(object instanceof ArrayList){
            List<Object> result = null;
            result = (List) object;
            result.forEach(obj->{
                Field[] fields = FieldUtils.getAllFields(obj.getClass());
                if (fields != null) {
                    for (Field field : fields) {
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        PicUrl picUrl = field.getAnnotation(PicUrl.class);
                        if (picUrl != null) {
                            try {
                                field.set(obj, JsonToImageUrl.JSON2ImageUrl(field.get(obj)));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            object=result;
        }
        else {
                Field[] fields = FieldUtils.getAllFields(object.getClass());
                if (fields != null) {
                    for (Field field : fields) {
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        PicUrl picUrl = field.getAnnotation(PicUrl.class);
                        if (picUrl != null) {
                            field.set(object, JsonToImageUrl.JSON2ImageUrl(field.get(object)));
                        }
                    }
                }
            }
        }
        return object;
    }
}
