<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Website Font style -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/periodicals-style.css">

    <title>Periodicals</title>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container ">
    <div class="row">
        <c:forEach items="${periodicals}" var="periodical">
            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                <div class="panel panel-default  panel--styled">
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
                            <p>Type: ${(periodicalTypes[periodical.typeId]).name}</p>
                        </div>

                        <div class="row">
                            <div class="col-md-12 panelBottom">
                                <div class="col-lg-6 pull-left">
                                    <form method="post" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="add_to_cart">
                                        <input type="hidden" name="id" value="${periodical.id}">
                                        <button class="btn btn-md btn-add-to-cart"><span
                                                class="glyphicon glyphicon-shopping-cart"></span> Add to Cart
                                        </button>
                                    </form>
                                </div>
                                <div class="row">
                                    <div class="col-lg-6 text-right">
                                        <h5>Price <span class="itemPrice">${periodical.price} BYN</span></h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row text-center">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#product_view${periodical.id}">
                                <i class="fa fa-search"></i> Quick View
                            </button>
                        </div>
                    </div>
                </div>
            </div>


            <div class="modal fade product_view" id="product_view${periodical.id}">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <a href="#" data-dismiss="modal" class="class pull-right"><span
                                    class="glyphicon glyphicon-remove"></span></a>
                            <h3 class="modal-title">${periodical.title}</h3>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-6 product_img">
                                    <img src="${periodical.coverImage}"
                                         class="img-responsive" id="img-custom">
                                </div>
                                <div class="col-md-6 product_content">
                                    <h4>Periodicity: <span>${periodicities[periodical.periodicityId].periodicity}</span></h4>

                                    <h4>Type: <span>${(periodicalTypes[periodical.typeId]).name}</span></h4>

                                    <p>${periodical.description}</p>

                                    <c:if test="${periodical.authorId ne 0}">
                                        <h4>Author: <span>${authors[periodical.authorId].fullName}</span></h4>
                                    </c:if>

                                    <h4>Genres: <span><c:forEach items="${genres[periodical.id]}" var="genre">
                                        ${genre.name}
                                    </c:forEach></span></h4>

                                    <h3 class="cost"><span class="glyphicon glyphicon-usd"></span> ${periodical.price}
                                            <%--<small class="pre-cost"><span class="glyphicon glyphicon-usd"></span> 60.00</small>--%>
                                    </h3>
                                    <form method="post" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="add_to_cart">
                                        <input type="hidden" name="id" value="${periodical.id}">
                                        <div class="row">
                                            <div class="col-md-12 col-sm-12 col-xs-12 text-center">
                                                <input type="number" name="monthsAmount"  placeholder="Количество месяцев" min="1" <c:if test="${periodical.booksAmount ne 0}">
                                                    max="${periodical.booksAmount}"</c:if>/>
                                            </div>
                                            <!-- end col -->
                                                <%--<div class="col-md-6 col-sm-6 col-xs-12">--%>
                                                <%--<select class="form-control" name="select">--%>
                                                <%--<option value="">Активна с</option>--%>
                                                <%--<option value="">16GB</option>--%>
                                                <%--<option value="">32GB</option>--%>
                                                <%--<option value="">64GB</option>--%>
                                                <%--<option value="">128GB</option>--%>
                                                <%--</select>--%>
                                                <%--</div>--%>
                                            <!-- end col -->
                                                <%--<div class="col-md-4 col-sm-12">--%>
                                                <%--<select class="form-control" name="select">--%>
                                                <%--<option value="" selected="">QTY</option>--%>
                                                <%--<option value="">1</option>--%>
                                                <%--<option value="">2</option>--%>
                                                <%--<option value="">3</option>--%>
                                                <%--</select>--%>
                                                <%--</div>--%>
                                                <%--<!-- end col -->--%>
                                        </div>
                                        <div class="space-ten"></div>
                                        <div class="btn-ground text-center">
                                            <button type="submit" class="btn btn-primary"><span
                                                    class="glyphicon glyphicon-shopping-cart"></span> Add To Cart
                                            </button>
                                                <%--<button type="button" class="btn btn-primary"><span--%>
                                                <%--class="glyphicon glyphicon-heart"></span> Add To Wishlist--%>
                                                <%--</button>--%>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/footer.jsp" />

<!-- Website CSS style -->
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
</html>
