package com.example.upc.service.impl;

import com.example.upc.controller.param.GridGrid1;
import com.example.upc.dao.GridGridMapper;
import com.example.upc.dataobject.GridGrid;
import com.example.upc.service.GridGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zcc
 * @date 2019/8/13 11:10
 */
@Service
public class GridGridServiceImpl implements GridGridService {
    @Autowired
    private GridGridMapper gridGridMapper;

    @Override
    public List<GridGrid1> getAll() {
        return gridGridMapper.getAll();
    }
    @Override
    public List<GridGrid1> getTop() {
        return gridGridMapper.getTop();
    }
    @Override
    public List<GridGrid1> getByParentId(int id) {
        return gridGridMapper.getByParentId(id);
    }
    @Override
    public int getParentId(int id){ return gridGridMapper.getParentId(id);}
    @Override
    public int insertSelective(GridGrid record){
        return gridGridMapper.insertSelective(record);
    };
    @Override
    public int updateByAreaId(GridGrid gridGrid){
        return gridGridMapper.updateByAreaId(gridGrid);
    }
}
