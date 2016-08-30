<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome <c:out value="${azureUser.firstName}" /> <c:out
		value="${azureUser.lastName}" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/style/dashboard.css">
<link rel="stylesheet" href="/style/materialize.min.css">
<link rel="stylesheet" href="/style/materialize-tags.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

</head>
<body class="main-body cover-background">
	<div class="row front-page-header">

		<div class="col s11 offset-s1">
			<span class="front-page-logo-2">Career Explora</span>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="col s12">
				<ul class="tabs">
					<li class="tab col s3"><a href="#test1">Test 1</a></li>
					<li class="tab col s3"><a class="active" href="#test2">Test
							2</a></li>
					<li class="tab col s3 disabled"><a href="#test3">Disabled
							Tab</a></li>
					<li class="tab col s3"><a href="#test4">Test 4</a></li>
				</ul>
			</div>
			<div id="test1" class="col s12">Test 1</div>
			<div id="test2" class="col s12">Test 2</div>
			<div id="test3" class="col s12">Test 3</div>
			<div id="test4" class="col s12">Test 4</div>
		</div>
	</div>

</body>
</html>