<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isErrorPage='true'%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Career Explora Login</title>
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
				<span class="front-page-logo-2" class="mobile-logo">Career Explora</span> <a
					href="<c:url value='/tosignup'/>"
					class="ca-btn-primary btn-sm v style="margin-bottom: 2%; margin-top: 1%;">Sign
					up</a>
			</div>
			<div class="col-md-6"></div>
		</div>

		<div class="row">
			<div class="col-sm-8 col-lg-6 col-centered" style="margin-top: 5%;">

				<div id="login-dialog" class="card-panel">
					<h4 class="mobile-font-header"
						style="margin-top: 4%; color: rgb(144, 144, 144); font-family: arial">Career
						Explora Login</h4>
					<hr style="margin: 0; margin-bottom: 4%;" />

					<c:choose>
						<c:when test="${incorrectUsername}">
							<div class="alert ca-alert-info mobile-font">
								<c:choose>
									<c:when test="${incorrectEmail}">
										<h4 style="color: black;">Incorrect Email</h4>
										<p>The Email you entered does not belong to any account.</p>
									</c:when>
									<c:when test="${incorrectMobile}">
										<h4 style="color: black;">Incorrect Mobile number</h4>
										<p>The mobile number you entered does not belong to any
											account.</p>
									</c:when>
									<c:otherwise>
										<h4 style="color: black;">Incorrect username</h4>
										<p>The username you entered does not belong to any
											account.</p>
									</c:otherwise>
								</c:choose>

								<p>You can log in using the email address, or mobile phone
									number you verified when opening your account or your username.
									</p>
									<p>Make sure that it is typed correctly.</p>
							</div>
						</c:when>
						<c:when test="${incorrectPassword}">
							<div class="alert ca-alert-info mobile-font">
								<h4 style="color: black;">Please re-enter your password</h4>
								<p>The password you entered is incorrect. Please try again
									(make sure your caps lock is off).</p>
								<p>
									Forgot your password? <a href="<c:url value='/password-recovery'/>">Request a new one.</a>
								</p>
							</div>
						</c:when>

						<c:when test="${notLoggedIn}">
							<div class="alert ca-alert-info mobile-font">
								<h4 class="mobile-font-header" style="color: black;">You are not logged in.</h4>
								<p>To access your account, You have to log in properly.</p>
							</div>
						</c:when>
						
						<c:when test="${fromPayment}">
							<div class="alert alert-success mobile-font">
								<h4 class="mobile-font-header" style="color: black;">Subscription Successful.</h4>
								<p>You have been subscribed for 12 months</p>
								<p>You can now login with your Email Address or Mobile number.</p>
							</div>
						</c:when>

					</c:choose>

					<form action="/login" id="login-form" method="post">
						<div class="row">
							<div class="col-md-8 col-md-offset-2">
								<table style="width: 100%;">
									<tr>
										<td><label class="pull-right mobile-font" for="username"
											style="margin: 2%; font: arial;font-weight: normal;">Email
												or Phone :</label></td>
										<td><c:choose>
												<c:when test="${incorrectUsername}">
													<input type="text" id="username" name="username"
														style="margin: 2%;" autofocus="autofocus" />
												</c:when>
												<c:otherwise>
													<input type="text" id="username" name="username"
														style="margin: 2%;" value="${username}" />
												</c:otherwise>
											</c:choose></td>
									</tr>
									<tr>
										<td><label class="pull-right mobile-font" for="password"
											style="margin: 2%; font: arial;font-weight: normal;">Password
												: </label></td>
										<td><c:choose>
												<c:when test="${incorrectPassword}">
													<input type="password" id="password" name="password"
														style="margin: 2%;" autofocus="autofocus" />
												</c:when>
												<c:otherwise>
													<input type="password" id="password" name="password"
														style="margin: 2%;" />
												</c:otherwise>
											</c:choose></td>
									</tr>
									<tr>
										<td></td>
										<td style="color: gray; font-size: 10pt; "><input type="checkbox"
											style="margin: 2%;" /> Keep me logged in</td>
									</tr>
									<tr>
										<td></td>
										<td class="login-text" colspan="2"><input
											class="ca-btn-primary btn-md" id="login" type="submit"
											style="margin: 2%;" value="Login"> or <a
											href="<c:url value='/tosignup'/>">Sign up</a></td>
									</tr>
									<tr>
										<td></td>
										<td class="login-text"><a href="<c:url value='/password-recovery'/>" style="margin: 2%;">Forgotten
												your password?</a></td>
									</tr>
								</table>
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

</body>
</html>