package com.cjk.filters;


import com.cjk.sys.constants.SysConstant;
import com.cjk.sys.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/4
 * @description
 */
@WebFilter("/*")
public class CheckLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        HttpSession session =request.getSession();


        String uri=request.getRequestURI();
        if (uri.endsWith("index.jsp")||uri.endsWith("/login/login")||uri.endsWith("/login/getPic")||uri.endsWith("/view/sys/login/forget.jsp")||uri.endsWith("/sys/email/sendEmail")||uri.endsWith("/sys/user/forgetPassword")){

        }else {
            Object obj = session.getAttribute(SysConstant.SESSION_LOGIN_NAME);
            if (null==obj){
                response.sendRedirect("/index.jsp");
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
