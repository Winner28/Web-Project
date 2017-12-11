<%@ page import="java.util.List" %>
<%@ page import="model.Gun" %>
<%@ page import="java.io.PrintWriter" %><%--

  Created by IntelliJ IDEA.
  User: vladey
  Date: 08.12.17
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
    <title>Bucket</title>
    <style>
        #gunlist {
            border: 3px solid #555555;
        }
    </style>
</head>
<body>


<%
    List<Gun> gunList = (List<Gun>) request.getAttribute("bucket_products");
    if (session.getAttribute("user") == null) {
        request.getRequestDispatcher("/pages/login.html").forward(request, response);
    } else if (gunList == null) {
        request.getRequestDispatcher("/products/MainBucketServlet").forward(request,response);
    } else if(gunList.size() == 0) {
        response.sendRedirect("/products/empty.html");
    }

%>

<h1> Ваша корзина </h1>
<form action="${pageContext.request.contextPath}/products/OrderServlet" method="post">

    <table id="gunlist">
        <tr>

            <th>Name</th>
            <th>Caliber</th>
            <th>Price</th>
        </tr>
        <c:forEach items="${bucket_products}" var="product">
            <tr>

                <td><c:out value="${product.name}" /></td>
                <td><c:out value="${product.caliber}" /></td>
                <td><c:out value="${product.price}" /></td>

            </tr>
        </c:forEach>


    </table>

    <h3>Total price: <%
        out.println(String.valueOf(request.getAttribute("order_price")));
        %></h3>
    <input type="hidden" name="order_price" value='<%=String.valueOf(request.getAttribute("order_price"))%>'>
    <input type="submit" value="Make an order">
</form>


</body>
</html>
