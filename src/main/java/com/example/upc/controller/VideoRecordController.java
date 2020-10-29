package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.VideoSearchParam;
import com.example.upc.dataobject.InspectBookRecord;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.dataobject.VideoRecord;
import com.example.upc.service.FormatAdditiveService;
import com.example.upc.service.SysDeptAreaService;
import com.example.upc.service.VideoRecordService;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/videoRecord")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class VideoRecordController {
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private FormatAdditiveService formatAdditiveService;
    @Autowired
    private VideoRecordService videoRecordService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        VideoSearchParam videoSearchParam = JSON.parseObject(json,VideoSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2) {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            videoSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(videoRecordService.getPageSup(pageQuery, videoSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(videoRecordService.getPageAdmin(pageQuery, videoSearchParam));
        }
        else
        {
            videoRecordService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/getPageLook")
    @ResponseBody
    public CommonReturnType getPageLook(@RequestBody String json, SysUser sysUser){
        VideoSearchParam videoSearchParam = JSON.parseObject(json,VideoSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if (sysUser.getUserType()==2) {
            SupervisionGa supervisionGa = formatAdditiveService.getGa(sysUser.getInfoId());
            videoSearchParam.setAreaList(sysDeptAreaService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            return CommonReturnType.create(videoRecordService.getPageLookSup(pageQuery, videoSearchParam));
        }
        else if (sysUser.getUserType()==0)
        {
            return CommonReturnType.create(videoRecordService.getPageLookAdmin(pageQuery, videoSearchParam));
        }
        else
        {
            videoRecordService.fail();
            return CommonReturnType.create(null);
        }
    }

    @RequestMapping("/getPageLookByEnterpriseId")
    @ResponseBody
    public CommonReturnType getPageLookByEnterpriseId(PageQuery pageQuery,int id){
        return CommonReturnType.create(videoRecordService.getPageLookByEnterpriseId(pageQuery,id));
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id) {
        videoRecordService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        VideoRecord videoRecord = JSONObject.parseObject(json,VideoRecord.class);
        videoRecordService.insert(videoRecord,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json,SysUser sysUser){
        VideoRecord videoRecord = JSONObject.parseObject(json,VideoRecord.class);
        videoRecordService.update(videoRecord,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/download")
    public void pointDownload(HttpServletResponse response, @RequestParam(name = "checkId")int checkId){
        VideoRecord videoRecord = videoRecordService.getRecordById(checkId);
        if (videoRecord==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"记录为空");
        }
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据
        Map<String, Object> picMap = new HashMap<String, Object>();// 图片
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy年MM月dd日" );


        parametersMap.put("enterprise", videoRecord.getEnterpriseName());
        parametersMap.put("permission", videoRecord.getPermissionId());
        parametersMap.put("address", videoRecord.getAddress());
        parametersMap.put("area", videoRecord.getAreaName());
        parametersMap.put("person", videoRecord.getCharger());
        parametersMap.put("contact", videoRecord.getContact());
        parametersMap.put("level", videoRecord.getLevel());
        parametersMap.put("number", videoRecord.getRecordCount());
        parametersMap.put("time", sdf.format(videoRecord.getRecordTime()));
        parametersMap.put("supervision", videoRecord.getRecordPerson());
        parametersMap.put("*pic1", "cut/"+videoRecord.getRecordPicture1());
        parametersMap.put("*pic2", "cut/"+videoRecord.getRecordPicture2());

        parametersMap.put("recordContent", videoRecord.getRecordContent());
        parametersMap.put("recordPerson", videoRecord.getRecordPerson());
        parametersMap.put("recordDate", sdf.format(videoRecord.getRecordTime()));
        parametersMap.put("handleContent", videoRecord.getHandleContent());
        parametersMap.put("handlePerson", videoRecord.getHandlePersonName());
        parametersMap.put("handleDate", sdf.format(videoRecord.getHandleTime()));

        wordDataMap.put("parametersMap", parametersMap);
        //wordDataMap.put("picMap", picMap);
        try {
            // 读取word模板
            ClassPathResource resource = new ClassPathResource("templates/VideoRecord.docx");
            InputStream fileInputStream = resource.getInputStream();
            WordTemplate template = new WordTemplate(fileInputStream);
            // 替换数据
            template.replaceDocument(wordDataMap);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode( sdf.format(videoRecord.getHandleTime())+"远程执法巡查单.docx","UTF-8"));
            OutputStream os = response.getOutputStream();
            //生成文件
            template.getDocument().write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
