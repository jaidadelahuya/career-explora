<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><c:out value="${userProfile.firstName}" /> <c:out
		value="${userProfile.lastName}" /> | profile</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" type="text/css" href="/style/jquery-ui.css">
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" type="text/css"
	href="/style/jquery.webui-popover.min.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">
</head>
<body style="padding-top: 50px" class="cover-background">
	<div id="fb-root"></div>
	<script>
		window.fbAsyncInit = function() {
			FB.init({
				appId : '876754309045840',//876754309045840 907361745985096
				cookie : true,
				xfbml : true,
				version : 'v2.2'
			});
		};
		(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id))
				return;
			js = d.createElement(s);
			js.id = id;
			js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.3";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
	</script>


	<%@ include file="/WEB-INF/nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8" id="main"
				style="padding-right: 0px; z-index: 4">
				<%@ include file="/WEB-INF/cover.jsp"%>
				<%@ include file="/WEB-INF/subnav.jsp"%>
				<div id="sub-main" class="row">
					<%@ include file="/pages/partials/module.html"%>
					<c:choose>
						<c:when test="${userProfile.currentUser}">
							<%@ include file="/WEB-INF/current-user-profile"%>
						</c:when>
						<c:otherwise>
							<%@ include file="/WEB-INF/non-current-user-profile"%>
						</c:otherwise>
					</c:choose>
					<div class="hidden-xs col-sm-2"
						style="height: 100%; position: fixed; top: 0; right: 0; padding-left: 3%; margin: 0">
						<div class="hidden-xs col-sm-6" style="height: 100%;"></div>
						<div class="hidden-xs col-sm-6" style="height: 100%;"></div>
					</div>
				</div>

			</div>

		</div>

		<div id="modals"></div>
		<%@ include file="/WEB-INF/modals.jsp"%>
	</div>

	<script src="/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/jquery.webui-popover.min.js"></script>
	<script type="text/javascript" src="/js/waitMe.js"></script>
	<script type="text/javascript" src="/js/init.js"></script>
	<script type="text/javascript" src="/js/modules.js"></script>
	<script type="text/javascript" src="/js/my-profile.js"></script>
</body>
</html>

