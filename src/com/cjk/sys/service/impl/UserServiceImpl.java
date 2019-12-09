package com.cjk.sys.service.impl;

import com.cjk.sys.dao.UserDao;
import com.cjk.sys.dto.UserDTO;
import com.cjk.sys.entity.Page;
import com.cjk.sys.entity.User;
import com.cjk.sys.service.UserService;
import com.cjk.utils.DateUtil;
import com.cjk.utils.MDUtil;

import java.util.List;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/2
 * @description
 */
public class UserServiceImpl implements UserService {
    UserDao userDao=new UserDao();

    public List<User> listAll(UserDTO userDTO) {
        return userDao.listAll(userDTO);
    }

    public Integer getCount(UserDTO userDTO) {
        return userDao.getCount(userDTO);
    }

    public void add(User user) {
        user.setPassword(MDUtil.md5(user.getPassword()));
        user.setCreateTime(DateUtil.getDateStr());
        userDao.add(user);
    }


    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }

    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void updatePassword(User user) {
        userDao.updatePassword(user);
    }

    @Override
    public Integer findUserCountByDeptId(Integer deptId) {
        return userDao.findUserCountByDeptId(deptId);
    }

    @Override
    public List<User> checkLogin(User user) {
        return userDao.checkLogin(user);
    }
}
