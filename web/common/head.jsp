<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <%@ include file="/taglib.jsp" %>
</head>
<body>
<div style="border: 1px solid red;height:10%;">
    <div>
        <h5>
            <%--从application作用域中取出在线人数--%>
            当前在线人数：${applicationScope.applicationLoginCount}
        </h5>
        <div class="btn btn-success">
            <!-- Log out -->
            <a id="logout" href="/login/logout" ><span>Logout</span></a>
        </div>
    </div>
</div>
</body>
</html>
