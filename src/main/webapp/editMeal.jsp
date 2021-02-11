<%--
  Created by IntelliJ IDEA.
  User: mich
  Date: 09.02.2021
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Meal</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h1>EditMeal</h1>

<form method="POST" action="${pageContext.request.contextPath}/meals?id=${requestScope.meal.id}">
    <table border-collapse="collapse">
        <tr>
            <th>DateTime:</th>
            <th><input type="datetime-local" name="dataTime" value="<c:out value="${requestScope.meal.dateTime}"/>"/></th>
        </tr>
        <tr>
            <th>Description:</th>
            <th><input type="text" name="description" value="<c:out value="${requestScope.meal.description}"/>"/></th>
        </tr>
        <tr>
            <th>Calories:</th>
            <th><input type="text" name="calories" value="<c:out value="${requestScope.meal.calories}"/>"/></th>
        </tr>
    </table>
    <br>
    <input type="submit" value="Save"/>
    <input type="button" onclick="window.history.back()" value="Cancel">
</form>
</body>
</html>
