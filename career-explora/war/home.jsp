<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Community</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/style/materialize.min.css">
<link rel="stylesheet" href="/style/materialize-tags.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet" href="/style/jquery.webui-popover.css">
<style type="text/css">
.ca-collection-text {
	line-height: 1;
	font-size: 1.2rem;
	padding-bottom: 4px
}

.ca-collection:hover,  .active  {
	background-color: #627aac
}

.ca-collection-icon {
	line-height: 1;
	font-size: 1.2rem;
}

.ca-collection {
	padding: 3% !important;
	cursor: pointer;
}

.ca-collection i {
	margin-right: 25px;
	margin-left: 25px;
	padding: 2%;
	background-color: red
}

main {
	padding-left: 300px;
}

@media only screen and (max-width : 992px) {
	main {
		padding-left: 0;
	}
}
</style>
</head>
<body style="background-color: #f1f3f7">
	<%@ include file="/WEB-INF/main-nav.html"%>
	<%@ include file="/WEB-INF/home.html"%>
	

	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/materialize.min.js"></script>
	<script src="/js/jquery.webui-popover.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".button-collapse").sideNav();
		});
	</script>
</body>
</html>