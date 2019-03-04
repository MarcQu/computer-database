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
                <c:out value="${nombreCompanies}"/>
                <c:choose>
                	<c:when test="${nombreCompanies > 1}">Companies found</c:when>
                	<c:otherwise>Company found</c:otherwise>
                </c:choose>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="/Computer-database/CompanyMenu" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" value="${search}" />
                        <input type="hidden" name="nombre" value="10" />
                        <input type="hidden" name="page" value="1" />
                        <input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addCompany" href="/Computer-database/CreateCompany">Add Company</a> 
                    <a class="btn btn-default" id="editCompany" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>
        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Company Name -->
                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
	                            <a href="/Computer-database/DeleteCompany?nombre=${nombre}&page=${page}&search=${search}&sort=${sort}" id="deleteSelected" onclick="$.fn.deleteSelected();deleteCompany();">
	                                <i class="fa fa-trash-o fa-lg"></i>
	                            </a>
                            </span>
                        </th>
                        <th>
                            Company name
                            <c:choose>
                            	<c:when test="${sort eq 'desc'}">
                            		<a href="/Computer-database/CompanyMenu?nombre=${nombre}&page=${page}&search=${search}&sort=asc" onclick="" class="fa fa-fw fa-sort"></a>
                            	</c:when>
                            	<c:otherwise>
                            		<a href="/Computer-database/CompanyMenu?nombre=${nombre}&page=${page}&search=${search}&sort=desc" onclick="" class="fa fa-fw fa-sort"></a>                            		
                            	</c:otherwise>
                            </c:choose>
                        </th>
                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach var="company" items="${companies}">
						<tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="${company.getId()}">
	                        </td>
	                        <td>
	                            <a href="/Computer-database/UpdateCompany?companyId=${company.getId()}&search=${search}&sort=${sort}" onclick=""><c:out value="${company.getName()}"/></a>
	                        </td>
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
	                    <a href="/Computer-database/CompanyMenu?nombre=${nombre}&page=${page-1}&search=${search}&sort=${sort}" aria-label="Previous">
	                      <span aria-hidden="true">&laquo;</span>
	                    </a>
	                </li>
				</c:if>
                <c:forEach var="page" begin="1" end="${pages}" step="1">
                    <li><a href="/Computer-database/CompanyMenu?nombre=${nombre}&page=${page}&search=${search}&sort=${sort}">${page}</a></li>
                </c:forEach>
     	        <c:if test="${page < pages}">
	                <li>
		               <a href="/Computer-database/CompanyMenu?nombre=${nombre}&page=${page+1}&search=${search}&sort=${sort}" aria-label="Next">
		                   <span aria-hidden="true">&raquo;</span>
		               </a>
	            	</li>   	        
                </c:if>                
        	</ul>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <a href="/Computer-database/CompanyMenu?nombre=10&page=1&search=${search}&sort=${sort}" onclick="" class="btn btn-default">10</a>
            <a href="/Computer-database/CompanyMenu?nombre=50&page=1&search=${search}&sort=${sort}" onclick="" class="btn btn-default">50</a>         
           	<a href="/Computer-database/CompanyMenu?nombre=100&page=1&search=${search}&sort=${sort}" onclick="" class="btn btn-default">100</a>
        </div>
    </footer>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
<script>
	function deleteCompany(){
		var companies = $("input.cb");
		var selected = [];
		for(var i = 0; i < companies.length; i++){
			if (companies[i].checked) {
				selected.push(companies[i].value);
			}
		}
		if(selected.length > 0){
			document.getElementById("deleteSelected").href = document.getElementById("deleteSelected").href+"&selected="+selected;
		}
	}
</script>
</body>
</html>