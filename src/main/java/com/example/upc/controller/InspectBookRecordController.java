package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.dataobject.InspectBookRecord;
import com.example.upc.service.InspectBookRecordService;
import com.example.upc.util.WordTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcc
 * @date 2019/8/31 20:05
 * 现场检查笔录
 */
@Controller
@RequestMapping("/inspect/record")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectBookRecordController {
    @Autowired
    private InspectBookRecordService inspectBookRecordService;

    @RequestMapping("/getByParentId")
    @ResponseBody
    public CommonReturnType getByParentId(int id){
        return CommonReturnType.create(inspectBookRecordService.getByParentId(id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
        InspectBookRecord inspectBookRecord = JSON.parseObject(json,InspectBookRecord.class);
        inspectBookRecordService.insert(inspectBookRecord);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        InspectBookRecord inspectBookRecord = JSON.parseObject(json,InspectBookRecord.class);
        inspectBookRecordService.update(inspectBookRecord);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        inspectBookRecordService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/download")
    public void pointDownload(HttpServletResponse response, @RequestParam(name = "checkId")int checkId){
        InspectBookRecord inspectBookRecord = inspectBookRecordService.getByParentId(checkId);
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据
        Map<String, Object> picMap = new HashMap<String, Object>();// 图片
        SimpleDateFormat sdfYear =   new SimpleDateFormat( "yyyy" );
        SimpleDateFormat sdfMonth =   new SimpleDateFormat( "MM" );
        SimpleDateFormat sdfDay =   new SimpleDateFormat( "dd" );
        SimpleDateFormat sdfHour =   new SimpleDateFormat( "HH" );
        SimpleDateFormat sdfM =   new SimpleDateFormat( "mm" );
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy年MM月dd日" );


        parametersMap.put("yearf", sdfYear.format(inspectBookRecord.getCheckTimeFrom()));
        parametersMap.put("monthf", sdfMonth.format(inspectBookRecord.getCheckTimeFrom()));
        parametersMap.put("dayf", sdfDay.format(inspectBookRecord.getCheckTimeFrom()));
        parametersMap.put("hourf", sdfHour.format(inspectBookRecord.getCheckTimeFrom()));
        parametersMap.put("minutef", sdfM.format(inspectBookRecord.getCheckTimeFrom()));

        parametersMap.put("yeart", sdfYear.format(inspectBookRecord.getCheckTimeTo()));
        parametersMap.put("montht", sdfMonth.format(inspectBookRecord.getCheckTimeTo()));
        parametersMap.put("dayt", sdfDay.format(inspectBookRecord.getCheckTimeTo()));
        parametersMap.put("hourt", sdfHour.format(inspectBookRecord.getCheckTimeTo()));
        parametersMap.put("minutet", sdfM.format(inspectBookRecord.getCheckTimeTo()));


        parametersMap.put("checkAddress", inspectBookRecord.getCheckAddress());
        parametersMap.put("examiner", inspectBookRecord.getExaminer());
        parametersMap.put("enforcementNumber", inspectBookRecord.getEnforcementNumber());
        parametersMap.put("party", inspectBookRecord.getParty());
        parametersMap.put("mainQualification", inspectBookRecord.getMainQualification());
        parametersMap.put("registrationNumber", inspectBookRecord.getRegistrationNumber());
        parametersMap.put("address", inspectBookRecord.getAddress());
        parametersMap.put("header", inspectBookRecord.getHeader());
        parametersMap.put("idNumber", inspectBookRecord.getIdNumber());
        parametersMap.put("phoneNumber", inspectBookRecord.getPhoneNumber());
        parametersMap.put("otherContact", inspectBookRecord.getOrtherContact());
        parametersMap.put("contactAddress", inspectBookRecord.getContactAddress());
        parametersMap.put("presentSituation", inspectBookRecord.getPresentSituation());
        parametersMap.put("informParties", inspectBookRecord.getInformParties());
        parametersMap.put("statement", inspectBookRecord.getStatement());
        parametersMap.put("sceneCondition", inspectBookRecord.getSceneCondition());

        parametersMap.put("$date", sdf.format(inspectBookRecord.getCheckTimeFrom()));
        picMap.put("$pic1", inspectBookRecord.getFirstEnforcer());
        picMap.put("$pic2", inspectBookRecord.getSecondEnforcer());

        wordDataMap.put("parametersMap", parametersMap);
        wordDataMap.put("picMap", picMap);
        try {
            // 读取word模板
            ClassPathResource resource = new ClassPathResource("templates/PenRecord.docx");
            InputStream fileInputStream = resource.getInputStream();
            WordTemplate template = new WordTemplate(fileInputStream);
            // 替换数据
            template.replaceDocument(wordDataMap);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode( inspectBookRecord.getParty()+"现场笔录.docx","UTF-8"));
            OutputStream os = response.getOutputStream();
            //生成文件
            template.getDocument().write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
