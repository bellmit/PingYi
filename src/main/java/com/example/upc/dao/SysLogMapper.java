package com.example.upc.dao;

import com.example.upc.dataobject.SysLog;
import com.example.upc.dataobject.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {

    int deleteByPrimaryKey(Integer id);
    int insert(SysLogWithBLOBs record);
    int insertSelective(SysLogWithBLOBs record);
    SysLogWithBLOBs selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysLogWithBLOBs record);
    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);
    int updateByPrimaryKey(SysLog record);
}