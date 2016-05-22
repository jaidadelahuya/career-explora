
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome <c:out value="${azureUser.firstName}" /> <c:out
		value="${azureUser.lastName}" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" type="text/css" href="/style/jquery-ui.css">
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" type="text/css"
	href="/style/jquery.webui-popover.min.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">
<style type="text/css">
</style>
</head>
<body style="padding-top: 50px;" id="main-body">
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

				<div id="sub-main" class="row hidden-xs">
					<%@ include file="/pages/partials/module.html"%>
					<div class="col-xs-12 col-sm-5" style="padding-right: 0;">
						<div class="panel panel-default">
							<div class="panel-heading"
								style="background-color: rgb(255, 140, 0)">
								<span class="panel-title pan-title-font">Last test/login
									activity</span>
							</div>

							<ul class="list-group list-group-flush">
								<li class="list-group-item orange-list" id="last-seen-date-row">
									<table
										class="table table-borderless table-condensed table-responsive"
										style="border: none; margin: 0 auto">
										<tr>
											<c:choose>
												<c:when test="${empty welcomePage.lastSeenDate}">
													<h4 style="color: blue">Welcome,
														${azureUser.firstName} ${azureUser.lastName}</h4>
												</c:when>
												<c:otherwise>
													<td class="text-right" style="width: 45%;"><strong
														class="last-login-label">Last login: </strong></td>
													<td id="last-seen-date" class="text-left last-login-data">
														<c:out value="${welcomePage.lastSeenDate}"
															default="First Login. Welcome!" />
													</td>
												</c:otherwise>
											</c:choose>
										</tr>
									</table>
								</li>

								<li class="list-group-item orange-list">
									<table
										class="table table-borderless table-condensed table-responsive"
										style="border: none; margin: 0 auto">
										<tr>
											<td class="text-right" style="width: 45%;"><strong
												class="last-login-label">Last test: </strong></td>
											<td class="text-left last-login-data" id="last-test-name"
												style="font-weight: bold; font-size: 12pt"><c:out
													value="${welcomePage.lastTest}"
													default="You have not taken a test." /></td>
										</tr>

										<c:if test="${not empty welcomePage.lastTestDate}">
											<tr>
												<td class="text-right" style="width: 45%;"><strong
													class="last-login-label">Test date: </strong></td>
												<td id="last-test-date" class="text-left last-login-data">
													<c:out value="${welcomePage.lastTestDate}" />
												</td>
											</tr>
										</c:if>

									</table>
								</li>

								<li class="list-group-item orange-list" id="last-seen-date-row">
									<table
										class="table table-borderless table-condensed table-responsive"
										style="border: none; margin: 0 auto">
										<tr>
											<td class="text-right" style="width: 45%;"><strong
												class="last-login-label">Subscription: </strong></td>
											<c:choose>
												<c:when test='${user.freeAccess}'>
													<td id="subscription" class="text-left last-login-data">Free Access
														<br /> <Strong
														class="upcoming-testname to-pointer view-subscription-details"
														style="font-size: 9pt">View details</Strong>
													</td>
												</c:when>
												<c:otherwise>
													<td id="subscription" class="text-left last-login-data">Active
														<br /> <Strong
														class="upcoming-testname to-pointer view-subscription-details"
														style="font-size: 9pt">View details</Strong>
													</td>
												</c:otherwise>
											</c:choose>

										</tr>

									</table>
								</li>

							</ul>
						</div>
						<!-- 
						<div class="panel panel-default">
							<div class="panel-heading" style="background-color: green">
								<span class="panel-title pan-title-font">Upcoming Test Reminder</span>
							</div>

							<ul class="list-group" id="upcoming-tests-section">

								<c:choose>
									<c:when test='${empty upComingTests }'>
										<div style="padding: 2%; font-family: georgia; color: gray;" id="uct-default">
											<strong>You are not preparing for a test</strong>
										</div>
									</c:when>
									<c:otherwise>
										<li class="list-group-item green-list" id="test-size-li"><strong
											style="font-family: georgia; color: gray;" id="test-size">You have
												${fn:length(upComingTests)} test(s)</strong></li>
										<c:forEach var='ucTest' items="${upComingTests}" varStatus='i'>
											<li class='list-group-item green-list'>
												<div class='topic-div'>
													<strong class='upcoming-testname'
														style='background-color: green'> <c:out
															value="${ucTest.testName}" />
													</strong><br /> <span class='daysleft'> <c:choose>
															<c:when test="${ucTest.daysLeft < 0}">
																<strong class='nodays' style="color: red"><c:out
																		value="Expired" /></strong>
															</c:when>
															<c:when test="${ucTest.daysLeft == 0}">
																<strong class='nodays' style="color: green"><c:out
																		value="You have this test today" /></strong>
															</c:when>
															<c:otherwise>
																<strong class='nodays' style="color:${ucTest.color}"><c:out
																		value="${ucTest.daysLeft}" /></strong>
														day(s) countdown.
													
													</c:otherwise>
														</c:choose></span><br /> <span><a href='#' class='edit-topic'
														name='${ucTest.id}'>edit</a> <a class='to-pointer delete-uct'
														name="${ucTest.id}">delete</a> </span> <br />
												</div>
											</li>
										</c:forEach>
									</c:otherwise>
								</c:choose>
								<li class="list-group-item">
									<div>
										<input type="button" value="Add a test"
											class="btn green-button" id="new-up-test"/> 
											<input type="button"
											value="Show more" class="btn green-button"
											style="visibility: hidden;" id="show-more-upcoming-test-btn"
											onclick="" />
									</div>
								</li>
							</ul>
						</div>  -->
						<!--  
						<div class="panel panel-default">
							<div class="panel-heading" style="background-color: indigo">
								<span class="panel-title pan-title-font">My favorite
									forums</span>
							</div>
							<div>You haven't join any forums yet</div>
						</div>
						-->
					</div>
					<div class="col-xs-12 col-sm-5" style="padding-right: 0;">
						<div class="panel panel-default" id="pymk">
							<div class="panel-heading" style="background-color: #f0ad4e;">
								<span class="panel-title pan-title-font">You may know</span>
							</div>

							<ul class="list-group fmk-lists">
								<c:if test='${not empty suggestedFriends[0]}'>
									<li class="list-group-item fmk-list yellow-list">
										<div class="row">
											<div class="col-sm-4">
												<img
													class="img img-circle img-responsive friend-image suggested-friend-picture-1"
													src='${suggestedFriends[0]["picture"]}' />
											</div>
											<div class="col-sm-8"
												style="padding-left: 0px; margin-left: 0px; padding-right: 0px; margin-right: 0px">
												<div class="col-sm-12"
													style="padding-left: 0px; margin-left: 0px; padding-right: 0px; margin-right: 0px">
													<strong class="fmk-name fmk-name-1"><c:out
															value='${suggestedFriends[0]["firstName"]}' /> <c:out
															value='${suggestedFriends[0]["lastName"]}' /></strong>
												</div>
												<div class="col-sm-12"
													style="padding-left: 0px; margin-left: 0px">
													<a class="btn btn-default btn-sm btn-warning"
														href="<c:url value='/azure/getuserprofile?id=0'/>">View
														profile</a>

												</div>
											</div>
										</div>
									</li>
								</c:if>

								<c:if test='${not empty suggestedFriends[1]}'>
									<li class="list-group-item fmk-list yellow-list">
										<div class="row">
											<div class="col-sm-4">
												<img
													class="img img-circle img-responsive friend-image suggested-friend-picture-2"
													src='${suggestedFriends[1]["picture"]}' />
											</div>
											<div class="col-sm-8"
												style="padding-left: 0px; margin-left: 0px; padding-right: 0px; margin-right: 0px">
												<div class="col-sm-12"
													style="padding-left: 0px; margin-left: 0px; padding-right: 0px; margin-right: 0px">
													<strong class="fmk-name fmk-name-2"><c:out
															value='${suggestedFriends[1]["firstName"]}' /> <c:out
															value='${suggestedFriends[1]["lastName"]}' /></strong>
												</div>
												<div class="col-sm-12"
													style="padding-left: 0px; margin-left: 0px">
													<a class="btn btn-default btn-sm btn-warning"
														href="<c:url value='/azure/getuserprofile?id=1'/>">View
														profile</a>

												</div>
											</div>
										</div>
									</li>

								</c:if>
								<c:if test='${not empty suggestedFriends[2]}'>
									<li class="list-group-item fmk-list yellow-list">
										<div class="row">
											<div class="col-sm-4">
												<img
													class="img img-circle img-responsive friend-image suggested-friend-picture-3"
													src='${suggestedFriends[2]["picture"]}' />
											</div>
											<div class="col-sm-8"
												style="padding-left: 0px; margin-left: 0px; padding-right: 0px; margin-right: 0px">
												<div class="col-sm-12"
													style="padding-left: 0px; margin-left: 0px; padding-right: 0px; margin-right: 0px">
													<strong class="fmk-name fmk-name-3"><c:out
															value='${suggestedFriends[2]["firstName"]}' /> <c:out
															value='${suggestedFriends[2]["lastName"]}' /></strong>
												</div>
												<div class="col-sm-12"
													style="padding-left: 0px; margin-left: 0px">

													<a class="btn btn-default btn-sm btn-warning"
														href="<c:url value='/azure/getuserprofile?id=2'/>">View
														profile</a>

												</div>
											</div>
										</div>
									</li>

								</c:if>


								<li class="list-group-item">
									<div>
										<a class="btn btn-default btn-sm btn-warning more-friends">Show
											me more</a>
									</div>
								</li>
							</ul>

						</div>
						<!--
						<div class="panel panel-default">
							<div class="panel-heading" style="background-color: blue">
								<span class="panel-title pan-title-font">Skills you can
									build</span>
							</div>
							<ul class="list-group">
								<li class="list-group-item">
									<div>
										<strong>Skill 1</strong>
									</div>
									<div>
										<input type="radio" /><span
											style="font-family: monospace; padding: 2%;">I have
											already built this skill.</span><br /> <input type="radio" /><span
											style="font-family: monospace; padding: 2%;">I want to
											build this skill.</span><br /> <input type="radio" /><span
											style="font-family: monospace; padding: 2%;">I don't
											want to build this skill.</span><br /> <input type="radio" /><span
											style="font-family: monospace; padding: 2%;">I am not
											decided on this yet.</span><br />
									</div>
								</li>

								<li class="list-group-item">
									<div>
										<strong>Skill 2</strong>
									</div>
									<div>
										<input type="radio" /><span
											style="font-family: monospace; padding: 2%;">I have
											already built this skill.</span><br /> <input type="radio" /><span
											style="font-family: monospace; padding: 2%;">I want to
											build this skill.</span><br /> <input type="radio" /><span
											style="font-family: monospace; padding: 2%;">I don't
											want to build this skill.</span><br /> <input type="radio" /><span
											style="font-family: monospace; padding: 2%;">I am not
											decided on this yet.</span><br />
									</div>
								</li>

								<li class="list-group-item">
									<div>
										<input type="button" value="Save to profile" class="btn"
											style="background-color: blue; color: white;" />
									</div>
								</li>
							</ul>
						</div>
						  
						<div class="panel panel-default">
							<div class="panel-heading" style="background-color: violet">
								<span class="panel-title pan-title-font">Forums you may
									like</span>
							</div>
							<div class="panel-body"></div>
						</div>
						-->
					</div>
				</div>
			</div>
			<div class="col-sm-2 hidden-xs"
				style="height: 100%; position: fixed; top: 0; right: 0; padding-left: 3%; margin: 0">
				<div class="col-sm-6" style="height: 100%;"></div>
				<div class="col-sm-6" style="height: 100%;"></div>
			</div>
		</div>

		<!-- mobile view -->
		<div class="row hidden-sm hidden-md hidden-lg">
			<div class="mobile-view mobile-view-top">

				<div style="height: auto; width: 40%; float: left;">
					<img class='img img-circle img-responsive mobile-profile-image'
						src='${azureUser["picture"]}'>
				</div>
				<div style="height: auto; width: 60%; float: left">
					<h4 class="user-name"
						style="word-wrap: break-word; font-size: 100%;">
						<c:out value='${azureUser.firstName}' />
						<c:out value='${azureUser.lastName}' />
					</h4>

					<div>
						<span style="font-size: 80%; font-weight: bold;"><c:out
								value='${azureUser.school}' />
					</div>

					<div style="font-size: 80%;">
						<c:if
							test="${(not empty azureUser.state) || (not empty azureUser.country)}">
							<span id="lv">Lives in</span>
						</c:if>
						<span style="text-transform: capitalize;" id="loc"><span
							id="p-state"><c:out value='${azureUser.state}' /></span> <c:if
								test="${(not empty azureUser.state)}"> , </c:if> <span
							id="p-country"><c:out value='${azureUser.country}' /> </span></span>
					</div>

				</div>
				<div style="clear: both;"></div>
				<hr></hr>
				<div style="padding: 3%; padding-bottom: 0; padding-top: 0">
					<c:choose>
						<c:when test="${empty welcomePage.lastSeenDate}">
							<h4 style="color: blue">Welcome, ${azureUser.firstName}
								${azureUser.lastName}</h4>
						</c:when>
						<c:otherwise>
							<strong class="last-login-label">Last login: </strong>
							<span id="last-seen-date"
								style="font-family: georgia; font-size: 100%;"><c:out
									value="${welcomePage.lastSeenDate}" /></span>
						</c:otherwise>
					</c:choose>
				</div>
				<hr></hr>
				<div>
					<div style="padding: 3%; padding-bottom: 2%; padding-top: 0">
						<strong class="last-login-label">Last test: </strong> <span
							id="last-test-name" style="font-weight: bold; font-size: 100%;"><c:out
								value="${welcomePage.lastTest}"
								default="You have not taken a test." /></span>
					</div>

					<c:if test="${not empty welcomePage.lastTestDate}">
						<div style="padding: 3%; padding-bottom: 0; padding-top: 0">
							<strong class="last-login-label">Test date: </strong> <span
								id="last-test-date"
								style="font-family: georgia; font-size: 100%;"><c:out
									value="${welcomePage.lastTestDate}" /></span>
						</div>
					</c:if>
				</div>
				<hr></hr>
				<div
					style="padding: 3%; padding-bottom: 0; padding-top: 0; padding-bottom: 3%;">
					<strong class="last-login-label">Subscription: </strong> <Strong
						class="upcoming-testname to-pointer view-subscription-details"
						style="font-size: 9pt">View details</Strong> </span>
				</div>
			</div>

			<div class="mobile-view fmk-lists">
				<header style="padding: 3%; padding-bottom: 0">
					<h4>You may know</h4>
				</header>
				<hr style="width: 90%; margin: 0 auto"></hr>
				<div style="height: auto; width: 40%; float: left;">
					<img
						class='img img-circle img-responsive mobile-profile-image suggested-friend-picture-1'
						src='${suggestedFriends[0]["picture"]}'>
				</div>
				<div style="height: auto; width: 60%; float: left">
					<h4 class="user-name fmk-name-1"
						style="word-wrap: break-word; font-size: 100%;">
						<c:out value='${suggestedFriends[0]["firstName"]}' />
						<c:out value='${suggestedFriends[0]["lastName"]}' />
					</h4>
					<div class="col-sm-12" style="padding-left: 0px; margin-left: 0px">
						<a class="btn btn-default btn-sm btn-warning"
							href="<c:url value='/azure/getuserprofile?id=0'/>">View
							profile</a>

					</div>

				</div>
				<div style="clear: both;"></div>
				<hr style="width: 90%; margin: 0 auto"></hr>

				<div style="height: auto; width: 40%; float: left;">
					<img
						class='img img-circle img-responsive mobile-profile-image suggested-friend-picture-2'
						src='${suggestedFriends[1]["picture"]}'>
				</div>
				<div style="height: auto; width: 60%; float: left">
					<h4 class="user-name fmk-name-2"
						style="word-wrap: break-word; font-size: 100%;">
						<c:out value='${suggestedFriends[1]["firstName"]}' />
						<c:out value='${suggestedFriends[1]["lastName"]}' />
					</h4>
					<div class="col-sm-12" style="padding-left: 0px; margin-left: 0px">
						<a class="btn btn-default btn-sm btn-warning"
							href="<c:url value='/azure/getuserprofile?id=1'/>">View
							profile</a>

					</div>

				</div>
				<div style="clear: both;"></div>
				<hr style="width: 90%; margin: 0 auto"></hr>

				<div style="height: auto; width: 40%; float: left;">
					<img
						class='img img-circle img-responsive mobile-profile-image suggested-friend-picture-3'
						src='${suggestedFriends[2]["picture"]}'>
				</div>
				<div style="height: auto; width: 60%; float: left">
					<h4 class="user-name fmk-name-3"
						style="word-wrap: break-word; font-size: 100%;">
						<c:out value='${suggestedFriends[2]["firstName"]}' />
						<c:out value='${suggestedFriends[2]["lastName"]}' />
					</h4>
					<div class="col-sm-12" style="padding-left: 0px; margin-left: 0px">
						<a class="btn btn-default btn-sm btn-warning"
							href="<c:url value='/azure/getuserprofile?id=2'/>">View
							profile</a>

					</div>

				</div>
				<div style="clear: both;"></div>
				<hr style="width: 90%; margin: 0 auto"></hr>

				<div dir="rtl" style="padding: 3%;">
					<a class="btn btn-default btn-sm btn-warning more-friends">Show
						me more</a>
				</div>
			</div>



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
	<script type="text/javascript" src="/js/fbook.js"></script>

</body>
</html>