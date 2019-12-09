package com.cjk.sys.service;

import com.cjk.sys.entity.Dept;
import com.cjk.sys.entity.Page;

import java.util.List;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/2
 * @description
 */
public interface DeptService {

    /**
     * 查询所有部门（不分页）
     * @return
     */
    public List<Dept> findAllDept();

    /**
     * 查询部门数
     * @param deptName
     * @return
     */
    public Integer getCount(String deptName);

    /**
     * 根据条件查询所有部门（分页）
     * @param deptName
     * @param page
     * @return
     */
    public List<Dept> listAll(String deptName, Page page);

    /**
     * 删除部门
     * @param id
     */
    public void deleteDeptById(Integer id);

    /**
     * 添加部门
     * @param dept
     */
    public void add(Dept dept);

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    public Dept findDeptById(Integer id);

    /**
     * 修改部门
     * @param dept
     */
    public void updateDept(Dept dept);
}
