package com.cjk.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cjk.base.BaseServlet;
import com.cjk.sys.entity.Menu;
import com.cjk.sys.service.impl.MenuServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/11/29
 * @description 菜单controller
 */
@WebServlet("/sys/menu")
public class MenuServlet extends BaseServlet {

    private MenuServiceImpl service = new MenuServiceImpl();

//    @Override
//    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<Menu> list = service.listAll();
//
//        //1级菜单
//        List<Menu> listParent =new ArrayList<>();
//        //2级菜单
//        List<Menu> listSon = new ArrayList<>();
//
//
//        listParent = list.stream().filter(menu -> {
//            return menu.getType().equals("1");
//        }).collect(Collectors.toList());
//
//        listSon = list.stream().filter(menu -> {
//            return menu.getType().equals("2");
//        }).collect(Collectors.toList());
//
//        Map<String, List<Menu>> map = new HashMap<>();
//        map.put("parent", listParent);
//        map.put("son", listSon);
//
//        String mapJsonStr = JSON.toJSONString(map);
//        Map<String, List<Menu>> map2 = JSON.parseObject(mapJsonStr, new TypeReference<Map<String, List<Menu>>>() {
//        });
//        PrintWriter out = response.getWriter();
//        out.append(mapJsonStr);
//    }

    public void menu(HttpServletRequest request, HttpServletResponse response){
        List<Menu> list = service.listAll();

        //1级菜单
        List<Menu> listParent =new ArrayList<>();
        //2级菜单
        List<Menu> listSon = new ArrayList<>();


        listParent = list.stream().filter(menu -> {
            return menu.getType().equals("1");
        }).collect(Collectors.toList());

        listSon = list.stream().filter(menu -> {
            return menu.getType().equals("2");
        }).collect(Collectors.toList());

        Map<String, List<Menu>> map = new HashMap<>();
        map.put("parent", listParent);
        map.put("son", listSon);

        String mapJsonStr = JSON.toJSONString(map);
        Map<String, List<Menu>> map2 = JSON.parseObject(mapJsonStr, new TypeReference<Map<String, List<Menu>>>() {
        });
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.append(mapJsonStr);
    }
}
