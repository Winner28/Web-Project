<%@ page import="model.User" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Профиль пользователя</title>
    <style>
        #user {
            border: 1px solid #333;
        }
    </style>
</head>
<body>

<%
    User userSess = (User) session.getAttribute("user");
    if (userSess == null) {
        request.getRequestDispatcher("/pages/login.html").forward(request, response);

    }
    else if (userSess.getId() == 0) {
        request.getRequestDispatcher("/pages/login.html").forward(request, response);
    }
%>

<jsp:useBean id="user" scope="session" class="model.User"/>

<table id="user">
    <tr>
        <th>название</th>
        <th>значение</th>
    </tr>
    <tr>
        <td>id</td>
        <td>${user.id}</td>
    </tr>
    <tr>
        <td>Имя</td>
        <td>${user.name}</td>
    </tr>
    <tr>
        <td>Логин</td>
        <td>${user.userName}</td>
    </tr>
</table>

<div>
    <br>

    <form action="${pageContext.request.contextPath}/products/MainBucketServlet" method="post">
        <input type="submit" value="Сделать заказ">
    </form>

    <form action="${pageContext.request.contextPath}/products/ProductsServlet" method="post">
        <input type="submit" value="Посмотреть все товары">
    </form>

    <form action="${pageContext.request.contextPath}/products/MainBucketServlet" method="post">
        <input type="submit" value="Корзина">
    </form>


    <form action="${pageContext.request.contextPath}/products/CompleteOrdersServlet" method="post">
        <input type="submit" value="Мои заказы">
    </form>




    <form action="${pageContext.request.contextPath}/pages/LogoutServlet" method="post">
    <input type="submit" value="Log out">
</form>


</div>

</body>
</html>