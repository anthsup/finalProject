<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<html lang="${locale}">
<head>
    <title>Users</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/users-style.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container bootstrap snippet">
    <div class="row">
        <div class="col-xl-12">
            <div class="main-box no-header clearfix">
                <div class="main-box-body clearfix">
                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-6">
                                <h2><b><fmt:message key="users.management"/></b></h2>
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table user-list table-striped table-hover">
                            <thead>
                            <tr>
                                <th><span><fmt:message key="user.user"/></span></th>
                                <th><span><fmt:message key="user.login"/></span></th>
                                <th class="text-center"><span><fmt:message key="users.status"/></span></th>
                                <th><span><fmt:message key="user.email"/></span></th>
                                <th>&nbsp;</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${users}" var="user">
                                <tr>
                                    <td>
                                        <img src="${user.photo}">
                                        <a href="${pageContext.request.contextPath}/controller?command=profile&id=${user.id}" class="user-link">${user.firstName} ${user.lastName}</a>
                                        <c:choose>
                                            <c:when test="${user.admin eq true}">
                                                <span class="user-subhead"><fmt:message key="users.admin"/></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="user-subhead"><fmt:message key="user.user"/></span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${user.login}</td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${user.banned eq true}">
                                                <span class="label label-danger"><fmt:message key="users.banned"/></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="label label-success"><fmt:message key="users.active"/></span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="mailto:${user.email}">${user.email}</a>
                                    </td>
                                    <td class="text-center">
                                        <form method="POST" action="${pageContext.request.contextPath}/controller?command=ban_user">
                                            <input type="hidden" name="user_id" value="${user.id}">
                                            <c:choose>
                                                <c:when test="${user.banned eq false}">
                                                    <button type="submit" class="btn btn-danger"><i class="fa fa-lock" aria-hidden="true"></i> <fmt:message key="users.ban"/></button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button type="submit" class="btn btn-success"><i class="fa fa-unlock" aria-hidden="true"></i> <fmt:message key="users.unban"/></button>
                                                </c:otherwise>
                                            </c:choose>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>
