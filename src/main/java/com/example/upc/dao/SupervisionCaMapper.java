package com.example.upc.dao;

import com.example.upc.controller.param.CaParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.SupervisionCaParam;
import com.example.upc.controller.searchParam.CaSearchParam;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dataobject.SupervisionCa;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionCaMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionCa record);
    int insertSelective(SupervisionCa record);

    SupervisionCa selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionCa record);
    int updateByPrimaryKey(SupervisionCa record);

    int countList(@Param("caParam") CaSearchParam CaSearchParam);
    List<SupervisionCa> getPageByEnterprise(@Param("page") PageQuery page,@Param("enterprise") Integer enterprise);
    int countListByEnterprise(@Param("enterprise") Integer enterprise);
    List<SupervisionCa> getPage(@Param("page") PageQuery page,@Param("caParam") CaSearchParam CaSearchParam);
    void batchInsert(@Param("supervisionCaList")List<SupervisionCa> supervisionCaList);
    int countListByTrain(@Param("caParam") CaSearchParam CaSearchParam,@Param("trainStatus") String trainStatus);
    int countTrainByEnterprise(@Param("id")Integer id,@Param("trainStatus") String trainStatus);
    void batchDelete(@Param("caIds")List<Integer> caIds);
    int countByIdNumber(@Param("idNumber") String idNumber, @Param("id") Integer id);
    List<SupervisionCa> getAll();
    List<SupervisionCa> getAllByEnterpriseId(@Param("enterpriseId") Integer id);
    List<SupervisionCaParam> getAllByEnterpriseId2(@Param("enterpriseId") Integer id);

    int countListNameByEnterpriseId(@Param("id") Integer id, @Param("search") MeasurementSearchParam search);
    List<SupervisionCa> getNameByEnterpriseId(@Param("page") PageQuery page, @Param("id") Integer id, @Param("search") MeasurementSearchParam search);

    SupervisionCa getCaInfoByUserId(@Param("userId")int id);

    SupervisionCa getCaInfoByIdNumber(@Param("idNumber")String idNumber);

    SupervisionCa getCaInfoByWeChatId(@Param("weChatId")String weChatId);
}