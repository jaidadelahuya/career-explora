<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Verification Pending</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" type="text/css" href="/style/jquery-ui.css">
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
</head>
<body class="main-body">
	<div class="container-fluid" id="main">
		<div class="row xs-hidden sm-hidden index-header">
			<div class="col-md-5 col-md-offset-1">
				<span id="logo" class="mobile-logo">Career Explora</span>
			</div>
			<div class="col-md-6"></div>
		</div>
		<div class="row">
			<div class="col-sm-8 col-lg-6 col-centered" style="margin-top: 5%;">
				<div id="payment-failure-dialog" class="dialog-div">
					<h4 class="mobile-font-header"
						style="margin-top: 4%; color: red; font-family: arial">Payment
						Verification Pending</h4>
					<hr style="margin: 0; margin-bottom: 4%;" />

					<div class="alert alert-danger mobile-font">
						<h4 style="color: black;">Payment Verification in progress</h4>
						<p>
							<strong><c:out value="${azureUser.firstName}" /></strong>, 
							Your payment is being verified.
						</p>
						<p>This may take some time depending on your internet
							connections.</p>
						<p>
							Click <a href="<c:url value='/paymentsuccess'/>">here</a> after 5
							mins.
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>