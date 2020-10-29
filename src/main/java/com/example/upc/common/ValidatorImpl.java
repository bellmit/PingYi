package com.example.upc.common;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


/**
 * @author zcc
 * @date 2019/7/14 16:32
 */
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;
    public static final String DEFAULT_parameter1="select";
    public static final String DEFAULT_parameter2="insert";

    //实现校验方法并返回校验结果
    public ValidationResult validate(Object bean){
         ValidationResult result = new ValidationResult();
         Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
         if(constraintViolationSet.size()>0){
             result.setHasErrors(true);
             constraintViolationSet.forEach(constraintViolation->{
                 String errMsg = constraintViolation.getMessage();
                 String propertyName = constraintViolation.getPropertyPath().toString();
                 result.getErrorMsgMap().put(propertyName,errMsg);
             });
         }
         return result;
    }

    public ValidationResult validate(Object bean,String type){
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet;
        //查询调用
        if(DEFAULT_parameter1.equals(type))
        {
            constraintViolationSet = validator.validate(bean,SearchGroup.class);
        }
        //插入调用
        else if(DEFAULT_parameter2.equals(type))
        {
            constraintViolationSet = validator.validate(bean,InsertGroup.class);
        }
        else
        {
            constraintViolationSet = validator.validate(bean);
        }
        if(constraintViolationSet.size()>0){
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation->{
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName,errMsg);
            });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate 的validator通过工厂的初始化方式十七实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
