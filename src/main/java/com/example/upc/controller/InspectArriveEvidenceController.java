package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.dataobject.InspectArriveEvidence;
import com.example.upc.service.InspectArriveEvidenceService;
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
 * @date 2019/8/31 20:04
 * 送达回证
 */
@Controller
@RequestMapping("/inspect/evidence")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectArriveEvidenceController {
    @Autowired
    private InspectArriveEvidenceService inspectArriveEvidenceService;

    @RequestMapping("/getByParentId")
    @ResponseBody
    public CommonReturnType getByParentId(int id){
        return CommonReturnType.create(inspectArriveEvidenceService.getByParentId(id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
        InspectArriveEvidence inspectArriveEvidence = JSON.parseObject(json,InspectArriveEvidence.class);
        inspectArriveEvidenceService.insert(inspectArriveEvidence);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        InspectArriveEvidence inspectArriveEvidence = JSON.parseObject(json,InspectArriveEvidence.class);
        inspectArriveEvidenceService.update(inspectArriveEvidence);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        inspectArriveEvidenceService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/download")
    public void pointDownload(HttpServletResponse response, @RequestParam(name = "checkId")int checkId){
        InspectArriveEvidence inspectArriveEvidence = inspectArriveEvidenceService.getByParentId(checkId);
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy年MM月dd日" );


        parametersMap.put("arriveFile", inspectArriveEvidence.getArriveFile());
        parametersMap.put("arrivePerson", inspectArriveEvidence.getArrivePerson());
        parametersMap.put("arriveDate", sdf.format(inspectArriveEvidence.getArriveDate()));
        parametersMap.put("arriveAddress", inspectArriveEvidence.getArriveAddress());
        parametersMap.put("arriveWay", inspectArriveEvidence.getArriveWay());
        parametersMap.put("remark", inspectArriveEvidence.getRemark());




        wordDataMap.put("parametersMap", parametersMap);
        try {
            // 读取word模板
            ClassPathResource resource = new ClassPathResource("templates/ArriveEvidence.docx");
            InputStream fileInputStream = resource.getInputStream();
            WordTemplate template = new WordTemplate(fileInputStream);
            // 替换数据
            template.replaceDocument(wordDataMap);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode( inspectArriveEvidence.getArrivePerson()+"送达回证.docx","UTF-8"));
            OutputStream os = response.getOutputStream();
            //生成文件
            template.getDocument().write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
