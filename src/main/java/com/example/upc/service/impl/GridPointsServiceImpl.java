package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.config.pageConfig.DoPage;
import com.example.upc.config.picConfig.PicMini;
import com.example.upc.controller.param.*;
import com.example.upc.controller.searchParam.EnterpriseSearchParam;
import com.example.upc.dao.GridPointsGpsMapper;
import com.example.upc.dao.GridPointsMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dao.SysAreaMapper;
import com.example.upc.dataobject.*;
import com.example.upc.service.GridPointsService;
import com.example.upc.service.SysAreaService;
import com.example.upc.service.model.MapIndustryNumber;
import com.example.upc.util.CaculateDisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zcc
 * @date 2019/8/13 11:12
 */
@Service
public class GridPointsServiceImpl implements GridPointsService {
    @Autowired
    private GridPointsMapper gridPointsMapper;
    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Autowired
    private SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private GridPointsGpsMapper gridPointsGpsMapper;

    @Override
    public List<GridPoints> getAll() {
        return gridPointsMapper.getAll();
    }
    @Override
    public List<GridPoints1> getAll1() {
        return gridPointsMapper.getAll1();
    }

    @Override
    public List<GridPoints> getByAreaId(int id) {
        return gridPointsMapper.getByAreaId(id);
    }
    @Override
    public int checkPoint(int id){
        return gridPointsMapper.checkPoint(id);
    }
    @Override
    public int updatePoint(GridPoints record){
        return gridPointsMapper.updatePoint(record);
    }
    @Override
    public int insertPoint(GridPoints record){
        return gridPointsMapper.insertSelective(record);
    }

    @Override
    public List<SmilePoints> getSmileMapPoints(EnterpriseSearchParam enterpriseSearchParam){
        return gridPointsMapper.getSmileAll(enterpriseSearchParam);
    }

    @Override
    public List<SmilePoints> getSmileMapPointsPhone(EnterpriseSearchParam enterpriseSearchParam){
        //设置距离多少以内
        if (enterpriseSearchParam.getDis() == null||enterpriseSearchParam.getDis().equals("")) {
            enterpriseSearchParam.setDis(1000);
        }
        //默认中心位置
        if (enterpriseSearchParam.getLocation() == null||enterpriseSearchParam.getLocation().equals("")){
            enterpriseSearchParam.setLocation("118.5821878900,37.4489563700");
        }
        //获取gps经纬度
        String[] gps = enterpriseSearchParam.getLocation().split(",");
        Double gpsA =Double.parseDouble(gps[0]);
        Double gpsB =Double.parseDouble(gps[1]);
        //将距离转化为经纬度
        Float gps1 = (float) (gpsA - enterpriseSearchParam.getDis() * 0.00001141);
        Float gps2 = (float) (gpsA + enterpriseSearchParam.getDis() * 0.00001141);
        Float gps3 = (float) (gpsB - enterpriseSearchParam.getDis() * 0.00000899);
        Float gps4 = (float) (gpsB + enterpriseSearchParam.getDis() * 0.00000899);
        //根据中心点上下左右距离内取点
        List<SmilePoints> smilePointsList = gridPointsMapper.getSmileAllPhone(enterpriseSearchParam,gps1,gps2,gps3,gps4);
        return smilePointsList;
    }

    @Override
    public  List<enterpriseId> getEnterpriseByName(String name) {
        return gridPointsMapper.getEnterpriseByName(name);
    }
    @Override
    public Map<String, Object> getAreaEnterprise(){
        List<SysArea> sysAreaList = sysAreaMapper.getAllAreaPa();//在地区表中查找备注为区域的记录
        List<Integer> areaIntegers=sysAreaService.getAll().stream().map((sysArea -> sysArea.getId())).collect(Collectors.toList());
        //获取所有区域的id列表
        Map<Integer,Object> areaCount= new HashMap<>();
        for(SysArea sysArea:sysAreaList){//循环备注为区域的list结果
            List<Integer> areaIdList = new ArrayList<>();
            areaIdList.add(sysArea.getId());//循环盛放搜索结果中的id，下面就是建立
            //以此为一组，分别是构建不同的产业类型
            EnterpriseSearchParam foodBusinessEnterpriseSearchParam = new EnterpriseSearchParam();//建立
            List<String> fooBusinessList = new ArrayList<>();
            fooBusinessList.add("foodBusiness");
            foodBusinessEnterpriseSearchParam.setAreaList(areaIdList);
            foodBusinessEnterpriseSearchParam.setIndustryList(fooBusinessList);

            EnterpriseSearchParam smallCaterEnterpriseSearchParam = new EnterpriseSearchParam();//建立
            List<String> smallCaterList = new ArrayList<>();
            smallCaterList.add("smallCater");
            smallCaterEnterpriseSearchParam.setAreaList(areaIdList);
            smallCaterEnterpriseSearchParam.setIndustryList(smallCaterList);

            EnterpriseSearchParam smallWorkshopEnterpriseSearchParam = new EnterpriseSearchParam();//建立
            List<String> smallWorkshopList = new ArrayList<>();
            smallWorkshopList.add("smallWorkshop");
            smallWorkshopEnterpriseSearchParam.setAreaList(areaIdList);
            smallWorkshopEnterpriseSearchParam.setIndustryList(smallWorkshopList);

            EnterpriseSearchParam foodProduceEnterpriseSearchParam = new EnterpriseSearchParam();
            List<String> foodProduceList = new ArrayList<>();
            foodProduceList.add("foodProduce");
            foodProduceEnterpriseSearchParam.setAreaList(areaIdList);
            foodProduceEnterpriseSearchParam.setIndustryList(foodProduceList);

            EnterpriseSearchParam drugsBusinessEnterpriseSearchParam = new EnterpriseSearchParam();
            List<String> drugsBusinessList = new ArrayList<>();
            drugsBusinessList.add("drugsBusiness");
            drugsBusinessEnterpriseSearchParam.setAreaList(areaIdList);
            drugsBusinessEnterpriseSearchParam.setIndustryList(drugsBusinessList);

            EnterpriseSearchParam drugsProduceEnterpriseSearchParam = new EnterpriseSearchParam();
            List<String> drugsProduceList = new ArrayList<>();
            drugsProduceList.add("drugsProduce");
            drugsProduceEnterpriseSearchParam.setAreaList(areaIdList);
            drugsProduceEnterpriseSearchParam.setIndustryList(drugsProduceList);


            EnterpriseSearchParam cosmeticsUseEnterpriseSearchParam = new EnterpriseSearchParam();
            List<String> cosmeticsUseList = new ArrayList<>();
            cosmeticsUseList.add("cosmeticsUse");
            cosmeticsUseEnterpriseSearchParam.setAreaList(areaIdList);
            cosmeticsUseEnterpriseSearchParam.setIndustryList(cosmeticsUseList);

            EnterpriseSearchParam medicalProduceEnterpriseSearchParam = new EnterpriseSearchParam();
            List<String> medicalProduceList = new ArrayList<>();
            medicalProduceList.add("medicalProduce");
            medicalProduceEnterpriseSearchParam.setAreaList(areaIdList);
            medicalProduceEnterpriseSearchParam.setIndustryList(medicalProduceList);

            EnterpriseSearchParam medicalBusinessEnterpriseSearchParam = new EnterpriseSearchParam();
            List<String> medicalBusinessList = new ArrayList<>();
            medicalBusinessList.add("medicalBusiness");
            medicalBusinessEnterpriseSearchParam.setAreaList(areaIdList);
            medicalBusinessEnterpriseSearchParam.setIndustryList(medicalBusinessList);

            EnterpriseSearchParam industrialProductsEnterpriseSearchParam = new EnterpriseSearchParam();
            List<String> industrialProductsList = new ArrayList<>();
            industrialProductsList.add("industrialProducts");
            industrialProductsEnterpriseSearchParam.setAreaList(areaIdList);
            industrialProductsEnterpriseSearchParam.setIndustryList(industrialProductsList);

            MapIndustryNumber mapIndustryNumber = new MapIndustryNumber();
            mapIndustryNumber.setFoodBusiness(supervisionEnterpriseMapper.countList(foodBusinessEnterpriseSearchParam));
            mapIndustryNumber.setSmallCater(supervisionEnterpriseMapper.countList(smallCaterEnterpriseSearchParam));
            mapIndustryNumber.setSmallWorkshop(supervisionEnterpriseMapper.countList(smallWorkshopEnterpriseSearchParam));
            mapIndustryNumber.setFoodProduce(supervisionEnterpriseMapper.countList(foodProduceEnterpriseSearchParam));
            mapIndustryNumber.setDrugsBusiness(supervisionEnterpriseMapper.countList(drugsBusinessEnterpriseSearchParam));
            mapIndustryNumber.setDrugsProduce(supervisionEnterpriseMapper.countList(drugsProduceEnterpriseSearchParam));
            mapIndustryNumber.setCosmeticsUse(supervisionEnterpriseMapper.countList(cosmeticsUseEnterpriseSearchParam));
            mapIndustryNumber.setMedicalBusiness(supervisionEnterpriseMapper.countList(medicalBusinessEnterpriseSearchParam));
            mapIndustryNumber.setMedicalProduce(supervisionEnterpriseMapper.countList(medicalProduceEnterpriseSearchParam));
            mapIndustryNumber.setIndustrialProducts(supervisionEnterpriseMapper.countList(industrialProductsEnterpriseSearchParam));
            areaCount.put(sysArea.getId(),mapIndustryNumber);//向map中存放地区id和当前地区的6个产业类型中企业的数量
        }
        EnterpriseSearchParam enterpriseSearchParam = new EnterpriseSearchParam();
        enterpriseSearchParam.setAreaList(areaIntegers);//放置areaId的list
        List<String> industryList = new ArrayList<>();
        industryList.add("foodBusiness");
        industryList.add("smallCater");
        industryList.add("smallWorkshop");
        industryList.add("foodProduce");
        industryList.add("drugsBusiness");
        industryList.add("drugsProduce");
        industryList.add("cosmeticsUse");
        industryList.add("medicalProduce");
        industryList.add("medicalBusiness");
        industryList.add("industrialProducts");
        enterpriseSearchParam.setIndustryList(industryList);//放置产业类型的list
        //抛出map
        Map<String,Object> map = new HashMap<>();
        map.put("areaCount",areaCount);//包含1、所有地区以及每个地区下的6大类产业的企业数量
        map.put("allCount",supervisionEnterpriseMapper.countList(enterpriseSearchParam));//2、所有企业的数量
        map.put("areaList",sysAreaList);//所有地区
        return map;
    }
    @Override
    @Transactional
    public int deleteByEnterpriseId(int id){
        return gridPointsMapper.deleteByEnterpriseId(id);
    }

    @Override
    public GridPoints getPointByEnterpriseId(Integer id) {
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(id);
            if (supervisionEnterprise.getGpsFlag() == 0) {
                GridPoints gridPoints = gridPointsMapper.getPointByEnterpriseId(id);
                return gridPoints;
            }
            else if (supervisionEnterprise.getGpsFlag() == 1) {
                GridPointsGps gridPointsGps = gridPointsGpsMapper.getPointByCodeId(supervisionEnterprise.getIdNumber());
                GridPoints gridPoints1 = new GridPoints();
                gridPoints1.setEnterpriseId(supervisionEnterprise.getId());
                gridPoints1.setAreaId(supervisionEnterprise.getArea());
                gridPoints1.setPoint(gridPointsGps.getPoint());
                return gridPoints1;
            }
            else {
                throw new BusinessException(EmBusinessError.UPDATE_ERROR);
            }
    }

    @Override
    public int getVideoIdByEnterprise(int id) {
        return gridPointsMapper.getVideoIdByEnterprise(id);
    }

    @Override
    public void updateEnterprisePoint(int id, String code, String points, SysUser sysUser) {
        SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(id);
        if (supervisionEnterprise != null){
            supervisionEnterprise.setGpsFlag(1);
            supervisionEnterprise.setBusinessState(2);
            supervisionEnterpriseMapper.updateByPrimaryKeySelective(supervisionEnterprise);
            GridPointsGps gridPointsGps = gridPointsGpsMapper.getPointByCodeId(code);
            if (gridPointsGps != null){
                gridPointsGps.setPoint(points);
                gridPointsGps.setOperator(sysUser.getUsername());
                gridPointsGps.setOperatorIp("1.1.1.1");
                gridPointsGpsMapper.updateByPrimaryKeySelective(gridPointsGps);
            }
            else {
                GridPointsGps gridPointsGps1 = new GridPointsGps();
                gridPointsGps1.setCodeId(code);
                gridPointsGps1.setAreaId(supervisionEnterprise.getArea());
                gridPointsGps1.setPoint(points);
                gridPointsGps1.setOperator(sysUser.getUsername());
                gridPointsGps1.setOperatorIp("1.1.1.1");
                gridPointsGpsMapper.insertSelective(gridPointsGps1);
            }
        }
        else{
            throw new BusinessException(EmBusinessError.UPDATE_ERROR);
        }
    }

    @Override
    @DoPage
    public List<NearEnterprise> getNearEnterprise(EnterpriseSearchParam enterpriseSearchParam, PageQuery pageQuery){

        if (enterpriseSearchParam.getSouthwestPoint() == null || enterpriseSearchParam.getNortheastPoint() == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "无边界点经纬度");
        }
        //无边界点经纬度时使用默认经纬度
        if (enterpriseSearchParam.getSouthwestPoint().getLongitude() == 0.0f && enterpriseSearchParam.getSouthwestPoint().getLatitude() == 0.0f) {
            enterpriseSearchParam.getSouthwestPoint().setLongitude((float) (118.35399));
            enterpriseSearchParam.getSouthwestPoint().setLatitude((float) (37.269157));
        }
        if (enterpriseSearchParam.getNortheastPoint().getLongitude() == 0.0f && enterpriseSearchParam.getNortheastPoint().getLatitude() == 0.0f) {
            enterpriseSearchParam.getNortheastPoint().setLongitude((float) (118.81039));
            enterpriseSearchParam.getNortheastPoint().setLatitude((float) (37.628757));
        }

        List<NearEnterprise> nearEnterpriseList = gridPointsMapper.getNearEnterprise(enterpriseSearchParam,
                enterpriseSearchParam.getSouthwestPoint().getLongitude(),
                enterpriseSearchParam.getNortheastPoint().getLongitude(),
                enterpriseSearchParam.getSouthwestPoint().getLatitude(),
                enterpriseSearchParam.getNortheastPoint().getLatitude(), 0, pageQuery);
        //企业列表显示时计算距离
        if(enterpriseSearchParam.getIsList()==1) {
            if (enterpriseSearchParam.getCurrentPoint() == null) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "无中心点经纬度");
            }
            //默认中心点位置
            if ((enterpriseSearchParam.getCurrentPoint().getLongitude() == 0.0f && enterpriseSearchParam.getCurrentPoint().getLatitude() == 0.0f)) {
                enterpriseSearchParam.getCurrentPoint().setLongitude((float) (118.58218789));
                enterpriseSearchParam.getCurrentPoint().setLatitude((float) (37.44895637));
            }
            nearEnterpriseList.forEach(item -> {
//                if (item.getPropagandaEnclosure().equals("")) {
//                    item.setPropagandaEnclosure("");
//                } else {
//                    item.setPropagandaEnclosure(JsonToImageUrl.JSON2ImageUrl(item.getPropagandaEnclosure()));
//                }
                String[] gpsTarget = item.getPoint().split(",");
                Double gpsC = Double.parseDouble(gpsTarget[0]);
                Double gpsD = Double.parseDouble(gpsTarget[1]);
                CaculateDisUtil caculateDisUtil = new CaculateDisUtil();
                item.setDistance((int) caculateDisUtil.Distance((double) enterpriseSearchParam.getCurrentPoint().getLongitude(), (double) enterpriseSearchParam.getCurrentPoint().getLatitude(), gpsC, gpsD));
            });
            //计算出距离后进行排序
            if(enterpriseSearchParam.getSortList().contains("distance")) {
                Collections.sort(nearEnterpriseList, new Comparator<NearEnterprise>() {
                    @Override
                    public int compare(NearEnterprise o1, NearEnterprise o2) {
                        int diff = o1.getDistance() - o2.getDistance();
                        return diff;
                    }
                });
            }
        }

        return nearEnterpriseList;
    }

    @Override
    public List<NearEnterprise> getEnterpriseByParam(EnterpriseSearchParam enterpriseSearchParam){
        return gridPointsMapper.getEnterpriseByParam(enterpriseSearchParam);
    }

    @Override
    public List<NearEnterprise> getNearEnterpriseScore(EnterpriseSearchParam enterpriseSearchParam){
//        CaculateDisUtil caculateDisUtil = new CaculateDisUtil();
//        //设置距离多少以内
//        if (enterpriseSearchParam.getDis() == null||enterpriseSearchParam.getDis().equals("")) {
//            enterpriseSearchParam.setDis(1000);
//        }
//        //默认中心位置
//        if (enterpriseSearchParam.getLocation() == null||enterpriseSearchParam.getLocation().equals("")){
//            enterpriseSearchParam.setLocation("118.5821878900,37.4489563700");
//        }
//        //获取gps经纬度
//        String[] gps = enterpriseSearchParam.getLocation().split(",");
//        Double gpsA =Double.parseDouble(gps[0]);
//        Double gpsB =Double.parseDouble(gps[1]);
//        //将距离转化为经纬度
//        Float gps1 = (float) (gpsA - enterpriseSearchParam.getDis() * 0.00001141);
//        Float gps2 = (float) (gpsA + enterpriseSearchParam.getDis() * 0.00001141);
//        Float gps3 = (float) (gpsB - enterpriseSearchParam.getDis() * 0.00000899);
//        Float gps4 = (float) (gpsB + enterpriseSearchParam.getDis() * 0.00000899);
//        //根据中心点上下左右距离内取点
//        List<NearEnterprise> nearEnterpriseList = gridPointsMapper.getNearEnterprise(enterpriseSearchParam,gps1,gps2,gps3,gps4,1);
//        nearEnterpriseList.forEach(item->{
////            if (item.getPropagandaEnclosure().equals("")) {
////                item.setPropagandaEnclosure("");
////            } else {
////                item.setPropagandaEnclosure(JsonToImageUrl.JSON2ImageUrl(item.getPropagandaEnclosure()));
////            }
//            String[] gpsTarget = item.getPoint().split(",");
//            Double gpsC =Double.parseDouble(gpsTarget[0]);
//            Double gpsD =Double.parseDouble(gpsTarget[1]);
//            item.setDistance((int) caculateDisUtil.Distance(gpsA, gpsB, gpsC, gpsD));
//        });
//        Collections.sort(nearEnterpriseList, new Comparator<NearEnterprise>() {
//            @Override
//            public int compare(NearEnterprise o1, NearEnterprise o2) {
//                float diff1 = o1.getAverageScore() - o2.getAverageScore();
//                int diff2 = o1.getDistance() - o2.getDistance();
//                if(diff1 <= 0){
//                    return 1;
//                }
//                else{
//                    return -1;
//                }
//            }
//        });
//        for (NearEnterprise item:nearEnterpriseList
//        ) {
//            System.out.println(item.getAverageScore());
//        }
//        return nearEnterpriseList;
        List<NearEnterprise> nearEnterpriseList = new ArrayList<>();
        return nearEnterpriseList;
    }
}
