package com.cjk.sys.service.impl;

import com.cjk.sys.dao.MenuDao;
import com.cjk.sys.entity.Menu;
import com.cjk.sys.service.MenuService;

import java.util.List;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/11/29 15:06
 * @description 菜单ServiceImpl
 */
public class MenuServiceImpl implements MenuService {

    private MenuDao menuDao = new MenuDao();

    /***
     * @decription 查询所有菜单
     * @author 陈俊奎
     * @date 2019/11/29 15:08
     * @params []
     * @return
     */
    @Override
    public List<Menu> listAll() {
        return menuDao.listAll();
    }
}
