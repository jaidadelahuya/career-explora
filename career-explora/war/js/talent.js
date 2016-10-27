var currentNo = 0;
var questionNumber = 1;
var testDate = null;
var msg = null;

$(document).ready(
		function() {
			msg = window.name;
			
			var info = null;
			var imgUrl = window.imgUrl;
			var username = window.username;
			
			

			$(".profile-img").attr('src', imgUrl);
			$.ajax({
				url : "/azure/gettalents",
				type : "GET",
				data : {
					test : msg
				},
				dataType : "Json",
				success : function(data) {
					info = data;
					var questions = initTest(info);
					questions = shuffle(questions);
					startTest(questions);
					for (i = 1; i <= questions.length; i++) {
						$('.test-btn-grp').append(
								'<button type="button" class="btn btn-default test-board-number-button">'
										+ i + '</button>');
					}

					$(".opt").click(function() {
						optionClick($(this), questions);
					});

					$("#next").click(function() {
						nextClick($(this), questions);
					});

					$("#back").click(function() {
						backClick($(this), questions);
					});

					$('.test-board-number-button').on('click', function() {
						testBoardNumberButton($(this), questions);
					});

					$("#finish").click(function() {
						$('#end-test-dialog').modal();
					});

					$('.submit-test').on('click', function() {
						$('#end-test-dialog').modal('hide');
						finishClick(info, questions, username, imgUrl);
					});

				},
				error : function() {
					alert('An error has occured. Try again in 3omins');
				},
				complete : function() {
					$("#back").prop("disabled", true);
				}
			});
		});

function cummulate(talents, questions) {

	for (i = 0; i < talents.length; i++) {

		for (j = 0; j < questions.length; j++) {
			if (talents[i].talentName.trim() == questions[j].talentName.trim()) {
				talents[i].value = talents[i].value
						+ Number(questions[j].choice);
			}
		}
	}

	return talents;
}

function getTalents(info) {
	var talents = [];
	for (i = 0; i < info.length; i++) {
		talents[i] = {
			talentName : info[i].name,
			value : 0
		};
	}
	return talents;
}

function getStrong(talents) {
	var vs = [];
	for (i = 0; i < talents.length; i++) {
		if (talents[i].value >= 6 && talents[i].value < 8) {
			vs[vs.length] = talents[i];
		}
	}

	return vs;
}

function getFair(talents) {
	var vs = [];
	for (i = 0; i < talents.length; i++) {
		if (talents[i].value == 5) {
			vs[vs.length] = talents[i];
		}
	}

	return vs;
}

function getVeryStrong(talents) {
	var vs = [];
	for (i = 0; i < talents.length; i++) {
		if (talents[i].value >= 8) {
			vs[vs.length] = talents[i];
		}
	}

	return vs;
}

function getWeak(talents) {
	var vs = [];
	for (i = 0; i < talents.length; i++) {
		if (talents[i].value < 5) {
			vs[vs.length] = talents[i];
		}
	}

	return vs;
}

function finishClick(info, questions, username, imgUrl) {

	var talents = getTalents(info);
	talents = cummulate(talents, questions);

	var strong = getStrong(talents);
	var veryStrong = getVeryStrong(talents);
	var fair = getFair(talents);
	var weak = getWeak(talents);

	$('.test-board-footer').hide();
	$('.heading').text('Talent Hunt Report');
	$("body").css('background-color', 'white');
	$("#cc-div-content").css('background-color', 'white');
	$("#cc-div-content")
			.load(
					'/pages/partials/talent.html',
					function() {

						$('#user-name').text(username);
						$('#today').text(testDate);
						$("#profile-image").attr('src', imgUrl);

						showVeryStrong(veryStrong);
						showStrong(strong);
						showFairlyStrong(fair);
						showWeak(weak);

						$('#save-div')
								.append(
										'<a id="print-doc" class="btn ca-btn-primary btn-sm cont-button" style="margin: 1em;"><span class="glyphicon glyphicon-print"></span><span class="hidden-sm hidden-md"> Print</span></a>');
						$('#save-div')
								.append(
										'<button id="save-button" type="button" class="btn ca-btn-primary btn-sm cont-button save-button"><span class="glyphicon glyphicon-save"></span><span class="hidden-sm hidden-md"> Save</span></button>');

						$('#print-doc').on('click', function() {
							window.print();
						});

						$('.save-button').on('click', function() {
							saveTalents(talents);
						});
					});
}

function saveTalents(talents) {
	var t = JSON.stringify(talents);
	var hasMore = null;
	$.ajax({
		url : '/azure/savetalents',
		type : 'POST',
		dataType: 'Json',
		data : {
			talents : t,
			testDate : testDate,
			category : msg
		},
		success : function(data) {
			$("#save-success-dialog").modal();
			$(".close-window").on('click', function() {
				$("#save-success-dialog").modal('hide');
				window.close();
			});
			talents = data.list;
			hasMore = data.hasMore;
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		complete : function() {
			var ltn = $("#last-test-name", opener.document);
			ltn.text('TALENT HUNT');
			var ltd = $("#last-test-date", opener.document);
			$("#last-test-date-row", opener.document).show();
			ltd.text(testDate);
			
			var talentList = $("#talent-profile",opener.document);
			console.log("got here");
			populateTalentList(talents,talentList,hasMore);
		}
	});
}

function showVeryStrong(vs) {
	for (i = 0; i < vs.length; i++) {
		$('#very-strong-list').append(
				'<li class="list-group-item description to-pointer"><div>'
						+ vs[i].talentName + '</div></li>');
	}
}

function showStrong(vs) {
	for (i = 0; i < vs.length; i++) {
		$('#strong-list').append(
				'<li class="list-group-item description to-pointer"><div>'
						+ vs[i].talentName + '</div></li>');
	}
}

function showFairlyStrong(vs) {
	for (i = 0; i < vs.length; i++) {
		$('#fairly-strong-list').append(
				'<li class="list-group-item description to-pointer"><div>'
						+ vs[i].talentName + '</div></li>');
	}
}

function showWeak(vs) {
	for (i = 0; i < vs.length; i++) {
		$('#weak-list').append(
				'<li class="list-group-item description to-pointer"><div>'
						+ vs[i].talentName + '</div></li>');
	}
}

function testBoardNumberButton(elem, data) {

	var n = elem.text();
	questionNumber = n;
	currentNo = n - 1;

	$("#div-q").text(questionNumber + ".  " + data[currentNo].question);

	var foo = data[currentNo].choice;
	if (foo == 3) {
		$("#opt-3").prop("checked", true);
	} else if (foo == 2) {
		$("#opt-2").prop("checked", true);
	} else if (foo == 1) {
		$("#opt-1").prop("checked", true);
	} else {
		$(".opt").prop("checked", false);
	}

	if ((currentNo + 1) == data.length) {
		$('#back').prop("disabled", false);
		$('#next').attr("disabled", "disabled");
		return;
	} else {
		$('#next').prop("disabled", false);
	}

	if (questionNumber == 1) {
		$('#back').attr("disabled", "disabled");
		return;
	} else {
		$('#back').prop("disabled", false);
	}

}

function backClick(elem, data) {
	if ($("#next").prop("disabled")) {
		$("#next").prop("disabled", false);
	}

	currentNo--;
	questionNumber--;
	$("#div-q").text(questionNumber + ".  " + data[currentNo].question);
	if (currentNo == 0) {
		elem.prop("disabled", true);
	}

	var foo = data[currentNo].choice;
	if (foo == 3) {
		$("#opt-3").prop("checked", true);
	} else if (foo == 2) {
		$("#opt-2").prop("checked", true);
	} else if (foo == 1) {
		$("#opt-1").prop("checked", true);
	} else {
		$(".opt").prop("checked", false);
	}

}

function nextClick(elem, data) {
	if ($("#back").prop("disabled")) {
		$("#back").prop("disabled", false);
	}
	currentNo++;
	questionNumber++;
	$("#div-q").text(questionNumber + ".  " + data[currentNo].question);

	var foo = data[currentNo].choice;
	if (foo == 3) {
		$("#opt-3").prop("checked", true);
	} else if (foo == 2) {
		$("#opt-2").prop("checked", true);
	} else if (foo == 1) {
		$("#opt-1").prop("checked", true);
	} else {
		$(".opt").prop("checked", false);
	}

	if ((currentNo + 1) == data.length) {
		elem.attr("disabled", "disabled");

	}

}

function optionClick(elem, data) {
	val = elem.val();
	data[currentNo].choice = val;
	var elem1 = document.getElementById("test-board-btn-grp");
	var c = elem1.childNodes;
	var text = '';
	for (i = 0; i < c.length; i++) {
		text = c[i].innerHTML;
		if (text == (currentNo + 1)) {
			c[i].style.color = 'orange';
		}
	}
}

function startTest(data) {
	$("#div-q").text(questionNumber + ".  " + data[currentNo].question);
	testDate = new Date();
}

function initTest(info) {
	var ma = [];
	var qs = null;
	for (i = 0; i < info.length; i++) {
		qs = info[i].questions;
		for (j = 0; j < qs.length; j++) {
			ma[ma.length] = {
				choice : 0,
				talentName : info[i].name,
				question : qs[j]
			};
		}
	}

	return ma;
}

function shuffle(o) { // v1.0
	for (var j, x, i = o.length; i; j = Math.floor(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x)
		;
	return o;
}