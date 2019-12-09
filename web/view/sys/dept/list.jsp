<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <%@ include file="/taglib.jsp" %>
</head>
<script>
</script>

<body>
<%@ include file="/common/head.jsp" %>
<div>
    <%@ include file="/common/menu.jsp" %>
    <div style="border: 1px solid red;width: 88%;height: 85%;float: right;">

        <form action="/sys/dept/deptList" method="get">
            部门名：<input type="text" value="${deptName}" name="deptName">
            <input type="submit" value="查询" class="btn btn-primary">
        </form>

        <a href="/view/sys/dept/add.jsp" class="btn btn-success">添加</a>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>序号</th>
                <th>部门名称</th>
                <th>部门人数</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${list}" var="dept" varStatus="status">
                <tr>
                    <td>${status.index+1}</td>
                    <td>${dept.name}</td>
                    <td>${dept.userCount}</td>
                    <td>${dept.createByName}</td>
                    <td>
                            <%--把日期类型字符串转换成日期对象--%>
                        <fmt:parseDate var="createTime" value="${dept.createTime}"
                                       pattern="yyyy-MM-dd HH:mm:ss"></fmt:parseDate>
                            <%--把日期对象转换成规定的字符串格式--%>
                        <fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                    </td>
                    <td>
                        <a href="/sys/dept/toUpdate?id=${dept.id}" class="btn btn-danger">修改</a>
                        <div class="btn btn-danger" onclick="deleteDept(${dept.id})">删除</div>
                    </td>
                </tr>

            </c:forEach>

            <a href="/sys/dept/deptList?deptName=${deptName}&page=1">首页</a>
            <a href="/sys/dept/deptList?deptName=${deptName}&page=${page.pageCurrent<=1 ? 1 : (page.pageCurrent-1)}">上一页</a>
            <a href="/sys/dept/deptList?deptName=${deptName}&page=${page.pageCurrent>=page.pageCount ? page.pageCount : (page.pageCurrent+1) }">下一页</a>
            <a href="/sys/dept/deptList?deptName=${deptName}&page=${page.pageCount}">末页</a>
            当前页：${page.pageCurrent},总页数：${page.pageCount}，总记录数：${page.count}

            </tbody>
        </table>

    </div>
</div>
</body>
<script>

    function deleteDept(id) {
        console.log("11111111");
        $.ajax({
            url: "/sys/dept/deleteDept",
            data: {id: id},
            type: "get",
            dataType: "text",
            success: function (data) {
                if (data == 200) {
                    location.replace("/sys/dept/deptList");
                } else {
                    alert("该部门所属用户未删除，删除失败！");
                }
            }
        });
    }
</script>
</html>

