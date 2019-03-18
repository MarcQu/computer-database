<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="model.Computer"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/css/font-awesome.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/css/main.css"/>" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="/Computer-database/"> Application - Computer Database </a>
            <div style="float: right;">
            	<a href="/Computer-database/ComputerMenu?nombre=${nombre}&page=${page}&search=${search}&sort=${sort}&lang=en">English</a> | <a href="/Computer-database/ComputerMenu?nombre=${nombre}&page=${page}&search=${search}&sort=${sort}&lang=fr">Français</a>
            </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${nombreComputers}"/>
                <c:choose>
                	<c:when test="${nombreComputers > 1}"><spring:message code="user.computers"/></c:when>
                	<c:otherwise><spring:message code="user.computer"/></c:otherwise>
                </c:choose>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="/Computer-database/ComputerMenu" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="<spring:message code="user.search"/>" value="${search}" />
                        <input type="hidden" name="nombre" value="10" />
                        <input type="hidden" name="page" value="1" />       
                        <input type="hidden" name="sort" value="" />                 
                        <input type="submit" id="searchsubmit" value="<spring:message code="user.filter"/>" class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="/Computer-database/CreateComputer"><spring:message code="user.addComputer"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="user.edit"/></a>
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
                        <!-- Table header for Computer Name -->
                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
	                            <a href="/Computer-database/DeleteComputer?nombre=${nombre}&page=${page}&search=${search}&sort=${sort}" id="deleteSelected" onclick="deleteComputer();">
	                                <i class="fa fa-trash-o fa-lg"></i>
	                            </a>
                            </span>
                        </th>
                        <th>
                            <spring:message code="user.nameComputer"/>
                            <c:choose>
                            	<c:when test="${sort eq 'desc'}">
                            		<a href="/Computer-database/ComputerMenu?nombre=${nombre}&page=${page}&search=${search}&sort=asc" onclick="" class="fa fa-fw fa-sort"></a>
                            	</c:when>
                            	<c:otherwise>
                            		<a href="/Computer-database/ComputerMenu?nombre=${nombre}&page=${page}&search=${search}&sort=desc" onclick="" class="fa fa-fw fa-sort"></a>                            		
                            	</c:otherwise>
                            </c:choose>
                        </th>
                        <th>
                            <spring:message code="user.introduced"/>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <spring:message code="user.discontinued"/>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <spring:message code="user.computerCompany"/>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach var="computer" items="${computers}">
	                    <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="${computer.getId()}">
	                        </td>
	                        <td>
	                            <a href="/Computer-database/UpdateComputer?computerId=${computer.getId()}&nombre=${nombre}&page=${page}&search=${search}&sort=${sort}" onclick=""><c:out value="${computer.getName()}"/></a>
	                        </td>
	                        <td><c:out value="${computer.getIntroduced()}"/></td>
	                        <td><c:out value="${computer.getDiscontinued()}"/></td>
	                        <td><c:out value="${computer.getCompany().getName()}"/></td>
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
	                    <a href="/Computer-database/ComputerMenu?nombre=${nombre}&page=${page-1}&search=${search}&sort=${sort}" aria-label="Previous">
	                      <span aria-hidden="true">&laquo;</span>
	                    </a>
	                </li>
				</c:if>
                <c:forEach var="page" begin="1" end="${pages}" step="1">
                    <li><a href="/Computer-database/ComputerMenu?nombre=${nombre}&page=${page}&search=${search}&sort=${sort}">${page}</a></li>
                </c:forEach>
     	        <c:if test="${page < pages}">
	                <li>
		               <a href="/Computer-database/ComputerMenu?nombre=${nombre}&page=${page+1}&search=${search}&sort=${sort}" aria-label="Next">
		                   <span aria-hidden="true">&raquo;</span>
		               </a>
	            	</li>     	        
                </c:if>
        	</ul>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <a href="/Computer-database/ComputerMenu?nombre=10&page=1&search=${search}&sort=${sort}" onclick="" class="btn btn-default">10</a>
            <a href="/Computer-database/ComputerMenu?nombre=50&page=1&search=${search}&sort=${sort}" onclick="" class="btn btn-default">50</a>
            <a href="/Computer-database/ComputerMenu?nombre=100&page=1&search=${search}&sort=${sort}" onclick="" class="btn btn-default">100</a>
        </div>
    </footer>
<script src="<c:url value="/js/jquery.min.js"/>"></script>
<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/js/dashboard.js"/>"></script>
<script src="jquery.js"></script>
<script>
	function deleteComputer(){
		var computers = $("input.cb");
		var selected = [];
		if (confirm("Are you sure you want to delete the selected computers?")){
			for(var i = 0; i < computers.length; i++){
				if (computers[i].checked) {
					selected.push(computers[i].value);
				}
			}
			if(selected.length > 0){
				document.getElementById("deleteSelected").href = document.getElementById("deleteSelected").href+"&selected="+selected;
			}			
		}
	}
</script>
</body>
</html>