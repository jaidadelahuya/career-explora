<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Career Explora Authorization</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">
</head>
<body class="main-body">

	<div class="container-fluid" id="main">
		<div class="row index-header">

			<div class="col-md-offset-1 col-md-11 ">
				<span id="logo">Career Explora</span>
			</div>
		</div>


		<div class="row">
			<div class="col-sm-offset-1 col-sm-5 dialog-div">
				<h3 style="margin-bottom: 5%; margin-left: 1%;"
					class="mobile-font-header">
					Welcome <span id="username"><c:out
							value="${azureUser.firstName}" /></span>, <small style="color: blue;">get
						ready to explore</small>
				</h3>
				<div id="confirmation-dialog" class="mobile-font">
					<h4 class="mobile-font-header"
						style="margin-bottom: 5%; color: rgb(144, 144, 144);">
						Confirm your payment</h4>
					<form action="<c:url value='/verifytoken'/>" role="form"
						class="wait-me" method="post" id="authorization-form">
						<div class="row">

							<div class="form-group col-sm-12">
								<label for="access-token">Enter your Authorization Code:</label>
								<input type="text" class="form-control mobile-input"
									id="access-token" name="access-token" />
							</div>
							<input name="authorization" value="true" type="hidden"
								id="authorization" />
							<div class="form-group col-sm-12">
								<button type="button" class="btn btn-warning"
									id="verify-access-token">Verify and Subscribe me</button>
							</div>
						</div>
					</form>
					<a id="continue" href="<c:url value="/azure/success"/>"></a>
				</div>
				<div id="message-div" class="col-sm-12"></div>
				<!-- <div style="text-align: center;">dont have an authorization code? you can get <strong><a href="<c:url value='/free-access' />" class="btn btn-success btn-xs">Free Limited Acesss</a></strong></div> -->
			</div>

			<div class="col-sm-5">
				<div class="like-confirmation" style="border: none;">
					<h4 class="mobile-font-header" style="color: rgb(144, 144, 144)">
						Don't have an Authorization Code? <small style="color: blue;">try
							other payment options</small>
					</h4>
					<hr></hr>

					<div class="col-sm-12 col-md-6">
						<form action="https://www.paypal.com/cgi-bin/webscr" method="post"
							target="_top">
							<input type="hidden" name="cmd" value="_s-xclick"> <input
								type="hidden" name="hosted_button_id" value="HNEH7UBEXQC5Y">
							<input type="hidden" name="return" value="<c:url value='/paymentsuccess'/>" />
							<input type="hidden" name="cancel-return" value="<c:url value='/payment-failure.html'/>" />
							<input type="hidden" name="notify" value="<c:url value='/paypalnotify'/>" />
							<input type="image"
								src="https://www.paypalobjects.com/en_US/i/btn/btn_subscribeCC_LG.gif"
								border="0" name="submit"
								alt="PayPal - The safer, easier way to pay online!"> <img
								alt="" border="0"
								src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif"
								width="1" height="1">
						</form>

					</div>

				</div>
			</div>

			<div class="col-sm-1"></div>
		</div>
	</div>


	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/authorization.js"></script>
	<script type="text/javascript" src="/js/waitMe.js"></script>
</body>
</html>