var questionNumber = 1;
var currentNumber = 0;
var numberPerPage = 8;
var selectedStyles = [];
var testDate = null;

$(document).ready(function() {

	var userid = window.userid;
	var username = window.username;
	var imgUrl = window.imgUrl;
	var ps = null;

	$(".profile-img").attr('src', imgUrl);

	$.ajax({
		url : "/azure/getpersonalstyle",
		type : "GET",
		dataType : "Json",
		success : function(data) {

			ps = getPersonalStyles(data);
			startTest(ps, selectedStyles);

			$("#next").click(function() {
				nextClick(ps, selectedStyles);

			});

			$("#back").click(function() {
				backClick(ps, selectedStyles);
			});

			$("#finish").click(function() {
				$("#end-test-dialog").modal();
			});
			
			$('.submit-test').on('click', function () {
				$("#end-test-dialog").modal('hide');
				finishClick(ps, selectedStyles, userid, imgUrl, username);
			});
			
			
		},
		error : function() {
			alert('An error has occured try again in 30mins');
		},
		complete : function() {
			$("#back").prop("disabled", true);
		}
	});

});

function finishClick(ps, selectedStyles, userid, imgUrl, username) {
	
	$('.test-board-footer').hide();
	$('.heading').text('Personal Style Report');
	$("body").css('background-color','white');
	$("#cc-div-content")
	.load(
			'/pages/partials/personal-style.html',
			function() {

				$('#user-name').text(username);
				$('#today').text(testDate);
				$("#profile-image").attr('src', imgUrl);
				
				
				showStyles(selectedStyles);
				
				$('#save-div')
						.append(
								'<a id="print-doc" class="btn btn-primary btn-sm cont-button" style="margin: 1em;"><span class="glyphicon glyphicon-print"></span><span class="hidden-sm hidden-md"> Print</span></a>');
				$('#save-div')
						.append(
								'<button id="save-button" type="button" class="btn btn-primary btn-sm cont-button save-button"><span class="glyphicon glyphicon-save"></span><span class="hidden-sm hidden-md"> Save</span></button>');

				$('#print-doc').on('click', function() {
					window.print();
				});

				$('.save-button').on('click', function() {
					savePersonalStyle(selectedStyles);
				});
			});
}

function savePersonalStyle(selectedStyles) {
	var styles = JSON.stringify(selectedStyles);
	var hasMore = false;
	
	$.ajax({
		url: '/azure/savepersonalstyle',
		type: 'POST',
		data : {
			styles: styles,
			testDate : testDate
		},
		success : function(data) {
			$("#save-success-dialog").modal();
			$(".close-window").on('click', function() {
				$("#save-success-dialog").modal('hide');
				window.close();
			});
			styles = data.list;
			hasMore = data.hasMore;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(textStatus);
		},
		complete : function() {
			var ltn = $("#last-test-name",opener.document);
			ltn.text('PERSONAL STYLE');
			var ltd = $("#last-test-date",opener.document);
			$("#last-test-date-row",opener.document).show();
			ltd.text(testDate);
			
			var list = $("#personal-style-profile",opener.document);
			
			populateStylesList(styles, list, hasMore);
			
		}
	});
}

function showStyles(vs) {
	var a = '';
	var b = '';
	for(i=0; i < vs.length; i+=3 ) {
		a = vs[i+1];
		b = vs[i+2];
		
		if(a === undefined) {
			a = '';
		}
		if(b === undefined) {
			b = "";
		}
		$('#personal-style-list').append('<li class="list-group-item description to-pointer"><div class="row"><span class="col-xs-4 col-sm-4 text-center">'+vs[i]+'</span><span class="col-xs-4 col-sm-4 text-center">'+a+'</span><span class="col-xs-4 col-sm-4 text-center">'+b+'</span></div></li>');
	}
}


function loadPrevious(ps, selectedStyles) {
	var $table = $("#table");

	var mod = ps.length % numberPerPage;
	if (currentNumber == ps.length) {
		currentNumber = currentNumber - mod;
		questionNumber = questionNumber - mod;
	} else {
		currentNumber = currentNumber - numberPerPage;
		questionNumber = questionNumber - numberPerPage;
	}

	if (questionNumber < numberPerPage) {
		$("#back").prop('disabled', true);

	}

	var qn = questionNumber - 1;
	var cn = currentNumber - 1;
	

	for (i = 0; i < numberPerPage; i++) {
		

		$table
				.prepend("<tr><td><strong>"
						+ qn
						+ "</strong></td><td><span class='career-values-name'>"
						+ ps[cn].name
						+ "</td><td align='center'><input class='form-contrl user-choice' type='checkbox' name='choice' value='"
						+ ps[cn].name + "'/></td></tr>");
		
		qn--;
		cn--;

	}
	if (qn < numberPerPage) {
		$("#back").prop('disabled', true);

	}

	$(".user-choice").on('click', function() {
		selectedStyles = userChoiceClick($(this), selectedStyles);
	});
}


function backClick(ps, selectedStyles) {

	$("#next").prop("disabled", false);
	var $table = $("#table");
	$table.empty();
	loadPrevious(ps, selectedStyles);

	for (i = 0; i < selectedStyles.length; i++) {
		var val = selectedStyles[i];
		$("input[value='" + val + "']").prop('checked', true);
	}

}

function userChoiceClick(elem, selectedStyles) {
	var psName = elem.prop('value');
	if (elem.prop('checked')) {

		selectedStyles[selectedStyles.length] = psName;
	} else {
		selectedStyles = remove(selectedStyles, psName);
	}

	return selectedStyles;
}

function nextClick(ps, selectedStyles) {

	$("#back").prop("disabled", false);
	var $table = $("#table");
	$table.empty();
	loadNext(ps, selectedStyles);

	for (i = 0; i < selectedStyles.length; i++) {
		var val = selectedStyles[i];
		$("input[value='" + val + "']").prop('checked', true);
	}

}

function startTest(ps, selectedStyles) {
	testDate = new Date();
	loadNext(ps, selectedStyles);
}

function loadNext(ps, selectedStyles) {
	var $table = $("#table");
	
	for (i = 0; i < numberPerPage; i++) {

		if (questionNumber > ps.length) {
			$("#next").prop("disabled", true)
			break;
		}
		$table
				.append("<tr><td><strong>"
						+ questionNumber
						+ "</strong></td><td><span class='career-values-name'>"
						+ ps[currentNumber].name
						+ "</td><td align='center'><input class='form-contrl user-choice' type='checkbox' name='choice' value='"
						+ ps[currentNumber].name + "'/></td></tr>");

		currentNumber++;
		questionNumber++;
	}


	$(".user-choice").on('click', function() {
		selectedStyles = userChoiceClick($(this), selectedStyles);
	});
}

function getPersonalStyles(data) {
	var ps = [];
	for (i = 0; i < data.length; i++) {
		ps[i] = {
			name : data[i],
			userChoice : false
		};
	}
	return ps;
}

function remove(arr, item) {
	for (var i = arr.length; i--;) {
		if (arr[i] === item) {
			arr.splice(i, 1);
		}
	}
	return arr;
}

