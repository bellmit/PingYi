package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SupervisionCaTransferMapper;
import com.example.upc.dataobject.SupervisionCaTransfer;
import com.example.upc.service.SupervisionCaTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/6/26 17:28
 */
@Service
public class SupervisionCaTransferServiceImpl implements SupervisionCaTransferService {
    @Autowired
    private SupervisionCaTransferMapper supervisionCaTransferMapper;
    @Override
    public PageResult<SupervisionCaTransfer> getPage(PageQuery pageQuery) {
        int count= supervisionCaTransferMapper.countList();
        if (count > 0) {
            List<SupervisionCaTransfer> supervisionCaTransferList = supervisionCaTransferMapper.getPage(pageQuery);
            PageResult<SupervisionCaTransfer> pageResult = new PageResult<>();
            pageResult.setData(supervisionCaTransferList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionCaTransfer> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<SupervisionCaTransfer> getPageByCaId(PageQuery pageQuery, Integer id) {
        int count= supervisionCaTransferMapper.countListByCaId(id);
        if (count > 0) {
            List<SupervisionCaTransfer> supervisionCaTransferList = supervisionCaTransferMapper.getPageByCaId(pageQuery,id);
            PageResult<SupervisionCaTransfer> pageResult = new PageResult<>();
            pageResult.setData(supervisionCaTransferList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SupervisionCaTransfer> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void insert(SupervisionCaTransfer supervisionCaTransfer) {
        supervisionCaTransfer.setOperateIp("124.214.124");
        supervisionCaTransfer.setOperateTime(new Date());
        supervisionCaTransfer.setOperator("操作人");
       supervisionCaTransferMapper.insertSelective(supervisionCaTransfer);
    }

    @Override
    @Transactional
    public void update(SupervisionCaTransfer supervisionCaTransfer) {
        SupervisionCaTransfer before = supervisionCaTransferMapper.selectByPrimaryKey(supervisionCaTransfer.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新晨检不存在");
        }
        supervisionCaTransfer.setOperateIp("124.214.124");
        supervisionCaTransfer.setOperateTime(new Date());
        supervisionCaTransfer.setOperator("操作人");
        supervisionCaTransferMapper.updateByPrimaryKeySelective(supervisionCaTransfer);
    }
    @Override
    public void delete(int id) {
        SupervisionCaTransfer before = supervisionCaTransferMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除晨检不存在");
        }
        supervisionCaTransferMapper.deleteByPrimaryKey(id);
    }

}
