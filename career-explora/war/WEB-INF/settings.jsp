<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Career Explora Settings</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" type="text/css" href="/style/jquery-ui.css">
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" type="text/css"
	href="/style/jquery.webui-popover.min.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">
<link rel="stylesheet" type="text/css" href="/style/prism.css">
</head>
<body style="padding-top: 50px" id="main-body">
	<div id="fb-root"></div>
	<script>
		window.fbAsyncInit = function() {
			FB.init({
				appId : '876754309045840',//876754309045840 907361745985096
				cookie : true,
				xfbml : true,
				version : 'v2.2'
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


	<%@ include file="/WEB-INF/nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8" id="main"
				style="padding-right: 0px; z-index: 4">
				<%@ include file="/WEB-INF/cover.jsp"%>
				<%@ include file="/WEB-INF/subnav.jsp"%>



				<div id="sub-main" class="row">
					<%@ include file="/pages/partials/module.html"%>
					<div class="col-xs-12 col-sm-5 mobile-view-panel"
						style="padding-right: 0;">
						<c:if test="${userSettingsModel.hasPassword}">

							<div class="panel panel-default mobile-view-div">
								<div class="panel-heading" style="background-color: orange;">
									<span class="panel-title pan-title-font">Change password</span>
								</div>
								<ul class="list-group list-group-flush"
									id="change-password-list">
									<li class="list-group-item">
										<p style="font-size: 8pt; font-weight: bold"
											id="reset-password-msg-div">Your password should be
											between 7-21 characters long and should contain alphabets and
											at least a digit and a special character.</p>
										<hr />
										<form id="change-password-form">
											<div class="form-group">
												<input type="password" id="old-password" name="old-password"
													placeholder="Enter old password">
											</div>
											<div class="form-group">
												<input type="password" id="password1" name="password1"
													placeholder="Enter new password">
											</div>
											<div class="form-group">
												<input type="password" id="password2" name="password2"
													placeholder="Re-enter new password">
											</div>
											<input type="hidden" name="with-old-password" value="true" />
										</form>
									</li>
									<li class="list-group-item" style="margin-bottom: 5%;">
										<div class="form-group">
											<input id="password-change-button"
												class="btn btn-sm orange-button pull-right form-control"
												value="Change Password" />
										</div>
									</li>
								</ul>
							</div>

						</c:if>
						<c:if test="${userSettingsModel.hasPassword}">

							<div class="panel panel-default mobile-view-div">
								<div class="panel-heading" style="background-color: #f0ad4e;">
									<span class="panel-title pan-title-font">Password
										Recovery</span>
								</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">
										<div style="font-size: 8pt; font-weight: bold;"
											id="password-recovery-msg-div">
											<p>Mobile numbers and email addresses not verified will
												not be available for password recovery.</p>
											<p>The mobile number or email address you use to log in
												cannot be changed.</p>
										</div>
										<hr />
										<h4>Mobile Numbers</h4> <c:choose>
											<c:when test="${userSettingsModel.hasMobile}">
												<table style="font-size: 9pt" class="table table-responsive">
													<tr>
														<c:choose>
															<c:when test="${userSettingsModel.mobile.username}">
																<td style="margin: 2%; border: none"><span><c:out
																			value="${userSettingsModel.mobile.key.name}" /></span></td>
															</c:when>
															<c:otherwise>
																<td style="margin: 2%; border: none"><span
																	class="tool-tip to-pointer editable-label"
																	data-toggle="tooltip"
																	title="Click to change and press enter to save"
																	data-placement="bottom"><c:out
																			value="${userSettingsModel.mobile.key.name}" /></span></td>
															</c:otherwise>
														</c:choose>

														<td style="margin: 2%; border: none"><c:choose>
																<c:when test="${userSettingsModel.mobile.verified}">
																	<span class="text-succes" style="font-weight: bold;">
																		<c:out value="Verified" />
																	</span>
																</c:when>
																<c:otherwise>
																	<span class="text-danger tool-tip to-pointer sp-verify"
																		style="font-weight: bold;" data-toggle="tooltip"
																		title="Click to verify" data-placement="bottom"><c:out
																			value="Not verified" /></span>
																</c:otherwise>
															</c:choose></td>
													</tr>
													<c:choose>
														<c:when test="${userSettingsModel.hasAltMobile}">
															<tr>
																<td style="border: none"><span class="to-pointer"
																	data-toggle="tooltip" title="Click to change"
																	data-placement="bottom"><c:out
																			value="${userSettingsModel.altMobile.key.name}" /></span></td>
																<td style="border: none"><c:choose>
																		<c:when test="${userSettingsModel.altMobile.verified}">
																			<c:out value="Verified" />
																		</c:when>
																		<c:otherwise>
																			<span class="to-pointer" data-toggle="tooltip"
																				title="Click to verify" data-placement="bottom"><c:out
																					value="Not verified" /></span>
																		</c:otherwise>
																	</c:choose></td>

															</tr>
														</c:when>
														<c:otherwise>
															<td style="border: none" colspan="2"><input
																type="button"
																class="btn btn-warning pull-left add-alt-pswr-mobile"
																value="Add alternative mobile number" /></td>
														</c:otherwise>
													</c:choose>
												</table>
											</c:when>
											<c:otherwise>
												<table style="font-size: 9pt" class="form-group">
													<tr>
														<td><div>
																You have not added a mobile number. </span><br /> <input
																	type="button" value="Add mobile number"
																	class="btn btn-warning add-alt-pswr-mobile" /></td>
													</tr>
												</table>
											</c:otherwise>
										</c:choose> <c:choose>
											<c:when test="${userSettingsModel.hasEmail}">
												<hr />
												<h4>Email Addresses</h4>
												<table
													style="font-size: 9pt; border: none; margin-bottom: 0"
													class="table table-responsive">
													<tr>
														<td style="border: none"><c:out
																value="${userSettingsModel.email.key.name}" /></td>
														<td style="border: none"><c:choose>
																<c:when test="${userSettingsModel.email.verified}">
																	<c:out value="Verified" />
																</c:when>
																<c:otherwise>
																	<c:out value="Not verified" />
																</c:otherwise>
															</c:choose></td>

													</tr>
													<c:choose>
														<c:when test="${userSettingsModel.hasAltEmail}">
															<tr>
																<td style="border: none"><c:out
																		value="${userSettingsModel.altEmail.key.name}" /></td>
																<td style="border: none"><c:choose>
																		<c:when test="${userSettingsModel.altEmail.verified}">
																			<c:out value="Verified" />
																		</c:when>
																		<c:otherwise>
																			<c:out value="Not verified" />
																		</c:otherwise>
																	</c:choose></td>

															</tr>
														</c:when>
														<c:otherwise>
															<!-- 	<tr>
																<td colspan="2" style="border: none;"><input
																	type="button" class="btn btn-warning"
																	value="Add alternative email address" /></td>
															</tr>  -->
														</c:otherwise>
													</c:choose>
												</table>
											</c:when>
											<c:otherwise>
												<!-- <table
													style="font-size: 9pt; border: none; margin-bottom: 0"
													class="table table-responsive">
													<tr id="default-msg1">
														<td style="border: none">You have not added an email address.</td>
													</tr>
													<tr id="default-button">
														<td style="border: none"><input type="button" value="Add email address"
															class="btn btn-warning add-id" /></td>
													</tr>

												</table> -->
											</c:otherwise>
										</c:choose>

									</li>

								</ul>
							</div>

						</c:if>
					</div>

					<div class="col-xs-12 col-sm-5 mobile-view-panel"
						style="padding-right: 0;">

						<c:if test="${!userSettingsModel.hasFacebookAccount}">
							<div class="panel panel-default mobile-view-div">
								<div class="panel-heading" style="background-color: #337ab7">
									<span class="panel-title pan-title-font">Add Facebook
										account</span>
								</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item " id="add-facebook-msg-div"
										style="font-size: 8pt;">
										<p style="font-weight: bold;">Adding a facebook account
											lets you log into Career Explora with your facebook account.</p>
										<p style="font-weight: bold;">You can choose to allow your
											your names, profile and cover pictures to be always updated
											from facebook.</p>
										<hr />
										<div>
											<input type="checkbox" value="true"
												name="update-from-facebook" style="margin-right: 2%;" />Always
											update names,pictures from facebook. <input type="button"
												value="Add facebook account"
												class="btn btn-primary form-control" style="margin-top: 4%;" />
										</div>
									</li>
								</ul>
							</div>
						</c:if>

						<c:if test="${!userSettingsModel.hasPassword}">
							<div class="panel panel-default mobile-view-div">
								<div class="panel-heading" style="background-color: #5cb85c;">
									<span class="panel-title pan-title-font">Get a Career
										Explora ID</span>
								</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">
										<div id="username-msg-div"
											style="font-size: 8pt; font-weight: bold;">
											<p>A Career Explora Username lets you log directly into
												Career Explora without using facebook.</p>
											<p>When you get a Career Explora Username, you can log in
												either with your ID or facebook.</p>
										</div>
										<hr />
										<div class="form-group">
											<input type="text" id="username" placeholder="Enter Username" />
										</div>
										<div class="form-group">
											<input type="password" id="u-password1"
												placeholder="Enter Password" />
										</div>
										<div class="form-group">
											<input type="text" id="u-password2"
												placeholder="Re-enter Password" />
										</div>
									</li>
									<li class="list-group-item"><input type="button"
										value="Add Username" class="btn btn-success form-control" /></li>
								</ul>
							</div>
						</c:if>

						<div class="panel panel-default mobile-view-div">
							<div class="panel-heading" style="background-color: #5bc0de">
								<span class="panel-title pan-title-font">Create a forum</span>
							</div>
							<ul class="list-group list-group-flush">
								<li class="list-group-item " id="add-facebook-msg-div"
									style="font-size: 8pt;">
									<p style="font-weight: bold;">Creating a forum
										automatically upgrades your subscription to a Power User</p>
									<p style="font-weight: bold;">Taking advantage of this
										service will incurr some extra charges.</p>
									<hr />
									<div>
										<form id="create-forum-form">
											<div class="form-group">
												<label for="forum-name">Forum Name:</label> <input
													type="text" id="forum-name" name="forum-name"
													style="width: 100%; padding: 1%;">
											</div>

											<div class="form-group">
												<label for="forum-name">Category:</label> <select class="chosen-select"
													name="catergory" style="width: 100%; padding: 1%;">
													<option>Agriculture and Natural Resources</option>
													<option>Architecture and Construction</option>
													<option>Arts, Audio/Video Technology, and
														Communications</option>
													<option>Business and Administration</option>
													<option>Education and Training</option>
													<option>Finance</option>
													<option>Government and Public Administration</option>
													<option>Health Science</option>
													<option>Hospitality and Tourism</option>
													<option>Human Services</option>
													<option>Information Technology</option>
													<option>Law, Public Safety, Corrections & Security</option>
													<option>Manufacturing</option>
													<option>Marketing</option>
													<option>Science, Technology, Engineering &
														Mathematics</option>
													<option>Transportation, Distribution, and
														Logistics</option>
												</select>
											</div>
											<div class="form-group">
												<label for="forum-name">Short description:</label>
												<textarea rows="5" cols="40" style="width: 100%;"
													style="overflow: hidden; resize: none" maxlength="250";>
												
												</textarea>
												<output></output>
											</div>
											<div class="form-group">
												<label for="forum-name">About you:</label>
												<textarea rows="5" cols="40" style="width: 100%;"
													style="overflow: hidden; resize: none" maxlength="250";>
												
												</textarea>
												<output></output>
											</div>

											<div class="form-group">
												<input type="file" name="forum-image">
											</div>
										</form>
									</div>
									<hr/>
									<div>
										<input type="button" value="Create Forum"
											class="btn btn-info form-control" style="margin-top: 4%;" />
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



	<div class="col-sm-2"
		style="height: 100%; position: fixed; top: 0; right: 0; padding-left: 3%; margin: 0">
		<div class="col-sm-6" style="height: 100%;"></div>
		<div class="col-sm-6" style="height: 100%;"></div>
	</div>
	</div>

	</div>
	<div id="modals"></div>
	<%@ include file="/WEB-INF/modals.jsp"%>

	<script src="/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/jquery.webui-popover.min.js"></script>
	<script type="text/javascript" src="/js/waitMe.js"></script>
	<script type="text/javascript" src="/js/init.js"></script>
	<script type="text/javascript" src="/js/modules.js"></script>
	<script type="text/javascript" src="/js/validators.js"></script>
	<script type="text/javascript" src="/js/settings.js"></script>
	<script type="text/javascript" src="/js/prism.js"></script>

</body>
</html>

