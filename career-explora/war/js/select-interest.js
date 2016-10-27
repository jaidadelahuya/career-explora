$(document).ready(function() {
	$('select').material_select();

	$(".default-img-btn").click(function(e) {
		e.preventDefault();
		$(this).closest("form").submit();
	});

	$(".default-img-form").on("submit", function(e) {
		e.preventDefault();
		var form = $(this);
		var x = form.prop("action");
		var y = form.find(".h-input").val();
		var msgD = $(this).find(".msg-div");
		$.ajax({
			url : x,
			data : form.serialize(),
			method : "POST",
			dataType : "json",
			success : function(data) {
				console.log(y);
				if (y == "profile") {

					$("#cover-picture-tab").removeClass("disabled");
					$('ul.tabs').tabs('select_tab', 'cover');
				} else if (y == "cover") {
					window.location.assign(data);
				}
			},
			error : function(xhr) {
				msgD.text(xhr.statusText);
				msgD.addClass("ca-alert ca-alert-danger");
			}
		});
	});

	$("#submit-mj").click(function(e) {
		e.preventDefault();
		var $form = $("#major-interest-form");
		var x = $form.prop("action");

		$.ajax({
			url : x,
			method : "POST",
			data : $form.serialize(),
			dataType : "json",
			success : function(data) {
				$("#profile-picture-form").prop('action', data);
				$("#profile-picture-tab").removeClass("disabled");
				$('ul.tabs').tabs('select_tab', 'profile');

			},
			error : function(xhr, statusText, str) {
				var msgD = $("#msg-div");
				msgD.text(xhr.statusText);
				msgD.addClass("ca-alert ca-alert-danger");

			},
		});
	});

	$(".upload-button").click(function(e) {
		e.preventDefault();
		$(this).closest("form").submit();
	});

	$(".upload-form").on("submit", function(e) {
		e.preventDefault();
		var x = $(this).prop("action");
		var y = $(this).find(".h-input").val();
		var butt = $(this).find(".upload-button");
		var msgD = $(this).find(".msg-div");
		$.ajax({
			url : x,
			type : 'POST',
			data : new FormData(this),
			processData : false,
			contentType : false,
			success : function(data) {
				if (y == "profile") {
					console.log($("#cover-picture-form").prop('action'));

					$("#cover-picture-form").prop('action', data);

					$("#cover-picture-tab").removeClass("disabled");
					$('ul.tabs').tabs('select_tab', 'cover');
					butt.prop("disabled", true);
				} else if (y == "cover") {
					window.location.assign(data);
				}
			},
			error : function(xhr, status, errorThrown) {
				msgD.text(xhr.statusText);
				msgD.addClass("ca-alert ca-alert-danger");
			}
		});

	});

});