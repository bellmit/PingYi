package com.example.upc.dao;

import com.example.upc.controller.param.FormatDisinfectionSupParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.DisinfectionSearchParam;
import com.example.upc.dataobject.FormatDisinfection;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface FormatDisinfectionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table format_disinfection
     *
     * @mbg.generated Tue Sep 08 21:15:03 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table format_disinfection
     *
     * @mbg.generated Tue Sep 08 21:15:03 CST 2020
     */
    int insert(FormatDisinfection record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table format_disinfection
     *
     * @mbg.generated Tue Sep 08 21:15:03 CST 2020
     */
    int insertSelective(FormatDisinfection record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table format_disinfection
     *
     * @mbg.generated Tue Sep 08 21:15:03 CST 2020
     */
    FormatDisinfection selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table format_disinfection
     *
     * @mbg.generated Tue Sep 08 21:15:03 CST 2020
     */
    int updateByPrimaryKeySelective(FormatDisinfection record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table format_disinfection
     *
     * @mbg.generated Tue Sep 08 21:15:03 CST 2020
     */
    int updateByPrimaryKey(FormatDisinfection record);
    int countList();
    int countListSup(@Param("search") DisinfectionSearchParam search);
    List<FormatDisinfectionSupParam> getPage(@Param("page") PageQuery page, @Param("search") DisinfectionSearchParam search);
    int countListEnterprise(@Param("enterprise") Integer enterprise, @Param("search") DisinfectionSearchParam search);
    List<FormatDisinfection> getPageEnterprise(@Param("page") PageQuery page, @Param("enterprise") Integer enterprise, @Param("search") DisinfectionSearchParam search);

    int countListAdmin(@Param("search") DisinfectionSearchParam search);
    List<FormatDisinfectionSupParam> getPageAdmin(@Param("page") PageQuery page, @Param("search") DisinfectionSearchParam search);
    void batchInsertEx(@Param("formatDisinfectionList") List<FormatDisinfection> formatDisinfectionList);
    /**
     * ???????????????mapper
     */
    // ???????????????????????????id??????????????????
    List<FormatDisinfection> getDisinfectionRecord(@Param("enterpriseId") Integer enterpriseId, @Param("startDate") Date startDate, @Param("endDate")Date endDate);

}