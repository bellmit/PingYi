package com.example.upc.dao;

import com.example.upc.dataobject.ViewSpotCheckStepResult;

import java.util.List;

public interface ViewSpotCheckStepResultMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table view_spot_check_step_result
     *
     * @mbg.generated Fri Aug 09 10:21:30 CST 2019
     */
    int insert(ViewSpotCheckStepResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table view_spot_check_step_result
     *
     * @mbg.generated Fri Aug 09 10:21:30 CST 2019
     */
    int insertSelective(ViewSpotCheckStepResult record);
    List<ViewSpotCheckStepResult> getListAll();
}