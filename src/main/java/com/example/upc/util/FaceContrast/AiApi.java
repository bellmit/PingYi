package com.example.upc.util.FaceContrast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.dataobject.AiToken;
import com.example.upc.util.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

public class AiApi {
    //获取新token
    public static   AiToken getNewToken(AiToken aiToken) {
            AiToken item = aiToken;
            Calendar dateOne = Calendar.getInstance();
            Calendar dateTwo = Calendar.getInstance();
            Date newDate = new Date();
            dateOne.setTime(newDate);//设置为当前系统时间
            dateTwo.setTime(item.getGetTime());//获取数据库中的时间
            long timeOne = dateOne.getTimeInMillis();
            long timeTwo = dateTwo.getTimeInMillis();
            long days = (timeOne - timeTwo) / (1000 * 60 * 60 * 24);//转化天
            if(days >= 25){
                System.out.println("aaa");
            }
            String getTokenUrl = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id="+item.getApiKey()+"&client_secret="+item.getSecretKey();
            String result = HttpClient.getClient(getTokenUrl);

            JSONObject jsonObject = JSONObject.parseObject(result);
            item.setAccessToken(jsonObject.get("access_token").toString());
            item.setGetTime(newDate);
            return item;
    }

    //人脸对比
    public static JSONObject faceContrast(String personFace1,String personFace2,String accessToken) {
        //百度AI人脸对比接口
        String faceContrastUrl = "https://aip.baidubce.com/rest/2.0/face/v3/match?access_token=" +accessToken;
        //构建数据包
        List<Map<String, Object>> FaceList = new ArrayList<Map<String, Object>>();

        Map<String, Object> personFaceMap1 = new HashMap<>();
        personFaceMap1.put("image", OperateBase64.deleteHead(personFace1));
        personFaceMap1.put("image_type", "BASE64");
        personFaceMap1.put("face_type", "CERT");
        personFaceMap1.put("quality_control", "NONE");

        Map<String, Object> personFaceMap2 = new HashMap<>();
        personFaceMap2.put("image", OperateBase64.deleteHead(personFace2));
        personFaceMap2.put("image_type", "BASE64");
        personFaceMap2.put("face_type", "LIVE");
        personFaceMap2.put("quality_control", "NONE");
        personFaceMap2.put("liveness_control", "HIGH");

        FaceList.add(personFaceMap1);
        FaceList.add(personFaceMap2);

        JSONArray params = JSONArray.parseArray(JSON.toJSONString(FaceList));
        //发送请求
        return HttpClient.postJSONArrayClient(faceContrastUrl, params);
    }


}
