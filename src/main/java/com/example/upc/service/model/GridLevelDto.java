package com.example.upc.service.model;

import com.example.upc.dataobject.GridMapInfo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zcc
 * @date 2019/7/9 23:46
 */
public class GridLevelDto extends GridMapInfo {
    private List<GridLevelDto> childrenList = new ArrayList<>();
    public static GridLevelDto adapt(GridMapInfo gridMapInfo) {
        GridLevelDto dto = new GridLevelDto();
        BeanUtils.copyProperties(gridMapInfo, dto);
        return dto;
    }

    public List<GridLevelDto> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<GridLevelDto> childrenList) {
        this.childrenList = childrenList;
    }
}
