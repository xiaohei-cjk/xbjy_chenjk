package com.cjk.sys.controller;

import com.cjk.base.BaseServlet;
import com.cjk.sys.constants.SysConstant;
import com.cjk.sys.dto.UserDTO;
import com.cjk.sys.entity.Page;
import com.cjk.sys.entity.User;
import com.cjk.sys.service.UserService;
import com.cjk.sys.service.impl.UserServiceImpl;
import com.cjk.utils.MDUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
@WebServlet("/sys/user/*")
public class UserServlet extends BaseServlet {

    UserService userService=new UserServiceImpl();


    /**
     * 查询所有用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void userList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //查询条件
        String account = request.getParameter("account");
        account = account == null ? "" : account;

        String beginTime=request.getParameter("beginTime");
        String engTime=request.getParameter("endTime");
        request.setAttribute("beginTime",beginTime);
        request.setAttribute("endTime",engTime);

        beginTime = (beginTime == null || beginTime == "") ? "0000-00-00" : beginTime;
        engTime = (engTime == null || engTime == "") ? "9999-12-31" : engTime;

        UserDTO userDTO=new UserDTO();
        userDTO.setAccount(account);
        userDTO.setBeginTime(beginTime);
        userDTO.setEndTime(engTime);

        //当前页
        String pageStr = request.getParameter("page");
        //查询总记录数
        Integer count = userService.getCount(userDTO);

        Page page = new Page();
        page.setCount(count);
        Integer pageCurrent = pageStr == null ? 1 : Integer.valueOf(pageStr);
        page.setPageCurrent(pageCurrent);
        userDTO.setPage(page);


        List<User> list = userService.listAll(userDTO);
        //查询的数据
        request.setAttribute("list", list);
        //查询条件
        request.setAttribute("account", account);
        //分页信息
        request.setAttribute("page", page);

        request.getRequestDispatcher("/view/sys/user/list.jsp").forward(request, response);
    }

    /**
     * 添加用户
     * @param request
     * @param response
     * @throws Exception
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = new User();
        //获取请求体里面的数据
        Map<String, String[]> map = request.getParameterMap();
        BeanUtils.populate(user, map);
        //设置创建人
        user.setCreateBy(super.getLoginUser().getId());
        userService.add(user);
        response.sendRedirect("/sys/user/userList");
    }

    /**
     * 根据id删除用户
     * @param request
     * @param response
     * @throws Exception
     */
    public void deleteById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        if (id == null) {
            return;
        }
        userService.deleteById(Integer.valueOf(id));
        response.sendRedirect("/sys/user/userList");
    }

    /**
     * 根据用户id查询用户前往修改页面
     * @param request
     * @param response
     */
    public void toUpdate(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        Integer id=idStr==null?0:Integer.valueOf(idStr);

        request.setAttribute("user",userService.findUserById(id));
        request.getRequestDispatcher("/view/sys/user/update.jsp").forward(request, response);
    }

    /**
     * 修改用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void updateUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        Map<String, String[]> map = request.getParameterMap();
        BeanUtils.populate(user, map);
        userService.updateUser(user);
        response.sendRedirect("/sys/user/userList");
    }

    /**
     * 修改密码
     * @param request
     * @param response
     * @throws Exception
     */
    public void forgetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        String code = request.getParameter("code");

        HttpSession session = request.getSession();
        Object object = session.getAttribute(SysConstant.SESSION_EMAIL_CODE_NAME);

        //比较前端输入的code和session中的code
        if (object == null || !code.equals(object.toString())) {
            response.sendRedirect("/view/sys/user/forget.jsp");
            return;
        }

        User user = new User();
        user.setAccount(account);
        user.setPassword(MDUtil.md5(password));
        userService.updatePassword(user);
        //修改完毕，跳转到登录界面
        response.sendRedirect("/index.jsp");

    }
}
