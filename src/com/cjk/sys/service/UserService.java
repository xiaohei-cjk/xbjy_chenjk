package com.cjk.sys.service;

import com.cjk.sys.dto.UserDTO;
import com.cjk.sys.entity.Page;
import com.cjk.sys.entity.User;

import java.util.List;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/2
 * @description
 */
public interface UserService {


    /**
     * 查询所有用户
     * @return
     */
    public List<User> listAll(UserDTO userDTO);

    /**
     * 查询用过总条数
     * @param userDTO
     * @return
     */
    public Integer getCount(UserDTO userDTO);

    /**
     * 添加用户
     * @param user
     */
    public void add(User user);

    /**
     * 根据id删除用户
     * @param id
     */
    public void deleteById(Integer id);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public User findUserById(Integer id);

    /**
     * 修改用户
     * @param user
     */
    public void updateUser(User user);

    /**
     * 修改用户密码
     * @param user
     */
    public void updatePassword(User user);

    /**
     * 根据部门id查询用户数
     * @param deptId
     * @return
     */
    public Integer findUserCountByDeptId(Integer deptId);

    /**
     * 根据用户信息（account/password）查询用户
     * @param user
     * @return
     */
    public List<User> checkLogin(User user);
}
