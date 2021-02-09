<%--
  Created by IntelliJ IDEA.
  User: mich
  Date: 08.02.2021
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>

<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h1>Meals</h1>
<a href="/topjava/meals?action=add">Add meal</a>
<table border="solid black">
    <tr>
        <th><p>Date</p></th>
        <th><p>Description</p></th>
        <th><p>Calories</p></th>
        <th><p></p></th>
        <th><p></p></th>
    </tr>
    <c:forEach items="${requestScope.meals}" var="meal">
        <tr style="color:${ (meal.excess ? 'red' : 'green')}">
            <td>
                <p>
                    <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />
                </p>

            </td>
            <td><p>${meal.description}</p></td>
            <td><p>${meal.calories}</p></td>
            <td><p><a href="/topjava/meals?action=update&mealId=<c:out value="${meal.id}"/>"> Update </a></p></td>
            <td><p><a href="/topjava/meals?action=delete&mealId=<c:out value="${meal.id}"/>"> Delete </a></p></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
