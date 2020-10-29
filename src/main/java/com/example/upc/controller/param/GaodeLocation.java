package com.example.upc.controller.param;

import com.example.upc.controller.param.Geocodes;

import java.util.List;

/**
 * 输入地址返回解析结果的类
 *
 * @author cp
 *
 */
public class GaodeLocation {
    private String status;// 结果状态0,表示失败,1:表示成功
    private String count;// 返回结果的数目
    private String info;// 返回状态说明
    private List<Geocodes> geocodes;// 识别的结果列表

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Geocodes> getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(List<Geocodes> geocodes) {
        this.geocodes = geocodes;
    }

}