package com.cjk.sys.controller;

import com.cjk.base.BaseServlet;
import com.cjk.sys.constants.SysConstant;
import com.cjk.utils.EmailUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/3
 * @description
 */

@WebServlet("/sys/email/*")
public class EmailServlet extends BaseServlet {

    public void sendEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取邮箱地址
        String emailName = request.getParameter("email");
        //生成随机的4位验证码
        int code = (int) ((Math.random() + 1) * 1000);

        //发送验证码
        EmailUtil.sendEmail(emailName, String.valueOf(code));

        //把验证码存到session中
        HttpSession session = request.getSession();
        session.setAttribute(SysConstant.SESSION_EMAIL_CODE_NAME, code);
        //设置有效时间60秒
        session.setMaxInactiveInterval(60);

        PrintWriter out = response.getWriter();
        out.append("发送邮件成功！");
    }
}
