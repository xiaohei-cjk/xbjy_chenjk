package com.cjk.sys.dao;

import com.cjk.sys.entity.Menu;
import com.cjk.utils.DBUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/11/29 15:02
 * @description
 */
public class MenuDao {

    private JdbcTemplate template = new JdbcTemplate(DBUtil.getDataSource());

    public List<Menu> listAll() {
        String sql = "select * from sys_menu order by order_by";
        return template.query(sql, new BeanPropertyRowMapper<>(Menu.class));
    }
}
