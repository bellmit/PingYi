package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.VideoRecordEnterpriseParam;
import com.example.upc.controller.searchParam.VideoSearchParam;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dao.SysAreaMapper;
import com.example.upc.dao.VideoRecordMapper;
import com.example.upc.dataobject.SysUser;
import com.example.upc.dataobject.VideoRecord;
import com.example.upc.service.VideoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class VideoRecordServiceImpl implements VideoRecordService {
    @Autowired
    VideoRecordMapper videoRecordMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public PageResult getPageSup(PageQuery pageQuery, VideoSearchParam videoSearchParam) {
        int count=videoRecordMapper.countListSup(videoSearchParam);
        if (count > 0) {
            List<VideoRecord> faList = videoRecordMapper.getPageSup(pageQuery, videoSearchParam);
            PageResult<VideoRecord> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<VideoRecord> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<VideoRecordEnterpriseParam> getPageLookSup(PageQuery pageQuery, VideoSearchParam videoSearchParam) {
        int count=videoRecordMapper.countListLookSup(videoSearchParam);
        if (count > 0) {
            List<VideoRecordEnterpriseParam> faList = videoRecordMapper.getPageLookSup(pageQuery, videoSearchParam);
            PageResult<VideoRecordEnterpriseParam> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<VideoRecordEnterpriseParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, VideoSearchParam videoSearchParam) {
        int count=videoRecordMapper.countListAdmin(videoSearchParam);
        if (count > 0) {
            List<VideoRecord> faList = videoRecordMapper.getPageAdmin(pageQuery, videoSearchParam);
            PageResult<VideoRecord> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<VideoRecord> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<VideoRecordEnterpriseParam> getPageLookAdmin(PageQuery pageQuery, VideoSearchParam videoSearchParam) {
        int count=videoRecordMapper.countListLookAdmin(videoSearchParam);
        if (count > 0) {
            List<VideoRecordEnterpriseParam> faList = videoRecordMapper.getPageLookAdmin(pageQuery, videoSearchParam);
            PageResult<VideoRecordEnterpriseParam> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<VideoRecordEnterpriseParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<VideoRecord> getPageLookByEnterpriseId (PageQuery pageQuery, int id) {
        int count=videoRecordMapper.countListLookByEnterpriseId(id);
        if (count > 0) {
            List<VideoRecord> faList = videoRecordMapper.getPageLookByEnterpriseId(pageQuery, id);
            PageResult<VideoRecord> pageResult = new PageResult<>();
            pageResult.setData(faList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<VideoRecord> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void delete(int id) {
        VideoRecord videoRecord = videoRecordMapper.selectByPrimaryKey(id);
        if(videoRecord==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        videoRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void insert(VideoRecord videoRecord, SysUser sysUser) {
        VideoRecord videoRecord1 = new VideoRecord();

        ValidationResult result = validator.validate(videoRecord);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        videoRecord1.setEnterpriseId(videoRecord.getEnterpriseId());
        videoRecord1.setEnterpriseName(videoRecord.getEnterpriseName());
        videoRecord1.setPermissionId(videoRecord.getPermissionId());
        videoRecord1.setCharger(videoRecord.getCharger());
        videoRecord1.setContact(videoRecord.getContact());
        videoRecord1.setArea(videoRecord.getArea());
        videoRecord1.setAreaName(videoRecord.getAreaName());
        videoRecord1.setAddress(videoRecord.getAddress());
        videoRecord1.setType(videoRecord.getType());
        videoRecord1.setRecordPicture1(videoRecord.getRecordPicture1());
        videoRecord1.setRecordPicture2(videoRecord.getRecordPicture2());
        videoRecord1.setRecordPerson(videoRecord.getRecordPerson());
        videoRecord1.setRecordTime(videoRecord.getRecordTime());
        videoRecord1.setLevel(videoRecord.getLevel());
        videoRecord1.setRecordCount(videoRecordMapper.countRecord(videoRecord.getEnterpriseId(), videoRecord.getId()));
        videoRecord1.setRecordContent(videoRecord.getRecordContent());
        //如果需要审核，则下方出现选择审核人，传回id和名字，但前端不填写审核时间和审核内容，因此需要自动填入空
        if (videoRecord.getType()==1){//不需审核
            videoRecord1.setHandlePersonId(sysUser.getInfoId());
            videoRecord1.setHandlePersonName(videoRecord.getRecordPerson());
            videoRecord1.setHandleTime(new Date());
            videoRecord1.setHandleContent(videoRecord.getHandleContent());
        }
        else if (videoRecord.getType()==2) {//需要审核
            videoRecord1.setHandlePersonId(videoRecord.getHandlePersonId());
            videoRecord1.setHandlePersonName(videoRecord.getHandlePersonName());
            videoRecord1.setHandleTime(new Date());
            videoRecord1.setHandleContent("");
        }
        videoRecord1.setOperator("zcc");
        videoRecord1.setOperatorIp("124.124.124");
        videoRecord1.setOperatorTime(new Date());
        videoRecordMapper.insertSelective(videoRecord1);//插入

        // TODO: sendEmail

    }

    @Override
    @Transactional
    public void update(VideoRecord videoRecord, SysUser sysUser) {
        VideoRecord videoRecord1 = new VideoRecord();

        ValidationResult result = validator.validate(videoRecord);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if (videoRecord.getHandleContent().isEmpty()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"处理意见不能为空");
        }

        if (videoRecord.getHandleTime()==null)
        {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"处理时间不能为空");
        }
        videoRecord1.setId(videoRecord.getId());
        videoRecord1.setEnterpriseId(videoRecord.getEnterpriseId());
        videoRecord1.setEnterpriseName(videoRecord.getEnterpriseName());
        videoRecord1.setPermissionId(videoRecord.getPermissionId());
        videoRecord1.setCharger(videoRecord.getCharger());
        videoRecord1.setContact(videoRecord.getContact());
        videoRecord1.setArea(videoRecord.getArea());
        videoRecord1.setAreaName(videoRecord.getAreaName());
        videoRecord1.setAddress(videoRecord.getAddress());
        videoRecord1.setType(videoRecord.getType());
        videoRecord1.setRecordPicture1(videoRecord.getRecordPicture1());
        videoRecord1.setRecordPicture2(videoRecord.getRecordPicture2());
        videoRecord1.setRecordPerson(videoRecord.getRecordPerson());
        videoRecord1.setRecordTime(videoRecord.getRecordTime());
        videoRecord1.setLevel(videoRecord.getLevel());
        videoRecord1.setRecordCount(videoRecordMapper.countRecord(videoRecord.getEnterpriseId(), videoRecord.getId()));
        videoRecord1.setRecordContent(videoRecord.getRecordContent());
        videoRecord1.setHandlePersonId(videoRecord.getHandlePersonId());
        videoRecord1.setHandlePersonName(videoRecord.getHandlePersonName());
        //如果需要审核，审核人拿到这条数据
        if (videoRecord.getType()==2) {
            videoRecord1.setHandleTime(videoRecord.getHandleTime());
            videoRecord1.setHandleContent(videoRecord.getHandleContent());
            videoRecord1.setType(1);
        }
        videoRecord1.setOperator("zcc");
        videoRecord1.setOperatorIp("124.124.124");
        videoRecord1.setOperatorTime(new Date());
        videoRecordMapper.updateByPrimaryKeySelective(videoRecord1);

        // TODO: sendEmail

    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }

    @Override
    public VideoRecord getRecordById(Integer id)
    {
        VideoRecord videoRecord = videoRecordMapper.getRecordById(id);
        return videoRecord;
    }

}
