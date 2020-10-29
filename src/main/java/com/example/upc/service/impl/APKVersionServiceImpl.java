package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.dao.APKVersionMapper;
import com.example.upc.dataobject.APKVersion;
import com.example.upc.service.APKVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class APKVersionServiceImpl implements APKVersionService {
    @Autowired
    APKVersionMapper apkVersionMapper;

    @Override
    public APKVersion selectTopOne(){
        return(apkVersionMapper.selectTopOne());
    }

    @Override
    public void insert(APKVersion apkVersion){
        if(checkVersionExist(apkVersion.getVersion(), apkVersion.getId())) {
            throw new BusinessException(EmBusinessError.DISHIES_All);
        }

        APKVersion apkVersion1 = new APKVersion();
        apkVersion1.setName(apkVersion.getName());
        apkVersion1.setVersion(apkVersion.getVersion());
        apkVersion1.setVersionName(apkVersion.getVersionName());
        apkVersion1.setVersionInformation(apkVersion.getVersionInformation());
        apkVersion1.setContent(apkVersion.getContent());
        apkVersion1.setNewAdd(apkVersion.getNewAdd());
        apkVersion1.setHotFix(apkVersion.getHotFix());
        apkVersion1.setOldDelete(apkVersion.getOldDelete());
        apkVersion1.setUpdateTime(apkVersion.getUpdateTime());
        apkVersion1.setOperator("操作人");
        apkVersion1.setOperatorIp("124.124.124");
        apkVersion1.setOperatorTime(new Date());

        // TODO: sendEmail

        apkVersionMapper.insertSelective(apkVersion1);
    }

    public boolean checkVersionExist(String version, Integer id) {
        return apkVersionMapper.countByVersion(version, id) > 0;
    }
}
