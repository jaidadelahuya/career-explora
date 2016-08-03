<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page
	import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService"%>
<%@ page import="com.google.appengine.api.blobstore.UploadOptions"%>

<%
	BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	UploadOptions options = UploadOptions.Builder
			.withMaxUploadSizeBytesPerBlob(2 * 1024 * 1024)
			.maxUploadSizeBytes(2 * 1024 * 1024);
%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Discussion</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/style/materialize.min.css">
<link rel="stylesheet" href="/style/materialize-tags.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>
<body>
	<div class="container"
		style="width: 60%; border: 1px solid gray; padding: 3%; margin-top: 4%;">
		<h4 style="text-align: center;">Add Discussion</h4>
		<div class="row">
			<form class="col s12"
				action="<%=blobstoreService.createUploadUrl("/ca/admin/discussion/create",
					options)%>"
				method="post" enctype="multipart/form-data">
				<c:choose>
					<c:when test="${ not empty formError}">
						<h5 style="color: white; background-color: red; padding: 2%;">
							<c:out value="${formError}" />
						</h5>
					</c:when>
					<c:when test="${ not empty formSuccess}">
						<h5 style="color: white; background-color: green; padding: 2%;">
							<c:out value="${formSuccess}" />
						</h5>
					</c:when>
				</c:choose>
				<div class="row">
					<div class="input-field col s12">
						<select name="group" id="group">
							<option value="" disabled selected>Select a group type</option>
							<option value="0">Collection</option>
							<option value="1">Community</option>
						</select> <label>Add discussion to a</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<select name="group-name" id="group-name" class="browser-default" disabled="disabled" >
							<option value="" disabled selected>Select the group name</option>
							<c:forEach var="item" items="${communityMap}">
								<option value="${item.key}">${item.value}</option>
							</c:forEach>
						</select> 
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<select name="unit-name" id="unit-name" class="browser-default" disabled="disabled">
							<option value="" disabled selected>Select the unit name</option>
							<c:forEach var="item" items="${unitMap}">
								<option value="${item.key}">${item.value}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				
				<div class="row">
					<div class="input-field col s12">
						<input id="title" type="text" class="validate"
							name="title"> <label for="title"
							data-error="wrong" data-success="right">Discussion title</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<input id="tags" type="text" class="validate" name="tags"
							data-role="materialtags"> <label for="tags"
							data-error="wrong" data-success="right">Tags</label>
					</div>
				</div>
				
				<div class="row">
					<div class="input-field col s12">
						<select name="format">
							<option value="" disabled selected></option>
							<option value="0">News</option>
							<option value="1">Post</option>
						</select> <label>Format</label>
					</div>
				</div>
				
				<div class="row">
					<div class="input-field col s12">
						<textarea id="textarea1" class="materialize-textarea"
							name="body"></textarea>
						<label for="textarea1">Body</label>
					</div>
				</div>
				<div class="row">
					<div class="file-field input-field col s12">
						<div class="btn">
							<span>File</span> <input type="file" name="image">
						</div>
						<div class="file-path-wrapper">
							<input class="file-path validate" type="text">
						</div>
					</div>
				</div>
				<br /> <br />
				<div class="row">
					<div class="col s6">
						<button class="btn waves-effect waves-light" type="submit"
							name="action">
							Submit <i class="material-icons right">send</i>
						</button>
					</div>
				</div>


			</form>
		</div>
	</div>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/materialize.min.js"></script>
	<script src="/js/materialize-tags.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('select').material_select();
			
			$("#group-name").on("change", function() {
				var x = $("#group").val();
				if(x=="1") {
					var y = $(this).val();
					$.ajax({
					url : "/admin/get-unit-ajax",
					dataType : "json",
					data : {
						"webkey" : y 
					}, 
					success : function(data) {
						console.log(data);
						 $("#unit-name").find('option').remove();
						 $("#unit-name").append('<option disabled selected>Select the unit name</option>')
						for(i = 0; i < data.length; i++) {
							$("#unit-name").append($("<option></option>").attr("value",data[i].propertyMap.id).text(data[i].propertyMap.name.value));;
				
						}
						$("#unit-name").prop('disabled', false);
						
						
					}
					});
				}
			});
			
			$("#group").on('change', function() {
				$.ajax({
					url : "/admin/get-ca-communities-ajax",
					dataType : "json",
					data : {
						"val" : $(this).val()
					},
					success : function(data) {
						 $("#group-name").find('option').remove();
						 $("#group-name").append('<option disabled selected>Select the group name</option>')
						for(i = 0; i < data.length; i++) {
							$("#group-name").append($("<option></option>").attr("value",data[i].propertyMap.id).text(data[i].propertyMap.webkey.value));;
				
						}
						$("#group-name").prop('disabled', false);
						
						
					}
					
				});
			});
		});
	</script>
</body>
</html>