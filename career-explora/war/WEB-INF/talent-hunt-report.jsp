<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><c:out value="${azureUser.firstName}" /> <c:out
		value="${azureUser.lastName}" /> | Talent Hunt Test Report</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" type="text/css" href="/style/jquery-ui.css">
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
</head>
<body id="main-body" class="talent-hunt-body">


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
					<div class="mobile-font test-report-header-2" style="">
						<c:out value='TALENT HUNT TEST' />
					</div>
				</div>
			</div>
		</div>
		<hr class="report-hr"></hr>
		<div class="report-content-pane">
			<div class="row">
				<div class="col-sm-12 mobile-font-header">
					<h4 style="font-weight: bold; text-align: center" class="text-info">SUMMARY</h4>
					<p class="mobile-font" style="line-height: 2">
						<strong> <c:out value='${azureUser.firstName}' /> <c:out
								value='${azureUser.middleName}' /> <c:out
								value='${azureUser.lastName}' /></strong> , from the Talent Hunt test,
						You discovered you have

						<c:out
							value="${talentReport.vsTalentCount+talentReport.sTalentCount}" />
						talents.The doughnut chart below shows your talents and how strong
						you are in them. <em><strong>Please note:</strong></em> The
						highest you can score is 10 per talent. This means 100% strong.

					</p>
				</div>
			</div>
			<div class="row">
				<div class="report-chart-div" id="chart-div-head" style="margin-bottom: 2%;"></div>
			</div>
			<div class="row">
				<div class="report-chart-div" id="chart-div-hand" style="margin-bottom: 2%;"></div>
			</div>
			<div class="row">
				<div class="report-chart-div" id="chart-div-body" style="margin-bottom: 2%;"></div>
			</div>
			<div class="row" style="margin-top: 2%;">
				<div class="col-sm-12 mobile-font-header">
					<h4 style="font-weight: bold; text-align: center" class="text-info">DETAILS</h4>
					<p class="mobile-font" style="line-height: 2">
						You have
						<c:out value="${talentReport.vsTalentCount}" />
						<Strong style="color: darkgreen">very strong</Strong> and
						<c:out value="${talentReport.sTalentCount}" />
						<strong style="color: olive">strong </strong>talents. The tables
						below shows you how there are categorized into head, hand and body
						talents.It also shows a percentage(%) telling how strong they are.
					</p>
				</div>
				<c:if test="${fn:length(talentReport.headTalents) > 0}">
					<div class="talent-report-div">
						<h4 style="text-align: center" class="text-primary">Head
							Talents</h4>
						
						<table class="table table-responsive table-striped"
							style="width: 100%;">
							<c:if test="${talentReport.vsHeadCount > 0}">
								<tr>
									<th colspan="2" class="mobile-table-header"
										style="color: darkgreen; text-align: center;">VERY STRONG</th>
								</tr>
								<tr>
									<th class="mobile-table-header">Talent Name</th>
									<th class="mobile-table-header">Percentage(%)</th>
								</tr>
								<c:forEach var='head' items='${talentReport.headTalents}'>
									<c:if test='${head.rating=="VS"}'>
										<tr class="mobile-font">
											<td><c:out value="${head.name}" /></td>
											<td><c:out value="${head.value*10}" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${talentReport.sHeadCount > 0}">
								<tr>
									<th colspan="2" class="mobile-table-header"
										style="color: olive; text-align: center;">STRONG</th>
								</tr>
								<tr>
									<th class="mobile-table-header">Talent Name</th>
									<th class="mobile-table-header">Percentage(%)</th>
								</tr>
								<c:forEach var='head' items='${talentReport.headTalents}'>
									<c:if test='${head.rating=="S"}'>
										<tr class="mobile-font">
											<td><c:out value="${head.name}" /></td>
											<td><c:out value="${head.value*10}" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:if>
						</table>
					</div>
				</c:if>
				<c:if test="${fn:length(talentReport.handTalents) > 0}">
					<div class="talent-report-div">
						<h4 style="text-align: center" class="text-primary">Hand
							Talents</h4>
						
						<table class="table table-responsive table-striped">
							<c:if test="${talentReport.vsHandCount > 0}">
								<tr>
									<th colspan="2" class="mobile-table-header"
										style="color: darkgreen; text-align: center;">VERY STRONG</th>
								</tr>
								<tr>
									<th class="mobile-table-header">Talent Name</th>
									<th class="mobile-table-header">Percentage(%)</th>
								</tr>
								<c:forEach var='hand' items='${talentReport.handTalents}'>
									<c:if test='${hand.rating=="VS"}'>
										<tr class="mobile-font">
											<td><c:out value="${hand.name}" /></td>
											<td><c:out value="${hand.value*10}" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${talentReport.sHandCount > 0}">
								<tr>
									<th colspan="2" class="mobile-table-header"
										style="color: olive; text-align: center;">STRONG</th>
								</tr>
								<tr>
									<th class="mobile-table-header">Talent Name</th>
									<th class="mobile-table-header">Percentage(%)</th>
								</tr>
								<c:forEach var='hand' items='${talentReport.handTalents}'>
									<c:if test='${hand.rating=="S"}'>
										<tr class="mobile-font">
											<td><c:out value="${hand.name}" /></td>
											<td><c:out value="${hand.value*10}" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:if>
						</table>
					</div>
				</c:if>
				<c:if test="${fn:length(talentReport.bodyTalents) > 0}">
					<div class="talent-report-div">
						<h4 style="text-align: center" class="text-primary">Body
							Talents</h4>
						
						<table class="table table-responsive table-striped">
							<c:if test="${talentReport.vsBodyCount > 0}">
								<tr>
									<th colspan="2" class="mobile-table-header"
										style="color: darkgreen; text-align: center;">VERY STRONG</th>
								</tr>
								<tr>
									<th class="mobile-table-header">Talent Name</th>
									<th class="mobile-table-header">Percentage(%)</th>
								</tr>
								<c:forEach var='body' items='${talentReport.bodyTalents}'>
									<c:if test='${body.rating=="VS"}'>
										<tr class="mobile-font">
											<td><c:out value="${body.name}" /></td>
											<td><c:out value="${body.value*10}" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${talentReport.sBodyCount > 0}">
								<tr>
									<th colspan="2" class="mobile-table-header"
										style="color: olive; text-align: center;">STRONG</th>
								</tr>
								<tr>
									<th class="mobile-table-header">Talent Name</th>
									<th class="mobile-table-header">Percentage(%)</th>
								</tr>
								<c:forEach var='body' items='${talentReport.bodyTalents}'>
									<c:if test='${body.rating=="S"}'>
										<tr class="mobile-font">
											<td><c:out value="${body.name}" /></td>
											<td><c:out value="${body.value*10}" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:if>
						</table>
					</div>
				</c:if>
			</div>
		</div>
	</div>


	<script src="/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/canvasjs.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			getTalentInfo();
		});

		function getTalentInfo() {
			$.ajax({
				url : '/azure/profile/talent/report/info',
				type : 'GET',
				dataType : 'Json',
				success : function(data) {
					console.log(data);
					var handTalent = data.handTalents;
					console.log(handTalent);
					var headTalent = data.headTalents;
					console.log(headTalent);
					var bodyTalent = data.bodyTalents;
					console.log(bodyTalent);
					
					var headObj = [];
					for(i=0;i<headTalent.length;i++) {
						headObj[i] = {
								talent : headTalent[i].name,
								val: headTalent[i].value
						}
					}
					if(headObj.length > 0) {
						var dPoints = getDataPoints(headObj);
						showChart(dPoints,"chart-div-head","YOUR HEAD TALENTS");
					} else {
						$("#chart-div-head").css('display','none');
					}
					
					var handObj = [];
					for(i=0;i<handTalent.length;i++) {
						handObj[i] = {
								talent : handTalent[i].name,
								val: handTalent[i].value
						}
					}
					if(handObj.length > 0) {
						var dPoints = getDataPoints(handObj);
						showChart(dPoints,"chart-div-hand","YOUR HAND TALENTS");
					} else {
						$("#chart-div-hand").css('display','none');
					}
					
					var bodyObj = [];
					for(i=0;i<bodyTalent.length;i++) {
						bodyObj[i] = {
								talent : bodyTalent[i].name,
								val: bodyTalent[i].value
						}
					}
					if(bodyObj.length > 0) {
						var dPoints = getDataPoints(bodyObj);
						showChart(dPoints,"chart-div-body","YOUR BODY TALENTS");
					} else {
						$("#chart-div-body").css('display','none');
					}
					
					
				},
				error : function(jqXHR, status, errorThrown) {
					console.log("error");
				}
			});
		}
		function getDataPoints(obj) {
			var dps = [];

			for (i = 0; i < obj.length; i++) {

				var choice = obj[i].val;
				var name = obj[i].talent;
				dps[dps.length] = {
					label : name,
					y : choice,
					legendText : name
				};
			}
			return dps;
		}

		function showChart(dPoints,area,title) {
			// alert(dPoints.length);
			var chart = new CanvasJS.Chart(area, {

				data : [ {
					type : "doughnut",
					exploded : true,
					showInLegend: true,
					dataPoints : dPoints,

				} ],
				title : {
					text : title,
					dockInsidePlotArea : true,
					verticalAlign : "top"
				}
			});
			chart.render();
		}
	</script>
</body>
</html>