package com.example.upc.service.model;

import com.example.upc.dataobject.SysArea;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zcc
 * @date 2019/7/9 20:05
 */
public class AreaLevelDto extends SysArea {
    private List<AreaLevelDto> childrenList = new ArrayList<>();

    public static AreaLevelDto adapt(SysArea area) {
        AreaLevelDto dto = new AreaLevelDto();
        BeanUtils.copyProperties(area, dto);
        return dto;
    }

    public List<AreaLevelDto> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<AreaLevelDto> childrenList) {
        this.childrenList = childrenList;
    }
}
