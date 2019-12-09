package com.cjk.base;

import com.cjk.sys.constants.SysConstant;
import com.cjk.sys.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 陈俊奎
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/2
 * @description
 */
public class BaseServlet extends HttpServlet {

    //登陆信息
    private User loginUser = new User();

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        //取出session中的登陆信息
        HttpSession session = req.getSession();
        loginUser = (User) session.getAttribute(SysConstant.SESSION_LOGIN_NAME);

        String requestURI = req.getRequestURI();
        String[] uriStrings = requestURI.split("/");
        String methodStr = uriStrings[uriStrings.length-1];

        Class clazz=this.getClass();

        try {
            Method method=clazz.getDeclaredMethod(methodStr,HttpServletRequest.class,HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this,req,resp);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }


    }
}
