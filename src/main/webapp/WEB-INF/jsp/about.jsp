<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--<!-- internationalization -->--%>
<%--<c:choose>--%>
  <%--<c:when test="${not empty sessionScope.locale}">--%>
    <%--<fmt:setLocale value="${sessionScope.locale}"/>--%>
  <%--</c:when>--%>
  <%--<c:otherwise>--%>
    <%--<fmt:setLocale value="en_US"/>--%>
  <%--</c:otherwise>--%>
<%--</c:choose>--%>
<%--<fmt:setBundle basename="Messages"/>--%>

<!DOCTYPE html>
<html>

  <head>
    <title><fmt:message key="about.titlePage"/></title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="This is page about movie rating project."/>
    <link rel="stylesheet" href="/resources/css/movie_rating_styles.css"/>
    <link rel="stylesheet" href="/resources/css/about_styles.css"/>
    <link rel="stylesheet" href="/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/resources/css/bootstrap-theme.min.css"/>
    <script src="/resources/js/jquery-3.1.0.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/navigation.js"></script>
  </head>

  <body>

    <mytags:navbar/>

    <section class="container">
      <div class="about-text">
        <div class="well">
          <h2><fmt:message key="about.title"/></h2>
          <p>
             <fmt:message key="about.titleMessage"/>
          </p>
        </div>
      </div>

    </section>

  </body>

</html>
