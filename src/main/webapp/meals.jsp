<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a>Filter</a>
    <form method="get" action="meals">
        <input hidden name="action" value="filter">
        <table border="0" cellpadding="8" cellspacing="0">

            <thead>
            <tr>
                <th align="left">From date</th>
                <th align="left">To date</th>
                <th></th>
                <th align="left">From time</th>
                <th align="left">To time</th>
            </tr>
            </thead>
            <tr>
                <td><input type="date" name="dateFrom" value="${param.get("dateFrom")}" ></td>
                <td><input type="date" name="dateTo" value="${param.get("dateTo")}"></td>
                <td width="100"></td>
                <td><input type="time" name="timeFrom" value="${param.get("timeFrom")}"></td>
                <td><input type="time" name="timeTo" value="${param.get("timeTo")}"></td>
            </tr>
        </table>
        <button type="submit">Filter</button>
    </form>
    <form method="get" action="meals">
        <button type=submit>Reset</button>
    </form>
    <hr/>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>