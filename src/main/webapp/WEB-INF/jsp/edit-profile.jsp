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
            <form class="form-horizontal" method="POST" action="${pageContext.request.contextPath}/controller?command=edit_profile">
                <input type="hidden" name="command" value="profile_edit">
                <fieldset>

                    <!-- Form Name -->
                    <legend>Edit Profile</legend>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="firstName">First Name</label>
                        <div class="col-md-4">
                                <input id="firstName" name="firstName" type="text" placeholder="First Name" class="form-control" value="${sessionScope.user.firstName}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="lastName">Last Name</label>
                        <div class="col-md-4">
                                <input id="lastName" name="lastName" type="text" placeholder="Last Name" class="form-control" value="${sessionScope.user.lastName}">
                        </div>
                    </div>

                    <!-- File Button -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="upload">Upload profile pic</label>
                        <div class="col-md-4">
                                <input id="upload" name="photo" class="form-control" type="url" placeholder="Insert your photo URL (max. 300x300)"
                                       pattern="^(https|http).+(jpg|svg|gif|png)$">
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="login">Login</label>
                        <div class="col-md-4">
                                <input id="login" name="login" type="text" placeholder="Login" class="form-control input-md" value="${sessionScope.user.login}">
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="email">Email Address</label>
                        <div class="col-md-4">
                                <input id="email" name="email" type="text" placeholder="Email Address" class="form-control input-md" value="${sessionScope.user.email}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label col-xs-12" for="city">City</label>
                        <div class="col-md-4">
                            <input id="city" name="city" type="text" placeholder="City" class="form-control input-md" value="${sessionScope.user.city}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="address">Home Address</label>
                        <div class="col-md-4">
                            <input id="address" name="address" type="text" placeholder="Home Address" class="form-control input-md" value="${sessionScope.user.address}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="postal">Postal Index</label>
                        <div class="col-md-4">
                            <input id="postal" name="postal" type="text" placeholder="Postal Index" class="form-control input-md" value="${sessionScope.user.postalIndex}">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-offset-5">
                            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#change_password">Change Password</button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" ></label>
                        <div class="col-md-6-offset-2">
                            <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-thumbs-up"></span> Submit</button>
                        </div>
                    </div>
                </fieldset>
            </form>
            <div class="modal fade product_view" id="change_password">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form class="form-horizontal" method="POST" action="${pageContext.request.contextPath}/controller?command=change_password">
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="password">Old Password</label>
                                <div class="col-md-4">
                                    <div class="input-group">
                                        <input id="old_password" name="old_password" type="password" placeholder="Old Password" class="form-control input-md">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="password">New Password</label>
                                <div class="col-md-4">
                                    <div class="input-group">
                                        <input id="password" name="new_password" type="password" placeholder="New Password" class="form-control input-md">
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
                                <label class="col-md-4 control-label"></label>
                                <div class="col-md-4">
                                    <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-thumbs-up"></span> Submit</button>
                                    <input type="reset" class="btn btn-danger" value="Clear">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
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
