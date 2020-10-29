package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.dataobject.InspectForceDecision;
import com.example.upc.service.InspectForceDecisionService;
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
import java.util.Map;

/**
 * @author zcc
 * @date 2019/8/31 20:06
 * 强制措施
 */
@Controller
@RequestMapping("/inspect/force")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectForceDecisionController {
    @Autowired
    private InspectForceDecisionService inspectForceDecisionService;

    @RequestMapping("/getByParentId")
    @ResponseBody
    public CommonReturnType getByParentId(int id){
        return CommonReturnType.create(inspectForceDecisionService.getByParentId(id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
        InspectForceDecision inspectForceDecision = JSON.parseObject(json,InspectForceDecision.class);
        inspectForceDecisionService.insert(inspectForceDecision);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        InspectForceDecision inspectForceDecision = JSON.parseObject(json,InspectForceDecision.class);
        inspectForceDecisionService.update(inspectForceDecision);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        inspectForceDecisionService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/download")
    public void pointDownload(HttpServletResponse response, @RequestParam(name = "checkId")int checkId){
        InspectForceDecision inspectForceDecision = inspectForceDecisionService.getByParentId(checkId);
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy年MM月dd日" );


        parametersMap.put("forceNumber",inspectForceDecision.getForceNumber());
        parametersMap.put("concernedPerson", inspectForceDecision.getConcernedPerson());
        parametersMap.put("mainQualification", inspectForceDecision.getMainQualification());
        parametersMap.put("registrationNumber",inspectForceDecision.getRegistrationNumber());
        parametersMap.put("address", inspectForceDecision.getAddress());
        parametersMap.put("header", inspectForceDecision.getHeader());
        parametersMap.put("idNumber", inspectForceDecision.getIdNumber());
        parametersMap.put("phoneNumber", inspectForceDecision.getPhoneNumber());
        parametersMap.put("ortherContact", inspectForceDecision.getOrtherContact());
        parametersMap.put("behavior", inspectForceDecision.getBehavior());
        parametersMap.put("accordingLaw", inspectForceDecision.getAccordingLaw()+inspectForceDecision.getLawProvisions()+inspectForceDecision.getLawTerm()+inspectForceDecision.getLawItem());
        parametersMap.put("fileNumber", inspectForceDecision.getFileNumber());
        parametersMap.put("forceMeasure", inspectForceDecision.getForceMeasure());
        parametersMap.put("forceMeasureAddress", inspectForceDecision.getForceMeasureAddress());
        parametersMap.put("forceDate", inspectForceDecision.getForceDate());
        parametersMap.put("saveCondition", inspectForceDecision.getSaveCondition());
        parametersMap.put("applyReconsideration",inspectForceDecision.getApplyReconsideration());
        parametersMap.put("applyLitigation",sdf.format(inspectForceDecision.getApplyLitigation()));
        parametersMap.put("contectPerson", inspectForceDecision.getContectPerson());
        parametersMap.put("contectPhone", inspectForceDecision.getContectPhone());



        wordDataMap.put("parametersMap", parametersMap);
        try {
            // 读取word模板
            ClassPathResource resource = new ClassPathResource("templates/ForceDecision.docx");
            InputStream fileInputStream = resource.getInputStream();
            WordTemplate template = new WordTemplate(fileInputStream);
            // 替换数据
            template.replaceDocument(wordDataMap);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode( inspectForceDecision.getConcernedPerson()+"强制措施.docx","UTF-8"));
            OutputStream os = response.getOutputStream();
            //生成文件
            template.getDocument().write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
