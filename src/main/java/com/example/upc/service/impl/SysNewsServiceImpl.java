package com.example.upc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.NewsParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.controller.param.SysNewsWeixinParam;
import com.example.upc.controller.searchParam.NewsSearchParam;
import com.example.upc.dao.SysNewsMapper;
import com.example.upc.dataobject.SysNews;
import com.example.upc.dataobject.SysUser;
import com.example.upc.service.SysNewsService;
import com.example.upc.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/8/23 11:25
 */
@Service
public class SysNewsServiceImpl implements SysNewsService {
    @Autowired
    private SysNewsMapper sysNewsMapper;

    @Override
    public PageResult<NewsParam> getPage(PageQuery pageQuery, SysUser sysUser, NewsSearchParam newsSearchParam) {
        int count= sysNewsMapper.countList(sysUser.getInfoId(),newsSearchParam);
        if (count > 0) {
            List<NewsParam> sysNewsList = sysNewsMapper.getPage(pageQuery,sysUser.getInfoId(),newsSearchParam);
            PageResult<NewsParam> pageResult = new PageResult<>();
            pageResult.setData(sysNewsList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<NewsParam> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public PageResult<NewsParam> getPageAndroid(PageQuery pageQuery, int type, NewsSearchParam newsSearchParam) {
        int count= sysNewsMapper.countListAndroid(type,newsSearchParam);
        if (count > 0) {
            List<NewsParam> sysNewsList = sysNewsMapper.getPageAndroid(pageQuery, type,newsSearchParam);
            PageResult<NewsParam> pageResult = new PageResult<>();
            pageResult.setData(sysNewsList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<NewsParam> pageResult = new PageResult<>();
        return pageResult;
    }

    //insert的时候按照status的值判断插入内容
    // 如果是是（1）则插入审核人id，审核时间为当前时间，
    // 如果是否（0），则审核人插入0，审核时间为当前时间，
    //0可以代表已审核，1为未审核，在前端进行getpage时可以显示当前状态，并根据当前状态值进行显示是否显示审核按钮，审核按钮为修改
    //update功能：修改按钮功能在于当前人匹配作者，如果状态为1，则显示修改按钮，否则不显示
    //一经审核或本来就不需要审核的文章不可修改，只可看，删

    @Override
    @Transactional
    public void insert(SysNews sysNews,SysUser sysUser) {
        SysNews sysNews1 = new SysNews();
        BeanUtils.copyProperties(sysNews, sysNews1);
        if (sysNews.getStatus()==0)
        {
            sysNews1.setCheckPerson(0);
            sysNews1.setPublicTime(new Date());
        }
        if (sysNews.getStatus()==1)
        {
            sysNews1.setCheckPerson(sysNews.getCheckPerson());
            sysNews1.setPublicTime(new Date());
        }
        sysNews1.setAuthor(sysUser.getUsername());
        sysNews1.setOperator("操作人");
        sysNews1.setOperateIp("123.124.124");
        sysNews1.setOperateTime(new Date());
        sysNewsMapper.insertSelective(sysNews1);
    }

    @Override
    public void delete(int id) {
        SysNews before = sysNewsMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除的信息不存在，无法删除");
        }
        sysNewsMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void check(int id) {
        SysNews before = sysNewsMapper.selectByPrimaryKey(id);
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待审核的信息不存在");
        }
        if(before.getStatus()==1){
            sysNewsMapper.changeStatus(id,0);
        }
    }

    @Override
    @Transactional
    public void update(SysNews sysNews) {
        SysNews before = sysNewsMapper.selectByPrimaryKey(sysNews.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的信息不存在，无法更新");
        }
        SysNews sysNews1 = new SysNews();
        BeanUtils.copyProperties(sysNews, sysNews1);
        sysNewsMapper.updateByPrimaryKeySelective(sysNews1);
    }

    @Override
    public NewsParam selectById(int id) {
        return sysNewsMapper.selectById(id);
    }

    @Override
    public List<SysNewsWeixinParam> list(String ids){
        List<SysNewsWeixinParam> list0 = new ArrayList<>();
        List<Integer> list1 = StringUtil.splitToListInt(ids);//id的list
        for (int i=0;i<list1.size();i++){
            SysNews sysNews =sysNewsMapper.selectByPrimaryKey(list1.get(i));
            SysNewsWeixinParam sysNewsWeixinParam = new SysNewsWeixinParam();
            JSONArray json = JSONArray.parseArray(sysNews.getEnclosure());
            if (sysNews.getStatus()==0&&json.size()!=0)
            {
            sysNewsWeixinParam.setAuthor(sysNews.getAuthor());
            sysNewsWeixinParam.setTitle(sysNews.getTitle());
            sysNewsWeixinParam.setContent(sysNews.getContent());
            sysNewsWeixinParam.setDigest(sysNews.getTitle());
            sysNewsWeixinParam.setPicture("upload/picture/"+json.getJSONObject(0).getJSONObject("response").getString("data"));
            list0.add(sysNewsWeixinParam);
            }
        }
        return list0;
    }

}
