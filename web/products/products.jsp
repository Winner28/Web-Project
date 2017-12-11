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
    <title>Products</title>
    <style>
        #gunlist {
            border: 3px solid #555555;
        }
    </style>
</head>
<body>


<%
    List<Gun> gunList = (List<Gun>) request.getAttribute("products");
    if (gunList == null || gunList.size() == 0) {
        request.getRequestDispatcher("/products/ProductsServlet").forward(request,response);
    }

%>

<form action="${pageContext.request.contextPath}/products/ActionBucketServlet" method="post">

<table id="gunlist">
    <tr>
        <th>Add</th>
        <th>Name</th>
        <th>Caliber</th>
        <th>Price</th>
    </tr>
    <c:forEach items="${products}" var="product">
        <tr>

            <td> <input type="checkbox" name="product" value="${product.id}"> </td>
            <td><c:out value="${product.name}" /></td>
            <td><c:out value="${product.caliber}" /></td>
            <td><c:out value="${product.price}" /></td>
        </tr>
    </c:forEach>


</table>
<input type="submit" value="Add to bucket">
</form>


</body>
</html>
