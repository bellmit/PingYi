package com.example.upc.service;

import com.example.upc.common.CommonReturnType;
import com.example.upc.controller.param.CommitteeCheckParam;
import com.example.upc.dataobject.CommitteeCheck;
import com.example.upc.dataobject.SysUser;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CommitteeCheckService {
    public void insert( CommitteeCheckParam committeeCheckParam, SysUser sysUser);
    public List<CommitteeCheck> getByDate( CommitteeCheckParam committeeCheckParam, SysUser sysUser);
    public CommitteeCheckParam getByCheckId( CommitteeCheckParam committeeCheckParam, SysUser sysUser);
    public void deleteByCheckId( CommitteeCheckParam committeeCheckParam, SysUser sysUser);
    public void updateSign(CommitteeCheck committeeCheck);
}
