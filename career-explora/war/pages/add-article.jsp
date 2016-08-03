<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<title>Add article</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/style/materialize.min.css">
<link rel="stylesheet" href="/style/materialize-tags.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
	<br>
	<br>
	<br>

	<div class="container">
		<div class="row">
			<form
				action="<%=blobstoreService.createUploadUrl("/add-article", options)%>"
				method="post" enctype="multipart/form-data">
				<div class="col-md-12">
					<h3>Create New Article</h3>
				</div>
				<div class="col-md-8">
					<input type="text" value="Amsterdam,Washington,Sydney,Beijing,Cairo"data-role="materialtags" />
					<div class="form-group">
						<label for="title">Title:</label> <input class="form-control" data-role="materialtags"
							name="title">
					</div>
					<div class="form-group">
						<label>Source Name:</label> <input
							class="form-control" name="source">
					</div>
					<div class="form-group">
						<label>External Link:</label> <input
							class="form-control" name="link">
					</div>
					<div class="form-group">
						<label>Tags:(separate each tag with ;)</label> <input
							class="form-control" name="tags">
					</div>
					
					
					<div class="form-group">
						<label for="post">Post:</label>
						<textarea name="post" rows="10" class="form-control tiny"></textarea>
					</div>


				</div>

				<div class="col-md-4">
					<h4>Categories</h4>
					<div class="well">
						<div class="form-group">
							<select name="category" class="form-control">
								<option value="" selected="selected" disabled="disabled">Choose
									Category</option>
								<option value="1">Accounting</option>
								<option value="2">Biology</option>
								<option value="3">Chemistry</option>
								<option value="4">Commerce</option>
								<option value="5">CRK</option>
								<option value="6">English</option>
								<option value="7">Mathematics</option>
								<option value="8">Social Science</option>
								<option value="9">News</option>
								<option value="10">Physics</option>
								<option value="11">Civic Education</option>
								<option value="12">Economics</option>
								<option value="13">Computer Science</option>
								<option value="14">History</option>
								<option value="15">Literature</option>
								<option value="16">Government/option>
								<option value="17">Mathematics</option>
								<option value="100">General</option>
							</select>
						</div>
					</div>
					<h4>Code for embedded video</h4>
					<div class="well">
						<div class="form-group">
							<textarea rows="4" class="form-control" name="embedded-code"></textarea>
						</div>
					</div>
					<h4>Featured Image</h4>
					<div class="well">
						<div class="form-group">
							<input type="file" name="image" class="form-control"> <i>Max
								size = 2MB</i>
						</div>
					</div>
				</div>
				<div class="col-sm-12">
					<div class="form-group">
						<input class="btn btn-success" type="button" value="Save">
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
	<script src="/js/materialize.min.js"></script>
	<script src="/js/materialize-tags.min.js"></script>
	<script>
		tinymce.init({
			selector : '.tiny'
		});
	</script>
	
</body>
</html>