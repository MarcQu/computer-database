<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<title>Company Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/css/font-awesome.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/css/main.css"/>" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="/webapp/CompanyMenu?nombre=10&page=1&sort=asc&search="> Application - Company Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Company</h1>
					<c:if test="${success != ''}">
						<label id="success">${success}</label>
					</c:if>
                    <c:if test="${errorName != ''}">
                    	<label id="errorName">${errorName}</label>
                    </c:if>
                    <c:if test="${error != ''}">
                    	<label id="error">${error}</label>
                    </c:if>                    
                    <form method="POST" action="CreateCompany">
                        <fieldset>
                            <div class="form-group">
                                <label for="companyName">Company name</label>
                                <input type="text" class="form-control" id="companyName" name="companyName" placeholder="Company name">
                            </div>      
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="/webapp/CompanyMenu?nombre=10&page=1&sort=asc&search=" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>