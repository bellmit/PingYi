package com.example.upc.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.common.EmBusinessError;
import com.example.upc.dataobject.SupervisionCa;
import com.example.upc.service.AiTokenService;
import com.example.upc.service.SupervisionCaService;
import com.example.upc.util.FaceContrast.OperateBase64;
import com.example.upc.util.JsonToImageUrl;
import com.example.upc.util.operateExcel.WasteExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author 董志涵
 */
@RestController
@RequestMapping("/aiToken")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class AiTokenController {
    @Autowired
    private AiTokenService aiTokenService;
    @Autowired
    private SupervisionCaService supervisionCaService;

    /**
     * 获取新的token
     * @return
     */
//    @Scheduled(cron = "0 0 1 ? * L")
    @RequestMapping("/getNewToken")
    public CommonReturnType getNewToken(){
        aiTokenService.getNewToken();
        return CommonReturnType.create(null);
    }

    @RequestMapping("/faceContrast")
    public CommonReturnType faceContrast(@RequestParam("file") MultipartFile file,@RequestParam("id") int id){
        SupervisionCa supervisionCa = supervisionCaService.selectByPrimaryKey(id);
        String image = JsonToImageUrl.JSON2ImageUrl2(supervisionCa.getPhoto());
        String face2= OperateBase64.imageToBase64(file);
        // JSONObject jsonObject = aiApi.driverLicense(record);
//            String filePath= WasteExcel.path+"/upload/IMG_1344.JPG";
            String filePath = image;
            File originalFile = new File(filePath);

            String face1=OperateBase64.imageToBase64(originalFile);
            JSONObject result=new JSONObject();
//            if(faceContrastWhiteListService.getFaceContrastWhiteList(driverId)==null){
                result = aiTokenService.faceContrast(face1,face2);
//            }else{
//                JSONObject jsonObject=new JSONObject();
//                jsonObject.put("score",100);
//                result.put("error_msg","SUCCESS");
//                result.put("result",jsonObject);
//            }
            System.out.println("result："+result);
            if(result==null){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("score",0);
                result.put("error_msg","FALSE");
                result.put("result",jsonObject);
                return CommonReturnType.create(result);
            }else {
                System.out.println("分数："+result.getJSONObject("result").getInteger("score"));
                if(result.getJSONObject("result").getInteger("score")<80){
                    result.getJSONObject("result").put("score",59);
                }
                return CommonReturnType.create(result);
            }
    }
}
