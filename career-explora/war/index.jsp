<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Career Explora</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">


</head>
<body style="background-color: white;">
	<div id="fb-root"></div>
	<script>
		window.fbAsyncInit = function() {
			FB.init({
				appId : '876754309045840',//876754309045840 907361745985096
				cookie : true, // enable cookies to allow the server to access
				// the session
				xfbml : true, // parse social plugins on this page
				version : 'v2.3' // use version 2.1
			});
			FB.getLoginStatus(function(response) {
				statusChangeCallback(response);
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

	<div class="container-fluid" id="main">
		<c:choose>
			<c:when test='${toSignUp}'>
				<%@ include file="/pages/partials/mobile-sign-up.html"%>
			</c:when>
			<c:otherwise>
				<div class="row hidden-sm hidden-md hidden-lg index-header">
					<div class="mobile-logo">Career Explora</div>
				</div>
				<div class="row hidden-sm hidden-md hidden-lg">
					<div class="login-div">
						<div>
							<form method="post">
								<div>
									<input type="text" placeholder="email, mobile or username"
										autofocus="autofocus" class="mobile-control" name="username" />
								</div>
								<div>
									<input type="password" placeholder="password"
										class="mobile-control" name="password" />
								</div>
								<div>
									<input type="button" value="login"
										class="mobile-control ca-btn-primary btn-sm login-button"
										style="margin-top: 3%; padding: 1%;" />
								</div>
							</form>
							<div style="text-align: center">
								<input type="button" value="Create New Account"
									class="mobile-new-account  btn-primary" id="new-account-button" />
							</div>
							<div style="text-align: center; font-size: 8pt; margin-top: 2%;">
								<a href="<c:url value='/password-recovery'/>"
									style="text-decoration: none; margin: 2%;">Forgot your
									password?</a> <a href="#" class="fblogin"
									style="text-decoration: none; margin: 2%;">Login with
									facebook</a>
							</div>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>


		<div class="row hidden-xs index-header">

			<div class="col-sm-4 col-md-5 col-md-offset-1">
				<span id="logo">Career Explora</span>
			</div>
			<div class="col-sm-6 col-md-4">
				<form id="auth-form" method="post">
					<table>
						<tr>
							<td class="login-label">Emai or Phone</td>
							<td class="login-label">Password</td>
							<td></td>
						</tr>
						<tr>
							<td><input type="text" id="username" name="username"
								class="login-input" autofocus="autofocus" /></td>
							<td><input type="password" id="password" name="password"
								class="login-input" /></td>
							<td><input type="button" value="Log in" class="ca-btn-primary" /></td>
						</tr>
						<tr>
							<td class="login-text"><input type="checkbox" /> Keep me
								logged in</td>
							<td class="login-text"><a
								href="<c:url value='/password-recovery'/>">Forgot your
									password?</a></td>
							<td></td>
					</table>

				</form>
			</div>
			<div class="col-sm-2 col-md-2">
				<div style="width: 100%;">
					<img alt="" src="/images/fb.png"
						style="height: 32px; width: 32px; margin: 0 auto"
						class="img img-responsive">
				</div>
				<div style="text-align: center;">
					<a href="#" class="login-text fblogin">Login with facebook</a>
				</div>

			</div>
		</div>
		<c:choose>
			<c:when test="${toSignUp}">
				<div class="row hidden-xs" id="index-body">
					<div class="col-sm-8 col-md-6 col-lg-4 col-centered">
						<div id="sign-up-header" class="row"
							style="text-align: left; color: black;">
							<span class="col-sm-12" style="font-size: 24pt">Sign Up</span>
						</div>
						<form method="post" id="register-user-form">
							<div class="row">
								<div class="form-group col-sm-6">
									<input type="text" class="form-control sign-up-input"
										id="first-name" placeholder="First Name" name="first-name" />
								</div>
								<div class="form-group col-sm-6">
									<input type="text" class="form-control sign-up-input"
										id="last-name" placeholder="Last Name" name="last-name" />
								</div>
							</div>
							<!-- <div class="row">
								<div class="form-group col-sm-6">
									<input type="radio" name="username-mode" value="phone"
										id="use-phone" /> Use mobile number
								</div>
								<div class="form-group col-sm-6">
									<input type="radio" name="username-mode" value="email"
										id="use-email" /> Use email
								</div>  -->
							</div>
							<div class="row">
								<div class="form-group col-sm-12">
									<input class="form-control sign-up-input" id="userid"
										name="userid" placeholder="E Mail address" style="width: 100%;" />
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-12">
									<input type="password" class="form-control sign-up-input"
										name="password1" id="pass1" placeholder="Password" />
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-12">
									<input type="password" class="form-control sign-up-input"
										name="password2" id="pass2" placeholder="Re-enter Password" />
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-4">
									<input type="radio" name="gender" value="female" id="female" />
									Female
								</div>
								<div class="form-group col-sm-4">
									<input type="radio" name="gender" value="male" id="male" />
									Male
								</div>
								<div class="form-group col-sm-4"></div>
							</div>
							<input type="hidden" name="using-username" value="false" />
							<div class="row">
								<div class="col-sm-10 form-group" id="terms-div">
									By clicking Sign Up, you agree to our <a href="#">terms</a> and
									you have read our <a href="#">data policy</a>
								</div>
								<div class="col-sm-2"></div>
							</div>
							<div class="row">
								<div class="col-sm-4 form-group">
									<input type="button" class="ca-btn-primary btn-lg" id="sign-up"
										value="Sign Up" />
								</div>
								<div class="col-sm-8" id="reg-error-div"></div>
							</div>

						</form>

					</div>
				</div>
			</c:when>

			<c:when test="${empty editingUsername}">
				<div class="row hidden-xs" id="index-body">
					<div class="hidden-sm col-md-1"></div>
					<div class="col-sm-6 col-md-5">
						<div class="row" id="slogan-div" style="margin-right: 5%;">
							<span>Career development and Social network tailored to
								work together.</span>
						</div>
						<div class="row" id="slider-div" style="margin-right: 5%;">
							<div class="carousel slide hidden-xs" id="myCarousel"
								data-ride='carousel'
								style="padding: 0; margin: 0 auto; width: 55%; background-color: orange">


								<div class="carousel-inner" role="listbox" style="margin: 0">
									<div class="item active" style="margin: 0">
										<img alt="" src="/images/cbt-ready.jpg" class="slider-img">
									</div>

									<div class="item">
										<img alt="" src="/images/talent-ready.jpg" class="slider-img">
									</div>

									<div class="item">
										<img alt="" src="/images/career-ready.jpg" class="slider-img">
									</div>

									<div class="item">
										<img alt="" src="/images/personal-ready.jpg"
											class="slider-img">
									</div>

									<div class="item">
										<img alt="" src="/images/skills-ready.jpg" class="slider-img">
									</div>

									<div class="item">
										<img alt="" src="/images/brain-ready.jpg" class="slider-img">
									</div>
									<div class="item">
										<img alt="" src="/images/values-ready.jpg" class="slider-img">
									</div>

									<div class="item">
										<img alt="" src="/images/forum-ready.jpg" class="slider-img">
									</div>

								</div>

								<a class="left carousel-control" href="#myCarousel"
									role="button" data-slide="prev"> <span
									class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
									<span class="sr-only">Previous</span>
								</a> <a class="right carousel-control" href="#myCarousel"
									role="button" data-slide="next"> <span
									class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
									<span class="sr-only">Next</span>
								</a>
							</div>

						</div>
						<div style="width: 100px; margin: 0 auto"><a href="/learn-more-page.docx">Learn more</a></div>
					</div>
					<div class="col-sm-6 col-md-4">
						<div id="sign-up-header" class="row"
							style="text-align: left; color: black;">
							<span class="col-sm-12" style="font-size: 24pt">Sign Up</span>
						</div>
						<form method="post" id="register-user-form">
							<div class="row">
								<div class="form-group col-sm-6">
									<input type="text" class="form-control sign-up-input"
										id="first-name" placeholder="First Name" name="first-name" />
								</div>
								<div class="form-group col-sm-6">
									<input type="text" class="form-control sign-up-input"
										id="last-name" placeholder="Last Name" name="last-name" />
								</div>
							</div>
							<div class="row">

								<!-- <div class="form-group col-sm-6">
									<input type="radio" name="username-mode" value="phone"
										id="use-phone" checked="checked" /> Use mobile number
								</div>
								<div class="form-group col-sm-6">
									<input type="radio" name="username-mode" value="email"
										id="use-email" /> Use email
								</div>  -->
							</div>
							<div class="row">
								<div class="form-group col-sm-12">
									<input class="form-control sign-up-input" id="userid"
										name="userid" placeholder="E Mail address" style="width: 100%;" />
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-12">
									<input type="password" class="form-control sign-up-input"
										name="password1" id="pass1" placeholder="Password" />
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-12">
									<input type="password" class="form-control sign-up-input"
										name="password2" id="pass2" placeholder="Re-enter Password" />
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-4">
									<input type="radio" name="gender" value="female" id="female" />
									Female
								</div>
								<div class="form-group col-sm-4">
									<input type="radio" name="gender" value="male" id="male" />
									Male
								</div>
								<div class="form-group col-sm-4"></div>
							</div>
							<input type="hidden" name="using-username" value="false" />
							<div class="row">
								<div class="col-sm-10 form-group" id="terms-div">
									By clicking Sign Up, you agree to our <a href="#">terms</a> and
									you have read our <a href="#">data policy</a>
								</div>
								<div class="col-sm-2"></div>
							</div>
							<div class="row">
								<div class="col-sm-4 form-group">
									<input type="button" class="ca-btn-primary btn-lg" id="sign-up"
										value="Sign Up" />
								</div>
								<div class="col-sm-8" id="reg-error-div"></div>
							</div>

						</form>
					</div>
					<div class="hidden-sm col-md-2"></div>
				</div>
			</c:when>
			<c:when test="${editingUsername}">
				<div class="row hidden-xs" id="index-body">
					<div class="col-sm-8 col-md-6 col-lg-4 col-centered">
						<div id="sign-up-header" class="row"
							style="text-align: left; color: black;">
							<span class="col-sm-12" style="font-size: 24pt">Sign Up</span>
						</div>
						<form method="post" id="register-user-form">
							<div class="row">
								<div class="form-group col-sm-6">
									<input type="text" class="form-control sign-up-input"
										id="first-name" placeholder="First Name" name="first-name"
										value="${registrationForm.firstName}" />
								</div>
								<div class="form-group col-sm-6">
									<input type="text" class="form-control sign-up-input"
										id="last-name" placeholder="Last Name" name="last-name"
										value="${registrationForm.lastName}" />
								</div>
							</div>
							<div class="row">
								<c:choose>
									<c:when test="${usingMobile}">
									<!-- 	<div class="form-group col-sm-6">
											<input type="radio" name="username-mode" value="phone"
												id="use-phone" checked="checked" /> Use mobile number
										</div>
										<div class="form-group col-sm-6">
											<input type="radio" name="username-mode" value="email"
												id="use-email" /> Use email
										</div>  -->
									</c:when>
									<c:when test="${usingEmail}">
									<!-- 	<div class="form-group col-sm-6">
											<input type="radio" name="username-mode" value="phone"
												id="use-phone" /> Use mobile number
										</div>
										<div class="form-group col-sm-6">
											<input type="radio" name="username-mode" value="email"
												id="use-email" checked="checked" /> Use email
										</div> -->
									</c:when>
								</c:choose>

							</div>
							<div class="row">
								<div class="form-group col-sm-12">
									<input class="form-control sign-up-input" id="userid"
										name="userid" placeholder="E Mail address" style="width: 100%;"
										value="${registrationForm.username}" />
								</div>
							</div>
							<script type="text/javascript">
								var x = document.getElementById("userid")
										.select();
							</script>
							<div class="row">
								<div class="form-group col-sm-12">
									<input type="password" class="form-control sign-up-input"
										name="password1" id="pass1" placeholder="Password" />
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-12">
									<input type="password" class="form-control sign-up-input"
										name="password2" id="pass2" placeholder="Re-enter Password" />
								</div>
							</div>
							<div class="row">
								<div class="form-group col-sm-4">
									<input type="radio" name="gender" value="female" id="female" />
									Female
								</div>
								<div class="form-group col-sm-4">
									<input type="radio" name="gender" value="male" id="male" />
									Male
								</div>
								<div class="form-group col-sm-4"></div>
							</div>
							<input type="hidden" name="using-username" value="false" />
							<div class="row">
								<div class="col-sm-10 form-group" id="terms-div">
									By clicking Sign Up, you agree to our <a href="#">terms</a> and
									you have read our <a href="#">data policy</a>
								</div>
								<div class="col-sm-2"></div>
							</div>
							<div class="row">
								<div class="col-sm-4 form-group">
									<input type="button" class="ca-btn-primary btn-lg" id="sign-up"
										value="Sign Up" />
								</div>
								<div class="col-sm-8" id="reg-error-div"></div>
							</div>

						</form>

					</div>
				</div>
			</c:when>

		</c:choose>

	</div>

	<form action="/azure/fbook" method="post" id="login-form">
		<input id="name" name="name" type="hidden" /> <input id="picture"
			name="picture" type="hidden" /> <input id="cover" name="cover"
			type="hidden" /> <input id="access" name="access" type="hidden" />
	</form>

	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/validators.js"></script>
	<script type="text/javascript" src="/js/index.js"></script>
	<script type="text/javascript" src="/js/waitMe.js"></script>


</body>
</html>