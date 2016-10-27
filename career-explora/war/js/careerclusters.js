var currentNo = 0;
var questionNumber = 1;
var cluster = null;
var testDate = null;

function addPopOver(name) {

	$('#imginfo').webuiPopover({
		placement : 'auto',
		width : '10em',
		height : 'auto',
		trigger : 'click',
		arrow : true,
		content : name,
		closeable : true,
		padding : true,
		type : 'html'
	});
}

$(document).ready(
		function() {
			var msg = window.name;
			var userid = window.userid;
			var imgUrl = window.imgUrl;
			var username = window.username;

			var questions = [];

			$(".profile-img").attr('src', imgUrl);

			$.ajax({
				url : "/azure/careerclusterboard",
				type : "GET",
				data : {
					test : msg
				},
				dataType : "Json",
				success : function(data) {
					cluster = data;
					questions = getQuestions(cluster);
					questions = shuffle(questions);

					startTest(questions);

					for (i = 1; i <= questions.length; i++) {
						$('.test-btn-grp').append(
								'<button type="button" class="btn btn-default test-board-number-button">'
										+ i + '</button>');
					}

				},
				error : function() {
					alert('An error has occured. Try again in 30mins');
				},
				complete : function() {
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

						finishClick(cluster, questions, username, imgUrl);
					});

				}

			});
		});

function optionClick(elem, questions) {
	val = elem.val();
	questions[currentNo].userChoice = val;
	var elem = document.getElementById("test-board-btn-grp");
	var c = elem.childNodes;
	var text = '';
	for (i = 0; i < c.length; i++) {
		text = c[i].innerHTML;
		if (text == (currentNo + 1)) {
			c[i].style.color = 'orange';
		}
	}
}

function backClick(elem, questions) {
	if ($("#next").prop("disabled")) {
		$("#next").prop("disabled", false);
	}

	currentNo--;
	questionNumber--;
	$("#div-q").html(questionNumber + ".  " + questions[currentNo].qBody);
	if (currentNo == 0) {
		elem.prop("disabled", true);
	}

	var foo = questions[currentNo].userChoice;
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

function nextClick(elem, questions) {
	if ($("#back").prop("disabled")) {
		$("#back").prop("disabled", false);
	}
	currentNo++;
	questionNumber++;
	$("#div-q").html(questionNumber + ".  " + questions[currentNo].qBody);

	var foo = questions[currentNo].userChoice;
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

	if ((currentNo + 1) == questions.length) {
		elem.attr("disabled", "disabled");

	}

}

function testBoardNumberButton(elem, questions) {
	var n = elem.text();
	questionNumber = n;
	currentNo = n - 1;

	$("#div-q").html(questionNumber + ".  " + questions[currentNo].qBody);

	var foo = questions[currentNo].userChoice;
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

	if ((currentNo + 1) == questions.length) {
		$('#back').prop("disabled", false);
		$('#next').attr("disabled", "disabled");
	} else {
		$('#next').prop("disabled", false);
	}

	if (questionNumber == 1) {
		$('#back').attr("disabled", "disabled");
	} else {
		$('#back').prop("disabled", false);
	}

}

function ccTitleClick(elem, clay) {
	var dHeading = elem.text();
	var desc = '';
	for (i = 0; i < clay.length; i++) {
		if (clay[i].clusterName == dHeading) {
			desc = clay[i].description;
		}
	}
	$("#modal-body").text(desc.value);
	$('#myModalLabel').text(dHeading);
	$("#description-dialog").modal();
}

function loadResultContent(clay, username, imgUrl) {

	$("#cc-div-content")
			.load(
					'/pages/partials/career-cluster.html',
					function() {

						$('#user-name').text(username);
						$('#today').text(testDate);
						$("#profile-image").attr('src', imgUrl);
						$('#end-test-dialog').modal('hide');
						$('#heading').text('Career Cluster Test Result');
						$('.test-board-footer').hide();
						var obj = createCircleObjects(clay);
						addCircleProgress(obj);
						var dPoints = getDataPoints(clay);
						showChart(dPoints);
						$('.cctitle').on('click', function() {
							ccTitleClick($(this), clay);
						});

						$('#body').css('min-width', '580px');

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
							saveCareerClusters(clay);
						});

					});
}

function saveCareerClusters(clay) {

	var c = removeDescription(clay);
	var clusters = JSON.stringify(c);
	var hasMore = false;

	$
			.ajax({
				url : '/azure/savecareerclusterrecord',
				type : 'POST',
				data : {
					clusters : clusters,
					testDate : testDate
				},
				success : function(data) {
					$("#save-success-dialog").modal();
					$(".close-window").on('click', function() {
						$("#save-success-dialog").modal('hide');
						window.close();
					});
					clusters = data.list;
					hasMore = data.hasMore;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(errorThrown);
				},
				complete : function() {
					var ltn = $("#last-test-name", opener.document);
					ltn.text('CAREER CLUSTERS');
					var ltd = $("#last-test-date", opener.document);
					$("#last-test-date-row", opener.document).show();
					ltd.text(testDate);

					var clusterList = $("#career-clusters-profile",opener.document);

					populateCareerClusterList(clusters,clusterList,hasMore);
				}
			});
}

function finishClick(cluster, questions, username, imgUrl) {

	var clay = loadClusterArray(cluster);

	clay = cummulate(clay, questions);

	loadResultContent(clay, username, imgUrl);

}

function getQuestions(cluster) {
	var questions = [];
	for (x = 0; x < cluster.length; x++) {

		var qs = cluster[x].questions;
		for (y = 0; y < qs.length; y++) {

			questions[questions.length] = {
				qBody : qs[y],
				userChoice : 0,
				clusterName : cluster[x].clusterName
			};

		}
	}

	return questions;
}

function removeDescription(clay) {
	var c = [];
	for (i = 0; i < clay.length; i++) {
		c[i] = {
			clusterName : clay[i].clusterName,
			value : clay[i].choice
		};
	}
	return c;
}

function showChart(dPoints) {
	// alert(dPoints.length);
	var chart = new CanvasJS.Chart("div-2", {

		title : {
			text : "Career Cluster Test Chart",
			fontColor : "blue",
			fontSize : 30,
			padding : 10,
			margin : 15,
			backgroundColor : "#FFFFE0",
			borderThickness : 1,
			cornerRadius : 5,
			fontWeight : "normal"
		},
		axisX : {
			labelMaxWidth : 105,
			title : "Career Clusters",
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
			type : "column",
			showInLegend : true,
			color : "darkgreen",
			legendText : "Very Strong",
			dataPoints : dPoints
		}, {
			type : "column",
			legendText : "Strong",
			color : "olive",
			showInLegend : true,
			dataPoints : []
		}, {
			type : "column",
			legendText : "Fairly Strong",
			color : "orange",
			showInLegend : true,
			dataPoints : []
		}, {
			type : "column",
			legendText : "Weak",
			color : "red",
			showInLegend : true,
			dataPoints : []
		} ]
	});
	chart.render();
}

function getDataPoints(clay) {
	var dps = [];

	for (i = 0; i < clay.length; i++) {

		var choice = clay[i].choice;
		var clr = null;
		var name = clay[i].clusterName;

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

function addCircleProgress(obj) {

	$('#header1').text(obj[0].clusterName);
	$('#header2').text(obj[1].clusterName);
	$('#header3').text(obj[2].clusterName);
	$('#header4').text(obj[3].clusterName);
	$('#header5').text(obj[4].clusterName);
	$('#header6').text(obj[5].clusterName);
	$('#header7').text(obj[6].clusterName);
	$('#header8').text(obj[7].clusterName);

	$('#comment1').text(obj[0].comment);
	$('#comment2').text(obj[1].comment);
	$('#comment3').text(obj[2].comment);
	$('#comment4').text(obj[3].comment);
	$('#comment5').text(obj[4].comment);
	$('#comment6').text(obj[5].comment);
	$('#comment7').text(obj[6].comment);
	$('#comment8').text(obj[7].comment);

	$("#phead1").css('background-color', obj[0].color);
	$("#phead2").css('background-color', obj[1].color);
	$("#phead3").css('background-color', obj[2].color);
	$("#phead4").css('background-color', obj[3].color);
	$("#phead5").css('background-color', obj[4].color);
	$("#phead6").css('background-color', obj[5].color);
	$("#phead7").css('background-color', obj[6].color);
	$("#phead8").css('background-color', obj[7].color);

	$("#progress1")
			.shieldProgressBar(
					{
						min : 0,
						max : 100,
						value : (obj[0].val * 5),
						layout : "circular",
						layoutOptions : {
							circular : {
								borderColor : "orange",
								width : 17,
								borderWidth : 3,
								color : "green",
								backgroundColor : "transparent"
							}
						},
						text : {
							enabled : true,
							template : '<span style="font-size:52px;color:green;">{0:n0}%</span>'
						}
					});
	$("#progress2")
			.shieldProgressBar(
					{
						min : 0,
						max : 100,
						value : (obj[1].val * 5),
						layout : "circular",
						layoutOptions : {
							circular : {
								borderColor : "orange",
								width : 17,
								borderWidth : 3,
								color : "green",
								backgroundColor : "transparent"
							}
						},
						text : {
							enabled : true,
							template : '<span style="font-size:52px;color:green;">{0:n0}%</span>'
						}
					});
	$("#progress3")
			.shieldProgressBar(
					{
						min : 0,
						max : 100,
						value : (obj[2].val * 5),
						layout : "circular",
						layoutOptions : {
							circular : {
								borderColor : "orange",
								width : 17,
								borderWidth : 3,
								color : "green",
								backgroundColor : "transparent"
							}
						},
						text : {
							enabled : true,
							template : '<span style="font-size:52px;color:green;">{0:n0}%</span>'
						}
					});
	$("#progress4")
			.shieldProgressBar(
					{
						min : 0,
						max : 100,
						value : (obj[3].val * 5),
						layout : "circular",
						layoutOptions : {
							circular : {
								borderColor : "orange",
								width : 17,
								borderWidth : 3,
								color : "green",
								backgroundColor : "transparent"
							}
						},
						text : {
							enabled : true,
							template : '<span style="font-size:52px;color:green;">{0:n0}%</span>'
						}
					});
	$("#progress5")
			.shieldProgressBar(
					{
						min : 0,
						max : 100,
						value : (obj[4].val * 5),
						layout : "circular",
						layoutOptions : {
							circular : {
								borderColor : "orange",
								width : 17,
								borderWidth : 3,
								color : "green",
								backgroundColor : "transparent"
							}
						},
						text : {
							enabled : true,
							template : '<span style="font-size:52px;color:green;">{0:n0}%</span>'
						}
					});
	$("#progress6")
			.shieldProgressBar(
					{
						min : 0,
						max : 100,
						value : (obj[5].val * 5),
						layout : "circular",
						layoutOptions : {
							circular : {
								borderColor : "orange",
								width : 17,
								borderWidth : 3,
								color : "green",
								backgroundColor : "transparent"
							}
						},
						text : {
							enabled : true,
							template : '<span style="font-size:52px;color:green;">{0:n0}%</span>'
						}
					});
	$("#progress7")
			.shieldProgressBar(
					{
						min : 0,
						max : 100,
						value : (obj[6].val * 5),
						layout : "circular",
						layoutOptions : {
							circular : {
								borderColor : "orange",
								width : 17,
								borderWidth : 3,
								color : "green",
								backgroundColor : "transparent"
							}
						},
						text : {
							enabled : true,
							template : '<span style="font-size:52px;color:green;">{0:n0}%</span>'
						}
					});
	$("#progress8")
			.shieldProgressBar(
					{
						min : 0,
						max : 100,
						value : (obj[7].val * 5),
						layout : "circular",
						layoutOptions : {
							circular : {
								borderColor : "orange",
								width : 17,
								borderWidth : 3,
								color : "green",
								backgroundColor : "transparent"
							}
						},
						text : {
							enabled : true,
							template : '<span style="font-size:52px;color:green;">{0:n0}%</span>'
						}
					});

}

function getColors(cls) {
	var comms = [];
	for (i = 0; i < cls.length; i++) {
		if ((cls[i].choice * 5) >= 80) {
			comms[i] = 'darkgreen';
		} else if ((cls[i].choice * 5) >= 65) {
			comms[i] = 'olive';
		} else if ((cls[i].choice * 5) >= 50) {
			comms[i] = 'orange';
		} else {
			comms[i] = 'red';
		}
	}
	return comms;
}

function getClusterComments(cls) {
	var comms = [];
	for (i = 0; i < cls.length; i++) {
		if ((cls[i].choice * 5) >= 80) {
			comms[i] = 'Very strong';
		} else if ((cls[i].choice * 5) >= 65) {
			comms[i] = 'Strong';
		} else if ((cls[i].choice * 5) >= 50) {
			comms[i] = 'Fairly strong';
		} else {
			comms[i] = 'Weak';
		}
	}
	return comms;
}

function trucClusterNames(cls) {
	var cName = [];
	for (j = 0; j < cls.length; j++) {
		cName[j] = cls[j].clusterName;
	}

	for (i = 0; i < cName.length; i++) {
		if (cName[i].length > 100) {
			cName[i] = cName[i].substring(0, 18);
			cName[i] = cName[i] + "...";
		}
	}

	return cName;
}

function sortStrengths(clay) {

	var strs = [];
	for (i = 0; i < clay.length; i++) {
		strs[i] = clay[i];
	}
	var choices = [];
	var sorted = [];
	for (i = 0; i < strs.length; i++) {
		choices[i] = strs[i].choice;
	}

	choices.sort(function(a, b) {
		return b - a
	});

	for (i = 0; i < choices.length; i++) {

		for (j = 0; j < strs.length; j++) {

			if (choices[i] == strs[j].choice) {

				sorted[i] = strs[j];
				strs.splice(j, 1);
				break;
			}
		}
	}
	return sorted;

}

function createCircleObjects(clay) {

	var cls = sortStrengths(clay);
	var clusterNames = trucClusterNames(cls);
	var comments = getClusterComments(cls);
	var bColors = getColors(cls);

	var obj1 = {
		clusterName : clusterNames[0],
		comment : comments[0],
		val : cls[0].choice,
		color : bColors[0]
	};
	var obj2 = {
		clusterName : clusterNames[1],
		comment : comments[1],
		val : cls[1].choice,
		color : bColors[1]
	};
	var obj3 = {
		clusterName : clusterNames[2],
		comment : comments[2],
		val : cls[2].choice,
		color : bColors[2]
	};
	var obj4 = {
		clusterName : clusterNames[3],
		comment : comments[3],
		val : cls[3].choice,
		color : bColors[3]
	};
	var obj5 = {
		clusterName : clusterNames[4],
		comment : comments[4],
		val : cls[4].choice,
		color : bColors[4]
	};
	var obj6 = {
		clusterName : clusterNames[5],
		comment : comments[5],
		val : cls[5].choice,
		color : bColors[5]
	};
	var obj7 = {
		clusterName : clusterNames[6],
		comment : comments[6],
		val : cls[6].choice,
		color : bColors[6]
	};
	var obj8 = {
		clusterName : clusterNames[7],
		comment : comments[7],
		val : cls[7].choice,
		color : bColors[7]
	};

	var objs = [ obj1, obj2, obj3, obj4, obj5, obj6, obj7, obj8 ];
	return objs;
}

function cummulate(clay, questions) {

	for (i = 0; i < questions.length; i++) {
		var name = questions[i].clusterName;
		var userChoice = questions[i].userChoice;
		for (j = 0; j < clay.length; j++) {
			if (name == clay[j].clusterName) {
				clay[j].choice += Number(userChoice);
			}
		}

	}

	return clay;

}

function loadClusterArray(cluster) {
	var ca = [];
	for (i = 0; i < cluster.length; i++) {

		ca[ca.length] = {
			choice : 0,
			clusterName : cluster[i].clusterName,
			description : cluster[i].description
		};

	}

	return ca;
}
function startTest(data) {
	$("#div-q").html(questionNumber + ".  " + data[currentNo].qBody);
	testDate = new Date();
}

function shuffle(o) { // v1.0
	for (var j, x, i = o.length; i; j = Math.floor(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x)
		;
	return o;
}
