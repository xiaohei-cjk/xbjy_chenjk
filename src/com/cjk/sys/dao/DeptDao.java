package com.cjk.sys.dao;

import com.cjk.sys.entity.Dept;
import com.cjk.sys.entity.Page;
import com.cjk.utils.DBUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/2
 * @description
 */
public class DeptDao {
    JdbcTemplate jdbcTemplate=new JdbcTemplate(DBUtil.getDataSource());

    /**
     * 查询所有部门（不分页）
     * @return
     */
    public List<Dept> findAllDept(){
        String sql="select * from sys_dept";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Dept.class));
    }

    /**
     * 查询部门数
     * @param deptName
     * @return
     */
    public Integer getCount(String deptName){
        String sql="select count(*) from sys_dept where name like ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,"%"+deptName+"%");
    }

    public List<Dept> listAll(String deptName, Page page){
        /**
         * 另一个查询sql
         * SELECT
         * 	d.id id,
         * 	d.name name,
         * 	d.create_time time,
         * 	a.counts counts,
         * 	a.deptId deptId
         * FROM
         * 	sys_dept d
         * 	LEFT JOIN (select count(*) counts,dept_id deptId from sys_user group by dept_id) a on d.id=a.deptId
         */
        String sql="SELECT " +
                "d.counts userCount,"+
                "u.NAME createByName," +
                "d.id id," +
                "d.name name," +
                "d.create_time createTime " +
                "FROM " +
                "(SELECT dept.id,dept.name,dept.create_time,dept.create_by, COUNT(user.dept_id) counts FROM sys_dept dept LEFT JOIN sys_user user ON dept.id=user.dept_id GROUP BY dept.id) d " +
                "LEFT JOIN sys_user u ON d.create_by = u.id " +
                "where d.name like ? order by d.create_time DESC limit ?,? ";
        return  jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Dept.class),"%"+deptName+"%",(page.getPageCurrent() - 1) * page.getPageSize(),page.getPageSize());
    }

    /**
     * 删除部门
     * @param id
     */
    public void deleteDeptById(Integer id){
        String sql="delete from sys_dept where id = ?";
        jdbcTemplate.update(sql,id);
    }

    /**
     * 添加部门
     * @param dept
     */
    public void add(Dept dept){
        String sql="insert into sys_dept (id,name,create_time,create_by) values (null,?,?,?)";
        jdbcTemplate.update(sql,dept.getName(),dept.getCreateTime(),dept.getCreateBy());
    }

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    public Dept findDeptById(Integer id){
        String sql="select * from sys_dept where id=?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Dept.class),id);
    }

    /**
     * 修改部门
     * @param dept
     */
    public void updateDept(Dept dept){
        String sql="update sys_dept set name=? where id=?";
        jdbcTemplate.update(sql,dept.getName(),dept.getId());
    }
}
