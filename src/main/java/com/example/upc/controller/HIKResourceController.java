package com.example.upc.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.HIKSearchParam;
import com.example.upc.dataobject.HikKey;
import com.example.upc.service.HikKeyService;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/HIKResource")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
@Component
public class HIKResourceController {
    @Autowired
    private HikKeyService hikKeyService;

    public void init(String hostNumber,String appkey,String appsecret){
        ArtemisConfig.host = hostNumber;// 代理API网关nginx服务器ip端口
        ArtemisConfig.appKey = appkey;// 秘钥appkey
        ArtemisConfig.appSecret = appsecret;// 秘钥appSecret
    }
    private static final String ARTEMIS_PATH = "/artemis";


    @RequestMapping("/getPageHost")
    @ResponseBody
    public CommonReturnType getPageHost(PageQuery pageQuery) {
        return CommonReturnType.create(hikKeyService.getPage(pageQuery));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(HikKey hikKey){
        hikKeyService.insert(hikKey);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        hikKeyService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(HikKey hikKey){
        hikKeyService.update(hikKey);
        return CommonReturnType.create(null);
    }


    //获取接口下的所有地区代码，根据地区代码可以调用下列接口，字段为regionIndexCode
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery,String region) {
        HikKey hikKey = hikKeyService.selectTopOne();

        init(hikKey.getHostNumber(),hikKey.getAppkey(),hikKey.getAppsecret());
        String getCamsApi = ARTEMIS_PATH + "/api/resource/v1/regions";
        String http;
        if (region.equals("平原县")){
         http ="http://";
        }
        else {
            http ="https://";
        }
            Map<String, String> path = new HashMap<String, String>(2) {
                {
                    put(http, getCamsApi);//根据现场环境部署确认是http还是https
                }
            };
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("pageNo", pageQuery.getPageNo());
            jsonBody.put("pageSize", pageQuery.getPageSize());
            String body = jsonBody.toJSONString();

            String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, "application/json", null);// post请求application/json类型参数
            System.out.println("请求路径：" + getCamsApi + ",请求参数：" + body + ",返回结果：" + result);
            //result.replaceAll("//","");
            JSONObject json_test = JSONObject.parseObject(result);
            return CommonReturnType.create(json_test);
    }


    //根据地区代码获取当前地区下的所有监控点信息，在返回中cameraIndexCode代表具体的某个监控点的唯一识别码
    @RequestMapping("/getPageCamerasByRegions")
    @ResponseBody
    public CommonReturnType getPageCamerasByRegions(PageQuery pageQuery, String regionIndexCode,String region) {
        HikKey hikKey = hikKeyService.selectTopOne();
        init(hikKey.getHostNumber(),hikKey.getAppkey(),hikKey.getAppsecret());
        String getRootApi  = ARTEMIS_PATH + "/api/resource/v1/regions/regionIndexCode/cameras";
        String http;
        if (region.equals("平原县")){
            http ="http://";
        }
        else {
            http ="https://";
        }
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put(http, getRootApi );//根据现场环境部署确认是http还是https
            }
        };
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("regionIndexCode", regionIndexCode);
        jsonBody.put("pageNo", pageQuery.getPageNo());
        jsonBody.put("pageSize", pageQuery.getPageSize());
        String body = jsonBody.toJSONString();

        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, "application/json", null);// post请求application/json类型参数
        System.out.println("请求路径：" + getRootApi  + ",请求参数：" + body + ",返回结果：" + result);
//        result.replace("\\","");
        JSONObject json_test = JSONObject.parseObject(result);
        return CommonReturnType.create(json_test);
    }


//获取具体某一监控点的视频流地址，参数为cameraIndexCode：监控点唯一索引值（必填，以下非必填）  string型
    //streamType：码流类型   0:主码流，1:子码流，2:第三码流，参数不填，默认为主码流   int型
    //protocol：取流协议（应用层协议）   “rtsp”:RTSP协议,              String型
                                      // “rtmp”:RTMP协议,
                                        // “hls”:HLS协议（HLS协议只支持海康SDK协议、EHOME协议、GB28181协议、ONVIF协议接入的设备；只支持H264视频编码和AAC音频编码）,参数不填，默认为RTSP协议
    //transmode：传输协议（传输层协议）  int型    0:UDP，1:TCP，默认是TCP，注：GB28181 2011版本接入设备的预览只支持UDP传输协议，GB28181 2016版本接入设备的预览支持UDP和TCP传输协议
   //expand：扩展   暂时用不到
    @RequestMapping("/getPageCamerasByCamerasIndex")
    @ResponseBody
    public CommonReturnType getPageCamerasByCamerasIndex(HIKSearchParam hikSearchParam,String region) {
        HikKey hikKey = hikKeyService.selectTopOne();
        init(hikKey.getHostNumber(),hikKey.getAppkey(),hikKey.getAppsecret());
        String getCamsApi   = ARTEMIS_PATH + "/api/video/v1/cameras/previewURLs";
        String http;
        if (region.equals("平原县")){
            http ="http://";
        }
        else {
            http ="https://";
        }
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put(http, getCamsApi);//根据现场环境部署确认是http还是https
            }
        };
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", hikSearchParam.getCameraIndexCode());
        jsonBody.put("streamType", hikSearchParam.getStreamType());
        jsonBody.put("protocol", hikSearchParam.getProtocol());
        jsonBody.put("transmode", hikSearchParam.getTransmode());
        String body = jsonBody.toJSONString();

        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, "application/json", null);// post请求application/json类型参数
        System.out.println("请求路径：" + getCamsApi   + ",请求参数：" + body + ",返回结果：" + result);
        JSONObject json_test = JSONObject.parseObject(result);
        return CommonReturnType.create(json_test);
        //返回中data是    "url": "rtsp://ip:port/EUrl/CLJ52BW"  这样的一条数据
        //前端拿到的这条url直接调用
    }
}
