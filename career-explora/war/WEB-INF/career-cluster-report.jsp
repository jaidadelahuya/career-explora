<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><c:out value="${azureUser.firstName}" /> <c:out
		value="${azureUser.lastName}" /> | Career Clusters Test Report</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" type="text/css" href="/style/jquery-ui.css">
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" type="text/css"
	href="https://www.shieldui.com/shared/components/latest/css/shieldui-all.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://www.shieldui.com/shared/components/latest/css/light/all.min.css" />
</head>
<body id="main-body" class="career-cluster-body">

	<div class="container report-container career-cluster-report-container">
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
						<c:out value='CAREER CLUSTERS TEST' />
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
								value='${azureUser.lastName}' /></strong> , from the Career Clusters
						test, You discovered you fit into

						<c:out
							value="${careerClusterReport.scCount+careerClusterReport.vscCount}" />
						Career Clusters. They are categorized as follows:-
					</p>
					<ul class="mobile-font">
						<c:if test="${careerClusterReport.vscCount > 0}">
							<li><c:out value="${careerClusterReport.vscCount}" /> very
								strong</li>
						</c:if>
						<c:if test="${careerClusterReport.scCount > 0}">
							<li><c:out value="${careerClusterReport.scCount}" /> strong</li>
						</c:if>
					</ul>
				</div>
			</div>
			<div class="row" style="margin-top: 2%;">
				<div class="col-sm-12 mobile-font-header">
					<h4 style="font-weight: bold; text-align: center" class="text-info">DETAILS</h4>
					<p class="mobile-font" style="line-height: 2">The chart series
						below show each career cluster, a percentage which indicates how
						well you fit into it, a brief discription and the career pathways
						that under its scope.</p>
				</div>
				<c:if test="${careerClusterReport.vscCount > 0}">
					<div class="career-cluster-report-div row">
					
						<h4 style="text-align: center; clear:both;" class="text-primary">Very
							Strong Career Clusters</h4>
						<c:forEach var="entry"
							items="${careerClusterReport.veryStrongClusters}">
							<div class="panel panel-default career-cluster-chart">
								<div class="panel-heading text-center ccpanelheader" style="background-color: darkgreen">
									<a class="panel-title pan-title-font cctitle" ><c:out value='${entry.key}'/></a>
								</div>
								<div class="panel-body text-center">
									<p class="lead">
										<strong ></strong>
									</p>
								</div>
								<div>
									<div class="row cpd">
										<div  class="ccprogress very-strong-ccprogress"
											style="height: 190px; width: 190px"></div>
									</div>
								</div>
							</div>
						</c:forEach>

					</div>
				</c:if>
				<c:if test="${careerClusterReport.scCount > 0}">
					<div class="career-cluster-report-div row">
						<h4 style="text-align: center" class="text-primary">Strong
							Career Clusters</h4>
						<c:forEach var="entry"
							items="${careerClusterReport.strongClusters}">
							<div  class="panel panel-primary career-cluster-chart strong-career-cluster-chart">
								<div class="panel-heading text-center ccpanelheader" style="background-color: olive" >
									<a class="panel-title pan-title-font cctitle" ><c:out value='${entry.key}'/></a>
								</div>
								<div class="panel-body text-center">
									<p class="lead">
										<strong></strong>
									</p>
								</div>
								<div>
									<div class="row cpd">
										<div  class="ccprogress strong-ccprogress"
											style="height: 190px; width: 190px"></div>
									</div>
								</div>
							</div>
						</c:forEach>

					</div>
				</c:if>
			</div>
		</div>
	</div>

	<script src="/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/canvasjs.min.js"></script>
	<script type="text/javascript"
		src="https://www.shieldui.com/shared/components/latest/js/shieldui-all.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			getCareerClustersInfo();
		});

		function getCareerClustersInfo() {
			$.ajax({
				url : '/azure/careerclusterreport',
				type : 'GET',
				dataType : 'Json',
				success : function(data) {
					var vsc = Object.keys(data.veryStrongClusters);
					var sc = Object.keys(data.strongClusters);
					var sList = document.getElementsByClassName("strong-ccprogress");
					var vsList = document.getElementsByClassName("very-strong-ccprogress");
					var x = 0;
					var y = 0;
					for(i=0; i < sList.length; i++) {
						x=data.strongClusters[sc[i]];
						sc[i] = sc[i].replace(/\s+/g, '-').replace(/[^a-zA-Z-]/g, '').toLowerCase();
						sList[i].id=sc[i];
						$("#"+sList[i].id).shieldProgressBar(
								{
									min : 0,
									max : 100,
									value : (x * 5),
									layout : "circular",
									layoutOptions : {
										circular : {
											borderColor : "orange",
											width : 17,
											borderWidth : 3,
											color : "green",
											backgroundColor : "transparent"
										}
									},
									text : {
										enabled : true,
										template : '<span style="font-size:52px;color:green;">{0:n0}%</span>'
									}
								});
					}
					
					for(i=0; i < vsList.length; i++) {
						y=data.veryStrongClusters[vsc[i]];
						vsc[i] = vsc[i].replace(/\s+/g, '-').replace(/[^a-zA-Z-]/g, '').toLowerCase();
						vsList[i].id=vsc[i];
						$("#"+vsList[i].id).shieldProgressBar(
								{
									min : 0,
									max : 100,
									value : (y * 5),
									layout : "circular",
									layoutOptions : {
										circular : {
											borderColor : "orange",
											width : 17,
											borderWidth : 3,
											color : "green",
											backgroundColor : "transparent"
										}
									},
									text : {
										enabled : true,
										template : '<span style="font-size:52px;color:green;">{0:n0}%</span>'
									}
								});
					}
				},
				error : function(jqXHR, status, errorThrown) {
					console.log("error");
				}
			});
		}
	

		
	</script>
	
</body>
</html>