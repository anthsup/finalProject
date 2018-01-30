<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="id" value="1" scope="page"/>
<html>
<head>
    <title>User profile</title>
    <%--Bootstrap core CSS--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile-style.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container">
    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">Profile Info</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-3 col-lg-3 " align="center"><img alt="User Pic" src="http://placehold.it/300x300" class="img-circle img-responsive"></div>
                    <div class=" col-md-9 col-lg-9 ">
                        <table class="table table-user-information">
                            <tbody>
                            <tr>
                                <td>Name:</td>
                                <td>${sessionScope.user.firstName} ${sessionScope.user.lastName}</td>
                            </tr>
                            <tr>
                                <td>Login:</td>
                                <td>${sessionScope.user.login}</td>
                            </tr>
                            <tr>
                                <td>Email:</td>
                                <td><a href="mailto:${sessionScope.user.email}">${sessionScope.user.email}</a></td>
                            </tr>
                            <tr>
                            <tr>
                                <td>Home Address</td>
                                <td>${sessionScope.user.city}, ${sessionScope.user.address}</td>
                            </tr>
                            <tr>
                                <td>Postal Index:</td>
                                <td>${sessionScope.user.postalIndex}</td>
                            </tr>
                            </tbody>
                        </table>

                        <button type="button" class="btn btn-primary pull-right" data-toggle="modal" data-keyboard="true" data-target="#subscriptions">My Subscriptions</button>
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <span class="pull-right">
                    <a href="${pageContext.request.contextPath}/controller?command=profile_edit" data-original-title="Edit this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-warning"><i class="glyphicon glyphicon-edit"></i></a>
                    <a data-original-title="Remove this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-danger"><i class="glyphicon glyphicon-remove"></i></a>
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
                                        Hello, <b>User, </b>below are your orders with dates, price and status.
                                    </div>
                                    <hr />
                                    <div class="table-responsive">
                                        <table class="table table-striped table-bordered table-hover text-center">
                                            <thead class="">
                                            <tr>
                                                <th scope="col">No.</th>
                                                <th scope="col">Title</th>
                                                <th scope="col">Start Date</th>
                                                <th scope="col">End Date</th>
                                                <th scope="col">Total Price</th>
                                                <th scope="col">Status</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${subscriptions}" var="subscription">
                                                <tr>
                                                    <th scope="row">${id}</th>
                                                    <c:set var="id" value="${id + 1}" scope="page"/>
                                                    <td>${periodicals[subscription.periodicalId].title}</td>
                                                    <td>${subscription.startDate}</td>
                                                    <td>${subscription.endDate}</td>
                                                    <td>
                                                        <label class="label label-primary">${subscription.price}</label>
                                                    </td>
                                                    <td>
                                                        <label class="label <c:choose>
                                                                                    <c:when test="${statuses[subscription.periodicalId] eq 'Active.'}">
                                                                                    label-success
                                                                                    </c:when>
                                                                                    <c:when test="${statuses[subscription.periodicalId] eq 'Expired.'}">
                                                                                    label-danger
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                    label-warning
                                                                                    </c:otherwise>
                                                                                </c:choose>">${statuses[subscription.periodicalId]}</label></td>
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
