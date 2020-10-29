package com.example.upc.service;

import com.example.upc.dataobject.InspectForceDecision;

/**
 * @author zcc
 * @date 2019/8/30 21:43
 */
public interface InspectForceDecisionService {
    InspectForceDecision getByParentId(int id);
    void insert(InspectForceDecision inspectForceDecision);
    void update(InspectForceDecision inspectForceDecision);
    void delete(int id);
}
