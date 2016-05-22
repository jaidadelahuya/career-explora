<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isErrorPage='true'%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Change Password Recovery Option</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" type="text/css" href="/style/jquery-ui.css">
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">
</head>
<body class="main-body">
	<div class="container-fluid" id="main">
		<div class="row xs-hidden sm-hidden" id="index-header">
			<div class="col-md-5 col-md-offset-1">
				<span id="logo">Career Explora</span> <a
					href="<c:url value='/tosignup'/>" class="btn btn-medium btn-info" v
					style="margin-bottom: 2%;">Sign up</a>
			</div>
			<div class="col-md-6"></div>
		</div>

		<div class="row">
			<div class="col-sm-8 col-lg-6 col-centered" style="margin-top: 5%;">

				<div id="password-recovery-dialog">
					<h4
						style="margin-top: 4%; color: rgb(144, 144, 144); font-family: arial">Select
						a different email address or mobile number</h4>
					<hr style="margin: 0; margin-bottom: 4%;" />

					<div class="alert alert-info">
						<h4 style="color: black;">You have <c:out value="${fn:length(passwordRecoveryList)}"></c:out> verified password recovery
							option(s)</h4>
						<p>A confirmation code will be sent to either the email
							address or mobile number you choose</p>
						<p>Select one and click the send confirmation code button.</p>

					</div>

					<form id="password-recovery-form" method="post">
						<div class="row">
							<div class="form-group col-md-6">
								<c:forEach var="passwordRecovery" items="${passwordRecoveryList}">
									<div class="col-sm-12">
										<input type="radio" name="username" class="opt"
											value="${passwordRecovery.key.name}" /> <span 
											class="test-board-option"><c:out value="${passwordRecovery.key.name}"></c:out></span>
									</div>
								</c:forEach>
							</div>
							<div class="form-group col-md-6"></div>
						</div>
						<hr style="margin: 0; margin-bottom: 3%; margin-top: 1%;" />
						<div class="row">
							<div class="col-md-8">
								<div class="form-group" id="reg-error-div"></div>
							</div>
							<div class="col-md-4 form-group">
								<input type="button" class="btn btn-info btn-sm pull-left"
									id="send-code" value="Send confirmation code" />
							</div>
						</div>

					</form>
				</div>

			</div>
		</div>
	</div>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/password-recovery.js"></script>
</body>
</html>