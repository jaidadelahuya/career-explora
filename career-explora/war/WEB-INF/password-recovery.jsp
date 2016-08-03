<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isErrorPage='true'%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Career Explora Password Recovery</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" type="text/css" href="/style/jquery-ui.css">
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">
</head>
<body class="main-body cover-background">
	<div class="container-fluid" id="main">
		<div class="row xs-hidden sm-hidden front-page-header">
			<div class="col-md-11 col-md-offset-1" >
				<span  class="mobile-logo front-page-logo-2">Career Explora</span> <a
					href="<c:url value='/tosignup'/>"
					class="mobile-font ca-btn-primary btn-sm" style="margin-bottom: 2%; margin-top: 1%; text-decoration: none;color: white;">Sign
					up</a>
			</div>
			
		</div>

		<div class="row">
			<div class="col-sm-8 col-lg-6 col-centered" style="margin-top: 5%;">

				<div id="password-recovery-dialog" class="card-panel" >
					<h4 class="mobile-font-header"
						style="margin-top: 4%; color: rgb(144, 144, 144); font-family: arial">Career
						Explora Password Recovery</h4>
					<hr style="margin: 0; margin-bottom: 4%;" />

					<div class="alert ca-alert-info mobile-font">
						<h4 style="color: black;">Forgot your password?</h4>
						<p>Enter your email, mobile number or username.</p>
						<p>A confirmation code will be sent to your email or mobile
							number.</p>
						<p>If you enter your username, a confirmation code will be
							sent to your default password recovery number if it has been
							verified.</p>
					</div>

					<form id="password-recovery-form" method="post">
						<div class="row">
							<div class="form-group col-md-6">
								<input type="text" class="form-control sign-up-input mobile-input"
									id="username" placeholder="Email or Mobile number"
									name="username" />
							</div>
							<div class="form-group col-md-6"></div>
						</div>
						<hr style="margin: 0; margin-bottom: 3%; margin-top: 1%;" />
						<div class="row">
							<div class="col-sm-6 col-md-7">
								<div class="form-group mobile-font" id="reg-error-div"></div>
							</div>
							<div class="col-sm-6 col-md-5 form-group">
								<input type="button" class="ca-btn-primary btn-lg mobile-font"
									id="send-code" value="Send confirmation code" style="margin-bottom: 1%"/>
							</div>
						</div>

					</form>
				</div>

			</div>
		</div>
	</div>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/validators.js"></script>
	<script type="text/javascript" src="/js/waitMe.js"></script>
	<script type="text/javascript" src="/js/password-recovery.js"></script>
</body>
</html>