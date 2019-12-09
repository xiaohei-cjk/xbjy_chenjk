package com.cjk.sys.service.impl;

import com.cjk.sys.dao.DeptDao;
import com.cjk.sys.entity.Dept;
import com.cjk.sys.entity.Page;
import com.cjk.sys.service.DeptService;
import com.cjk.utils.DateUtil;

import java.util.List;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/2
 * @description
 */
public class DeptServiceImpl implements DeptService {

    DeptDao deptDao=new DeptDao();

    @Override
    public List<Dept> findAllDept() {
        return deptDao.findAllDept();
    }

    @Override
    public Integer getCount(String deptName) {
        return deptDao.getCount(deptName);
    }

    @Override
    public List<Dept> listAll(String deptName, Page page) {
        return deptDao.listAll(deptName,page);
    }

    @Override
    public void deleteDeptById(Integer id) {
        deptDao.deleteDeptById(id);
    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(DateUtil.getDateStr());
        deptDao.add(dept);
    }

    @Override
    public Dept findDeptById(Integer id) {
        return deptDao.findDeptById(id);
    }

    @Override
    public void updateDept(Dept dept) {
        deptDao.updateDept(dept);
    }
}
