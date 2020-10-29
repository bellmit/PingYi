package com.example.upc.dao;

import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.OriginRecordExSearchParam;
import com.example.upc.controller.searchParam.WasteSearchParam;
import com.example.upc.dataobject.BillReport;
import com.example.upc.dataobject.FormatOriginRecordEx;
import com.example.upc.dataobject.FormatWaste;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface FormatOriginRecordExMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table format_origin_record_ex
     *
     * @mbg.generated Mon Sep 21 20:58:17 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table format_origin_record_ex
     *
     * @mbg.generated Mon Sep 21 20:58:17 CST 2020
     */
    int insert(FormatOriginRecordEx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table format_origin_record_ex
     *
     * @mbg.generated Mon Sep 21 20:58:17 CST 2020
     */
    int insertSelective(FormatOriginRecordEx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table format_origin_record_ex
     *
     * @mbg.generated Mon Sep 21 20:58:17 CST 2020
     */
    FormatOriginRecordEx selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table format_origin_record_ex
     *
     * @mbg.generated Mon Sep 21 20:58:17 CST 2020
     */
    int updateByPrimaryKeySelective(FormatOriginRecordEx record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table format_origin_record_ex
     *
     * @mbg.generated Mon Sep 21 20:58:17 CST 2020
     */
    int updateByPrimaryKey(FormatOriginRecordEx record);

    int countListSup(@Param("search") OriginRecordExSearchParam search);
    List<FormatOriginRecordExEnParam> getPage(@Param("page") PageQuery page, @Param("search") OriginRecordExSearchParam search);
    int countListAdmin(@Param("search") OriginRecordExSearchParam search);
    List<FormatOriginRecordExEnParam> getPageAdmin(@Param("page") PageQuery page, @Param("search") OriginRecordExSearchParam search);
    int countListEnterprise(@Param("enterprise") Integer enterprise, @Param("search") OriginRecordExSearchParam search);
    List<FormatOriginRecordExEnParam> getPageEnterprise(@Param("page") PageQuery page, @Param("enterprise") Integer enterprise, @Param("search") OriginRecordExSearchParam search);
    void batchInsertEx(@Param("formatOriginRecordExList") List<FormatOriginRecordEx> formatOriginRecordExList);
    List<FormatOriginRecordExEnParam> getRecordExByDate(@Param("enterpriseId") Integer enterpriseId, @Param("startDate") Date startDate, @Param("endDate")Date endDate);

    List<FormatOriginRecordExListParam> getRecordExPublicByDate(@Param("enterpriseId") Integer enterpriseId, @Param("startDate") Date startDate, @Param("endDate")Date endDate);

    List<FormatOriginRecordEx> getPageEnterprise2(@Param("enterprise")Integer infoId,@Param("search") OriginRecordExSearchParam originRecordExSearchParam);
}