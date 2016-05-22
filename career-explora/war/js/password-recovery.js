$(document).ready(
		function() {
			var msgDiv = $("#reg-error-div");
			var $username = $("#username");
			$username.focus(function() {
				
				msgDiv.removeClass("alert alert-danger");
				msgDiv.text("");
				$(this).removeClass("reg-input-error");
			});
			
			$("#send-code").click(
					function(e) {
						e.preventDefault();
						var myForm = $("#password-recovery-form");
						msgDiv.removeClass("alert alert-danger");
						msgDiv.addClass("alert alert-success");
						msgDiv.text("checking...");
						var jqXHR = $.post("/passwordrecovery",
								myForm.serialize()).done(function(data) {
									msgDiv.text("redirecting...");
									window.location.assign(data);
						}).fail(function(jqXHR, status, errorThrown) {
							msgDiv.removeClass("alert alert-success");
							
							$username.addClass("reg-input-error");
							msgDiv.addClass("alert alert-danger");
							msgDiv.text(jqXHR.statusText);
						});
					});
		});