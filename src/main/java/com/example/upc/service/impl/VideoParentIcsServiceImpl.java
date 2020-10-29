package com.example.upc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.VideoIcsParam;
import com.example.upc.controller.searchParam.VideoSearchParam;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dao.SysAreaMapper;
import com.example.upc.dao.VideoConfigIcsMapper;
import com.example.upc.dao.VideoParentIcsMapper;
import com.example.upc.dataobject.*;
import com.example.upc.service.VideoParentIcsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoParentIcsServiceImpl implements VideoParentIcsService{
    @Autowired
    VideoParentIcsMapper videoParentIcsMapper;
    @Autowired
    VideoConfigIcsMapper videoConfigIcsMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;

    @Override
    public PageResult getPageSup(PageQuery pageQuery, VideoSearchParam videoSearchParam) {
        int count=videoParentIcsMapper.countListSup(videoSearchParam);
        if (count > 0) {
            List<VideoParentIcs> faList = videoParentIcsMapper.getPageSup(pageQuery, videoSearchParam);
            PageResult<VideoParentIcs> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<VideoParentIcs> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, VideoSearchParam videoSearchParam) {
        int count=videoParentIcsMapper.countListAdmin(videoSearchParam);
        if (count > 0) {
            List<VideoParentIcs> faList = videoParentIcsMapper.getPageAdmin(pageQuery, videoSearchParam);
            PageResult<VideoParentIcs> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<VideoParentIcs> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(String json, SysUser sysUser) {
        VideoIcsParam videoIcsParam = JSONObject.parseObject(json,VideoIcsParam.class);
        VideoParentIcs videoParentIcs = new VideoParentIcs();
        BeanUtils.copyProperties(videoIcsParam,videoParentIcs);

//        ValidationResult result = validator.validate(formatAdditive);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(videoIcsParam.getEnterpriseId());
        if (supervisionEnterprise==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业，请重新选择");
        }
        else if (checkidExist(videoIcsParam.getEnterpriseId(),videoIcsParam.getId())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"此企业监控信息已存在，请查找进行修改");
        }
        else {
            videoParentIcs.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
            videoParentIcs.setPermissionId(supervisionEnterprise.getIdNumber());
            videoParentIcs.setCharger(supervisionEnterprise.getCantacts());
            videoParentIcs.setContact(supervisionEnterprise.getCantactWay());
            videoParentIcs.setAddress(supervisionEnterprise.getRegisteredAddress());
            videoParentIcs.setCheckLevel(supervisionEnterprise.getYearAssessment());
        }
        SysArea sysArea = sysAreaMapper.selectByPrimaryKey(supervisionEnterprise.getArea());
        if (sysArea == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"此企业地区不正确，请检查后重试");
        }
        else {
            videoParentIcs.setArea(supervisionEnterprise.getArea());
            videoParentIcs.setAreaName(sysArea.getName());
        }
        videoParentIcs.setOperatorIp("124.124.124");
        videoParentIcs.setOperatorTime(new Date());
        videoParentIcs.setOperator("zcc");
        videoParentIcsMapper.insertSelective(videoParentIcs);//插入
        if(videoParentIcs.getId()==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"插入失败");
        }

        videoConfigIcsMapper.deleteByParentId(videoParentIcs.getId());//子表插入
        List<VideoConfigIcs> videoConfigIcsList = videoIcsParam.getList();
        if(videoConfigIcsList.size()>0){
            videoConfigIcsMapper.batchInsert(videoConfigIcsList.stream().map((recordConfig)->{

//                ValidationResult result1 = validator.validate(recordConfig);
//                if(result1.isHasErrors()){
//                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result1.getErrMsg());
//                }

                recordConfig.setOperatorIp("124.124.124");
                recordConfig.setOperatorTime(new Date());
                recordConfig.setOperator("zcc");
                recordConfig.setParentId(videoParentIcs.getId());
                return recordConfig;}).collect(Collectors.toList()));
        }

        // TODO: sendEmail

    }
    @Override
    public void delete(int id) {
        VideoParentIcs videoParentIcs = videoParentIcsMapper.selectByPrimaryKey(id);
        if(videoParentIcs==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        videoParentIcsMapper.deleteByPrimaryKey(id);
        videoConfigIcsMapper.deleteByParentId(id);
    }

    @Override
    @Transactional
    public void update(String json,SysUser sysUser) {
        VideoIcsParam videoIcsParam = JSONObject.parseObject(json,VideoIcsParam.class);
        VideoParentIcs videoParentIcs = new VideoParentIcs();
        BeanUtils.copyProperties(videoIcsParam,videoParentIcs);
//        ValidationResult result = validator.validate(formatAdditive);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }

        VideoParentIcs before = videoParentIcsMapper.selectByPrimaryKey(videoIcsParam.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新项目不存在");
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(videoIcsParam.getEnterpriseId());
        if (supervisionEnterprise==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业，请重新选择");
        }
        else if (checkidExist(videoIcsParam.getEnterpriseId(),videoIcsParam.getId())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"此企业监控信息已存在，请查找进行修改");
        }
        else {
            videoParentIcs.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
            videoParentIcs.setPermissionId(supervisionEnterprise.getIdNumber());
            videoParentIcs.setCharger(supervisionEnterprise.getCantacts());
            videoParentIcs.setContact(supervisionEnterprise.getCantactWay());
            videoParentIcs.setAddress(supervisionEnterprise.getRegisteredAddress());
            videoParentIcs.setCheckLevel(supervisionEnterprise.getYearAssessment());
        }
        SysArea sysArea = sysAreaMapper.selectByPrimaryKey(supervisionEnterprise.getArea());
        if (sysArea == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"此企业地区不正确，请检查后重试");
        }
        else {
            videoParentIcs.setArea(supervisionEnterprise.getArea());
            videoParentIcs.setAreaName(sysArea.getName());
        }

        videoParentIcs.setOperatorIp("124.124.124");
        videoParentIcs.setOperatorTime(new Date());
        videoParentIcs.setOperator("zcc");
        videoConfigIcsMapper.deleteByParentId(videoParentIcs.getId());
        List<VideoConfigIcs> videoConfigIcsList = videoIcsParam.getList();
        if(videoConfigIcsList.size()>0){
            videoConfigIcsMapper.batchInsert(videoConfigIcsList.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(videoParentIcs.getId());
                return list;}).collect(Collectors.toList()));
        }
        videoParentIcsMapper.updateByPrimaryKeySelective(videoParentIcs);
    }

    @Override
    public VideoIcsParam getById(int id) {
        VideoParentIcs videoParentIcs = videoParentIcsMapper.selectByPrimaryKey(id);
        VideoIcsParam videoIcsParam = new VideoIcsParam();
        BeanUtils.copyProperties(videoParentIcs, videoIcsParam);
        List<VideoConfigIcs> list = videoConfigIcsMapper.selectByParentId(videoParentIcs.getId());
        videoIcsParam.setList(list);
        return videoIcsParam;
    }

    @Override
    public VideoIcsParam selectByEnterpriseId(int id) {
        Integer a = videoParentIcsMapper.selectByEnterpriseId(id);
        if (a == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业视频信息，请检查后重试");
        }
        else {
            VideoParentIcs videoParentIcs = videoParentIcsMapper.selectByPrimaryKey(a);
            VideoIcsParam videoIcsParam = new VideoIcsParam();
            BeanUtils.copyProperties(videoParentIcs, videoIcsParam);
            List<VideoConfigIcs> list = videoConfigIcsMapper.selectByParentId(videoParentIcs.getId());
            videoIcsParam.setList(list);
            return videoIcsParam;
        }
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

    public boolean checkidExist(Integer eid,Integer id) {
        return videoParentIcsMapper.countById(eid,id) > 0;
    }
}
