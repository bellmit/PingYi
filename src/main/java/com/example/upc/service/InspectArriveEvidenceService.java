package com.example.upc.service;

import com.example.upc.dataobject.InspectArriveEvidence;

/**
 * @author zcc
 * @date 2019/8/30 21:46
 */
public interface InspectArriveEvidenceService {
    InspectArriveEvidence getByParentId(int id);
    void insert(InspectArriveEvidence inspectArriveEvidence);
    void update(InspectArriveEvidence inspectArriveEvidence);
    void delete(int id);
}
