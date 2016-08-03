<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<c:choose>
	<c:when test="${registrationForm.useMobile}">
		<title>Confirm mobile number</title>
	</c:when>
	<c:when test="${registrationForm.useEmail}">
		<title>Confirm email address</title>
	</c:when>
</c:choose>

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
			<div class="col-md-5 col-md-offset-1">
				<span class="front-page-logo-2">Career Explora</span>
			</div>
			<div class="col-md-6"></div>
		</div>
		<div class="row">
			<div class="col-sm-8 col-md-6 col-centered " style="margin-top: 5%; margin-bottom: 2%;">
				<div class="card-panel">
					<h4 class="mobile-font-header"
						style="margin-top: 4%; color: rgb(144, 144, 144); font-family: arial">Enter
						confirmation code</h4>
					<hr style="margin: 0; margin-bottom: 4%;" />
					<div class="alert ca-alert-info mobile-font">
						<c:choose>
							<c:when test="${registrationForm.useMobile}">
								<span style="display: block;"> An SMS with a confirmation
									code has been sent to <Strong><c:out
											value="${registrationForm.username}" /></Strong>.
								</span>
								<span style="display: block; margin-bottom: 2%;">Enter it
									here to continue</span>
							</c:when>
							<c:when test="${registrationForm.useEmail}">
								<span style="display: block;"> An Email with a
									confirmation code has been sent to <Strong><c:out
											value="${registrationForm.username}" /></Strong>.
								</span>
								<span style="display: block; margin-bottom: 2%;">Enter it
									here to continue</span>
							</c:when>
						</c:choose>
					</div>
					<form action="" id="confirmation-code-form">
						<div class="row">
							<div class="form-group col-md-6">
								<input type="text" class="form-control sign-up-input mobile-input"
									id="confirmation-code" placeholder="Confirmation code"
									name="confirmation-code" />
							</div>
							<div class="form-group col-md-6"></div>
						</div>
						<hr style="margin: 0; margin-bottom: 3%; margin-top: 1%;" />
						<div class="row">
							<div class="col-md-9">
								<div class="form-group mobile-font" id="reg-error-div"></div>
							</div>
							<div class="col-md-3 form-group">
								<input type="button" class="ca-btn-primary btn-lg pull-right mobile-input"
									id="continue" value="Continue" />
							</div>

						</div>
					</form>
				</div>
				<div class="col-md-10 col-centered">

					<table id="confirmation-table">
						<tr>
							<td><a class="mobile-font-link" id="resend-code" href="<c:url value='/resendcode'/>">Resend
									code</a></td>
							<c:choose>
								<c:when test="${from_password_recovery}">
									<c:if test="${fn:length(passwordRecoveryList) >0 }">
										<td><a class="mobile-font-link" href="<c:url value='/change-password-recovery-option'/>">Use a different email address or mobile number</a></td>
									 </c:if>
								</c:when>
								<c:otherwise>
									<td><a class="mobile-font-link" href="<c:url value='/editusername-mobile'/>">Change
											number</a></td>
									<td><a class="mobile-font-link" href="<c:url value='/editusername-email'/>">Confirm
											with email</a></td>
								</c:otherwise>
							</c:choose>


						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/validators.js"></script>
	<script type="text/javascript" src="/js/waitMe.js"></script>
	<script type="text/javascript" src="/js/confirmation-code.js"></script>
</body>
</html>