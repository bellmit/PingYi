package com.example.upc.controller;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.DeptParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.dataobject.SysDept;
import com.example.upc.service.*;
import com.example.upc.service.model.DeptLevelDto;
import com.example.upc.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcc
 * @date 2019/3/28 13:19
 */
@Controller
@RequestMapping("/sys/dept")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysTreeService sysTreeService;
    @Autowired
    private SysDeptIndustryService sysDeptIndustryService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private SysDeptGridService sysDeptGridService;
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private GridMapInfoService gridMapInfoService;
    @Autowired
    private SysIndustryService sysIndustryService;

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery){
        return CommonReturnType.create(sysDeptService.getPage(pageQuery));
    }

    @RequestMapping("/save")
    @ResponseBody
    public CommonReturnType saveDept(DeptParam param) {
        sysDeptService.save(param);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType updateDept(DeptParam param){
        sysDeptService.update(param);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType deleteDept(@RequestParam("id") int id){
        sysDeptService.delete(id);
        return CommonReturnType.create(null);
    }
    @RequestMapping("/tree")
    @ResponseBody
    public CommonReturnType tree(){
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return CommonReturnType.create(dtoList);
    }
    @RequestMapping("/getAll")
    @ResponseBody
    public CommonReturnType getAll(){
        return CommonReturnType.create(sysDeptService.getAll());
    }
    @RequestMapping("/getIndustryIds")
    @ResponseBody
    public CommonReturnType getIndustryIds(@RequestParam("deptId") int deptId) {
        Map<String,Object> map =new HashMap<>();
        map.put("tree",sysIndustryService.getAll());
        map.put("checkKey",sysDeptIndustryService.getIdListByDeptId(deptId));
        return CommonReturnType.create(map);
    }

    @RequestMapping("/changeIndustry")
    @ResponseBody
    public CommonReturnType changeIndustryIds(@RequestParam("deptId") int deptId, @RequestParam(value = "industryIds", required = false, defaultValue = "") String industryIds) {
        List<Integer> industryIdList = StringUtil.splitToListInt(industryIds);
        sysDeptIndustryService.changeDeptIndustries(deptId,industryIdList);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getAreaIds")
    @ResponseBody
    public CommonReturnType getAreaIds(@RequestParam("deptId") int deptId) {
        Map<String,Object> map =new HashMap<>();
        map.put("tree",sysAreaService.areaTree());
        map.put("checkKey",sysDeptAreaService.getIdListByDeptId(deptId));
        return CommonReturnType.create(map);
    }

    @RequestMapping("/changeArea")
    @ResponseBody
    public CommonReturnType changeAreaIds(@RequestParam("deptId") int deptId, @RequestParam(value = "areaIds", required = false, defaultValue = "") String areaIds,@RequestParam(value = "halfIds", required = false, defaultValue = "") String halfIds) {
        List<Integer> areaIdList = StringUtil.splitToListInt(areaIds);
        List<Integer> areaHalfList = StringUtil.splitToListInt(halfIds);
        sysDeptAreaService.changeDeptAreas(deptId,areaIdList,areaHalfList);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getGridIds")
    @ResponseBody
    public CommonReturnType getGridIds(@RequestParam("deptId") int deptId) {
        Map<String,Object> map =new HashMap<>();
        map.put("tree",gridMapInfoService.gridTree());
        map.put("checkKey",sysDeptGridService.getIdListByDeptId(deptId));
        return CommonReturnType.create(map);
    }

    @RequestMapping("/changeGrid")
    @ResponseBody
    public CommonReturnType changeGridIds(@RequestParam("deptId") int deptId, @RequestParam(value = "gridIds", required = false, defaultValue = "") String gridIds) {
        List<Integer> gridIdList = StringUtil.splitToListInt(gridIds);
        sysDeptGridService.changeDeptGrids(deptId,gridIdList);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/changeLeader")
    @ResponseBody
    public CommonReturnType changeLeader(@RequestParam("deptId") int deptId, @RequestParam("leaderId") int leaderId) {
        sysDeptService.changeLeaderId(deptId,leaderId);
        return CommonReturnType.create(null);
    }
}
