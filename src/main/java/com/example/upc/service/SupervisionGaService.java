package com.example.upc.service;

import com.example.upc.controller.param.GaParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.ComplaintLeaderSearchParam;
import com.example.upc.controller.searchParam.GaSearchParam;
import com.example.upc.dataobject.SupervisionGa;
import com.example.upc.dataobject.SysDept;
import com.example.upc.service.model.GaStatistics;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


public interface SupervisionGaService {
    //SupervisionGa selectByTelephone(String telephone) throws BusinessException;
    PageResult getPage (PageQuery pageQuery, GaSearchParam gaSearchParam);
    void insert(GaParam gaParam);
    void delete(int gaId);
    void update(GaParam gaParam);
    void changeDept(int id,String checkDept);
    void changeStop(int id);
    void importExcel(MultipartFile file, Integer type);
    int countList();
    SupervisionGa getById(int id);
    List<GaStatistics> getStatistics();
    GaStatistics getMyDeptInfo(SysDept sysDept);
    PageResult getPageAllList (PageQuery pageQuery, ComplaintLeaderSearchParam complaintLeaderSearchParam);
    List<SupervisionGa> getGaByAreaForMap(int areaId);
}
