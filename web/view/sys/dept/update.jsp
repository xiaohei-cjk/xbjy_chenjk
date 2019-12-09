<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <%@ include file="/taglib.jsp" %>
    <script src="${path}/static/jquery-validation-1.9.0/jquery.validate.js"></script>
</head>
<style>
    .error {
        color: red;
    }

</style>

<body>
<%@ include file="/common/head.jsp" %>
<div>
    <%@ include file="/common/menu.jsp" %>
    <div style="border: 1px solid red;width: 88%;height: 85%;float: right;">

        <form action="/sys/dept/update" method="get" id="form-add">
            <%--隐藏域--%>
            <input type="text" name="id" value="${dept.id}" style="display: none;">

            <div class="form-group">
                <label>部门名</label>
                <input type="text" class="form-control" id="deptName" name="name" placeholder="请输入部门名" value="${dept.name}">
            </div>
            <!--居中-->
            <div class="text-center">
                <button type="submit" class="btn btn-primary">保存</button>
                <button type="reset" class="btn btn-default">重置</button>
                <a href="/sys/dept/deptList" class="btn btn-danger">返回</a>
            </div>
        </form>

    </div>
</div>
</body>
<script>
    $(function () {
        $.noConflict();

        $("#form-add").validate({
            rules: {
                name: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "请输入部门名！"
                }
            }
        });

    })
    ;
</script>
</html>
