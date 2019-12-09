package com.cjk.sys.dao;

import com.cjk.sys.dto.UserDTO;
import com.cjk.sys.entity.Page;
import com.cjk.sys.entity.User;
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
public class UserDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(DBUtil.getDataSource());

    /**
     * 查询所有用户
     * @return
     */
    public List<User> listAll(UserDTO userDTO) {
        String sql = "SELECT " +
                "d.NAME deptName," +
                "u.id id," +
                "u.account account," +
                "u.NAME name," +
                "u.age age," +
                "u.sex sex," +
                "u.birth_date birthDate," +
                "u.create_time createTime " +
                "FROM " +
                "sys_user u " +
                "LEFT JOIN sys_dept d ON u.dept_id = d.id " +
                "where u.account like ? and date(u.create_time) between ? and ? limit ?,?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), "%" + userDTO.getAccount() + "%", userDTO.getBeginTime(),userDTO.getEndTime(),(userDTO.getPage().getPageCurrent() - 1) * userDTO.getPage().getPageSize(), userDTO.getPage().getPageSize());
    }

    /**
     * 查询用户总条数
     * @param userDTO
     * @return
     */
    public Integer getCount(UserDTO userDTO) {
        String sql = "select count(*) from sys_user where account like ? and date(create_time) between ? and ? ";
        return jdbcTemplate.queryForObject(sql, Integer.class, "%" + userDTO.getAccount() + "%",userDTO.getBeginTime(),userDTO.getEndTime());
    }

    /**
     * 添加用户
     * @param user
     */
    public void add(User user) {
        String sql = "insert into sys_user (id,dept_id,account,password,name,age,sex,email,birth_date,create_time,create_by) values (null,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getDeptId(), user.getAccount(), user.getPassword(), user.getName(), user.getAge(), user.getSex(), user.getEmail(), user.getBirthDate(), user.getCreateTime(),user.getCreateBy());
    }

    /**
     *根据id删除用户
     * @param id
     */
    public void deleteById(Integer id) {
        String sql = "delete from sys_user where id = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * 根据id 查询用户
     * @param id
     * @return
     */
    public User findUserById(Integer id){
        String sql="select * from sys_user where id=?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),id);
    }

    /**
     * 修改用户
     * @param user
     */
    public void updateUser(User user){
        String sql = "update sys_user set dept_id=?, account=?, name=? ,age=? ,sex=?,email=?, birth_date=? where id=?";
        jdbcTemplate.update(sql, user.getDeptId(), user.getAccount(), user.getName(), user.getAge(), user.getSex(), user.getEmail(), user.getBirthDate(), user.getId());
    }

    /**
     * 修改密码
     * @param user
     */
    public void updatePassword(User user){
        //系统account唯一
        String sql = "update sys_user set password =? where account = ? ";
        jdbcTemplate.update(sql, user.getPassword(), user.getAccount());
    }

    /**
     * 根据部门id查询用户数
     * @param deptId
     * @return
     */
    public Integer findUserCountByDeptId(Integer deptId){
        String sql="select count(*) from sys_user where dept_id=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,deptId);
    }

    /**
     * 根据用户信息（account/password）查询用户
     * @param user
     * @return
     */
    public List<User> checkLogin(User user){
        String sql = "select * from sys_user where account= ? and password = ? ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), user.getAccount(), user.getPassword());

    }
}
