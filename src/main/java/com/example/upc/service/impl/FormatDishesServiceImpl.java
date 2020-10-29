package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.common.ValidationResult;
import com.example.upc.common.ValidatorImpl;
import com.example.upc.controller.param.FormatDishesParam;
import com.example.upc.controller.param.FormatDishesSupParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.DishSearchParam;
import com.example.upc.dao.FormatDishesMapper;
import com.example.upc.dao.SupervisionCaMapper;
import com.example.upc.dao.SupervisionEnterpriseMapper;
import com.example.upc.dataobject.FormatDishes;
import com.example.upc.dataobject.SupervisionEnterprise;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.FormatDishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FormatDishesServiceImpl implements FormatDishesService {
    @Autowired
    FormatDishesMapper formatDishesMapper;
    @Autowired
    SupervisionEnterpriseMapper supervisionEnterpriseMapper;
    @Autowired
    SupervisionCaMapper supervisionCaMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public PageResult getPage(PageQuery pageQuery, DishSearchParam dishSearchParam) {
        int count=formatDishesMapper.countListSup(dishSearchParam);
        if (count > 0) {
            List<FormatDishesSupParam> fdList = formatDishesMapper.getPage(pageQuery, dishSearchParam);
            PageResult<FormatDishesSupParam> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatDishesSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageEnterprise(PageQuery pageQuery, Integer id, DishSearchParam dishSearchParam) {

        int count=formatDishesMapper.countListEnterprise(id, dishSearchParam);
        if (count > 0) {
            List<FormatDishes> fdList = formatDishesMapper.getPageEnterprise(pageQuery, id, dishSearchParam);
            PageResult<FormatDishes> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatDishes> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, DishSearchParam dishSearchParam) {
        int count=formatDishesMapper.countListAdmin(dishSearchParam);
        if (count > 0) {
            List<FormatDishesSupParam> fdList = formatDishesMapper.getPageAdmin(pageQuery, dishSearchParam);
            PageResult<FormatDishesSupParam> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<FormatDishesSupParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(FormatDishesParam formatDishesParam, SysUser sysUser) {

        ValidationResult result = validator.validate(formatDishesParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if (sysUser.getUserType()==1)
        {
            SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
            if (supervisionEnterprise==null){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
            }
            if (checkNumberExist(sysUser.getInfoId(), supervisionEnterprise.getArea(), formatDishesParam.getNumber(), formatDishesParam.getId()))
            {
                throw new BusinessException(EmBusinessError.NUMBER_ERROR);
            }
            if(checkNameExist(sysUser.getInfoId(), supervisionEnterprise.getArea(), formatDishesParam.getName(), formatDishesParam.getType(), formatDishesParam.getId())) {
                throw new BusinessException(EmBusinessError.NAME_ERROR);
            }
            if (checkAllExist(sysUser.getInfoId(), supervisionEnterprise.getArea(), formatDishesParam.getName(), formatDishesParam.getNumber(), formatDishesParam.getType(), formatDishesParam.getId()))
            {
                throw new BusinessException(EmBusinessError.DISHIES_All);
            }
            else
            {
                FormatDishes formatDishes1 = new FormatDishes();
                formatDishes1.setUnit(sysUser.getInfoId());
                formatDishes1.setArea(supervisionEnterprise.getArea());
                formatDishes1.setNumber(formatDishesParam.getNumber());
                formatDishes1.setName(formatDishesParam.getName());
                formatDishes1.setPrice(formatDishesParam.getPrice());
                formatDishes1.setType(formatDishesParam.getType());
                formatDishes1.setRemark(formatDishesParam.getRemark());
                formatDishes1.setPhoto(formatDishesParam.getPhoto());
                formatDishes1.setOperator("操作人");
                formatDishes1.setOperatorIp("124.124.124");
                formatDishes1.setOperatorTime(new Date());

                // TODO: sendEmail

                formatDishesMapper.insertSelective(formatDishes1);
            }
        }
        else
            {
                SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(formatDishesParam.getUnitId());
                if (supervisionEnterprise==null){
                    throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
                }
                if (checkNumberExist(formatDishesParam.getUnitId(), formatDishesParam.getAreaId(), formatDishesParam.getNumber(), formatDishesParam.getId()))
                {
                    throw new BusinessException(EmBusinessError.NUMBER_ERROR);
                }
                if(checkNameExist(formatDishesParam.getUnitId(), formatDishesParam.getAreaId(), formatDishesParam.getName(), formatDishesParam.getType(), formatDishesParam.getId())) {
                    throw new BusinessException(EmBusinessError.NAME_ERROR);
                }
                if (checkAllExist(formatDishesParam.getUnitId(), formatDishesParam.getAreaId(), formatDishesParam.getName(), formatDishesParam.getNumber(), formatDishesParam.getType(), formatDishesParam.getId()))
                {
                    throw new BusinessException(EmBusinessError.DISHIES_All);
                }
                else
                {
                    FormatDishes formatDishes1 = new FormatDishes();
                    formatDishes1.setUnit(formatDishesParam.getUnitId());
                    formatDishes1.setArea(formatDishesParam.getAreaId());
                    formatDishes1.setNumber(formatDishesParam.getNumber());
                    formatDishes1.setName(formatDishesParam.getName());
                    formatDishes1.setPrice(formatDishesParam.getPrice());
                    formatDishes1.setType(formatDishesParam.getType());
                    formatDishes1.setRemark(formatDishesParam.getRemark());
                    formatDishes1.setPhoto(formatDishesParam.getPhoto());
                    formatDishes1.setOperator("操作人");
                    formatDishes1.setOperatorIp("124.124.124");
                    formatDishes1.setOperatorTime(new Date());

                    // TODO: sendEmail

                    formatDishesMapper.insertSelective(formatDishes1);
                }
            }


//        http://localhost:8080/formatdishes/update?id=1&unit=%E5%B1%B1%E4%B8%9C%E5%A6%82%E6%96%B0%E5%85%AC%E5%8F%B8&number=2&name=%E5%A4%A7%E7%B1%B3%E9%A5%AD&price=11&type=%E4%B8%BB%E9%A3%9F&remark=%E7%B1%B3%E9%A5%AD
         }
    @Override
    public void delete(int fdId) {
        FormatDishes formatDishes = formatDishesMapper.selectByPrimaryKey(fdId);
        if(formatDishes==null){
            throw new BusinessException(EmBusinessError.ID_ERROR);
        }
        formatDishesMapper.deleteByPrimaryKey(fdId);
    }


    @Override
    @Transactional
    public void update(FormatDishesParam formatDishesParam, SysUser sysUser) {

        ValidationResult result = validator.validate(formatDishesParam);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        if (sysUser.getUserType()==1)
        {
            SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(sysUser.getInfoId());
            if (supervisionEnterprise==null){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
            }
            if (checkNumberExist(sysUser.getInfoId(), supervisionEnterprise.getArea(), formatDishesParam.getNumber(), formatDishesParam.getId()))
            {
                throw new BusinessException(EmBusinessError.NUMBER_ERROR);
            }
            if(checkNameExist(sysUser.getInfoId(), supervisionEnterprise.getArea(), formatDishesParam.getName(), formatDishesParam.getType(), formatDishesParam.getId())) {
                throw new BusinessException(EmBusinessError.NAME_ERROR);
            }
            if (checkAllExist(sysUser.getInfoId(), supervisionEnterprise.getArea(), formatDishesParam.getName(), formatDishesParam.getNumber(), formatDishesParam.getType(), formatDishesParam.getId()))
            {
                throw new BusinessException(EmBusinessError.DISHIES_All);
            }
            else
            {
                FormatDishes formatDishes1 = new FormatDishes();
                formatDishes1.setId(formatDishesParam.getId());
                formatDishes1.setUnit(sysUser.getInfoId());
                formatDishes1.setArea(supervisionEnterprise.getArea());
                formatDishes1.setNumber(formatDishesParam.getNumber());
                formatDishes1.setName(formatDishesParam.getName());
                formatDishes1.setPrice(formatDishesParam.getPrice());
                formatDishes1.setType(formatDishesParam.getType());
                formatDishes1.setRemark(formatDishesParam.getRemark());
                formatDishes1.setPhoto(formatDishesParam.getPhoto());
                formatDishes1.setOperator("操作人");
                formatDishes1.setOperatorIp("124.124.124");
                formatDishes1.setOperatorTime(new Date());

                // TODO: sendEmail

                formatDishesMapper.updateByPrimaryKeySelective(formatDishes1);
            }
        }
        else
        {
            SupervisionEnterprise supervisionEnterprise = supervisionEnterpriseMapper.selectByPrimaryKey(formatDishesParam.getUnitId());
            if (supervisionEnterprise==null){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"无此企业信息");
            }
            if (checkNumberExist(formatDishesParam.getUnitId(), formatDishesParam.getAreaId(), formatDishesParam.getNumber(), formatDishesParam.getId()))
            {
                throw new BusinessException(EmBusinessError.NUMBER_ERROR);
            }
            if(checkNameExist(formatDishesParam.getUnitId(), formatDishesParam.getAreaId(), formatDishesParam.getName(), formatDishesParam.getType(), formatDishesParam.getId())) {
                throw new BusinessException(EmBusinessError.NAME_ERROR);
            }
            if (checkAllExist(formatDishesParam.getUnitId(), formatDishesParam.getAreaId(), formatDishesParam.getName(), formatDishesParam.getNumber(), formatDishesParam.getType(), formatDishesParam.getId()))
            {
                throw new BusinessException(EmBusinessError.DISHIES_All);
            }
            else
            {
                FormatDishes formatDishes1 = new FormatDishes();
                formatDishes1.setId(formatDishesParam.getId());
                formatDishes1.setUnit(formatDishesParam.getUnitId());
                formatDishes1.setArea(formatDishesParam.getAreaId());
                formatDishes1.setNumber(formatDishesParam.getNumber());
                formatDishes1.setName(formatDishesParam.getName());
                formatDishes1.setPrice(formatDishesParam.getPrice());
                formatDishes1.setType(formatDishesParam.getType());
                formatDishes1.setRemark(formatDishesParam.getRemark());
                formatDishes1.setPhoto(formatDishesParam.getPhoto());
                formatDishes1.setOperator("操作人");
                formatDishes1.setOperatorIp("124.124.124");
                formatDishes1.setOperatorTime(new Date());

                // TODO: sendEmail

                formatDishesMapper.updateByPrimaryKeySelective(formatDishes1);
            }
        }
        // TODO: sendEmail


    }



    public boolean checkNameExist(Integer unit,Integer area, String name, String type, Integer id) {
        return formatDishesMapper.countByName(unit, area, name, type, id) > 0;
    }
    public boolean checkNumberExist(Integer unit,Integer area, String number, Integer id) {
        return formatDishesMapper.countByNumber(unit, area, number, id) > 0;
    }
    public boolean checkAllExist(Integer unit,Integer area, String name, String number, String type, Integer id) {
        return formatDishesMapper.countByAll(unit, area, name, number, type, id) > 0;
    }
    @Override
    public void fail() {
            throw new BusinessException(EmBusinessError.USER_NO);
    }
}
