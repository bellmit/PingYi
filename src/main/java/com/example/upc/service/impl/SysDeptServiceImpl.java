package com.example.upc.service.impl;

import com.example.upc.common.BusinessException;
import com.example.upc.common.EmBusinessError;
import com.example.upc.controller.param.DeptParam;
import com.example.upc.controller.param.PageQuery;
import com.example.upc.controller.param.PageResult;
import com.example.upc.dao.SysDeptMapper;
import com.example.upc.dao.SysUserMapper;
import com.example.upc.dataobject.SysDept;
import com.example.upc.service.SysDeptService;
import com.example.upc.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zcc
 * @date 2019/3/28 11:16
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public PageResult<SysDept> getPage(PageQuery pageQuery) {
        int count=sysDeptMapper.countList();
        if (count > 0) {
            List<SysDept> inspectDailyFoodList = sysDeptMapper.getPage(pageQuery);
            PageResult<SysDept> pageResult = new PageResult<>();
            pageResult.setData(inspectDailyFoodList);
            pageResult.setTotal(count);
            pageResult.setPageNo(pageQuery.getPageNo());
            pageResult.setPageSize(pageQuery.getPageSize());
            return pageResult;
        }
        PageResult<SysDept> pageResult = new PageResult<>();
        return pageResult;
    }

    @Override
    @Transactional
    public void changeLeaderId(int deptId, int leaderId) {
        sysDeptMapper.changeLeaderId(deptId,leaderId);
    }

    @Override
    public List<Integer> getIdListSearch(int searchId) {
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(searchId);
        List<Integer> list = sysDeptMapper.getChildIdListByLevel(LevelUtil.calculateLevel(sysDept.getLevel(),searchId));
        list.add(searchId);
        return list;
    }

    @Override
    public List<SysDept> getAll() {
        return sysDeptMapper.getAllDept();
    }

    @Override
    @Transactional
    public void save(DeptParam param) {
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"同一层级下存在相同名称的部门");
        }
        SysDept sysDept = new SysDept();
        sysDept.setName(param.getName());
        sysDept.setParentId(param.getParentId());
        sysDept.setSeq(param.getSeq());
        sysDept.setAddress(param.getAddress());
        sysDept.setDefaultRole(param.getDefaultRole());
        sysDept.setType(param.getType());
        sysDept.setAreaId(param.getAreaId());
        sysDept.setLeaderId(param.getLeaderId());
        sysDept.setRemark(param.getRemark());
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        sysDept.setOperator("admin");
        sysDept.setOperateIp("0.0.0.1");
        sysDept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(sysDept);
    }

    @Override
    @Transactional
    public void update(DeptParam param) {
        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());
        if(before==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待更新的部门不存在");
        }
    //    Preconditions.checkNotNull(before, "待更新的部门不存在");
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"同一层级下存在相同名称的部门");
        }
        SysDept after = new SysDept();
        after.setId(param.getId());
        after.setName(param.getName());
        after.setParentId(param.getParentId());
        after.setSeq(param.getSeq());
        after.setAddress(param.getAddress());
        after.setDefaultRole(param.getDefaultRole());
        after.setType(param.getType());
        after.setAreaId(param.getAreaId());
        after.setLeaderId(param.getLeaderId());
        after.setRemark(param.getRemark());
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator("admin");
        after.setOperateIp("0.0.0.1");
        after.setOperateTime(new Date());
        updateWithChild(before, after);
    }

    @Override
    public void delete(int deptId){
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if(dept==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"待删除的部门不存在，无法删除");
        }
       // Preconditions.checkNotNull(dept, "待删除的部门不存在，无法删除");
        if (sysDeptMapper.countByParentId(dept.getId()) > 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"当前部门下面有子部门，无法删除");
        }
        if(sysUserMapper.countByDeptId(dept.getId()) > 0) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"当前部门下面有用户，无法删除");
        }
        sysDeptMapper.deleteByPrimaryKey(deptId);
    }

    @Override
    public SysDept getById(int id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }

    @Override
    public int countList() {
        return sysDeptMapper.countList();
    }

    public void updateWithChild(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(LevelUtil.calculateLevel(before.getLevel(),before.getId()));
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        sysDeptMapper.updateByPrimaryKeySelective(after);
    }

    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }
    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }

}
