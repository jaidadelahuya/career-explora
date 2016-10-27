<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Collections</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/style/materialize.min.css">
<link rel="stylesheet" href="/style/materialize-tags.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet" href="/style/jquery.webui-popover.css">
<link rel="stylesheet" href="/style/dashboard.css">
<link rel="stylesheet" href="/style/main.css">
</head>
<body style="background-color: #f1f3f7">
	<%@ include file="/WEB-INF/main-nav.html"%>
	<%@ include file="/WEB-INF/collections/home.html"%>

	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/materialize.min.js"></script>
	<script src="/js/jquery.webui-popover.js"></script>
	<script type="text/javascript" src="/js/main.js"></script>
	
</body>
</html>