package com.example.upc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.EnterpriseSearchParam;
import com.example.upc.dao.*;
import com.example.upc.dataobject.*;
import com.example.upc.service.*;
import com.example.upc.util.*;
import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.upc.util.JsonToImageUrl.JSON2ImageUrl;


/**
 * @author zcc
 * @date 2019/4/25 11:01
 */
@Service
public class SupervisionEnterpriseServiceImpl implements SupervisionEnterpriseService {


    @Autowired
    private SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    private SupervisionEnMedicalProMapper supervisionEnMedicalProMapper;
    @Autowired
    private SupervisionEnMedicalProIndexMapper supervisionEnMedicalProIndexMapper;
    @Autowired
    private SupervisionEnMedicalBuMapper supervisionEnMedicalBuMapper;
    @Autowired
    private SupervisionEnMedicalBuIndexMapper supervisionEnMedicalBuIndexMapper;
    @Autowired
    private SupervisionEnFoodProMapper supervisionEnFoodProMapper;
    @Autowired
    private SupervisionEnFoodProIndexMapper supervisionEnFoodProIndexMapper;
    @Autowired
    private SupervisionEnDrugsBuMapper supervisionEnDrugsBuMapper;
    @Autowired
    private SupervisionEnDrugsBuIndexMapper supervisionEnDrugsBuIndexMapper;
    @Autowired
    private SupervisionEnDrugsProMapper supervisionEnDrugsProMapper;
    @Autowired
    private SupervisionEnDrugsProIndexMapper supervisionEnDrugsProIndexMapper;
    @Autowired
    private SupervisionEnCosmeticsMapper supervisionEnCosmeticsMapper;
    @Autowired
    private SupervisionEnCosmeticsIndexMapper supervisionEnCosmeticsIndexMapper;
    @Autowired
    private SupervisionEnFoodBuMapper supervisionEnFoodBuMapper;
    @Autowired
    private SupervisionEnFoodBuIndexMapper supervisionEnFoodBuIndexMapper;
    @Autowired
    private SupervisionEnProCategoryMapper supervisionEnProCategoryMapper;
    @Autowired
    private SupervisionEnSmallCaterMapper supervisionEnSmallCaterMapper;
    @Autowired
    private SupervisionEnSmallCaterIndexMapper supervisionEnSmallCaterIndexMapper;
    @Autowired
    private SupervisionEnSmallWorkshopMapper supervisionEnSmallWorkshopMapper;
    @Autowired
    private SupervisionEnSmallWorkshopIndexMapper supervisionEnSmallWorkshopIndexMapper;
    @Autowired
    private SupervisionEnIndustrialProductsMapper supervisionEnIndustrialProductsMapper;
    @Autowired
    private SupervisionEnIndustrialProductsIndexMapper supervisionEnIndustrialProductsIndexMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SupervisionGaService supervisionGaService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private SysIndustryService sysIndustryService;
    @Autowired
    private SysDeptIndustryService sysDeptIndustryService;
    @Autowired
    private SysDeptAreaService sysDeptAreaService;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private GridPointsGpsMapper gridPointsGpsMapper;
    @Autowired
    private GridPointsMapper gridPointsMapper;
    @Autowired
    private SupervisionEnterpriseDocumentMapper supervisionEnterpriseDocumentMapper;

    @Override
    public SmilePointsParam getSmileMapPoints(EnterpriseSearchParam enterpriseSearchParam){
        CaculateDisUtil caculateDisUtil = new CaculateDisUtil();
        if (enterpriseSearchParam.getLocation() == null||enterpriseSearchParam.getLocation().equals("")){
            enterpriseSearchParam.setLocation("118.5821878900,37.4489563700");
        }
        String[] gps = enterpriseSearchParam.getLocation().split(",");
        Double gpsA =Double.parseDouble(gps[0]);
        Double gpsB =Double.parseDouble(gps[1]);
        List<SmilePoints> SmilePointsList = supervisionEnterpriseMapper.getListPhone(enterpriseSearchParam);
        for(SmilePoints smilePoints:SmilePointsList){
            String[] gpsTarget = smilePoints.getPoint().split(",");
            Double gpsC =Double.parseDouble(gpsTarget[0]);
            Double gpsD =Double.parseDouble(gpsTarget[1]);
            smilePoints.setDistance((int) caculateDisUtil.Distance(gpsA, gpsB, gpsC, gpsD));
        }
        List<SmilePoints> afterSmilePointsPhoneList  = ListSortUtil.sort(SmilePointsList,"distance",null);
        Integer a = afterSmilePointsPhoneList.size();
        afterSmilePointsPhoneList = ListSubUtil.sub(afterSmilePointsPhoneList,enterpriseSearchParam.getIndexNum());
        SmilePointsParam smilePointsParam = new SmilePointsParam();
        smilePointsParam.setSmilePointsList(afterSmilePointsPhoneList);
        smilePointsParam.setTotal(a);
        return smilePointsParam;
    }

    @Override
    public Map<String,Integer> getCountPhone(EnterpriseSearchParam enterpriseSearchParam,SysUser sysUser,Integer areaId,boolean searchIndustry){
        if (sysUser.getUserType()==0){
            if(areaId==null){
                enterpriseSearchParam.setAreaList(sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            }else {
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            if(searchIndustry){
                enterpriseSearchParam.setIndustryList(sysIndustryService.getAll().stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
        }else if(sysUser.getUserType()==2){
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            if(areaId==null){
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListByDeptId(supervisionGa.getDepartment()));
            }else {
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            if(searchIndustry){
                enterpriseSearchParam.setIndustryList(sysDeptIndustryService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
            SysDept sysDept = sysDeptService.getById(supervisionGa.getDepartment());
            if(sysDept.getType()==2){
                if(supervisionGa.getType()!=2){
                    enterpriseSearchParam.setSupervisor(supervisionGa.getName());
                }
            }
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????");
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("??????",supervisionEnterpriseMapper.countListPhone(enterpriseSearchParam));
        enterpriseSearchParam.setOperationMode("??????");
        map.put("??????",supervisionEnterpriseMapper.countListPhone(enterpriseSearchParam));
        enterpriseSearchParam.setOperationMode("??????");
        map.put("??????",supervisionEnterpriseMapper.countListPhone(enterpriseSearchParam));
        enterpriseSearchParam.setOperationMode("?????????");
        map.put("?????????",supervisionEnterpriseMapper.countListPhone(enterpriseSearchParam));
        enterpriseSearchParam.setOperationMode("??????");
        map.put("??????",supervisionEnterpriseMapper.countListPhone(enterpriseSearchParam));
        return map;
    }

    @Override
    public EnterpriseCountParam getCount(EnterpriseSearchParam enterpriseSearchParam,SysUser sysUser,Integer areaId,boolean searchIndustry){
        if (sysUser.getUserType()==0){
            if(areaId==null){
                enterpriseSearchParam.setAreaList(sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            }else {
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            if(searchIndustry){
                enterpriseSearchParam.setIndustryList(sysIndustryService.getAll().stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
        }else if(sysUser.getUserType()==2){
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            if(areaId==null){
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListByDeptId(supervisionGa.getDepartment()));
            }else {
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            if(searchIndustry){
                enterpriseSearchParam.setIndustryList(sysDeptIndustryService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
            SysDept sysDept = sysDeptService.getById(supervisionGa.getDepartment());
            if(sysDept.getType()==2){
                if(supervisionGa.getType()!=2){
                    enterpriseSearchParam.setSupervisor(supervisionGa.getName());
                }
            }
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????");
        }
        EnterpriseCountParam enterpriseCountParam = new EnterpriseCountParam();
            enterpriseSearchParam.setOperationMode("??????");
            enterpriseCountParam.setEnterprise(supervisionEnterpriseMapper.countListPC(enterpriseSearchParam));
            enterpriseSearchParam.setOperationMode("??????");
            enterpriseCountParam.setIndividual(supervisionEnterpriseMapper.countListPC(enterpriseSearchParam));
            enterpriseSearchParam.setOperationMode("?????????");
            enterpriseCountParam.setCooperation(supervisionEnterpriseMapper.countListPC(enterpriseSearchParam));
            enterpriseSearchParam.setOperationMode("??????");
            enterpriseCountParam.setOthers(supervisionEnterpriseMapper.countListPC(enterpriseSearchParam));

        if (enterpriseSearchParam.getIndexNum()==1) {
            List<String> industryList1 = new ArrayList<>();
            industryList1.clear();
            industryList1.add("foodBusiness");
            enterpriseSearchParam.setIndustryList(industryList1);
            enterpriseCountParam.setFoodBu(supervisionEnterpriseMapper.countListPCHave(enterpriseSearchParam));
            industryList1.clear();
            industryList1.add("smallCater");
            enterpriseSearchParam.setIndustryList(industryList1);
            enterpriseCountParam.setSmallCater(supervisionEnterpriseMapper.countListPCHave(enterpriseSearchParam));
            industryList1.clear();
            industryList1.add("smallWorkshop");
            enterpriseSearchParam.setIndustryList(industryList1);
            enterpriseCountParam.setSmallWorkshop(supervisionEnterpriseMapper.countListPCHave(enterpriseSearchParam));
            industryList1.clear();
            industryList1.add("foodProduce");
            enterpriseSearchParam.setIndustryList(industryList1);
            enterpriseCountParam.setFoodPro(supervisionEnterpriseMapper.countListPCHave(enterpriseSearchParam));
            industryList1.clear();
            industryList1.add("drugsBusiness");
            enterpriseSearchParam.setIndustryList(industryList1);
            enterpriseCountParam.setDrugsBu(supervisionEnterpriseMapper.countListPCHave(enterpriseSearchParam));
            industryList1.clear();
            industryList1.add("drugsProduce");
            enterpriseSearchParam.setIndustryList(industryList1);
            enterpriseCountParam.setDrugsPro(supervisionEnterpriseMapper.countListPCHave(enterpriseSearchParam));
            industryList1.clear();
            industryList1.add("cosmeticsUse");
            enterpriseSearchParam.setIndustryList(industryList1);
            enterpriseCountParam.setCosmeticsUse(supervisionEnterpriseMapper.countListPCHave(enterpriseSearchParam));
            industryList1.clear();
            industryList1.add("medicalProduce");
            enterpriseSearchParam.setIndustryList(industryList1);
            enterpriseCountParam.setMedicalPro(supervisionEnterpriseMapper.countListPCHave(enterpriseSearchParam));
            industryList1.clear();
            industryList1.add("medicalBusiness");
            enterpriseSearchParam.setIndustryList(industryList1);
            enterpriseCountParam.setMedicalBu(supervisionEnterpriseMapper.countListPCHave(enterpriseSearchParam));
            industryList1.clear();
            industryList1.add("industrialProducts");
            enterpriseSearchParam.setIndustryList(industryList1);
            enterpriseCountParam.setIndustrialProducts(supervisionEnterpriseMapper.countListPCHave(enterpriseSearchParam));
            enterpriseCountParam.setNone(supervisionEnterpriseMapper.countListPCNone(enterpriseSearchParam));
        }
        return enterpriseCountParam;
    }



    @Override
    public PageResult<EnterpriseListResult> getPage(PageQuery pageQuery, EnterpriseSearchParam enterpriseSearchParam,SysUser sysUser,Integer areaId,boolean searchIndustry) {
        if (sysUser.getUserType()==0){
            if(areaId==null){
                enterpriseSearchParam.setAreaList(sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            }else {
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            if(searchIndustry){
                enterpriseSearchParam.setIndustryList(sysIndustryService.getAll().stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
            enterpriseSearchParam.setUserType("admin");
        }else if(sysUser.getUserType()==2){
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            if(areaId==null){
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListByDeptId(supervisionGa.getDepartment()));
            }else {
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            if(searchIndustry){
                enterpriseSearchParam.setIndustryList(sysDeptIndustryService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
            SysDept sysDept = sysDeptService.getById(supervisionGa.getDepartment());
            if(sysDept.getType()==2){
                if(supervisionGa.getType()!=2){
                    enterpriseSearchParam.setSupervisor(supervisionGa.getName());
                }
            }
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????");
        }
        int count= supervisionEnterpriseMapper.countList(enterpriseSearchParam);
        if (count > 0) {
            List<EnterpriseListResult> enterpriseList = supervisionEnterpriseMapper.getPage(pageQuery,enterpriseSearchParam);
            PageResult<EnterpriseListResult> pageResult = new PageResult<>();
            pageResult.setData(enterpriseList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<EnterpriseListResult> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<EnterpriseListResult> getPageState(PageQuery pageQuery, EnterpriseSearchParam enterpriseSearchParam,SysUser sysUser,Integer areaId,boolean searchIndustry) {
        if (sysUser.getUserType()==0){
            if(areaId==null){
                enterpriseSearchParam.setAreaList(sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
            }else {
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            if(searchIndustry){
                enterpriseSearchParam.setIndustryList(sysIndustryService.getAll().stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
            enterpriseSearchParam.setUserType("admin");
        }else if(sysUser.getUserType()==2){
            SupervisionGa supervisionGa = supervisionGaService.getById(sysUser.getInfoId());
            if(areaId==null){
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListByDeptId(supervisionGa.getDepartment()));
            }else {
                enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
            }
            if(searchIndustry){
                enterpriseSearchParam.setIndustryList(sysDeptIndustryService.getListByDeptId(supervisionGa.getDepartment()).stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
            }
            SysDept sysDept = sysDeptService.getById(supervisionGa.getDepartment());
            if(sysDept.getType()==2){
                if(supervisionGa.getType()!=2){
                    enterpriseSearchParam.setSupervisor(supervisionGa.getName());
                }
            }
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????");
        }
        int count= supervisionEnterpriseMapper.countListState(enterpriseSearchParam);
        if (count > 0) {
            List<EnterpriseListResult> enterpriseList = supervisionEnterpriseMapper.getPageState(pageQuery,enterpriseSearchParam);
            PageResult<EnterpriseListResult> pageResult = new PageResult<>();
            pageResult.setData(enterpriseList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<EnterpriseListResult> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<EnterpriseListResult> getPageNoUser(PageQuery pageQuery, EnterpriseSearchParam enterpriseSearchParam, Integer areaId, boolean searchIndustry) {
        if(areaId==null){
            enterpriseSearchParam.setAreaList(sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList()));
        }else {
            enterpriseSearchParam.setAreaList(sysDeptAreaService.getIdListSearch(areaId));
        }
        if(searchIndustry){
            enterpriseSearchParam.setIndustryList(sysIndustryService.getAll().stream().map((sysIndustry -> sysIndustry.getRemark())).collect(Collectors.toList()));
        }
        int count= supervisionEnterpriseMapper.countList(enterpriseSearchParam);
        if (count > 0) {
            List<EnterpriseListResult> enterpriseList = supervisionEnterpriseMapper.getPage(pageQuery,enterpriseSearchParam);
            PageResult<EnterpriseListResult> pageResult = new PageResult<>();
            pageResult.setData(enterpriseList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<EnterpriseListResult> pageResult = new PageResult<>();
        return pageResult;
    }

//????????????????????????????????????????????????????????????????????????????????????????????????????????????
    @Override
    public EnterpriseParam getById(int id) {
        SupervisionEnterprise supervisionEnterprise= supervisionEnterpriseMapper.selectByPrimaryKey(id);
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"??????????????????");
        }
        EnterpriseParam enterpriseParam = new EnterpriseParam();
        BeanUtils.copyProperties(supervisionEnterprise,enterpriseParam);
        enterpriseParam.setPermissionFamily(supervisionEnterprise.getPermissionType());
        GridPoints gridPoints = gridPointsMapper.getPointByEnterpriseId(id);
        if (gridPoints==null){
            enterpriseParam.setPosition("");
        }else{
            enterpriseParam.setPosition(gridPoints.getPoint());
        }

        if (supervisionEnterprise.getGpsFlag()==0){
                enterpriseParam.setLocation("");
        }
        if (supervisionEnterprise.getGpsFlag()==1){
            GridPointsGps gridPointsGps = gridPointsGpsMapper.getPointByCodeId(supervisionEnterprise.getIdNumber());
            if (gridPointsGps==null){
                enterpriseParam.setLocation("");
            }
            else {
                enterpriseParam.setLocation(gridPointsGps.getPoint());
            }
        }
        List<String> permissionType = new ArrayList<>();
        //yes  ????????????
        List<SupervisionEnFoodBu> supervisionEnFoodBuList = supervisionEnFoodBuMapper.getListByEnterpriseId(id);
        if(supervisionEnFoodBuList.size()>0){
            permissionType.add("foodBusiness");
            enterpriseParam.setFoodBusinessList(supervisionEnFoodBuList);
        }
        //yes ????????????
        List<SupervisionEnFoodPro> supervisionEnFoodProList = supervisionEnFoodProMapper.getListByEnterpriseId(id);
        if (supervisionEnFoodProList.size()>0) {
            permissionType.add("foodProduce");
            List<EnFoodProduceParam> enFoodProduceParamList = new ArrayList<>();//??????list?????????
            for (SupervisionEnFoodPro supervisionEnFoodPro : supervisionEnFoodProList){
                EnFoodProduceParam enFoodProduceParam = new EnFoodProduceParam();
                BeanUtils.copyProperties(supervisionEnFoodPro,enFoodProduceParam);
                List<SupervisionEnProCategory> list = supervisionEnProCategoryMapper.selectByParentId(supervisionEnFoodPro.getId());
                enFoodProduceParam.setList(list);
                enFoodProduceParamList.add(enFoodProduceParam);
            }
            enterpriseParam.setFoodProduceList(enFoodProduceParamList);
        }
        //yes ????????????
        List<SupervisionEnDrugsBu> supervisionEnDrugsBuList = supervisionEnDrugsBuMapper.getListByEnterpriseId(id);
        if(supervisionEnDrugsBuList.size()>0){
            permissionType.add("drugsBusiness");
            enterpriseParam.setDrugsBusinessList(supervisionEnDrugsBuList);
        }
        //yes ????????????
        List<SupervisionEnDrugsPro> supervisionEnDrugsProList = supervisionEnDrugsProMapper.getListByEnterpriseId(id);
        if(supervisionEnDrugsProList.size()>0){
            permissionType.add("drugsProduce");
            enterpriseParam.setDrugsProduceList(supervisionEnDrugsProList);
        }
        //yes,?????????
        List<SupervisionEnCosmetics> supervisionEnCosmeticsList = supervisionEnCosmeticsMapper.getListByEnterpriseId(id);
        if(supervisionEnCosmeticsList.size()>0){
            permissionType.add("cosmeticsUse");
            enterpriseParam.setCosmeticsList(supervisionEnCosmeticsList);
        }
        //yes ??????????????????
        List<SupervisionEnMedicalPro> supervisionEnMedicalProList = supervisionEnMedicalProMapper.getListByEnterpriseId(id);
        if(supervisionEnMedicalProList.size()>0){
            permissionType.add("medicalProduce");
            enterpriseParam.setMedicalProduceList(supervisionEnMedicalProList);
        }
        //yes ??????????????????
        List<SupervisionEnMedicalBu> supervisionEnMedicalBuList = supervisionEnMedicalBuMapper.getListByEnterpriseId(id);
        if(supervisionEnMedicalBuList.size()>0){
            permissionType.add("medicalBusiness");
            enterpriseParam.setMedicalBusinessList(supervisionEnMedicalBuList);
        }
        //yes ?????????
        List<SupervisionEnSmallCater> supervisionEnSmallCaterList = supervisionEnSmallCaterMapper.getListByEnterpriseId(id);
        if(supervisionEnSmallCaterList.size()>0){
            permissionType.add("smallCater");
            enterpriseParam.setSmallCaterList(supervisionEnSmallCaterList);
        }
        //yes ?????????
        List<SupervisionEnSmallWorkshop> supervisionEnSmallWorkshopList = supervisionEnSmallWorkshopMapper.getListByEnterpriseId(id);
        if(supervisionEnSmallWorkshopList.size()>0){
            permissionType.add("smallWorkshop");
            enterpriseParam.setSmallWorkshopList(supervisionEnSmallWorkshopList);
        }
        //yes ????????????
        List<SupervisionEnIndustrialProducts> supervisionEnIndustrialProductsList = supervisionEnIndustrialProductsMapper.getListByEnterpriseId(id);
        if(supervisionEnIndustrialProductsList.size()>0){
            enterpriseParam.setIndustrialProductsList(supervisionEnIndustrialProductsList);
        }

        List<SupervisionEnterpriseDocument> list = supervisionEnterpriseDocumentMapper.selectByEnterpriseId(id);
        if (list.size()>0) {
            for (SupervisionEnterpriseDocument supervisionEnterpriseDocument : list) {
                if (supervisionEnterpriseDocument.getFlag() == 1) {
                    enterpriseParam.setBusinessLicensePhoto(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 2) {
                    enterpriseParam.setFoodBusinessPhotos(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 3) {
                    enterpriseParam.setSmallCaterPhotos(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 4) {
                    enterpriseParam.setSmallWorkshopPhotos(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 5) {
                    enterpriseParam.setFoodProducePhotos(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 6) {
                    enterpriseParam.setDrugsBusinessPhotos(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 7) {
                    enterpriseParam.setDrugsProducePhotos(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 8) {
                    enterpriseParam.setCosmeticsUsePhotos(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 9) {
                    enterpriseParam.setMedicalProducePhotos(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 10) {
                    enterpriseParam.setMedicalBusinessPhotos(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 11) {
                    enterpriseParam.setIndustrialProductsPhotos(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 12) {
                    enterpriseParam.setPublicityPhotos(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 13) {
                    enterpriseParam.setCertificatePhotos(supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 14) {
                    enterpriseParam.setOtherPhotos(supervisionEnterpriseDocument.getDocument());
                }
            }
        }
        return enterpriseParam;
    }

    @Override
    public SupervisionEnterprise selectById(int id) {
        return supervisionEnterpriseMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void insert(String json, SysUser sysUser) {
        SupervisionEnterprise supervisionEnterprise = JSONObject.parseObject(json,SupervisionEnterprise.class);
        EnterpriseParam enterpriseParam = JSON.parseObject(json,EnterpriseParam.class);
        supervisionEnterprise.setPermissionType(enterpriseParam.getPermissionFamily());
        ValidationResult result = validator.validate(supervisionEnterprise);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(supervisionEnterpriseMapper.countByIdNumber(supervisionEnterprise.getIdNumber(),supervisionEnterprise.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"???????????????????????????");
        }
        supervisionEnterprise.setOperateIp("124.124.124");
        supervisionEnterprise.setOperateTime(new Date());
        supervisionEnterprise.setOperator("zcc");
        if(supervisionEnterprise.getGpsFlag()==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"?????????????????????");
        }
        if (supervisionEnterprise.getGpsFlag()==1){
            JSONObject jsonResult = JSON.parseObject(json);
            String location = jsonResult.getString("location");
            if(location==null){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????????????????");
            }
            GridPointsGps gridPointsGps = gridPointsGpsMapper.getPointByCodeId(supervisionEnterprise.getIdNumber());
            if (gridPointsGps != null){
                gridPointsGps.setPoint(location);
                gridPointsGps.setOperator(sysUser.getUsername());
                gridPointsGps.setOperatorIp("1.1.1.1");
                gridPointsGpsMapper.updateByPrimaryKeySelective(gridPointsGps);
            }
            else {
                GridPointsGps gridPointsGps1 = new GridPointsGps();
                gridPointsGps1.setCodeId(supervisionEnterprise.getIdNumber());
                gridPointsGps1.setAreaId(supervisionEnterprise.getArea());
                gridPointsGps1.setPoint(location);
                gridPointsGps1.setOperator(sysUser.getUsername());
                gridPointsGps1.setOperatorIp("1.1.1.1");
                gridPointsGpsMapper.insertSelective(gridPointsGps1);
            }
        }
        supervisionEnterpriseMapper.insertSelective(supervisionEnterprise);
        if(supervisionEnterprise.getId()==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????");
        }
        SysUser sysUser1 = new SysUser();//???????????????????????????
        String encryptedPassword = MD5Util.md5("123456+");
        sysUser1.setUsername(supervisionEnterprise.getEnterpriseName());
        sysUser1.setLoginName(supervisionEnterprise.getIdNumber());
        sysUser1.setPassword(encryptedPassword);
        sysUser1.setUserType(1);
        sysUser1.setInfoName(supervisionEnterprise.getEnterpriseName());
        sysUser1.setInfoId(supervisionEnterprise.getId());
        sysUser1.setStatus(0);
        sysUser1.setOperator("?????????");
        sysUser1.setOperateIp("124.124.124");
        sysUser1.setOperateTime(new Date());
        sysUserMapper.insertSelective(sysUser1);
        if(enterpriseParam.getPermissionFamily()!=null||enterpriseParam.getPermissionFamily()!=""){
        insertEnterpriseChildrenList(supervisionEnterprise,enterpriseParam);
        }
        //???????????????????????????????????????????????????
        insertEnterpriseDocumentList(supervisionEnterprise,enterpriseParam);
    }

    @Override
    @Transactional
    public void update(String json, SysUser sysUser) {
        SupervisionEnterprise supervisionEnterprise = JSONObject.parseObject(json,SupervisionEnterprise.class);
        EnterpriseParam enterpriseParam = JSON.parseObject(json,EnterpriseParam.class);
        supervisionEnterprise.setPermissionType(enterpriseParam.getPermissionFamily());
        ValidationResult result = validator.validate(supervisionEnterprise);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        if(supervisionEnterpriseMapper.countByIdNumber(supervisionEnterprise.getIdNumber(),supervisionEnterprise.getId())>0){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"???????????????????????????");
        }
        SupervisionEnterprise before = supervisionEnterpriseMapper.selectByPrimaryKey(supervisionEnterprise.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????????????????");
        }
        supervisionEnterprise.setBusinessState(2);
        supervisionEnterprise.setOperateIp("124.124.124");
        supervisionEnterprise.setOperateTime(new Date());
        supervisionEnterprise.setOperator("zcc");
        if (supervisionEnterprise.getGpsFlag()==1){
            JSONObject jsonResult = JSON.parseObject(json);
            String location = jsonResult.getString("location");
            if(location==null){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????????????????");
            }
            GridPointsGps gridPointsGps = gridPointsGpsMapper.getPointByCodeId(supervisionEnterprise.getIdNumber());
            if (gridPointsGps != null){
                gridPointsGps.setPoint(location);
                gridPointsGps.setOperator(sysUser.getUsername());
                gridPointsGps.setOperatorIp("1.1.1.1");
                gridPointsGpsMapper.updateByPrimaryKeySelective(gridPointsGps);
            }
            else {
                GridPointsGps gridPointsGps1 = new GridPointsGps();
                gridPointsGps1.setCodeId(supervisionEnterprise.getIdNumber());
                gridPointsGps1.setAreaId(supervisionEnterprise.getArea());
                gridPointsGps1.setPoint(location);
                gridPointsGps1.setOperator(sysUser.getUsername());
                gridPointsGps1.setOperatorIp("1.1.1.1");
                gridPointsGpsMapper.insertSelective(gridPointsGps1);
            }
        }
        insertEnterpriseChildrenList(supervisionEnterprise,enterpriseParam);
        insertEnterpriseDocumentList(supervisionEnterprise,enterpriseParam);
        supervisionEnterpriseMapper.updateByPrimaryKeySelectiveEx(supervisionEnterprise);
    }

    //??????????????????????????????????????????????????????
    void insertEnterpriseChildrenList(SupervisionEnterprise supervisionEnterprise,EnterpriseParam enterpriseParam){

        if(!enterpriseParam.getPermissionFamily().contains("foodBusiness")){
            SupervisionEnFoodBuIndex recordEFB = supervisionEnFoodBuIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            if (recordEFB!=null){
                supervisionEnFoodBuIndexMapper.deleteByPrimaryKey(recordEFB.getId());
                supervisionEnFoodBuMapper.deleteByIndexId(recordEFB.getId());
            }
        }
        if(!enterpriseParam.getPermissionFamily().contains("foodProduce")){
            SupervisionEnFoodProIndex recordEFP = supervisionEnFoodProIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            if (recordEFP!=null){
                supervisionEnFoodProIndexMapper.deleteByPrimaryKey(recordEFP.getId());
                supervisionEnFoodProMapper.deleteByIndexId(recordEFP.getId());
            }
        }
        if(!enterpriseParam.getPermissionFamily().contains("drugsBusiness")){
            SupervisionEnDrugsBuIndex recordEDB = supervisionEnDrugsBuIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            if (recordEDB!=null){
                supervisionEnDrugsBuIndexMapper.deleteByPrimaryKey(recordEDB.getId());
                supervisionEnDrugsBuMapper.deleteByIndexId(recordEDB.getId());
            }
        }
        if(!enterpriseParam.getPermissionFamily().contains("drugsProduce")){
            SupervisionEnDrugsProIndex recordEDP = supervisionEnDrugsProIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            if (recordEDP!=null){
                supervisionEnDrugsProIndexMapper.deleteByPrimaryKey(recordEDP.getId());
                supervisionEnDrugsProMapper.deleteByIndexId(recordEDP.getId());
            }
        }
        if(!enterpriseParam.getPermissionFamily().contains("cosmeticsUse")){
            SupervisionEnCosmeticsIndex recordEC = supervisionEnCosmeticsIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            if (recordEC!=null){
                supervisionEnCosmeticsIndexMapper.deleteByPrimaryKey(recordEC.getId());
                supervisionEnCosmeticsMapper.deleteByIndexId(recordEC.getId());
            }
        }
        if(!enterpriseParam.getPermissionFamily().contains("medicalProduce")){
            SupervisionEnMedicalProIndex recordEMP = supervisionEnMedicalProIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            if (recordEMP!=null){
                supervisionEnMedicalProIndexMapper.deleteByPrimaryKey(recordEMP.getId());
                supervisionEnMedicalProMapper.deleteByIndexId(recordEMP.getId());
            }
        }
        if(!enterpriseParam.getPermissionFamily().contains("medicalBusiness")){
            SupervisionEnMedicalBuIndex recordEMB = supervisionEnMedicalBuIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            if (recordEMB!=null){
                supervisionEnMedicalBuIndexMapper.deleteByPrimaryKey(recordEMB.getId());
                supervisionEnMedicalBuMapper.deleteByIndexId(recordEMB.getId());
            }
        }
        if(!enterpriseParam.getPermissionFamily().contains("smallCater")){
            SupervisionEnSmallCaterIndex recordESC = supervisionEnSmallCaterIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            if (recordESC!=null){
                supervisionEnSmallCaterIndexMapper.deleteByPrimaryKey(recordESC.getId());
                supervisionEnSmallCaterMapper.deleteByIndexId(recordESC.getId());
            }
        }
        if(!enterpriseParam.getPermissionFamily().contains("smallWorkshop")){
            SupervisionEnSmallWorkshopIndex recordESW = supervisionEnSmallWorkshopIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            if (recordESW!=null){
                supervisionEnSmallWorkshopIndexMapper.deleteByPrimaryKey(recordESW.getId());
                supervisionEnSmallWorkshopMapper.deleteByIndexId(recordESW.getId());
            }
        }
        if(!enterpriseParam.getPermissionFamily().contains("industrialProducts")){
            SupervisionEnIndustrialProductsIndex recordEIP = supervisionEnIndustrialProductsIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            if (recordEIP!=null){
                supervisionEnIndustrialProductsIndexMapper.deleteByPrimaryKey(recordEIP.getId());
                supervisionEnIndustrialProductsMapper.deleteByIndexId(recordEIP.getId());
            }
        }
        //??????????????????type????????????list???????????????size??????
        //?????????index???????????????????????????????????????????????????????????????id?????????????????????????????????indexid?????????id??????????????????
        //??????????????????list????????????index??????index???id?????????????????????????????????indexid??????????????????
        if(enterpriseParam.getPermissionFamily().contains("foodBusiness")){
            List<SupervisionEnFoodBu> supervisionEnFoodBuList = enterpriseParam.getFoodBusinessList();
            //??????????????????????????????
            if(supervisionEnFoodBuList.size()==0){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????????????????????????????");
            }
            //?????????index???????????????????????????????????????
            SupervisionEnFoodBuIndex supervisionEnFoodBuIndex = supervisionEnFoodBuIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            //????????????????????????????????????????????????
            SupervisionEnFoodBuIndex supervisionEnFoodBuIndex1 = new SupervisionEnFoodBuIndex();
            if (supervisionEnFoodBuIndex == null) {
                supervisionEnFoodBuIndex1.setEnterpriseId(supervisionEnterprise.getId());
                supervisionEnFoodBuIndexMapper.insertSelective(supervisionEnFoodBuIndex1);//??????????????????id
            }
            //??????????????????????????????????????????????????????????????????
            else{
                supervisionEnFoodBuMapper.deleteByIndexId(supervisionEnFoodBuIndex.getId());
            }
            SupervisionEnFoodBuIndex supervisionEnFoodBuIndex2 = supervisionEnFoodBuIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            SupervisionEnFoodBuIndex supervisionEnFoodBuIndex3 = new SupervisionEnFoodBuIndex();
            supervisionEnFoodBuIndex3.setId(supervisionEnFoodBuIndex2.getId());
            supervisionEnFoodBuIndex3.setNumber("");
            supervisionEnFoodBuList  = ListSortUtil.sort(supervisionEnFoodBuList,"endTime",null);
            supervisionEnFoodBuIndex3.setEndTime(supervisionEnFoodBuList.get(0).getEndTime());
            //?????????????????????list???
            for (SupervisionEnFoodBu supervisionEnFoodBu : supervisionEnFoodBuList){
                ValidationResult result = validator.validate(supervisionEnFoodBu);
                if(result.isHasErrors()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
                }//??????validator?????????
                supervisionEnFoodBu.setIndexId(supervisionEnFoodBuIndex3.getId());
                supervisionEnFoodBu.setOperateIp("124.124.124");
                supervisionEnFoodBu.setOperateTime(new Date());
                supervisionEnFoodBu.setOperator("zcc");
                supervisionEnFoodBuMapper.insertSelective(supervisionEnFoodBu);
                supervisionEnFoodBuIndex3.setNumber(supervisionEnFoodBuIndex3.getNumber() + ","+ supervisionEnFoodBu.getNumber());
            }
            supervisionEnFoodBuIndexMapper.updateByPrimaryKeySelective(supervisionEnFoodBuIndex3);
        }

        if(enterpriseParam.getPermissionFamily().contains("foodProduce")) {
            List<EnFoodProduceParam> enFoodProduceParamList = enterpriseParam.getFoodProduceList();
            List<Date> dateList = new ArrayList<>();
            //            //??????????????????????????????
            if (enFoodProduceParamList.size() == 0) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "????????????????????????????????????");
            }
//            //?????????index???????????????????????????????????????
            SupervisionEnFoodProIndex supervisionEnFoodProIndex = supervisionEnFoodProIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
//            //????????????????????????????????????????????????
            SupervisionEnFoodProIndex supervisionEnFoodProIndex1 = new SupervisionEnFoodProIndex();
            if (supervisionEnFoodProIndex == null) {
                supervisionEnFoodProIndex1.setEnterpriseId(supervisionEnterprise.getId());
                supervisionEnFoodProIndexMapper.insertSelective(supervisionEnFoodProIndex1);//??????????????????id
            }
//            //??????????????????????????????????????????????????????????????????
            else {
                supervisionEnFoodProMapper.deleteByIndexId(supervisionEnFoodProIndex.getId());
            }
            SupervisionEnFoodProIndex supervisionEnFoodProIndex2 = supervisionEnFoodProIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            SupervisionEnFoodProIndex supervisionEnFoodProIndex3 = new SupervisionEnFoodProIndex();
            supervisionEnFoodProIndex3.setId(supervisionEnFoodProIndex2.getId());
            supervisionEnFoodProIndex3.setNumber("");
            for (EnFoodProduceParam enFoodProduceParam : enFoodProduceParamList) {
            ValidationResult result = validator.validate(enFoodProduceParam);
            if(result.isHasErrors()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
            }
            SupervisionEnFoodPro supervisionEnFoodPro = new SupervisionEnFoodPro();
            BeanUtils.copyProperties(enFoodProduceParam,supervisionEnFoodPro);
            supervisionEnFoodProIndex3.setNumber(supervisionEnFoodProIndex3.getNumber() + ","+ supervisionEnFoodPro.getNumber());
            dateList.add(enFoodProduceParam.getEndTime());
            supervisionEnFoodPro.setIndexId(supervisionEnFoodProIndex3.getId());
            supervisionEnFoodPro.setOperatorIp("124.124.124");
            supervisionEnFoodPro.setOperatorTime(new Date());
            supervisionEnFoodPro.setOperator("zcc");
            supervisionEnFoodProMapper.insertSelective(supervisionEnFoodPro);
            supervisionEnProCategoryMapper.deleteByParentId(supervisionEnFoodPro.getId());
            List<SupervisionEnProCategory> supervisionEnProCategoryList = enFoodProduceParam.getList();
            if(supervisionEnProCategoryList.size()>0){
                supervisionEnProCategoryMapper.batchInsert(supervisionEnProCategoryList.stream().map((list)->{
                    list.setOperateIp("124.124.124");
                    list.setOperateTime(new Date());
                    list.setOperator("zcc");
                    list.setParentId(supervisionEnFoodPro.getId());
                    return list;}).collect(Collectors.toList()));
            }
            }
            List<Date> afterDateList =(List<Date>) ListSortUtil.sort(dateList,null);
            supervisionEnFoodProIndex3.setEndTime(afterDateList.get(0));
            supervisionEnFoodProIndexMapper.updateByPrimaryKeySelective(supervisionEnFoodProIndex3);
        }

        if(enterpriseParam.getPermissionFamily().contains("drugsBusiness")){
            List<SupervisionEnDrugsBu> supervisionEnDrugsBuList = enterpriseParam.getDrugsBusinessList();
            //??????????????????????????????
            if(enterpriseParam.getDrugsBusinessList().size()==0){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????????????????????????????");
            }
            //?????????index???????????????????????????????????????
            SupervisionEnDrugsBuIndex supervisionEnDrugsBuIndex = supervisionEnDrugsBuIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            //????????????????????????????????????????????????
            SupervisionEnDrugsBuIndex supervisionEnDrugsBuIndex1 = new SupervisionEnDrugsBuIndex();
            if (supervisionEnDrugsBuIndex == null) {
                supervisionEnDrugsBuIndex1.setEnterpriseId(supervisionEnterprise.getId());
                supervisionEnDrugsBuIndexMapper.insertSelective(supervisionEnDrugsBuIndex1);//??????????????????id
            }
            //??????????????????????????????????????????????????????????????????
            else{
                supervisionEnDrugsBuMapper.deleteByIndexId(supervisionEnDrugsBuIndex.getId());
            }
            SupervisionEnDrugsBuIndex supervisionEnDrugsBuIndex2 = supervisionEnDrugsBuIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            SupervisionEnDrugsBuIndex supervisionEnDrugsBuIndex3 = new SupervisionEnDrugsBuIndex();
            supervisionEnDrugsBuIndex3.setId(supervisionEnDrugsBuIndex2.getId());
            supervisionEnDrugsBuIndex3.setNumber("");
            supervisionEnDrugsBuList  = ListSortUtil.sort(supervisionEnDrugsBuList,"endTime",null);
            supervisionEnDrugsBuIndex3.setEndTime(supervisionEnDrugsBuList.get(0).getEndTime());
            //?????????????????????list???
            for (SupervisionEnDrugsBu supervisionEnDrugsBu : supervisionEnDrugsBuList){
                ValidationResult result = validator.validate(supervisionEnDrugsBu);
                if(result.isHasErrors()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
                }//??????validator?????????
                supervisionEnDrugsBu.setIndexId(supervisionEnDrugsBuIndex3.getId());
                supervisionEnDrugsBu.setOperatorIp("124.124.124");
                supervisionEnDrugsBu.setOperatorTime(new Date());
                supervisionEnDrugsBu.setOperator("zcc");
                supervisionEnDrugsBuMapper.insertSelective(supervisionEnDrugsBu);
                supervisionEnDrugsBuIndex3.setNumber(supervisionEnDrugsBuIndex3.getNumber() + ","+ supervisionEnDrugsBu.getNumber());
            }
            supervisionEnDrugsBuIndexMapper.updateByPrimaryKeySelective(supervisionEnDrugsBuIndex3);
        }

        if(enterpriseParam.getPermissionFamily().contains("drugsProduce")){
            List<SupervisionEnDrugsPro> supervisionEnDrugsProList = enterpriseParam.getDrugsProduceList();
            //??????????????????????????????
            if(supervisionEnDrugsProList.size()==0){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????????????????????????????");
            }
            //?????????index???????????????????????????????????????
            SupervisionEnDrugsProIndex supervisionEnDrugsProIndex= supervisionEnDrugsProIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            //????????????????????????????????????????????????
            SupervisionEnDrugsProIndex supervisionEnDrugsProIndex1 = new SupervisionEnDrugsProIndex();
            if (supervisionEnDrugsProIndex == null) {
                supervisionEnDrugsProIndex1.setEnterpriseId(supervisionEnterprise.getId());
                supervisionEnDrugsProIndexMapper.insertSelective(supervisionEnDrugsProIndex1);//??????????????????id
            }
            //??????????????????????????????????????????????????????????????????
            else{
                supervisionEnDrugsProMapper.deleteByIndexId(supervisionEnDrugsProIndex.getId());
            }
            SupervisionEnDrugsProIndex supervisionEnDrugsProIndex2 = supervisionEnDrugsProIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            SupervisionEnDrugsProIndex supervisionEnDrugsProIndex3 = new SupervisionEnDrugsProIndex();
            supervisionEnDrugsProIndex3.setId(supervisionEnDrugsProIndex2.getId());
            supervisionEnDrugsProIndex3.setNumber("");
            supervisionEnDrugsProList  = ListSortUtil.sort(supervisionEnDrugsProList,"endTime",null);
            supervisionEnDrugsProIndex3.setEndTime(supervisionEnDrugsProList.get(0).getEndTime());
            //?????????????????????list???
            for (SupervisionEnDrugsPro supervisionEnDrugsPro : supervisionEnDrugsProList){
                ValidationResult result = validator.validate(supervisionEnDrugsPro);
                if(result.isHasErrors()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
                }//??????validator?????????
                supervisionEnDrugsPro.setIndexId(supervisionEnDrugsProIndex3.getId());
                supervisionEnDrugsPro.setOperatorIp("124.124.124");
                supervisionEnDrugsPro.setOperatorTime(new Date());
                supervisionEnDrugsPro.setOperator("zcc");
                supervisionEnDrugsProMapper.insertSelective(supervisionEnDrugsPro);
                supervisionEnDrugsProIndex3.setNumber(supervisionEnDrugsProIndex3.getNumber() + ","+ supervisionEnDrugsPro.getNumber());
            }
            supervisionEnDrugsProIndexMapper.updateByPrimaryKeySelective(supervisionEnDrugsProIndex3);
        }

        if(enterpriseParam.getPermissionFamily().contains("cosmeticsUse")){
            List<SupervisionEnCosmetics> supervisionEnCosmeticsList = enterpriseParam.getCosmeticsList();
            //??????????????????????????????
            if(supervisionEnCosmeticsList.size()==0){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"?????????????????????????????????");
            }
            //?????????index???????????????????????????????????????
            SupervisionEnCosmeticsIndex supervisionEnCosmeticsIndex= supervisionEnCosmeticsIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            //????????????????????????????????????????????????
            SupervisionEnCosmeticsIndex supervisionEnCosmeticsIndex1 = new SupervisionEnCosmeticsIndex();
            if (supervisionEnCosmeticsIndex == null) {
                supervisionEnCosmeticsIndex1.setEnterpriseId(supervisionEnterprise.getId());
                supervisionEnCosmeticsIndexMapper.insertSelective(supervisionEnCosmeticsIndex1);//??????????????????id
            }
            //??????????????????????????????????????????????????????????????????
            else{
                supervisionEnCosmeticsMapper.deleteByIndexId(supervisionEnCosmeticsIndex.getId());
            }
            SupervisionEnCosmeticsIndex supervisionEnCosmeticsIndex2 = supervisionEnCosmeticsIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            SupervisionEnCosmeticsIndex supervisionEnCosmeticsIndex3= new SupervisionEnCosmeticsIndex();
            supervisionEnCosmeticsIndex3.setId(supervisionEnCosmeticsIndex2.getId());
            supervisionEnCosmeticsIndex3.setNumber("");
            supervisionEnCosmeticsList  = ListSortUtil.sort(supervisionEnCosmeticsList,"endTime",null);
            supervisionEnCosmeticsIndex3.setEndTime(supervisionEnCosmeticsList.get(0).getEndTime());
            //?????????????????????list???
            for (SupervisionEnCosmetics supervisionEnCosmetics : supervisionEnCosmeticsList){
                ValidationResult result = validator.validate(supervisionEnCosmetics);
                if(result.isHasErrors()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
                }//??????validator?????????
                supervisionEnCosmetics.setIndexId(supervisionEnCosmeticsIndex3.getId());
                supervisionEnCosmetics.setOperateIp("124.124.124");
                supervisionEnCosmetics.setOperateTime(new Date());
                supervisionEnCosmetics.setOperator("zcc");
                supervisionEnCosmeticsMapper.insertSelective(supervisionEnCosmetics);
                supervisionEnCosmeticsIndex3.setNumber(supervisionEnCosmeticsIndex3.getNumber() + ","+ supervisionEnCosmetics.getRegisterCode());
            }
            supervisionEnCosmeticsIndexMapper.updateByPrimaryKeySelective(supervisionEnCosmeticsIndex3);
        }

        if(enterpriseParam.getPermissionFamily().contains("medicalProduce")){
            List<SupervisionEnMedicalPro> supervisionEnMedicalProList = enterpriseParam.getMedicalProduceList();
            //??????????????????????????????
            if(supervisionEnMedicalProList.size()==0){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????????????????????????????");
            }
            //?????????index???????????????????????????????????????
            SupervisionEnMedicalProIndex supervisionEnMedicalProIndex= supervisionEnMedicalProIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            //????????????????????????????????????????????????
            SupervisionEnMedicalProIndex supervisionEnMedicalProIndex1 = new SupervisionEnMedicalProIndex();
            if (supervisionEnMedicalProIndex == null) {
                supervisionEnMedicalProIndex1.setEnterpriseId(supervisionEnterprise.getId());
                supervisionEnMedicalProIndexMapper.insertSelective(supervisionEnMedicalProIndex1);//??????????????????id
            }
            //??????????????????????????????????????????????????????????????????
            else{
                supervisionEnMedicalProMapper.deleteByIndexId(supervisionEnMedicalProIndex.getId());
            }
            SupervisionEnMedicalProIndex supervisionEnMedicalProIndex2 = supervisionEnMedicalProIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            SupervisionEnMedicalProIndex supervisionEnMedicalProIndex3 = new SupervisionEnMedicalProIndex();
            supervisionEnMedicalProIndex3.setId(supervisionEnMedicalProIndex2.getId());
            supervisionEnMedicalProIndex3.setNumber("");
            supervisionEnMedicalProList  = ListSortUtil.sort(supervisionEnMedicalProList,"endTime",null);
            supervisionEnMedicalProIndex3.setEndTime(supervisionEnMedicalProList.get(0).getEndTime());
            //?????????????????????list???
            for (SupervisionEnMedicalPro supervisionEnMedicalPro : supervisionEnMedicalProList){
                ValidationResult result = validator.validate(supervisionEnMedicalPro);
                if(result.isHasErrors()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
                }//??????validator?????????
                supervisionEnMedicalPro.setIndexId(supervisionEnMedicalProIndex3.getId());
                supervisionEnMedicalPro.setOperateIp("124.124.124");
                supervisionEnMedicalPro.setOperateTime(new Date());
                supervisionEnMedicalPro.setOperator("zcc");
                supervisionEnMedicalProMapper.insertSelective(supervisionEnMedicalPro);
                supervisionEnMedicalProIndex3.setNumber(supervisionEnMedicalProIndex3.getNumber() + ","+ supervisionEnMedicalPro.getRegisterNumber());
            }
            supervisionEnMedicalProIndexMapper.updateByPrimaryKeySelective(supervisionEnMedicalProIndex3);
        }

        if(enterpriseParam.getPermissionFamily().contains("medicalBusiness")){
            List<SupervisionEnMedicalBu> supervisionEnMedicalBuList = enterpriseParam.getMedicalBusinessList();
            //??????????????????????????????
            if(supervisionEnMedicalBuList.size()==0){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????????????????????????????");
            }
            //?????????index???????????????????????????????????????
            SupervisionEnMedicalBuIndex supervisionEnMedicalBuIndex= supervisionEnMedicalBuIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            //????????????????????????????????????????????????
            SupervisionEnMedicalBuIndex supervisionEnMedicalBuIndex1 = new SupervisionEnMedicalBuIndex();
            if (supervisionEnMedicalBuIndex == null) {
                supervisionEnMedicalBuIndex1.setEnterpriseId(supervisionEnterprise.getId());
                supervisionEnMedicalBuIndexMapper.insertSelective(supervisionEnMedicalBuIndex1);//??????????????????id
            }
            //??????????????????????????????????????????????????????????????????
            else{
                supervisionEnMedicalBuMapper.deleteByIndexId(supervisionEnMedicalBuIndex.getId());
            }
            SupervisionEnMedicalBuIndex supervisionEnMedicalBuIndex2 = supervisionEnMedicalBuIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            SupervisionEnMedicalBuIndex supervisionEnMedicalBuIndex3 = new SupervisionEnMedicalBuIndex();
            supervisionEnMedicalBuIndex3.setId(supervisionEnMedicalBuIndex2.getId());
            supervisionEnMedicalBuIndex3.setNumber("");
            supervisionEnMedicalBuList  = ListSortUtil.sort(supervisionEnMedicalBuList,"endTime",null);
            supervisionEnMedicalBuIndex3.setEndTime(supervisionEnMedicalBuList.get(0).getEndTime());
            //?????????????????????list???
            for (SupervisionEnMedicalBu supervisionEnMedicalBu : supervisionEnMedicalBuList){
                ValidationResult result = validator.validate(supervisionEnMedicalBu);
                if(result.isHasErrors()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
                }//??????validator?????????
                supervisionEnMedicalBu.setIndexId(supervisionEnMedicalBuIndex3.getId());
                supervisionEnMedicalBu.setOperateIp("124.124.124");
                supervisionEnMedicalBu.setOperateTime(new Date());
                supervisionEnMedicalBu.setOperator("zcc");
                supervisionEnMedicalBuMapper.insertSelective(supervisionEnMedicalBu);
                supervisionEnMedicalBuIndex3.setNumber(supervisionEnMedicalBuIndex3.getNumber() + ","+ supervisionEnMedicalBu.getRegisterNumber());
            }
            supervisionEnMedicalBuIndexMapper.updateByPrimaryKeySelective(supervisionEnMedicalBuIndex3);
        }

        if(enterpriseParam.getPermissionFamily().contains("smallCater")){
            List<SupervisionEnSmallCater> supervisionEnSmallCaterList = enterpriseParam.getSmallCaterList();
            //??????????????????????????????
            if(supervisionEnSmallCaterList.size()==0){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"?????????????????????????????????");
            }
            //?????????index???????????????????????????????????????
            SupervisionEnSmallCaterIndex supervisionEnSmallCaterIndex= supervisionEnSmallCaterIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            //????????????????????????????????????????????????
            SupervisionEnSmallCaterIndex supervisionEnSmallCaterIndex1 = new SupervisionEnSmallCaterIndex();
            if (supervisionEnSmallCaterIndex == null) {
                supervisionEnSmallCaterIndex1.setEnterpriseId(supervisionEnterprise.getId());
                supervisionEnSmallCaterIndexMapper.insertSelective(supervisionEnSmallCaterIndex1);//??????????????????id
            }
            //??????????????????????????????????????????????????????????????????
            else{
                supervisionEnSmallCaterMapper.deleteByIndexId(supervisionEnSmallCaterIndex.getId());
            }
            SupervisionEnSmallCaterIndex supervisionEnSmallCaterIndex2 = supervisionEnSmallCaterIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            SupervisionEnSmallCaterIndex supervisionEnSmallCaterIndex3 = new SupervisionEnSmallCaterIndex();
            supervisionEnSmallCaterIndex3.setId(supervisionEnSmallCaterIndex2.getId());
            supervisionEnSmallCaterIndex3.setNumber("");
            supervisionEnSmallCaterList  = ListSortUtil.sort(supervisionEnSmallCaterList,"endTime",null);
            supervisionEnSmallCaterIndex3.setEndTime(supervisionEnSmallCaterList.get(0).getEndTime());
            //?????????????????????list???
            for (SupervisionEnSmallCater supervisionEnSmallCater : supervisionEnSmallCaterList){
                ValidationResult result = validator.validate(supervisionEnSmallCater);
                if(result.isHasErrors()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
                }//??????validator?????????
                supervisionEnSmallCater.setIndexId(supervisionEnSmallCaterIndex3.getId());
                supervisionEnSmallCater.setOperateIp("124.124.124");
                supervisionEnSmallCater.setOperateTime(new Date());
                supervisionEnSmallCater.setOperator("zcc");
                supervisionEnSmallCaterMapper.insertSelective(supervisionEnSmallCater);
                supervisionEnSmallCaterIndex3.setNumber(supervisionEnSmallCaterIndex3.getNumber() + ","+ supervisionEnSmallCater.getRegisterNumber());
            }
            supervisionEnSmallCaterIndexMapper.updateByPrimaryKeySelective(supervisionEnSmallCaterIndex3);
        }

        if(enterpriseParam.getPermissionFamily().contains("smallWorkshop")){
            List<SupervisionEnSmallWorkshop> supervisionEnSmallWorkshopList = enterpriseParam.getSmallWorkshopList();
            //??????????????????????????????
            if(supervisionEnSmallWorkshopList.size()==0){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"?????????????????????????????????");
            }
            //?????????index???????????????????????????????????????
            SupervisionEnSmallWorkshopIndex supervisionEnSmallWorkshopIndex= supervisionEnSmallWorkshopIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            //????????????????????????????????????????????????
            SupervisionEnSmallWorkshopIndex supervisionEnSmallWorkshopIndex1 = new SupervisionEnSmallWorkshopIndex();
            if (supervisionEnSmallWorkshopIndex == null) {
                supervisionEnSmallWorkshopIndex1.setEnterpriseId(supervisionEnterprise.getId());
                supervisionEnSmallWorkshopIndexMapper.insertSelective(supervisionEnSmallWorkshopIndex1);//??????????????????id
            }
            //??????????????????????????????????????????????????????????????????
            else{
                supervisionEnSmallWorkshopMapper.deleteByIndexId(supervisionEnSmallWorkshopIndex.getId());
            }
            SupervisionEnSmallWorkshopIndex supervisionEnSmallWorkshopIndex2 = supervisionEnSmallWorkshopIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            SupervisionEnSmallWorkshopIndex supervisionEnSmallWorkshopIndex3 = new SupervisionEnSmallWorkshopIndex();
            supervisionEnSmallWorkshopIndex3.setId(supervisionEnSmallWorkshopIndex2.getId());
            supervisionEnSmallWorkshopIndex3.setNumber("");
            supervisionEnSmallWorkshopList  = ListSortUtil.sort(supervisionEnSmallWorkshopList,"endTime",null);
            supervisionEnSmallWorkshopIndex3.setEndTime(supervisionEnSmallWorkshopList.get(0).getEndTime());
            //?????????????????????list???
            for (SupervisionEnSmallWorkshop supervisionEnSmallWorkshop : supervisionEnSmallWorkshopList){
                ValidationResult result = validator.validate(supervisionEnSmallWorkshop);
                if(result.isHasErrors()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
                }//??????validator?????????
                supervisionEnSmallWorkshop.setIndexId(supervisionEnSmallWorkshopIndex3.getId());
                supervisionEnSmallWorkshop.setOperateIp("124.124.124");
                supervisionEnSmallWorkshop.setOperateTime(new Date());
                supervisionEnSmallWorkshop.setOperator("zcc");
                supervisionEnSmallWorkshopMapper.insertSelective(supervisionEnSmallWorkshop);
                supervisionEnSmallWorkshopIndex3.setNumber(supervisionEnSmallWorkshopIndex3.getNumber() + ","+ supervisionEnSmallWorkshop.getRegisterNumber());
            }
            supervisionEnSmallWorkshopIndexMapper.updateByPrimaryKeySelective(supervisionEnSmallWorkshopIndex3);
        }

        if(enterpriseParam.getPermissionFamily().contains("industrialProducts")){
            List<SupervisionEnIndustrialProducts> supervisionEnIndustrialProductsList = enterpriseParam.getIndustrialProductsList();
            //??????????????????????????????
            if(supervisionEnIndustrialProductsList.size()==0){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????????????????????????????");
            }
            //?????????index???????????????????????????????????????
            SupervisionEnIndustrialProductsIndex supervisionEnIndustrialProductsIndex= supervisionEnIndustrialProductsIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            //????????????????????????????????????????????????
            SupervisionEnIndustrialProductsIndex supervisionEnIndustrialProductsIndex1 = new SupervisionEnIndustrialProductsIndex();
            if (supervisionEnIndustrialProductsIndex == null) {
                supervisionEnIndustrialProductsIndex1.setEnterpriseId(supervisionEnterprise.getId());
                supervisionEnIndustrialProductsIndexMapper.insertSelective(supervisionEnIndustrialProductsIndex1);//??????????????????id
            }
            //??????????????????????????????????????????????????????????????????
            else{
                supervisionEnIndustrialProductsMapper.deleteByIndexId(supervisionEnIndustrialProductsIndex.getId());
            }
            SupervisionEnIndustrialProductsIndex supervisionEnIndustrialProductsIndex2 = supervisionEnIndustrialProductsIndexMapper.selectByEnterpriseId(supervisionEnterprise.getId());
            SupervisionEnIndustrialProductsIndex supervisionEnIndustrialProductsIndex3 = new SupervisionEnIndustrialProductsIndex();
            supervisionEnIndustrialProductsIndex3.setId(supervisionEnIndustrialProductsIndex2.getId());
            supervisionEnIndustrialProductsIndex3.setNumber("");
            supervisionEnIndustrialProductsList  = ListSortUtil.sort(supervisionEnIndustrialProductsList,"endTime",null);
            supervisionEnIndustrialProductsIndex3.setEndTime(supervisionEnIndustrialProductsList.get(0).getEndTime());
            //?????????????????????list???
            for (SupervisionEnIndustrialProducts supervisionEnIndustrialProducts : supervisionEnIndustrialProductsList){
                ValidationResult result = validator.validate(supervisionEnIndustrialProducts);
                if(result.isHasErrors()){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
                }//??????validator?????????
                supervisionEnIndustrialProducts.setIndexId(supervisionEnIndustrialProductsIndex3.getId());
                supervisionEnIndustrialProducts.setOperateIp("124.124.124");
                supervisionEnIndustrialProducts.setOperateTime(new Date());
                supervisionEnIndustrialProducts.setOperator("zcc");
                supervisionEnIndustrialProductsMapper.insertSelective(supervisionEnIndustrialProducts);
                supervisionEnIndustrialProductsIndex3.setNumber(supervisionEnIndustrialProductsIndex3.getNumber() + ","+ supervisionEnIndustrialProducts.getRegisterNumber());
            }
            supervisionEnIndustrialProductsIndexMapper.updateByPrimaryKeySelective(supervisionEnIndustrialProductsIndex3);
        }


    }

    //??????????????????????????????????????????????????????
    void insertEnterpriseDocumentList(SupervisionEnterprise supervisionEnterprise,EnterpriseParam enterpriseParam){
        //??????????????????????????????????????????????????????????????????

        List<SupervisionEnterpriseDocument> insertList = new ArrayList<>();

        if (enterpriseParam.getBusinessLicensePhoto()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument1 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument1.setDocument(enterpriseParam.getBusinessLicensePhoto());
            supervisionEnterpriseDocument1.setFlag(1);
            insertList.add(supervisionEnterpriseDocument1);
        }
        if (enterpriseParam.getFoodBusinessPhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument2 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument2.setDocument(enterpriseParam.getFoodBusinessPhotos());
            supervisionEnterpriseDocument2.setFlag(2);
            insertList.add(supervisionEnterpriseDocument2);
        }
        if (enterpriseParam.getSmallCaterPhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument3 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument3.setDocument(enterpriseParam.getSmallCaterPhotos());
            supervisionEnterpriseDocument3.setFlag(3);
            insertList.add(supervisionEnterpriseDocument3);
        }
        if (enterpriseParam.getSmallWorkshopPhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument4 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument4.setDocument(enterpriseParam.getSmallWorkshopPhotos());
            supervisionEnterpriseDocument4.setFlag(4);
            insertList.add(supervisionEnterpriseDocument4);
        }
        if (enterpriseParam.getFoodProducePhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument5 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument5.setDocument(enterpriseParam.getFoodProducePhotos());
            supervisionEnterpriseDocument5.setFlag(5);
            insertList.add(supervisionEnterpriseDocument5);
        }
        if (enterpriseParam.getDrugsBusinessPhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument6 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument6.setDocument(enterpriseParam.getDrugsBusinessPhotos());
            supervisionEnterpriseDocument6.setFlag(6);
            insertList.add(supervisionEnterpriseDocument6);
        }
        if (enterpriseParam.getDrugsProducePhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument7 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument7.setDocument(enterpriseParam.getDrugsProducePhotos());
            supervisionEnterpriseDocument7.setFlag(7);
            insertList.add(supervisionEnterpriseDocument7);
        }
        if (enterpriseParam.getCosmeticsUsePhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument8 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument8.setDocument(enterpriseParam.getCosmeticsUsePhotos());
            supervisionEnterpriseDocument8.setFlag(8);
            insertList.add(supervisionEnterpriseDocument8);
        }
        if (enterpriseParam.getMedicalProducePhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument9 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument9.setDocument(enterpriseParam.getMedicalProducePhotos());
            supervisionEnterpriseDocument9.setFlag(9);
            insertList.add(supervisionEnterpriseDocument9);
        }
        if (enterpriseParam.getMedicalBusinessPhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument10 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument10.setDocument(enterpriseParam.getMedicalBusinessPhotos());
            supervisionEnterpriseDocument10.setFlag(10);
            insertList.add(supervisionEnterpriseDocument10);
        }
        if (enterpriseParam.getIndustrialProductsPhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument11 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument11.setDocument(enterpriseParam.getIndustrialProductsPhotos());
            supervisionEnterpriseDocument11.setFlag(11);
            insertList.add(supervisionEnterpriseDocument11);
        }
        if (enterpriseParam.getPublicityPhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument12 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument12.setDocument(enterpriseParam.getPublicityPhotos());
            supervisionEnterpriseDocument12.setFlag(12);
            insertList.add(supervisionEnterpriseDocument12);
        }
        if (enterpriseParam.getCertificatePhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument13 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument13.setDocument(enterpriseParam.getCertificatePhotos());
            supervisionEnterpriseDocument13.setFlag(13);
            insertList.add(supervisionEnterpriseDocument13);
        }
        if (enterpriseParam.getOtherPhotos()!=null){
            SupervisionEnterpriseDocument supervisionEnterpriseDocument14 = new SupervisionEnterpriseDocument();
            supervisionEnterpriseDocument14.setDocument(enterpriseParam.getOtherPhotos());
            supervisionEnterpriseDocument14.setFlag(14);
            insertList.add(supervisionEnterpriseDocument14);
        }
        supervisionEnterpriseDocumentMapper.deleteByEnterpriseId(supervisionEnterprise.getId());
        if (insertList.size()>0){
            supervisionEnterpriseDocumentMapper.batchInsert(insertList.stream().map((list)->{
                list.setEnterpriseId(supervisionEnterprise.getId());
                return list;}).collect(Collectors.toList()));
        }
    }

    @Override
    @Transactional
    public void changeNormal(Integer id, Integer bId) {
        SupervisionEnterprise supervisionEnterprise = new SupervisionEnterprise();
        supervisionEnterprise.setId(id);
        supervisionEnterprise.setBusinessState(bId);
        supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
    }

    @Override
    @Transactional
    public void changeAbnormal(Integer id, Integer abId, String content) {
        SupervisionEnterprise supervisionEnterprise = new SupervisionEnterprise();
        supervisionEnterprise.setId(id);
        supervisionEnterprise.setBusinessState(3);
        supervisionEnterprise.setAbnormalId(abId);
        supervisionEnterprise.setAbnormalContent(content);
        supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
    }

    @Override
    @Transactional
    public void changeGpsFlag() {
        List<String> idNumberList = gridPointsGpsMapper.getIdNumber();
        for (String idNumber : idNumberList){
            supervisionEnterpriseMapper.changeFlagByIdNumber(idNumber);
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        SupervisionEnterprise before = supervisionEnterpriseMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????????????????");
        }
        supervisionEnterpriseMapper.deleteByPrimaryKey(id);
        supervisionEnFoodBuIndexMapper.deleteByEnterpriseId(id);
        supervisionEnFoodProIndexMapper.deleteByEnterpriseId(id);
        supervisionEnDrugsBuIndexMapper.deleteByEnterpriseId(id);
        supervisionEnDrugsProIndexMapper.deleteByEnterpriseId(id);
        supervisionEnCosmeticsIndexMapper.deleteByEnterpriseId(id);
        supervisionEnMedicalProIndexMapper.deleteByEnterpriseId(id);
        supervisionEnMedicalBuIndexMapper.deleteByEnterpriseId(id);
        supervisionEnSmallCaterIndexMapper.deleteByEnterpriseId(id);
        supervisionEnSmallWorkshopIndexMapper.deleteByEnterpriseId(id);
        supervisionEnIndustrialProductsIndexMapper.deleteByEnterpriseId(id);
    }



    //??????????????????
    @Override
    public void changeStop(int id) {
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(id);
        if(supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"????????????????????????");
        }
        int isStop;
        if(supervisionEnterprise.getIsStop()==0){
            isStop=1;
        }else{
            isStop=0;
        }
        supervisionEnterpriseMapper.changeStop(id,isStop);
    }

    @Override
    public PageResult<EnterpriseListResult> getPageByEnterpriseId(int id) {
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(id);
        EnterpriseListResult enterpriseListResult = new EnterpriseListResult();
        if(supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"???????????????");
        }
        BeanUtils.copyProperties(supervisionEnterprise,enterpriseListResult);
        List<EnterpriseListResult> enterpriseList = new ArrayList<>();
        enterpriseList.add(enterpriseListResult);
        PageResult<EnterpriseListResult> pageResult = new PageResult<>();
        pageResult.setData(enterpriseList);
        pageResult.setTotal(1);
        pageResult.setPageNo(1);
        pageResult.setPageSize(1);
        return pageResult;
    }

    @Override
    public Map<Integer,Integer> getStatistics(List<SysIndustry> sysIndustryList, List<Integer> sysAreaList,String supervisor) {
        Map<Integer,Integer> map = new HashMap<>();
        for (SysIndustry sysIndustry:sysIndustryList){
                EnterpriseSearchParam enterpriseSearchParam = new EnterpriseSearchParam();
                List<String> industryList = new ArrayList<>();
                industryList.add(sysIndustry.getRemark());
                enterpriseSearchParam.setAreaList(sysAreaList);
                enterpriseSearchParam.setIndustryList(industryList);
                enterpriseSearchParam.setSupervisor(supervisor);
                map.put(sysIndustry.getId(),supervisionEnterpriseMapper.countList(enterpriseSearchParam));
        }
        return map;
    }
//????????????
    @Override
    @Transactional
    public JSONObject importExcel(MultipartFile file, Integer type) {

        JSONObject changedNumbers = new JSONObject();
        int updateNumber=0;
        int insertNumber=0;

        List<SysDept> sysDeptList = sysDeptMapper.getAllDept();//???????????????????????????id???????????????map
        Map<String,Integer> deptMap = new HashMap<>();
        for (SysDept sysDept : sysDeptList){
            deptMap.put(sysDept.getName(),sysDept.getId());
        }

        List<SysArea> sysAreaList = sysAreaMapper.getAllArea();//???????????????????????????id???????????????map
        Map<String,Integer> areaMap = new HashMap<>();
        for(SysArea sysArea : sysAreaList){
            areaMap.put(sysArea.getName(),sysArea.getId());
        }

        List<EnterpriseListResult> allEnterpriseList = supervisionEnterpriseMapper.getAll();//???????????????????????????id???????????????map
        Map<String,Integer> enterpriseIdMap = new HashMap<>();
        for (EnterpriseListResult enterpriseListResult : allEnterpriseList){
            enterpriseIdMap.put(enterpriseListResult.getIdNumber(),enterpriseListResult.getId());
        }

//        //???????????????map??????????????????????????????????????????????????????id??????????????????????????????
//        List<SupervisionEnterprise> supervisionEnterpriseList = new ArrayList<>();
//        List<SupervisionEnFoodPro> supervisionEnFoodProList = new ArrayList<>();
//        List<SupervisionEnProCategory> supervisionEnProCategoryList = new ArrayList<>();
//        List<SupervisionEnMedical> supervisionEnMedicalList = new ArrayList<>();
//        List<SupervisionEnFoodCir> supervisionEnFoodCirList = new ArrayList<>();
//        List<SupervisionEnFoodBu> supervisionEnFoodBuList = new ArrayList<>();
//        List<SupervisionEnDrugsBu> supervisionEnDrugsBuList = new ArrayList<>();
//        List<SupervisionEnCommon>  supervisionEnCommonList = new ArrayList<>();
//        List<SupervisionEnCosmetics> supervisionEnCosmeticsList = new ArrayList<>();
        if(type == 7) {
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet sheet0 = workbook.getSheetAt(0);
                Map<String, String> errorMap = new HashMap<>();
                List<String> errorList = new ArrayList<String>();
                List<SysUser> sysUserList = new ArrayList<>();

                //?????????????????????????????????id????????????????????????????????????
                int rowNumber=sheet0.getPhysicalNumberOfRows();
                for (int j = 0; j <rowNumber; j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    XSSFRow titleRow = sheet0.getRow(0);
                    XSSFRow row = sheet0.getRow(j);
                    XSSFRow nextRow = sheet0.getRow(j+1);
                    if((row.getCell(0).getCellType()==CellType.BLANK)&&(row.getCell(1).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    int a = j + 1;
                    if(row.getCell(0).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????");
                    }
                    if(row.getCell(1).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????");
                    }
                    if(row.getCell(3).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????");
                    }
                    if(row.getCell(4).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????");
                    }
                    if(row.getCell(5).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????");
                    }
                    if(row.getCell(14).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(14).toString()+"??????");
                    }
                    if(row.getCell(15).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(15).toString()+"??????");
                    }
                    if ((row.getCell(0) != null) && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(0).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(0).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????????????????");
                    }
                    if ((row.getCell(1) != null) && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(1).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????????????????");
                    }
                    if ((row.getCell(2) != null) && row.getCell(2).getCellType() != CellType.BLANK  && row.getCell(2).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(2).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????????????????");
                    }
                    if ((row.getCell(3) != null)&& row.getCell(3).getCellType() != CellType.BLANK  && row.getCell(3).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(3).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????????????????");
                    }
                    if ((row.getCell(4) != null) && (row.getCell(4).getCellType()!=CellType.BLANK)&& row.getCell(4).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(4).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????????????????");
                    }
                    if ((row.getCell(5) != null) &&(row.getCell(5).getCellType()!=CellType.BLANK)&& row.getCell(5).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(5).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????????????????");
                    }
                    if ((row.getCell(6) != null) && row.getCell(6).getCellType() != CellType.BLANK && row.getCell(6).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(6).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(6).toString()+"??????????????????");
                    }
                    if ((row.getCell(7) != null) && (row.getCell(7).getCellType()!=CellType.BLANK)&& row.getCell(7).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(7).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(7).toString()+"??????????????????");
                    }
                    if ((row.getCell(8) != null) && row.getCell(8).getCellType() != CellType.BLANK && row.getCell(8).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(8).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(8).toString()+"??????????????????");
                    }
                    if ((row.getCell(9) != null)&& row.getCell(9).getCellType() != CellType.BLANK  &&row.getCell(9).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(9).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????????????????");
                    }
                    if ((row.getCell(10) != null) && row.getCell(10).getCellType() != CellType.BLANK &&row.getCell(10).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(10).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????????????????");
                    }
                    if ((row.getCell(11) != null)&& row.getCell(11).getCellType() != CellType.BLANK  &&row.getCell(11).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(11).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(11).toString()+"??????????????????");
                    }
                    if ((row.getCell(12) != null)&& row.getCell(12).getCellType() != CellType.BLANK  && row.getCell(12).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(12).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????????????????");
                    }
                    if ((row.getCell(13) != null) && row.getCell(13).getCellType() != CellType.BLANK && row.getCell(13).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(13).toString(),"??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(13).toString()+"??????????????????");
                    }
                    if ((row.getCell(14) != null)&& row.getCell(14).getCellType() != CellType.BLANK && row.getCell(14).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(14).toString(),"??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(14).toString()+"??????????????????");
                    }
                    if ((row.getCell(15) != null) && row.getCell(15).getCellType() != CellType.BLANK && row.getCell(15).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(15).toString(),"??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(15).toString()+"??????????????????");
                    }
                    if ((row.getCell(16) != null) && row.getCell(16).getCellType() != CellType.BLANK && row.getCell(16).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(16).toString(),"??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(16).toString()+"??????????????????");
                    }
                    if ((row.getCell(17) != null)&& row.getCell(17).getCellType() != CellType.BLANK  && row.getCell(17).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(17).toString(),"??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(17).toString()+"??????????????????");
                    }
                    if ((row.getCell(18) != null) && row.getCell(18).getCellType() != CellType.BLANK && row.getCell(18).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(18).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(18).toString()+"??????????????????");
                    }
                    if ((row.getCell(19) != null) && row.getCell(19).getCellType() != CellType.BLANK && row.getCell(19).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(19).toString(),"??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(19).toString()+"??????????????????");
                    }
                    if ((row.getCell(20) != null) && row.getCell(20).getCellType() != CellType.BLANK && row.getCell(20).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(20).toString(),"??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(20).toString()+"??????????????????");
                    }
                }
                if (!errorList.isEmpty()) {
                    System.out.println(errorList);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, errorList);
                }
                for (int j = 0; j < rowNumber; j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    SupervisionEnterprise supervisionEnterprise = new SupervisionEnterprise();
                    XSSFRow row = sheet0.getRow(j);
                    supervisionEnterprise.setOperationMode(ExcalUtils.handleStringXSSF(row.getCell(0)));
                    supervisionEnterprise.setEnterpriseName(ExcalUtils.handleStringXSSF(row.getCell(1)));
                    supervisionEnterprise.setShopName(ExcalUtils.handleStringXSSF(row.getCell(2)));
                    supervisionEnterprise.setIdNumber(ExcalUtils.handleStringXSSF(row.getCell(3)));
                    supervisionEnterprise.setRegisteredAddress(ExcalUtils.handleStringXSSF(row.getCell(4)));
                    supervisionEnterprise.setLegalPerson(ExcalUtils.handleStringXSSF(row.getCell(5)));
                    supervisionEnterprise.setIpIdNumber(ExcalUtils.handleStringXSSF(row.getCell(6)));
                    supervisionEnterprise.setCantacts(ExcalUtils.handleStringXSSF(row.getCell(7)));
                    supervisionEnterprise.setCantactWay(ExcalUtils.handleStringXSSF(row.getCell(8)));
                    supervisionEnterprise.setBusinessTermStart(ExcalUtils.handleStringXSSF(row.getCell(9)));
                    supervisionEnterprise.setBusinessTermEnd(ExcalUtils.handleStringXSSF(row.getCell(10)));
                    if (ExcalUtils.handleStringXSSF(row.getCell(10)).isEmpty()||ExcalUtils.handleStringXSSF(row.getCell(10))==null||ExcalUtils.handleStringXSSF(row.getCell(10))==""){
                        supervisionEnterprise.setBusinessTermFlag(0);
                    }else {
                        supervisionEnterprise.setBusinessTermFlag(1);
                    }
                    supervisionEnterprise.setGivenDate(ExcalUtils.handleDateXSSF(row.getCell(11)));
                    supervisionEnterprise.setGivenGov(ExcalUtils.handleStringXSSF(row.getCell(12)));
                    supervisionEnterprise.setBusinessScale(ExcalUtils.handleStringXSSF(row.getCell(13)));
                    supervisionEnterprise.setArea(areaMap.get(ExcalUtils.handleStringXSSF(row.getCell(14))));
                    supervisionEnterprise.setRegulators(deptMap.get(ExcalUtils.handleStringXSSF(row.getCell(15))));
                    supervisionEnterprise.setSupervisor(ExcalUtils.handleStringXSSF(row.getCell(16)));
                    supervisionEnterprise.setGrid(areaMap.get(ExcalUtils.handleStringXSSF(row.getCell(17))));
                    supervisionEnterprise.setGridPerson(ExcalUtils.handleStringXSSF(row.getCell(18)));
                    supervisionEnterprise.setTransformationType(ExcalUtils.handleStringXSSF(row.getCell(19)));
                    supervisionEnterprise.setIntegrityLevel(ExcalUtils.handleStringXSSF(row.getCell(20)));
                    supervisionEnterprise.setOperator("?????????");
                    supervisionEnterprise.setOperateIp("123.123.123");
                    supervisionEnterprise.setOperateTime(new Date());
                    if (!supervisionEnterprise.getIdNumber().equals("")) {
                        if (enterpriseIdMap.get(supervisionEnterprise.getIdNumber()) != null) {
                            int id = enterpriseIdMap.get(supervisionEnterprise.getIdNumber());
                            supervisionEnterprise.setId(id);
                            supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
                            updateNumber++;
                        } else {//??????????????????
                            supervisionEnterpriseMapper.insertSelective(supervisionEnterprise);
                            SysUser sysUser1 = new SysUser();//???????????????????????????
                            String encryptedPassword = MD5Util.md5("123456+");
                            sysUser1.setUsername(supervisionEnterprise.getEnterpriseName());
                            sysUser1.setLoginName(supervisionEnterprise.getIdNumber());
                            sysUser1.setPassword(encryptedPassword);
                            sysUser1.setUserType(1);
                            sysUser1.setInfoName(supervisionEnterprise.getEnterpriseName());
                            sysUser1.setInfoId(supervisionEnterprise.getId());
                            sysUser1.setStatus(0);
                            sysUser1.setOperator("?????????");
                            sysUser1.setOperateIp("124.124.124");
                            sysUser1.setOperateTime(new Date());
                            sysUserMapper.insertSelective(sysUser1);
                            insertNumber++;
                        }
                    }
                }
                changedNumbers.put("updateNumbers",updateNumber);
                changedNumbers.put("insertNumbers",insertNumber);

                allEnterpriseList = supervisionEnterpriseMapper.getAll();//???????????????????????????id???????????????map
                enterpriseIdMap = new HashMap<>();
                for (EnterpriseListResult enterpriseListResult : allEnterpriseList){
                    enterpriseIdMap.put(enterpriseListResult.getIdNumber(),enterpriseListResult.getId());
                }

                //?????????????????????
                List<SupervisionEnFoodBu> supervisionEnFoodBuList = supervisionEnFoodBuMapper.getAll();
                SupervisionEnterprise supervisionEnterprise = new SupervisionEnterprise();
                DateComparedUtil dateComparedUtil = new DateComparedUtil();

                //?????????????????????????????????id
                Map <String,Integer> numberMap =new HashMap<>();
                for (SupervisionEnFoodBu supervisionEnFoodBu:supervisionEnFoodBuList){
                    numberMap.put(supervisionEnFoodBu.getNumber(),supervisionEnFoodBu.getId());
                }

                //????????????id???id
                List<SupervisionEnFoodBuIndex> supervisionEnFoodBuIndexList = supervisionEnFoodBuIndexMapper.getAll();
                Map <Integer,Integer> numberFoodBuIndexMap =new HashMap<>();
                for (SupervisionEnFoodBuIndex supervisionEnFoodBuIndex:supervisionEnFoodBuIndexList){
                    numberFoodBuIndexMap.put(supervisionEnFoodBuIndex.getEnterpriseId(),supervisionEnFoodBuIndex.getId());
                }

                XSSFSheet sheet1 = workbook.getSheetAt(1);
                for (int j = 0; j <sheet1.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    XSSFRow titleRow = sheet1.getRow(0);
                    XSSFRow row = sheet1.getRow(j);
                    XSSFRow nextRow = sheet1.getRow(j+1);
//                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    if((row.getCell(1)==null)&&(row.getCell(2)==null))
                    {
                        break;
                    }
                    int a = j + 1;
                    if(row.getCell(1).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????");
                    }
                    if(row.getCell(2).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????");
                    }
                    if(row.getCell(12).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????");
                    }
                    if ((row.getCell(0) != null) && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(0).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(0).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????????????????");
                    }
                    if ((row.getCell(1) != null) && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(1).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????????????????");
                    }
                    if ((row.getCell(2) != null) && row.getCell(2).getCellType() != CellType.BLANK  && row.getCell(2).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(2).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????????????????");
                    }
                    if ((row.getCell(3) != null)&& row.getCell(3).getCellType() != CellType.BLANK  && row.getCell(3).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(3).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????????????????");
                    }
                    if ((row.getCell(4) != null) && (row.getCell(4).getCellType()!=CellType.BLANK)&& row.getCell(4).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(4).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????????????????");
                    }
                    if ((row.getCell(5) != null) &&(row.getCell(5).getCellType()!=CellType.BLANK)&& row.getCell(5).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(5).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????????????????");
                    }
                    if ((row.getCell(6) != null) && row.getCell(6).getCellType() != CellType.BLANK && row.getCell(6).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(6).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(6).toString()+"??????????????????");
                    }
                    if ((row.getCell(7) != null) && (row.getCell(7).getCellType()!=CellType.BLANK)&& row.getCell(7).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(7).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(7).toString()+"??????????????????");
                    }
                    if ((row.getCell(8) != null) && row.getCell(8).getCellType() != CellType.BLANK && row.getCell(8).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(8).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(8).toString()+"??????????????????");
                    }
                    if ((row.getCell(9) != null)&& row.getCell(9).getCellType() != CellType.BLANK  &&row.getCell(9).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(9).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????????????????");
                    }
                    if ((row.getCell(10) != null) && row.getCell(10).getCellType() != CellType.BLANK &&row.getCell(10).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(10).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????????????????");
                    }
                    if ((row.getCell(11) != null)&& row.getCell(11).getCellType() != CellType.BLANK  &&row.getCell(11).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(11).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(11).toString()+"??????????????????");
                    }
                    if ((row.getCell(12) != null)&& row.getCell(12).getCellType() != CellType.BLANK  && row.getCell(12).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(12).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????????????????");
                    }
                    if ((row.getCell(13) != null) && row.getCell(13).getCellType() != CellType.BLANK && row.getCell(13).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(13).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(13).toString()+"??????????????????");
                    }
                    if ((row.getCell(14) != null)&& row.getCell(14).getCellType() != CellType.BLANK && row.getCell(14).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(14).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(14).toString()+"??????????????????");
                    }
                    if ((row.getCell(15) != null) && row.getCell(15).getCellType() != CellType.BLANK && row.getCell(15).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(15).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(15).toString()+"??????????????????");
                    }
                    if ((row.getCell(16) != null) && row.getCell(16).getCellType() != CellType.BLANK && row.getCell(16).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(16).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(16).toString()+"??????????????????");
                    }
                }
                if (!errorList.isEmpty()) {
                    System.out.println(errorList);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, errorList);
                }
                for (int j = 0; j < sheet1.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    SupervisionEnFoodBu supervisionEnFoodBu = new SupervisionEnFoodBu();
                    SupervisionEnFoodBuIndex supervisionEnFoodBuIndex = new SupervisionEnFoodBuIndex();
                    XSSFRow row = sheet1.getRow(j);
                    if((row.getCell(1)==null)&&(row.getCell(2)==null))
                    {
                        break;
                    }
                    if(enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)))!=null)
                    {
                        int id= enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        supervisionEnFoodBuIndex.setEnterpriseId(id);
                    }
                    else
                    {
                        int a=j+1;
                        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "????????????????????? ???"+a+"?????????????????????????????????");
                    }
                    supervisionEnFoodBu.setBusinessName(ExcalUtils.handleStringXSSF(row.getCell(0)));
                    supervisionEnFoodBu.setNumber(ExcalUtils.handleStringXSSF(row.getCell(2)));
                    supervisionEnFoodBu.setBusinessFormat(ExcalUtils.handleStringXSSF(row.getCell(3)));
                    supervisionEnFoodBu.setCategory(ExcalUtils.handleStringXSSF(row.getCell(4)));
                    supervisionEnFoodBu.setBusinessNotes(ExcalUtils.handleStringXSSF(row.getCell(5)));
                    supervisionEnFoodBu.setBusinessProject(ExcalUtils.handleStringXSSF(row.getCell(6)));
                    supervisionEnFoodBu.setBusinessAddress(ExcalUtils.handleStringXSSF(row.getCell(7)));
                    supervisionEnFoodBu.setStartTime(ExcalUtils.handleDateXSSF(row.getCell(8)));
                    supervisionEnFoodBu.setEndTime(ExcalUtils.handleDateXSSF(row.getCell(9)));
                    supervisionEnFoodBu.setGiveTime(ExcalUtils.handleDateXSSF(row.getCell(10)));
                    supervisionEnFoodBu.setLicenseAuthority(ExcalUtils.handleStringXSSF(row.getCell(11)));
                    supervisionEnFoodBu.setCheckType(ExcalUtils.handleStringXSSF(row.getCell(12)));
                    supervisionEnFoodBu.setDynamicGrade(ExcalUtils.handleStringXSSF(row.getCell(13)));
                    supervisionEnFoodBu.setYearAssessment(ExcalUtils.handleStringXSSF(row.getCell(14)));
                    supervisionEnFoodBu.setPatrolFrequency(ExcalUtils.handleStringXSSF(row.getCell(15)));
                    supervisionEnFoodBu.setEnterpriseScale(ExcalUtils.handleStringXSSF(row.getCell(16)));
                    supervisionEnFoodBu.setOperator("?????????");
                    supervisionEnFoodBu.setOperateIp("123.123.123");
                    supervisionEnFoodBu.setOperateTime(new Date());
                    if(!supervisionEnFoodBu.getNumber().equals("")) {
                        if(numberMap.get(supervisionEnFoodBu.getNumber())!=null)
                        {
                            //??????????????????????????????????????????????????????????????????
                            int id = numberMap.get(supervisionEnFoodBu.getNumber());
                            supervisionEnFoodBu.setId(id);
                            id = numberFoodBuIndexMap.get(supervisionEnFoodBuIndex.getEnterpriseId());
                            supervisionEnFoodBu.setIndexId(id);
                            supervisionEnFoodBuMapper.updateByPrimaryKey(supervisionEnFoodBu);
                        }
                        else{
                            //??????????????????????????????
                            Date dateNew = new Date();
                            //????????????????????????????????????????????????????????????
                            if(supervisionEnFoodBuIndexMapper.selectByEnterpriseId(supervisionEnFoodBuIndex.getEnterpriseId())!=null) {
                                dateNew=supervisionEnFoodBu.getEndTime();
                                supervisionEnFoodBuIndex=supervisionEnFoodBuIndexMapper.selectByEnterpriseId(supervisionEnFoodBuIndex.getEnterpriseId());
                                //??????????????????????????????????????????????????????????????????
                                if(dateComparedUtil.DateCompared(supervisionEnFoodBuIndex.getEndTime(),dateNew)==1)
                                {
                                    supervisionEnFoodBuIndex.setEndTime(dateNew);
                                }
                                //?????????????????? ?????????????????????A,B???
                                supervisionEnFoodBuIndex.setNumber(supervisionEnFoodBuIndex.getNumber()+","+supervisionEnFoodBu.getNumber());
                                supervisionEnFoodBuIndexMapper.updateByPrimaryKeySelective(supervisionEnFoodBuIndex);
                                supervisionEnFoodBu.setIndexId(supervisionEnFoodBuIndex.getId());
                            }
                            else {
                                supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(supervisionEnFoodBuIndex.getEnterpriseId());
                                supervisionEnterprise.setPermissionType(supervisionEnterprise.getPermissionType() + ",foodBusiness");
                                supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
                                supervisionEnFoodBuIndex.setNumber(supervisionEnFoodBu.getNumber());
                                supervisionEnFoodBuIndex.setEndTime(supervisionEnFoodBu.getEndTime());
                                int indexId=supervisionEnFoodBuIndexMapper.insertSelective(supervisionEnFoodBuIndex);
                                supervisionEnFoodBu.setIndexId(indexId);
                            }
                            supervisionEnFoodBuMapper.insertSelective(supervisionEnFoodBu);
                        }
                    }
                }

                //?????????????????????
                List<SupervisionEnFoodPro> supervisionEnFoodProList1 = supervisionEnFoodProMapper.getAll();
                Map <String,Integer> numberFoodProMap =new HashMap<>();
                for (SupervisionEnFoodPro supervisionEnFoodPro:supervisionEnFoodProList1){
                    numberFoodProMap.put(supervisionEnFoodPro.getNumber(),supervisionEnFoodPro.getId());
                }

                List<SupervisionEnFoodProIndex> supervisionEnFoodProIndexList = supervisionEnFoodProIndexMapper.getAll();
                Map <Integer,Integer> numberFoodBuProIndexMap =new HashMap<>();
                for (SupervisionEnFoodProIndex supervisionEnFoodProIndex:supervisionEnFoodProIndexList){
                    numberFoodBuProIndexMap.put(supervisionEnFoodProIndex.getEnterpriseId(),supervisionEnFoodProIndex.getId());
                }

                XSSFSheet sheet2 = workbook.getSheetAt(2);
                for (int j = 0; j <sheet2.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    XSSFRow titleRow = sheet2.getRow(0);
                    XSSFRow row = sheet2.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    int a = j + 1;
                    if(row.getCell(1).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????");
                    }
                    if(row.getCell(2).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????");
                    }
                    if(row.getCell(9).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????");
                    }
                    if ((row.getCell(0) != null) && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(0).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(0).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????????????????");
                    }
                    if ((row.getCell(1) != null) && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(1).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????????????????");
                    }
                    if ((row.getCell(2) != null) && row.getCell(2).getCellType() != CellType.BLANK  && row.getCell(2).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(2).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????????????????");
                    }
                    if ((row.getCell(3) != null)&& row.getCell(3).getCellType() != CellType.BLANK  && row.getCell(3).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(3).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????????????????");
                    }
                    if ((row.getCell(4) != null) && (row.getCell(4).getCellType()!=CellType.BLANK)&& row.getCell(4).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(4).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????????????????");
                    }
                    if ((row.getCell(5) != null) &&(row.getCell(5).getCellType()!=CellType.BLANK)&& row.getCell(5).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(5).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????????????????");
                    }
                    if ((row.getCell(6) != null) && row.getCell(6).getCellType() != CellType.BLANK && row.getCell(6).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(6).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(6).toString()+"??????????????????");
                    }
                    if ((row.getCell(7) != null) && (row.getCell(7).getCellType()!=CellType.BLANK)&& row.getCell(7).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(7).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(7).toString()+"??????????????????");
                    }
                    if ((row.getCell(8) != null) && row.getCell(8).getCellType() != CellType.BLANK && row.getCell(8).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(8).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(8).toString()+"??????????????????");
                    }
                    if ((row.getCell(9) != null)&& row.getCell(9).getCellType() != CellType.BLANK  &&row.getCell(9).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(9).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????????????????");
                    }
                    if ((row.getCell(10) != null) && row.getCell(10).getCellType() != CellType.BLANK &&row.getCell(10).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(10).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????????????????");
                    }
                    if ((row.getCell(11) != null)&& row.getCell(11).getCellType() != CellType.BLANK  &&row.getCell(11).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(11).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(11).toString()+"??????????????????");
                    }
                    if ((row.getCell(12) != null)&& row.getCell(12).getCellType() != CellType.BLANK  && row.getCell(12).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(12).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????????????????");
                    }
                    if ((row.getCell(13) != null) && row.getCell(13).getCellType() != CellType.BLANK && row.getCell(13).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(13).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(13).toString()+"??????????????????");
                    }
                }
                if (!errorList.isEmpty()) {
                    System.out.println(errorList);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, errorList);
                }
                for (int j = 0; j < sheet2.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    SupervisionEnFoodPro supervisionEnFoodPro = new SupervisionEnFoodPro();
                    SupervisionEnFoodProIndex supervisionEnFoodProIndex = new SupervisionEnFoodProIndex();
                    XSSFRow row = sheet2.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    if(enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)))!=null)
                    {
                        int id= enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        supervisionEnFoodProIndex.setEnterpriseId(id);
                    }
                    else
                    {
                        int a=j+1;
                        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "????????????????????? ???"+a+"?????????????????????????????????");
                    }
                    supervisionEnFoodPro.setBusinessName(ExcalUtils.handleStringXSSF(row.getCell(0)));
                    supervisionEnFoodPro.setNumber(ExcalUtils.handleStringXSSF(row.getCell(2)));
                    supervisionEnFoodPro.setFoodType(ExcalUtils.handleStringXSSF(row.getCell(3)));
                    supervisionEnFoodPro.setBusinessAddress(ExcalUtils.handleStringXSSF(row.getCell(4)));
                    supervisionEnFoodPro.setStartTime(ExcalUtils.handleDateXSSF(row.getCell(5)));
                    supervisionEnFoodPro.setEndTime(ExcalUtils.handleDateXSSF(row.getCell(6)));
                    supervisionEnFoodPro.setGiveTime(ExcalUtils.handleDateXSSF(row.getCell(7)));
                    supervisionEnFoodPro.setLicenseAuthority(ExcalUtils.handleStringXSSF(row.getCell(8)));
                    supervisionEnFoodPro.setCheckType(ExcalUtils.handleStringXSSF(row.getCell(9)));
                    supervisionEnFoodPro.setDynamicGrade(ExcalUtils.handleStringXSSF(row.getCell(10)));
                    supervisionEnFoodPro.setYearAssessment(ExcalUtils.handleStringXSSF(row.getCell(11)));
                    supervisionEnFoodPro.setPatrolFrequency(ExcalUtils.handleStringXSSF(row.getCell(12)));
                    supervisionEnFoodPro.setEnterpriseScale(ExcalUtils.handleStringXSSF(row.getCell(13)));
                    supervisionEnFoodPro.setOperator("?????????");
                    supervisionEnFoodPro.setOperatorIp("123.123.123");
                    supervisionEnFoodPro.setOperatorTime(new Date());
                    if(!supervisionEnFoodPro.getNumber().equals("")) {
                        if(numberFoodProMap.get(supervisionEnFoodPro.getNumber())!=null)
                        {
                            //??????????????????????????????
                            int id = numberFoodProMap.get(supervisionEnFoodPro.getNumber());
                            supervisionEnFoodPro.setId(id);
                            id = numberFoodBuProIndexMap.get(supervisionEnFoodProIndex.getEnterpriseId());
                            supervisionEnFoodPro.setIndexId(id);
                            supervisionEnFoodProMapper.updateByPrimaryKey(supervisionEnFoodPro);
                        }
                        else{
                            Date dateNew = new Date();
                            if(supervisionEnFoodProIndexMapper.selectByEnterpriseId(supervisionEnFoodProIndex.getEnterpriseId())!=null){
                                dateNew=supervisionEnFoodPro.getEndTime();
                                supervisionEnFoodProIndex=supervisionEnFoodProIndexMapper.selectByEnterpriseId(supervisionEnFoodProIndex.getEnterpriseId());
                                if(dateComparedUtil.DateCompared(supervisionEnFoodPro.getEndTime(),dateNew)==1)
                                {
                                    supervisionEnFoodProIndex.setEndTime(dateNew);
                                }
                                supervisionEnFoodProIndex.setNumber(supervisionEnFoodProIndex.getNumber()+","+supervisionEnFoodPro.getNumber());
                                supervisionEnFoodProIndexMapper.updateByPrimaryKeySelective(supervisionEnFoodProIndex);
                                supervisionEnFoodPro.setIndexId(supervisionEnFoodProIndex.getId());
                            }
                            else {
                                supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(supervisionEnFoodProIndex.getEnterpriseId());
                                supervisionEnterprise.setPermissionType(supervisionEnterprise.getPermissionType() + ",foodProduce");
                                supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
                                supervisionEnFoodProIndex.setNumber(supervisionEnFoodPro.getNumber());
                                supervisionEnFoodProIndex.setEndTime(supervisionEnFoodPro.getEndTime());
                                int indexId = supervisionEnFoodProIndexMapper.insertSelective(supervisionEnFoodProIndex);
                                supervisionEnFoodPro.setIndexId(indexId);
                            }
                            supervisionEnFoodProMapper.insertSelective(supervisionEnFoodPro);
                        }
                    }
                }

                //?????????????????????
                supervisionEnFoodProList1 = supervisionEnFoodProMapper.getAll();
                numberFoodProMap =new HashMap<>();
                for (SupervisionEnFoodPro supervisionEnFoodPro:supervisionEnFoodProList1){
                    numberFoodProMap.put(supervisionEnFoodPro.getNumber(),supervisionEnFoodPro.getId());
                }
                List<SupervisionEnProCategory> supervisionEnProCategoryList = supervisionEnProCategoryMapper.getAll();
                Map <Integer,Integer> numberProCategoryMap =new HashMap<>();
                for (SupervisionEnProCategory supervisionEnProCategory:supervisionEnProCategoryList){
                    numberProCategoryMap.put(supervisionEnProCategory.getParentId(),supervisionEnProCategory.getId());
                }
                XSSFSheet sheet3 = workbook.getSheetAt(3);
                for (int j = 0; j <sheet3.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    XSSFRow titleRow = sheet3.getRow(0);
                    XSSFRow row = sheet3.getRow(j);
                    if((row.getCell(0).getCellType()==CellType.BLANK)&&(row.getCell(1).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    int a = j + 1;
                    if(row.getCell(0).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????");
                    }
                    if(row.getCell(1).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????");
                    }

                    if ((row.getCell(0) != null) && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(0).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(0).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????????????????");
                    }
                    if ((row.getCell(1) != null) && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(1).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????????????????");
                    }
                    if ((row.getCell(2) != null) && row.getCell(2).getCellType() != CellType.BLANK  && row.getCell(2).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(2).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????????????????");
                    }
                    if ((row.getCell(3) != null)&& row.getCell(3).getCellType() != CellType.BLANK  && row.getCell(3).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(3).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????????????????");
                    }
                    if ((row.getCell(4) != null) && (row.getCell(4).getCellType()!=CellType.BLANK)&& row.getCell(4).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(4).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????????????????");
                    }
                    if ((row.getCell(5) != null) &&(row.getCell(5).getCellType()!=CellType.BLANK)&& row.getCell(5).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(5).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????????????????");
                    }
                }
                if (!errorList.isEmpty()) {
                    System.out.println(errorList);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, errorList);
                }
                for (int j = 0; j < sheet3.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    SupervisionEnProCategory supervisionEnProCategory = new SupervisionEnProCategory();
                    XSSFRow row = sheet3.getRow(j);
                    if((row.getCell(0).getCellType()==CellType.BLANK)&&(row.getCell(1).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    if(numberFoodProMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)))!=null)
                    {
                        int id= numberFoodProMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        supervisionEnProCategory.setParentId(id);
                    }
                    supervisionEnProCategory.setCategory(ExcalUtils.handleStringXSSF(row.getCell(2)));
                    supervisionEnProCategory.setCode(ExcalUtils.handleStringXSSF(row.getCell(3)));
                    supervisionEnProCategory.setName(ExcalUtils.handleStringXSSF(row.getCell(4)));
                    supervisionEnProCategory.setDetail(ExcalUtils.handleStringXSSF(row.getCell(5)));
                    supervisionEnProCategory.setOperator("?????????");
                    supervisionEnProCategory.setOperateIp("123.123.123");
                    supervisionEnProCategory.setOperateTime(new Date());
                        if(numberProCategoryMap.get(supervisionEnProCategory.getParentId())!=null)
                        {
                            //??????????????????????????????
                            int id = numberProCategoryMap.get(supervisionEnProCategory.getParentId());
                            supervisionEnProCategory.setId(id);
                            supervisionEnProCategoryMapper.updateByPrimaryKey(supervisionEnProCategory);
                        }
                        else{
                            supervisionEnProCategoryMapper.insertSelective(supervisionEnProCategory);
                        }
                }

                //?????????????????????
                List<SupervisionEnDrugsBu> supervisionEnDrugsBuList = supervisionEnDrugsBuMapper.getAll();
                Map <String,Integer> numberDrugsMap =new HashMap<>();
                for (SupervisionEnDrugsBu supervisionEnDrugsBu:supervisionEnDrugsBuList){
                    numberDrugsMap.put(supervisionEnDrugsBu.getNumber(),supervisionEnDrugsBu.getId());
                }
                List<SupervisionEnDrugsBuIndex> supervisionEnDrugsBuIndexList = supervisionEnDrugsBuIndexMapper.getAll();
                Map <Integer,Integer> numberDrugsIndexMap =new HashMap<>();
                for (SupervisionEnDrugsBuIndex supervisionEnDrugsBuIndex:supervisionEnDrugsBuIndexList){
                    numberDrugsIndexMap.put(supervisionEnDrugsBuIndex.getEnterpriseId(),supervisionEnDrugsBuIndex.getId());
                }
                XSSFSheet sheet6 = workbook.getSheetAt(6);
                for (int j = 0; j <sheet6.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    XSSFRow titleRow = sheet6.getRow(0);
                    XSSFRow row = sheet6.getRow(j);
                    XSSFRow nextRow = sheet6.getRow(j+1);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    int a = j + 1;
                    if(row.getCell(1).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????");
                    }
                    if(row.getCell(2).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????");
                    }
                    if(row.getCell(12).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????");
                    }
                    if ((row.getCell(0) != null) && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(0).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(0).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????????????????");
                    }
                    if ((row.getCell(1) != null) && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(1).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????????????????");
                    }
                    if ((row.getCell(2) != null) && row.getCell(2).getCellType() != CellType.BLANK  && row.getCell(2).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(2).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????????????????");
                    }
                    if ((row.getCell(3) != null)&& row.getCell(3).getCellType() != CellType.BLANK  && row.getCell(3).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(3).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????????????????");
                    }
                    if ((row.getCell(4) != null) && (row.getCell(4).getCellType()!=CellType.BLANK)&& row.getCell(4).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(4).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????????????????");
                    }
                    if ((row.getCell(5) != null) &&(row.getCell(5).getCellType()!=CellType.BLANK)&& row.getCell(5).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(5).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????????????????");
                    }
                    if ((row.getCell(6) != null) && row.getCell(6).getCellType() != CellType.BLANK && row.getCell(6).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(6).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(6).toString()+"??????????????????");
                    }
                    if ((row.getCell(7) != null) && (row.getCell(7).getCellType()!=CellType.BLANK)&& row.getCell(7).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(7).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(7).toString()+"??????????????????");
                    }
                    if ((row.getCell(8) != null) && row.getCell(8).getCellType() != CellType.BLANK && row.getCell(8).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(8).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(8).toString()+"??????????????????");
                    }
                    if ((row.getCell(9) != null)&& row.getCell(9).getCellType() != CellType.BLANK  &&row.getCell(9).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(9).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????????????????");
                    }
                    if ((row.getCell(10) != null) && row.getCell(10).getCellType() != CellType.BLANK &&row.getCell(10).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(10).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????????????????");
                    }
                    if ((row.getCell(11) != null)&& row.getCell(11).getCellType() != CellType.BLANK  &&row.getCell(11).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(11).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(11).toString()+"??????????????????");
                    }
                    if ((row.getCell(12) != null)&& row.getCell(12).getCellType() != CellType.BLANK  && row.getCell(12).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(12).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????????????????");
                    }
                    if ((row.getCell(13) != null) && row.getCell(13).getCellType() != CellType.BLANK && row.getCell(13).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(13).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(13).toString()+"??????????????????");
                    }
                    if ((row.getCell(14) != null)&& row.getCell(14).getCellType() != CellType.BLANK && row.getCell(14).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(14).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(14).toString()+"??????????????????");
                    }
                    if ((row.getCell(15) != null) && row.getCell(15).getCellType() != CellType.BLANK && row.getCell(15).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(15).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(15).toString()+"??????????????????");
                    }
                    if ((row.getCell(16) != null) && row.getCell(16).getCellType() != CellType.BLANK && row.getCell(16).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(16).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(16).toString()+"??????????????????");
                    }
                }
                if (!errorList.isEmpty()) {
                    System.out.println(errorList);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, errorList);
                }
                for (int j = 0; j < sheet6.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    SupervisionEnDrugsBu supervisionEnDrugsBu = new SupervisionEnDrugsBu();
                    SupervisionEnDrugsBuIndex supervisionEnDrugsBuIndex = new SupervisionEnDrugsBuIndex();
                    XSSFRow row = sheet6.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    if(enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)))!=null)
                    {
                        int id= enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        supervisionEnDrugsBuIndex.setEnterpriseId(id);
                    }

                    supervisionEnDrugsBu.setBusinessName(ExcalUtils.handleStringXSSF(row.getCell(0)));
                    supervisionEnDrugsBu.setNumber(ExcalUtils.handleStringXSSF(row.getCell(2)));
                    supervisionEnDrugsBu.setOperationMode(ExcalUtils.handleStringXSSF(row.getCell(3)));
                    supervisionEnDrugsBu.setBusinessScope(ExcalUtils.handleStringXSSF(row.getCell(4)));
                    supervisionEnDrugsBu.setEnterprisePerson(ExcalUtils.handleStringXSSF(row.getCell(5)));
                    supervisionEnDrugsBu.setQualityPerson(ExcalUtils.handleStringXSSF(row.getCell(6)));
                    supervisionEnDrugsBu.setBusinessAddress(ExcalUtils.handleStringXSSF(row.getCell(7)));
                    supervisionEnDrugsBu.setStartTime(ExcalUtils.handleDateXSSF(row.getCell(8)));
                    supervisionEnDrugsBu.setEndTime(ExcalUtils.handleDateXSSF(row.getCell(9)));
                    supervisionEnDrugsBu.setGiveTime(ExcalUtils.handleDateXSSF(row.getCell(10)));
                    supervisionEnDrugsBu.setLicenseAuthority(ExcalUtils.handleStringXSSF(row.getCell(11)));
                    supervisionEnDrugsBu.setCheckType(ExcalUtils.handleStringXSSF(row.getCell(12)));
                    supervisionEnDrugsBu.setDynamicGrade(ExcalUtils.handleStringXSSF(row.getCell(13)));
                    supervisionEnDrugsBu.setYearAssessment(ExcalUtils.handleStringXSSF(row.getCell(14)));
                    supervisionEnDrugsBu.setPatrolFrequency(ExcalUtils.handleStringXSSF(row.getCell(15)));
                    supervisionEnDrugsBu.setEnterpriseScale(ExcalUtils.handleStringXSSF(row.getCell(16)));
                    supervisionEnDrugsBu.setOperator("?????????");
                    supervisionEnDrugsBu.setOperatorIp("123.123.123");
                    supervisionEnDrugsBu.setOperatorTime(new Date());
                    if(!supervisionEnDrugsBu.getNumber().equals("")) {
                        if(numberDrugsMap.get(supervisionEnDrugsBu.getNumber())!=null)
                        {
                            //??????????????????????????????
                            int id = numberDrugsMap.get(supervisionEnDrugsBu.getNumber());
                            supervisionEnDrugsBu.setId(id);
                            id=numberDrugsIndexMap.get(supervisionEnDrugsBuIndex.getEnterpriseId());
                            supervisionEnDrugsBu.setIndexId(id);
                            supervisionEnDrugsBuMapper.updateByPrimaryKey(supervisionEnDrugsBu);
                        }
                        else{
                            Date dateNew = new Date();
                            if(supervisionEnDrugsBuIndexMapper.selectByEnterpriseId(supervisionEnDrugsBuIndex.getEnterpriseId())!=null)
                            {
                                dateNew=supervisionEnDrugsBu.getEndTime();
                                supervisionEnDrugsBuIndex=supervisionEnDrugsBuIndexMapper.selectByEnterpriseId(supervisionEnDrugsBuIndex.getEnterpriseId());
                                if(dateComparedUtil.DateCompared(supervisionEnDrugsBuIndex.getEndTime(),dateNew)==1)
                                {
                                    supervisionEnDrugsBuIndex.setEndTime(dateNew);
                                }
                                supervisionEnDrugsBuIndex.setNumber(supervisionEnDrugsBuIndex.getNumber()+","+supervisionEnDrugsBu.getNumber());
                                supervisionEnDrugsBuIndexMapper.updateByPrimaryKeySelective(supervisionEnDrugsBuIndex);
                                supervisionEnDrugsBu.setIndexId(supervisionEnDrugsBuIndex.getId());
                            }
                            else {
                                supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(supervisionEnDrugsBuIndex.getEnterpriseId());
                                supervisionEnterprise.setPermissionType(supervisionEnterprise.getPermissionType() + ",drugsBusiness");
                                supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
                                supervisionEnDrugsBuIndex.setNumber(supervisionEnDrugsBu.getNumber());
                                supervisionEnDrugsBuIndex.setEndTime(supervisionEnDrugsBu.getEndTime());
                                int indexId = supervisionEnDrugsBuIndexMapper.insertSelective(supervisionEnDrugsBuIndex);
                                supervisionEnDrugsBu.setIndexId(indexId);
                            }
                            supervisionEnDrugsBuMapper.insertSelective(supervisionEnDrugsBu);
                        }

                    }
                }

                //?????????????????????
                List<SupervisionEnDrugsPro> supervisionEnDrugsProsList = supervisionEnDrugsProMapper.getAll();
                Map <String,Integer> numberDrugsProMap =new HashMap<>();
                for (SupervisionEnDrugsPro supervisionEnDrugsPro:supervisionEnDrugsProsList){
                    numberDrugsProMap.put(supervisionEnDrugsPro.getNumber(),supervisionEnDrugsPro.getId());
                }
                List<SupervisionEnDrugsProIndex> supervisionEnDrugsProIndexList = supervisionEnDrugsProIndexMapper.getAll();
                Map <Integer,Integer> numberDrugsProIndexMap =new HashMap<>();
                for (SupervisionEnDrugsProIndex supervisionEnDrugsProIndex:supervisionEnDrugsProIndexList){
                    numberDrugsProIndexMap.put(supervisionEnDrugsProIndex.getEnterpriseId(),supervisionEnDrugsProIndex.getId());
                }

                XSSFSheet sheet7 = workbook.getSheetAt(7);
                for (int j = 0; j <sheet7.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    XSSFRow titleRow = sheet7.getRow(0);
                    XSSFRow row = sheet7.getRow(j);
                    XSSFRow nextRow = sheet7.getRow(j+1);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    int a = j + 1;
                    if(row.getCell(1).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????");
                    }
                    if(row.getCell(2).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????");
                    }
                    if(row.getCell(12).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????");
                    }
                    if ((row.getCell(0) != null) && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(0).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(0).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????????????????");
                    }
                    if ((row.getCell(1) != null) && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(1).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????????????????");
                    }
                    if ((row.getCell(2) != null) && row.getCell(2).getCellType() != CellType.BLANK  && row.getCell(2).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(2).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????????????????");
                    }
                    if ((row.getCell(3) != null)&& row.getCell(3).getCellType() != CellType.BLANK  && row.getCell(3).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(3).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????????????????");
                    }
                    if ((row.getCell(4) != null) && (row.getCell(4).getCellType()!=CellType.BLANK)&& row.getCell(4).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(4).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????????????????");
                    }
                    if ((row.getCell(5) != null) &&(row.getCell(5).getCellType()!=CellType.BLANK)&& row.getCell(5).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(5).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????????????????");
                    }
                    if ((row.getCell(6) != null) && row.getCell(6).getCellType() != CellType.BLANK && row.getCell(6).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(6).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(6).toString()+"??????????????????");
                    }
                    if ((row.getCell(7) != null) && (row.getCell(7).getCellType()!=CellType.BLANK)&& row.getCell(7).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(7).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(7).toString()+"??????????????????");
                    }
                    if ((row.getCell(8) != null) && row.getCell(8).getCellType() != CellType.BLANK && row.getCell(8).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(8).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(8).toString()+"??????????????????");
                    }
                    if ((row.getCell(9) != null)&& row.getCell(9).getCellType() != CellType.BLANK  &&row.getCell(9).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(9).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????????????????");
                    }
                    if ((row.getCell(10) != null) && row.getCell(10).getCellType() != CellType.BLANK &&row.getCell(10).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(10).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????????????????");
                    }
                    if ((row.getCell(11) != null)&& row.getCell(11).getCellType() != CellType.BLANK  &&row.getCell(11).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(11).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(11).toString()+"??????????????????");
                    }
                    if ((row.getCell(12) != null)&& row.getCell(12).getCellType() != CellType.BLANK  && row.getCell(12).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(12).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????????????????");
                    }
                    if ((row.getCell(13) != null) && row.getCell(13).getCellType() != CellType.BLANK && row.getCell(13).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(13).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(13).toString()+"??????????????????");
                    }
                    if ((row.getCell(14) != null)&& row.getCell(14).getCellType() != CellType.BLANK && row.getCell(14).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(14).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(14).toString()+"??????????????????");
                    }
                    if ((row.getCell(15) != null) && row.getCell(15).getCellType() != CellType.BLANK && row.getCell(15).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(15).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(15).toString()+"??????????????????");
                    }
                    if ((row.getCell(16) != null) && row.getCell(16).getCellType() != CellType.BLANK && row.getCell(16).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(16).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(16).toString()+"??????????????????");
                    }
                }
                if (!errorList.isEmpty()) {
                    System.out.println(errorList);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, errorList);
                }
                for (int j = 0; j < sheet7.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    SupervisionEnDrugsPro supervisionEnDrugsPro = new SupervisionEnDrugsPro();
                    SupervisionEnDrugsProIndex supervisionEnDrugsProIndex = new SupervisionEnDrugsProIndex();
                    XSSFRow row = sheet7.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    if(enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)))!=null)
                    {
                        int id= enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        supervisionEnDrugsProIndex.setEnterpriseId(id);
                    }
                    else
                    {
                        int a=j+1;
                        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "????????????????????? ???"+a+"?????????????????????????????????");
                    }
                    supervisionEnDrugsPro.setBusinessName(ExcalUtils.handleStringXSSF(row.getCell(0)));
                    supervisionEnDrugsPro.setNumber(ExcalUtils.handleStringXSSF(row.getCell(2)));
                    supervisionEnDrugsPro.setOperationMode(ExcalUtils.handleStringXSSF(row.getCell(3)));
                    supervisionEnDrugsPro.setBusinessScope(ExcalUtils.handleStringXSSF(row.getCell(4)));
                    supervisionEnDrugsPro.setEnterprisePerson(ExcalUtils.handleStringXSSF(row.getCell(5)));
                    supervisionEnDrugsPro.setQualityPerson(ExcalUtils.handleStringXSSF(row.getCell(6)));
                    supervisionEnDrugsPro.setBusinessAddress(ExcalUtils.handleStringXSSF(row.getCell(7)));
                    supervisionEnDrugsPro.setStartTime(ExcalUtils.handleDateXSSF(row.getCell(8)));
                    supervisionEnDrugsPro.setEndTime(ExcalUtils.handleDateXSSF(row.getCell(9)));
                    supervisionEnDrugsPro.setGiveTime(ExcalUtils.handleDateXSSF(row.getCell(10)));
                    supervisionEnDrugsPro.setLicenseAuthority(ExcalUtils.handleStringXSSF(row.getCell(11)));
                    supervisionEnDrugsPro.setCheckType(ExcalUtils.handleStringXSSF(row.getCell(12)));
                    supervisionEnDrugsPro.setDynamicGrade(ExcalUtils.handleStringXSSF(row.getCell(13)));
                    supervisionEnDrugsPro.setYearAssessment(ExcalUtils.handleStringXSSF(row.getCell(14)));
                    supervisionEnDrugsPro.setPatrolFrequency(ExcalUtils.handleStringXSSF(row.getCell(15)));
                    supervisionEnDrugsPro.setEnterpriseScale(ExcalUtils.handleStringXSSF(row.getCell(16)));
                    supervisionEnDrugsPro.setOperator("?????????");
                    supervisionEnDrugsPro.setOperatorIp("123.123.123");
                    supervisionEnDrugsPro.setOperatorTime(new Date());
                    if(!supervisionEnDrugsPro.getNumber().equals("")) {
                        if(numberDrugsProMap.get(supervisionEnDrugsPro.getNumber())!=null)
                        {
                            //??????????????????????????????
                            int id = numberDrugsProMap.get(supervisionEnDrugsPro.getNumber());
                            supervisionEnDrugsPro.setId(id);
                            id=numberDrugsProIndexMap.get(supervisionEnDrugsProIndex.getEnterpriseId());
                            supervisionEnDrugsPro.setIndexId(id);
                            supervisionEnDrugsProMapper.updateByPrimaryKey(supervisionEnDrugsPro);
                        }
                        else{
                            Date dateNew = new Date();
                            if(supervisionEnDrugsProIndexMapper.selectByEnterpriseId(supervisionEnDrugsProIndex.getEnterpriseId())!=null){
                                dateNew=supervisionEnDrugsPro.getEndTime();
                                supervisionEnDrugsProIndex=supervisionEnDrugsProIndexMapper.selectByEnterpriseId(supervisionEnDrugsProIndex.getEnterpriseId());
                                if(dateComparedUtil.DateCompared(supervisionEnDrugsProIndex.getEndTime(),dateNew)==1)
                                {
                                    supervisionEnDrugsProIndex.setEndTime(dateNew);
                                }
                                supervisionEnDrugsProIndex.setNumber(supervisionEnDrugsProIndex.getNumber()+","+supervisionEnDrugsProIndex.getNumber());
                                supervisionEnDrugsProIndexMapper.updateByPrimaryKeySelective(supervisionEnDrugsProIndex);
                                supervisionEnDrugsPro.setIndexId(supervisionEnDrugsProIndex.getId());
                            }
                            else {
                                supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(supervisionEnDrugsProIndex.getEnterpriseId());
                                supervisionEnterprise.setPermissionType(supervisionEnterprise.getPermissionType() + ",drugsProduce");
                                supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
                                supervisionEnDrugsProIndex.setNumber(supervisionEnDrugsPro.getNumber());
                                supervisionEnDrugsProIndex.setEndTime(supervisionEnDrugsPro.getEndTime());
                                int indexId = supervisionEnDrugsProIndexMapper.insertSelective(supervisionEnDrugsProIndex);
                                supervisionEnDrugsPro.setIndexId(indexId);
                            }
                            supervisionEnDrugsProMapper.insertSelective(supervisionEnDrugsPro);
                        }
                    }
                }

                //????????????????????????
                List<SupervisionEnCosmetics> supervisionEnCosmeticsList = supervisionEnCosmeticsMapper.getAll();
                Map <String,Integer> numberCosmeticsMap =new HashMap<>();
                for (SupervisionEnCosmetics supervisionEnCosmetics:supervisionEnCosmeticsList){
                    numberCosmeticsMap.put(supervisionEnCosmetics.getRegisterCode(),supervisionEnCosmetics.getId());
                }
                List<SupervisionEnCosmeticsIndex> supervisionEnCosmeticsIndexList = supervisionEnCosmeticsIndexMapper.getAll();
                Map <Integer,Integer> numberCosmeticsIndexMap =new HashMap<>();
                for (SupervisionEnCosmeticsIndex supervisionEnCosmeticsIndex:supervisionEnCosmeticsIndexList){
                    numberCosmeticsIndexMap.put(supervisionEnCosmeticsIndex.getEnterpriseId(),supervisionEnCosmeticsIndex.getId());
                }
                XSSFSheet sheet10 = workbook.getSheetAt(10);
                for (int j = 0; j <sheet10.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    XSSFRow titleRow = sheet10.getRow(0);
                    XSSFRow row = sheet10.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    int a = j + 1;
                    if(row.getCell(1).getCellType()==CellType.BLANK)
                    {
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????");
                    }
                    if(row.getCell(2).getCellType()==CellType.BLANK)
                    {
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????");
                    }
                    if(row.getCell(10).getCellType()==CellType.BLANK)
                    {
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????");
                    }
                    if ((row.getCell(0) != null) && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(0).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(0).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????????????????");
                    }
                    if ((row.getCell(1) != null) && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(1).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????????????????");
                    }
                    if ((row.getCell(2) != null) && row.getCell(2).getCellType() != CellType.BLANK  && row.getCell(2).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(2).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????????????????");
                    }
                    if ((row.getCell(3) != null)&& row.getCell(3).getCellType() != CellType.BLANK  && row.getCell(3).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(3).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????????????????");
                    }
                    if ((row.getCell(4) != null) && (row.getCell(4).getCellType()!=CellType.BLANK)&& row.getCell(4).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(4).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????????????????");
                    }
                    if ((row.getCell(5) != null) &&(row.getCell(5).getCellType()!=CellType.BLANK)&& row.getCell(5).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(5).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????????????????");
                    }
                    if ((row.getCell(6) != null) && row.getCell(6).getCellType() != CellType.BLANK && row.getCell(6).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(6).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(6).toString()+"??????????????????");
                    }
                    if ((row.getCell(7) != null) && (row.getCell(7).getCellType()!=CellType.BLANK)&& row.getCell(7).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(7).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(7).toString()+"??????????????????");
                    }
                    if ((row.getCell(8) != null) && row.getCell(8).getCellType() != CellType.BLANK && row.getCell(8).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(8).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(8).toString()+"??????????????????");
                    }
                    if ((row.getCell(9) != null)&& row.getCell(9).getCellType() != CellType.BLANK  &&row.getCell(9).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(9).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????????????????");
                    }
                    if ((row.getCell(10) != null) && row.getCell(10).getCellType() != CellType.BLANK &&row.getCell(10).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(10).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????????????????");
                    }
                    if ((row.getCell(11) != null)&& row.getCell(11).getCellType() != CellType.BLANK  &&row.getCell(11).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(11).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(11).toString()+"??????????????????");
                    }
                    if ((row.getCell(12) != null)&& row.getCell(12).getCellType() != CellType.BLANK  && row.getCell(12).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(12).toString(), "??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????????????????");
                    }
                    if ((row.getCell(13) != null) && row.getCell(13).getCellType() != CellType.BLANK && row.getCell(13).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(13).toString(),"??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(13).toString()+"??????????????????");
                    }
                    if ((row.getCell(14) != null)&& row.getCell(14).getCellType() != CellType.BLANK && row.getCell(14).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(14).toString(),"??????????????????");
                        errorList.add("???????????????????????? ???" + a + "???"+titleRow.getCell(14).toString()+"??????????????????");
                    }
                }
                if (!errorList.isEmpty()) {
                    System.out.println(errorList);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, errorList);
                }
                for (int j = 0; j < sheet10.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    SupervisionEnCosmetics supervisionEnCosmetics = new SupervisionEnCosmetics();
                    SupervisionEnCosmeticsIndex supervisionEnCosmeticsIndex = new SupervisionEnCosmeticsIndex();
                    XSSFRow row = sheet10.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    if(enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)))!=null)
                    {
                        int id= enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        supervisionEnCosmeticsIndex.setEnterpriseId(id);
                    }
                    else
                    {
                        int a=j+1;
                        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "???????????????????????? ???"+a+"?????????????????????????????????");
                    }
                    supervisionEnCosmetics.setBusinessName(ExcalUtils.handleStringXSSF(row.getCell(0)));
                    supervisionEnCosmetics.setRegisterCode(ExcalUtils.handleStringXSSF(row.getCell(2)));
                    supervisionEnCosmetics.setLicenseProject(ExcalUtils.handleStringXSSF(row.getCell(3)));
                    supervisionEnCosmetics.setQualityPerson(ExcalUtils.handleStringXSSF(row.getCell(4)));
                    supervisionEnCosmetics.setBusinessAddress(ExcalUtils.handleStringXSSF(row.getCell(5)));
                    supervisionEnCosmetics.setStartTime(ExcalUtils.handleDateXSSF(row.getCell(6)));
                    supervisionEnCosmetics.setEndTime(ExcalUtils.handleDateXSSF(row.getCell(7)));
                    supervisionEnCosmetics.setGiveTime(ExcalUtils.handleDateXSSF(row.getCell(8)));
                    supervisionEnCosmetics.setLicenseAuthority(ExcalUtils.handleStringXSSF(row.getCell(9)));
                    supervisionEnCosmetics.setCheckType(ExcalUtils.handleStringXSSF(row.getCell(10)));
                    supervisionEnCosmetics.setDynamicGrade(ExcalUtils.handleStringXSSF(row.getCell(11)));
                    supervisionEnCosmetics.setYearAssessment(ExcalUtils.handleStringXSSF(row.getCell(12)));
                    supervisionEnCosmetics.setPatrolFrequency(ExcalUtils.handleStringXSSF(row.getCell(13)));
                    supervisionEnCosmetics.setEnterpriseScale(ExcalUtils.handleStringXSSF(row.getCell(14)));
                    supervisionEnCosmetics.setOperator("?????????");
                    supervisionEnCosmetics.setOperateIp("123.123.123");
                    supervisionEnCosmetics.setOperateTime(new Date());
                    if(!supervisionEnCosmetics.getRegisterCode().equals("")) {
                        if(numberCosmeticsMap.get(supervisionEnCosmetics.getRegisterCode())!=null)
                        {
                            //??????????????????????????????
                            int id = numberCosmeticsMap.get(supervisionEnCosmetics.getRegisterCode());
                            supervisionEnCosmetics.setId(id);
                            id=numberCosmeticsIndexMap.get(supervisionEnCosmeticsIndex.getEnterpriseId());
                            supervisionEnCosmetics.setIndexId(id);
                            supervisionEnCosmeticsMapper.updateByPrimaryKey(supervisionEnCosmetics);
                        }
                        else{
                            Date dateNew = new Date();
                            if(supervisionEnCosmeticsIndexMapper.selectByEnterpriseId(supervisionEnCosmeticsIndex.getEnterpriseId())!=null)
                            {
                                dateNew=supervisionEnCosmetics.getEndTime();
                                supervisionEnCosmeticsIndex=supervisionEnCosmeticsIndexMapper.selectByEnterpriseId(supervisionEnCosmeticsIndex.getEnterpriseId());
                                if(dateComparedUtil.DateCompared(supervisionEnCosmeticsIndex.getEndTime(),dateNew)==1)
                                {
                                    supervisionEnCosmeticsIndex.setEndTime(dateNew);
                                }
                                supervisionEnCosmeticsIndex.setNumber(supervisionEnCosmeticsIndex.getNumber()+","+supervisionEnCosmetics.getRegisterCode());
                                supervisionEnCosmeticsIndexMapper.updateByPrimaryKeySelective(supervisionEnCosmeticsIndex);
                                supervisionEnCosmetics.setIndexId(supervisionEnCosmeticsIndex.getId());
                            }
                            else {
                                supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(supervisionEnCosmeticsIndex.getEnterpriseId());
                                supervisionEnterprise.setPermissionType(supervisionEnterprise.getPermissionType() + ",cosmeticsUse");
                                supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
                                supervisionEnCosmeticsIndex.setNumber(supervisionEnCosmetics.getRegisterCode());
                                supervisionEnCosmeticsIndex.setEndTime(supervisionEnCosmetics.getEndTime());
                                int indexId = supervisionEnCosmeticsIndexMapper.insertSelective(supervisionEnCosmeticsIndex);
                                supervisionEnCosmetics.setIndexId(indexId);
                            }
                            supervisionEnCosmeticsMapper.insertSelective(supervisionEnCosmetics);
                        }
                    }
                }

                //???????????????????????????
                List<SupervisionEnMedicalBu> supervisionEnMedicalBuList = supervisionEnMedicalBuMapper.getAll();
                Map <String,Integer> numberMedicalMap =new HashMap<>();
                for (SupervisionEnMedicalBu supervisionEnMedicalBu:supervisionEnMedicalBuList){
                    numberMedicalMap.put(supervisionEnMedicalBu.getRegisterNumber(),supervisionEnMedicalBu.getId());
                }
                List<SupervisionEnMedicalBuIndex> supervisionEnMedicalBuIndexList = supervisionEnMedicalBuIndexMapper.getAll();
                Map <Integer,Integer> numberMedicalIndexMap =new HashMap<>();
                for (SupervisionEnMedicalBuIndex supervisionEnMedicalBuIndex:supervisionEnMedicalBuIndexList){
                    numberMedicalIndexMap.put(supervisionEnMedicalBuIndex.getEnterpriseId(),supervisionEnMedicalBuIndex.getId());
                }
                XSSFSheet sheet8 = workbook.getSheetAt(8);
                for (int j = 0; j <sheet8.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    XSSFRow titleRow = sheet8.getRow(0);
                    XSSFRow row = sheet8.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    int a = j + 1;
                    if(row.getCell(1).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????");
                    }
                    if(row.getCell(2).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????");
                    }
                    if(row.getCell(10).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????");
                    }
                    if ((row.getCell(0) != null) && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(0).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(0).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????????????????");
                    }
                    if ((row.getCell(1) != null) && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(1).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????????????????");
                    }
                    if ((row.getCell(2) != null) && row.getCell(2).getCellType() != CellType.BLANK  && row.getCell(2).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(2).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????????????????");
                    }
                    if ((row.getCell(3) != null)&& row.getCell(3).getCellType() != CellType.BLANK  && row.getCell(3).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(3).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????????????????");
                    }
                    if ((row.getCell(4) != null) && (row.getCell(4).getCellType()!=CellType.BLANK)&& row.getCell(4).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(4).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????????????????");
                    }
                    if ((row.getCell(5) != null) &&(row.getCell(5).getCellType()!=CellType.BLANK)&& row.getCell(5).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(5).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????????????????");
                    }
                    if ((row.getCell(6) != null) && row.getCell(6).getCellType() != CellType.BLANK && row.getCell(6).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(6).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(6).toString()+"??????????????????");
                    }
                    if ((row.getCell(7) != null) && (row.getCell(7).getCellType()!=CellType.BLANK)&& row.getCell(7).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(7).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(7).toString()+"??????????????????");
                    }
                    if ((row.getCell(8) != null) && row.getCell(8).getCellType() != CellType.BLANK && row.getCell(8).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(8).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(8).toString()+"??????????????????");
                    }
                    if ((row.getCell(9) != null)&& row.getCell(9).getCellType() != CellType.BLANK  &&row.getCell(9).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(9).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????????????????");
                    }
                    if ((row.getCell(10) != null) && row.getCell(10).getCellType() != CellType.BLANK &&row.getCell(10).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(10).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????????????????");
                    }
                    if ((row.getCell(11) != null)&& row.getCell(11).getCellType() != CellType.BLANK  &&row.getCell(11).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(11).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(11).toString()+"??????????????????");
                    }
                    if ((row.getCell(12) != null)&& row.getCell(12).getCellType() != CellType.BLANK  && row.getCell(12).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(12).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????????????????");
                    }
                    if ((row.getCell(13) != null) && row.getCell(13).getCellType() != CellType.BLANK && row.getCell(13).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(13).toString(),"??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(13).toString()+"??????????????????");
                    }
                    if ((row.getCell(14) != null)&& row.getCell(14).getCellType() != CellType.BLANK && row.getCell(14).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(14).toString(),"??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(14).toString()+"??????????????????");
                    }
                }
                if (!errorList.isEmpty()) {
                    System.out.println(errorList);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, errorList);
                }
                for (int j = 0; j < sheet8.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    SupervisionEnMedicalBu supervisionEnMedicalBu = new SupervisionEnMedicalBu();
                    SupervisionEnMedicalBuIndex supervisionEnMedicalBuIndex = new SupervisionEnMedicalBuIndex();
                    XSSFRow row = sheet8.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    if(enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)))!=null)
                    {
                        int id= enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        supervisionEnMedicalBuIndex.setEnterpriseId(id);
                    }
                    else
                    {
                        int a=j+1;
                        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "??????????????????????????? ???"+a+"?????????????????????????????????");
                    }
                    supervisionEnMedicalBu.setBusinessName(ExcalUtils.handleStringXSSF(row.getCell(0)));
                    supervisionEnMedicalBu.setRegisterNumber(ExcalUtils.handleStringXSSF(row.getCell(2)));
                    supervisionEnMedicalBu.setCategory(ExcalUtils.handleStringXSSF(row.getCell(3)));
                    supervisionEnMedicalBu.setMedicalSubject(ExcalUtils.handleStringXSSF(row.getCell(4)));
                    supervisionEnMedicalBu.setBusinessAddress(ExcalUtils.handleStringXSSF(row.getCell(5)));
                    supervisionEnMedicalBu.setStartTime(ExcalUtils.handleDateXSSF(row.getCell(6)));
                    supervisionEnMedicalBu.setEndTime(ExcalUtils.handleDateXSSF(row.getCell(7)));
                    supervisionEnMedicalBu.setGiveTime(ExcalUtils.handleDateXSSF(row.getCell(8)));
                    supervisionEnMedicalBu.setLssueAuthority(ExcalUtils.handleStringXSSF(row.getCell(9)));
                    supervisionEnMedicalBu.setCheckType(ExcalUtils.handleStringXSSF(row.getCell(10)));
                    supervisionEnMedicalBu.setDynamicGrade(ExcalUtils.handleStringXSSF(row.getCell(11)));
                    supervisionEnMedicalBu.setYearAssessment(ExcalUtils.handleStringXSSF(row.getCell(12)));
                    supervisionEnMedicalBu.setPatrolFrequency(ExcalUtils.handleStringXSSF(row.getCell(13)));
                    supervisionEnMedicalBu.setEnterpriseScale(ExcalUtils.handleStringXSSF(row.getCell(14)));
                    supervisionEnMedicalBu.setOperator("?????????");
                    supervisionEnMedicalBu.setOperateIp("123.123.123");
                    supervisionEnMedicalBu.setOperateTime(new Date());
                    if(!supervisionEnMedicalBu.getRegisterNumber().equals("")) {
                        if(numberMedicalMap.get(supervisionEnMedicalBu.getRegisterNumber())!=null)
                        {
                            //??????????????????????????????
                            int id = numberMedicalMap.get(supervisionEnMedicalBu.getRegisterNumber());
                            supervisionEnMedicalBu.setId(id);
                            id=numberMedicalIndexMap.get(supervisionEnMedicalBuIndex.getEnterpriseId());
                            supervisionEnMedicalBu.setIndexId(id);
                            supervisionEnMedicalBuMapper.updateByPrimaryKey(supervisionEnMedicalBu);
                        }
                        else{
                            Date dateNew = new Date();
                            if(supervisionEnMedicalBuIndexMapper.selectByEnterpriseId(supervisionEnMedicalBuIndex.getEnterpriseId())!=null) {
                                dateNew = supervisionEnMedicalBu.getEndTime();
                                if(dateComparedUtil.DateCompared(supervisionEnMedicalBuIndex.getEndTime(),dateNew)==1)
                                {
                                    supervisionEnMedicalBuIndex.setEndTime(dateNew);
                                }
                                supervisionEnMedicalBuIndex.setNumber(supervisionEnMedicalBuIndex.getNumber()+","+supervisionEnMedicalBu.getRegisterNumber());
                                supervisionEnMedicalBuIndexMapper.updateByPrimaryKeySelective(supervisionEnMedicalBuIndex);
                                supervisionEnMedicalBu.setIndexId(supervisionEnMedicalBuIndex.getId());
                            }
                            else {
                                supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(supervisionEnMedicalBuIndex.getEnterpriseId());
                                supervisionEnterprise.setPermissionType(supervisionEnterprise.getPermissionType() + ",medicalBusiness");
                                supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
                                supervisionEnMedicalBuIndex.setNumber(supervisionEnMedicalBu.getRegisterNumber());
                                supervisionEnMedicalBuIndex.setEndTime(supervisionEnMedicalBu.getEndTime());
                                int indexId = supervisionEnMedicalBuIndexMapper.insertSelective(supervisionEnMedicalBuIndex);
                                supervisionEnMedicalBu.setIndexId(indexId);
                            }
                            supervisionEnMedicalBuMapper.insertSelective(supervisionEnMedicalBu);
                        }
                    }
                }

                //???????????????????????????
                List<SupervisionEnMedicalPro> supervisionEnMedicalProList = supervisionEnMedicalProMapper.getAll();
                Map <String,Integer> numberMedicalProMap =new HashMap<>();
                for (SupervisionEnMedicalPro supervisionEnMedicalPro:supervisionEnMedicalProList){
                    numberMedicalProMap.put(supervisionEnMedicalPro.getRegisterNumber(),supervisionEnMedicalPro.getId());
                }
                List<SupervisionEnMedicalProIndex> supervisionEnMedicalProIndexList = supervisionEnMedicalProIndexMapper.getAll();
                Map <Integer,Integer> numberMedicalProIndexMap =new HashMap<>();
                for (SupervisionEnMedicalProIndex supervisionEnMedicalProIndex:supervisionEnMedicalProIndexList){
                    numberMedicalProIndexMap.put(supervisionEnMedicalProIndex.getEnterpriseId(),supervisionEnMedicalProIndex.getId());
                }
                XSSFSheet sheet9 = workbook.getSheetAt(9);
                for (int j = 0; j <sheet9.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    XSSFRow titleRow = sheet9.getRow(0);
                    XSSFRow row = sheet9.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    int a = j + 1;
                    if(row.getCell(1).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????");
                    }
                    if(row.getCell(2).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????");
                    }
                    if(row.getCell(10).getCellType()==CellType.BLANK)
                    {
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????");
                    }
                    if ((row.getCell(0) != null) && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(0).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(0).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????????????????");
                    }
                    if ((row.getCell(1) != null) && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(1).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????????????????");
                    }
                    if ((row.getCell(2) != null) && row.getCell(2).getCellType() != CellType.BLANK  && row.getCell(2).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(2).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????????????????");
                    }
                    if ((row.getCell(3) != null)&& row.getCell(3).getCellType() != CellType.BLANK  && row.getCell(3).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(3).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????????????????");
                    }
                    if ((row.getCell(4) != null) && (row.getCell(4).getCellType()!=CellType.BLANK)&& row.getCell(4).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(4).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????????????????");
                    }
                    if ((row.getCell(5) != null) &&(row.getCell(5).getCellType()!=CellType.BLANK)&& row.getCell(5).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(5).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????????????????");
                    }
                    if ((row.getCell(6) != null) && row.getCell(6).getCellType() != CellType.BLANK && row.getCell(6).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(6).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(6).toString()+"??????????????????");
                    }
                    if ((row.getCell(7) != null) && (row.getCell(7).getCellType()!=CellType.BLANK)&& row.getCell(7).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(7).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(7).toString()+"??????????????????");
                    }
                    if ((row.getCell(8) != null) && row.getCell(8).getCellType() != CellType.BLANK && row.getCell(8).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(8).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(8).toString()+"??????????????????");
                    }
                    if ((row.getCell(9) != null)&& row.getCell(9).getCellType() != CellType.BLANK  &&row.getCell(9).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(9).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????????????????");
                    }
                    if ((row.getCell(10) != null) && row.getCell(10).getCellType() != CellType.BLANK &&row.getCell(10).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(10).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????????????????");
                    }
                    if ((row.getCell(11) != null)&& row.getCell(11).getCellType() != CellType.BLANK  &&row.getCell(11).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(11).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(11).toString()+"??????????????????");
                    }
                    if ((row.getCell(12) != null)&& row.getCell(12).getCellType() != CellType.BLANK  && row.getCell(12).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(12).toString(), "??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????????????????");
                    }
                    if ((row.getCell(13) != null) && row.getCell(13).getCellType() != CellType.BLANK && row.getCell(13).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(13).toString(),"??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(13).toString()+"??????????????????");
                    }
                    if ((row.getCell(14) != null)&& row.getCell(14).getCellType() != CellType.BLANK && row.getCell(14).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(14).toString(),"??????????????????");
                        errorList.add("??????????????????????????? ???" + a + "???"+titleRow.getCell(14).toString()+"??????????????????");
                    }
                }
                if (!errorList.isEmpty()) {
                    System.out.println(errorList);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, errorList);
                }
                for (int j = 0; j < sheet9.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    SupervisionEnMedicalPro supervisionEnMedicalPro = new SupervisionEnMedicalPro();
                    SupervisionEnMedicalProIndex supervisionEnMedicalProIndex = new SupervisionEnMedicalProIndex();
                    XSSFRow row = sheet9.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    if(enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)))!=null)
                    {
                        int id= enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        supervisionEnMedicalProIndex.setEnterpriseId(id);
                    }
                    else
                    {
                        int a=j+1;
                        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "??????????????????????????? ???"+a+"?????????????????????????????????");
                    }
                    supervisionEnMedicalPro.setBusinessName(ExcalUtils.handleStringXSSF(row.getCell(0)));
                    supervisionEnMedicalPro.setRegisterNumber(ExcalUtils.handleStringXSSF(row.getCell(2)));
                    supervisionEnMedicalPro.setProduceScale(ExcalUtils.handleStringXSSF(row.getCell(3)));
                    supervisionEnMedicalPro.setEnterprisePerson(ExcalUtils.handleStringXSSF(row.getCell(4)));
                    supervisionEnMedicalPro.setBusinessAddress(ExcalUtils.handleStringXSSF(row.getCell(5)));
                    supervisionEnMedicalPro.setStartTime(ExcalUtils.handleDateXSSF(row.getCell(6)));
                    supervisionEnMedicalPro.setEndTime(ExcalUtils.handleDateXSSF(row.getCell(7)));
                    supervisionEnMedicalPro.setGiveTime(ExcalUtils.handleDateXSSF(row.getCell(8)));
                    supervisionEnMedicalPro.setLssueAuthority(ExcalUtils.handleStringXSSF(row.getCell(9)));
                    supervisionEnMedicalPro.setCheckType(ExcalUtils.handleStringXSSF(row.getCell(10)));
                    supervisionEnMedicalPro.setDynamicGrade(ExcalUtils.handleStringXSSF(row.getCell(11)));
                    supervisionEnMedicalPro.setYearAssessment(ExcalUtils.handleStringXSSF(row.getCell(12)));
                    supervisionEnMedicalPro.setPatrolFrequency(ExcalUtils.handleStringXSSF(row.getCell(13)));
                    supervisionEnMedicalPro.setEnterpriseScale(ExcalUtils.handleStringXSSF(row.getCell(14)));
                    supervisionEnMedicalPro.setOperator("?????????");
                    supervisionEnMedicalPro.setOperateIp("123.123.123");
                    supervisionEnMedicalPro.setOperateTime(new Date());
                    if(!supervisionEnMedicalPro.getRegisterNumber().equals("")) {
                        if(numberMedicalProMap.get(supervisionEnMedicalPro.getRegisterNumber())!=null)
                        {
                            //??????????????????????????????
                            int id = numberMedicalProMap.get(supervisionEnMedicalPro.getRegisterNumber());
                            supervisionEnMedicalPro.setId(id);
                            id = numberMedicalProIndexMap.get(supervisionEnMedicalProIndex.getEnterpriseId());
                            supervisionEnMedicalPro.setIndexId(id);
                            supervisionEnMedicalProMapper.updateByPrimaryKey(supervisionEnMedicalPro);
                        }
                        else{
                            Date dateNew = new Date();
                            if(supervisionEnMedicalProIndexMapper.selectByEnterpriseId(supervisionEnMedicalProIndex.getEnterpriseId())!=null){
                                dateNew = supervisionEnMedicalPro.getEndTime();
                                supervisionEnMedicalProIndex=supervisionEnMedicalProIndexMapper.selectByEnterpriseId(supervisionEnMedicalProIndex.getEnterpriseId());
                                if(dateComparedUtil.DateCompared(supervisionEnMedicalProIndex.getEndTime(),dateNew)==1)
                                {
                                    supervisionEnMedicalProIndex.setEndTime(dateNew);
                                }
                                supervisionEnMedicalProIndex.setNumber(supervisionEnMedicalProIndex.getNumber()+","+supervisionEnMedicalPro.getRegisterNumber());
                                supervisionEnMedicalProIndexMapper.updateByPrimaryKeySelective(supervisionEnMedicalProIndex);
                                supervisionEnMedicalPro.setIndexId(supervisionEnMedicalProIndex.getId());
                            }
                            else{
                                supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(supervisionEnMedicalProIndex.getEnterpriseId());
                                supervisionEnterprise.setPermissionType(supervisionEnterprise.getPermissionType() + ",medicalProduce");
                                supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
                                supervisionEnMedicalProIndex.setNumber(supervisionEnMedicalPro.getRegisterNumber());
                                supervisionEnMedicalProIndex.setEndTime(supervisionEnMedicalPro.getEndTime());
                                int indexId=supervisionEnMedicalProIndexMapper.insertSelective(supervisionEnMedicalProIndex);
                                supervisionEnMedicalPro.setIndexId(indexId);
                            }
                            supervisionEnMedicalProMapper.insertSelective(supervisionEnMedicalPro);

                        }
                    }
                }

                //?????????
                List<SupervisionEnSmallCater> supervisionEnSmallCaterList = supervisionEnSmallCaterMapper.getAll();
                Map <String,Integer> numberSmallCaterMap =new HashMap<>();
                for (SupervisionEnSmallCater supervisionEnSmallCater:supervisionEnSmallCaterList){
                    numberSmallCaterMap.put(supervisionEnSmallCater.getRegisterNumber(),supervisionEnSmallCater.getId());
                }
                List<SupervisionEnSmallCaterIndex> supervisionEnSmallCaterIndexList = supervisionEnSmallCaterIndexMapper.getAll();
                Map <Integer,Integer> numberSmallCaterIndexMap =new HashMap<>();
                for (SupervisionEnSmallCaterIndex supervisionEnSmallCaterIndex:supervisionEnSmallCaterIndexList){
                    numberSmallCaterIndexMap.put(supervisionEnSmallCaterIndex.getEnterpriseId(),supervisionEnSmallCaterIndex.getId());
                }
                XSSFSheet sheet4 = workbook.getSheetAt(4);
                for (int j = 0; j <sheet4.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    XSSFRow titleRow = sheet4.getRow(0);
                    XSSFRow row = sheet4.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    int a = j + 1;
                    if(row.getCell(1).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????");
                    }
                    if(row.getCell(2).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????");
                    }
                    if(row.getCell(10).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????");
                    }
                    if ((row.getCell(0) != null) && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(0).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(0).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????????????????");
                    }
                    if ((row.getCell(1) != null) && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(1).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????????????????");
                    }
                    if ((row.getCell(2) != null) && row.getCell(2).getCellType() != CellType.BLANK  && row.getCell(2).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(2).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????????????????");
                    }
                    if ((row.getCell(3) != null)&& row.getCell(3).getCellType() != CellType.BLANK  && row.getCell(3).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(3).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????????????????");
                    }
                    if ((row.getCell(4) != null) && (row.getCell(4).getCellType()!=CellType.BLANK)&& row.getCell(4).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(4).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????????????????");
                    }
                    if ((row.getCell(5) != null) &&(row.getCell(5).getCellType()!=CellType.BLANK)&& row.getCell(5).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(5).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????????????????");
                    }
                    if ((row.getCell(6) != null) && row.getCell(6).getCellType() != CellType.BLANK && row.getCell(6).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(6).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(6).toString()+"??????????????????");
                    }
                    if ((row.getCell(7) != null) && (row.getCell(7).getCellType()!=CellType.BLANK)&& row.getCell(7).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(7).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(7).toString()+"??????????????????");
                    }
                    if ((row.getCell(8) != null) && row.getCell(8).getCellType() != CellType.BLANK && row.getCell(8).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(8).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(8).toString()+"??????????????????");
                    }
                    if ((row.getCell(9) != null)&& row.getCell(9).getCellType() != CellType.BLANK  &&row.getCell(9).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(9).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????????????????");
                    }
                    if ((row.getCell(10) != null) && row.getCell(10).getCellType() != CellType.BLANK &&row.getCell(10).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(10).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????????????????");
                    }
                    if ((row.getCell(11) != null)&& row.getCell(11).getCellType() != CellType.BLANK  &&row.getCell(11).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(11).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(11).toString()+"??????????????????");
                    }
                    if ((row.getCell(12) != null)&& row.getCell(12).getCellType() != CellType.BLANK  && row.getCell(12).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(12).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????????????????");
                    }
                    if ((row.getCell(13) != null) && row.getCell(13).getCellType() != CellType.BLANK && row.getCell(13).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(13).toString(),"??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(13).toString()+"??????????????????");
                    }
                    if ((row.getCell(14) != null)&& row.getCell(14).getCellType() != CellType.BLANK && row.getCell(14).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(14).toString(),"??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(14).toString()+"??????????????????");
                    }
                }
                if (!errorList.isEmpty()) {
                    System.out.println(errorList);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, errorList);
                }
                for (int j = 0; j < sheet4.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    SupervisionEnSmallCater supervisionEnSmallCater = new SupervisionEnSmallCater();
                    SupervisionEnSmallCaterIndex supervisionEnSmallCaterIndex = new SupervisionEnSmallCaterIndex();
                    XSSFRow row = sheet9.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    if(enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)))!=null)
                    {
                        int id= enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        supervisionEnSmallCaterIndex.setEnterpriseId(id);
                    }
                    else
                    {
                        int a=j+1;
                        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "????????? ???"+a+"?????????????????????????????????");
                    }
                    supervisionEnSmallCater.setBusinessName(ExcalUtils.handleStringXSSF(row.getCell(0)));
                    supervisionEnSmallCater.setRegisterNumber(ExcalUtils.handleStringXSSF(row.getCell(2)));
                    supervisionEnSmallCater.setMainCategory(ExcalUtils.handleStringXSSF(row.getCell(3)));
                    supervisionEnSmallCater.setMainSubject(ExcalUtils.handleStringXSSF(row.getCell(4)));
                    supervisionEnSmallCater.setBusinessAddress(ExcalUtils.handleStringXSSF(row.getCell(5)));
                    supervisionEnSmallCater.setStartTime(ExcalUtils.handleDateXSSF(row.getCell(6)));
                    supervisionEnSmallCater.setEndTime(ExcalUtils.handleDateXSSF(row.getCell(7)));
                    supervisionEnSmallCater.setGiveTime(ExcalUtils.handleDateXSSF(row.getCell(8)));
                    supervisionEnSmallCater.setLssueAuthority(ExcalUtils.handleStringXSSF(row.getCell(9)));
                    supervisionEnSmallCater.setCheckType(ExcalUtils.handleStringXSSF(row.getCell(10)));
                    supervisionEnSmallCater.setDynamicGrade(ExcalUtils.handleStringXSSF(row.getCell(11)));
                    supervisionEnSmallCater.setYearAssessment(ExcalUtils.handleStringXSSF(row.getCell(12)));
                    supervisionEnSmallCater.setPatrolFrequency(ExcalUtils.handleStringXSSF(row.getCell(13)));
                    supervisionEnSmallCater.setEnterpriseScale(ExcalUtils.handleStringXSSF(row.getCell(14)));
                    supervisionEnSmallCater.setOperator("?????????");
                    supervisionEnSmallCater.setOperateTime(new Date());
                    supervisionEnSmallCater.setOperateIp("123.123.123");
                    if(!supervisionEnSmallCater.getRegisterNumber().equals("")) {
                        if(numberSmallCaterMap.get(supervisionEnSmallCater.getRegisterNumber())!=null)
                        {
                            //??????????????????????????????
                            int id = numberSmallCaterMap.get(supervisionEnSmallCater.getRegisterNumber());
                            supervisionEnSmallCater.setId(id);
                            id= numberSmallCaterIndexMap.get(supervisionEnSmallCaterIndex.getEnterpriseId());
                            supervisionEnSmallCater.setIndexId(id);
                            supervisionEnSmallCaterMapper.updateByPrimaryKey(supervisionEnSmallCater);
                        }
                        else{
                            Date dateNew = new Date();
                            if(supervisionEnSmallCaterIndexMapper.selectByEnterpriseId(supervisionEnSmallCaterIndex.getEnterpriseId())!=null){
                                dateNew=supervisionEnSmallCater.getEndTime();
                                supervisionEnSmallCaterIndex=supervisionEnSmallCaterIndexMapper.selectByEnterpriseId(supervisionEnSmallCaterIndex.getEnterpriseId());
                                if(dateComparedUtil.DateCompared(supervisionEnSmallCaterIndex.getEndTime(),dateNew)==1)
                                {
                                    supervisionEnSmallCaterIndex.setEndTime(dateNew);
                                }
                                supervisionEnSmallCaterIndex.setNumber(supervisionEnSmallCaterIndex.getNumber()+","+supervisionEnSmallCater.getRegisterNumber());
                                supervisionEnSmallCater.setIndexId(supervisionEnSmallCaterIndex.getId());
                            }
                            else {
                                supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(supervisionEnSmallCaterIndex.getEnterpriseId());
                                supervisionEnterprise.setPermissionType(supervisionEnterprise.getPermissionType() + ",smallCater");
                                supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
                                supervisionEnSmallCaterIndex.setNumber(supervisionEnSmallCater.getRegisterNumber());
                                supervisionEnSmallCaterIndex.setEndTime(supervisionEnSmallCater.getEndTime());
                                int indexId = supervisionEnSmallCaterIndexMapper.insertSelective(supervisionEnSmallCaterIndex);
                                supervisionEnSmallCater.setIndexId(indexId);
                            }
                            supervisionEnSmallCaterMapper.insertSelective(supervisionEnSmallCater);
                        }
                    }
                }

                //?????????
                List<SupervisionEnSmallWorkshop> supervisionEnSmallWorkshopList = supervisionEnSmallWorkshopMapper.getAll();
                Map <String,Integer> numberSmallWorkshopMap =new HashMap<>();
                for (SupervisionEnSmallWorkshop supervisionEnSmallWorkshop:supervisionEnSmallWorkshopList){
                    numberSmallWorkshopMap.put(supervisionEnSmallWorkshop.getRegisterNumber(),supervisionEnSmallWorkshop.getId());
                }
                List<SupervisionEnSmallWorkshopIndex> supervisionEnSmallWorkshopIndexList = supervisionEnSmallWorkshopIndexMapper.getAll();
                Map <Integer,Integer> numberSmallWorkshopIndexMap =new HashMap<>();
                for (SupervisionEnSmallWorkshopIndex supervisionEnSmallWorkshopIndex:supervisionEnSmallWorkshopIndexList){
                    numberSmallWorkshopIndexMap.put(supervisionEnSmallWorkshopIndex.getEnterpriseId(),supervisionEnSmallWorkshopIndex.getId());
                }
                XSSFSheet sheet5 = workbook.getSheetAt(5);
                for (int j = 0; j <sheet5.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    XSSFRow titleRow = sheet5.getRow(0);
                    XSSFRow row = sheet5.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    int a = j + 1;
                    if(row.getCell(1).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????");
                    }
                    if(row.getCell(2).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????");
                    }
                    if(row.getCell(10).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????");
                    }
                    if ((row.getCell(0) != null) && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(0).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(0).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????????????????");
                    }
                    if ((row.getCell(1) != null) && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(1).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????????????????");
                    }
                    if ((row.getCell(2) != null) && row.getCell(2).getCellType() != CellType.BLANK  && row.getCell(2).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(2).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????????????????");
                    }
                    if ((row.getCell(3) != null)&& row.getCell(3).getCellType() != CellType.BLANK  && row.getCell(3).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(3).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????????????????");
                    }
                    if ((row.getCell(4) != null) && (row.getCell(4).getCellType()!=CellType.BLANK)&& row.getCell(4).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(4).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????????????????");
                    }
                    if ((row.getCell(5) != null) &&(row.getCell(5).getCellType()!=CellType.BLANK)&& row.getCell(5).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(5).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????????????????");
                    }
                    if ((row.getCell(6) != null) && row.getCell(6).getCellType() != CellType.BLANK && row.getCell(6).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(6).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(6).toString()+"??????????????????");
                    }
                    if ((row.getCell(7) != null) && (row.getCell(7).getCellType()!=CellType.BLANK)&& row.getCell(7).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(7).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(7).toString()+"??????????????????");
                    }
                    if ((row.getCell(8) != null) && row.getCell(8).getCellType() != CellType.BLANK && row.getCell(8).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(8).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(8).toString()+"??????????????????");
                    }
                    if ((row.getCell(9) != null)&& row.getCell(9).getCellType() != CellType.BLANK  &&row.getCell(9).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(9).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????????????????");
                    }
                    if ((row.getCell(10) != null) && row.getCell(10).getCellType() != CellType.BLANK &&row.getCell(10).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(10).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????????????????");
                    }
                    if ((row.getCell(11) != null)&& row.getCell(11).getCellType() != CellType.BLANK  &&row.getCell(11).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(11).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(11).toString()+"??????????????????");
                    }
                    if ((row.getCell(12) != null)&& row.getCell(12).getCellType() != CellType.BLANK  && row.getCell(12).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(12).toString(), "??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????????????????");
                    }
                    if ((row.getCell(13) != null) && row.getCell(13).getCellType() != CellType.BLANK && row.getCell(13).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(13).toString(),"??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(13).toString()+"??????????????????");
                    }
                    if ((row.getCell(14) != null)&& row.getCell(14).getCellType() != CellType.BLANK && row.getCell(14).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(14).toString(),"??????????????????");
                        errorList.add("????????? ???" + a + "???"+titleRow.getCell(14).toString()+"??????????????????");
                    }
                }
                if (!errorList.isEmpty()) {
                    System.out.println(errorList);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, errorList);
                }
                for (int j = 0; j < sheet5.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    SupervisionEnSmallWorkshop supervisionEnSmallWorkshop = new SupervisionEnSmallWorkshop();
                    SupervisionEnSmallWorkshopIndex supervisionEnSmallWorkshopIndex= new SupervisionEnSmallWorkshopIndex();
                    XSSFRow row = sheet9.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    if(enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)))!=null)
                    {
                        int id= enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        supervisionEnSmallWorkshopIndex.setEnterpriseId(id);
                    }
                    else
                    {
                        int a=j+1;
                        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "????????? ???"+a+"?????????????????????????????????");
                    }
                    supervisionEnSmallWorkshop.setBusinessName(ExcalUtils.handleStringXSSF(row.getCell(0)));
                    supervisionEnSmallWorkshop.setRegisterNumber(ExcalUtils.handleStringXSSF(row.getCell(2)));
//                    supervisionEnSmallWorkshop.(ExcalUtils.handleStringXSSF(row.getCell(3)));
                    supervisionEnSmallWorkshop.setFoodType(ExcalUtils.handleStringXSSF(row.getCell(4)));
                    supervisionEnSmallWorkshop.setBusinessAddress(ExcalUtils.handleStringXSSF(row.getCell(5)));
                    supervisionEnSmallWorkshop.setStartTime(ExcalUtils.handleDateXSSF(row.getCell(6)));
                    supervisionEnSmallWorkshop.setEndTime(ExcalUtils.handleDateXSSF(row.getCell(7)));
                    supervisionEnSmallWorkshop.setGiveTime(ExcalUtils.handleDateXSSF(row.getCell(8)));
                    supervisionEnSmallWorkshop.setLssueAuthority(ExcalUtils.handleStringXSSF(row.getCell(9)));
                    supervisionEnSmallWorkshop.setCheckType(ExcalUtils.handleStringXSSF(row.getCell(10)));
                    supervisionEnSmallWorkshop.setDynamicGrade(ExcalUtils.handleStringXSSF(row.getCell(11)));
                    supervisionEnSmallWorkshop.setYearAssessment(ExcalUtils.handleStringXSSF(row.getCell(12)));
                    supervisionEnSmallWorkshop.setPatrolFrequency(ExcalUtils.handleStringXSSF(row.getCell(13)));
                    supervisionEnSmallWorkshop.setEnterpriseScale(ExcalUtils.handleStringXSSF(row.getCell(14)));
                    supervisionEnSmallWorkshop.setOperator("?????????");
                    supervisionEnSmallWorkshop.setOperateIp("123.123.123");
                    supervisionEnSmallWorkshop.setOperateTime(new Date());
                    if(!supervisionEnSmallWorkshop.getRegisterNumber().equals("")) {
                        if(numberSmallWorkshopMap.get(supervisionEnSmallWorkshop.getRegisterNumber())!=null)
                        {
                            //??????????????????????????????
                            int id = numberSmallWorkshopMap.get(supervisionEnSmallWorkshop.getRegisterNumber());
                            supervisionEnSmallWorkshop.setId(id);
                            id=numberSmallWorkshopIndexMap.get(supervisionEnSmallWorkshopIndex.getEnterpriseId());
                            supervisionEnSmallWorkshop.setIndexId(id);
                            supervisionEnSmallWorkshopMapper.updateByPrimaryKey(supervisionEnSmallWorkshop);
                        }
                        else{
                            Date dateNew = new Date();
                            if(supervisionEnSmallWorkshopIndexMapper.selectByEnterpriseId(supervisionEnSmallWorkshopIndex.getEnterpriseId())!=null) {
                                dateNew=supervisionEnSmallWorkshop.getEndTime();
                                supervisionEnSmallWorkshopIndex=supervisionEnSmallWorkshopIndexMapper.selectByEnterpriseId(supervisionEnSmallWorkshopIndex.getEnterpriseId());
                                if(dateComparedUtil.DateCompared(supervisionEnSmallWorkshopIndex.getEndTime(),dateNew)==1)
                                {
                                    supervisionEnSmallWorkshopIndex.setEndTime(dateNew);
                                }
                                supervisionEnSmallWorkshopIndex.setNumber(supervisionEnSmallWorkshopIndex.getNumber()+supervisionEnSmallWorkshop.getRegisterNumber());
                                supervisionEnSmallWorkshopIndexMapper.updateByPrimaryKeySelective(supervisionEnSmallWorkshopIndex);
                                supervisionEnSmallWorkshop.setIndexId(supervisionEnSmallWorkshopIndex.getId());
                            }
                            else {
                                supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(supervisionEnSmallWorkshopIndex.getEnterpriseId());
                                supervisionEnterprise.setPermissionType(supervisionEnterprise.getPermissionType() + ",smallWorkshop");
                                supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
                                supervisionEnSmallWorkshopIndex.setNumber(supervisionEnSmallWorkshop.getRegisterNumber());
                                supervisionEnSmallWorkshopIndex.setEndTime(supervisionEnSmallWorkshop.getEndTime());
                                int indexId = supervisionEnSmallWorkshopIndexMapper.insertSelective(supervisionEnSmallWorkshopIndex);
                                supervisionEnSmallWorkshop.setIndexId(indexId);
                            }
                            supervisionEnSmallWorkshopMapper.insertSelective(supervisionEnSmallWorkshop);
                        }
                    }
                }

                //?????????????????????
                List<SupervisionEnIndustrialProducts> supervisionEnIndustrialProductsList = supervisionEnIndustrialProductsMapper.getAll();
                Map <String,Integer> numberIndustrialMap =new HashMap<>();
                for (SupervisionEnIndustrialProducts supervisionEnIndustrialProducts:supervisionEnIndustrialProductsList){
                    numberIndustrialMap.put(supervisionEnIndustrialProducts.getRegisterNumber(),supervisionEnIndustrialProducts.getId());
                }
                List<SupervisionEnIndustrialProductsIndex> supervisionEnIndustrialProductsIndexList = supervisionEnIndustrialProductsIndexMapper.getAll();
                Map <Integer,Integer> numberIndustrialIndexMap =new HashMap<>();
                for (SupervisionEnIndustrialProductsIndex supervisionEnIndustrialProductsIndex:supervisionEnIndustrialProductsIndexList){
                    numberIndustrialIndexMap.put(supervisionEnIndustrialProductsIndex.getEnterpriseId(),supervisionEnIndustrialProductsIndex.getId());
                }
                XSSFSheet sheet11 = workbook.getSheetAt(11);
                for (int j = 0; j <sheet11.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    XSSFRow titleRow = sheet11.getRow(0);
                    XSSFRow row = sheet11.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    int a = j + 1;
                    if(row.getCell(1).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????");
                    }
                    if(row.getCell(2).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????");
                    }
                    if(row.getCell(9).getCellType()==CellType.BLANK)
                    {
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????");
                    }
                    if ((row.getCell(0) != null) && row.getCell(0).getCellType() != CellType.BLANK && row.getCell(0).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(0).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(0).toString()+"??????????????????");
                    }
                    if ((row.getCell(1) != null) && row.getCell(1).getCellType() != CellType.BLANK && row.getCell(1).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(1).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(1).toString()+"??????????????????");
                    }
                    if ((row.getCell(2) != null) && row.getCell(2).getCellType() != CellType.BLANK  && row.getCell(2).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(2).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(2).toString()+"??????????????????");
                    }
                    if ((row.getCell(3) != null)&& row.getCell(3).getCellType() != CellType.BLANK  && row.getCell(3).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(3).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(3).toString()+"??????????????????");
                    }
                    if ((row.getCell(4) != null) && (row.getCell(4).getCellType()!=CellType.BLANK)&& row.getCell(4).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(4).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(4).toString()+"??????????????????");
                    }
                    if ((row.getCell(5) != null) &&(row.getCell(5).getCellType()!=CellType.BLANK)&& row.getCell(5).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(5).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(5).toString()+"??????????????????");
                    }
                    if ((row.getCell(6) != null) && row.getCell(6).getCellType() != CellType.BLANK && row.getCell(6).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(6).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(6).toString()+"??????????????????");
                    }
                    if ((row.getCell(7) != null) && (row.getCell(7).getCellType()!=CellType.BLANK)&& row.getCell(7).getCellType() != CellType.NUMERIC) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(7).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(7).toString()+"??????????????????");
                    }
                    if ((row.getCell(8) != null) && row.getCell(8).getCellType() != CellType.BLANK && row.getCell(8).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(8).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(8).toString()+"??????????????????");
                    }
                    if ((row.getCell(9) != null)&& row.getCell(9).getCellType() != CellType.BLANK  &&row.getCell(9).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(9).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(9).toString()+"??????????????????");
                    }
                    if ((row.getCell(10) != null) && row.getCell(10).getCellType() != CellType.BLANK &&row.getCell(10).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(10).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(10).toString()+"??????????????????");
                    }
                    if ((row.getCell(11) != null)&& row.getCell(11).getCellType() != CellType.BLANK  &&row.getCell(11).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(11).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(11).toString()+"??????????????????");
                    }
                    if ((row.getCell(12) != null)&& row.getCell(12).getCellType() != CellType.BLANK  && row.getCell(12).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(12).toString(), "??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(12).toString()+"??????????????????");
                    }
                    if ((row.getCell(13) != null) && row.getCell(13).getCellType() != CellType.BLANK && row.getCell(13).getCellType() != CellType.STRING) {
//                        errorMap.put("???" + a + "???"+titleRow.getCell(13).toString(),"??????????????????");
                        errorList.add("????????????????????? ???" + a + "???"+titleRow.getCell(13).toString()+"??????????????????");
                    }
                }
                if (!errorList.isEmpty()) {
                    System.out.println(errorList);
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, errorList);
                }
                for (int j = 0; j < sheet11.getPhysicalNumberOfRows(); j++) {
                    if (j == 0) {
                        continue;//?????????
                    }
                    SupervisionEnIndustrialProducts supervisionEnIndustrialProducts = new SupervisionEnIndustrialProducts();
                    SupervisionEnIndustrialProductsIndex supervisionEnIndustrialProductsIndex= new SupervisionEnIndustrialProductsIndex();
                    XSSFRow row = sheet11.getRow(j);
                    if((row.getCell(1).getCellType()==CellType.BLANK)&&(row.getCell(2).getCellType()==CellType.BLANK))
                    {
                        break;
                    }
                    if(enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)))!=null)
                    {
                        int id= enterpriseIdMap.get(ExcalUtils.handleStringXSSF(row.getCell(1)));
                        supervisionEnIndustrialProductsIndex.setEnterpriseId(id);
                    }
                    else
                    {
                        int a=j+1;
                        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "????????????????????? ???"+a+"?????????????????????????????????");
                    }
                    supervisionEnIndustrialProducts.setBusinessName(ExcalUtils.handleStringXSSF(row.getCell(0)));
                    supervisionEnIndustrialProducts.setRegisterNumber(ExcalUtils.handleStringXSSF(row.getCell(2)));
                    supervisionEnIndustrialProducts.setProductsName(ExcalUtils.handleStringXSSF(row.getCell(3)));
                    supervisionEnIndustrialProducts.setBusinessAddress(ExcalUtils.handleStringXSSF(row.getCell(4)));
                    supervisionEnIndustrialProducts.setStartTime(ExcalUtils.handleDateXSSF(row.getCell(5)));
                    supervisionEnIndustrialProducts.setEndTime(ExcalUtils.handleDateXSSF(row.getCell(6)));
                    supervisionEnIndustrialProducts.setGiveTime(ExcalUtils.handleDateXSSF(row.getCell(7)));
                    supervisionEnIndustrialProducts.setLssueAuthority(ExcalUtils.handleStringXSSF(row.getCell(8)));
                    supervisionEnIndustrialProducts.setCheckType(ExcalUtils.handleStringXSSF(row.getCell(9)));
                    supervisionEnIndustrialProducts.setDynamicGrade(ExcalUtils.handleStringXSSF(row.getCell(10)));
                    supervisionEnIndustrialProducts.setYearAssessment(ExcalUtils.handleStringXSSF(row.getCell(11)));
                    supervisionEnIndustrialProducts.setPatrolFrequency(ExcalUtils.handleStringXSSF(row.getCell(12)));
                    supervisionEnIndustrialProducts.setEnterpriseScale(ExcalUtils.handleStringXSSF(row.getCell(13)));
                    supervisionEnIndustrialProducts.setOperator("?????????");
                    supervisionEnIndustrialProducts.setOperateIp("123.123.123");
                    supervisionEnIndustrialProducts.setOperateTime(new Date());
                    if(!supervisionEnIndustrialProducts.getRegisterNumber().equals("")) {
                        if(numberIndustrialMap.get(supervisionEnIndustrialProducts.getRegisterNumber())!=null)
                        {
                            //??????????????????????????????
                            int id = numberIndustrialMap.get(supervisionEnIndustrialProducts.getRegisterNumber());
                            supervisionEnIndustrialProducts.setId(id);
                            id = numberIndustrialIndexMap.get(supervisionEnIndustrialProductsIndex.getEnterpriseId());
                            supervisionEnIndustrialProducts.setIndexId(id);
                            supervisionEnIndustrialProductsMapper.updateByPrimaryKey(supervisionEnIndustrialProducts);
                        }
                        else{
                            Date dateNew = new Date();
                            if(supervisionEnIndustrialProductsIndexMapper.selectByEnterpriseId(supervisionEnIndustrialProductsIndex.getEnterpriseId())!=null){
                                dateNew=supervisionEnIndustrialProducts.getEndTime();
                                supervisionEnIndustrialProductsIndex=supervisionEnIndustrialProductsIndexMapper.selectByEnterpriseId(supervisionEnIndustrialProductsIndex.getEnterpriseId());
                                if(dateComparedUtil.DateCompared(supervisionEnIndustrialProductsIndex.getEndTime(),dateNew)==1)
                                {
                                    supervisionEnIndustrialProductsIndex.setEndTime(dateNew);
                                }
                                supervisionEnIndustrialProductsIndex.setNumber(supervisionEnIndustrialProductsIndex.getNumber()+","+supervisionEnIndustrialProducts.getRegisterNumber());
                                supervisionEnIndustrialProductsIndexMapper.updateByPrimaryKeySelective(supervisionEnIndustrialProductsIndex);
                                supervisionEnIndustrialProducts.setIndexId(supervisionEnIndustrialProductsIndex.getId());
                            }
                            else {
                                supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(supervisionEnIndustrialProductsIndex.getEnterpriseId());
                                supervisionEnterprise.setPermissionType(supervisionEnterprise.getPermissionType() + ",IndustrialProducts");
                                supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
                                supervisionEnIndustrialProductsIndex.setNumber(supervisionEnIndustrialProducts.getRegisterNumber());
                                supervisionEnIndustrialProductsIndex.setEndTime(supervisionEnIndustrialProducts.getEndTime());
                                int indexId = supervisionEnIndustrialProductsIndexMapper.insertSelective(supervisionEnIndustrialProductsIndex);
                                supervisionEnIndustrialProducts.setIndexId(indexId);
                            }
                            supervisionEnIndustrialProductsMapper.insertSelective(supervisionEnIndustrialProducts);
                        }
                    }
                }
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return changedNumbers;
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "????????????");
        }
    }



    //?????????????????????map?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
    Integer importCheckArea(String areaName,Map<String,Integer> areaMap,String idNumber){
        if(areaMap.get(areaName)!=null||StringUtils.isEmpty(idNumber)){
            return areaMap.get(areaName);
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,areaName+"???????????????");
        }
    }

    Integer importCheckDept(String deptName,Map<String,Integer> deptMap,String idNumber){
        if(deptMap.get(deptName)!=null||StringUtils.isEmpty(idNumber)){
            return deptMap.get(deptName);
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,deptName+"???????????????");
        }
    }


    /**
     * ???????????????serviceImpl
     */
    @Override
    public Map<String,Object> getFoodBusinessLicenseById(int id) {
        Map<String,Object> result = new HashMap<>();
        List<SupervisionEnFoodBu> foodBusinessLicenseList = supervisionEnFoodBuMapper.getListByEnterpriseId(id);
        result.put("number",foodBusinessLicenseList.get(0).getNumber());
        result.put("businessFormat",foodBusinessLicenseList.get(0).getBusinessFormat());
        result.put("category",foodBusinessLicenseList.get(0).getCategory());
        result.put("businessNotes",foodBusinessLicenseList.get(0).getBusinessNotes());
        result.put("businessProject",foodBusinessLicenseList.get(0).getBusinessProject());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        result.put("startTime",simpleDateFormat.format(foodBusinessLicenseList.get(0).getStartTime()));
        result.put("endTime",simpleDateFormat.format(foodBusinessLicenseList.get(0).getEndTime()));
        result.put("giveTime",simpleDateFormat.format(foodBusinessLicenseList.get(0).getGiveTime()));
        result.put("licenseAuthority",foodBusinessLicenseList.get(0).getLicenseAuthority());
        result.put("checkType",foodBusinessLicenseList.get(0).getCheckType());
        result.put("dynamicGrade",foodBusinessLicenseList.get(0).getDynamicGrade());
        result.put("yearAssessment",foodBusinessLicenseList.get(0).getYearAssessment());
        result.put("patrolFrequency",foodBusinessLicenseList.get(0).getPatrolFrequency());
        result.put("enterpriseScale",foodBusinessLicenseList.get(0).getEnterpriseScale());
        return result;
    }
    @Override
    public Map<String, Object> getLicensePhotosById(int id) {
        SupervisionEnterprise supervisionEnterprise= supervisionEnterpriseMapper.selectByPrimaryKey(id);
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"??????????????????");
        }
        Map<String, Object> result= new HashMap<>();
        result.put("businessLicensePhoto","");
        result.put("foodBusinessPhoto","");
        List<SupervisionEnterpriseDocument> list = supervisionEnterpriseDocumentMapper.selectByEnterpriseId(id);
        if (list.size()>0) {
            for (SupervisionEnterpriseDocument supervisionEnterpriseDocument : list) {
                if (supervisionEnterpriseDocument.getFlag() == 1) {
                    result.put("businessLicensePhoto",supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 2) {
                    result.put("foodBusinessPhoto",supervisionEnterpriseDocument.getDocument());
                }
            }
        }
        return result;
    }
    @Override
    public Map<String, Object> getLicensePhotos(int id) {
        SupervisionEnterprise supervisionEnterprise= supervisionEnterpriseMapper.selectByPrimaryKey(id);
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"??????????????????");
        }
        Map<String, Object> result= new HashMap<>();
      //  EnterpriseParam enterpriseParam = new EnterpriseParam();
        List<SupervisionEnterpriseDocument> list = supervisionEnterpriseDocumentMapper.selectByEnterpriseId(id);
        if (list.size()>0) {
            for (SupervisionEnterpriseDocument supervisionEnterpriseDocument : list) {
                if (supervisionEnterpriseDocument.getFlag() == 1) {
                   //enterpriseParam.setBusinessLicensePhoto(supervisionEnterpriseDocument.getDocument());
                    result.put("BusinessLicensePhoto",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 2) {
                //    enterpriseParam.setFoodBusinessPhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("FoodBusinessPhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 3) {
                //    enterpriseParam.setSmallCaterPhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("SmallCaterPhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 4) {
                 //   enterpriseParam.setSmallWorkshopPhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("SmallWorkshopPhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 5) {
                 //   enterpriseParam.setFoodProducePhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("FoodProducePhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 6) {
                 //   enterpriseParam.setDrugsBusinessPhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("DrugsBusinessPhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 7) {
                 //   enterpriseParam.setDrugsProducePhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("DrugsProducePhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 8) {
                  //  enterpriseParam.setCosmeticsUsePhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("CosmeticsUsePhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 9) {
                   // enterpriseParam.setMedicalProducePhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("MedicalProducePhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 10) {
                   // enterpriseParam.setMedicalBusinessPhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("MedicalBusinessPhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 11) {
                  //  enterpriseParam.setIndustrialProductsPhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("IndustrialProductsPhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 12) {
                  //  enterpriseParam.setPublicityPhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("PublicityPhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 13) {
                  //  enterpriseParam.setCertificatePhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("CertificatePhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
                if (supervisionEnterpriseDocument.getFlag() == 14) {
                   // enterpriseParam.setOtherPhotos(supervisionEnterpriseDocument.getDocument());
                    result.put("OtherPhotos",JSON2ImageUrl(supervisionEnterpriseDocument.getDocument()));
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> updateLicensePhotosById(int enterpriseId,String businessLicensePhoto,String foodBusinessPhoto){
        List<SupervisionEnterpriseDocument> list = supervisionEnterpriseDocumentMapper.selectByEnterpriseId(enterpriseId);
        Map<String, Object> result= new HashMap<>();
        if (list.size()>0) {
            for (SupervisionEnterpriseDocument supervisionEnterpriseDocument : list) {

                if (supervisionEnterpriseDocument.getFlag() == 1) {
                    supervisionEnterpriseDocument.setDocument(businessLicensePhoto);
                    supervisionEnterpriseDocumentMapper.updateByPrimaryKey(supervisionEnterpriseDocument);
                    result.put("businessLicensePhoto",supervisionEnterpriseDocument.getDocument());
                }
                if (supervisionEnterpriseDocument.getFlag() == 2) {
                    supervisionEnterpriseDocument.setDocument(foodBusinessPhoto);
                    supervisionEnterpriseDocumentMapper.updateByPrimaryKey(supervisionEnterpriseDocument);
                    result.put("foodBusinessPhoto",supervisionEnterpriseDocument.getDocument());
                }
            }
        }

        return result;
    }

    @Override
    public List<SupervisionEnterprise> getAll(){
        return supervisionEnterpriseMapper.getAll2();
    }

    @Override
    public Map<String, Object> getVrUrl(int enterpriseId) {

        SupervisionEnterprise supervisionEnterprise= supervisionEnterpriseMapper.selectByPrimaryKey(enterpriseId);
        Map<String, Object> result= new HashMap<>();
        result.put("vrUrl",supervisionEnterprise.getVrUrl());
        result.put("point","");
        if (supervisionEnterprise==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"??????????????????");
        }
        if(supervisionEnterprise.getGpsFlag()==0){
            GridPoints gridPoints =gridPointsMapper.getPointByEnterpriseId(supervisionEnterprise.getId());
            result.put("point",gridPoints.getPoint());
        }else if(supervisionEnterprise.getGpsFlag()==1){
            GridPointsGps gridPointsGps = gridPointsGpsMapper.getPointByCodeId(supervisionEnterprise.getIdNumber());
            result.put("point",gridPointsGps.getPoint());
        }
        return result;
    }

    @Override
    public void changeLicensePhoto(SysUser sysUser,String json) {
        //EnterpriseParam enterpriseParam
        EnterpriseParam enterpriseParam = JSONObject.parseObject(json,EnterpriseParam.class);
        ValidationResult result = validator.validate(enterpriseParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        SupervisionEnterprise before = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
        if (before == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "????????????????????????");
        }
        SupervisionEnterprise supervisionEnterprise = new SupervisionEnterprise();
        supervisionEnterprise.setId(sysUser.getInfoId());
        insertEnterpriseDocumentList(supervisionEnterprise,enterpriseParam);
        return;
    }

}
