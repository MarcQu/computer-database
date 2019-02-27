<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Company"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<title>Company Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="index.jsp"> Application - Company Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${nombreCompanies}"/> Companies found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
            </div>
        </div>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->
                        <th>
                            Company id
                        </th>
                        <th>
                            Company name
                        </th>
                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach var="company" items="${companies}">
						<tr id="companiesList">
	                        <td><c:out value="${company.getId()}"/></td>
	                        <td><c:out value="${company.getName()}"/></td>
						</tr>
                	</c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom" style="height:auto;">
        <div class="container text-center">
            <ul class="pagination">
	            <c:if test="${page > 1}">
	                <li>
	                    <a href="/Computer-database/CompanyMenu?nombre=${nombre}&page=${page-1}" aria-label="Previous">
	                      <span aria-hidden="true">&laquo;</span>
	                    </a>
	                </li>
				</c:if>
                <c:forEach var="page" begin="1" end="${pages}" step="1">
                    <li><a href="/Computer-database/CompanyMenu?nombre=${nombre}&page=${page}">${page}</a></li>
                </c:forEach>
     	        <c:if test="${page < pages}">
	                <li>
		               <a href="/Computer-database/CompanyMenu?nombre=${nombre}&page=${page+1}" aria-label="Next">
		                   <span aria-hidden="true">&raquo;</span>
		               </a>
	            	</li>   	        
                </c:if>                
        	</ul>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <a href="/Computer-database/CompanyMenu?nombre=10&page=1" onclick="" class="btn btn-default" role="button">10</a>
            <a href="/Computer-database/CompanyMenu?nombre=50&page=1" onclick="" class="btn btn-default" role="button">50</a>         
           	<a href="/Computer-database/CompanyMenu?nombre=100&page=1" onclick="" class="btn btn-default" role="button">100</a>
        </div>
    </footer>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>