<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reset Password</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" type="text/css" href="/style/jquery-ui.css">
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">
</head>
<body class="cover-background">
	<div class="container-fluid" id="main">
		<div class="row xs-hidden sm-hidden front-page-header">
			<div class="col-md-5 col-md-offset-1">
				<span class="front-page-logo-2" class="mobile-logo">Career Explora</span>
			</div>
			<div class="col-md-6"></div>
		</div>
		<div class="row">
			<div class="col-sm-8 col-md-6 col-centered" style="margin-top: 5%;">
				<div id="confirmation-dialog" class="card-panel">
					<h4 class="mobile-font-header"
						style="margin-top: 4%; color: rgb(144, 144, 144); font-family: arial">Reset
						Password</h4>
					<hr style="margin: 0; margin-bottom: 4%;" />
					<div class="alert ca-alert-info mobile-font">
						<p>Your password should be between 7-21 characters long and
							should contain alphabets and at least a digit and a special
							character.</p>
					</div>
					<form action="" id="password-reset-form">
						<div class="row">
							<div class="form-group col-md-6">
								<input type="password" class="form-control sign-up-input mobile-input"
									placeholder="Enter password" name="password1" />
							</div>
							<div class="form-group col-md-6"></div>
						</div>
						<div class="row">
							<div class="form-group col-md-6">
								<input type="password" class="form-control sign-up-input mobile-input"
									placeholder="Re-enter password" name="password2" />
							</div>
							<div class="form-group col-md-6"></div>
						</div>
						<hr style="margin: 0; margin-bottom: 3%; margin-top: 1%;" />
						<div class="row">
							<div class="col-md-9">
								<div class="form-group mobile-font" id="reg-error-div"></div>
							</div>
							<div class="col-md-3 form-group">
								<input type="button" class="ca-btn-primary btn-lg pull-right"
									id="reset-password" value="Reset Password" />
							</div>

						</div>
					</form>
				</div>

			</div>
		</div>
	</div>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(
				function() {
					$("#reset-password").click(
							function(e) {
								var myForm = $("#password-reset-form");
								var msgDiv = $("#reg-error-div");
								msgDiv.addClass("alert alert-success");
								msgDiv.text("processing...");
								var jqxhr = $.post("/resetpassword",
										myForm.serialize()).done(
										function(data) {
											msgDiv.removeClass("alert alert-danger");
											msgDiv.addClass("alert alert-success");
											msgDiv.text("Password reset successful. Redirecting to login...");
											window.location.assign(data);
										}).fail(
										function(jqXHR, status, errorThrown) {
											msgDiv.removeClass("alert alert-success");
											msgDiv.addClass("alert alert-danger");
											msgDiv.text(errorThrown);
										});
							});
				});
	</script>

</body>
</html>