package com.example.upc.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 董志涵
 */
public interface AiTokenService {
    /**
     *  请求token（注:accessToken 30天后失效）
     */
    void getNewToken();

    /**
     *
     * @param personFace1 照片1
     * @param personFace2 照片2
     * @return
     */
    JSONObject faceContrast(String personFace1, String personFace2);

    /**
     * 接收前端传过来的文件流和用户id，和对应 id的用户身份证照片比对
     * @param file 照片
     * @param id userId
     * @return
     */
    Integer faceContrastByCaId(MultipartFile file,int id);
}
