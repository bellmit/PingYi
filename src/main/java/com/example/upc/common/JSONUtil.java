package com.example.upc.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONUtil {
    /**
     * 将List<Map<String, Object>>对象转换为json字符串
     *
     * @param list 数据列表
     * @return json数组字符串
     */
    private static String stringifyList(Object list, SerializerFeature... features){
        return JSON.toJSONStringWithDateFormat(list, JSON.DEFFAULT_DATE_FORMAT, features);
    }

    private static String stringifyList(Object list, boolean useUpperCaseKey, SerializerFeature... features){
        return stringifyBean(list, useUpperCaseKey, features);
    }

    /**
     * 将实体对象转换为json字符串，字段名被转换为大写，其中dbid，bizid依然保留小写
     *
     * @param bean 实体对象
     * @return json对象字符串
     */
    private static String stringifyBean(Object bean, boolean useUpperCaseName, SerializerFeature... features){
        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out);
            serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            if(features != null){
                for(SerializerFeature feature : features){
                    serializer.config(feature, true);
                }
            }
            serializer.setDateFormat(JSON.DEFFAULT_DATE_FORMAT);
            if(useUpperCaseName){
                serializer.getNameFilters().add(new UpperCaseNameFilter());
            }
            serializer.write(bean);
            return out.toString();
        } finally {
            out.close();
        }
    }

    /**
     * 增加booleanValue参数转换，主要解决boolean值转化为json问题
     * @author 王炎超     2016.7.18
     * @param bean 转化bean
     * @param useUpperCaseName
     * @param booleanValue
     * @param features
     * @return
     */
    private static String stringifyBean(Object bean, boolean useUpperCaseName,boolean booleanValue, SerializerFeature... features){
        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out);
            serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            if(features != null){
                for(SerializerFeature feature : features){
                    serializer.config(feature, true);
                }
            }
            serializer.setDateFormat(JSON.DEFFAULT_DATE_FORMAT);
            if(useUpperCaseName){
                serializer.getNameFilters().add(new UpperCaseNameFilter());
            }
            if(booleanValue){
                serializer.getValueFilters().add(new BooleanValueFilter());
            }
            serializer.write(bean);
            return out.toString();
        } finally {
            out.close();
        }
    }

    /**
     * 通用序列化方法(主要解决boolean型值转换)
     * @author 王炎超     2016.7.18
     * @param bean
     * @param features @see SerializerFeature
     * @return json字符串
     */
    public static String stringifyWithBoolean(Object bean, SerializerFeature... features){
        return stringifyBean(bean, false,true, features);
    }

    /**
     * 通用序列化方法(主要解决boolean型值转换问题)
     * @author 王炎超     2016.7.18
     * @param bean
     * @parm useUpperCaseName 是否大写
     * @return json字符串
     */
    public static String stringifyWithBoolean(Object bean,boolean useUpperCaseName, SerializerFeature... features){
        return stringifyBean(bean, useUpperCaseName,true, features);
    }

    /**
     * 通用序列化方法
     *
     * @param bean 实体对象获列表
     * @return json字符串
     */
    public static String stringify(Object bean, SerializerFeature... features){
        if(bean instanceof List){
            return stringifyList(bean, features);
        }
        return stringifyBean(bean, true, features);
    }

    public static String stringify(Object bean, boolean useUpperCaseKey, SerializerFeature... features){
        if(bean instanceof List){
            if(useUpperCaseKey){
                return stringifyList(bean, useUpperCaseKey, features);
            }else{
                return stringifyList(bean, features);
            }
        }
        return stringifyBean(bean, useUpperCaseKey, features);
    }

    public static <T> T parse(String jsonString, Class<T> clazz){
        return JSON.parseObject(jsonString, clazz, Feature.OrderedField);
    }

    /**
     * 将map或者字符串转换为java对象
     * @author 王峰     2016.10.10
     * @param bean map或者字符串
     * @param clazz java类
     * @return
     */
    public static <T> T parse(Object bean, Class<T> clazz){
        if(bean instanceof Map){
            Map<String,String> result = new HashMap<String,String>();
            Map<String,Object> temp = (Map<String, Object>)bean;

            for(Map.Entry<String, Object> entry : temp.entrySet()){
                String value = null;

                if(entry.getValue() instanceof String[]){
                    String[] values = (String[]) entry.getValue();

                    if(values.length > 1){
                        System.out.println("期望数组中只有一个值，实际包含多个值");
                    }

                    value = values[0];
                }else{
                    value = (String) entry.getValue();
                }

                result.put("\"" + entry.getKey() + "\"", "\"" + value + "\"");
            }
            return parse(result.toString().replaceAll("=", ":"), clazz);
        }else if(bean instanceof String){
            return parse(ObjectUtils.toString(bean), clazz);
        }
        return null;
    }
}
