<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <!-- Website CSS style -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Website Font style -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register-style.css">
    <!-- Google Fonts -->
    <link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>

    <title>Registration</title>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container">
    <div class="row main">
        <div class="main-login main-center">
            <form method="post" action="${pageContext.request.contextPath}/controller">
                <input type="hidden" name="command" value="signup">
                <div class="form-group">
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-users fa-fw" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="login" id="login"  placeholder="Login"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-envelope fa-fw" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="email" id="email"  placeholder="Email"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user fa-fw" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="firstName" id="firstName"  placeholder="First Name"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user fa-fw" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="lastName" id="lastName"  placeholder="Last Name"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-location-arrow fa-fw" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="city" id="city"  placeholder="City"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-map-marker fa-fw" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="address" id="address"  placeholder="Home Address"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-envelope-o fa-fw" aria-hidden="true"></i></span>
                            <input type="text" class="form-control" name="postal" id="postal"  placeholder="Postal Index"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-lock fa-fw" aria-hidden="true"></i></span>
                            <input type="password" class="form-control" name="password" id="password"  placeholder="Password"/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="cols-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-lock fa-fw" aria-hidden="true"></i></span>
                            <input type="password" class="form-control" name="confirm" id="confirm"  placeholder="Confirm your Password"/>
                        </div>
                    </div>
                </div>

                <div class="form-group ">
                    <button type="submit" id="button" class="btn btn-primary btn-lg btn-block login-button">Register</button>
                </div>

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

