package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.InspectDailyClauseParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.InspectSearchParam;
import com.example.upc.dataobject.InspectDailyFood;
import com.example.upc.dataobject.SysArea;
import com.example.upc.dataobject.SysIndustry;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.*;
import com.example.upc.util.WordTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zcc
 * @date 2019/5/18 23:40
 */
@Controller
@RequestMapping("/inspect/dailyFood")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class InspectDailyFoodController {
    @Autowired
    private InspectDailyFoodService inspectDailyFoodService;
    @Autowired
    private InspectDailyClauseService inspectDailyClauseService;
    @Autowired
    private SupervisionGaService supervisionGaService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private SysAreaService sysAreaService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, int industry, SysUser sysUser, InspectSearchParam searchParam){
        List<Integer> areaList = new ArrayList<>();
        if(sysUser.getUserType()==0){
            areaList = sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList());
        }
        else if(sysUser.getUserType()==2){
            areaList = sysDeptAreaService.getIdListByDeptId(supervisionGaService.getById(sysUser.getInfoId()).getDepartment());
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非法用户");
        }
        return CommonReturnType.create(inspectDailyFoodService.getPage(pageQuery,industry,areaList,searchParam));
    }

    @RequestMapping("/getEmptyList")
    @ResponseBody
    public CommonReturnType getEmptyList(int industryId,SysUser sysUser){
        Map<String,Object> map = new HashMap<>();
        if(sysUser.getUserType()==2){
            map.put("gaInfo",supervisionGaService.getById(sysUser.getInfoId()));
        }else {
            map.put("gaInfo",null);
        }
        map.put("list",inspectDailyClauseService.getEmptyList(industryId));
        return CommonReturnType.create(map);
    }

    @RequestMapping("/getCheckInfo")
    @ResponseBody
    public CommonReturnType getCheckInfo(Integer objectId){
       Map<String,Object> map = new HashMap<>();
       map.put("lastDate",inspectDailyFoodService.getCheckLastDate(objectId));
       map.put("yearCheckNumber",inspectDailyFoodService.yearCheckNumber(objectId));
       return CommonReturnType.create(map);
    }

    @RequestMapping("/getList")
    @ResponseBody
    public CommonReturnType getList(int checkId,int industryId){
        HashMap<String,Object> map = new HashMap<>();
        map.put("clauseList",inspectDailyClauseService.getList(checkId,industryId));
        return CommonReturnType.create(map);
    }

    @RequestMapping("/getStatistics")
    @ResponseBody
    public CommonReturnType getStatistics(String checkDate){
        return CommonReturnType.create(inspectDailyFoodService.getStatistics(checkDate));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        InspectDailyFood inspectDailyFood = JSON.toJavaObject(jsonObject,InspectDailyFood.class);
        List<InspectDailyClauseParam> inspectDailyClauseParamList = JSONObject.parseArray(jsonObject.getString("list"),InspectDailyClauseParam.class);
        inspectDailyFoodService.insert(inspectDailyFood,inspectDailyClauseParamList);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        InspectDailyFood inspectDailyFood = JSON.toJavaObject(jsonObject,InspectDailyFood.class);
        List<InspectDailyClauseParam> inspectDailyClauseParamList = JSONObject.parseArray(jsonObject.getString("list"),InspectDailyClauseParam.class);
        inspectDailyFoodService.update(inspectDailyFood,inspectDailyClauseParamList);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        inspectDailyFoodService.delete(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/pointDownload")
    public void pointDownload(HttpServletResponse response,@RequestParam(name = "checkId")int checkId,@RequestParam(name = "industryId")int industryId){
        List<InspectDailyClauseParam> inspectDailyClauseParamList = inspectDailyClauseService.getList(checkId,industryId);
        InspectDailyFood inspectDailyFood = inspectDailyFoodService.getInspectDailyFood(checkId);
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据
        Map<String, Object> picMap = new HashMap<String, Object>();// 存储报表中不循环的数据
        List<Map<String, Object>> table = new ArrayList<Map<String, Object>>();
        int importantNumber = 0;
        int specialNumber =0 ;
        for(InspectDailyClauseParam inspectDailyClauseParam :inspectDailyClauseParamList){
            Map<String, Object> map=new HashMap<>();
            map.put("largeName", inspectDailyClauseParam.getLargeClassName());
            map.put("number", getCheckNumber(inspectDailyClauseParam));
            map.put("checkContent", inspectDailyClauseParam.getClauseName());
            map.put("result",getResultName(inspectDailyClauseParam.getResult()));
            map.put("remark",inspectDailyClauseParam.getResultRemark());
            if(inspectDailyClauseParam.getImportance()==2){
                importantNumber++;
            }
            if(inspectDailyClauseParam.getImportance()==4){
                specialNumber++;
            }
                table.add(map);
        }
        String time = "";
        try{
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(inspectDailyFood.getCheckDate());
            time = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }


        parametersMap.put("enterpriseName",inspectDailyFood.getCheckObject());
        parametersMap.put("checkAddress", inspectDailyFood.getCheckAddress());
        parametersMap.put("supervisor", inspectDailyFood.getSupervisor());
        parametersMap.put("supervisorNumber", inspectDailyFood.getSupervisorNumber());
        parametersMap.put("time", time);
        parametersMap.put("importantNumber", importantNumber);
        parametersMap.put("commonNumber",inspectDailyClauseParamList.size()-importantNumber-specialNumber);
        if(industryId==3){
            parametersMap.put("specialNumber",specialNumber);
        }
        parametersMap.put("mostNumber", inspectDailyClauseParamList.size());


        parametersMap.put("$pic1","picture/"+inspectDailyFood.getFirstEnforcer());
        parametersMap.put("$pic2","picture/"+inspectDailyFood.getSecondEnforcer());

        wordDataMap.put("table", table);
        wordDataMap.put("parametersMap", parametersMap);
        try {
          // 读取word模板
            ClassPathResource resource;
         if (industryId==2){
             resource = new ClassPathResource("templates/CheckPoint.docx");
         } else if(industryId==3){
            resource = new ClassPathResource("templates/CheckPoint2.docx");
         } else if(industryId==5){
             resource = new ClassPathResource("templates/CheckPoint3.docx");
         } else{
             resource = new ClassPathResource("templates/CheckPoint.docx");
         }
         InputStream fileInputStream = resource.getInputStream();
         WordTemplate template = new WordTemplate(fileInputStream);
        // 替换数据
        template.replaceDocument(wordDataMap);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode( inspectDailyFood.getCheckObject()+"日常检查要点表.docx","UTF-8"));
        OutputStream os = response.getOutputStream();
        //生成文件
        template.getDocument().write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/recordDownload")
    public void recordDownload(HttpServletResponse response,@RequestParam(name = "checkId")int checkId,@RequestParam(name = "industryId")int industryId){

        List<InspectDailyClauseParam> inspectDailyClauseParamList = inspectDailyClauseService.getList(checkId,industryId);
        InspectDailyFood inspectDailyFood = inspectDailyFoodService.getInspectDailyFood(checkId);
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy年MM月dd日" );
        List<InspectDailyClauseParam> inspectDailyClauseImportantList = inspectDailyClauseParamList
                .stream().filter(inspectDailyClauseParam -> inspectDailyClauseParam.getImportance()==2).collect(Collectors.toList());
        List<InspectDailyClauseParam> inspectDailyClauseNormalList = inspectDailyClauseParamList
                .stream().filter(inspectDailyClauseParam -> inspectDailyClauseParam.getImportance()==1||inspectDailyClauseParam.getImportance()==4).collect(Collectors.toList());
        List<String> ImportIntegers = inspectDailyClauseImportantList.stream().map(inspectDailyClauseParam -> inspectDailyClauseParam.getSeq()).collect(Collectors.toList());
        List<String> NormalIntegers = inspectDailyClauseNormalList.stream().map(inspectDailyClauseParam -> inspectDailyClauseParam.getSeq()).collect(Collectors.toList());
        List<InspectDailyClauseParam> inspectProImportantList= inspectDailyClauseImportantList
                .stream().filter(inspectDailyClauseParam -> inspectDailyClauseParam.getResult()==2).collect(Collectors.toList());
        List<InspectDailyClauseParam> inspectProNormalList= inspectDailyClauseNormalList
                .stream().filter(inspectDailyClauseParam -> inspectDailyClauseParam.getResult()==2).collect(Collectors.toList());
        List<String> ImportProIntegers = inspectProImportantList.stream().map(inspectDailyClauseParam -> inspectDailyClauseParam.getSeq()).collect(Collectors.toList());
        List<String> NormalProIntegers = inspectProNormalList.stream().map(inspectDailyClauseParam -> inspectDailyClauseParam.getSeq()).collect(Collectors.toList());
        String time = "";
        try{
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(inspectDailyFood.getCheckDate());
            time = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        parametersMap.put("time", time);
        parametersMap.put("checkName", "食品生产经营");
        parametersMap.put("checkObject",inspectDailyFood.getCheckObject());
        parametersMap.put("checkAddress", inspectDailyFood.getCheckAddress());
        parametersMap.put("chargePerson", inspectDailyFood.getChargePerson());
        parametersMap.put("contactPhone", inspectDailyFood.getContactPhone());
        parametersMap.put("okNumber", inspectDailyFood.getOkNumber());
        parametersMap.put("checkCount", inspectDailyFood.getCheckCount());
        parametersMap.put("supervisor", inspectDailyFood.getSupervisor());
        parametersMap.put("checkTotal", inspectDailyClauseParamList.size());
        parametersMap.put("iNumber", inspectDailyClauseImportantList.size());
        parametersMap.put("disposalMeasures", inspectDailyFood.getDisposalMeasures());
        parametersMap.put("iPoint", StringUtils.join(ImportIntegers,","));
        parametersMap.put("iProNumber", inspectProImportantList.size());
        parametersMap.put("iProPoint", StringUtils.join(ImportProIntegers,","));
        parametersMap.put("nNumber", inspectDailyClauseNormalList.size());
        parametersMap.put("nPoint", StringUtils.join(NormalIntegers,","));
        parametersMap.put("nProNumber", inspectProNormalList.size());
        parametersMap.put("nProPoint", StringUtils.join(NormalProIntegers,","));
        if(inspectDailyFood.getCheckResult().equals("基本符合")){
            parametersMap.put("result2","☑");
        }else {
            parametersMap.put("result2","□");
        }
        if(inspectDailyFood.getCheckResult().equals("不符合")){
            parametersMap.put("result3","☑");
        }else {
            parametersMap.put("result3","□");
        }
        if(inspectDailyFood.getCheckResult().equals("符合")){
            parametersMap.put("result1","☑");
        }else {
            parametersMap.put("result1","□");
        }
        if(inspectDailyFood.getResultProcess().equals("通过")){
            parametersMap.put("handle1","☑");
        }else {
            parametersMap.put("handle1","□");
        }
        if(inspectDailyFood.getResultProcess().equals("书面限令整改")){
            parametersMap.put("handle2","☑");
        }else {
            parametersMap.put("handle2","□");
        }
        if(inspectDailyFood.getResultProcess().equals("食品生产经营者立即停止食品生产经营活动")){
            parametersMap.put("handle3","☑");
        }else {
            parametersMap.put("handle3","□");
        }

        parametersMap.put("$pic1","picture/"+inspectDailyFood.getFirstEnforcer());
        parametersMap.put("$pic2","picture/"+inspectDailyFood.getSecondEnforcer());


        wordDataMap.put("parametersMap", parametersMap);
        try {
            // 读取word模板
            ClassPathResource resource = new ClassPathResource("templates/CheckRecord.docx");
            InputStream fileInputStream = resource.getInputStream();
            WordTemplate template = new WordTemplate(fileInputStream);
            // 替换数据
            template.replaceDocument(wordDataMap);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(inspectDailyFood.getCheckObject()+"日常监督检查结果记录表.docx","UTF-8"));
            OutputStream os = response.getOutputStream();
            //生成文件
            template.getDocument().write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getResultName(int id){
        if(id==1){
            return "合格";
        }if(id==2){
            return "不合格";
        }else{
            return "待核验";
        }
    }
    public String getCheckNumber(InspectDailyClauseParam inspectDailyClauseParam){
        if(inspectDailyClauseParam.getImportance()==2){
            return inspectDailyClauseParam.getSeq()+"*";
        }else{
            return inspectDailyClauseParam.getSeq().toString();
        }
    }
}
