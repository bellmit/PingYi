package com.example.upc.service.model;

import com.example.upc.dataobject.SysAcl;
import org.springframework.beans.BeanUtils;

/**
 * @author zcc
 * @date 2019/4/12 11:28
 */
public class AclDto extends SysAcl {
    // 是否要默认选中
    private boolean checked = false;

    // 是否有权限操作
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl, dto);
        return dto;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isHasAcl() {
        return hasAcl;
    }

    public void setHasAcl(boolean hasAcl) {
        this.hasAcl = hasAcl;
    }
}
