<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<head>
    <meta charset="utf-8">
    <meta http-equiv="content-type" content="text/html;charset=utf-8"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header-style.css"/>
</head>

<!-- Second navbar for sign in -->
<nav class="navbar navbar-default">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-2">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="nav.subscription"/></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navbar-collapse-2">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                        <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
                        <fmt:message key="nav.lang"/> <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${pageContext.request.contextPath}/controller?command=change-locale&locale=ru_RU"><fmt:message key="nav.lang.ru"/></a></li>
                        <li><a href="${pageContext.request.contextPath}/controller?command=change-locale&locale=en_US"><fmt:message key="nav.lang.en"/></a></li>
                    </ul>
                </li>
                <li><a href="${pageContext.request.contextPath}/controller?command=periodicals"><fmt:message key="nav.periodicals"/></a></li>
                <c:if test="${not empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/controller?command=cart"><fmt:message key="nav.cart"/> <span class="badge">${fn:length(sessionScope.cartProducts)}</span></a></li>
                </c:if>
                <c:if test="${sessionScope.user.admin eq true}">
                    <li class="dropdown">
                        <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
                            <fmt:message key="nav.admin"/> <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="${pageContext.request.contextPath}/controller?command=users"><fmt:message key="users.management"/></a></li>
                            <li><a href="${pageContext.request.contextPath}/controller?command=admin-panel"><fmt:message key="period.management"/></a></li>
                        </ul>
                    </li>
                </c:if>
            <c:if test="${empty sessionScope.user}">
                <li><a href="${pageContext.request.contextPath}/controller?command=signup-page"><fmt:message key="nav.signup"/></a></li>
                <li>
                        <a class="btn btn-default btn-outline btn-circle"  data-toggle="collapse" href="#nav-collapse2" aria-expanded="false" aria-controls="nav-collapse2"><fmt:message key="nav.login"/></a>
                    </li>
                </ul>
                <div class="collapse nav navbar-nav nav-collapse" id="nav-collapse2">
                    <form class="navbar-form navbar-right form-inline" role="form" method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="login">
                        <div class="form-group">
                            <label class="sr-only" for="login">Login</label>
                            <input type="text" class="form-control" name="login" id="login" placeholder="<fmt:message key="user.login"/>" autofocus required />
                        </div>
                        <div class="form-group">
                            <label class="sr-only" for="Password">Password</label>
                            <input type="password" class="form-control" name="password" id="Password" placeholder="<fmt:message key="user.password"/>" required />
                        </div>
                        <button type="submit" class="btn btn-success"><fmt:message key="nav.login"/></button>
                    </form>
                </div>
                </c:if>

                <c:if test="${not empty sessionScope.user}">
                    <li>
                        <a class="btn btn-default btn-outline btn-circle"  data-toggle="collapse" href="#nav-collapse4" aria-expanded="false" aria-controls="nav-collapse4"><fmt:message key="nav.profile"/><i class=""></i> </a>
                    </li>
                    </ul>
                    <ul class="collapse nav navbar-nav nav-collapse" id="nav-collapse4">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">${sessionScope.user.login}<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="${pageContext.request.contextPath}/controller?command=profile"><fmt:message key="nav.myProfile"/></a></li>
                                <li><a href="${pageContext.request.contextPath}/controller?command=profile-edit"><fmt:message key="nav.settings"/></a></li>
                                <li class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message key="nav.logout"/></a></li>
                            </ul>
                        </li>
                    </ul>
                </c:if>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container -->
</nav><!-- /.navbar -->
