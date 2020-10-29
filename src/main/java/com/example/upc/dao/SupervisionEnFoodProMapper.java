package com.example.upc.dao;

import com.example.upc.dataobject.SupervisionEnFoodPro;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionEnFoodProMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table supervision_en_food_pro
     *
     * @mbg.generated Fri Apr 24 16:30:08 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table supervision_en_food_pro
     *
     * @mbg.generated Fri Apr 24 16:30:08 CST 2020
     */
    int insert(SupervisionEnFoodPro record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table supervision_en_food_pro
     *
     * @mbg.generated Fri Apr 24 16:30:08 CST 2020
     */
    int insertSelective(SupervisionEnFoodPro record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table supervision_en_food_pro
     *
     * @mbg.generated Fri Apr 24 16:30:08 CST 2020
     */
    SupervisionEnFoodPro selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table supervision_en_food_pro
     *
     * @mbg.generated Fri Apr 24 16:30:08 CST 2020
     */
    int updateByPrimaryKeySelective(SupervisionEnFoodPro record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table supervision_en_food_pro
     *
     * @mbg.generated Fri Apr 24 16:30:08 CST 2020
     */
    int updateByPrimaryKey(SupervisionEnFoodPro record);

    List<SupervisionEnFoodPro> getListByEnterpriseId(@Param("enterpriseId") int eid);
    void deleteByIndexId(@Param("iid") int iid);

    List<SupervisionEnFoodPro> getAll();
}