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
            <a class="navbar-brand" href="/webapp/"> Application - Accueil </a>
            <div style="float: right;">
            	<a href="/webapp/?lang=en">English</a> | <a href="?lang=fr">Fran�ais</a>
            </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="user.home"/></h1>
                    <br/>
                    <h3><spring:message code="user.select"/></h3>
					<h3><a href="/webapp/CompanyMenu?nombre=10&page=1&sort=asc&search=">Company</a></h3>
					<h3><a href="/webapp/ComputerMenu?nombre=10&page=1&sort=asc&search=">Computer</a></h3>
                </div>
            </div>
        </div>
    </section>
</body>
</html>