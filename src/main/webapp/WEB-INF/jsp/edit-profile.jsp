<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <title>Edit Profile</title>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-10 ">
            <form class="form-horizontal" method="POST" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="edit_profile">
                <fieldset>

                    <!-- Form Name -->
                    <legend>Edit Profile</legend>

                    <!-- Text input-->

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="firstName">First Name</label>
                        <div class="col-md-4">
                            <div class="input-group">
                                <input id="firstName" name="firstName" type="text" placeholder="First Name" class="form-control" value="${sessionScope.user.firstName}">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="lastName">Last Name</label>
                        <div class="col-md-4">
                            <div class="input-group">
                                <input id="lastName" name="lastName" type="text" placeholder="Last Name" class="form-control" value="${sessionScope.user.lastName}">
                            </div>
                        </div>
                    </div>

                    <!-- File Button -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="Upload photo">Upload photo</label>
                        <div class="col-md-4">
                            <input id="Upload photo" name="photo" class="input-file" type="file">
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="login">Login</label>
                        <div class="col-md-4">
                            <div class="input-group">
                                <input id="login" name="login" type="text" placeholder="Login" class="form-control input-md" value="${sessionScope.user.login}">
                            </div>
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="email">Email Address</label>
                        <div class="col-md-4">
                            <div class="input-group">
                                <input id="email" name="email" type="text" placeholder="Email Address" class="form-control input-md" value="${sessionScope.user.email}">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label col-xs-12" for="country">Home Address</label>
                        <div class="col-md-2  col-xs-4">
                            <input id="country" name="country" type="text" placeholder="Country" class="form-control input-md ">
                        </div>

                        <div class="col-md-2 col-xs-4">
                            <input id="city" name="city" type="text" placeholder="City" class="form-control input-md ">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="street"></label>
                        <div class="col-md-2  col-xs-4">
                            <input id="street" name="street" type="text" placeholder="Street" class="form-control input-md ">
                        </div>

                        <div class="col-md-2  col-xs-4">
                            <input id="house" name="house" type="text" placeholder="House" class="form-control input-md ">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 col-md-offset-1 control-label" for="apartment"></label>
                        <div class="col-md-2  col-xs-4">
                            <input id="apartment" name="apartment" type="text" placeholder="Apartment" class="form-control input-md ">
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="password">New Password</label>
                        <div class="col-md-4">
                            <div class="input-group">
                                <input id="password" name="password" type="password" placeholder="New Password" class="form-control input-md">
                            </div>
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="confirm_password">Confirm New Password</label>
                        <div class="col-md-4">
                            <div class="input-group">
                                <input id="confirm_password" name="confirm_password" type="password" placeholder="Confirm New Password" class="form-control input-md">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" ></label>
                        <div class="col-md-4">
                            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-thumbs-up"></span> Submit</button>
                            <a href="#" class="btn btn-danger"><span class="glyphicon glyphicon-remove-sign"></span> Clear</a>
                        </div>
                    </div>
                </fieldset>
            </form>
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
