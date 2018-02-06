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
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container admin">
    <div class="page-header">
        <h3>Periodical Management</h3>
    </div>
    <div class="row grid-divider">
        <div class="col-sm-6">
            <div class="col-padding">
                <h3>Add</h3>

                <div class="input-group">
                    <span class="input-group-addon" id="genre">@</span>
                    <input type="text" class="form-control" placeholder="Genre">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button">Go!</button>
                    </span>
                </div>

                <div class="input-group">
                    <span class="input-group-addon" id="author">@</span>
                    <input type="text" class="form-control" placeholder="Genre">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button">Go!</button>
                    </span>
                </div>

                <div class="input-group">
                    <span class="input-group-addon" id="type">@</span>
                    <input type="text" class="form-control" placeholder="Genre">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button">Go!</button>
                    </span>
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="col-padding">
                <h3>Delete</h3>
                <div class="input-group">
                    <span class="input-group-addon" id="sizing-addon2">@</span>
                    <input type="text" class="form-control" placeholder="Username" aria-describedby="sizing-addon2">
                </div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="/WEB-INF/jsp/footer.jsp"/>

<!-- Website CSS style -->
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
</html>
