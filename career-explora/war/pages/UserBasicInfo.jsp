<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome <c:out value="${azureUser.firstName}" /> <c:out
		value="${azureUser.lastName}" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/style/dashboard.css">
<link rel="stylesheet" href="/style/materialize.min.css">
<link rel="stylesheet" href="/style/materialize-tags.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

</head>
<body class="main-body">
	<div class="row front-page-header">

		<div class="col s11 offset-s1">
			<span class="front-page-logo-2">Career Explora</span>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col s12">
				<ul class="tabs">
					<li class="col s3 tab"><a href="#about">About Yourself</a></li>
					<li class="col s3 tab"><a href="#profile">Profile Picture</a></li>
					<li class="col s3 tab"><a href="#cover">Cover Photo</a></li>
				</ul>
			</div>
			<div id="about" class="col s12">

				<p class="flow-text" style="color: #983b59">Tell us a little
					about yourself</p>
				<div class="row">
					<form action="<c:url value='/azure/interest/add' />"
						id="major-interest-form" class="col s12">
						<div class="row">
							<div class="col s12">
								<div id="msg-div"></div>
							</div>
						</div>
						<div class="row">
							<div class="input-field col s12">
								<input id="school" type="text" class="validate" name="school">
								<label for="school">School</label>
							</div>
						</div>
						<div class="row">
							<div class="input-field col s6">
								<select name="class">
									<optgroup label="Primary">
										<option>Basic 1</option>
										<option>Basic 2</option>
										<option>Basic 3</option>
										<option>Basic 4</option>
										<option>Basic 5</option>
										<option>Basic 6</option>
									</optgroup>
									<optgroup label="Junior Secondary">
										<option>Basic 7</option>
										<option>Basic 8</option>
										<option>Basic 9</option>
									</optgroup>
									<optgroup label="Senoir Secondary">
										<option>SS 1</option>
										<option>SS 2</option>
										<option>SS 3</option>
									</optgroup>
								</select> <label>Class</label>
							</div>
							<div class="input-field col s6">
								<select multiple name="interest">
									<option value="" disabled selected>Choose your area(s)
										of interest</option>
									<option value="1">Arts</option>
									<option value="2">Commercial</option>
									<option value="3">Science</option>
								</select> <label>Area of interest</label>
							</div>
							<div class="input-field col s6">
								<button type="button" class="ca-btn-primary ca-btn-lg"
									id="submit-mj">Continue</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div id="profile" class="col s12">Test 2</div>
			<div id="cover" class="col s12">Test 3</div>

		</div>
	</div>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/materialize.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('select').material_select();

			$("#submit-mj").click(function(e) {
				e.preventDefault();
				var $form = $("#major-interest-form");
				var x = $form.prop("action");

				$.ajax({
					url : x,
					method : "POST",
					data : $form.serialize(),
					dataType : "json",
					success : function(data) {
						window.location.assign(data);
					},
					error : function(xhr, statusText, str) {
						var msgD = $("#msg-div");
						msgD.text(xhr.statusText);
						msgD.addClass("ca-alert ca-alert-danger");
						console.log(xhr.statusText);

					}
				});
			});

		});
	</script>
</body>
</html>