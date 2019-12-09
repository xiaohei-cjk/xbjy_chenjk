package com.dfbz.sys.controller;

import com.dfbz.sys.constants.SysConstant;
import com.dfbz.sys.entity.User;
import com.dfbz.sys.service.impl.UserServiceImpl;
import com.dfbz.sys.utils.MDUtil;
import com.dfbz.sys.utils.ImgCodeUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * @author admin
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/4 11:06
 * @description
 */
@WebServlet("/sys/login/*")
public class LoginServlet extends BaseServlet {

    private UserServiceImpl service = new UserServiceImpl();

    /***
     * @decription 验证账号和密码
     * @author admin
     * @date 2019/12/4 14:48
     * @params [user]
     * @return com.dfbz.sys.entity.User
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String picCode = request.getParameter("picCode");

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

        List<User> list = service.checkLogin(user);
        //账号或密码不正确或系统存在相同的账号和密码
        if (list == null || list.size() == 0 || list.size() > 1) {
            //登录失败，跳转到登录界面
            response.sendRedirect("/index.jsp");
            return;
        } else {
            //验证通过
            session.setAttribute(SysConstant.SESSION_LOGIN_NAME, list.get(0));

            request.getRequestDispatcher("/view/common/home.jsp").forward(request, response);
            return;
        }
    }

    /***
     * @decription 获取图片验证码
     * @author admin
     * @date 2019/12/4 16:51
     * @params [request, response]
     * @return void
     */
    public void getPic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}
