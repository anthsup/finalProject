<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="sessionScope.lastPageURI" value="/resources/login.jsp"/>

<%--<!-- internationalization -->--%>
<%--<c:choose>--%>
    <%--<c:when test="${not empty sessionScope.locale}">--%>
        <%--<fmt:setLocale value="${sessionScope.locale}"/>--%>
    <%--</c:when>--%>
    <%--<c:otherwise>--%>
        <%--<fmt:setLocale value="en_US"/>--%>
    <%--</c:otherwise>--%>
<%--</c:choose>--%>
<%--<fmt:setBundle basename="Messages"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Login</title>
    <%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="This is home page of movie rating web application."/>
    <link rel="stylesheet" href="/resources/css/movie_rating_styles.css"/>
    <link rel="stylesheet" href="/resources/css/index_styles.css"/>
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/resources/css/bootstrap-theme.min.css"/>
    <script src="/resources/js/jquery-3.1.0.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/navigation.js"></script>
</head>

<body>
<mytags:navbar/>
<section class="container">
    <div class="well text-center">
        <h1>
            ${pageContext.errorData.statusCode}<br/>
            ${pageContext.exception.message}<br/>
        </h1>
        <c:if test="${sessionScope.user.role eq 'ADMIN'}">
            <b>Error: </b>${pageContext.exception} <br/>
            <b>Stack trace:</b><br/>
            <c:forEach var="trace"
                       items="${pageContext.exception.stackTrace}">
                <p>${trace}</p>
            </c:forEach>
        </c:if>
    </div>
</section>
</body>

</html>