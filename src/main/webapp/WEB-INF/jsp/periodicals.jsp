<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<!DOCTYPE html>
<html lang="${locale}">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Website Font style -->
    <script defer src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/periodicals-style.css">

    <title><fmt:message key="nav.periodicals"/></title>
</head>
<body>

<custom:header/>

<div class="container">
    <div class="row periodicals">
        <input type="hidden" name="command" id="searchCommand" value="${searchCommand}">
        <input type="hidden" id="periodicalsNumber" value="${periodicalsNumber}">
        <c:forEach items="${periodicals}" var="periodical">
            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                <div class="panel panel-default  panel--styled">
                    <c:if test="${sessionScope.user.admin eq true}">
                        <div class="panel-heading clearfix">
                            <a href="${pageContext.request.contextPath}/controller?command=periodical-edit&periodicalId=${periodical.id}" data-toggle="tooltip" type="button" class="btn btn-sm btn-warning pull-left"><i class="glyphicon glyphicon-edit"></i></a>
                            <a data-toggle="modal" data-target="#delete${periodical.id}" type="button" class="btn btn-sm btn-danger pull-right"><i class="glyphicon glyphicon-remove"></i></a>
                        </div>
                    </c:if>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <img class="img-responsive center-block" src="${periodical.coverImage}" alt=""/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 text-center title-view">
                                <h3>${periodical.title}</h3>
                            </div>
                        </div>

                        <div class="row text-center">
                            <p><fmt:message key="period.type"/>: <a href="${pageContext.request.contextPath}/controller?command=search-periodicals&periodicalType=${periodical.typeId}">${(periodicalTypes[periodical.typeId]).name}</a></p>
                        </div>

                        <div class="row">
                            <div class="col-md-12 panelBottom">
                                <div class="col-lg-6 pull-left">
                                    <form method="post" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="add-to-cart">
                                        <input type="hidden" name="periodicalId" value="${periodical.id}">
                                        <button class="btn btn-md btn-add-to-cart" <c:if test="${empty sessionScope.user || sessionScope.user.loan < 0}">disabled</c:if>><span
                                                class="glyphicon glyphicon-shopping-cart"></span> <fmt:message key="period.addToCart"/>
                                        </button>
                                    </form>
                                </div>
                                <div class="row">
                                    <div class="col-lg-6 text-right">
                                        <h5>Price <span class="itemPrice">${periodical.price} <fmt:message key="currency.value"/></span></h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row text-center">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#product_view${periodical.id}">
                                <i class="fa fa-search"></i> <fmt:message key="period.details"/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>


            <div class="modal fade product_view" id="product_view${periodical.id}">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <a href="product_view${periodical.id}" data-dismiss="modal" class="class pull-right"><span
                                    class="glyphicon glyphicon-remove"></span></a>
                            <h3 class="modal-title">${periodical.title}</h3>
                            <div class="alert alert-warning" role="alert">
                                <i class="fas fa-exclamation-triangle"></i> <fmt:message key="period.priceAlert"/>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-6 product_img">
                                    <img src="${periodical.coverImage}"
                                         class="img-responsive" id="img-custom">
                                </div>
                                <div class="col-md-6 product_content">
                                    <h4><fmt:message key="period.periodicity"/>: <span><a href="${pageContext.request.contextPath}/controller?command=search-periodicals&periodicity=${periodical.periodicity}">${periodical.periodicity}</a> <fmt:message key="period.times"/></span></h4>

                                    <h4><fmt:message key="period.type"/>: <span><a href="${pageContext.request.contextPath}/controller?command=search-periodicals&periodicalType=${periodical.typeId}">${(periodicalTypes[periodical.typeId]).name}</a></span></h4>

                                    <p>${periodical.description}</p>

                                    <c:if test="${periodical.authorId ne 0}">
                                        <h4><fmt:message key="period.author"/>: <span><a href="${pageContext.request.contextPath}/controller?command=search-periodicals&author=${periodical.authorId}">${authors[periodical.authorId].fullName}</a></span></h4>
                                        <h4><fmt:message key="period.books"/>: <span>${periodical.booksAmount}</span></h4>
                                    </c:if>

                                    <h4><fmt:message key="period.genres"/>: <span><c:forEach items="${genres[periodical.id]}" var="genre">
                                        <a href="${pageContext.request.contextPath}/controller?command=search-periodicals&genre=${genre.name}">${genre.name}</a>
                                    </c:forEach></span></h4>

                                    <h3 class="cost">${periodical.price} <fmt:message key="currency.value"/></h3>
                                    <form method="post" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="add-to-cart">
                                        <input type="hidden" name="periodicalId" value="${periodical.id}">
                                        <div class="space-ten"></div>
                                        <div class="btn-ground text-center">
                                            <button type="submit" class="btn btn-md btn-add-to-cart" <c:if test="${empty sessionScope.user || sessionScope.user.loan < 0}">disabled</c:if>><span
                                                    class="glyphicon glyphicon-shopping-cart"></span> <fmt:message key="period.addToCart"/>
                                            </button>
                                            <c:if test="${empty sessionScope.user}">
                                                <div class="alert alert-danger" role="alert">
                                                    <i class="fas fa-exclamation-circle"></i> <fmt:message key="period.loginAlert"/> <a data-dismiss="modal" data-toggle="collapse" href="#nav-collapse2" aria-expanded="false" aria-controls="nav-collapse2"><fmt:message key="period.loginAlert.link"/></a>
                                                </div>
                                            </c:if>
                                            <c:if test="${sessionScope.user.loan < 0}">
                                                <div class="alert alert-danger" role="alert">
                                                    <i class="fas fa-exclamation-circle"></i> <fmt:message key="period.creditAlert"/> <a href="${pageContext.request.contextPath}/controller?command=profile"><fmt:message key="period.creditAlert.link"/></a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="delete${periodical.id}" tabindex="-1" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">×</button>
                            <h4 class="modal-title"><fmt:message key="period.delete"/> ${periodical.title}</h4>
                        </div>
                        <div class="modal-body">
                            <strong><fmt:message key="period.delete.msg"/></strong>
                        </div>
                        <div class="modal-footer">
                            <a href="${pageContext.request.contextPath}/controller?command=delete-periodical&periodicalId=${periodical.id}" type="button" id="del" class="btn btn-danger"><fmt:message key="period.delete"/></a>
                            <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="period.close"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="row text-center">
        <ul id="pagination" class="pagination"></ul>
    </div>
</div>

<custom:footer/>

<!-- Website CSS style -->
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/jquery.twbsPagination.min.js"></script>
<script src="${pageContext.request.contextPath}/js/pagination.js"></script>
</body>
</html>
