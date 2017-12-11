<%--
  Created by IntelliJ IDEA.
  User: devladey
  Date: 11.12.17
  Time: 11.55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order page</title>
</head>
<body>
<h1> Order is complete! </h1>
<h2>Total price is: <%out.println(String.valueOf(request.getAttribute("order_price")));%></h2>
<form action="${pageContext.request.contextPath}/profile/profile.jsp" method="post">
    <input type="submit" value="Back to profile">
</form>

</body>
</html>
