<%--
  Created by IntelliJ IDEA.
  User: vladey
  Date: 11.12.17
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Empty</title>
</head>

<body>

<h1><%out.print(request.getAttribute("value"));%>  is empty!</h1>

<form action="${pageContext.request.contextPath}/profile/profile.jsp" method="post">
    <input type="submit" value="Profile">
</form>

</body>

</html>
