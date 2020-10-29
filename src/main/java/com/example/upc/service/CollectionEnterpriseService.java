package com.example.upc.service;


import com.example.upc.controller.param.CollectionEnterpriseParam;
import com.example.upc.controller.param.NearEnterprise;
import com.example.upc.dataobject.CollectionEnterprise;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CollectionEnterpriseService {
    public void insert(CollectionEnterpriseParam collectionEnterpriseParam) throws InvocationTargetException, IllegalAccessException;
    public void delete(CollectionEnterpriseParam collectionEnterpriseParam);
    public List<NearEnterprise> selectByWeChatId(CollectionEnterpriseParam collectionEnterpriseParam);
}
