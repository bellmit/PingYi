package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.GoodsSearchParam;
import com.example.upc.dao.SupervisionCaMapper;
import com.example.upc.dao.ViewFormatGoodsMapper;
import com.example.upc.dataobject.FormatGoods;
import com.example.upc.dataobject.SupervisionCa;
import com.example.upc.dataobject.ViewFormatGoods;
import com.example.upc.service.ViewFormatGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewFormatGoodsServiceImpl implements ViewFormatGoodsService {

    @Autowired
    ViewFormatGoodsMapper viewFormatGoodsMapper;
    @Autowired
    SupervisionCaMapper supervisionCaMapper;

    @Override
    public PageResult getPage(PageQuery pageQuery, GoodsSearchParam goodsSearchParam) {
        int count=viewFormatGoodsMapper.countListSup(goodsSearchParam);
        if (count > 0) {
            List<ViewFormatGoods> fqtList = viewFormatGoodsMapper.getPage(pageQuery, goodsSearchParam);
            PageResult<ViewFormatGoods> pageResult = new PageResult<>();
            pageResult.setData(fqtList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ViewFormatGoods> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageAdmin(PageQuery pageQuery, GoodsSearchParam goodsSearchParam) {
        int count=viewFormatGoodsMapper.countListAdmin(goodsSearchParam);
        if (count > 0) {
            List<ViewFormatGoods> fqtList = viewFormatGoodsMapper.getPageAdmin(pageQuery, goodsSearchParam);
            PageResult<ViewFormatGoods> pageResult = new PageResult<>();
            pageResult.setData(fqtList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ViewFormatGoods> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult getPageEnterprise(PageQuery pageQuery, Integer id, GoodsSearchParam goodsSearchParam) {

        int count=viewFormatGoodsMapper.countListEnterprise(id, goodsSearchParam);
        if (count > 0) {
            List<ViewFormatGoods> fdList = viewFormatGoodsMapper.getPageEnterprise(pageQuery, id, goodsSearchParam);
            PageResult<ViewFormatGoods> pageResult = new PageResult<>();
            pageResult.setData(fdList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<ViewFormatGoods> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void fail() {
        throw new BusinessException(EmBusinessError.USER_NO);
    }
}
