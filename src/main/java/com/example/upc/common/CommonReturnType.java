package com.example.upc.common;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.config.picConfig.PicUrl;
import com.example.upc.config.picConfig.GetUser;
import com.example.upc.util.JsonToImageUrl;
import org.apache.commons.collections4.Get;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.Map;

import static com.example.upc.common.ResponseUtil.toMessage;

/**
 * @author zcc
 * @date 2019/3/25 20:13
 */
//返回通用格式
public class CommonReturnType {
    private String status;//success或fail

    private Object data;

    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result, "success", "返回成功");
    }

    public static CommonReturnType create(Object result, String status) {
        return CommonReturnType.create(result, status, "返回成功");
    }

    public static CommonReturnType create(Object result, String status, String message) {
        CommonReturnType type = new CommonReturnType();
        HttpServletResponse resp = ApplicationContextUtil.getServletActionContext().getResponse();
        assert resp != null;
        resp.setContentType(HttpConstants.CONTENT_TYPE_JSON);
        //返回对象转化为Map类型键值对
        Map<String, Object> obj = toMessage(result);
        //去掉其中的空值
        String response = JSONUtil.stringify(obj, false);
        System.out.println(response);
//        //如果是微信小程序端则进行图片路径转化
//        JSONObject jsonObject = JSONObject.parseObject(JSONObject.parseObject(String.valueOf(response)).get("data").toString());
//        jsonObject.put(name, JsonToImageUrl.JSON2ImageUrl(jsonObject.get(name)));
//        getUser2.getUserPlat() == 1

        type.setStatus(status);
        JSONObject jsonObject = JSONObject.parseObject(String.valueOf(response));
        type.setData(jsonObject.get("data"));
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
