<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="id" value="1" scope="page"/>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<html>
<head>
    <title>User profile</title>
    <%--Bootstrap core CSS--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile-style.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container">
    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="profile.info"/></h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-3 col-lg-3 " align="center"><img src="<c:choose>
                                                                                <c:when test="${not empty user.photo}">
                                                                                    ${user.photo}
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    http://placehold.it/300x300
                                                                                </c:otherwise>
                                                                            </c:choose>" alt="User Pic" class="img-circle img-responsive"></div>
    <div class=" col-md-9 col-lg-9 ">
                        <table class="table table-user-information">
                            <tbody>
                            <tr>
                                <td><fmt:message key="profile.fullName"/>:</td>
                                <td>${user.firstName} ${user.lastName}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="user.login"/>:</td>
                                <td>${user.login}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="user.email"/>:</td>
                                <td><a href="mailto:${user.email}">${user.email}</a></td>
                            </tr>
                            <tr>
                            <tr>
                                <td><fmt:message key="user.address"/>:</td>
                                <td>${user.city}, ${user.address}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="user.postal"/>:</td>
                                <td>${user.postalIndex}</td>
                            </tr>
                            </tbody>
                        </table>

                        <button type="button" class="btn btn-primary pull-right" data-toggle="modal" data-keyboard="true" data-target="#subscriptions"><fmt:message key="profile.subscriptions"/></button>
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <span class="pull-right">
                    <c:if test="${sessionScope.user eq user}">
                        <a href="${pageContext.request.contextPath}/controller?command=profile_edit" data-original-title="Edit this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-warning"><i class="glyphicon glyphicon-edit"></i></a>
                    </c:if>
                </span>
            </div>
        </div>
    </div>
    <div class="modal fade" tabindex="-1" id="subscriptions">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="Order-list">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                    <div class="alert alert-info">
                                        <b>${sessionScope.user.login}</b><fmt:message key="profile.orders"/>
                                    </div>
                                    <hr />
                                    <div class="table-responsive">
                                        <table class="table table-striped table-hover text-center">
                                            <thead class="">
                                            <tr>
                                                <th scope="col">#</th>
                                                <th scope="col"><fmt:message key="profile.title"/></th>
                                                <th scope="col"><fmt:message key="profile.startDate"/></th>
                                                <th scope="col"><fmt:message key="profile.endDate"/></th>
                                                <th scope="col"><fmt:message key="profile.total"/></th>
                                                <th scope="col"><fmt:message key="profile.status"/></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${subscriptions}" var="subscription">
                                                <tr>
                                                    <th scope="row">${id}</th>
                                                    <c:set var="id" value="${id + 1}" scope="page"/>
                                                    <td>${periodicals[subscription.id].title}</td>
                                                    <td>${subscription.startDate}</td>
                                                    <td>${subscription.endDate}</td>
                                                    <td>
                                                        <label class="label label-primary">${subscription.price}</label>
                                                    </td>
                                                    <td>
                                                        <label class="label <c:choose>
                                                                                    <c:when test="${statuses[subscription.id] eq 'Active.'}">
                                                                                    label-success
                                                                                    </c:when>
                                                                                    <c:when test="${statuses[subscription.id] eq 'Expired.'}">
                                                                                    label-danger
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                    label-warning
                                                                                    </c:otherwise>
                                                                                </c:choose>">${statuses[subscription.id]}</label></td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- ORDER LIST END-->
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
