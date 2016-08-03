<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Unit</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/style/materialize.min.css">
<link rel="stylesheet" href="/style/materialize-tags.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>
<body>
	<div class="container"
		style="width: 60%; border: 1px solid gray; padding: 3%; margin-top: 4%;">
		<h4 style="text-align: center;">Add Unit</h4>
		<div class="row">
			<form action="<c:url value='/admin/save-unit' />" method="post">
				<c:choose>
					<c:when test="${ not empty formError}">
						<h5 style="color: white; background-color: red; padding: 2%;">
							<c:out value="${formError}" />
						</h5>
					</c:when>
					<c:when test="${ not empty formSuccess}">
						<h5 style="color: white; background-color: green; padding: 2%;">
							<c:out value="${formSuccess}" />
						</h5>
					</c:when>
				</c:choose>
				<div class="row">
					<div class="input-field col s12">
						<select name="community">
							<option value="" disabled selected>Select a community</option>
							<c:forEach var="item" items="${communityMap}">
								<option value="${item.key}">${item.value}</option>
							</c:forEach>
						</select> <label>Community</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<input id="unit-name" type="text" class="validate" name="name">
						<label for="community-name" data-error="wrong"
							data-success="right">Unit Name</label>
					</div>
				</div>
				
				<div class="row">
					<div class="col s6">
						<button class="btn waves-effect waves-light" type="submit"
							name="action">
							Submit <i class="material-icons right">send</i>
						</button>
					</div>
				</div>


			</form>
		</div>
	</div>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/materialize.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('select').material_select();
		});
	</script>
</body>
</html>