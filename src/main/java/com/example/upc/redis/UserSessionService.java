package com.example.upc.redis;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.FaceParam;
import com.example.upc.controller.param.UserParam;
import com.example.upc.dao.SupervisionCaMapper;
import com.example.upc.dao.SysUserErrorMapper;
import com.example.upc.dao.SysUserMapper;
import com.example.upc.dataobject.SupervisionCa;
import com.example.upc.dataobject.SysUser;
import com.example.upc.dataobject.SysUserError;
import com.example.upc.util.MD5Util;
import com.example.upc.util.UUIDUtil;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zcc
 * @date 2019/5/25 21:02
 */
@Service
public class UserSessionService {
    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    RedisService redisService;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysUserErrorMapper sysUserErrorMapper;
    @Autowired
    SupervisionCaMapper supervisionCaMapper;

    public SysUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            throw new BusinessException(EmBusinessError.PLEASE_LOGIN);
        }
        SysUser sysUser =(SysUser)redisService.getUser(SessionUserKey.token, token);

        //延长有效期
        if(sysUser != null) {
            addCookie(response, token, sysUser);
        }else {
            throw new BusinessException(EmBusinessError.PLEASE_LOGIN);
        }
        return sysUser;
    }

    public boolean logout(HttpServletRequest request,HttpServletResponse response){
        String paramToken = request.getParameter(UserSessionService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, UserSessionService.COOKIE_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return true;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        redisService.delUser(SessionUserKey.token, token);
        return true;
    }

    public boolean login(HttpServletResponse response, UserParam userParam) throws BusinessException, InvocationTargetException, IllegalAccessException {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        //new一个Calendar类,把Date放进去
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);

        if(userParam == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"参数错误");
        }

        String loginName = userParam.getLoginName();
        String formPass = userParam.getPassword();
        //判断账号是否存在
        SysUser sysUser = sysUserMapper.selectByLoginName(loginName);
        if(sysUser == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"帐号不存在");
        }
        //验证密码
        String dbPass = sysUser.getPassword();
        MD5Util md5Code =new MD5Util();
        SysUserError sysUserError = sysUserErrorMapper.selectByUserId(sysUser.getId(),formatter.format(date),formatter.format(calendar.getTime()));
        if(!md5Code.md5(formPass).equals(dbPass)) {
            //如果错误log为空，创建新记录，
            if (sysUserError==null){
                SysUserError sysUserError1 = new SysUserError();
                sysUserError1.setUserId(sysUser.getId());
                sysUserError1.setError(1);
                sysUserErrorMapper.insertSelective(sysUserError1);
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"密码错误，可再尝试4次！");
            }
            //如果有记录，那么将记录中的
            else {
                if (sysUserError.getError()==5){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"您今日已经尝试登录5次，请明日再试！");
                }
                else {
                    int a = sysUserError.getError()+1;
                    sysUserError.setError(a);
                    sysUserErrorMapper.updateByPrimaryKeySelective(sysUserError);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"密码错误，可再尝试"+(5-a)+"次！");
                }
            }
        }
        if (sysUserError!=null&&sysUserError.getError()==5){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"您今日已经尝试登录5次，请明日再试！");
        }
        sysUser.setPlatType(0);
        //生成cookie
        String token = sysUser.getId().toString()+'_'+UUIDUtil.uuid();

        addCookie(response, token, sysUser);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, SysUser sysUser) {
        redisService.setUser(SessionUserKey.token, token, sysUser);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(SessionUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[]  cookies = request.getCookies();
        if(cookies==null){
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    // 小程序登录
    public Map<String, Object>  miniUserLogin(HttpServletResponse response, UserParam userParam) {
        if(userParam == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"参数错误");
        }

        String loginName = userParam.getLoginName();
        String formPass = userParam.getPassword();
        String weChatId = userParam.getWeChatId();

        //判断账号是否存在
        SysUser sysUser = sysUserMapper.selectByLoginName(loginName);
        if(sysUser == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"帐号不存在");
        }

        //验证密码
        String dbPass = sysUser.getPassword();
        MD5Util md5Code =new MD5Util();

        if(!md5Code.md5(formPass).equals(dbPass)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"密码错误");
        }

        SupervisionCa supervisionCa = supervisionCaMapper.getCaInfoByWeChatId(weChatId);
        Map<String, Object> result = new HashMap<>();
        //默认是首次登录false
        result.put("flag", false);
        // 企业id
        result.put("enterpriseId", sysUser.getInfoId());
        if(supervisionCa==null){
            result.put("userId", null);
        }
        else{
            if(!supervisionCa.getCompanyId().equals(sysUser.getInfoId()))
            {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"您不是该公司的员工");
            }
            //是该公司员工且非首次登录
            result.put("flag", true);
            //userId
            result.put("userId", supervisionCa.getId());
        }
        return result;
    }

    // 小程序登录
    public FaceParam checkWeChatId(HttpServletResponse response,UserParam userParam) {
        SupervisionCa supervisionCa = supervisionCaMapper.selectByPrimaryKey(userParam.getUserId());

        if(supervisionCa.getWeChatId()==null||"".equals(supervisionCa.getWeChatId().trim()))
        {
            if(userParam.getWeChatId()==null||"".equals(userParam.getWeChatId()))
            {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"未发送wechatId");
            }
            supervisionCa.setWeChatId(userParam.getWeChatId());
            supervisionCaMapper.updateByPrimaryKey(supervisionCa);
        }

        SysUser sysUser = sysUserMapper.selectByLoginName(userParam.getLoginName());
        sysUser.setWeChatId(supervisionCa.getWeChatId());
        sysUser.setPlatType(1);
        String token = sysUser.getId().toString()+'_'+UUIDUtil.uuid();

        addCookie(response, token, sysUser);

        FaceParam faceParam = new FaceParam();
        faceParam.setName(supervisionCa.getName());
        return faceParam;
    }

    public boolean touristLogin(HttpServletResponse response, int id,String weChatId) throws BusinessException {

        String loginName = "tourist";
        SysUser sysUser = new SysUser();
        sysUser.setUsername("游客");
        sysUser.setLoginName(loginName);
        sysUser.setInfoId(id);
        sysUser.setWeChatId(weChatId);
        //生成cookie
        String token = "12345"+'_'+UUIDUtil.uuid();
        addCookie(response, token, sysUser);
        return true;
    }

    public int getEnterpriseIdByInfoId(SysUser sysUser){
        int infoId = sysUser.getInfoId();
        return sysUserMapper.getEnterpriseIdByInfoId(infoId);
    }
}
