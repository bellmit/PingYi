package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.UserSearchParam;
import com.example.upc.dataobject.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SysUser record);
    int insertSelective(SysUser record);
    SysUser selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SysUser record);
    int updateByPrimaryKey(SysUser record);

    int updateInforId(@Param("infoId") Integer infoId, @Param("id") Integer id);

    SysUser selectByLoginName(@Param("loginName") String loginName);

    int countByLoginName(@Param("loginName") String loginName, @Param("id") Integer id);

    List<SysUser> getPageByDeptId(@Param("deptId") int deptId, @Param("page") PageQuery page);

    int countByDeptId(@Param("deptId") int deptId);

    List<SysUser> getByIdList(@Param("idList") List<Integer> idList);

    List<SysUser> getAllPage(@Param("page") PageQuery page,@Param("userSearchParam") UserSearchParam userSearchParam);

    int countList(@Param("userSearchParam") UserSearchParam userSearchParam);

    void batchInsert(@Param("userList") List<SysUser> userList);

    int changePsd(@Param("id") int id,@Param("password") String password);

    int getEnterpriseIdByInfoId(@Param("infoId") int infoId);
}