<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta charset="ISO-8859-1">
<title>Skill Builder</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="/style/jquery.webui-popover.min.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">
<link rel="stylesheet" href="/style/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<style type="text/css">
.cont-button {
	position: relative;
	top: 22%;
}
</style>
</head>
<body id="body" style="padding-top: 0px; height: 100%;" class="test-grad">

	<div class="container-fluid topmost-container" id="div-1"
		style="padding-right: 0px;">
		<div class="row">
			<div class="test-board-header">
				<div class="hidden-xs col-sm-2" style="height: 4.5em">
					<img class="img-circle img-responsive profile-img to-pointer" />
				</div>

				<div class="col-sm-12 col-sm-8 heading-div" style="height: 4.5em">
					<h2 class="heading">Skills Builder</h2>
				</div>
				<div class="hidden-xs col-sm-2 col-md-2" id="save-div"
					style="height: 4.5em; padding-bottom: 1%;"></div>
			</div>
		</div>
		<div id="cc-div-content" class="row">
			<div class="row">
				<div id="skill-div-1">Your Talents</div>
			</div>
			<div class="row talent-skill-name">
				<div class="col-xs-12 col-sm-12" id="skill-div-2"></div>
				
			</div>

			<div class="row talent-skill-groups">
				<div class="col-xs-12 col-sm-12" id="skill-div-3">
					<div class="col-xs-12 col-sm-6">
						<div class="panel panel-default" style="margin-top: 2%;">

							<div class="panel-heading text-center"
								style="background-color: darkgreen">
								<span class="panel-title pan-title-font"><span id="no-vs"></span>
									Very Strong</span>
							</div>
							<ul class="list-group" id="very-strong-list"
								style="word-wrap: break-word;"></ul>

						</div>
					</div>
					<div class="col-xs-12 col-sm-6">
						<div class="panel panel-default" style="margin-top: 2%;">
							<div class="panel-heading text-center"
								style="background-color: olive">
								<span class="panel-title pan-title-font"><span id="no-s"></span>
									Strong</span>
							</div>
							<ul class="list-group text-left" id="strong-list"
								style="word-wrap: break-word;"></ul>

						</div>
					</div>
				</div>

			</div>
			<div class="row talent-skill-desc">
				<div class="col-xs-12 col-sm-12" id="skill-div-3a"></div>
			</div>
			<div class="row test-board-alternative talent-skill-options">
				<div class="col-sm-12">
					<input type="radio" name="opt" class="opt" id="opt-4" value="4" />
					<span id="opt-a" class="test-board-option"> I have already
						built this skill</span>
				</div>
			</div>
			<div class="row test-board-alternative talent-skill-options">
				<div class="col-sm-12">
					<input type="radio" name="opt" class="opt" id="opt-3" value="3" />
					<span id="opt-b" class="test-board-option">I want to build
						this skill.</span>
				</div>
			</div>
			<div class="row test-board-alternative talent-skill-options">
				<div class="col-sm-12">
					<input type="radio" name="opt" class="opt" id="opt-2" value="2" />
					<span id="opt-c" class="test-board-option">I don't want to
						build this skill</span>
				</div>
			</div>
			<div class="row test-board-alternative talent-skill-options">
				<div class="col-sm-12">
					<input type="radio" name="opt" class="opt" id="opt-1" value="1" />
					<span id="opt-d" class="test-board-option">I am not decided
						on this yet.</span>
				</div>
			</div>

			<div class="row talent-skill-info">
				<div id="skill-div-4" class="col-xs-12 col-sm-12">
					<strong id="talent-skill-info-1">There are <span
						id="total-skill" style="color: red;"></span> skills you can build
					</strong>
				</div>
			</div>
			<div class="row talent-skill-info">
				<div id="skill-div-5" class="col-xs-12 col-sm-12">
					<strong id="talent-skill-info-2">Skill <span id='qN'
						style='color: olive'></span> out of <span id='tN'
						style='color: red'></span>.
					</strong>
				</div>
			</div>

		</div>

	</div>


	<div class="row test-board-footer">
		<div class="col-sm-12">
			<button id="back" class="btn ca-btn-primary  test-board-button">Back</button>
			<button id="next" class="btn ca-btn-primary  test-board-button">Next</button>
			<button id="finish"
				class="btn ca-btn-primary pull-right  test-board-button">Finish</button>
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
					<button class="btn ca-btn-primary btn-sm submit-test">Submit</button>
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
							<h3 style="color: red; font-family: tahoma">Save Sucessful</h3>
							<p>
								<small>Click close window to go back to welcome page</small>
							</p>
						</div>
					</div>
				</div>
				<div class="modal-footer" style="background-color: #3b5998;">
					<button class="btn ca-btn-primary btn-sm close-window">Close
						window</button>
					<button type="button" class="btn btn-default btn-sm"
						data-dismiss="modal">Cancel</button>

				</div>
			</div>
		</div>
	</div>

	<!-- End test modal -->
	<div id="go-back-dialog" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #3b5998;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="color: white;">Go
						Back confirmation</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12">
							<h3 style="color: #983b59; font-family: tahoma">GOING BACK?</h3>
							<p>
								<small>If you go back to the start page, your choices
									will not be saved.</small>
							</p>
						</div>
					</div>
				</div>
				<div class="modal-footer" style="background-color: #3b5998;">
					<button class="btn ca-btn-primary btn-sm go-back">Go Back</button>
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
	<script type="text/javascript" src="/js/skill-builder.js"></script>
	<script type="text/javascript" src="/js/methods.js"></script>
</body>
</html>