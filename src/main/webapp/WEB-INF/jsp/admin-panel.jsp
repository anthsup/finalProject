<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- internationalization -->
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="Contents"/>

<html>
<head>
    <title>Admin Panel</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-panel-style.css"/>
    <script defer src="https://use.fontawesome.com/releases/v5.0.6/js/all.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container admin">
    <div class="page-header">
        <h3><fmt:message key="period.management"/></h3>
    </div>
    <div class="row grid-divider">
        <div class="col-sm-6">
            <div class="col-padding">
                <h3><fmt:message key="period.add"/></h3>

                <form method="POST" action="${pageContext.request.contextPath}/controller">
                    <div class="input-group">
                        <input type="hidden" name="command" value="add_genre">
                        <span class="input-group-addon"><i class="fas fa-book fa-fw"></i></span>
                        <input type="text" name="genre" class="form-control" placeholder="<fmt:message key="period.genre"/>">
                        <span class="input-group-btn">
                            <button class="btn btn-success" type="submit"><fmt:message key="period.add"/></button>
                        </span>
                    </div>
                </form>
                <hr>
                <form method="POST" action="${pageContext.request.contextPath}/controller">
                    <div class="input-group">
                        <input type="hidden" name="command" value="add_author">
                        <span class="input-group-addon"><i class="fas fa-user fa-fw"></i></span>
                        <input type="text" name="author" class="form-control" placeholder="<fmt:message key="period.author"/>">
                        <span class="input-group-btn">
                            <button class="btn btn-success" type="submit"><fmt:message key="period.add"/></button>
                        </span>
                    </div>
                </form>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="col-padding">
                <h3><fmt:message key="period.delete"/></h3>
                <form method="POST" action="${pageContext.request.contextPath}/controller">
                    <div class="input-group">
                        <input type="hidden" name="command" value="delete_authors">
                        <span class="input-group-addon"><i class="fas fa-minus-circle fa-fw"></i></span>
                        <select name="authors" class="selectpicker form-control input-md show-tick" data-selected-text-format="count > 3" multiple title="<fmt:message key="period.authors"/>">
                            <c:forEach items="${authors}" var="author">
                                <option value="${author.id}">${author.fullName}</option>
                            </c:forEach>
                        </select>
                        <span class="input-group-btn">
                                <button class="btn btn-danger" type="submit"><fmt:message key="period.delete"/></button>
                        </span>
                    </div>
                </form>
                <hr>
                <form method="POST" action="${pageContext.request.contextPath}/controller">
                    <div class="input-group">
                        <input type="hidden" name="command" value="delete_genres">
                        <span class="input-group-addon" id="sizing-addon2"><i class="fas fa-trash fa-fw"></i></span>
                        <select name="genres" class="selectpicker form-control input-md show-tick" data-selected-text-format="count > 3" multiple data-live-search="true" data-size="5" title="<fmt:message key="period.genres"/>">
                            <c:forEach items="${genres}" var="genre">
                                <option>${genre.name}</option>
                            </c:forEach>
                        </select>
                        <span class="input-group-btn">
                                <button class="btn btn-danger" type="submit"><fmt:message key="period.delete"/></button>
                        </span>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-12 text-center">
        <hr>
        <a href="${pageContext.request.contextPath}/controller?command=periodical_add" type="button" class="btn btn-success"><fmt:message key="period.addPeriodical"/></a>
    </div>
</div>


<jsp:include page="/WEB-INF/jsp/footer.jsp"/>

<!-- Latest compiled and minified JavaScript -->
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
</body>
</body>
</html>
