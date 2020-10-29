package com.example.upc.controller;

import com.example.upc.common.BusinessException;
import com.example.upc.common.CommonReturnType;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.UserParam;
import com.example.upc.controller.searchParam.UserSearchParam;
import com.example.upc.dao.SysUserErrorMapper;
import com.example.upc.dataobject.*;
import com.example.upc.redis.UserSessionService;
import com.example.upc.service.*;
import com.example.upc.util.StringUtil;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zcc
 * @date 2019/3/28 13:19
 */
@Controller
@RequestMapping("/sys/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysTreeService sysTreeService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private UserSessionService userSessionService;
    @Autowired
    private SysDeptIndustryService sysDeptIndustryService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private SupervisionGaService supervisionGaService;
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private SysIndustryService sysIndustryService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SupervisionEnterpriseService supervisionEnterpriseService;
    @Autowired
    SysUserErrorMapper sysUserErrorMapper;

    @RequestMapping("/loginTest")
    @ResponseBody
    public CommonReturnType loginTest(HttpServletResponse response, UserParam userParam) throws InvocationTargetException, IllegalAccessException {
        return CommonReturnType.create(userSessionService.login(response,userParam));
    }

    @RequestMapping("/dateTest")
    @ResponseBody
    public CommonReturnType dateTest(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        System.out.println("今天的日期为:" + formatter.format(date));
        //new一个Calendar类,把Date放进去
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        System.out.println("明天的日期为:" + formatter.format(calendar.getTime()));
        SysUserError sysUserError = sysUserErrorMapper.selectByUserId(1,formatter.format(date),formatter.format(calendar.getTime()));
        return CommonReturnType.create(sysUserError);
    }

    @RequestMapping("/getTest")
    @ResponseBody
    public CommonReturnType getTest(SysUser sysUser){
        int userId = sysUser.getId();
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysTreeService.userAclTree(userId));
        map.put("roles", sysRoleService.getRoleListByUserId(userId));
        map.put("userName",sysUser.getUsername());
        map.put("userType",sysUser.getUserType());
        if(sysUser.getUserType()==0){
            map.put("industryList",sysIndustryService.getAll());
            map.put("areaList",sysAreaService.areaTree());
        } else if(sysUser.getUserType()==1){
            map.put("industryList",sysIndustryService.getAll());
            map.put("areaList",sysAreaService.areaTree());
            map.put("userInfo",supervisionEnterpriseService.selectById(sysUser.getInfoId()));
        } else if(sysUser.getUserType()==2){
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            map.put("industryList",sysDeptIndustryService.getListByDeptId(supervisionGa.getDepartment()));
            map.put("areaList",sysDeptAreaService.getDeptAreaTree(supervisionGa.getDepartment()));
            map.put("userInfo",supervisionGa);
        }
        return CommonReturnType.create(map);
    }

    @RequestMapping("/govGet")
    @ResponseBody
    public CommonReturnType govGet(SysUser sysUser){
        if (sysUser.getUserType()==0){
            return CommonReturnType.create(sysUserService.govGet(sysIndustryService.getAll(),sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()),null));
        }else if(sysUser.getUserType()==2){
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            List<SysIndustry> sysIndustryList = sysDeptIndustryService.getListByDeptId(supervisionGa.getDepartment());
            List<Integer> sysAreaList = sysDeptAreaService.getIdListByDeptId(supervisionGa.getDepartment());
            SysDept sysDept = sysDeptService.getById(supervisionGaService.getById(sysUser.getInfoId()).getDepartment());
            String supervisor = null;
            if(sysDept.getType()==2){
                if(supervisionGa.getType()!=2){
                    supervisor=supervisionGa.getName();
                }
            }
            return CommonReturnType.create(sysUserService.govGet(sysIndustryList,sysAreaList,supervisor));
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"非法用户");
        }
    }

    @RequestMapping("/logoutTest")
    @ResponseBody
    public CommonReturnType logoutTest(HttpServletRequest request,HttpServletResponse response){
        userSessionService.logout(request,response);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/save")
    @ResponseBody
    public CommonReturnType saveUser(UserParam param) {
        sysUserService.save(param);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType updateUser(UserParam param) {
        sysUserService.update(param);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/changePsd")
    @ResponseBody
    public CommonReturnType changePsd(@RequestParam("id")int id,@RequestParam("password")String password) {
        sysUserService.changePsd(id,password);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/changeUserPsd")
    @ResponseBody
    public CommonReturnType changeUserPsd(SysUser sysUser,@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword) {
        sysUserService.changeUserPsd(sysUser.getId(),oldPassword,newPassword);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/page")
    @ResponseBody
    public CommonReturnType page(@RequestParam("deptId") int deptId, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery);
        return CommonReturnType.create(result);
    }

    @RequestMapping("/getPage")
    @ResponseBody
    public CommonReturnType getPage(PageQuery pageQuery, UserSearchParam userSearchParam) {
        return CommonReturnType.create(sysUserService.getAllPage(pageQuery,userSearchParam));
    }

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestParam("userId")int userId) {
        sysUserService.delete(userId);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/acls")
    @ResponseBody
    public CommonReturnType acls(@RequestParam("userId") int userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysTreeService.userAclTree(userId));
        map.put("roles", sysRoleService.getRoleListByUserId(userId));
        return CommonReturnType.create(map);
    }

    @RequestMapping("/userRole")
    @ResponseBody
    public CommonReturnType userRole(@RequestParam("userId") int userId) {
        return CommonReturnType.create(sysRoleService.getRoleListByUserId(userId));
    }

    @RequestMapping("/changeRole")
    @ResponseBody
    public CommonReturnType changeRole(@RequestParam("userId") int userId, @RequestParam(value = "roleIds", required = false, defaultValue = "") String roleIds) {
        List<Integer> roleIdList = StringUtil.splitToListInt(roleIds);
        sysRoleUserService.changeRoleUsers(roleIdList,userId);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public CommonReturnType getUserInfo(SysUser sysUser){
        sysUser.setPassword("");
        return CommonReturnType.create(sysUser);
    }
}

