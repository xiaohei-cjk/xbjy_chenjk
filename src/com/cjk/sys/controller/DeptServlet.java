package com.cjk.sys.controller;

import com.alibaba.fastjson.JSON;
import com.cjk.base.BaseServlet;
import com.cjk.sys.entity.Dept;
import com.cjk.sys.entity.Page;
import com.cjk.sys.service.DeptService;
import com.cjk.sys.service.UserService;
import com.cjk.sys.service.impl.DeptServiceImpl;
import com.cjk.sys.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/2
 * @description
 */
@WebServlet("/sys/dept/*")
public class DeptServlet extends BaseServlet {
    private void test(HttpServletRequest req, HttpServletResponse resp){
        System.out.println("反射测试！！！");
    }

    DeptService deptService=new DeptServiceImpl();
    UserService userService=new UserServiceImpl();

    /**
     * 查询所有部门（不分页）
     * @param request
     * @param response
     * @return
     */
    public void allDept(HttpServletRequest request,HttpServletResponse response) throws IOException {
        List<Dept> allDept = deptService.findAllDept();
        response.getWriter().append(JSON.toJSONString(allDept));
    }

    /**
     * 查询部门（分页）
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deptList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String deptName = request.getParameter("deptName");
        deptName=deptName==null?"":deptName;


        //当前页
        String pageStr = request.getParameter("page");
        //查询总记录数
        Integer count = deptService.getCount(deptName);

        Page page=new Page();
        page.setCount(count);
        Integer pageCurrent = pageStr == null ? 1 : Integer.valueOf(pageStr);
        page.setPageCurrent(pageCurrent);


        List<Dept> deptList=deptService.listAll(deptName,page);

        //查询的数据
        request.setAttribute("list", deptList);
        //查询条件
        request.setAttribute("deptName", deptName);
        //分页信息
        request.setAttribute("page", page);

        request.getRequestDispatcher("/view/sys/dept/list.jsp").forward(request, response);
    }

    /**
     * 删除部门
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void deleteDept(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        String deptIdStr = request.getParameter("id");
        Integer deptId=deptIdStr==null?0:Integer.valueOf(deptIdStr);

        Integer userCountByDeptId = userService.findUserCountByDeptId(deptId);
        if(userCountByDeptId<=0){
            deptService.deleteDeptById(deptId);
            response.getWriter().append("200");
            return;
        }
        response.getWriter().append("删除失败！");
    }

    /**
     * 添加部门
     * @param request
     * @param response
     */
    public void add(HttpServletRequest request,HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {
        Dept dept=new Dept();

        Map<String, String[]> map = request.getParameterMap();
        BeanUtils.populate(dept, map);
        dept.setCreateBy(super.getLoginUser().getId());
        deptService.add(dept);

        response.sendRedirect("/sys/dept/deptList");
    }

    /**
     * 根据部门id查询部门并前往修改页面
     * @param request
     * @param response
     */
    public void toUpdate(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String idStr=request.getParameter("id");
        Integer id=idStr==null?0:Integer.valueOf(idStr);

        Dept dept = deptService.findDeptById(id);

        request.setAttribute("dept",dept);
        request.getRequestDispatcher("/view/sys/dept/update.jsp").forward(request, response);
    }

    /**
     * 修改部门
     * @param request
     * @param response
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IOException
     */
    public void update(HttpServletRequest request,HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {
        Dept dept = new Dept();
        Map<String, String[]> map = request.getParameterMap();
        BeanUtils.populate(dept, map);
        deptService.updateDept(dept);
        response.sendRedirect("/sys/dept/deptList");
    }
}
