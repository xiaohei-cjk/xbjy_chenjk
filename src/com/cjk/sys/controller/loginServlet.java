package com.cjk.sys.controller;

import com.alibaba.fastjson.JSON;
import com.cjk.base.BaseServlet;
import com.cjk.sys.constants.SysConstant;
import com.cjk.sys.entity.User;
import com.cjk.sys.service.UserService;
import com.cjk.sys.service.impl.UserServiceImpl;
import com.cjk.utils.ImgCodeUtil;
import com.cjk.utils.MDUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/4
 * @description
 */
@WebServlet("/login/*")
public class loginServlet extends BaseServlet {

    UserService userService = new UserServiceImpl();

    /**
     * 登陆验证
     *
     * @param request
     * @param response
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String picCode = request.getParameter("picCode");
        String remember = request.getParameter("remember");
        remember = remember == null ? "" : remember;

        HttpSession session = request.getSession();

        Object obj = session.getAttribute(SysConstant.SESSION_PIC_CODE_NAME);
        if (obj == null || !obj.toString().equals(picCode)) {
            //验证码不正确
            response.sendRedirect("/index.jsp");
            return;
        }

        User user = new User();
        user.setAccount(account);
        //密文
        user.setPassword(MDUtil.md5(password));

        List<User> list = userService.checkLogin(user);

        if (list == null || list.size() == 0 || list.size() > 1) {
            //登录失败，跳转到登录界面
            response.sendRedirect("/index.jsp");
            return;
        } else {
            //验证通过
            User user1= (User) session.getAttribute(SysConstant.SESSION_LOGIN_NAME);
            if(user1==null||!user1.getAccount().equals(account)){
                //在线人数
                ServletContext application = getServletContext();
                //取出application中的在线人数
                Object countObj = application.getAttribute(SysConstant.APPLICATION_LOGIN_COUNT);
                int count = 1;
                if (countObj != null) {
                    count = Integer.valueOf(countObj.toString()) + 1;
                }
                //把新的在线人数存入application中
                application.setAttribute(SysConstant.APPLICATION_LOGIN_COUNT, count);
            }

            User loginUser = list.get(0);
            session.setAttribute(SysConstant.SESSION_LOGIN_NAME, loginUser);

            //如果勾选7天免登录，则把登陆信息放入cookie，cookie中不能直接存对象，所以只能存入字符串
            if ("1".equals(remember)) {
                loginUser.setPassword(password);
                String strJson = JSON.toJSONString(loginUser);
                strJson = URLEncoder.encode(strJson, "utf-8");
                Cookie cookLoginUser = new Cookie(SysConstant.COOKIE_LOGIN_USER, strJson);
                cookLoginUser.setMaxAge(7 * 24 * 60 * 60);
                //任何请求都能获取cookie
                cookLoginUser.setPath("/");
                response.addCookie(cookLoginUser);
            }
            if("".equals(remember)){
                Cookie[] cookies=request.getCookies();
                for (int i = 0; i <cookies.length ; i++) {
                    if ("cookieLoginUser".equals(cookies[i].getName())){
                        cookies[i].setMaxAge(0);
                    }
                    cookies[i].setPath("/");
                    response.addCookie(cookies[i]);
                }
            }
            request.getRequestDispatcher("/common/home.jsp").forward(request, response);
            return;
        }
    }

    /**
     * 获取图片验证码
     *
     * @param request
     * @param response
     */
    public void getPic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImgCodeUtil imgCodeUtil = new ImgCodeUtil();
        //获取验证码图片
        BufferedImage image = imgCodeUtil.getImage();

        ////获取验证码文本内容
        String code = imgCodeUtil.getText();

        //把图片验证码存入session
        HttpSession session = request.getSession();
        session.setAttribute(SysConstant.SESSION_PIC_CODE_NAME, code);

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(image, "jpeg", sos);
        sos.flush();
        sos.close();
    }

    /**
     * 登出
     * @param request
     * @param response
     */
    public void logout(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        session.setAttribute(SysConstant.SESSION_LOGIN_NAME,null);
        //在线人数
        ServletContext application = getServletContext();
        //取出application中的在线人数
        Object countObj = application.getAttribute(SysConstant.APPLICATION_LOGIN_COUNT);
        int count = 0;
        if (countObj != null&&Integer.valueOf(countObj.toString())>0) {
            count = Integer.valueOf(countObj.toString()) - 1;
        }
        //把新的在线人数存入application中
        application.setAttribute(SysConstant.APPLICATION_LOGIN_COUNT, count);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
