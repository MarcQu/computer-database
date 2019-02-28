<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Computer"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
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
            <a class="navbar-brand" href="index.jsp"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${nombreComputers}"/>
                <c:choose>
                	<c:when test="${nombreComputers > 1}">Computers found</c:when>
                	<c:otherwise>Computer found</c:otherwise>
                </c:choose>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="/Computer-database/ComputerMenu" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" value="${search}" />
                        <input type="hidden" name="nombre" value="10" />
                        <input type="hidden" name="page" value="1" />                        
                        <input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="/Computer-database/CreateComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
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
	                            <a href="/Computer-database/DeleteComputer?nombre=${nombre}&page=${page}" id="deleteSelected" onclick="$.fn.deleteSelected();deleteComputer();">
	                                <i class="fa fa-trash-o fa-lg"></i>
	                            </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
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
	                            <a href="/Computer-database/UpdateComputer?computerId=${computer.getId()}" onclick=""><c:out value="${computer.getName()}"/></a>
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
	                    <a href="/Computer-database/ComputerMenu?nombre=${nombre}&page=${page-1}&search=${search}" aria-label="Previous">
	                      <span aria-hidden="true">&laquo;</span>
	                    </a>
	                </li>
				</c:if>
                <c:forEach var="page" begin="1" end="${pages}" step="1">
                    <li><a href="/Computer-database/ComputerMenu?nombre=${nombre}&page=${page}&search=${search}">${page}</a></li>
                </c:forEach>
     	        <c:if test="${page < pages}">
	                <li>
		               <a href="/Computer-database/ComputerMenu?nombre=${nombre}&page=${page+1}&search=${search}" aria-label="Next">
		                   <span aria-hidden="true">&raquo;</span>
		               </a>
	            	</li>     	        
                </c:if>
        	</ul>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <a href="/Computer-database/ComputerMenu?nombre=10&page=1&search=${search}" onclick="" class="btn btn-default" role="button">10</a>
            <a href="/Computer-database/ComputerMenu?nombre=50&page=1&search=${search}" onclick="" class="btn btn-default" role="button">50</a>
            <a href="/Computer-database/ComputerMenu?nombre=100&page=1&search=${search}" onclick="" class="btn btn-default" role="button">100</a>
        </div>
    </footer>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
<script src="jquery.js"></script>
<script>
	function deleteComputer(){
		var computers = $("input.cb");
		var selected = [];
		for(var i = 0; i < computers.length; i++){
			if (computers[i].checked) {
				selected.push(computers[i].value);
			}
		}
		if(selected.length > 0){
			document.getElementById("deleteSelected").href = document.getElementById("deleteSelected").href+"&selected="+selected;
		} else {
			document.getElementById("deleteSelected").href = document.getElementById("deleteSelected").href;
		}
	}
</script>
</body>
</html>