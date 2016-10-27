<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta charset="ISO-8859-1">
<title>${cbtInfo.subject}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="/style/jquery.webui-popover.min.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">
<link rel="stylesheet" href="/style/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" href="/style/theme.css">
<style type="text/css">
.cont-button {
	position: relative;
	top: 22%;
}


</style>
</head>
<body id="body" style="padding-top: 0px; height: 100%;" class="test-grad">

	<input id="h-user-name" type="hidden" value="${welcomePage.firstName} ${welcomePage.lastName}">
	<div class="container-fluid" id="div-1">
		<div class="row">
			<div class="test-board-header">
				<div class="hidden-xs col-sm-2" style="height: 4.5em">
					<img class="img-circle img-responsive profile-img to-pointer"
						src="${welcomePage.profileImg}" />
				</div>

				<div class="hidden-xs col-sm-8 heading-div" style="height: 4.5em">
					<h2 class="heading">
						<c:out value='${cbtInfo.subject}' />
					</h2>
				</div>
				<div class="col-xs-12 col-sm-2 col-md-2" id="save-div">
					<span id="demo" class="timer"></span>
				</div>
			</div>
		</div>
		<div id="cc-div-content" class="row">
			<div class="row">
				<div class="col-sm-12 test-board-extra-text" id="extra-info"></div>
			</div>
			<div class="row" id="diagram-passage">
				<div class="col-sm-12"></div>
			</div>
			<div class="row test-board-question">
				<div id="div-q" class="col-sm-12"></div>
			</div>
			<div class="row test-board-alternative">
				<div class="col-sm-12">
					<input type="radio" name="opt" class="opt" id="opt-4" value="A" />
					A. <span id="opt-a" class="test-board-option"></span>
				</div>
			</div>
			<div class="row test-board-alternative">
				<div class="col-sm-12">
					<input type="radio" name="opt" class="opt" id="opt-3" value="B" />
					B. <span id="opt-b" class="test-board-option"></span>
				</div>
			</div>
			<div class="row test-board-alternative">
				<div class="col-sm-12">
					<input type="radio" name="opt" class="opt" id="opt-2" value="C" />
					C. <span id="opt-c" class="test-board-option"></span>
				</div>
			</div>
			<div class="row test-board-alternative">
				<div class="col-sm-12">
					<input type="radio" name="opt" class="opt" id="opt-1" value="D" />
					D. <span id="opt-d" class="test-board-option"></span>
				</div>
			</div>
			<div class="row test-board-button-group">
				<div class="col-sm-12">
					<div class="btn-group-xs test-btn-grp" role="group"
						id="test-board-btn-grp"></div>
				</div>
			</div>
		</div>

		<div class="row test-board-footer">
			<div class="col-sm-12" style="overflow: auto">
				<button id="back" class="ca-btn-primary test-board-button">Back</button>
				<button id="next" class="ca-btn-primary test-board-button">Next</button>
				<button id="finish"
					class="ca-btn-primary pull-right  test-board-button">Finish</button>
			</div>
		</div>

	</div>

	<div id="time-dialog" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #3b5998;">
					<h4 class="modal-title" id="myModalLabel" style="color: white;">Time
						Up</h4>
				</div>
				<div class="modal-body">
					<h3>Your time is up!</h3>
				</div>
				<div class="modal-footer" style="background-color: #3b5998;">

					<button type="button" class="btn ca-btn-primary  time-up-button">See
						result</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Instructions  modal -->
	
	<div id="instructions-dialog" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #3b5998;">
					<h4 class="modal-title" id="myModalLabel" style="color: white;">Instructions</h4>
				</div>
				<div class="modal-body" id="modal-body">
					<strong>Time: </strong><span class='test-time'></span><br /> <strong>Subject:
					</strong><span class='subject-name'></span><br />
					<h6>Please read the information carefully</h6>
					<ol>
						<li>There are <span class='no-of-questions'></span> questions
							and you are expected answer all
						</li>
						<li>The maximum time allowed for this test is <strong
							class="test-time"></strong></li>
						<li>If you do not have an idea of a you can skip it by
							clicking the skip button you can re-attempt the skipped questions
							when you have attempted others.</li>
						<li>If you do not have an idea of a you can skip it by
							clicking the skip button you can re-attempt the skipped questions
							when you have attempted others.</li>
					</ol>

				</div>
				<div class="modal-footer" style="background-color: #3b5998;">
					<button class="btn ca-btn-primary  start-test">Start Test</button>
				</div>
			</div>
		</div>
	</div>

	<!-- End test modal -->
	<div id="end-test-dialog" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #3b5998;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="color: white;">End
						Test confirmation</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12">
							<h3 style="color: red; font-family: tahoma">END TEST?</h3>
							<p>
								<small>click the submit button to end the test.</small>
							</p>
						</div>
					</div>
				</div>
				<div class="modal-footer" style="background-color: #3b5998;">
					<button class="btn ca-btn-primary  btn-sm submit-test">Submit</button>
					<button type="button" class="btn btn-default btn-sm"
						data-dismiss="modal">Cancel</button>

				</div>
			</div>
		</div>
	</div>

	<!-- save successful modal -->
	<div id="save-success-dialog" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #3b5998;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="color: white;">Result
						Saved</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12">
							<h3 style="color: #983b59; font-family: tahoma">Save Sucessful</h3>
							<p>
								<small>Click close window to go back to welcome page</small>
							</p>
						</div>
					</div>
				</div>
				<div class="modal-footer" style="background-color: #3b5998;">
					<button class="btn btn-primary btn-sm close-window">Close
						window</button>
					<button type="button" class="btn btn-default btn-sm"
						data-dismiss="modal">Cancel</button>

				</div>
			</div>
		</div>
	</div>




	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/jquery.webui-popover.min.js"></script>
	<script type="text/javascript" src="/js/waitMe.js"></script>
	<script type="text/javascript" src="/js/cbt.js"></script>
	<script type="text/javascript" src="/js/jquery-backward-timer.min.js"></script>
	<script type="text/javascript" src="/js/canvasjs.min.js"></script>
</body>
</html>