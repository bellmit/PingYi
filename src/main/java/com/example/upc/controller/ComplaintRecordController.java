package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.ComplaintRecordParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.ComplaintEnterpriseSearchParam;
import com.example.upc.controller.searchParam.ComplaintRecordSearchParam;
import com.example.upc.dataobject.ComplaintRecord;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.ComplaintRecordService;
import com.example.upc.service.ComplaintRecordStatisticsService;
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
import java.util.*;

@Controller
@RequestMapping("/complaintRecord")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ComplaintRecordController {
    @Autowired
    private ComplaintRecordService complaintRecordService;
    @Autowired
    private ComplaintRecordStatisticsService complaintRecordStatisticsService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        PageQuery pageQuery= JSON.parseObject(json,PageQuery.class);
        ComplaintRecordSearchParam complaintRecordSearchParam = JSONObject.parseObject(json,ComplaintRecordSearchParam.class);
        if (sysUser.getUserType()==2) {
            return CommonReturnType.create(complaintRecordService.getPage(pageQuery, sysUser, complaintRecordSearchParam));
        }
        if (sysUser.getUserType()==0) {
            return CommonReturnType.create(complaintRecordService.getPageAdmin(pageQuery, complaintRecordSearchParam));
        }
        else
        {
            complaintRecordService.fail();
            return CommonReturnType.create(null);
        }

    }
    @RequestMapping("/getPageEnterprise")
    @ResponseBody
    public CommonReturnType getPageEnterprise(PageQuery pageQuery, ComplaintEnterpriseSearchParam complaintEnterpriseSearchParam){
        return CommonReturnType.create(complaintRecordService.getPageEnterprise(pageQuery,complaintEnterpriseSearchParam));

    }
    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        ComplaintRecord complaintRecord = JSONObject.parseObject(json,ComplaintRecord.class);
        complaintRecordService.insert(complaintRecord,sysUser);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        complaintRecordService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json, SysUser sysUser){
        ComplaintRecord complaintRecord = JSONObject.parseObject(json,ComplaintRecord.class);
        complaintRecordService.update(complaintRecord,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getListAll")
    @ResponseBody
    public CommonReturnType getListAll(@RequestBody String json){
        ComplaintRecordSearchParam complaintRecordSearchParam = JSONObject.parseObject(json,ComplaintRecordSearchParam.class);
        if (complaintRecordSearchParam.getStart1()==null||complaintRecordSearchParam.getEnd1()==null) {
            return CommonReturnType.create("正确");
        }
        else {
            return CommonReturnType.create(complaintRecordStatisticsService.getListTypeNumberByDate(complaintRecordSearchParam));
        }
    }

    @RequestMapping("/getListAll1")
    @ResponseBody
    public CommonReturnType getListAll1(ComplaintRecordSearchParam complaintRecordSearchParam){
        return CommonReturnType.create(complaintRecordStatisticsService.getListTypeNumberByDate(complaintRecordSearchParam));
    }

    @RequestMapping("/pointDownload")
    public void pointDownload(HttpServletResponse response, @RequestParam(name = "checkId")int checkId){

        ComplaintRecord complaintRecord = complaintRecordService.getRecordById(checkId);
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy年MM月dd日" );


        parametersMap.put("recordNumber", complaintRecord.getRecordNumber());
        parametersMap.put("finishTime",sdf.format(complaintRecord.getFinishTime()));
        parametersMap.put("record", complaintRecord.getRecord());
        parametersMap.put("emergencyType", complaintRecord.getEmergencyType());
        parametersMap.put("incomingType", complaintRecord.getIncomingType());
        parametersMap.put("informationType", complaintRecord.getInformationType());
        parametersMap.put("reply", complaintRecord.getReply());
        parametersMap.put("secrecy",complaintRecord.getSecrecy());
        parametersMap.put("complaintPerson", complaintRecord.getComplaintPerson());

        parametersMap.put("contact", complaintRecord.getContact());
        parametersMap.put("address", complaintRecord.getAddress());

        parametersMap.put("problemTwo", complaintRecord.getProblemTwo());

        parametersMap.put("incomingContent", complaintRecord.getIncomingContent());


        if (complaintRecord.getInstruction()==null && complaintRecord.getStep()==3)
        {
            parametersMap.put("instruction","领导未批示");
        }
        else if (complaintRecord.getStep()!=3)
        {
            parametersMap.put("instruction", "无需批示");
        }
        else
        {
            parametersMap.put("instruction",complaintRecord.getInstruction());
        }
        parametersMap.put("suggestion", complaintRecord.getSuggestion());
        if (complaintRecord.getResult()==null)
        {
            parametersMap.put("result", "未完成办理");
        }
        else
            {
                parametersMap.put("result", complaintRecord.getResult());
        }

        wordDataMap.put("parametersMap", parametersMap);

        try {
            // 读取word模板
            ClassPathResource resource = new ClassPathResource("templates/ComplaintRecord.docx");
            InputStream fileInputStream = resource.getInputStream();
            WordTemplate template = new WordTemplate(fileInputStream);
            // 替换数据
            template.replaceDocument(wordDataMap);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + complaintRecord.getRecord()+".docx");
            OutputStream os = response.getOutputStream();
            //生成文件
            template.getDocument().write(os);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
