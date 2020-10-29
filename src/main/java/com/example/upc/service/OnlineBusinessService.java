package com.example.upc.service;

import com.example.upc.controller.param.OnlineBusinessParm;
import com.example.upc.controller.searchParam.OnlineBusinessSearchParam;
import com.example.upc.dataobject.OnlineBusiness;

import java.util.Map;

public interface OnlineBusinessService {
    OnlineBusiness getMessageByEnterpriseId(OnlineBusinessSearchParam onlineBusinessSearchParam);

    void insertMessageByEnterpriseId(String json);
}
