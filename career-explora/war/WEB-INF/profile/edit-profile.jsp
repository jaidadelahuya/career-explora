<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title><c:out value="${userProfile.firstName}" /> <c:out
		value="${userProfile.lastName}" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/style/materialize.min.css">
<link rel="stylesheet" href="/style/materialize-tags.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet" href="/style/jquery.webui-popover.css">
<link rel="stylesheet" href="/style/dashboard.css">
<link rel="stylesheet" href="/style/main.css">
<link rel="stylesheet" href="/style/media-queries.css">
<style type="text/css">
	.card-title {
		color: #3b5998;
		font-weight: bold !important;
	}
</style>
</head>
<body>
<body style="background-color: #f1f3f7">

	<%@ include file="/WEB-INF/main-nav.html"%>
	<div class="center-div">
		<div id="home-center-div" class="center-main-content">
			<div class="row">
				<div class="col s12">
					<div class="card">
						<div class="card-content">

							<div class="row">

								<form method="post"
									action="<c:url value="/azure/profile/update" />"
									class="col s12">
									<c:if test="${not empty profileError}">
										<div class="err-msg">
											<c:out value="${profileError}" />
										</div>
									</c:if>
									<span class="card-title">Personal Data</span>
									<div class="row">
										<div class="input-field col s12 m6">
											<input value="${userProfile.firstName}" id="first_name"
												type="text" name="first-name" required="required"
												class="validate"> <label for="first_name" data-error="Enter your first name">First
												Name</label>
										</div>
										<div class="input-field col s12 m6">
											<input id="last_name" type="text" class="validate"
												name="last-name" required="required"
												value="${userProfile.lastName}"> <label data-error="Enter your first name"
												for="last_name">Last Name</label>
										</div>
									</div>
									<div class="row">
										<div class="input-field col s12 m6">
											<input value="" id="birth_date" type="date" name="birth-date"
												class="validate datepicker"> <label for="birth_date">Birth
												Date</label>
										</div>
										<div class="input-field col s12 m6">
											<input class="with-gap" name="gender" type="radio"
												id="female" value="female"
												<c:if test='${userProfile.gender eq "female" }'>checked</c:if> />
											<label for="female">Female</label> <input class="with-gap"
												name="gender" type="radio" id="male" value="male"
												<c:if test='${userProfile.gender eq "male" }'>checked</c:if> />
											<label for="male">Male</label>
										</div>
									</div>
									<span class="card-title">Education</span>
									<div class="row">
										<div class="input-field col s12 m6">
											<%@ include file="/pages/partials/class.html"%>
										</div>
										<div class="input-field col s12 m6">
											<%@ include file="/pages/partials/interest.html"%>
										</div>
									</div>
									<div class="row">
										<div class="input-field col s12">
											<input value="${userProfile.school}" id="school" type="text"
												name="school" required="required" class="validate">
											<label for="school">School</label>
										</div>
									</div>
									<span class="card-title">Current Location</span>
									<div class="row">
										<div class="input-field col s12 m6">
											<%@ include file="/pages/partials/states.html"%>
										</div>
										<div class="input-field col s12 m6">
											<input id="country" type="text" name="country"
												value="${userProfile.country}"> <label for="country">Country</label>
										</div>
									</div>

									<span class="card-title">Other Information</span>
									<div class="row">
										<div class="input-field col s12">
											<input value="${userProfile.hobbies}" id="hobbies"
												type="text" name="hobbies"> <label for="hobbies">Hobbies</label>
										</div>
									</div>


									<div class="row">
										<div class="input-field col s12">
											<textarea id="textarea1" class="materialize-textarea"
												name="summary" placeholder="Write about you">
												${userProfile.about}
												</textarea>
											<label for="textarea1">Profile Summary</label>
										</div>
									</div>

									<div class="fixed-action-btn"
										style="bottom: 45px; right: 24px;">
										<button type="submit" class="btn-floating btn-large green">
											<i class="large material-icons">save</i>
										</button>
									</div>
								</form>
							</div>
						</div>
						<div class="card-action">
							<a href="/azure/careerplus#clusters">Career Clusters</a><a
								href="/azure/careerplus#mit">Multiple Intelligence Test</a><a
								href="/azure/careerplus#talent">Talent Test</a><a
								href="/azure/careerplus#skills">Skill Builder</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/materialize.min.js"></script>
	<script src="/js/jquery.webui-popover.js"></script>
	<script type="text/javascript" src="/js/main.js"></script>

</body>
</body>

</html>