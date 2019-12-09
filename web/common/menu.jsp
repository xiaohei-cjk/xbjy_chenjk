<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<script src="/static/js/jquery-3.3.1.js"></script>
<body>
<div style="border: 1px solid red;width: 10%;height: 85%;float: left;" id="div-menu">
<script>

    $.ajax({
        url: "/sys/menu",
        type: "get",
        data: "",
        dataType: "json",
        success: function (data) {
            console.log(data);

            var parent = data.parent;
            var son = data.son;
            console.log(son);
            var html = "";
            for (var i = 0; i < parent.length; i++) {
                html = html + parent[i].name;
                html = html + '<ul>';
                for (var j = 0; j < son.length; j++) {
                    if (parent[i].id == son[j].pId) {
                        html = html + '<li><a href="' + son[j].menuUrl + '">' + son[j].name + '</a></li>';
                    }
                }
                html = html + '</ul>';
            }

            $("#div-menu").append(html);
        }
    });

</script>


</div>
</body>
</html>
