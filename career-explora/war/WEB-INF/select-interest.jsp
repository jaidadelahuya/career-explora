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
<body class="main-body cover-background"">
	<div class="row front-page-header">

		<div class="col s11 offset-s1">
			<span class="front-page-logo-2">Career Explora</span>
		</div>
	</div>
	<div class="container">
		<h2
			style="text-align: center; margin-bottom: 2%; margin-left: 1%; color: white; font-weight: bold; text-shadow: 1px 1px black; font-size: 28pt"
			class="mobile-font-header">Let's help you get started</h2>
		<div class="card-panel">
			<div class="row">
				<div class="col s12">
					<ul class="tabs">
						<li class="col s3 tab"><a href="#about">About Yourself</a></li>
						<li id="profile-picture-tab" class="col s3 tab disabled"><a
							href="#profile">Profile Picture</a></li>
						<li id="cover-picture-tab" class="col s3 tab disabled"><a
							href="#cover">Cover Photo</a></li>
					</ul>
				</div>
				<div id="about" class="col s12"
					style="padding: 4%; padding-top: 2%;">

					<p class="flow-text"
						style="color: #983b59; margin-bottom: 0px; margin-top: 15px">Tell
						us a little about yourself</p>
					<div class="row">
						<form action="<c:url value='/azure/interest/add' />"
							id="major-interest-form" class="col s12">
							<div class="row">
								<div class="col s12">
									<div class="msg-div"></div>
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
											<option>SSS 1</option>
											<option>SSS 2</option>
											<option>SSS 3</option>
										</optgroup>
									</select> <label>Class</label>
								</div>
								<div class="input-field col s6">
									<select multiple name="interest">
										<option value="" disabled selected>Choose your
											area(s) of interest</option>
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
				<div id="profile" class="col s12"
					style="padding-left: 4%; padding-right: 4%;">
					<p class="flow-text" style="color: #983b59">Upload a profile
						picture</p>
					<div class="row">
						<form class="upload-form" id="profile-picture-form">
							<div class="row">
								<div class="col s12">
									<div class="msg-div"></div>
								</div>
							</div>
							<div class="col s6">

								<div class="file-field input-field">
									<div class="btn">
										<span>File</span> <input name="image" type="file">
									</div>
									<div class="file-path-wrapper">
										<input class="file-path validate" type="text">
									</div>
								</div>
								<input type="hidden" name="type" class="h-input" value="profile">

							</div>
							<div class="col s6" style="padding-top: 15px">
								<button type="button"
									class="ca-btn-primary ca-btn-lg upload-button">Upload
									and Continue</button>
							</div>
						</form>

					</div>
					<div class="row">
						<div class="col s12">
							<p class="flow-text"
								style="background-color: #983b59; color: white; padding: 10px 15px; line-height: 1; font-size: 120%;">Don't
								have a profile picture yet? Select an Avatar</p>
							<form class="default-img-form"
								action="<c:url value='/azure/interest/profile/picture/default/add'/>">
								<div class="row">
									<div class="col s12">
										<div class="msg-div"></div>
									</div>
								</div>
								<c:set var="gender" value="male" />
								<c:choose>
									<c:when test="${azureUser.gender eq gender}">
										<div class="row" style="margin: 2%; margin-bottom: 20%">
											<div class="col s3">
												<input name="avatar" type="radio" id="test1"
													value="/images/male-avatar/av1.jpg" /><label for="test1"><img
													class="materialboxed responsive-img circle" alt=""
													src="/images/male-avatar/av1.jpg"></label>
											</div>
											<div class="col s3">
												<input name="avatar" type="radio" id="test2"
													value="/images/male-avatar/av4.jpg" /><label for="test2"><img
													class="materialboxed responsive-img circle" alt=""
													src="/images/male-avatar/av4.jpg"></label>
											</div>
											<div class="col s3">
												<input name="avatar" type="radio" id="test3"
													value="/images/male-avatar/av5.jpg" /><label for="test3"><img
													class="materialboxed responsive-img circle" alt=""
													src="/images/male-avatar/av5.jpg"></label>
											</div>
											<div class="col s3">
												<input name="avatar" type="radio" id="test4"
													value="/images/male-avatar/av7.jpg" /><label for="test4"><img
													class="materialboxed responsive-img circle" alt=""
													src="/images/male-avatar/av7.jpg"></label>
											</div>

										</div>
									</c:when>
									<c:otherwise>
										<div class="row" style="margin: 2%; margin-bottom: 20%;">
											<div class="col s3">
												<input name="avatar" type="radio" id="test5"
													value="/images/female-avatar/av2.jpg" /><label for="test5"><img
													class="materialboxed responsive-img circle" alt=""
													src="/images/female-avatar/av2.jpg"></label>
											</div>
											<div class="col s3">
												<input name="avatar" type="radio" id="test6"
													value="/images/female-avatar/av3.jpg" /><label for="test6"><img
													class="materialboxed responsive-img circle" alt=""
													src="/images/female-avatar/av3.jpg"></label>
											</div>
											<div class="col s3">
												<input name="avatar" type="radio" id="test7"
													value="/images/female-avatar/av6.jpg" /><label for="test7"><img
													class="materialboxed responsive-img circle" alt=""
													src="/images/female-avatar/av6.jpg"></label>
											</div>
											<div class="col s3">
												<input name="avatar" type="radio" id="test8"
													value="/images/female-avatar/av8.jpg" /><label for="test8"><img
													class="materialboxed responsive-img circle" alt=""
													src="/images/female-avatar/av8.jpg"></label>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
								<input type="hidden" name="type" class="h-input" value="profile">
								<div class="row">
									<div class="col s12" style="text-align: center;">
										<button type="button"
											class="ca-btn-primary ca-btn-lg default-img-btn">Continue</button>
									</div>
								</div>
							</form>



						</div>
					</div>
				</div>
				<div id="cover" class="col s12"
					style="padding-left: 4%; padding-right: 4%;">
					<p class="flow-text" style="color: #983b59">Upload a cover
						photo</p>
					<div class="row">
						<form class="upload-form" id="cover-picture-form">
							<div class="row">
								<div class="col s12">
									<div class="msg-div"></div>
								</div>
							</div>
							<div class="col s6">

								<div class="file-field input-field">
									<div class="btn">
										<span>File</span> <input name="image" type="file">
									</div>
									<div class="file-path-wrapper">
										<input class="file-path validate" type="text">
									</div>
								</div>
								<input type="hidden" name="type" class="h-input" value="cover">

							</div>
							<div class="col s6" style="padding-top: 15px">
								<button type="button"
									class="ca-btn-primary ca-btn-lg upload-button">Upload
									and Continue</button>
							</div>
						</form>
					</div>
					<div class="row">
						<div class="col s12">
							<p class="flow-text"
								style="background-color: #983b59; color: white; padding: 10px 15px; line-height: 1; font-size: 120%;">Don't
								have a cover photo yet? Choose from the cool ones below</p>
							<form class="default-img-form"
								action="<c:url value='/azure/interest/profile/picture/default/add'/>">
								<div class="row" style="margin: 2%; margin-bottom: 10%">
									<div class="col s3">
										<input name="avatar" type="radio" id="bck1"
											value="/images/cover-photo/background1.jpg" /><label
											for="bck1"><img class="materialboxed responsive-img"
											alt="" src="/images/cover-photo/background1.jpg"></label>
									</div>
									<div class="col s3">
										<input name="avatar" type="radio" id="bck2"
											value="/images/cover-photo/background2.jpg" /><label
											for="bck2"><img class="materialboxed responsive-img"
											alt="" src="/images/cover-photo/background2.jpg"></label>
									</div>
									<div class="col s3">
										<input name="avatar" type="radio" id="bck3"
											value="/images/cover-photo/background3.jpg" /><label
											for="bck3"><img class="materialboxed responsive-img"
											alt="" src="/images/cover-photo/background3.jpg"></label>
									</div>
									<div class="col s3">
										<input name="avatar" type="radio" id="bck4"
											value="/images/cover-photo/background4.jpg" /><label
											for="bck4"><img class="materialboxed responsive-img"
											alt="" src="/images/cover-photo/background4.jpg"></label>
									</div>
									<input type="hidden" value="cover" name="type" class="h-input">

								</div>


								<div class="row">
									<div class="col s12" style="text-align: center;">
										<button type="button"
											class="ca-btn-primary ca-btn-lg default-img-btn">Continue</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/materialize.min.js"></script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('select').material_select();

							$(".default-img-btn").click(function(e) {
								e.preventDefault();
								$(this).closest("form").submit();
							});

							$(".default-img-form")
									.on(
											"submit",
											function(e) {
												e.preventDefault();
												var form = $(this);
												var x = form.prop("action");
												var y = form.find(".h-input")
														.val();
												var msgD = $(this).find(
														".msg-div");
												$
														.ajax({
															url : x,
															data : form
																	.serialize(),
															method : "POST",
															dataType : "json",
															success : function(
																	data) {
																console
																		.log(y);
																if (y == "profile") {

																	$(
																			"#cover-picture-tab")
																			.removeClass(
																					"disabled");
																	$('ul.tabs')
																			.tabs(
																					'select_tab',
																					'cover');
																} else if (y == "cover") {
																	window.location
																			.assign(data);
																}
															},
															error : function(
																	xhr) {
																msgD
																		.text(xhr.statusText);
																msgD
																		.addClass("ca-alert ca-alert-danger");
															}
														});
											});

							$("#submit-mj")
									.click(
											function(e) {
												e.preventDefault();
												var $form = $("#major-interest-form");
												var x = $form.prop("action");

												$
														.ajax({
															url : x,
															method : "POST",
															data : $form
																	.serialize(),
															dataType : "json",
															success : function(
																	data) {
																$(
																		"#profile-picture-form")
																		.prop(
																				'action',
																				data);
																$(
																		"#profile-picture-tab")
																		.removeClass(
																				"disabled");
																$('ul.tabs')
																		.tabs(
																				'select_tab',
																				'profile');

															},
															error : function(
																	xhr,
																	statusText,
																	str) {
																var msgD = $("#msg-div");
																msgD
																		.text(xhr.statusText);
																msgD
																		.addClass("ca-alert ca-alert-danger");

															},
														});
											});

							$(".upload-button").click(function(e) {
								e.preventDefault();
								$(this).closest("form").submit();
							});

							$(".upload-form")
									.on(
											"submit",
											function(e) {
												e.preventDefault();
												var x = $(this).prop("action");
												var y = $(this)
														.find(".h-input").val();
												var butt = $(this).find(
														".upload-button");
												var msgD = $(this).find(
														".msg-div");
												$
														.ajax({
															url : x,
															type : 'POST',
															data : new FormData(
																	this),
															processData : false,
															contentType : false,
															success : function(
																	data) {
																if (y == "profile") {
																	console
																			.log($(
																					"#cover-picture-form")
																					.prop(
																							'action'));

																	$(
																			"#cover-picture-form")
																			.prop(
																					'action',
																					data);

																	$(
																			"#cover-picture-tab")
																			.removeClass(
																					"disabled");
																	$('ul.tabs')
																			.tabs(
																					'select_tab',
																					'cover');
																	butt
																			.prop(
																					"disabled",
																					true);
																} else if (y == "cover") {
																	window.location
																			.assign(data);
																}
															},
															error : function(
																	xhr,
																	status,
																	errorThrown) {
																msgD
																		.text(xhr.statusText);
																msgD
																		.addClass("ca-alert ca-alert-danger");
															}
														});

											});

						});
	</script>
</body>
</html>