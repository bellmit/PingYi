package com.example.upc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.GaParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.ComplaintLeaderSearchParam;
import com.example.upc.controller.searchParam.GaSearchParam;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysArea;
import com.example.upc.dataobject.SysDept;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequestMapping("/supervision/ga")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SupervisionGaController {

    @Autowired
    private SupervisionGaService supervisionGaService;
    @Autowired
    private SysTreeService sysTreeService;
    @Autowired
    private SysDutiesInfoService sysDutiesInfoService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private GridGridService gridGridService;
    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(@RequestBody String json,SysUser sysUser){
        JSONObject jsonObject = JSON.parseObject(json);
        GaSearchParam gaSearchParam= JSON.parseObject(json,GaSearchParam.class);
        PageQuery pageQuery=JSON.parseObject(json,PageQuery.class);
        if(sysUser.getUserType()==2||sysUser.getUserType()==0){
            if(StringUtils.isEmpty(jsonObject.getJSONArray("department").get(0))){
                gaSearchParam.setDepartment(null);
            }else {
                gaSearchParam.setDepartment(sysDeptService.getIdListSearch((Integer) jsonObject.getJSONArray("department").get(0)));
            }
            if(jsonObject.getInteger("gridId")!=null && jsonObject.getInteger("gridId")!=0 ){
                List<Integer> list = sysDeptAreaService.getIdListByArea(jsonObject.getInteger("gridId"));
                gaSearchParam.setDepartment(list);
            }
            if(sysUser.getUserType()==2){
                SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
                SysDept sysDept = sysDeptService.getById(supervisionGa.getDepartment());
                if(sysDept.getType()==2){
                    if(supervisionGa.getType()!=2){
                        gaSearchParam.setDepartment(sysDeptService.getIdListSearch(sysDept.getId()));
                    }
                }
            }
            return CommonReturnType.create(supervisionGaService.getPage(pageQuery,gaSearchParam));
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非法用户");
        }
    }

    @RequestMapping("/getDept")
    @ResponseBody
    public CommonReturnType getDept(SysUser sysUser){
        Map<String,Object> map = new HashMap<>();
        map.put("dept",sysTreeService.deptTree());
        map.put("duties",sysDutiesInfoService.getList());
        if(sysUser.getUserType()==2){
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            SysDept sysDept = sysDeptService.getById(supervisionGa.getDepartment());
            if(sysDept.getType()==2&&supervisionGa.getType()!=2){
                map.put("myDept",supervisionGaService.getMyDeptInfo(sysDept));
                }else{
                map.put("deptNumber",sysDeptService.countList());
                map.put("personNumber",supervisionGaService.countList());
                map.put("statistics",supervisionGaService.getStatistics());
            }
            }else{
            map.put("deptNumber",sysDeptService.countList());
            map.put("personNumber",supervisionGaService.countList());
            map.put("statistics",supervisionGaService.getStatistics());
        }

        return CommonReturnType.create(map);
    }
    @RequestMapping("/changeDept")
    @ResponseBody
    public CommonReturnType changeDept(@RequestParam("id") int id, @RequestParam("checkDept")String checkDept){
        supervisionGaService.changeDept(id,checkDept);

        return CommonReturnType.create(null);
    }

    @RequestMapping("/changeStop")
    @ResponseBody
    public CommonReturnType changeStop(int id){
        supervisionGaService.changeStop(id);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/insert")
    @ResponseBody
    public CommonReturnType insert(@RequestBody String json){
          GaParam gaParam = JSONObject.parseObject(json,GaParam.class);
        supervisionGaService.insert(gaParam);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(int gaId) {
        supervisionGaService.delete(gaId);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestBody String json){
        GaParam gaParam = JSONObject.parseObject(json,GaParam.class);
        supervisionGaService.update(gaParam);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/importExcel")//导入excel
    @ResponseBody
    public CommonReturnType importExcel(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(fileName.matches("^.+\\.(?i)(xls)$")){//03版本excel,xls
             supervisionGaService.importExcel(file,3);
        }else if (fileName.matches("^.+\\.(?i)(xlsx)$")){//07版本,xlsx
            supervisionGaService.importExcel(file,7);
        }
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getPageAllList")
    @ResponseBody
    public CommonReturnType getPageAllList(PageQuery pageQuery, ComplaintLeaderSearchParam complaintLeaderSearchParam){
        return CommonReturnType.create(supervisionGaService.getPageAllList(pageQuery, complaintLeaderSearchParam));

    }

    @RequestMapping("/getGaForMap")
    @ResponseBody
    public CommonReturnType getGaForMap(@Param("id") int id,@Param("level") int level){
            return CommonReturnType.create(supervisionGaService.getGaByAreaForMap(id));
    }
}
