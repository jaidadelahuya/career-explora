<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Career Explora</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">
</head>
<body class="home-body">
	
	<div class="container-fluid" id="main">
		<div class="row xs-hidden sm-hidden front-page-header" >

			<div class="col-md-5 col-md-offset-1">
				<span class="front-page-logo">Career Explora</span>
			</div>
			<div class="col-sm-8 col-md-6" style="padding-top: 10px;">
				<form id="auth-form" method="post">
					<table>
						<tr>
							<td class="login-label">Email or Phone</td>
							<td class="login-label">Password</td>
							<td></td>
						</tr>
						<tr>
							<td><input type="text" id="username" name="username"
								class="login-input" autofocus="autofocus" /></td>
							<td><input type="password" id="password" name="password"
								class="login-input" /></td>
							<td><input type="button" value="Log in"
								class="ca-btn-primary login-button" /></td>
						</tr>
						<tr>
							<td class="login-text"><input type="checkbox" /> Keep me
								logged in</td>
							<td class="login-text"><a style="color: white; text-decoration: none;"
								href="<c:url value='/password-recovery'/>">Forgot your
									password?</a></td>
							<td></td>
					</table>

				</form>
			</div>
			
		</div>

		<div class="row" id="index-body">
			<div class="col-md-1"></div>
			<div class="col-md-5">
				<div class="row" id="slogan-div" style="margin-right: 5%;">
					<span style="color: #983b59">Career development and Social network tailored to work
						together.</span>
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
								<img alt="" src="/images/personal-ready.jpg" class="slider-img">
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

						<a class="left carousel-control" href="#myCarousel" role="button"
							data-slide="prev"> <span
							class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
							<span class="sr-only">Previous</span>
						</a> <a class="right carousel-control" href="#myCarousel"
							role="button" data-slide="next"> <span
							class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
							<span class="sr-only">Next</span>
						</a>
					</div>

				</div>
			</div>
			<div class="col-md-4">
				<div id="sign-up-header" class="row"
					style="text-align: left; color: #983b59">
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
						<div class="form-group col-sm-12">
							<input class="form-control sign-up-input" id="userid"
								name="userid" placeholder="Username" style="width: 100%;" />
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
						<div class="form-group col-sm-12">
							<input class="form-control sign-up-input" id="password-recovery"
								name="password-recovery"
								placeholder="Password recovery mobile number"
								style="width: 100%;" />
						</div>
					</div>
					<div class="row">
						<div class="form-group col-sm-4">
							<input type="radio" name="gender" value="female" id="female" />
							Female
						</div>
						<div class="form-group col-sm-4">
							<input type="radio" name="gender" value="male" id="male" /> Male
						</div>
						<div class="form-group col-sm-4"></div>
					</div>
					<input type="hidden" name="using-username" value="true" id="using-username"/>
					<div class="row">
						<div class="col-sm-10 form-group" id="terms-div">
							By clicking Sign Up, you agree to our <a href="#">terms</a> and
							you have read our <a href="#">data policy</a>
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="row">
						<div class="col-sm-4 form-group">
							<input type="button" class="btn btn-success" id="sign-up"
								value="Sign Up" />
						</div>
						<div class="col-sm-8" id="reg-error-div"></div>
					</div>

				</form>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>

	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/validators.js"></script>
	<script type="text/javascript" src="/js/index.js"></script>
	<script type="text/javascript" src="/js/waitMe.js"></script>


</body>
</html>