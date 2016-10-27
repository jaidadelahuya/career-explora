<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><c:out value="${azureUser.firstName}" /> <c:out
		value="${azureUser.lastName}" /> | Multiple Intelligence Test Report</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" type="text/css" href="/style/jquery-ui.css">
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">
</head>
<body id="main-body" class="mit-body">

	<div class="container report-container">
		<div class="row">
			<div class="report-header">
				<img alt="" src="${azureUser.picture}"
					style="width: 11%; height: auto; margin-left: 0.5em; margin-right: 0.5em; margin-top: 0.5em; float: left"
					class="img img-responsive img-circle report-picture ">
				<div style="float: left; width: 82%; padding: 2%;">
					<div class="mobile-font-header test-report-header-1">
						<c:out value='${azureUser.firstName}' />
						<c:out value='${azureUser.middleName}' />
						<c:out value='${azureUser.lastName}' />
					</div>
					<div class="mobile-font test-report-header-2"
						style="color: #a94442">
						<c:out value='MULTIPLE INTELLIGENCE TEST' />
					</div>
				</div>
			</div>
		</div>
		<hr class="report-hr"></hr>
		<div class="report-content-pane">
			<div class="row">
				<div class="col-sm-12 mobile-font-header">
					<h4 style="font-weight: bold; text-align: center"
						class="text-danger">SUMMARY</h4>
					<p class="mobile-font" style="line-height: 2">
						<strong> <c:out value='${azureUser.firstName}' /> <c:out
								value='${azureUser.middleName}' /> <c:out
								value='${azureUser.lastName}' /></strong> . From the Multiple
						Intelligence Test, toy discovered you have
						<c:out
							value="${fn:length(mitReport.vsmit)+fn:length(mitReport.smit)+fn:length(mitReport.fsmit) }" />
						Intelligence type(s) . The doughnut chart below shows your
						Intelliegence Types and how strong you are in each of them. <em><strong>Please
								note:</strong></em> The highest you can score is 20 per talent. This means
						100% strong.:-
					</p>
				</div>
			</div>

			<div class="row mit-chart-div">
				<div id="chart-div" class="chart-d" style="width: 300px; height: 300px; float: left"></div>

				<div class="mobile-font mit-report-key">
					<div class="col-sm-12" style="margin-bottom: 2%;">
						<h4 class="text-danger">Key</h4>
						<div
							style="width: 10px; height: 10px; background-color: darkgreen; margin: 2%; float: left;"></div>
						<div style="float: left; margin-left: 2%; margin-right: 2%;">
							Very Strong</div>
					</div>
					<div class="col-sm-12" style="margin-bottom: 2%;">
						<div
							style="width: 10px; height: 10px; background-color: olive; margin: 2%; float: left"></div>
						<div style="float: left; margin-left: 2%; margin-right: 2%;">Strong</div>
					</div>
					<div class="col-sm-12" style="margin-bottom: 2%;">
						<div
							style="width: 10px; height: 10px; background-color: orange; margin: 2%; float: left;"></div>
						<div style="float: left; margin-left: 2%; margin-right: 2%;">Fairly
							Strong</div>
					</div>
				</div>
			</div>

			<div class="row" style="margin-top: 2%;">
				<div class="col-sm-12 mobile-font-header">
					<h4 style="font-weight: bold; text-align: center"
						class="text-danger">DETAILS</h4>
				</div>
				<p class="mobile-font" style="line-height: 2">
					You have
					<c:if test="${fn:length(mitReport.vsmit) > 0}">
					<c:out value="${fn:length(mitReport.vsmit)}" />
					<Strong style="color: darkgreen">very strong</Strong>, </c:if>
					<c:if test="${fn:length(mitReport.smit) > 0}">
					<c:out value="${fn:length(mitReport.smit)}" />
					<strong style="color: olive">strong </strong> and</c:if>
					<c:if test="${fn:length(mitReport.fsmit) > 0}">
					<c:out value="${fn:length(mitReport.fsmit)}" />
					<strong style="color: orange"> fairly strong </strong></c:if>Intelligence
					Type(s). The tables below shows a brief description of each
					Intelligence Type and how strong you are on each of them.
				</p>
				<div
					class="col-xs-12 hidden-sm hidden-md hidden-lg col-centered mobile-font"
					style="padding: 4%; padding-top: 2%; margin-bottom: 2em">
					<div
						style="width: 10px; height: 10px; background-color: darkgreen; float: left; margin: 1%;"></div>
					<div style="float: left; margin-left: 1%; margin-right: 2%;">
						Very Strong</div>
					<div
						style="width: 10px; height: 10px; background-color: olive; float: left; margin: 1%;"></div>
					<div style="float: left; margin-left: 1%; margin-right: 2%;">Strong</div>
					<div
						style="width: 10px; height: 10px; background-color: orange; float: left; margin: 1%;"></div>
					<div style="float: left; margin-left: 1%; margin-right: 2%;">Fairly
						Strong</div>
				</div>

			</div>

			<div class="row mit-report-tables" >
				<c:forEach var='mit' items='${mitReport.vsmit}'>
					<div class="report-styled-div row">
						<div class="report-styled-header"
							style="background-color: darkgreen">
							<h5 class="mobile-font">
								<c:out value='${mit.key.intelligenceType}' />
								<span class="status pull-right hidden">Show</span>
							</h5>
						</div>
						<div class="mit-report-content col-sm-12"
							style="padding-left: 0; padding-right: 0">
							<table class="table table-responsive table-striped mobile-font">
								<tr>
									<td colspan="2"><strong>Description</strong></td>
									<td>${mit.key.longDescription}</td>
								</tr>
								<tr>
									<td colspan="2"><strong>Typical Roles</strong></td>
									<td>${mit.key.typicalRoles}</td>
								</tr>
								<tr>
									<td colspan="2"><strong>Preferred Learning Style</strong></td>
									<td>${mitkey.preferredLearningStyle}</td>
								</tr>
							</table>
						</div>
					</div>
				</c:forEach>
				<c:forEach var='mit' items='${mitReport.smit}'>
					<div class="report-styled-div row">
						<div class="report-styled-header"
							style="background-color: olive">
							<h5 class="mobile-font">
								<c:out value='${mit.key.intelligenceType}' />
								<span class="status pull-right hidden">Show</span>
							</h5>
						</div>
						<div class="mit-report-content col-sm-12"
							style="padding-left: 0; padding-right: 0">
							<table class="table table-responsive table-striped mobile-font">
								<tr>
									<td><strong>Description</strong></td>
									<td>${mit.key.longDescription}</td>
								</tr>
								<tr>
									<td><strong>Typical Roles</strong></td>
									<td>${mit.key.typicalRoles}</td>
								</tr>
								<tr>
									<td><strong>Preferred Learning Style</strong></td>
									<td>${mit.key.preferredLearningStyle}</td>
								</tr>
							</table>
						</div>
					</div>
				</c:forEach>
				<c:forEach var='mit' items='${mitReport.fsmit}'>
					<div class="report-styled-div row">
						<div class="report-styled-header"
							style="background-color: orange">
							<h5 class="mobile-font">
								<c:out value='${mit.key.intelligenceType}' />
								<span class="status pull-right hidden">Show</span>
							</h5>
						</div>
						<div class="mit-report-content col-sm-12"
							style="padding-left: 0; padding-right: 0">
							<table class="table table-responsive table-striped mobile-font">
								<tr>
									<td><strong>Description</strong></td>
									<td>${mit.key.longDescription}</td>
								</tr>
								<tr>
									<td><strong>Typical Roles</strong></td>
									<td>${mit.key.typicalRoles}</td>
								</tr>
								<tr>
									<td><strong>Preferred Learning Style</strong></td>
									<td>${mit.key.preferredLearningStyle}</td>
								</tr>
							</table>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<script src="/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/canvasjs.min.js"></script>
	<script type="text/javascript" src="/js/waitMe.js"></script>
	<script type="text/javascript">
		$(document).ready(
				function() {
					getMitTestInfo();
					$(".report-styled-header").click(
							function() {
								$(this).parent().find(".mit-report-content")
										.slideToggle('slow');
								var x = $(this).find(".status");
								var y = x.text();
								if (y === "Show") {
									x.text("Hide");
								} else {
									x.text("Show");
								}
							});

					$(".report-styled-header").mouseover(function() {

						$(this).find(".status").removeClass("hidden");
					});

					$(".report-styled-header").mouseout(function() {
						$(this).find(".status").addClass("hidden");
					});
				});
		function getMitTestInfo() {
			$.ajax({
				url : '/azure/profile/mit/report/info',
				type : 'GET',
				dataType : 'Json',
				success : function(data) {

					var vs = JSON.stringify(data.vs).replace(/{|}|\"/g, '')
							.split(",");
					var s = JSON.stringify(data.s).replace(/{|}|\"/g, '')
							.split(",");
					var fs = JSON.stringify(data.fs).replace(/{|}|\"/g, '')
							.split(",");
					var allTypes = [];

					for (i = 0; i < vs.length; i++) {
						if (vs[i] === "" || vs[i] === undefined) {
							break;
						} else {
							allTypes[i] = vs[i]
						}
					}
					j = allTypes.length;
					for (i = 0; i < s.length + j; i++) {
						if (s[i] === "" || s[i] === undefined) {
							break;
						} else {
							allTypes[allTypes.length] = s[i]
						}
					}

					console.log(allTypes.length);
					j = allTypes.length;
					for (i = 0; i < fs.length + j; i++) {
						if (fs[i] === "" || fs[i] === undefined) {
							break;
						} else {
							allTypes[allTypes.length] = fs[i]
						}
					}
					console.log(allTypes.length);
					var objs = [];

					for (i = 0; i < allTypes.length; i++) {
						var vals = allTypes[i].split(":");
						objs[i] = {
							type : vals[0],
							value : vals[1]
						}

					}
					var dPoints = getDataPoints(objs);

					showChart(dPoints);

				},
				error : function(jqXHR, status, errorThrown) {
					alert(jqXHR.responseText);
				}
			});
		}
		function getDataPoints(obj) {
			var dps = [];

			for (i = 0; i < obj.length; i++) {

				var choice = obj[i].value;
				var clr = null;
				var name = obj[i].type;

				if (choice >= 16) {
					clr = "darkgreen";
				} else if (choice >= 13) {
					clr = "olive";
				} else if (choice >= 10) {
					clr = "orange";
				} else {
					clr = "red";
				}
				dps[dps.length] = {
					label : name,
					y : choice,
					color : clr
				};
			}
			return dps;
		}

		function showChart(dPoints) {
			// alert(dPoints.length);
			var chart = new CanvasJS.Chart("chart-div", {

				data : [ {
					type : "doughnut",
					exploded : true,
					dataPoints : dPoints
				} ]
			});
			chart.render();
		}
	</script>
</body>
</html>