package com.example.upc.service;

import com.example.upc.controller.param.FoodAdditiveParam;
import com.example.upc.controller.searchParam.FoodAdditiveSearchParam;
import com.example.upc.dataobject.SysUser;

import java.io.IOException;
import java.util.List;

/**
 * @author 75186
 */
public interface FoodAdditiveService {
    /**
     * 按照使用日期查询食品添加剂
     * @param foodAdditiveSearchParam
     * @return
     */
    List<FoodAdditiveParam> selectByDate(FoodAdditiveSearchParam foodAdditiveSearchParam, SysUser sysUser);

    /**
     * 插入
     * @param foodAdditiveSearchParam
     */
    void insert(FoodAdditiveSearchParam foodAdditiveSearchParam, SysUser sysUser);

    /**
     * 更改
     * @param foodAdditiveSearchParam
     */
    void update(FoodAdditiveSearchParam foodAdditiveSearchParam,SysUser sysUser);

    /**
     * 删除
     * @param foodAdditiveSearchParam
     */
    void delete(FoodAdditiveSearchParam foodAdditiveSearchParam);

    Object standingFoodAdditive(FoodAdditiveSearchParam foodAdditiveSearchParam, SysUser sysUser) throws IOException;
}

