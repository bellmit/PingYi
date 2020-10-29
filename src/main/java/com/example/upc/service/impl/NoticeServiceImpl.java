package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.NoticeMapper;
import com.example.upc.dataobject.Notice;
import com.example.upc.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeMapper noticeMapper;
    @Override
    public PageResult getPage(PageQuery pageQuery) {
        int count=noticeMapper.countList();
        if (count > 0) {
            List<Notice> fpList = noticeMapper.getPage(pageQuery);
            PageResult<Notice> pageResult = new PageResult<>();
            pageResult.setData(fpList);
            pageResult.setTotal(count);
            return pageResult;
        }
        PageResult<Notice> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    public void insert(Notice notice) {
//        if(checkComNameExist(sysFoodProduce.getComName(), sysFoodProduce.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业名称已被占用");
//        }

        Notice notice1 = new Notice();
        notice1.setSendTo(notice.getSendTo());
        notice1.setSendToPerson(notice.getSendToPerson());
        notice1.setType(notice.getType());
        notice1.setTitle(notice.getTitle());
        notice1.setContent(notice.getContent());
        notice1.setPersonName(notice.getPersonName());
        notice1.setDocument(notice.getDocument());

        // TODO: sendEmail

        noticeMapper.insertSelective(notice1);
        //http://localhost:8080/ga/insert?department=1&name=1&sexy=1&job=1&type=1&idNumber=1&enforce=1&mobilePhone=1&officePhone=1&number=1&workPhone=1&category=1&photo=1&isStop=1
    }
    @Override
    public void delete(int fpId) {
        Notice notice = noticeMapper.selectByPrimaryKey(fpId);
        if(notice==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"不存在，无法删除");
        }
        noticeMapper.deleteByPrimaryKey(fpId);
    }
    @Override
    public void update(Notice notice) {
//        if(checkComNameExist(sysFoodProduce.getComName(), sysFoodProduce.getId())) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"企业名称已被占用");
//        }

        Notice notice1 = new Notice();
        notice1.setId(notice.getId());
        notice1.setSendTo(notice.getSendTo());
        notice1.setSendToPerson(notice.getSendToPerson());
        notice1.setType(notice.getType());
        notice1.setTitle(notice.getTitle());
        notice1.setContent(notice.getContent());
        notice1.setPersonName(notice.getPersonName());
        notice1.setDocument(notice.getDocument());

        // TODO: sendEmail

        noticeMapper.updateByPrimaryKeySelective(notice1);
    }
}
