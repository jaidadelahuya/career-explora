var questionNumber = 1;
var currentNumber = 0;
var numberPerPage = 8;
var selectedValues = [];
var testDate = null;

$(document).ready(function() {
	var userid = window.userid;
	var username = window.username;
	var imgUrl = window.imgUrl;
	
	var cv = null;

	$(".profile-img").attr('src', imgUrl);

	$.ajax({
		url : "/azure/getcareervalues",
		type : "GET",
		dataType : "Json",
		success : function(data) {

			cv = getCareerValues(data.values);

			startTest(cv, selectedValues);

			$("#next").click(function() {
				nextClick(cv, selectedValues);

			});

			$("#back").click(function() {
				backClick(cv, selectedValues);
			});

			$("#finish").click(function() {
				$('#end-test-dialog').modal();
			});

			$(".submit-test").on('click', function() {

				finishClick(cv, selectedValues, userid, imgUrl, username);

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

function finishClick(cv, selectedValues, userid, imgUrl, username) {
	$('.test-board-footer').hide();
	$('.heading').text('Career Values Report');

	$("#cc-div-content")
			.load(
					'/pages/partials/career-values.html',
					function() {
						
						$('#user-name').text(username);
						$('#today').text(testDate);
						$("#profile-image").attr('src', imgUrl);

						var values = getSelected(selectedValues, cv);

						showValues(values);

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
							saveCareerValues(values);
						});
						$("body").css('background-color', 'white');
						$('#end-test-dialog').modal('hide');
					});
}

function saveCareerValues(values) {
	var values = JSON.stringify(values);
	var hasMore = false;

	$
			.ajax({
				url : '/azure/savecareervalues',
				type : 'POST',
				data : {
					values : values,
					testDate : testDate
				},
				success : function(data) {
					$("#save-success-dialog").modal();
					$(".close-window").on('click', function() {
						$("#save-success-dialog").modal('hide');
						window.close();
					});
					values = data.list;
					hasMore = data.hasMore;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(errorThrown);
				},
				complete : function() {
					var ltn = $("#last-test-name", opener.document);
					ltn.text('CAREER VALUES');
					var ltd = $("#last-test-date", opener.document);
					$("#last-test-date-row", opener.document).show();
					ltd.text(testDate);
					
					var list = $("#career-values-profile", opener.document);
					populateValuesList(values, list, hasMore );

					
				}
			});
}

function getSelected(selectedValues, cv) {

	var val = [];
	for (i = 0; i < selectedValues.length; i++) {
		for (j = 0; j < cv.length; j++) {
			if (selectedValues[i].trim() == cv[j].name.trim()) {
				val[val.length] = cv[j];
			}
		}
	}
	return val;
}

function showValues(vs) {
	for (i = 0; i < vs.length; i++) {
		$('#career-values-list')
				.append(
						'<li class="list-group-item description to-pointer"><div><span class="career-values-name">'
								+ vs[i].name
								+ '</span> <span class="career-values-desc"> - '
								+ vs[i].desc + '</span></div></li>');
	}
}

function startTest(cv, selectedValues) {
	loadNext(cv, selectedValues);
	testDate = new Date();
}

function loadNext(cv, selectedValues) {
	var $table = $("#table");

	for (i = 0; i < numberPerPage; i++) {

		if (questionNumber > cv.length) {
			$("#next").prop("disabled", true)
			break;

		}
		$table
				.append("<tr><td><strong>"
						+ questionNumber
						+ "</strong></td><td><span class='career-values-name'>"
						+ cv[currentNumber].name
						+ "</span><span class='career-values-desc'> -"
						+ cv[currentNumber].desc
						+ "</span>"
						+ "</td><td align='center'><input class='form-contrl user-choice' type='checkbox' name='choice' value='"
						+ cv[currentNumber].name + "'/></td></tr>");

		currentNumber++;
		questionNumber++;
	}

	$(".user-choice").on('click', function() {
		selectedValues = userChoiceClick($(this), selectedValues);
	});
}

function loadPrevious(cv, selectedValues) {
	var $table = $("#table");

	var mod = cv.length % numberPerPage;
	if (currentNumber == cv.length) {
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
						+ cv[cn].name
						+ "</span><span class='career-values-desc'> -"
						+ cv[cn].desc
						+ "</span>"
						+ "</td><td align='center'><input class='form-contrl user-choice' type='checkbox' name='choice' value='"
						+ cv[cn].name + "'/></td></tr>");

		qn--;
		cn--;

	}
	if (qn < numberPerPage) {
		$("#back").prop('disabled', true);

	}

	$(".user-choice").on('click', function() {
		selectedValues = userChoiceClick($(this), selectedValues);
	});
}

function nextClick(cv, selectedValues) {

	$("#back").prop("disabled", false);
	var $table = $("#table");
	$table.empty();
	loadNext(cv, selectedValues);

	for (i = 0; i < selectedValues.length; i++) {
		var val = selectedValues[i];
		$("input[value='" + val + "']").prop('checked', true);
	}

}

function backClick(cv, selectedValues) {

	$("#next").prop("disabled", false);
	var $table = $("#table");
	$table.empty();
	loadPrevious(cv, selectedValues);

	for (i = 0; i < selectedValues.length; i++) {
		var val = selectedValues[i];
		$("input[value='" + val + "']").prop('checked', true);
	}

}

function userChoiceClick(elem, selectedValues) {
	var cvName = elem.prop('value');
	if (elem.prop('checked')) {

		selectedValues[selectedValues.length] = cvName;
	} else {
		selectedValues = remove(selectedValues, cvName);
	}

	return selectedValues;
}

function getCareerValues(data) {
	var cv = [];
	var cvName = '';
	var cvDesc = '';
	for (i = 0; i < data.length; i++) {

		cvName = data[i].substring(0, data[i].indexOf('*'));
		cvDesc = data[i].substring(data[i].indexOf('*') + 1);
		cv[i] = {
			name : cvName,
			desc : cvDesc,
			userChoice : false
		};
	}
	return cv;
}

function remove(arr, item) {
	for (var i = arr.length; i--;) {
		if (arr[i] === item) {
			arr.splice(i, 1);
		}
	}
	return arr;
}
