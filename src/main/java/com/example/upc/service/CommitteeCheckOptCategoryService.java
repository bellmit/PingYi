package com.example.upc.service;

import com.example.upc.controller.param.CommitteCheckOptParam;
import com.example.upc.dataobject.CommitteeCheck;
import com.example.upc.dataobject.SysUser;

import java.util.List;

public interface CommitteeCheckOptCategoryService {
    List<CommitteCheckOptParam> selectAllOpt(CommitteeCheck committeeCheck, SysUser sysUser);
}
