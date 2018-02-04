<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<html lang="${locale}">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <title><fmt:message key="edit.title"/></title>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-10 ">
            <form class="form-horizontal" method="POST" action="${pageContext.request.contextPath}/controller?command=edit_profile">
                <input type="hidden" name="command" value="profile_edit">
                <fieldset>

                    <!-- Form Name -->
                    <legend><fmt:message key="edit.title"/></legend>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="firstName"><fmt:message key="user.firstName"/></label>
                        <div class="col-md-4">
                                <input id="firstName" name="firstName" type="text" placeholder="<fmt:message key="user.firstName"/>" class="form-control" value="${sessionScope.user.firstName}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="lastName"><fmt:message key="user.lastName"/></label>
                        <div class="col-md-4">
                                <input id="lastName" name="lastName" type="text" placeholder="<fmt:message key="user.lastName"/>" class="form-control" value="${sessionScope.user.lastName}">
                        </div>
                    </div>

                    <!-- File Button -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="upload"><fmt:message key="edit.photo"/></label>
                        <div class="col-md-4">
                                <input id="upload" name="photo" class="form-control" type="url" placeholder="<fmt:message key="edit.insert"/>"
                                       pattern="^(https|http).+(jpg|svg|gif|png)$">
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="login"><fmt:message key="user.login"/></label>
                        <div class="col-md-4">
                                <input id="login" name="login" type="text" placeholder="<fmt:message key="user.login"/>" class="form-control input-md" value="${sessionScope.user.login}">
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="email"><fmt:message key="user.email"/></label>
                        <div class="col-md-4">
                                <input id="email" name="email" type="text" placeholder="<fmt:message key="user.email"/>" class="form-control input-md" value="${sessionScope.user.email}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label col-xs-12" for="city"><fmt:message key="user.city"/></label>
                        <div class="col-md-4">
                            <input id="city" name="city" type="text" placeholder="<fmt:message key="user.city"/>" class="form-control input-md" value="${sessionScope.user.city}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="address"><fmt:message key="user.address"/></label>
                        <div class="col-md-4">
                            <input id="address" name="address" type="text" placeholder="<fmt:message key="user.address"/>" class="form-control input-md" value="${sessionScope.user.address}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="postal"><fmt:message key="user.postal"/></label>
                        <div class="col-md-4">
                            <input id="postal" name="postal" type="text" placeholder="<fmt:message key="user.postal"/>" class="form-control input-md" value="${sessionScope.user.postalIndex}">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-offset-5">
                            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#change_password"><fmt:message key="edit.changePassword"/></button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" ></label>
                        <div class="col-md-6-offset-2">
                            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-thumbs-up"></span> <fmt:message key="edit.submit"/></button>
                        </div>
                    </div>
                </fieldset>
            </form>
            <div class="modal fade product_view" id="change_password">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form class="form-horizontal" method="POST" action="${pageContext.request.contextPath}/controller?command=change_password">
                            <div class="form-group">
                                <label class="col-md-6 control-label" for="password"><fmt:message key="edit.oldPassword"/></label>
                                <div class="col-md-6">
                                    <div class="input-group">
                                        <input id="old_password" name="old_password" type="password" placeholder="<fmt:message key="edit.oldPassword"/>" class="form-control input-md">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-6 control-label" for="password"><fmt:message key="edit.newPassword"/></label>
                                <div class="col-md-6">
                                    <div class="input-group">
                                        <input id="password" name="new_password" type="password" placeholder="<fmt:message key="edit.newPassword"/>" class="form-control input-md">
                                    </div>
                                </div>
                            </div>

                            <!-- Text input-->
                            <div class="form-group">
                                <label class="col-md-6 control-label" for="confirm_password"><fmt:message key="edit.confirm"/></label>
                                <div class="col-md-6">
                                    <div class="input-group">
                                        <input id="confirm_password" name="confirm_password" type="password" placeholder="<fmt:message key="edit.confirm"/>" class="form-control input-md">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-6 control-label"></label>
                                <div class="col-md-6">
                                    <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-thumbs-up"></span> <fmt:message key="edit.submit"/></button>
                                    <input type="reset" class="btn btn-danger" value="<fmt:message key="edit.clear"/>">
                                </div>
                            </div>
                        </form>
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
