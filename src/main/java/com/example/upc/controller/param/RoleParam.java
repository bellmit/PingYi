package com.example.upc.controller.param;

/**
 * @author zcc
 * @date 2019/4/12 10:24
 */
public class RoleParam {
    private Integer id;
    private String name;
    private Integer type = 1;
    private Integer status;
    private Integer aclLevel;
    private String remark;

    public Integer getAclLevel() {
        return aclLevel;
    }

    public void setAclLevel(Integer aclLevel) {
        this.aclLevel = aclLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
