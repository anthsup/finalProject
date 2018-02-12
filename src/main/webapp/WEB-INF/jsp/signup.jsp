<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<!DOCTYPE html>
<html lang="${locale}">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Website CSS style -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Website Font style -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register-style.css">
    <title><fmt:message key="title.registration"/></title>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container signup">
    <div class="row">
        <div class="col-sm-8 col-sm-offset-2">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><fmt:message key="title.registration"/></h3>
                </div>
                <div class="panel-body">
                    <form id="signupForm" method="post" class="form-horizontal" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="signup">
                    <div class="form-group">
                            <label class="col-sm-4 control-label" for="login"><fmt:message key="user.login"/></label>
                            <div class="col-sm-5 login">
                                <input type="text" class="form-control" name="login" id="login"  placeholder="<fmt:message key="user.login"/>"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="email"><fmt:message key="user.email"/></label>
                            <div class="col-sm-5 email">
                                <input type="email" class="form-control" name="email" id="email"  placeholder="<fmt:message key="user.email"/>"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="firstName"><fmt:message key="user.firstName"/></label>
                            <div class="col-sm-5 firstName">
                                <input type="text" class="form-control" name="firstName" id="firstName"  placeholder="<fmt:message key="user.firstName"/>"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="lastName"><fmt:message key="user.lastName"/></label>
                            <div class="col-sm-5 lastName">
                                <input type="text" class="form-control" name="lastName" id="lastName"  placeholder="<fmt:message key="user.lastName"/>"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="city"><fmt:message key="user.city"/></label>
                            <div class="col-sm-5 city">
                                <input type="text" class="form-control" name="city" id="city"  placeholder="<fmt:message key="user.city"/>"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label" for="address"><fmt:message key="user.address"/></label>
                            <div class="col-sm-5 address">
                                <input type="text" class="form-control" name="address" id="address"  placeholder="<fmt:message key="user.address"/>"/>
                            </div>
                        </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="postal"><fmt:message key="user.postal"/></label>
                        <div class="col-sm-5 postal">
                            <input type="text" class="form-control" name="postal" id="postal"  placeholder="<fmt:message key="user.postal"/>"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="password"><fmt:message key="user.password"/></label>
                        <div class="col-sm-5 password">
                            <input type="password" class="form-control" name="password" id="password"  placeholder="<fmt:message key="user.password"/>"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="confirm"><fmt:message key="user.confirm"/></label>
                        <div class="col-sm-5 confirm">
                            <input type="password" class="form-control" name="confirm" id="confirm"  placeholder="<fmt:message key="user.confirm"/>"/>
                        </div>
                    </div>

                    <div class="form-group">
                            <div class="col-sm-9 col-sm-offset-4">
                                <button type="submit" class="btn btn-success signup"><fmt:message key="signup.register"/></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/jquery.validate.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/additional-methods.min.js"></script>
<script src="${pageContext.request.contextPath}/js/signup.js"></script>
<script src="${pageContext.request.contextPath}/js/messages_ru.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</body>
</html>

