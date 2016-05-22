$(document).ready(
		function() {

			$("#continue").click(
					function(event) {
						event.preventDefault();
						var ok = validateCode();
						var myForm = $("#confirmation-code-form");
						if (ok) {
							var msgDiv = $("#reg-error-div");
							msgDiv.removeClass("alert alert-danger");
							msgDiv.addClass("alert alert-success");
							msgDiv.text("verifying...");
							var jqxhr = $.post("/confirmcode",
									myForm.serialize()).done(function(data) {
								msgDiv.text("redirecting...");
								window.location.assign(data);
							}).error(function(jqXHR, status, errorThrown) {
								msgDiv.removeClass("alert alert-success");
								msgDiv.addClass("alert alert-danger");
								msgDiv.text(jqXHR.statusText);
							});
						}
					});
		});

function validateCode() {
	var $input = $("#confirmation-code");

	if (required($input)) {
		regInputOk($input);
	} else {
		regInputBad($input, "Please enter your confirmation code.");
		$input.select();
		return false;
	}

	if (allNumeric($input)) {
		regInputOk($input);
	} else {
		regInputBad($input, "You entered a wrong confirmation code.");
		$input.select();
		return false;
	}

	if (lengthRange($input, 6, 6)) {
		regInputOk($input);
	} else {
		regInputBad($input, "You entered a wrong confirmation code.");
		$input.select();
		return false;
	}
	
	return true;
}