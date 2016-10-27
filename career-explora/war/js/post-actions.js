function updateComment(data, commentDiv) {
	for (i = 0; i < data.length; i++) {
		var sec = $("#cloneable-comment").clone();
		sec.css("display", "block");
		sec.find('img').prop("src", data[i].authorImage);
		sec.find(".author-name").text(data[i].author);
		sec.find(".comment").text(data[i].snippet);
		sec.find(".date-posted").text(data[i].postDate);
		commentDiv.find(".all-comments").append(sec);
	}
}
function updateComment1(img, name, comment, commentDiv) {

	var sec = $("#cloneable-comment").clone();
	sec.css("display", "block");
	sec.find('img').prop("src", img);
	sec.find(".author-name").text(name);
	sec.find(".comment").text(comment);
	sec.find(".date-posted").text("now");
	var main = commentDiv.find(".all-comments");
	main.append(sec);
	main.animate({
		scrollTop : main[0].scrollHeight
	});

}
$(document).ready(function() {

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
			success : function(data) {
				likes.text(data);
				var xyz = me.html();
				if (xyz.includes("thumb_up")) {
					me.html("thumb_down");
				} else {
					me.html("thumb_up");
				}
			},
			error : function(xhr) {
				console.log("error");
			}
		});
	});
	$(".post-comment").click(function() {
		var commentDiv = $(this).closest(".comment-div");
		var form = commentDiv.find("form");
		var img = commentDiv.find("img").prop("src");
		var name = commentDiv.find(".comment-author-name").text();
		var comment = commentDiv.find(".comment-text");
		var commentText = comment.val();
		console.log(comment);
		$.ajax({
			url : "/azure/post/comment",
			data : form.serialize(),
			method : "post",
			beforeSend : function() {
				console.log("sending");
			},
			success : function(data) {
				updateComment1(img, name, commentText, commentDiv);
				console.log(comment);
			},
			error : function(xhr) {
				console.log(xhr);
			},
			complete : function() {
				comment.val("");
			}
		});
	});
	$(".comment").click(function() {
		var me = $(this);
		var y = me.closest(".card-action").find(".h-input").val();
		var comments = me.closest(".card-action").find(".no-comments");
		var card = me.closest(".card");
		var progress = card.find(".progress");
		var commentDiv = card.find(".comment-div");
		$.ajax({
			url : "/azure/post/comments/get",
			data : {
				"webkey" : y
			},
			dataType : "json",
			beforeSend : function() {
				progress.show();
			},
			success : function(data) {
				if (data == "/") {
					window.location.assign(data);
				} else {
					updateComment(data, commentDiv);
					commentDiv.show();
				}
			},
			error : function(xhr) {
				console.log("error");
			},
			complete : function() {
				progress.hide();
				me.off();
			}
		});
	});
});