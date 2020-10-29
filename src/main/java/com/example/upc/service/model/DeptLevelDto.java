package com.example.upc.service.model;

import com.example.upc.dataobject.SysDept;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zcc
 * @date 2019/3/28 13:26
 */
public class DeptLevelDto extends SysDept {

    public List<DeptLevelDto> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<DeptLevelDto> childrenList) {
        this.childrenList = childrenList;
    }

    private List<DeptLevelDto> childrenList = new ArrayList<>();

    public static DeptLevelDto adapt(SysDept dept) {
        DeptLevelDto dto = new DeptLevelDto();
        BeanUtils.copyProperties(dept, dto);
        return dto;
    }
}
