package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.VideoConfigParam;
import com.example.upc.dao.VideoConfigMapper;
import com.example.upc.dataobject.VideoConfig;
import com.example.upc.service.VideoConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @author zcc
 * @date 2019/9/6 14:39
 */
@Service
public class VideoConfigServiceImpl implements VideoConfigService {
    @Autowired
    private VideoConfigMapper videoConfigMapper;
    @Override
    public PageResult<VideoConfigParam> getPage(PageQuery pageQuery) {
        int count= videoConfigMapper.countList();
        if (count > 0) {
            List<VideoConfigParam> videoConfigList = videoConfigMapper.getPage(pageQuery);
            PageResult<VideoConfigParam> pageResult = new PageResult<>();
            pageResult.setData(videoConfigList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<VideoConfigParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(VideoConfig videoConfig) {
        if(videoConfigMapper.countByEnterprise(videoConfig.getEnterpriseId(),videoConfig.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同工作类别");
        }
        videoConfig.setOperateIp("124.214.124");
        videoConfig.setOperator("操作人");
        videoConfig.setOperateTime(new Date());
        videoConfigMapper.insertSelective(videoConfig);
    }

    @Override
    @Transactional
    public void update(VideoConfig videoConfig) {
        VideoConfig before = videoConfigMapper.selectByPrimaryKey(videoConfig.getId());
        if(before == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新工作类别不存在");
        }
        if(videoConfigMapper.countByEnterprise(videoConfig.getEnterpriseId(),videoConfig.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"存在相同工作类别");
        }
        videoConfig.setOperateIp("124.214.124");
        videoConfig.setOperator("操作人");
        videoConfig.setOperateTime(new Date());
        videoConfigMapper.updateByPrimaryKeySelective(videoConfig);
    }

    @Override
    public void delete(int id) {
        VideoConfig before = videoConfigMapper.selectByPrimaryKey(id);
        if(before == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新工作类别不存在");
        }
        videoConfigMapper.deleteByPrimaryKey(id);
    }
}
