package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.example.upc.common.CommonReturnType;
import com.example.upc.dataobject.InspectAssistBook;
import com.example.upc.service.InspectAssistBookService;
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
 * @date 2019/8/31 20:05
 * 协助扣押
 */
@Controller
@RequestMapping("/inspect/assist")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectAssistBookController {
    @Autowired
    private InspectAssistBookService inspectAssistBookService;

    @RequestMapping("/getByParentId")
    @ResponseBody
    public CommonReturnType getByParentId(int id){
        return CommonReturnType.create(inspectAssistBookService.getByParentId(id));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
        InspectAssistBook inspectAssistBook = JSON.parseObject(json,InspectAssistBook.class);
        inspectAssistBookService.insert(inspectAssistBook);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        InspectAssistBook inspectAssistBook = JSON.parseObject(json,InspectAssistBook.class);
        inspectAssistBookService.update(inspectAssistBook);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        inspectAssistBookService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/download")
    public void pointDownload(HttpServletResponse response, @RequestParam(name = "checkId")int checkId){
        InspectAssistBook inspectAssistBook = inspectAssistBookService.getByParentId(checkId);
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据


        parametersMap.put("assistNumber", inspectAssistBook.getAssistNumber());
        parametersMap.put("concernedPerson", inspectAssistBook.getConcernedPerson());
        parametersMap.put("actionCause", inspectAssistBook.getActionCause());
        parametersMap.put("desicionFileNumber", inspectAssistBook.getDesicionFileNumber());
        parametersMap.put("listFileNumber", inspectAssistBook.getListFileNumber());
        parametersMap.put("contactPerson", inspectAssistBook.getContactPerson());
        parametersMap.put("contactPhone", inspectAssistBook.getContactPhone());



        wordDataMap.put("parametersMap", parametersMap);
        try {
            // 读取word模板
            ClassPathResource resource = new ClassPathResource("templates/AssistBook.docx");
            InputStream fileInputStream = resource.getInputStream();
            WordTemplate template = new WordTemplate(fileInputStream);
            // 替换数据
            template.replaceDocument(wordDataMap);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode( inspectAssistBook.getConcernedPerson()+"协助扣押.docx","UTF-8"));
            OutputStream os = response.getOutputStream();
            //生成文件
            template.getDocument().write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
