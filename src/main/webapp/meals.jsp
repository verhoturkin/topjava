<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr noshade="true">
<h2>Meals</h2>
<c:if test="${meals != null}">
<table cellpadding="2" cols="3">
    <thead>
    <tr>
        <th width="50"> ID</th>
        <th width="200">Date / Time</th>
        <th width="100">Description</th>
        <th width="100">Calories</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach var="meal" items="${meals}">
        <tr style="color:${meal.excess ? "red" : "green"}">
            <td align="center">${meal.id}</td>
            <td align="center">
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" type="both" var="parsedDateTime"/>
                <fmt:formatDate value="${parsedDateTime}" pattern="yyyy.MM.dd HH:mm"/>
            </td>
            <td align="center">${meal.description}</td>
            <td align="center">${meal.calories}</td>
            <td><a href="meals?action=edit&id=${meal.id}">EDIT</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">DELETE</a></td>
        </tr>
    </c:forEach>
    </c:if>
    </tbody>
</table>
<a href="meals?action=add">ADD MEAL</a>
</body>
</html>
