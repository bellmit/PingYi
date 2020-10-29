package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dataobject.FormatMeasurement;
import com.example.upc.dataobject.FormatQualityTime;

public interface FormatMeasurementService {
    PageResult getPage (PageQuery pageQuery, MeasurementSearchParam measurementSearchParam);
    void insert(FormatMeasurement formatMeasurement);
    void delete(int fsId);
    void update(FormatMeasurement formatMeasurement);
}
