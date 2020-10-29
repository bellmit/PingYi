package com.example.upc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.VideoParam;
import com.example.upc.controller.searchParam.VideoSearchParam;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dao.SysAreaMapper;
import com.example.upc.dao.VideoConfigExMapper;
import com.example.upc.dao.VideoParentMapper;
import com.example.upc.dataobject.*;
import com.example.upc.service.VideoParentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideoParentServiceImpl implements VideoParentService {
    @Autowired
    VideoParentMapper videoParentMapper;
    @Autowired
    VideoConfigExMapper videoConfigExMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;

    @Override
    public PageResult getPageSup(PageQuery pageQuery, VideoSearchParam videoSearchParam) {
        int count=videoParentMapper.countListSup(videoSearchParam);
        if (count > 0) {
            List<VideoParent> faList = videoParentMapper.getPageSup(pageQuery, videoSearchParam);
            PageResult<VideoParent> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<VideoParent> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, VideoSearchParam videoSearchParam) {
        int count=videoParentMapper.countListAdmin(videoSearchParam);
        if (count > 0) {
            List<VideoParent> faList = videoParentMapper.getPageAdmin(pageQuery, videoSearchParam);
            PageResult<VideoParent> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<VideoParent> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(String json, SysUser sysUser) {
        VideoParam videoParam = JSONObject.parseObject(json,VideoParam.class);
        VideoParent videoParent = new VideoParent();
        BeanUtils.copyProperties(videoParam,videoParent);

//        ValidationResult result = validator.validate(formatAdditive);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(videoParam.getEnterpriseId());
        if (supervisionEnterprise==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业，请重新选择");
        }
        else if (checkidExist(videoParam.getEnterpriseId(),videoParam.getId())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"此企业监控信息已存在，请查找进行修改");
        }
        else {
            videoParent.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
            videoParent.setPermissionId(supervisionEnterprise.getIdNumber());
            videoParent.setCharger(supervisionEnterprise.getCantacts());
            videoParent.setContact(supervisionEnterprise.getCantactWay());
            videoParent.setAddress(supervisionEnterprise.getRegisteredAddress());
            videoParent.setCheckLevel(supervisionEnterprise.getYearAssessment());
        }
        SysArea sysArea = sysAreaMapper.selectByPrimaryKey(supervisionEnterprise.getArea());
        if (sysArea == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"此企业地区不正确，请检查后重试");
        }
        else {
            videoParent.setArea(supervisionEnterprise.getArea());
            videoParent.setAreaName(sysArea.getName());
        }
        videoParent.setOperatorIp("124.124.124");
        videoParent.setOperatorTime(new Date());
        videoParent.setOperator("zcc");
        videoParentMapper.insertSelective(videoParent);//插入
        if(videoParent.getId()==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"插入失败");
        }

        videoConfigExMapper.deleteByParentId(videoParent.getId());//子表插入
        List<VideoConfigEx> videoConfigExList = videoParam.getList();
        if(videoConfigExList.size()>0){
            videoConfigExMapper.batchInsert(videoConfigExList.stream().map((recordConfig)->{

//                ValidationResult result1 = validator.validate(recordConfig);
//                if(result1.isHasErrors()){
//                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result1.getErrMsg());
//                }

                recordConfig.setOperatorIp("124.124.124");
                recordConfig.setOperatorTime(new Date());
                recordConfig.setOperator("zcc");
                recordConfig.setParentId(videoParent.getId());
                return recordConfig;}).collect(Collectors.toList()));
        }

        // TODO: sendEmail

    }
    @Override
    public void delete(int id) {
        VideoParent videoParent = videoParentMapper.selectByPrimaryKey(id);
        if(videoParent==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        videoParentMapper.deleteByPrimaryKey(id);
        videoConfigExMapper.deleteByParentId(id);
    }

    @Override
    @Transactional
    public void update(String json,SysUser sysUser) {
        VideoParam videoParam = JSONObject.parseObject(json,VideoParam.class);
        VideoParent videoParent = new VideoParent();
        BeanUtils.copyProperties(videoParam,videoParent);

//        ValidationResult result = validator.validate(formatAdditive);
//        if(result.isHasErrors()){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
//        }

        VideoParent before = videoParentMapper.selectByPrimaryKey(videoParent.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新项目不存在");
        }

        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(videoParam.getEnterpriseId());
        if (supervisionEnterprise==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业，请重新选择");
        }
        else if (checkidExist(videoParam.getEnterpriseId(),videoParam.getId())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"此企业监控信息已存在，请查找进行修改");
        }
        else {
            videoParent.setEnterpriseName(supervisionEnterprise.getEnterpriseName());
            videoParent.setPermissionId(supervisionEnterprise.getIdNumber());
            videoParent.setCharger(supervisionEnterprise.getCantacts());
            videoParent.setContact(supervisionEnterprise.getCantactWay());
            videoParent.setAddress(supervisionEnterprise.getRegisteredAddress());
            videoParent.setCheckLevel(supervisionEnterprise.getYearAssessment());
        }
        SysArea sysArea = sysAreaMapper.selectByPrimaryKey(supervisionEnterprise.getArea());
        if (sysArea == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"此企业地区不正确，请检查后重试");
        }
        else {
            videoParent.setArea(supervisionEnterprise.getArea());
            videoParent.setAreaName(sysArea.getName());
        }

        videoParent.setOperatorIp("124.124.124");
        videoParent.setOperatorTime(new Date());
        videoParent.setOperator("zcc");
        videoConfigExMapper.deleteByParentId(videoParent.getId());
        List<VideoConfigEx> videoConfigExList = videoParam.getList();
        if(videoConfigExList.size()>0){
            videoConfigExMapper.batchInsert(videoConfigExList.stream().map((list)->{
                list.setOperatorIp("124.124.124");
                list.setOperatorTime(new Date());
                list.setOperator("zcc");
                list.setParentId(videoParent.getId());
                return list;}).collect(Collectors.toList()));
        }
        videoParentMapper.updateByPrimaryKeySelective(videoParent);
    }

    @Override
    public VideoParam getById(int id) {
        VideoParent videoParent = videoParentMapper.selectByPrimaryKey(id);
        VideoParam videoParam = new VideoParam();
        BeanUtils.copyProperties(videoParent, videoParam);
        List<VideoConfigEx> list = videoConfigExMapper.selectByParentId(videoParent.getId());
        videoParam.setList(list);
        return videoParam;
    }


    @Override
    public VideoParam selectByEnterpriseId(int id) {
        Integer a = videoParentMapper.selectByEnterpriseId(id);
        if (a == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业视频信息，请检查后重试");
        }
        else {
            VideoParent videoParent = videoParentMapper.selectByPrimaryKey(a);
            VideoParam videoParam = new VideoParam();
            BeanUtils.copyProperties(videoParent, videoParam);
            List<VideoConfigEx> list = videoConfigExMapper.selectByParentId(videoParent.getId());
            videoParam.setList(list);
            return videoParam;
        }
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

    public boolean checkidExist(Integer eid,Integer id) {
        return videoParentMapper.countById(eid,id) > 0;
    }

    /**
     * 小程序专用serviceImpl
     */
    @Override
    public List<Object> getVideoListById(int enterpriseId){
        Integer a = videoParentMapper.selectByEnterpriseId(enterpriseId);
        if (a == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业视频信息，请检查后重试");
        }else {
            VideoParent videoParent = videoParentMapper.selectByPrimaryKey(a);
            List<VideoConfigEx> videoList = videoConfigExMapper.selectByParentId(videoParent.getId());
            List<Object> resultList= new ArrayList<>();
            String tempUrl1 = "rtmp://"+videoParent.getRtmpIp()+":"+videoParent.getRtmpPort()+"/live/pag/"+videoParent.getVagIp()+"/"+videoParent.getVagPort()+"/";
            String tempUrl2 = "http://"+videoParent.getHttpIp()+":"+videoParent.getHttpPort()+"/live/cameraid/";
            int id = 1;
            if(videoParent.getType()=="海康"){
                for(VideoConfigEx attribute : videoList) {
                    Map<String,Object> tempItem = new LinkedHashMap<>();
                    tempItem.put("id",id);
                    tempItem.put("videoPosition",attribute.getPosition());
                    tempItem.put("videoUrl",tempUrl1+attribute.getNumber()+"/0/MAIN/TCP");
                    resultList.add(tempItem);
                }
            }
            else {
                for(VideoConfigEx attribute : videoList) {
                    Map<String,Object> tempItem = new LinkedHashMap<>();
                    tempItem.put("id",id);
                    tempItem.put("videoPosition",attribute.getPosition());
                    tempItem.put("videoUrl",tempUrl2+attribute.getNumber()+"%24"+attribute.getChannelNumber()+"/substream/"+attribute.getByteType()+".m3u8");
                    resultList.add(tempItem);
                }
            }

            return resultList;
        }

    }
}
