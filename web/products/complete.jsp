<%@ page import="java.util.List" %>
<%@ page import="model.Gun" %><%--
  Created by IntelliJ IDEA.
  User: vladey
  Date: 08.12.17
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
    <title>Complete Orders</title>
    <style>
        #gunlist {
            border: 3px solid #555555;
        }
    </style>
</head>
<body>


<%
    List<Gun> gunList = (List<Gun>) request.getAttribute("order_products");
    if (gunList == null || gunList.size() == 0) {
        request.getRequestDispatcher("/products/CompleteOrdersServlet").forward(request,response);
    }

%>



    <table id="gunlist">
        <tr>

            <th>Name</th>
            <th>Caliber</th>
            <th>Price</th>
        </tr>
        <c:forEach items="${order_products}" var="product">
            <tr>

                <td><c:out value="${product.name}" /></td>
                <td><c:out value="${product.caliber}" /></td>
                <td><c:out value="${product.price}" /></td>
            </tr>
        </c:forEach>


    </table>

<br>

<form action="${pageContext.request.contextPath}/profile/profile.jsp" method="post">
    <input type="submit" value="Back to profile">
</form>


</body>
</html>
