package com.example.upc.service.impl;

import com.example.upc.controller.param.CommitteCheckOptParam;
import com.example.upc.dao.CommitteCheckOptCategoryMapper;
import com.example.upc.dataobject.CommitteeCheck;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.CommitteeCheckOptCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommitteeCheckOptCategoryServiceImpl implements CommitteeCheckOptCategoryService {
    @Autowired
    CommitteCheckOptCategoryMapper committeCheckOptCategoryMapper;

    @Override
    public List<CommitteCheckOptParam> selectAllOpt(CommitteeCheck committeeCheck, SysUser sysUser){
        return committeCheckOptCategoryMapper.selectAllOpt(committeeCheck);
    }
}
