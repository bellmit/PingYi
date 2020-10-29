package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.dataobject.InspectRetificationNotice;
import com.example.upc.service.InspectRetificationNoticeService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zcc
 * @date 2019/8/31 20:08
 * 责令整改
 */
@Controller
@RequestMapping("/inspect/retification")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectRetificationNoticeController {
    @Autowired
    private InspectRetificationNoticeService inspectRetificationNoticeService;

    @RequestMapping("/getByParentId")
    @ResponseBody
    public CommonReturnType getByParentId(int id){
        return CommonReturnType.create(inspectRetificationNoticeService.getByParentId(id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
        InspectRetificationNotice inspectRetificationNotice =  JSON.parseObject(json,InspectRetificationNotice.class);
        inspectRetificationNoticeService.insert(inspectRetificationNotice);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        InspectRetificationNotice inspectRetificationNotice =  JSON.parseObject(json,InspectRetificationNotice.class);
        inspectRetificationNoticeService.update(inspectRetificationNotice);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        inspectRetificationNoticeService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/download")
    public void pointDownload(HttpServletResponse response, @RequestParam(name = "checkId")int checkId){
        InspectRetificationNotice inspectRetificationNotice = inspectRetificationNoticeService.getByParentId(checkId);
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据
        SimpleDateFormat sdfYear =   new SimpleDateFormat( "yyyy" );
        SimpleDateFormat sdfMonth =   new SimpleDateFormat( "MM" );
        SimpleDateFormat sdfDay =   new SimpleDateFormat( "dd" );

        parametersMap.put("year", sdfYear.format(new Date()));
        parametersMap.put("fileNumber", inspectRetificationNotice.getFileNumber());
        parametersMap.put("concernedPerson", inspectRetificationNotice.getConcernedPerson());
        parametersMap.put("behavior", inspectRetificationNotice.getBehavior());
        parametersMap.put("violationRegulations", inspectRetificationNotice.getViolationRegulations());
        parametersMap.put("accordingLaw", inspectRetificationNotice.getAccordingLaw()+inspectRetificationNotice.getLawProvisions()+inspectRetificationNotice.getLawTerm()+inspectRetificationNotice.getLawItem());
        parametersMap.put("outdataPunishment", inspectRetificationNotice.getOutdataPunishment());
        parametersMap.put("correctContent", inspectRetificationNotice.getCorrectContent());
        parametersMap.put("contacts", inspectRetificationNotice.getContacts());
        parametersMap.put("phoneNumber", inspectRetificationNotice.getPhoneNumber());
        parametersMap.put("year1", sdfYear.format(inspectRetificationNotice.getCompletionDate()));
        parametersMap.put("month1", sdfMonth.format(inspectRetificationNotice.getCompletionDate()));
        parametersMap.put("day1", sdfDay.format(inspectRetificationNotice.getCompletionDate()));
        parametersMap.put("year2", sdfYear.format(inspectRetificationNotice.getRecorderDate()));
        parametersMap.put("month2", sdfMonth.format(inspectRetificationNotice.getRecorderDate()));
        parametersMap.put("day2", sdfDay.format(inspectRetificationNotice.getRecorderDate()));



        wordDataMap.put("parametersMap", parametersMap);
        try {
            // 读取word模板
            ClassPathResource resource = new ClassPathResource("templates/RetificationNotice.docx");
            InputStream fileInputStream = resource.getInputStream();
            WordTemplate template = new WordTemplate(fileInputStream);
            // 替换数据
            template.replaceDocument(wordDataMap);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode( inspectRetificationNotice.getConcernedPerson()+"责令整改.docx","UTF-8"));
            OutputStream os = response.getOutputStream();
            //生成文件
            template.getDocument().write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
