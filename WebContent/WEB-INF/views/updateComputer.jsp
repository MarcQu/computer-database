<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="model.Company"%>
<%@ page import="java.util.ArrayList"%>
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
            <a class="navbar-brand" href="/Computer-database/ComputerMenu?nombre=${nombre}&page=${page}&sort=${sort}&search=${search}"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <c:out value="${computerId}"/>	
                    </div>
                    <h1>Edit Computer</h1>
					<c:if test="${success != ''}">
						<label id="success">${success}</label>
					</c:if>
                    <c:if test="${errorName != ''}">
                    	<label id="errorName">${errorName}</label>
                    </c:if>
                    <c:if test="${errorDate != ''}">
                    	<label id="errorDate">${errorDate}</label>                            
                    </c:if>
                    <c:if test="${error != ''}">
                    	<label id="error">${error}</label>
                    </c:if>
                    <form action="/Computer-database/UpdateComputer" method="POST">
                        <input type="hidden" value="${computerId}" id="computerId" name="computerId"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name" value="${computerName}">                            
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" value="${introduced}">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value="${discontinued}">                             
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                	<option value="${companyComputer.id}"><c:out value="${companyComputer.name}"/></option>										
				                	<c:forEach var="company" items="${companies}">
										<c:if test="${companyComputer.id != company.id}">
											<option value="${company.id}"><c:out value="${company.name}"/></option>										
										</c:if>
				                	</c:forEach>                              
								</select>
                            </div>
                           	<input type ="hidden" name="nombre" value="${nombre}">
                           	<input type ="hidden" name="page" value="${page}">    
                           	<input type ="hidden" name="search" value="${search}">
                           	<input type ="hidden" name="sort" value="${sort}">   
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="/Computer-database/ComputerMenu?nombre=${nombre}&page=${page}&sort=${sort}&search=${search}" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>