<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome <c:out value="${azureUser.firstName}" /> <c:out
		value="${azureUser.lastName}" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="/style/materialize.min.css">
<link rel="stylesheet" href="/style/materialize-tags.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet" href="/style/jquery.webui-popover.css">
<style type="text/css">
.post-icons {
	margin-left: 2%;
	margin-right: 2%;
	cursor: pointer
	
}
.material-icons.md-dark { color: rgba(0, 0, 0, 0.54); }
.material-icons.md-dark.md-inactive { color: rgba(0, 0, 0, 0.26); }
.ca-collection-text {
	line-height: 1;
	font-size: 1.0rem;
	padding-bottom: 3px
}

.ca-collection:hover,.active {
	background-color: #cb9dac;
	color: white
}

.ca-collection-icon {
	line-height: 1;
	font-size: 1.0rem;
	color: white;
}

.ca-collection {
	padding: 2% !important;
	cursor: pointer;
}

.ca-collection i {
	margin-right: 25px;
	margin-left: 25px;
	padding: 2%;
	background-color: red
}

main {
	padding-left: 260px;
}

.modal {
	width: 35%;
	max-height: 100% !important;
	overflow-y: hidden !important;
}

@media only screen and (max-width : 1200px) {
	.modal {
		width: 45%;
	}
}

@media only screen and (max-width : 992px) {
	main {
		padding-left: 0;
	}
	.modal {
		width: 55%;
	}
}

@media only screen and (max-width : 750px) {
	.modal {
		width: 75%;
	}
}

@media only screen and (max-width : 500px) {
	.modal {
		width: 95%;
	}
}
</style>
</head>
<body style="background-color: #f1f3f7">
	<%@ include file="/WEB-INF/main-nav.html"%>
	<%@ include file="/WEB-INF/home.html"%>

	<div id="template" style="display: none;">
		<div  class="row temp-panel" >
			<div class="card" style="padding-top: 20px">
				<div class="row valign-wrapper" style="margin-bottom: 10px;">
					<img class="circle" src="${welcomePage.profileImg}"
						style="width: 45px; height: 45px; margin: 0px 10px; margin-left: 30px">
					<span class="name"><c:out value='${welcomePage.firstName}' /> <c:out value='${welcomePage.lastName}' /></span>
				</div>

				<div class="card-image">
					<img class="responsive-img materialboxed">
				</div>
				<div class="card-content"></div>
				<div class="card-action">
					<a target="_blank" href="#"></a>
				</div>

			</div>
		</div>
	</div>

	<div id="modal1" class="modal card">

		<div class="row valign-wrapper" style="margin: 2%">
			<img class="circle" src="${welcomePage.profileImg}"
				style="width: 45px; height: 45px; margin: 0px 10px"> <span><c:out
					value='${welcomePage.firstName}' /> <c:out
					value='${welcomePage.lastName}' /></span>
		</div>
		<div style="max-height: 400px; overflow-y: scroll">
			<div class="card-image">
				<img id="new-img" class="responsive-img materialboxed" alt="" />
			</div>

			<div class="card-content"
				style="padding-top: 4px; padding-bottom: 4px;">

				<div class="row">
					<div class="input-field col s12" style="margin-top: 0px;">
						<textarea id="textarea1" class="materialize-textarea"
							style="padding-top: 4px; padding-bottom: 4px"></textarea>
						<label for="textarea1">Write something or ask a question</label>

					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<input id="tags" type="text" class="validate" name="tags"
							data-role="materialtags"> <label for="tags"
							data-error="wrong" data-success="right">Tag your
							discussion with keywords</label>
					</div>
				</div>
				<div class="row">
					<div class="file-field input-field">
						<div class="btn">
							<span> <i class="material-icons">camera_alt</i></span>
							<form id="img-input-form">
								<input id="img-input" type="file" name="image">
							</form>
						</div>
						<div class="file-path-wrapper">
							<input class="file-path validate" type="text"
								style="display: none">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<a href="#!"
				class=" modal-action modal-close waves-effect waves-green btn-flat">Cancel</a>
			<button id="submit-post" class="ca-btn-lg" style="">Post</button>
		</div>
	</div>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/materialize.min.js"></script>
	<script src="/js/jquery.webui-popover.js"></script>
	<script src="/js/materialize-tags.min.js"></script>
	<script type="text/javascript">
		function getUploadUrl(url, form) {
			$
					.ajax({
						url : "/getuploadurl",
						data : {
							"url" : url
						},
						success : function(data) {
							form.prop("action", data);
						},
						error : function() {
							alert("An error has occurred. Check your internet connection");
						}
					});
		}
		$(document).ready(
				function() {
					$(".comment").click(function() {
						var me = $(this);
						var y = me.closest(".card-action").find(".h-input").val();
						var comments = me.closest(".card-action").find(".no-comments");
						var progress=  me.closest(".card").find(".progress");
						var commentDiv =  me.closest(".card").find(".comment-div");
						$.ajax({
							url : "/azure/post/comments/get",
							data : {
								"webkey" : y
							},
							method : "POST",
							dataType : "json",
							beforeSend : function () {
								progress.show();
							},
							success : function (data) {
								alert("success");
							},
							error : function (xhr) {
								console.log("error");
							},
							complete : function () {
								progress.hide();
							}
						});
					});
					$(".like").click(function() {
						var me = $(this);
						var y = me.closest(".card-action").find(".h-input").val();
						var likes = me.closest(".card-action").find(".no-likes");
						$.ajax({
							url : "/azure/post/like",
							data : {
								"webkey" : y
							},
							method : "POST",
							dataType : "json",
							success : function (data) {
								likes.text(data);
								me.toggleClass("thumb_up thumb_down");
							},
							error : function (xhr) {
								console.log("error");
							}
						});
					});
					
					var x = 0;
					$(window).scroll(function() {
						var hT = $('#load-status').offset().top,
					       hH = $('#load-status').outerHeight(),
					       wH = $(window).height(),
					       wS = $(this).scrollTop();
						 if (wS > (hT+hH-wH)){
							 $('#load-status').fadeIn(13500);
							// alert("call server");
						 }
					});
					
					$("#submit-post").click(function() {
						var x = $("#textarea1").val();
						var z = $("#new-img").prop("src");
						var y = $("#tags").val();
						
						$.ajax({
							url : "/azure/post/save",
							data : {
								"post" : x,
								"tags" : y
							},
							method : "POST",
							success : function(data) {
								var clone = $("#template").find(".temp-panel").clone(true);
								clone.find(".card-content").text(x);
								if (z) {
									clone.find(".materialboxed").prop("src",z);
								}
								$("#f-col").prepend(clone);
								$('#modal1').closeModal();
							},
							error : function(xhr) {
								console.log("error");
							}
						});
					});

					$("#img-input").change(function() {
						$(this).closest("form").submit();
					});

					$("#img-input-form").submit(function(e) {
						e.preventDefault();
						var x = $(this).prop("action");
						$.ajax({
							url : x,
							data : new FormData(this),
							method : "POST",
							processData : false,
							contentType : false,
							success : function(data) {
								console.log(data);
								$("#new-img").prop("src", data);
							},
							error : function() {
								alert("jbjbj");
							}
						});
					});

					$(".button-collapse").sideNav();
					$('.materialboxed').materialbox();
					$('.modal-trigger').leanModal(
							{
								ready : function() {
									var url = getUploadUrl(
											"/azure/post/image/new",
											$("#img-input-form"));

								}
							});
				});
	</script>
</body>
</html>