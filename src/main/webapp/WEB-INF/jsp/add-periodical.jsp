<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<html lang="${locale}">
<head>
    <title><fmt:message key="period.addPeriodical"/></title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-12 ">
            <form class="form-horizontal" method="POST" action="${pageContext.request.contextPath}/controller?command=add_periodical">
                <fieldset>
                    <legend><fmt:message key="period.addPeriodical"/></legend>
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="title"><fmt:message key="period.title"/></label>
                        <div class="col-md-4">
                            <input id="title" name="title" type="text" placeholder="<fmt:message key="period.title"/>" class="form-control">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="type"><fmt:message key="period.type"/></label>
                        <div class="col-md-4">
                            <select id="type" name="type" data-style="btn-primary" class="selectpicker form-control input-md show-tick" title="<fmt:message key="period.type"/>">
                                <c:forEach items="${periodicalTypes}" var="periodicalType">
                                    <option value="${periodicalType.id}">${periodicalType.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="coverImage"><fmt:message key="period.image"/></label>
                        <div class="col-md-4">
                            <input id="coverImage" name="coverImage" class="form-control" type="url" placeholder="<fmt:message key="period.image"/> URL"
                                   pattern="^(https|http).+(jpg|svg|gif|png)$">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="periodicity"><fmt:message key="period.periodicity"/></label>
                        <div class="col-md-4">
                            <input id="periodicity" name="periodicity" type="number" placeholder="<fmt:message key="period.periodicity"/>" class="form-control input-md" min="1" max="30">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="price"><fmt:message key="period.price"/></label>
                        <div class="col-md-4">
                            <input id="price" name="price" type="number" step="0.01" placeholder="<fmt:message key="period.price"/>" class="form-control input-md" min="0" value="${periodical.price}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label col-xs-12" for="genres"><fmt:message key="period.genres"/></label>
                        <div class="col-md-4">
                            <select id="genres" name="genres" data-style="btn-info" class="selectpicker form-control input-md" multiple data-max-options="5" data-live-search="true" title="<fmt:message key="period.genres"/>" data-size="5">
                                <c:forEach items="${genres}" var="genre">
                                    <option>${genre.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <c:if test="${periodical.authorId ne 0}">
                        <div class="form-group">
                            <label class="col-md-4 control-label col-xs-12" for="author"><fmt:message key="period.author"/></label>
                            <div class="col-md-4">
                                <select id="author" name="author" data-style="btn-danger" data-actions-box="true" class="selectpicker form-control input-md show-tick" title="<fmt:message key="period.author"/>">
                                    <c:forEach items="${authors}" var="author">
                                        <option value="${author.id}">${author.fullName}</option>
                                    </c:forEach>
                                </select>
                                <p class="help-block"><fmt:message key="period.help"/></p>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label" for="books"><fmt:message key="period.books"/></label>
                            <div class="col-md-4">
                                <input id="books" name="books" type="number" placeholder="<fmt:message key="period.books"/>" class="form-control input-md" min="1">
                                <p class="help-block"><fmt:message key="period.help"/></p>
                            </div>
                        </div>
                    </c:if>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="Description"><fmt:message key="period.description"/></label>
                        <div class="col-md-4">
                            <textarea id="description" name="description" placeholder="<fmt:message key="period.description"/>" class="form-control input-md" rows="5"></textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" ></label>
                        <div class="col-md-4">
                            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-thumbs-up"></span> <fmt:message key="period.add"/></button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp"/>

<!-- Latest compiled and minified JavaScript -->
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
</body>
</html>