/*function checkLoginState() {

	FB.getLoginStatus(function(response) {
		statusChangeCallback(response);
	});
}

function statusChangeCallback(response) {

	if (response.status === 'connected') {
		getPhotoData(response);
	} else if (response.status === 'not_authorized') {
		getPhotoData(response);
	} else {

	}
}*/

function getPhotoData(resp) {

	FB
			.api(
					'/',
					'POST',
					{
						batch : [
								{
									method : 'GET',
									relative_url : 'me?fields=cover&include_headers=false'
								},
								{
									method : "GET",
									relative_url : "me/picture?type=normal&redirect=false&height=250&width=250&include_headers=false"
								} ]
					}, function(response) {
						var x = response[0].body;
						x = JSON.parse(x);
						var coverSource = null;
						if (x.hasOwnProperty("cover")) {
							coverSource = x.cover.source;
						}
						var a = response[1].body;
						a = JSON.parse(a);
						var pictureUrl = null;
						if (a.hasOwnProperty("data")) {
							pictureUrl = a.data.url;
						}

						$("#picture").val(pictureUrl);
						$("#cover").val(coverSource);
						callServer(resp);
					});
}

function login() {

	FB.login(function(response) {
		if (response.authResponse) {

			getPhotoData(response);
		} else {
			console.log("user cancelled action");
		}
	}, {
		scope : 'public_profile,email',
		return_scopes : true
	});
}

function callServer(response) {

	$("#access").val(response.authResponse.accessToken);
	$("#name").val(response.authResponse.userID);

	$("#login-form").submit();
}

$(document)
		.ready(
				function() {
					
					$("#new-account-button").click(function() {
						window.location.assign("/sign-up");
					});

					$(".login-button").click(function() {
						var $form = $(this).closest('form');
						$form.prop('action', "/azure/login");
						$form.submit();
					});

					$(".fblogin").click(function() {
						login();
					});

					var userid = $("#userid");
					$("#use-phone").click(function() {
						userid.val("");
						userid.prop('placeholder', 'Mobile number');
						regInputOk(userid);
					});
					$("#use-email").click(function() {
						userid.val("");
						userid.prop('placeholder', 'E-Mail');
						regInputOk(userid);
					});

					$("#sign-up")
							.click(
									function() {

										var ok = validateRegistrationForm();

										if (ok) {
											var msgDiv = $("#reg-error-div");
											msgDiv
													.removeClass("alert alert-danger");
											msgDiv
													.addClass("alert alert-success");
											msgDiv.text("processing...");
											var myForm = $("#register-user-form");
											var jqxhr = $
													.post("/registeruser",
															myForm.serialize(),function() {},'text')
													.done(
															function(data) {
																msgDiv
																		.text("redirecting...");
																window.location
																		.assign(data);
															})
													.error(
															function(jqXHR,
																	status,
																	errorThrown) {
																msgDiv
																		.removeClass("alert alert-success");
																msgDiv
																		.addClass("alert alert-danger");
																
																msgDiv
																		.html(jqXHR.responseText);
																
															});
										}
									});

					$("#first-name").blur(function() {
						validateFirstName($(this));
					});

					$("#last-name").blur(function() {
						validateLastName($(this));
					});

					$("#userid").blur(function() {
						var $usingUsername = $("#using-username").val();
						if (!$usingUsername) {
							validateUserId($(this));
						}

					});

					$("#pass1").blur(function() {
						validatePassword($(this));
					});

					$("#pass2").blur(function() {
						validatePassword2($(this));

					});

				});

function validateFirstName($input) {

	if (required($input)) {
		regInputOk($input);
	} else {
		regInputBad($input, "Please enter your first name.");
		$input.select();
		return false;
	}

	if (allLetter($input)) {
		regInputOk($input);

	} else {
		regInputBad($input, "Please enter a real name.");
		$input.select();
		return false;
	}

	return true;

}

function validateLastName($input) {
	if (required($input)) {
		regInputOk($input);
	} else {
		regInputBad($input, "Please enter your last name.");
		$input.select();
		return false;
	}

	if (allLetter($input)) {
		regInputOk($input);
	} else {
		regInputBad($input, "Please enter a real name.");
		$input.select();
		return false;
	}

	return true;
}

function validateUserId($input) {
	var usingPhone = $("#use-phone").prop('checked');

	if (usingPhone) {

		if (required($input)) {
			regInputOk($input);
		} else {
			regInputBad($input, "Please enter your mobile number.");
			$input.select();
			return false;
		}

		if (allNumeric($input)) {
			regInputOk($input);
		} else {
			regInputBad($input, "Your mobile number cannot contain alphabets.");
			$input.select();
			return false;
		}

		if (lengthRange($input, 11, 11)) {
			regInputOk($input);
		} else {
			regInputBad($input, "Your mobile number is not valid .");
			$input.select();
			return false;
		}

	} else {
		if (required($input)) {
			regInputOk($input);
		} else {
			regInputBad($input, "Please enter your e-mail address.");
			$input.select();
			return false;
		}

		if (validateEmail($input)) {
			regInputOk($input);
		} else {
			regInputBad($input, "Please enter a valid e-mail address.");
			$input.select();
			return false;
		}

	}

	return true;

}

function validatePassword($input) {
	if (required($input)) {
		regInputOk($input);
	} else {
		regInputBad($input, "Please enter a password.");
		$input.select();
		return false;
	}

	if (lengthRange($input, 7, 21)) {
		regInputOk($input);
	} else {
		regInputBad($input,
				"Your password should have between 7 to 21 characters.");
		$input.select();
		return false;
	}

	if (checkPassword($input)) {
		regInputOk($input);
	} else {
		regInputBad($input,
				"Your password should contain at least a digit and a special character.");
		$input.select();
		return false;
	}

	return true;
}

function validatePassword2($pass2) {

	if (required($pass2)) {
		regInputOk($pass2);
	} else {
		regInputBad($pass2, "Please re-enter your password.");
		$pass2.select();
		return false;
	}
	return true;
}

function matchPasswords($input, $pass2) {
	var p1 = $input.val();
	var p2 = $pass2.val();

	if (p1 === p2) {
		regInputOk($input);
		regInputOk($pass2);
	} else {
		regInputBad($input, "Passwords do not match.");
		regInputBad($pass2, "Passwords do not match.");
		$input.select();
		return false;
	}

	return true;
}

function validateRegistrationForm() {
	// validating first name
	var $input = $("#first-name");
	var ok = validateFirstName($input);
	if (!ok) {
		return false;
	}

	// validating last name
	$input = $("#last-name");
	ok = validateLastName($input);
	if (!ok) {
		return false;
	}

	var $usingUsername = $("#using-username").val();

	if ($usingUsername) {
		// do nothing for now. validation is on server
	} else {
		// are we using phone?
		$input = $("#userid");
		ok = validateUserId($input);
		if (!ok) {
			return false;
		}
	}

	// validating pass1
	$input = $("#pass1");
	ok = validatePassword($input);
	if (!ok) {
		return false;
	}

	// validating pass2
	$pass2 = $("#pass2");
	ok = validatePassword2($pass2);
	if (!ok) {
		return false;
	}

	ok = matchPasswords($input, $pass2);
	if (!ok) {
		return false;
	}

	var f = $("#female");
	var m = $("#male");

	if (f.prop("checked") | m.prop("checked")) {
		regInputOk(f);
		regInputOk(m);
	} else {
		regInputBad(f, "Please select your gender.");
		regInputBad(m, "Please select your gender.");
		return false;
	}

	return true;

}
