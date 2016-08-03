<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Career Explora Admin Dashboard</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/style/materialize.min.css">
<link rel="stylesheet" href="/style/materialize-tags.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>
<body class="blue lighten-5">
	<div class="navbar-fixed">
		<nav class="indigo darken-4">
			<div class="nav-wrapper">
				<a style="margin-right: 2%; margin-left: 1%; font-size: 13pt" href="#" data-activates="slide-out" class="button-collapse show-on-large"><i class="material-icons">menu</i></a>
				<a href="#!" class="brand-logo">Career Explora Admin</a>
				<ul class="right hide-on-med-and-down">
					
					<li><a href="#">Log out</a></li>
				</ul>
			</div>
		</nav>
	</div>

	<ul id="slide-out" class="side-nav">
      <li><a href="#!">First Sidebar Link</a></li>
      <li><a href="#!">Second Sidebar Link</a></li>
    </ul>
    
    <div class="container">
    	<div class="row" style="margin: 2%;">
    		<div class="col s3">
    			<div class="card green-text text-darken-2" style="text-align: center;">
    				<div><a href="/ca/admin/user/form/create"><i class="large material-icons">perm_identity</i></a></div>
    				<a href="/ca/admin/user/form/create">New User</a>
    			</div>
    		</div>
    		<div class="col s3">
    			<div class="card blue-text text-darken-2" style="text-align: center;">
    				<div><a href="/ca/admin/community/form/new"><i class="large material-icons">loyalty</i></a></div>
    				<a href="/ca/admin/community/form/new">New Community</a>
    			</div>
    		</div>
    		<div class="col s3">
    			<div class="card red-text text-darken-2" style="text-align: center;">
    				<div><a href="/ca/admin/communities/get"><i class="large material-icons">library_add</i></a></div>
    				<a href="/ca/admin/communities/get">New Unit</a>
    			</div>
    		</div>
    		<div class="col s3">
    			<div class="card orange-text text-darken-2" style="text-align: center;">
    				<div><a href="/ca/admin/discussion/form/new"><i class="large material-icons">note_add</i></a></div>
    				<a href="/ca/admin/discussion/form/new">New Discussion</a>
    			</div>
    		</div>
    		
    	</div>
    </div>
    
          


	


	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/materialize.min.js"></script>
	<script>
		$(document).ready(function() {
			// Activate the side menu 
			$(".button-collapse").sideNav();
		});
	</script>
</body>
</html>