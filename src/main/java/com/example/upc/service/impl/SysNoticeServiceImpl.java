package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.SysNoticeSearchParam;
import com.example.upc.dao.SysNoticeMapper;
import com.example.upc.dataobject.SysNotice;
import com.example.upc.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/3/28 11:16
 */
@Service
public class SysNoticeServiceImpl implements SysNoticeService {
    @Autowired
    private SysNoticeMapper sysNoticeMapper;

    @Override
    public PageResult<SysNotice> getPage(PageQuery pageQuery) {
        int count= sysNoticeMapper.countList();
        if (count > 0) {
            List<SysNotice> sysNoticeList = sysNoticeMapper.getPage(pageQuery);
            PageResult<SysNotice> pageResult = new PageResult<>();
            pageResult.setData(sysNoticeList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SysNotice> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public List<SysNotice> getPage2 (SysNoticeSearchParam sysNoticeSearchParam){
        int count= sysNoticeMapper.countList();
        if (count > 0) {
            List<SysNotice> sysNoticeList = sysNoticeMapper.getPage2(sysNoticeSearchParam);
            return sysNoticeList;
        }
        List<SysNotice> pageResult = new ArrayList<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SysNotice sysNotice) {
           sysNotice.setOperator("操作人");
           sysNotice.setOperateIp("123.124.124");
           sysNotice.setOperateTime(new Date());
           sysNoticeMapper.insertSelective(sysNotice);
    }

    @Override
    @Transactional
    public void delete(int id) {
        SysNotice before = sysNoticeMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除的信息不存在，无法删除");
        }
        sysNoticeMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void check(int id) {
        SysNotice before = sysNoticeMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待审核的信息不存在");
        }
        if(before.getStatus()==0){
            sysNoticeMapper.changeStatus(id,1);
        }
    }

    @Override
    @Transactional
    public void update(SysNotice sysNotice) {
        SysNotice before = sysNoticeMapper.selectByPrimaryKey(sysNotice.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的信息不存在，无法更新");
        }
        sysNoticeMapper.updateByPrimaryKeySelective(sysNotice);
    }

    @Override
    public SysNotice getById(int id) {
        return sysNoticeMapper.selectByPrimaryKey(id);
    }
}
