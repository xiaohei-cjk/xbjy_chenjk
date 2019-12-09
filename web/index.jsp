<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.alibaba.fastjson.TypeReference" %>
<%@ page import="com.cjk.sys.entity.User" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="/static/css/bootstrap-3.3.7-dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="/static/css/style.css">
  <link rel="stylesheet" href="/static/css/form-elements.css">
  <link rel="stylesheet" href="/static/css/font-awesome.min.css">
  <title>登陆界面</title>
  <style>
    body {
      background: url("/static/img/bg.jpg") no-repeat fixed;
      background-size: cover;
      overflow: hidden;
    }

  </style>

  <%
    Cookie[] cookies=request.getCookies();
    Integer value=0;
    User loginUser = new User();
    if (cookies!=null){
      for (int i = 0; i <cookies.length ; i++) {
        if ("cookieLoginUser".equals(cookies[i].getName())) {
          value = 1;
          //获取cookie的值
          String cookieValue = cookies[i].getValue();
          //解码
          String strJson = URLDecoder.decode(cookieValue, "utf-8");
          //把json字符串转化为java对象
          loginUser = JSON.parseObject(strJson, new TypeReference<User>() {
          });

        }
      }
    }
  %>
</head>
<body>
<form action="/login/login" method="get">
  账号：<input type="text" name="account" value="<%=loginUser.getAccount()==null?"":loginUser.getAccount()%>"><br><br>
  密码：<input type="text" name="password" value="<%=loginUser.getPassword()==null?"":loginUser.getPassword()%>"><a href="/view/sys/login/forget.jsp">忘记密码？</a><br><br>
  <img src="/login/getPic" alt="无法加载" id="img" onclick="getPic()">
  验证码：<input type="text" name="picCode" value=""><br><br>
  7天免登录：
  <c:if test="<%=value==1%>">
    <input type="checkbox" name="remember" value="1" checked="checked"><br><br>
  </c:if>
  <c:if test="<%=value==0%>">
    <input type="checkbox" name="remember" value="1" ><br><br>
  </c:if>
  <input type="submit" name="" value="登录">
</form>

<%--<a href="/common/home.jsp">登陆</a>--%>
<%--<a href="/view/sys/user/forget.jsp">忘记密码？</a>--%>
</body>
<script>
    function getPic() {
        // $("#img").attr("src", $("#img").attr("src") + "?nocache="+new Date().getTime());
        document.getElementById("img").src = document.getElementById("img").src + "?nocache=" + new Date().getTime();
    }
</script>

</html>
