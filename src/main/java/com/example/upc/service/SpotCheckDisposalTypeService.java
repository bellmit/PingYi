package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dataobject.SpotCheckDisposalType;

public interface SpotCheckDisposalTypeService {
    PageResult getPage (PageQuery pageQuery);
    void insert(SpotCheckDisposalType spotCheckDisposalType);
    void delete(int fpId);
    void update(SpotCheckDisposalType spotCheckDisposalType);
}
