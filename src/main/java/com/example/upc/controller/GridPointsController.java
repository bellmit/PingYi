package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.GaodeLocation;
import com.example.upc.controller.param.GridPoints1;
import com.example.upc.controller.searchParam.EnterpriseSearchParam;
import com.example.upc.dataobject.*;
import com.example.upc.service.*;
import com.example.upc.util.GaoDeMapUtil;
import com.example.upc.util.MapToStrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zcc
 * @date 2019/8/13 15:32
 */
@Controller
@RequestMapping("/grid/points")
public class GridPointsController {
    @Autowired
    private GridPointsService gridPointsService;
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private SysIndustryService sysIndustryService;
    @Autowired
    private SysDeptIndustryService sysDeptIndustryService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private SupervisionGaService supervisionGaService;
    @Autowired
    private SupervisionEnterpriseService supervisionEnterpriseService;

    @RequestMapping("/getAll")
    @ResponseBody
    public CommonReturnType getAll(){
        return CommonReturnType.create(gridPointsService.getAll());
    }

    @RequestMapping("/getAll1")
    @ResponseBody
    public CommonReturnType getAll1(){
        return CommonReturnType.create(gridPointsService.getAll1());
    }

    @RequestMapping("/getByAreaId")
    @ResponseBody
    public CommonReturnType getByAreaId(int id){
        return CommonReturnType.create(gridPointsService.getByAreaId(id));
    }
    @RequestMapping("/addPoint")
    @ResponseBody
    public CommonReturnType addPoint(@RequestBody GridPoints gridPoints){
       int enterpriseId=gridPoints.getEnterpriseId();
       int x=gridPointsService.checkPoint(enterpriseId);
       JSONObject data=new JSONObject();
       int y;
       if (x==1){
            y=gridPointsService.updatePoint(gridPoints);
           System.out.println("?????????");
       }else {
            y=gridPointsService.insertPoint(gridPoints);
           System.out.println("???????????????");
       }
        if (y==1) data.put("done",true);
        else data.put("done",false);
        return CommonReturnType.create(data);
    }

    @RequestMapping("/getPointByEnterpriseId")
    @ResponseBody
    public CommonReturnType getPointByEnterpriseId(int id){
        return CommonReturnType.create(gridPointsService.getPointByEnterpriseId(id));
    }

    @RequestMapping("/getPointByName")
    @ResponseBody
    public CommonReturnType getPointByName(String address){
        GaoDeMapUtil gaoDe = new GaoDeMapUtil();
        GaodeLocation result = gaoDe.getLocatoin(address);
        return CommonReturnType.create(result);
    }
    @RequestMapping("/transform")
    @ResponseBody
    @Transactional
    public CommonReturnType transform(){
        JSONObject data=new JSONObject();
        Map<String,String> returnMap =new HashMap<>();
        List<GridPoints1> list= gridPointsService.getAll1();//??????????????????
        int l=list.size();
        int flag=0;
        if(l>0) {
            GaoDeMapUtil gaoDe = new GaoDeMapUtil();
            for (int x = 0; x < l; x++) {
                String address = list.get(x).getRegistered_address();//???????????????????????????
                GridPoints gridPoints = new GridPoints();
                if (address == null || address.equals("")) {
                    address = "???????????????";
                    gridPoints.setOperator("????????????");
                    System.out.println("????????????,id???" + list.get(x).getId());
                }
                GaodeLocation result = gaoDe.getLocatoin(address);
                System.out.println(result.getCount());
                if (result.getCount().equals("1")){
                String point = result.getGeocodes().get(0).getLocation();
                if (point == null || point.equals("")) {

//                    point="117.07632,36.666395";//??????????????????
//                    gridPoints.setOperator("????????????");
//                    point="116.434032,37.165323";//??????????????????
//                    point="115.704953,36.838373";//??????????????????
//                    point="117.135354,36.192083";//?????????????????????
                    point="118.5821878900,37.4489563700";//??????????????????
                }
                gridPoints.setAreaId(list.get(x).getGrid());
                gridPoints.setEnterpriseId(list.get(x).getId());
                gridPoints.setPoint(point);
                gridPointsService.insertPoint(gridPoints);
                flag=1;
            } else {
                    SupervisionEnterprise supervisionEnterprise=supervisionEnterpriseService.selectById(list.get(x).getId());
                    flag=2;
                    returnMap.put("state","failed");
                    returnMap.put("update","?????????"+supervisionEnterprise.getEnterpriseName()+"?????????????????????????????????"+address);
                    break;
                }
            }
            if (flag==1){
                returnMap.put("state","success");
                returnMap.put("update","????????????"+l+"??????");
            }
        }else {
            returnMap.put("state","success");
            returnMap.put("update","????????????????????????????????????????????????");
        }
        return CommonReturnType.create(returnMap);
    }
    @RequestMapping("/getSmilePoints")
    @ResponseBody
    public CommonReturnType getSmilePoints(SysUser sysUser,@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        EnterpriseSearchParam enterpriseSearchParam= JSON.parseObject(json,EnterpriseSearchParam.class);
        if (sysUser.getUserType()==0){//??????????????????
            if(StringUtils.isEmpty(jsonObject.getJSONArray("industryList").get(0))) {
                enterpriseSearchParam.setIndustryList(sysIndustryService.getAll().stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
            if(StringUtils.isEmpty(jsonObject.getJSONArray("areaList").get(0))){
                enterpriseSearchParam.setAreaList(sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            }else{
                Integer areaId = (Integer)jsonObject.getJSONArray("areaList").get(0);
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            return CommonReturnType.create(gridPointsService.getSmileMapPoints(enterpriseSearchParam));
        }
        else if(sysUser.getUserType()==2){//????????????
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            List<SysIndustry> sysIndustryList = sysDeptIndustryService.getListByDeptId(supervisionGa.getDepartment());
            List<Integer> sysAreaList = sysDeptAreaService.getIdListByDeptId(supervisionGa.getDepartment());
            if(StringUtils.isEmpty(jsonObject.getJSONArray("industryList").get(0))) {
                enterpriseSearchParam.setIndustryList(sysIndustryList.stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
            if(StringUtils.isEmpty(jsonObject.getJSONArray("areaList").get(0))) {
                enterpriseSearchParam.setAreaList(sysAreaList);
            }else{
                Integer areaId = (Integer)jsonObject.getJSONArray("areaList").get(0);
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            return CommonReturnType.create(gridPointsService.getSmileMapPoints(enterpriseSearchParam));
        }
        else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????");
        }
    }

    @RequestMapping("/getSmilePointsPhone")
    @ResponseBody
    public CommonReturnType getSmilePointsPhone(SysUser sysUser,@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        EnterpriseSearchParam enterpriseSearchParam= JSON.parseObject(json,EnterpriseSearchParam.class);
        if (sysUser.getUserType()==0){//??????????????????
            if(StringUtils.isEmpty(jsonObject.getJSONArray("industryList").get(0))) {
                enterpriseSearchParam.setIndustryList(sysIndustryService.getAll().stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
            if(StringUtils.isEmpty(jsonObject.getJSONArray("areaList").get(0))){
                enterpriseSearchParam.setAreaList(sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            }else{
                Integer areaId = (Integer)jsonObject.getJSONArray("areaList").get(0);
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            return CommonReturnType.create(gridPointsService.getSmileMapPointsPhone(enterpriseSearchParam));
        }
        else if(sysUser.getUserType()==2){//????????????
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            List<SysIndustry> sysIndustryList = sysDeptIndustryService.getListByDeptId(supervisionGa.getDepartment());
            List<Integer> sysAreaList = sysDeptAreaService.getIdListByDeptId(supervisionGa.getDepartment());
            if(StringUtils.isEmpty(jsonObject.getJSONArray("industryList").get(0))) {
                enterpriseSearchParam.setIndustryList(sysIndustryList.stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
            if(StringUtils.isEmpty(jsonObject.getJSONArray("areaList").get(0))) {
                enterpriseSearchParam.setAreaList(sysAreaList);
            }else{
                Integer areaId = (Integer)jsonObject.getJSONArray("areaList").get(0);
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            return CommonReturnType.create(gridPointsService.getSmileMapPointsPhone(enterpriseSearchParam));
        }
        else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????");
        }
    }

    @RequestMapping("/getEnterpriseIdByName")
    @ResponseBody
    public CommonReturnType getEnterpriseIdByName(String name) {
        return CommonReturnType.create(gridPointsService.getEnterpriseByName(name));
    }
    @RequestMapping("/getAreaEnterprise")
    @ResponseBody
    public CommonReturnType getAreaEnterprise(){
        return CommonReturnType.create(gridPointsService.getAreaEnterprise());
    }
    @RequestMapping("/getVideoIdByEnterprise")
    @ResponseBody
    public CommonReturnType getVideoIdByEnterprise(int id){
        return CommonReturnType.create(gridPointsService.getVideoIdByEnterprise(id));
    }

    @RequestMapping("/updatePointByPhone")
    @ResponseBody
    public CommonReturnType updatePointByPhone(int enterpriseId, String code, String point, SysUser sysUser){
        gridPointsService.updateEnterprisePoint(enterpriseId,code,point,sysUser);
        return CommonReturnType.create(null);
    }
}
