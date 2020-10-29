package com.example.upc.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonToImageUrl {
    /**
     * 给小程序前端发送时用，将JSON格式的图片相对地址转换为String类型图片的绝对地址，小程序可以直接访问
     * @param jsonObj
     * @return imgUrl
     */
    public static String JSON2ImageUrl(Object jsonObj) {
        JSONArray jsonArray = JSONArray.fromObject(jsonObj);
        JSONObject jsonObject1 = JSONObject.fromObject(jsonArray.get(0));
        JSONObject jsonObject2 = JSONObject.fromObject(jsonObject1.get("response"));
        // 图片存储地址记得上传的时候更改
//        String host = "/Users/weixj/Desktop/wph/IMDY/upload/";
//        String host = "http://127.0.0.1:8080/upload/picture/"
        String host = "http://172.26.79.229:8088/upload/picture/";
        //String host = "https://www.yiwifi1.com:8088/upload/picture/";
        String imgUrl = host+ jsonObject2.get("data");
        return imgUrl;
    }

    /**
     * 人脸识别时要用，获取服务器上、本机上存放的图片地址
     * @param jsonObj
     * @return
     */
    public static String JSON2ImageUrl2(Object jsonObj) {
        JSONArray jsonArray = JSONArray.fromObject(jsonObj);
        JSONObject jsonObject1 = JSONObject.fromObject(jsonArray.get(0));
        JSONObject jsonObject2 = JSONObject.fromObject(jsonObject1.get("response"));
        // 图片存储地址记得上传的时候更改IP
//        String host = "/Users/weixj/Desktop/wph/IMDY/upload/";
//        String host = "http://127.0.0.1:8080/upload/picture/";
        String host = "upload/";
        String imgUrl = host+ jsonObject2.get("data");
        return imgUrl;
    }
}