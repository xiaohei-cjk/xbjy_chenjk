<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改用户</title>
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

        <form action="/sys/user/updateUser" method="get" id="form-add">

            <%--隐藏域--%>
            <input type="text" name="id" value="${user.id}" style="display: none;">

            <div class="form-group">
                <label>部门</label>

                <%--<select id="dept" name="deptId" class="form-control">--%>
                <%--<c:forEach items="${deptList}" var="dept">--%>
                <%--<option value="${dept.id}"--%>
                <%--<c:if test="${user.deptId==dept.id}">selected</c:if> >${dept.name}</option>--%>
                <%--</c:forEach>--%>
                <%--</select>--%>

                <select id="dept" name="deptId" class="form-control">

                </select>

            </div>

            <div class="form-group">
                <label>账号</label>
                <input type="text" class="form-control" id="account" value="${user.account}" name="account"
                       placeholder="请输入账号">
            </div>
            <div class="form-group">
                <label>姓名</label>
                <input type="text" class="form-control" id="name" value="${user.name}" name="name" required>
            </div>
            <div class="form-group">
                <label>年龄</label>
                <input type="text" class="form-control" id="age" value="${user.age}" name="age">
            </div>
            <div class="form-group">
                <label>性别</label>
                <input type="radio" id="male" name="sex" value="1"
                       <c:if test="${user.sex==1}">checked</c:if> ><label for="male">男</label>
                <input type="radio" id="female" name="sex" value="0"
                       <c:if test="${user.sex==0}">checked</c:if> ><label for="female">女</label>
            </div>
            <div class="form-group">
                <label>邮箱</label>
                <input type="text" class="form-control" id="email" name="email" value="${user.email}" required>
            </div>
            <div class="form-group">
                <label>出生日期</label>
                <input type="date" class="form-control" id="birthDate" value="${user.birthDate}" name="birthDate">
            </div>
            <!--居中-->
            <div class="text-center">
                <button type="submit" class="btn btn-primary">保存</button>
                <button type="reset" class="btn btn-default">重置</button>
                <a href="/sys/user/userList" class="btn btn-danger">返回</a>
            </div>
        </form>

    </div>
</div>
</body>
<script>
    $(function () {

        var deptId =${user.deptId};

        //获取部门
        $.ajax({
            url: "/sys/dept/allDept",
            data: "",
            type: "get",
            dataType: "json",
            success: function (data) {
                var html = '';
                for (var i = 0; i < data.length; i++) {
                    // if (data[i].id == deptId) {
                    //     html = html + '<option selected value="' + data[i].id + '">' + data[i].name + '</option>';
                    // } else {
                    //     html = html + '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                    // }
                    if (data[i].id == deptId) {
                        html = html + '<option selected value="' + data[i].id + '">' + data[i].name + '</option>';
                        continue;
                    }
                    html = html + '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                }
                $("#dept").append(html);
            }
        });

        // 表单验证start
        $.noConflict();
        $.validator.addMethod("checkAccount", function (value, element, params) {
            var reg = /^[0-9a-zA-Z]{5,10}$/;
            if (reg.test(value)) {
                return true;
            } else {
                return false;
            }
        });

        $.validator.addMethod("checkDept", function (value, element, params) {
            if (value>0) {
                return true;
            } else {
                return false;
            }
        });


        $("#form-add").validate({
            rules: {
                account: {
                    checkAccount: ""
                },
                deptId:{
                    checkDept:""
                }
            },
            messages: {
                account: {
                    checkAccount: "请输入5-10位的账号！"
                },
                deptId: {
                    checkDept: "请选择部门！"
                }
            }
        });
        // 表单验证end

    })
    ;
</script>
</html>

