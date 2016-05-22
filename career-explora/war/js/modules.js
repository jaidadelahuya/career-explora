function initModals() {

	var url = $("#picture-url").val();
	var username = $("#user-first-name").val() + " "
			+ $("#user-last-name").val()

	$('#start-cbt').on('click', function(event) {

		var time = validateTime($('#time'));
		var question = validateQuestions($('#questions'));
		var pass = validatepassMark($('#pass-percentage'));

		if (time && question && pass) {
			openCbtWindow(url, username);
		}

	});

	$('.start-personal-style').on('click', function() {
		openPersonalStyleWindow(id, url, username);
	});

	$('.start-career-cluster-test').on('click', function() {
		var test = $(this).prop('value');
		openCareerClusterTestWindow(test, id, url, username);
	});

	$(".start-mit").click(function() {
		openMultipleIntelligenceTestWindow(id, url, username);
	});

	$(".start-career-values").click(function() {
		openCareerValuesWindow(id, url, username);
	});

	$('.start-talent-test').on('click', function() {
		var test = $(this).prop('value');
		openTalentHuntWindow(test, id, url, username);
	});

	$('.start-skill-builder').on('click', function() {
			openSkillBuilderWindow(id, url, username);
	});
}

$(document).ready(function() {
	
	 $('[data-toggle="tooltip"]').tooltip();
	
	$("#background-img").mouseenter(function() {
		$("#cover-div").show();
	});

	$(".date-picker").datepicker({
		changeMonth : true,
		changeYear : true
	});

	$("#modals").load("/pages/partials/modals.html", function() {
		console.log("loaded");
		initModals();

	});

	$('#time').on('keyup', function() {
		validateTime($(this));
	});

	$('#questions').on('keyup', function() {
		validateQuestions($(this));
	});

	$('#pass-percentage').on('keyup', function() {
		validatepassMark($(this));
	});

	$("#imginfo").mouseenter(function() {
		addPopOver();
	});

	$('.skill-builder-home').on('click', function() {
		$('#skill-builder-dialog').modal();
	});

	$('.career-cluster-home').on('click', function() {
		$('#career-cluster-dialog').modal();
	});

	$('.personal-style-home').on('click', function() {
		$('#personal-style-dialog').modal();
	});

	$('.cbt-home').on('click', function() {
		$('#cbt-dialog').modal({
			keyboard : false
		});
	});

	$('#cbt-dialog').on('shown.bs.modal', function() {
		$('#subject').focus();

	});

	$('.mit-home').on('click', function() {
		$('#multiple-intelligence-dialog').modal();
	});

	$('.talent-home').on('click', function() {
		$('#talent-dialog').modal();
	});

	$('.career-values-home').on('click', function() {
		$('#career-values-dialog').modal();
	});

	$(".panel-img").mouseover(function() {

		$(this).parent().find(".module-footer").slideDown('fast');
	});

	$(".panel-img").mouseout(function() {

		$(this).parent().find(".module-footer").slideUp('fast');
	});

	$('#change-cover-picture').on('click', function() {
		changeCoverPicture(upLoadUrl);
	});

	$("#edit-profile-name").on('click', function() {

		var isValid = validateDate($("#date-of-birth"), $("#dob-error-div"));
		var dob = $("#date-of-birth").text();

		if (!dob || isValid) {

			editProfileName();
		}

	});

});


function editProfileName() {
	$("#edit-name-form").waitMe({
		effect : 'ios',
		color : 'orange',
		sizeW : '15',
		sizeH : '15'
	});
	saveProfileName();

}

function showProfile() {
	$("#view-profile-form").submit();
}

function addPopOver() {

	$('#imginfo').webuiPopover({
		placement : 'auto',
		width : 'auto',
		height : 'auto',
		trigger : 'click',
		arrow : true,
		content : $('#popover-content').html(),
		closeable : false,
		padding : true,
		type : 'html'
	});
}

function logout() {

	
	$.ajax({
		url : '/azure/logout',
		type : 'GET',
		success : function(data) {
		
			window.location.href = '/index.jsp';
			FB.getLoginStatus(function(response) {
				if (response.status === 'connected') {
					FB.logout(function() {
						
					});
				}
			});
		},
		error : function(jqXHR, status, errorThrown) {
			alert("You have not been logged out. Try again");
		}
	});
}

function validateTime(elem) {
	var text = elem.val();
	var err = $("#time-error-div");
	var isPInt = false
	if ($.isNumeric(text) && Math.floor(text) == text && text > 0) {
		isPInt = true;
	}

	if (text == null || text == "" || !isPInt) {
		elem.closest('div').addClass('has-error');
		err.text('enter a positive integer greater than 0');
		return false;
	} else {
		elem.closest('div').removeClass('has-error');
		err.text('');
		return true;
	}

}

function validateQuestions(elem) {
	var text = elem.val();
	var err = $("#questions-error-div");
	err.text('enter a positive integer not greater than 50');
	var isPInt = false;
	if ($.isNumeric(text) && Math.floor(text) == text && text > 0 && text < 51) {
		isPInt = true;
	}

	if (text == null || text == "" || !isPInt) {
		elem.closest('div').addClass('has-error');
		err.text('enter a positive integer not greater than 50');
		return false;
	} else {
		elem.closest('div').removeClass('has-error');
		err.text('');
		return true;
	}

}

function validatepassMark(elem) {
	var text = elem.val();
	var err = $("#pass-error-div");
	var isPInt = false;
	if ($.isNumeric(text) && Math.floor(text) == text && text > 0 && text < 101) {
		isPInt = true;
	}

	if (text == null || text == "" || !isPInt) {
		elem.closest('div').addClass('has-error');
		err.text('enter a positive integer not greater than 100');
		return false;
	} else {
		elem.closest('div').removeClass('has-error');
		err.text('');
		return true;
	}
}

function openPersonalStyleWindow(userid, imgUrl, username) {

	var wind = window.open("/pages/personal-style.html", 'Personal Style Test',
			"toolbar=yes, scrollbars=yes, fullscreen=1");
	wind.userid = userid;
	wind.imgUrl = imgUrl;
	wind.username = username;
	$('#personal-style-dialog').modal('hide');
}

function openSkillBuilderWindow(userid, imgUrl, username) {

	var wind = window.open("/pages/skill-builder.html", 'Skill Builder',
			"toolbar=yes, scrollbars=yes, fullscreen=1");
	wind.userid = userid;
	wind.imgUrl = imgUrl;
	wind.username = username;
	$('#skill-builder-dialog').modal('hide');
}

function openTalentHuntWindow(test, userid, imgUrl, username) {

	var wind = window.open("/pages/talent-hunt.html", test,
			"toolbar=yes, scrollbars=yes, fullscreen=1");
	wind.userid = userid;
	wind.imgUrl = imgUrl;
	wind.username = username;
	$('#talent-dialog').modal('hide');
}

function openCareerClusterTestWindow(test, userid, imgUrl, username) {

	var wind = window.open("/pages/career-clusters.html", test,
			"toolbar=yes, scrollbars=yes, fullscreen=1");
	wind.userid = userid;
	wind.imgUrl = imgUrl;
	wind.username = username;
	$('#career-cluster-dialog').modal('hide');
}

function openCareerValuesWindow(userid, imgUrl, username) {

	var wind = window.open("/pages/career-values.html", "Career Values Test",
			"toolbar=yes, scrollbars=yes, fullscreen=1");
	wind.userid = userid;
	wind.imgUrl = imgUrl;
	wind.username = username;
	$('#career-values-dialog').modal('hide');
}

function openMultipleIntelligenceTestWindow(userid, imgUrl, username) {
	var wind = window.open("/pages/multiple-intelligence.html",
			"Multiple-Intelligence-Test",
			"toolbar=yes, scrollbars=yes, fullscreen=1")
	wind.userid = userid;
	wind.imgUrl = imgUrl;
	wind.username = username;
	$("#multiple-intelligence-dialog").modal('hide');
}

function openCbtWindow(src, username) {

	var $sub = $('#subject').val();
	var $time = $('#time').val();
	var $questions = $('#questions').val();
	var $passmark = $('#pass-percentage').val();
	var imageUrl = src;
	var user = username;
	var win = window.open("/pages/cbt.html", "CBT",
			"toolbar=yes, scrollbars=yes, fullscreen=1");
	win.subject = $sub;
	win.time = $time;
	win.questions = $questions;
	win.passmark = $passmark;
	win.imageUrl = imageUrl;
	win.username = user;
	$('#cbt-dialog').modal('hide');

}

function saveProfileName() {
	var myForm = $("#edit-name-form");
	var fName = $('#first-name').val();
	var lName = $('#last-name').val();
	var mName = $('#middle-name').val();
	var dob = $('#date-of-birth').val();
	var $diag = $('#profile-name-dialog');
	var jqxhr = $.post("/azure/editname", myForm.serialize(), function() {

	}).done(function(data) {
		$('#profile-name').text(data.firstName + " " + data.lastName);
		$('#user-name').text(data.firstName + " " + data.lastName);
		$("#pop-user-first-name").text(data.firstName);
		$("#pop-user-last-name").text(data.lastName);

	}).fail(function(jqXHR, textStatus, errorThrown) {
		myForm.waitMe('hide');
		alert("Could not edit your profile. Try again later.");
	}).always(function() {
		myForm.waitMe('hide');
		$diag.modal('hide');
		clearForm(myForm);
		$('#first-name').val(fName);
		$('#last-name').val(lName);
		$('#middle-name').val(mName);
		$('#date-of-birth').val(dob);
	});
}


