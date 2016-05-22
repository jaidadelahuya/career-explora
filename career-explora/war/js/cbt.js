var currentNo = 0;
var questionNumber = 1;
var secondsLeft = 0;
var testDate = null;

$(document).ready(
		function() {
			var subject = window.subject;
			var time = window.time;
			var qNumber = window.questions;
			var passMark = window.passmark;
			var info = null;
			var questions = [];
			var imageUrl = window.imageUrl;
			var username = window.username;
			var history = null;

			$(".profile-img").attr('src', imageUrl);

			$.ajax({
				url : "/azure/getquestions",
				type : "GET",
				data : {
					sub : subject,
					quest : qNumber
				},
				dataType : "Json",
				success : function(data) {
					
					history = data.rec
					$(".subject-name").text(subject);
					$(".no-of-questions").text(qNumber);
					$(".test-time").text(getTime(time));
					
					
					if(!data.questions) {
						info = data.englishQuestions;
						questions = initEnglishQuestionArray(info);
					} else{
						info = data.questions;
						info = shuffle(info);
						questions = initQuestionArray(info);
						
					}
					
					$('#instructions-dialog').modal({
						keyboard : false,
						backdrop : 'static'
					});
					$('.start-test').on(
							'click',
							function() {
								startCBT(subject, questions, history, time,
										passMark, username, imageUrl);
							});
						
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(errorThrown);
				},
				complete : function() {
				}
			});

		});

function getSpeedDataPoints(data) {
	
	var vals = [];
	var a = '';
	var b = 29;
	var test = '';
	for (i = 0; i < data.tests.length; i++) {
		a = new Date(data.tests[i].testDate);
		
		a = new Date(a.getFullYear(),a.getMonth(),a.getDate());
		//a = a.getDate() + "/" + (a.getMonth() + 1)+' '+a.getHours()+":"+a.getMinutes();
		a = b + 3;
		b = b + 24;
		vals[i] = {
				x : a,
				y : b
			};
		
	}
	
	return vals;
}

function getAccuracyDataPoints(data) {
	var vals = [];
	for (i = 0; i < data.length; i++) {
		vals[i] = {
			x : new Date(data[i].testDate),
			y : data[i].accuracy
		};
	}
}

function getConfidenceDataPoints(data) {
	var vals = [];
	for (i = 0; i < data.length; i++) {
		vals[i] = {
			x : new Date(data[i].testDate),
			y : data[i].confidence
		};
	}
}

function getPercentageScoreDataPoints(data) {
	var vals = [];
	for (i = 0; i < data.length; i++) {
		vals[i] = {
			x : new Date(data[i].testDate),
			y : data[i].percentageScore
		};
	}
}

function getPerformanceDataPoints(data) {
	var vals = [];
	for (i = 0; i < data.length; i++) {
		vals[i] = {
			x : new Date(data[i].testDate),
			y : data[i].overallperformance
		};
	}
}

function showChart(spd) {
	var chart = new CanvasJS.Chart('history-chart', {
		title : {
			text : "Site Traffic",
			fontSize : 30
		},
		animationEnabled : true,
		axisX : {

			gridColor : "Silver",
			tickColor : "silver",
			valueFormatString : "DD/MMM"

		},
		toolTip : {
			shared : true
		},
		theme : "theme2",
		axisY : {
			gridColor : "Silver",
			tickColor : "silver"
		},
		legend : {
			verticalAlign : "center",
			horizontalAlign : "right"
		},
		data : [ {
			type : "line",
			showInLegend : true,
			lineThickness : 2,
			name : "Visits",
			markerType : "square",
			color : "#F08080",
			dataPoints : spd
		}

		],
		legend : {
			cursor : "pointer",
			itemclick : function(e) {
				if (typeof (e.dataSeries.visible) === "undefined"
						|| e.dataSeries.visible) {
					e.dataSeries.visible = false;
				} else {
					e.dataSeries.visible = true;
				}
				chart.render();
			}
		}
	});
	chart.render();
}

function getTime(time) {

	if (time < 60) {
		time = time + " mins";
	} else if (time == 60) {
		time = 1 + " hr";
	} else if (time > 60) {
		var h = (time - 30) / 60;
		h = Math.round(h);
		var m = time % 60;
		if (m < 10) {
			m = "0" + m;
		}
		time = h + " hr" + " " + m + " mins";
	}
	return time;
}

function startCBT(subject, data, history, time, passMark, username, imgUrl) {
	
	time = time * 60;
	$('#instructions-dialog').modal('hide');
	$('.heading').text(subject);
	$('#extra-info').html(data[0].extraText);
	$('#div-q').html(questionNumber + ". " + data[0].body);
	var alts = data[0].alts;
	$('#opt-a').html(alts[0]);
	$('#opt-b').html(alts[1]);
	$('#opt-c').html(alts[2]);
	$('#opt-d').html(alts[3]);
	
	for (i = 1; i <= data.length; i++) {
		$('.test-btn-grp').append(
				'<button type="button" class="btn btn-default test-board-number-button">'
						+ i + '</button>');
	}
	
	
	$(".opt").click(function() {
		optionClick($(this), data);
	});

	$('.test-board-number-button').on('click', function() {
		testBoardNumberButton($(this), data);
	});

	$("#next").click(function() {
		nextClick($(this), data);
	});

	$("#back").click(function() {
		backClick($(this), data);
	});

	$("#finish").click(function() {
		$('#end-test-dialog').modal();
	});
	
	$('.submit-test').on('click', function() {
		$('#end-test-dialog').modal('hide');
		finishClick(data, history, passMark, username, time,imgUrl);
	});
	
	

	$('#demo').backward_timer({

		// specify timer's timeout value
		seconds : time,

		// timer's step (in seconds)
		step : 1,

		value_setter : undefined,

		// Set output format
		format : 'h%:m%:s%',

		// Handle event of exhausting
		on_exhausted : function(timer) {
			timer.target.text('Time Up!!!');
			
			timeUp(data, history, passMark, username, time, imgUrl);
		},

		// Handle tick events
		on_tick : function(timer) {
			setSecondsLeft(timer.seconds_left);
		}
	});

	$('#demo').backward_timer('start');
	
	testDate = new Date();

}

function timeUp(data, history, passMark, username, time, imgUrl) {
	$('#time-dialog').modal({
		keyboard : false,
		backdrop : 'static'
	});
	
	$('.time-up-button').click(function() {
		$('#time-dialog').modal('hide');
		finishClick(data, history, passMark, username, time, imgUrl);
	});
	
}

function setSecondsLeft(sec) {
	secondsLeft = sec;
}

function getSecondsLeft() {
	return secondsLeft;
}

function finishClick(data, history, passMark, username, time, imgUrl) {

	console.log("called");
	$('#demo').backward_timer('cancel');
	data = markTest(data);
	$('.test-board-footer').hide();
	$('.heading').text('Test Report');
	$('#demo').hide();
	$("body").css('background-color','white');
	$("#cc-div-content")
			.load(
					'/pages/partials/cbt.html',
					function() {
						$('.test-board-header').addClass('hidden-xs');
						$("#profile-image").attr('src', imgUrl);
						initGeneralTab(data, username);
						initTestInfo(data, time);
						initReportAnalysis(data, passMark);
						initChartAnalysis(data, passMark, time);
						$('#save-div').append('<a id="print-doc" class="btn btn-primary btn-sm cont-button print-doc" style="margin: 1em;"><span class="glyphicon glyphicon-print"></span><span class="hidden-sm hidden-md"> Print</span></a>');
						$('#save-div').append('<button id="save-button" type="button" class="btn btn-primary btn-sm cont-button save-button"><span class="glyphicon glyphicon-save"></span><span class="hidden-sm hidden-md"> Save</span></button>');		
						
						$(".print-doc").on('click', function() {
							window.print();
						});
						
						$(".save-button").on('click', function() {
							
							saveCbtRecord(data, time, passMark);
						});

						var spd = getSpeedDataPoints(history);
						for(i=0; i<spd.length;i++) {
							alert(spd[i].x);
							alert(spd[i].y);
						}
						showChart(spd);

					});
}

function saveCbtRecord(data, totalTime, passMark) {

	var subjectName = data[0].subjectName;
	var testDate = $('#test-date').text();
	var totalQuestions = data.length;
	var noCorrect = getScore(data);
	var nuans = getUnanswered(data);
	var noWrong = totalQuestions - (noCorrect + nuans);
	var speed = getSpeed(data, totalTime);
	var accuracy = getAccuracy(data);
	var confidence = getConfidence(data, accuracy, passMark);
	var pScore = getPercentageScore(noCorrect, data.length);
	var overallPerformance = getOverallPerformance(pScore, accuracy,
			confidence, speed);
	
	var data = {
			testDate : testDate,
			totalQuestions : totalQuestions,
			noCorrect : noCorrect,
			nuans : nuans,
			noWrong : noWrong,
			speed : speed,
			accuracy : accuracy,
			confidence : confidence,
			pScore : pScore,
			overallPerformance : overallPerformance
	};
	
	data = JSON.stringify(data);

	$.ajax({
		url : "/azure/savecbt",
		type : "POST",
		data : {
			data : data,
			testDate : testDate,
			subjectName : subjectName
		},
		success : function(data) {
			$("#save-success-dialog").modal();
			$(".close-window").on('click', function() {
				$("#save-success-dialog").modal('hide');
				window.close();
			});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		complete : function() {
			var ltn = $("#last-test-name",opener.document);
			ltn.text('CBT '+subjectName);
			var ltd = $("#last-test-date",opener.document);
			$("#last-test-date-row",opener.document).show();
			ltd.text(testDate);
		}
	});
}

function initChartAnalysis(data, passMark, time) {
	var scr = getScore(data);
	var psc = getPercentageScore(scr, data.length);
	var color = getCPColor(psc);

	$('#score-progress').css('width', psc + "%");
	$('#score-progress').css('background-color', color);
	$('#cp-score').text(psc + "%");

	var accuracy = getAccuracy(data);
	color = getCPColor(accuracy);
	$('#accuracy-progress').css('width', accuracy + "%");
	$('#accuracy-progress').css('background-color', color);
	$('#cp-accuracy').text(accuracy + "%");

	var confidence = getConfidence(data, accuracy, passMark);
	color = getCPColor(confidence);
	$('#confidence-progress').css('width', confidence + "%");
	$('#confidence-progress').css('background-color', color);
	$('#cp-confidence').text(confidence + "%");

	var speed = getSpeed(data, time);
	color = getCPColor(speed);
	$('#speed-progress').css('width', speed + "%");
	$('#speed-progress').css('background-color', color);
	$('#cp-speed').text(speed + "%");

	var overallPerformance = getOverallPerformance(psc, accuracy, confidence,
			speed);
	color = getCPColor(overallPerformance);
	$('#overall-progress').css('width', overallPerformance + "%");
	$('#overall-progress').css('background-color', color);
	$('#cp-overall').text(overallPerformance + "%");

}

function getOverallPerformance(pScore, accuracy, confidence, speed) {
	var a = pScore * 0.5;
	var b = accuracy * 0.25;
	var c = confidence * 0.2;
	var d = speed * 0.05;
	var e = a + b + c + d;
	e = Math.round(e);
	return e;
}

function getTimeLeft() {
	var time = getSecondsLeft();
	return time;
}

function getTimeRate(data, totalTime) {
	var timeLeft = getTimeLeft();
	var timeUsed = totalTime - timeLeft;
	var timeRate = 0;
	var nans = data.length - getUnanswered(data);
	if (nans == 0) {
		timeLeft = 0;
	} else {
		timeRate = timeUsed / nans;
	}
	timeRate = Math.round(timeRate);
	return timeRate;
}

function getSpeed(data, totalTime) {
	var a = 240;
	var b = 30;
	var x = 0;
	var y = 100;
	var t = getTimeRate(data, totalTime);
	var speed = (((x - y) * (t - b)) / (a - b)) + y;
	speed = Math.round(speed);
	if (speed > 100) {
		speed = 100;
	} else if (speed < 0) {
		speed = 0;
	}
	return speed;
}

function getConfidence(data, accuracy, passMark) {

	var confidence = '';
	var score = getPercentageScore(getScore(data), data.length);

	if (passMark > 50) {
		confidence = accuracy * (score / 50) * (score / passMark);

		if (confidence > 100) {
			confidence == 100;
		}
	} else if (confidence == 50) {
		confidence = accuracy * (score / 50);
	} else {
		confidence = accuracy * (score / 50) * (passMark / 50);
	}

	confidence = Math.round(confidence);
	return confidence;
}

function getCPColor(val) {
	var color = '';
	if (val >= 80) {
		color = 'darkgreen';
	} else if (val >= 60) {
		color = 'olive';
	} else if (val >= 40) {
		color = 'orange';
	} else {
		color = 'red';
	}
	return color;
}

function getAccuracy(data) {
	var a = 0;
	var scr = getScore(data);
	var unans = getUnanswered(data);

	var ans = data.length - unans;
	if (ans != 0) {
		a = scr / ans;
	}

	a *= 100;
	a = Math.round(a);
	return a;
}

function initReportAnalysis(data, passMark) {
	var scr = getScore(data);
	var sc = scr + " out of " + data.length;
	var psc = getPercentageScore(scr, data.length);
	var grade = getGrade(psc);
	var comment = getComment(psc);

	$('#score').text(sc);
	$('#percentage-score').text(psc + '%');
	$('#grade').text(grade);
	$('#comment').text(comment);
}

function initTestInfo(data, time) {
	var score = getScore(data);
	var unanswered = getUnanswered(data);
	var wrong = data.length - (score + unanswered);
	$('#total-questions').text(data.length);
	$('#correct-answers').text(score);
	$('#wrong-answers').text(wrong);
	$('#unanswered').text(unanswered);
}

function initGeneralTab(data, username) {

	$('#username').text(username);
	$('#subject-name').text(data[0].subjectName);
	$('#test-date').text(testDate);
}

function backClick(elem, data) {
	if ($("#next").prop("disabled")) {
		$("#next").prop("disabled", false);
	}

	currentNo--;
	questionNumber--;
	$("#div-q").html(questionNumber + ".  " + data[currentNo].body);
	$("#extra-info").html(data[currentNo].extraText);
	if (currentNo == 0) {
		elem.prop("disabled", true);
	}

	var alts = data[currentNo].alts;
	$('#opt-a').html(alts[0]);
	$('#opt-b').html(alts[1]);
	$('#opt-c').html(alts[2]);
	$('#opt-d').html(alts[3]);

	var foo = data[currentNo].choice;
	if (foo.trim() == 'A') {
		$("#opt-4").prop("checked", true);
	} else if (foo.trim() == 'B') {
		$("#opt-3").prop("checked", true);
	} else if (foo.trim() == 'C') {
		$("#opt-2").prop("checked", true);
	} else if (foo.trim() == 'D') {
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
	$("#div-q").html(questionNumber + ".  " + data[currentNo].body);
	$("#extra-info").html(data[currentNo].extraText);
	var alts = data[currentNo].alts;
	$('#opt-a').html(alts[0]);
	$('#opt-b').html(alts[1]);
	$('#opt-c').html(alts[2]);
	$('#opt-d').html(alts[3]);

	var foo = data[currentNo].choice;
	if (foo.trim() == 'A') {
		$("#opt-4").prop("checked", true);
	} else if (foo.trim() == 'B') {
		$("#opt-3").prop("checked", true);
	} else if (foo.trim() == 'C') {
		$("#opt-2").prop("checked", true);
	} else if (foo.trim() == 'D') {
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

function testBoardNumberButton(elem, data) {

	var n = elem.text();
	questionNumber = n;
	currentNo = n - 1;

	$("#div-q").html(questionNumber + ".  " + data[currentNo].body);
	$("#extra-info").html(data[currentNo].extraText);
	var alts = data[currentNo].alts;
	$('#opt-a').html(alts[0]);
	$('#opt-b').html(alts[1]);
	$('#opt-c').html(alts[2]);
	$('#opt-d').html(alts[3]);

	var foo = data[currentNo].choice;

	$(".opt").prop("checked", false);
	if (foo.trim() == 'A') {
		$("#opt-4").prop("checked", true);
	} else if (foo.trim() == 'B') {
		$("#opt-3").prop("checked", true);
	} else if (foo.trim() == 'C') {
		$("#opt-2").prop("checked", true);
	} else if (foo.trim() == 'D') {
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

function initEnglishQuestionArray(data) {
	var questionArray = [];
	var quest = null;
	var qs = null;
	
	for(i=0; i< data.length; i++) {
		qs = data[i].questions;
		
		for(j=0; j < qs.length; j++) {
			quest = {
					subjectName : qs[j].subjectName,
					body : qs[j].body,
					extraText : data[i].instruction,
					correctAlt : qs[j].correctAlternative,
					alts : qs[j].alternatives,
					isCorrect : qs[j].isCorrect,
					choice : ""
				};
				questionArray[questionArray.length] = quest;
		}
	}
	return questionArray;
}


function initQuestionArray(data) {
	var questionArray = [];
	var quest = null;
	for (i = 0; i < data.length; i++) {
		quest = {
			subjectName : data[i].subjectName,
			body : data[i].body,
			extraText : data[i].extraText,
			correctAlt : data[i].correctAlternative,
			alts : data[i].alternatives,
			isCorrect : data[i].isCorrect,
			choice : ""
		};
		questionArray[i] = quest;
	}

	return questionArray;
}

function markTest(data) {
	for (i = 0; i < data.length; i++) {

		if (data[i].choice == data[i].correctAlt) {
			data[i].isCorrect = true;
		} else if (data[i].choice == '') {
			data[i].isCorrect = null;
		}
	}
	return data;
}

function getUnanswered(data) {
	score = 0;
	for (i = 0; i < data.length; i++) {
		if (data[i].isCorrect == null) {
			score += 1;
		}
	}
	return score;
}

function getScore(data) {
	score = 0;
	for (i = 0; i < data.length; i++) {
		if (data[i].isCorrect) {
			score += 1;
		}
	}
	return score;
}

function getPercentageScore(score, total) {
	score = score / total * 100;
	score = Math.round(score);
	return score;
}

function getGrade(score) {
	

	if(score >= 80) {
		return 'A';
	} else if(score >= 70) {
		return 'B';
	}else if(score >= 60) {
		return 'C';
	}else if(score >= 50) {
		return 'D';
	}else if(score >= 40) {
		return 'E';
	}else {
		return 'F';
	}
}

function getComment(score) {
	

	if(score >= 80) {
		return 'An excellent performance';
	} else if(score >= 70) {
		return 'A very good performance';
	}else if(score >= 60) {
		return 'A Good performance';
	}else if(score >= 50) {
		return 'A fairly good performance';
	}else if(score >= 40) {
		return 'A poor performance';
	}else {
		return 'A very poor peformance';
	}
}

function shuffle(o) { // v1.0
	for (var j, x, i = o.length; i; j = Math.floor(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x)
		;
	return o;
}