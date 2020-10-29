package com.example.upc.service.impl;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.PreAndEdEnterpriseParam;
import com.example.upc.controller.param.PreAndEdParam;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.service.PreAndEdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreAndEdServiceImpl implements PreAndEdService {
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;

    @Override
    public PageResult getPagePreAndEd(PageQuery pageQuery) {
        int count=supervisionEnterpriseMapper.countListPreAndEd();
        if (count > 0) {
            List<PreAndEdParam> fpList = supervisionEnterpriseMapper.getPagePreAndEd(pageQuery);
            PageResult<PreAndEdParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<PreAndEdParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPagePre(PageQuery pageQuery,int dept) {
        int count=supervisionEnterpriseMapper.countListPre(dept);
        if (count > 0) {
            List<PreAndEdEnterpriseParam> fpList = supervisionEnterpriseMapper.getPagePre(pageQuery, dept);
            PageResult<PreAndEdEnterpriseParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<PreAndEdEnterpriseParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageEd(PageQuery pageQuery,int dept) {
        int count=supervisionEnterpriseMapper.countListEd(dept);
        if (count > 0) {
            List<PreAndEdEnterpriseParam> fpList = supervisionEnterpriseMapper.getPageEd(pageQuery, dept);
            PageResult<PreAndEdEnterpriseParam> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<PreAndEdEnterpriseParam> pageResult = new PageResult<>();
        return pageResult;
    }
}
