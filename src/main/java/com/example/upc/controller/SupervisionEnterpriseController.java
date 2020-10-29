package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.common.EmBusinessError;
import com.example.upc.config.picConfig.PicMini;
import com.example.upc.controller.param.EnterpriseParam;
import com.example.upc.controller.param.NearEnterprise;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.SmilePoints;
import com.example.upc.controller.searchParam.EnterpriseSearchParam;
import com.example.upc.dataobject.*;
import com.example.upc.service.*;
import com.example.upc.util.miniProgram.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.upc.util.JsonToImageUrl.JSON2ImageUrl;

/**
 * @author zcc
 * @date 2019/4/25 10:47
 */
@Controller
@RequestMapping("/supervision/enterprise")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SupervisionEnterpriseController {
    @Autowired
    private SupervisionEnterpriseService supervisionEnterpriseService;
    @Autowired
    private SysDeptIndustryService sysDeptIndustryService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private SysTreeService sysTreeService;
    @Autowired
    private SupervisionConfigCategoryService supervisionConfigCategoryService;
    @Autowired
    private SupervisionConfigLicenceService supervisionConfigLicenceService;
    @Autowired
    private SupervisionGaService supervisionGaService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private SysIndustryService sysIndustryService;
    @Autowired
    private SupervisionCaService supervisionCaService;
    @Autowired
    private GridPointsService gridPointsService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json, SysUser sysUser){
        JSONObject jsonObject = JSON.parseObject(json);
        EnterpriseSearchParam enterpriseSearchParam= JSON.parseObject(json,EnterpriseSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        Integer areaId = null;
        if(!StringUtils.isEmpty(jsonObject.getJSONArray("areaList").get(0)))
        {
            areaId = (Integer)jsonObject.getJSONArray("areaList").get(0);
        }
        boolean searchIndustry = StringUtils.isEmpty(jsonObject.getJSONArray("industryList").get(0));

        if(sysUser.getUserType()==1){
            return CommonReturnType.create(supervisionEnterpriseService.getPageByEnterpriseId(sysUser.getInfoId()));
        }else{
            return CommonReturnType.create(supervisionEnterpriseService.getPage(pageQuery,enterpriseSearchParam,sysUser,areaId,searchIndustry));
        }
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public CommonReturnType getAll(){
        return CommonReturnType.create(supervisionEnterpriseService.getAll());
    }

    @RequestMapping("/getPageState")
    @ResponseBody
    public CommonReturnType getPageState(@RequestBody String json, SysUser sysUser){
        JSONObject jsonObject = JSON.parseObject(json);
        EnterpriseSearchParam enterpriseSearchParam= JSON.parseObject(json,EnterpriseSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        Integer areaId = null;
        if(!StringUtils.isEmpty(jsonObject.getJSONArray("areaList").get(0)))
        {
            areaId = (Integer)jsonObject.getJSONArray("areaList").get(0);
        }
        boolean searchIndustry = StringUtils.isEmpty(jsonObject.getJSONArray("industryList").get(0));

        if(sysUser.getUserType()==1){
            return CommonReturnType.create(supervisionEnterpriseService.getPageByEnterpriseId(sysUser.getInfoId()));
        }else{
            return CommonReturnType.create(supervisionEnterpriseService.getPageState(pageQuery,enterpriseSearchParam,sysUser,areaId,searchIndustry));
        }
    }

    //统计企业信息,不同区域不同业态权限统计
    @RequestMapping("/getStatistics")
    @ResponseBody
    public CommonReturnType getStatistics(SysUser sysUser){
        if (sysUser.getUserType()==0){
            return CommonReturnType.create(supervisionEnterpriseService.getStatistics(sysIndustryService.getAll(),sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()),null));
        }else if(sysUser.getUserType()==1){
            return CommonReturnType.create(new HashMap<>());
        }
        else if(sysUser.getUserType()==2){
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            List<SysIndustry> sysIndustryList = sysDeptIndustryService.getListByDeptId(supervisionGa.getDepartment());
            List<Integer> sysAreaList = sysDeptAreaService.getIdListByDeptId(supervisionGa.getDepartment());
            SysDept sysDept = sysDeptService.getById(supervisionGa.getDepartment());
            String supervisor = null;
            if(sysDept.getType()==2){
                if(supervisionGa.getType()!=2){
                    supervisor=supervisionGa.getName();
                }
            }
            return CommonReturnType.create(supervisionEnterpriseService.getStatistics(sysIndustryList,sysAreaList,supervisor));
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非法用户");
        }
    }

//根据某个企业id来查看这个企业的信息，主要接口，要修改这里的关联方法
    @RequestMapping("/getById")
    @ResponseBody
    public CommonReturnType getById(int id){
        return CommonReturnType.create(supervisionEnterpriseService.getById(id));
    }

    //无用户登录时测试用
    @RequestMapping("/getPageNoUser")
    @ResponseBody
    public CommonReturnType getPageNoUser(@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        EnterpriseSearchParam enterpriseSearchParam= JSON.parseObject(json,EnterpriseSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        Integer areaId = null;
        if(!StringUtils.isEmpty(jsonObject.getJSONArray("areaList").get(0))){
            areaId = (Integer)jsonObject.getJSONArray("areaList").get(0);}
        boolean searchIndustry = StringUtils.isEmpty(jsonObject.getJSONArray("industryList").get(0));

        return CommonReturnType.create(supervisionEnterpriseService.getPageNoUser(pageQuery,enterpriseSearchParam,areaId,searchIndustry));

    }


    @RequestMapping("/getQrcodeById")
    @ResponseBody
    public CommonReturnType getQrcodeById(int id){
        Map<String,Object> map = new HashMap<>();
        map.put("enterprise",supervisionEnterpriseService.getById(id));
        map.put("caList",supervisionCaService.getAllByEnterpriseId(id));
        return CommonReturnType.create(map);
    }

    @RequestMapping("/changeStop")
    @ResponseBody
    public CommonReturnType changeStop(int id){
        supervisionEnterpriseService.changeStop(id);
        return CommonReturnType.create(null);
    }

    //获取部门树
    @RequestMapping("/getDeptAndGrid")
    @ResponseBody
    public CommonReturnType getDeptAndGrid(){
        Map<String,Object> map = new HashMap<>();
        map.put("deptTree",sysTreeService.deptTree());
        return CommonReturnType.create(map);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json, SysUser sysUser){
        supervisionEnterpriseService.insert(json,sysUser);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json, SysUser sysUser){
        supervisionEnterpriseService.update(json,sysUser);
        return CommonReturnType.create(null);
    }


    //删除企业同时删除定位中的企业，要改，在service中写删除定位，或者在这写两个表都删除
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int id){
        supervisionEnterpriseService.delete(id);
        gridPointsService.deleteByEnterpriseId(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getCateAndLicence")
    @ResponseBody
    public CommonReturnType getCateAndSend(int id){
        Map<String,Object> map = new HashMap<>();
        map.put("categoryList",supervisionConfigCategoryService.getByPermiss(id));
        map.put("licenceList",supervisionConfigLicenceService.getByPermiss(id));
        return CommonReturnType.create(map);
    }

//    企业导入，着重修改这里，使之能够不删除修改
    @RequestMapping("/importExcel")
    @ResponseBody
    public CommonReturnType importExcel(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        JSONObject result=new JSONObject();
        if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"该文件类型已不支持，请使用07版本后缀为.xlsx版本导入");
        }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
           result=supervisionEnterpriseService.importExcel(file,7);
        }
        return CommonReturnType.create(result);
    }

    @RequestMapping("/getCount")
    @ResponseBody
    public CommonReturnType getCount(@RequestBody String json, SysUser sysUser){
        JSONObject jsonObject = JSON.parseObject(json);
        EnterpriseSearchParam enterpriseSearchParam= JSON.parseObject(json,EnterpriseSearchParam.class);
        Integer areaId = null;
        if(!StringUtils.isEmpty(jsonObject.getJSONArray("areaList").get(0)))
        {
            areaId = (Integer)jsonObject.getJSONArray("areaList").get(0);
        }
        boolean searchIndustry = StringUtils.isEmpty(jsonObject.getJSONArray("industryList").get(0));

        if(sysUser.getUserType()==1){
            return CommonReturnType.create("企业用户无统计数据");
        }else{
            return CommonReturnType.create(supervisionEnterpriseService.getCountPhone(enterpriseSearchParam,sysUser,areaId,searchIndustry));
        }
    }

    @RequestMapping("/getCountPC")
    @ResponseBody
    public CommonReturnType getCountPC(@RequestBody String json, SysUser sysUser){
        JSONObject jsonObject = JSON.parseObject(json);
        EnterpriseSearchParam enterpriseSearchParam= JSON.parseObject(json,EnterpriseSearchParam.class);
        Integer areaId = null;
        if(!StringUtils.isEmpty(jsonObject.getJSONArray("areaList").get(0)))
        {
            areaId = (Integer)jsonObject.getJSONArray("areaList").get(0);
        }
        boolean searchIndustry = StringUtils.isEmpty(jsonObject.getJSONArray("industryList").get(0));

        if(sysUser.getUserType()==1){
            return CommonReturnType.create("企业用户无统计数据");
        }else{
            return CommonReturnType.create(supervisionEnterpriseService.getCount(enterpriseSearchParam,sysUser,areaId,searchIndustry));
        }
    }

    @RequestMapping("/getPoints")
    @ResponseBody
    public CommonReturnType getSmilePoints(SysUser sysUser,@RequestBody String json){
        JSONObject jsonObject = JSON.parseObject(json);
        EnterpriseSearchParam enterpriseSearchParam= JSON.parseObject(json,EnterpriseSearchParam.class);
        if (sysUser.getUserType()==0){//管理员的筛选
            if(StringUtils.isEmpty(jsonObject.getJSONArray("industryList").get(0))) {
                enterpriseSearchParam.setIndustryList(sysIndustryService.getAll().stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
            if(StringUtils.isEmpty(jsonObject.getJSONArray("areaList").get(0))){
                enterpriseSearchParam.setAreaList(sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            }else{
                Integer areaId = (Integer)jsonObject.getJSONArray("areaList").get(0);
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            return CommonReturnType.create(supervisionEnterpriseService.getSmileMapPoints(enterpriseSearchParam));
        }
        else if(sysUser.getUserType()==2){//政府人员
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
            return CommonReturnType.create(supervisionEnterpriseService.getSmileMapPoints(enterpriseSearchParam));
        }
        else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非法用户");
        }
    }

    @RequestMapping("/changeGpsFlag")
    @ResponseBody
    public CommonReturnType changeGpsFlag(){
        supervisionEnterpriseService.changeGpsFlag();
        return CommonReturnType.create(null);
    }

    //小程序专用

    //修改证照/公示
    @RequestMapping("/changeLicensePhoto")
    @ResponseBody
    public ResultVo changeLicensePhoto(@RequestBody String json, SysUser sysUser){
        //EnterpriseParam enterpriseParam
        supervisionEnterpriseService.changeLicensePhoto(sysUser,json);
        return new ResultVo("新增或修改成功");
    }

    // 获取证照/公示的照片
    @RequestMapping("/getLicensePhotos")
    @ResponseBody
    public CommonReturnType getLicensePhotos(SysUser sysUser){
        int enterpriseId=sysUser.getInfoId();
        Map<String, Object> data = supervisionEnterpriseService.getLicensePhotos(enterpriseId);
        return CommonReturnType.create(data);
    }

    //获取企业VR
    @RequestMapping("/getVrUrl")
    @ResponseBody
    public ResultVo getVrUrl(SysUser sysUser){
        int enterpriseId=sysUser.getInfoId();
        Map<String,Object> result = new HashMap<>();
        result = supervisionEnterpriseService.getVrUrl(enterpriseId);
        return new ResultVo(result);
    }

    @RequestMapping("/getNearEnterprise")
    @ResponseBody
    public CommonReturnType getNearEnterprise(@RequestBody EnterpriseSearchParam enterpriseSearchParam,PageQuery pageQuery){
        List<NearEnterprise> nearEnterpriseList = gridPointsService.getNearEnterprise(enterpriseSearchParam,pageQuery);
        return CommonReturnType.create(nearEnterpriseList);
    }

    @RequestMapping("/getByEnterpriseId")
    @ResponseBody
    public CommonReturnType getByEnterpriseId(@RequestBody EnterpriseSearchParam enterpriseSearchParam){
        return CommonReturnType.create(gridPointsService.getEnterpriseByParam(enterpriseSearchParam));
    }

    @RequestMapping("/getNearEnterpriseScore")
    @ResponseBody
    public CommonReturnType getNearEnterpriseScore(@RequestBody EnterpriseSearchParam enterpriseSearchParam){
        List<NearEnterprise> nearEnterpriseList = gridPointsService.getNearEnterpriseScore(enterpriseSearchParam);
        return CommonReturnType.create(nearEnterpriseList);
    }
}
