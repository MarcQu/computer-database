<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Company"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<title>Company Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="/Computer-database/CompanyMenu?nombre=${nombre}&page=${page}&search=${search}&sort=${sort}"> Application - Company Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <c:out value="${companyId}"/>	
                    </div>
                    <h1>Edit Company</h1>
					<c:if test="${success != ''}">
						<label id="success">${success}</label>
					</c:if>
                    <form action="/Computer-database/UpdateCompany" method="POST">
                        <input type="hidden" value="${companyId}" id="companyId" name="companyId"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="companyName">Company name</label>
                                <input type="text" class="form-control" id="companyName" name="companyName" placeholder="Company name" value="${companyName}">
                                <c:if test="${errorName != ''}">
                                	<label id="errorName">${errorName}</label>
                                </c:if>                            
                            </div>        
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="/Computer-database/CompanyMenu?nombre=${nombre}&page=${page}&search=${search}&sort=${sort}" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>