package com.example.upc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.common.EmBusinessError;
import com.example.upc.dao.AiTokenMapper;
import com.example.upc.dao.SupervisionCaMapper;
import com.example.upc.dataobject.AiToken;
import com.example.upc.dataobject.SupervisionCa;
import com.example.upc.service.AiTokenService;
import com.example.upc.util.FaceContrast.AiApi;
import com.example.upc.util.FaceContrast.OperateBase64;
import com.example.upc.util.JsonToImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 董志涵
 */

@Service
public class AiTokenServiceImpl implements AiTokenService {
    @Autowired
    private AiTokenMapper aiTokenMapper;
    @Autowired
    private SupervisionCaMapper supervisionCaMapper;

    @Override
    public void getNewToken(){
        List<AiToken> aiTokenList = aiTokenMapper.selectAllToken();
        for (int i = 0; i < aiTokenList.size(); i++) {
            AiToken aiToken = AiApi.getNewToken(aiTokenList.get(i));
            System.out.println(aiToken.getAccessToken());
            aiTokenMapper.updateByPrimaryKeySelective(aiToken);
        }
    }

    @Override
    public JSONObject faceContrast(String personFace1,String personFace2){
        String accessToken = new String();
        accessToken = aiTokenMapper.selectByPrimaryKey(1).getAccessToken();
        return AiApi.faceContrast(personFace1,personFace2,accessToken);
    }

    @Override
    public Integer faceContrastByCaId(MultipartFile file, int id){
        String face2= OperateBase64.imageToBase64(file);

        SupervisionCa supervisionCa = supervisionCaMapper.selectByPrimaryKey(id);
        String image = JsonToImageUrl.JSON2ImageUrl2(supervisionCa.getPhoto());
        String filePath = image;
        File originalFile = new File(filePath);
        String face1=OperateBase64.imageToBase64(originalFile);

        String accessToken = new String();
        accessToken = aiTokenMapper.selectByPrimaryKey(1).getAccessToken();

        JSONObject result=new JSONObject();
        result = AiApi.faceContrast(face1,face2,accessToken);

        System.out.println("result："+result);

        if(result==null){
            throw new BusinessException(EmBusinessError.FACE_ERROR);
        }else {
            Integer score = result.getJSONObject("result").getInteger("score");
            return score;
        }
    }
}

