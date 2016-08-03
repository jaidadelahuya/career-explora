<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Admin User</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/style/materialize.min.css">
<link rel="stylesheet" href="/style/materialize-tags.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>
<body  class="indigo accent-1 " >
	
		<div class="row valign-wrapper">
			<div class="col s6 offset-s3">
				<div class="card-panel">
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
						<form class="col s12" method="post"
							action="<c:url value='/ca/admin/user/create' />">
							<div class="row">
								<div class="input-field col s6">
									<input id="first_name" type="text" name="first-name"
										class="validate"> <label for="first_name">First
										Name</label>
								</div>
								<div class="input-field col s6">
									<input name="last-name" id="last_name" type="text"
										class="validate"> <label for="last_name">Last
										Name</label>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s12">
									<input id="email" type="email" class="validate" name="email">
									<label for="email">Email</label>
								</div>
							</div>

							<div class="row">
								<div class="input-field col s12">
									<input id="password" type="password" class="validate"
										name="password"> <label for="password">Password</label>
								</div>
							</div>

							<div class="row">
								<div class="input-field col s12">
									<button class="btn waves-effect waves-light" type="submit"
										name="action">
										Submit <i class="material-icons right">send</i>
									</button>
								</div>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>
	


	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/materialize.min.js"></script>
</body>
</html>