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
<title>Add Community</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/style/materialize.min.css">
<link rel="stylesheet" href="/style/materialize-tags.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>
<body>
	<div class="container"
		style="width: 60%; border: 1px solid gray; padding: 3%; margin-top: 4%;">
		<h4 style="text-align: center;">Add Community/Collection</h4>
		<div class="row">
			<form class="col s12"
				action="<%=blobstoreService.createUploadUrl("/ca/admin/community/create",
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
						<input id="community-name" type="text" class="validate"
							name="name"> <label for="community-name"
							data-error="wrong" data-success="right">Community/Collection
							Name</label>
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
						<input id="short-desc" type="text" class="validate"
							name="short-desc"> <label for="short-desc"
							data-error="wrong" data-success="right">Short Description</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<textarea id="textarea1" class="materialize-textarea"
							name="long-desc"></textarea>
						<label for="textarea1">Long Description</label>
					</div>
				</div>
				<div class="row">
					<div class="file-field input-field col s6">
						<div class="btn">
							<span>File</span> <input type="file" name="image">
						</div>
						<div class="file-path-wrapper">
							<input class="file-path validate" type="text">
						</div>
					</div>
					<div class="col s6">
						<p>
							<input name="group" type="radio" id="test1" value="community" />
							<label for="test1">Community</label>
						</p>
						<p>
							<input name="group" type="radio" id="test2" value="collection" />
							<label for="test2">Collection</label>
						</p>
					</div>
				</div>
				<br />
				<br />
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
</body>
</html>