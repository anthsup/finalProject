<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cart-style.css">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <title>Cart</title>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container">
    <table id="cart" class="table table-hover table-condensed">
        <thead>
        <tr>
            <th style="width:50%">Periodical</th>
            <th style="width:10%">Price</th>
            <th style="width:8%">Months</th>
            <th style="width:22%" class="text-center">Subtotal</th>
            <th style="width:10%"></th>
        </tr>
        </thead>
        <c:forEach items="${sessionScope.cart_products}" var="product">
            <tbody>
            <tr>
                <td data-th="Product">
                    <div class="row">
                        <div class="col-sm-2 hidden-xs"><img src="${product.coverImage}" class="img-responsive"/></div>
                        <div class="col-sm-10">
                            <h4 class="nomargin">${product.title}</h4>
                                <%--<p>Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Lorem ipsum dolor sit amet.</p>--%>
                        </div>
                    </div>
                </td>
                <form action="${pageContext.request.contextPath}/controller" method="post" class="parent-custom${product.id}">
                    <td data-th="Price" class="price">$${product.price}</td>
                    <td data-th="Quantity">
                        <input type="hidden" name="command" value="change_price">
                        <input type="number" name="quantity" class="form-control text-center input-custom" min="1" value="${sessionScope.quantities[product.id]}" <c:if test="${product.booksAmount ne 0}">
                               max="${product.booksAmount}"</c:if>>
                        <input type="hidden" id="command-id" name="id" value="${product.id}">
                    </td>
                    <td data-th="Subtotal" id="subtotal${product.id}" class="text-center">${product.price * sessionScope.quantities[product.id]}</td>
                </form>
                <td class="actions" data-th="">
                    <%--<button class="btn btn-info btn-sm"><i class="fa fa-refresh"></i></button>--%>
                    <form method="post" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="delete_from_cart">
                        <input type="hidden" name="id" value="${product.id}"/>
                        <button type="submit" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i></button>
                    </form>
                </td>
            </tr>
            </tbody>
        </c:forEach>
        <tfoot>
        <tr class="visible-xs">
            <td class="text-center total-xs"><strong>Total 1.99</strong></td>
        </tr>
        <tr>
            <td><a href="${pageContext.request.contextPath}/controller?command=periodicals" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continue Shopping</a></td>
            <td colspan="2" class="hidden-xs"></td>
            <td class="hidden-xs text-center"><strong id="total" class="total">$${sessionScope.totalPrice}</strong></td>
            <td><a href="${pageContext.request.contextPath}/controller?command=checkout" class="btn btn-success btn-block">Checkout <i class="fa fa-angle-right"></i></a></td>
        </tr>
        </tfoot>
    </table>

    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>

    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.2.1.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/changePrice.js"></script>
</div>
</body>
</html>
