<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/css/font-awesome.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/css/main.css"/>" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand"> Application - Login Page </a>
            <div style="float: right;">
            	<a href="/webapp/?lang=en">English</a> | <a href="?lang=fr">Français</a>
            </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
					<form action="<spring:url value="/login"/>" method="POST">		
						<fieldset>
                            <div class="form-group">
                                <label for="username">Login:</label>
                                <input type="text" class="form-control" id="username" name="username" placeholder="login" value="">                            
                            </div>
                            <div class="form-group">
                                <label for="password">Password:</label>
                                <input type="password" class="form-control" id="password" name="password" placeholder="password" value="">                            
                            </div>
						</fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="login" class="btn btn-primary">
                        </div>
					</form>			
                </div>
            </div>
        </div>
    </section>
</body>
</html>