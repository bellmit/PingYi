package com.example.upc.service;

import com.example.upc.controller.param.CaParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.SupervisionCaParam;
import com.example.upc.controller.searchParam.CaSearchParam;
import com.example.upc.controller.searchParam.EnterpriseSearchParam;
import com.example.upc.controller.searchParam.MeasurementSearchParam;
import com.example.upc.dataobject.SupervisionCa;
import com.example.upc.dataobject.SysIndustry;
import com.example.upc.util.miniProgram.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import com.example.upc.dataobject.SysUser;

public interface SupervisionCaService {
    PageResult<SupervisionCa> getPage (PageQuery pageQuery, CaSearchParam caSearchParam);
    void insert(CaParam caParam);
    void delete(int caId);
    void update(CaParam caParam);
    void importExcel(MultipartFile file, Integer type);
    Map<String,Integer> getStatistics(CaSearchParam caSearchParam, EnterpriseSearchParam enterpriseSearchParam);
    Map<String,Integer> getEnStatistics(Integer id);
    PageResult<SupervisionCa> getListByEnterpriseId (PageQuery pageQuery, SysUser sysUser);
    PageResult<SupervisionCa> getCaPageByEnterprise (PageQuery pageQuery, int id);
    List<SupervisionCa> getAllByEnterpriseId(Integer id);
    PageResult<SupervisionCa> getNameByEnterpriseId (PageQuery pageQuery, SysUser sysUser, MeasurementSearchParam measurementSearchParam);
    ResultVo getAllByEnterpriseId2(Integer id);
    public SupervisionCa getCaInfo(int id);

    public SupervisionCa getCaInfoByIdNumber(String idNumber) throws ParseException;
    SupervisionCa selectByPrimaryKey(Integer id);
}
