<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>忘记密码</title>
    <%@ include file="/taglib.jsp" %>
</head>
<body>

<form action="/sys/user/forgetPassword" method="get">
    账号：<input type="text" name="account" value=""><br><br>
    新密码：<input type="text" name="password" value=""><br><br>
    邮箱：<input type="text" id="email" value="">
    <input type="button" value="发送验证码" id="btn-send"/> <span id="time"></span><br><br>
    验证码：<input type="text" name="code" value=""><br><br>
    <input type="submit" name="" value="修改">

</form>

</body>
<script>

    var time = 60;
    //定时器
    var dsq;

    $(function () {
        $("#btn-send").click(function () {

            dsq = window.setInterval("changeTime()", 1000);
            $("#btn-send").attr("disabled", "disabled");

            var email = $("#email").val();
            $.ajax({
                url: "/sys/email/sendEmail",
                data: {email: email},
                type: "get",
                dataType: "text",
                success: function (data) {

                }
            });

        });
    });

    function changeTime() {
        --time;
        $("#time").text(time);
        if (time == 0) {
            $("#time").text("");
            time = 60;
            window.clearInterval(dsq);
            $("#btn-send").attr("disabled", null);
        }
    }

</script>

</html>
