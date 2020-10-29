package com.example.upc.controller.searchParam;

/**
 * @author zcc
 * @date 2019/9/9 17:47
 */
public class UserSearchParam {
    private String LoginName;
    private String username;
    private Integer userType;

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
