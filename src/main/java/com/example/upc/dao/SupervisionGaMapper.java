package com.example.upc.dao;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.searchParam.ComplaintLeaderSearchParam;
import com.example.upc.controller.searchParam.GaSearchParam;
import com.example.upc.dataobject.SupervisionGa;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisionGaMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(SupervisionGa record);
    int insertSelective(SupervisionGa record);
    SupervisionGa selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(SupervisionGa record);
    int updateByPrimaryKey(SupervisionGa record);

    int countList(@Param("gaParam") GaSearchParam gaSearchParam);
    List<SupervisionGa> getPage(@Param("page") PageQuery page, @Param("gaParam") GaSearchParam gaSearchParam);
    int countListAllList(@Param("search") ComplaintLeaderSearchParam search);
    List<SupervisionGa> getPageAllList(@Param("page") PageQuery page,@Param("search") ComplaintLeaderSearchParam search);
    void changeStop(@Param("id")int id,@Param("isStop")int isStop);
    void changeDept(@Param("id")int id,@Param("checkDept")String checkDept);
    void batchInsert(@Param("supervisionGaList")List<SupervisionGa> supervisionGaList);
    int countAll();
    int countByDept(@Param("deptId")int deptId);
    void batchDelete(@Param("gaIds")List<Integer> gaIds);
    int countByTelephone(@Param("mobilePhone") String mobilePhone, @Param("id") Integer id);
    List<SupervisionGa> getAll();
    List<SupervisionGa> getGaByAreaForMap(int areaId);
}