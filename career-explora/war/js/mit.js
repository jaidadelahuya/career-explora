var currentNo = 0;
var questionNumber = 1;
var testDate = null;

$(document).ready(
		function() {

			var userid = window.userid;
			var username = window.username;
			var imgUrl = window.imgUrl;
			var info = null;

			$(".profile-img").attr('src', imgUrl);

			$.ajax({
				url : "/azure/getmultipleintelligencetype",
				type : "GET",
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
				error : function(jqXHR, status, errorThrown) {
					if (jqXHR.status == 401) {
						reLogin();
					} else {
						alert("an error has occured");
					}
				}

			});

		});

function finishClick(info, questions, username, imgUrl) {
	var types = getIntelligenceTypes(info);
	types = cummulate(types, questions);

	var veryStrong = getVeryStrong(types);
	var strong = getStrong(types);
	var fairlyStrong = getFairlyStrong(types);
	var weak = getWeak(types);

	$('.test-board-footer').hide();
	$('.heading').text('Multiple Intelligence Test Report');
	$("body").css('background-color', 'white');
	$("#cc-div-content")
			.load(
					'/pages/partials/mit.html',
					function() {

						var dPoints = getDataPoints(types);
						$('#user-name').text(username);
						$('#today').text(testDate);
						showChart(dPoints);

						showVeryStrong(veryStrong);
						showStrong(strong);
						showFairlyStrong(fairlyStrong);
						showWeak(weak);

						$("#profile-image").attr('src', imgUrl);

						$(".description").on('click', function() {
							descriptionClick($(this), types);
						});

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
							saveMit(types);
						});
					});
}

function saveMit(t) {
	var types = getPersistTypes(t);
	types = JSON.stringify(types);
	var hasMore = false;

	$
			.ajax({
				url : '/azure/savemit',
				type : 'POST',
				data : {
					types : types,
					testDate : testDate
				},
				success : function(data) {
					$("#save-success-dialog").modal();
					$(".close-window").on('click', function() {
						$("#save-success-dialog").modal('hide');
						window.close();
					});
					types = data.list;
					hasMore = data.hasMore;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(errorThrown);
				},
				complete : function() {
					var ltn = $("#last-test-name", opener.document);
					ltn.text('MULTIPLE INTELLIGENCE TEST');
					var ltd = $("#last-test-date", opener.document);
					$("#last-test-date-row", opener.document).show();
					ltd.text(testDate);

					var list = $("#mit-profile", opener.document);
					populateMITList(types,list,hasMore);
				}
			});
}

function getPersistTypes(types) {
	var ty = [];
	for (i = 0; i < types.length; i++) {
		ty[i] = {
			intelligenceType : types[i].intelligenceType,
			value : types[i].value

		};
	}

	return ty;
}

function descriptionClick(elem, types) {

	var ity = getCurrentType(elem, types);

	$('#multiple-intelligence-dialog').modal();

	$('#intelligence-type-header').text(ity.intelligenceType);
	$('#intelligence-type-description').text(ity.longDescription);
	$('#intelligence-type-typical-roles').text(ity.typicalRoles);
	$('#intelligence-type-related-activities').text(ity.relatedActivities);
	$('#intelligence-type-preferred-learning-style').text(
			ity.preferredLearningStyle);
}

function getCurrentType(elem, types) {
	var ity = null;
	for (i = 0; i < types.length; i++) {
		if (elem.text().trim() == types[i].intelligenceType.trim()) {
			ity = types[i]
		}
	}
	return ity;
}

function showWeak(vs) {
	for (i = 0; i < vs.length; i++) {
		$('#weak-list').append(
				'<li class="list-group-item description to-pointer"><div>'
						+ vs[i].intelligenceType + '</div></li>');
	}
}

function getWeak(types) {
	var vs = [];
	for (i = 0; i < types.length; i++) {
		if (types[i].value < 10) {
			vs[vs.length] = types[i];
		}
	}
	return vs;
}

function showFairlyStrong(vs) {
	for (i = 0; i < vs.length; i++) {
		$('#fairly-strong-list').append(
				'<li class="list-group-item description to-pointer"><div>'
						+ vs[i].intelligenceType + '</div></li>');
	}
}

function getFairlyStrong(types) {
	var vs = [];
	for (i = 0; i < types.length; i++) {
		if (types[i].value >= 10 && types[i].value < 13) {
			vs[vs.length] = types[i];
		}
	}
	return vs;
}

function showStrong(vs) {
	for (i = 0; i < vs.length; i++) {
		$('#strong-list').append(
				'<li class="list-group-item description to-pointer"><div>'
						+ vs[i].intelligenceType + '</div></li>');
	}
}

function getStrong(types) {
	var vs = [];
	for (i = 0; i < types.length; i++) {
		if (types[i].value >= 13 && types[i].value < 16) {
			vs[vs.length] = types[i];
		}
	}
	return vs;
}

function showVeryStrong(vs) {
	for (i = 0; i < vs.length; i++) {
		$('#very-strong-list').append(
				'<li class="list-group-item description to-pointer"><div>'
						+ vs[i].intelligenceType + '</div></li>');
	}
}

function getVeryStrong(types) {
	var vs = [];
	for (i = 0; i < types.length; i++) {
		if (types[i].value >= 16) {
			vs[vs.length] = types[i];
		}
	}

	return vs;
}

function showChart(dPoints) {

	var chart1 = new CanvasJS.Chart("div-3", {

		title : {
			text : "Multiple Intelligence Test Chart",
			fontColor : "blue",
			fontSize : 30,
			fontWeight : "normal"
		},
		axisX : {
			labelMaxWidth : 105,
			title : "Intelligence Types",
			titleFontColor : "Blue",
			titleFontFamily : "tahoma",
			titleFontSize : 20,
			labelFontSize : 15,
			labelFontColor : "dimGrey",
			gridThickness : 0
		},
		axisY : {
			title : "Score",
			interlacedColor : "Azure",
			titleFontColor : "Blue",
			titleFontFamily : "tahoma",
			titleFontSize : 20,
			labelFontSize : 15,
			labelFontColor : "dimGrey",
			gridThickness : 0
		},
		legend : {
			fontSize : 16,
			fontFamily : "tahoma",
			fontColor : "Sienna"
		},
		data : [ {
			type : "bar",
			showInLegend : true,
			color : "darkgreen",
			legendText : "Very Strong",
			dataPoints : dPoints
		}, {
			type : "bar",
			legendText : "Strong",
			color : "olive",
			showInLegend : true,
			dataPoints : []
		}, {
			type : "bar",
			legendText : "Fairly Strong",
			color : "orange",
			showInLegend : true,
			dataPoints : []
		}, {
			type : "bar",
			legendText : "Weak",
			color : "red",
			showInLegend : true,
			dataPoints : []
		} ]
	});

	var chart = new CanvasJS.Chart("div-2", {

		title : {
			text : "Multiple Intelligence Test Chart",
			fontColor : "blue",
			fontSize : 30,
			fontWeight : "normal"
		},
		axisX : {
			labelMaxWidth : 105,
			title : "Intelligence Types",
			titleFontColor : "Blue",
			titleFontFamily : "tahoma",
			titleFontSize : 20,
			labelFontSize : 15,
			labelFontColor : "dimGrey",
			gridThickness : 0
		},
		axisY : {
			title : "Score",
			interlacedColor : "Azure",
			titleFontColor : "Blue",
			titleFontFamily : "tahoma",
			titleFontSize : 20,
			labelFontSize : 15,
			labelFontColor : "dimGrey",
			gridThickness : 0
		},
		legend : {
			fontSize : 16,
			fontFamily : "tahoma",
			fontColor : "Sienna"
		},
		data : [ {
			type : "bar",
			showInLegend : true,
			color : "darkgreen",
			legendText : "Very Strong",
			dataPoints : dPoints
		}, {
			type : "bar",
			legendText : "Strong",
			color : "olive",
			showInLegend : true,
			dataPoints : []
		}, {
			type : "bar",
			legendText : "Fairly Strong",
			color : "orange",
			showInLegend : true,
			dataPoints : []
		}, {
			type : "bar",
			legendText : "Weak",
			color : "red",
			showInLegend : true,
			dataPoints : []
		} ]
	});
	chart.render();
	chart1.render();
}

function getDataPoints(types) {
	var dps = [];

	for (i = 0; i < types.length; i++) {

		var choice = types[i].value;
		var clr = null;
		var name = types[i].intelligenceType;

		if (choice >= 16) {
			clr = "darkgreen";
		} else if (choice >= 13) {
			clr = "olive";
		} else if (choice >= 10) {
			clr = "orange";
		} else {
			clr = "red";
		}
		dps[dps.length] = {
			label : name,
			y : choice,
			color : clr
		};
	}
	return dps;
}

function cummulate(types, questions) {

	for (i = 0; i < types.length; i++) {

		for (j = 0; j < questions.length; j++) {
			if (types[i].intelligenceType.trim() == questions[j].intelligenceType
					.trim()) {
				types[i].value = types[i].value + Number(questions[j].choice);
			}
		}
	}

	return types;
}

function getIntelligenceTypes(info) {
	var types = [];
	for (i = 0; i < info.length; i++) {
		types[i] = {
			intelligenceType : info[i].intelligenceType,
			value : 0,
			longDescription : info[i].longDescription,
			typicalRoles : info[i].typicalRoles,
			relatedActivities : info[i].relatedActivities,
			preferredLearningStyle : info[i].preferredLearningStyle,
		};
	}
	return types;
}

function testBoardNumberButton(elem, data) {

	var n = elem.text();
	questionNumber = n;
	currentNo = n - 1;

	$("#div-q").text(questionNumber + ".  " + data[currentNo].question);

	var foo = data[currentNo].choice;
	if (foo == 4) {
		$("#opt-4").prop("checked", true);
	} else if (foo == 3) {
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
	if (foo == 4) {
		$("#opt-4").prop("checked", true);
	} else if (foo == 3) {
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
	if (foo == 4) {
		$("#opt-4").prop("checked", true);
	} else if (foo == 3) {
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
				intelligenceType : info[i].intelligenceType,
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