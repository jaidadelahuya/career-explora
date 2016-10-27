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
<link rel="stylesheet" href="/style/main.css">
<link rel="stylesheet" href="/style/media-queries.css">

<style type="text/css">
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
		<div class="row temp-panel">
			<div class="card" style="padding-top: 20px">
				<div class="row" style="margin-bottom: 10px;">
					<img class="circle a-img" src="${welcomePage.profileImg}"
						style="width: 45px; height: 45px; margin: 0px 10px; margin-left: 30px; float: left">
					<div style="padding: 1%;">
						<span class="a-name"><c:out
								value='${welcomePage.firstName}' /> <c:out
								value='${welcomePage.lastName}' /></span><a class="more"
							style="color: #59983b"><i class="material-icons right">more_vert</i></a><br>
						<span class="date"
							style="font-style: italic; font-family: calibri; color: #983b59">Just
							now</span>
					</div>
					<div class="card-content"></div>
					<div class="card-image">
						<img class="responsive-img materialboxed">
					</div>
					<div class="card-action" style="overflow: auto; background-color: #f4ebee">
						<a class="source" target="_blank" href="#"></a> <input
							class="h-input" type="hidden"> <i
							class="material-icons md-dark right post-icons comment">
							comment</i><span class="right no-comments">0</span> <i
							class="material-icons md-dark right post-icons like">
							thumb_up </i><span class="right no-likes">0</span>
					</div>
					<div class="progress" style="display: none">
						<div class="indeterminate"></div>
					</div>
					<div class="comment-div" style="display: none; overflow: auto;">
						<div class="all-comments"
							style="max-height: 300px; overflow-y: auto; border-top: 1px solid #dadada; border-bottom: 1px solid #dadada"></div>
						<div class="new-comment">
							<div class="row">
								<div class="col s12">
									<img alt="" src="${welcomePage.profileImg}"
										style="height: 45px; width: 45px; margin: 2%; float: left"
										class="image circle">
									<div>
										<h6
											style="margin-bottom: 4px; margin-top: 25px; font-weight: bold;">
											<span class="comment-author-name"><c:out
													value='${welcomePage.firstName}' /> <c:out
													value='${welcomePage.lastName}' /></span>
										</h6>
										<form>
											<input class="h-input" type="hidden" value="${item.webkey}"
												name="web-key">
											<textarea class="materialize-textarea comment-text" rows="1"
												placeholder="Write a comment" name="comment"
												style="margin: 0px; padding: 0px; border-bottom: none;"></textarea>
										</form>
									</div>
									<div style="margin-bottom: 10px">
										<span class="right post-comment"
											style="font-weight: bold; cursor: pointer;">POST</span>
									</div>
								</div>

							</div>
						</div>
					</div>

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
		<div style="max-height: 400px; overflow-y: auto">


			<div class="card-content"
				style="padding-top: 10px; padding-bottom: 0px">

				<div class="row">
					<div class="input-field col s12" style="margin-top: 0px;">
						<textarea id="textarea11" class="materialize-textarea"
							style="padding-top: 1px; padding-bottom: 1px; margin-bottom: 2px"></textarea>
						<label for="textarea11">Write something or ask a question</label>

					</div>
				</div>
			</div>

			<div class="card-image">
				<div style="padding-right: 25px">
					<span class="right" id="remove-image"
						style="display: none; font-family: calibri; color: #983b59; cursor: pointer;">remove</span>
				</div>
				<img id="new-img" class="responsive-img" alt="" />
			</div>
			<div class="card-content"
				style="padding-top: 0px; padding-bottom: 0px">
				<div class="row" style="margin-bottom: 0px">
					<div class="file-field input-field">
						<div>
							<span> <i style="cursor: pointer;" class="material-icons">camera_alt</i></span>
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
		<div class="modal-footer" style="margin: 0px">
			<a href="#!"
				class=" modal-action modal-close waves-effect waves-green btn-flat">Cancel</a>
			<a id="submit-post"
				class=" modal-action modal-close waves-effect waves-green btn-flat">Post</a>
		</div>
	</div>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/materialize.min.js"></script>
	<script src="/js/jquery.webui-popover.js"></script>
	<script src="/js/post-actions.js"></script>
	<script type="text/javascript">
		function clonePost(aImg, aName, pImg, pText, likes, comments, date,
				source, webkey) {
			var clone = $("#template").find(".temp-panel").clone(true);
			clone.find(".a-img").prop("src", aImg);
			clone.find(".a-name").text(aName);
			clone.find(".nolikes").text(likes);
			clone.find(".no-comments").text(comments);
			clone.find(".date").text(date);
			clone.find(".h-input").val(webkey);
			clone.find(".more").prop("href",
					"/azure/post/single/get?bean=welcome&webkey=" + webkey);
			if (source) {
				clone.find(".source").text("Source");
				clone.find(".source").prop("href", source);
			}
			clone.find(".card-content").text(pText);
			if (pImg) {
				clone.find(".materialboxed").prop("src", pImg);
			}
			return clone;
		}

		function getMoreDiscussions() {
			$.ajax({
				url : "/azure/post/get",
				dataType : "json",
				success : function(data) {
					console.log(data);
					for (i = 0; i < data.length; i++) {
						var val = data[i];
						var clone = clonePost(val.authorImage, val.author,
								val.pictureUrl, val.snippet, val.likes,
								val.comments, val.postDate, val.link,
								val.webkey);
						if (i % 2 == 0) {
							$("#s-col").append(clone);
						} else {
							$("#f-col").append(clone);
						}
					}
				},
				error : function(xhr) {
					console.log(xhr);
				},
				complete : function() {
					console.log("done");

				}
			});
		}
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

		$(document)
				.ready(
						function() {

							$(window).scroll(function() {
								var iScrollPos = 300;
								var y = $("#f-col").height();
								var icurScrollpos = $(this).scrollTop();

								console.log(y);

								console.log(icurScrollpos);
								if (icurScrollpos > y) {
									getMoreDiscussions();

								}
							});

							

							$("#submit-post")
									.click(
											function(e) {
												e.preventDefault();
												var x = $("#textarea11").val();
												var z = $("#new-img").prop(
														"src");
												var y = $("#tags").val();

												$
														.ajax({
															url : "/azure/post/save",
															data : {
																"post" : x,
																"tags" : y
															},
															method : "POST",
															success : function(
																	data) {
																var clone = $(
																		"#template")
																		.find(
																				".temp-panel")
																		.clone(
																				true);
																clone
																		.find(
																				".card-content")
																		.text(x);
																if (z) {
																	clone
																			.find(
																					".materialboxed")
																			.prop(
																					"src",
																					z);
																}
																$("#f-col")
																		.prepend(
																				clone);
																$('#modal1')
																		.closeModal();
															},
															error : function(
																	xhr) {
																console
																		.log("error");
															}
														});
											});

							$("#img-input").change(function() {
								$(this).closest("form").submit();
							});

							$("#img-input-form")
									.submit(
											function(e) {
												e.preventDefault();
												var x = $(this).prop("action");
												$
														.ajax({
															url : x,
															data : new FormData(
																	this),
															method : "POST",
															processData : false,
															contentType : false,
															success : function(
																	data) {
																console
																		.log(data);
																$("#new-img")
																		.prop(
																				"src",
																				data);
																getUploadUrl(
																		"/azure/post/image/new",
																		$("#img-input-form"));
																$(
																		"#remove-image")
																		.show();
															},
															error : function() {
																alert("We could not upload your image.");
															}
														});
											});

							$("#remove-image")
									.click(
											function() {
												$("#new-img").prop("src", "");
												getUploadUrl(
														"/azure/post/image/new",
														$("#img-input-form"));
												var img = $('<img id="new-img" class="responsive-img">');
												$(this).closest(".card-image")
														.find("img")
														.replaceWith(img);
												$(this).hide();
											});

							$(".button-collapse").sideNav();
							$('.materialboxed').materialbox();
							$('.modal-trigger')
									.leanModal(
											{
												ready : function() {
													var url = getUploadUrl(
															"/azure/post/image/new",
															$("#img-input-form"));

												},
												complete : function() {
													var img = $('<img id="new-img" class="responsive-img">');
													$("#new-img").replaceWith(
															img);
													$("#textarea11").val("");
													$("#remove-image").hide();
												}
											});
						});
	</script>
</body>
</html>