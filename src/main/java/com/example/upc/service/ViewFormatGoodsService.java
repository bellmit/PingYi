package com.example.upc.service;

import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.searchParam.GoodsSearchParam;
import com.example.upc.dataobject.ViewFormatGoods;

public interface ViewFormatGoodsService {
    PageResult<ViewFormatGoods> getPage (PageQuery pageQuery, GoodsSearchParam goodsSearchParam);
    PageResult<ViewFormatGoods> getPageEnterprise (PageQuery pageQuery, Integer id, GoodsSearchParam goodsSearchParam);
    PageResult<ViewFormatGoods> getPageAdmin (PageQuery pageQuery, GoodsSearchParam goodsSearchParam);
    void fail();
}
