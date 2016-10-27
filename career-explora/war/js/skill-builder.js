var questionNumber = 1;
var currentNumber = 0;
var testDate = null;

$(document).ready(function() {
	var userid = window.userid;
	var username = window.username;
	var imgUrl = window.imgUrl;
	var vStrong = null;
	var strong = null;

	$(".profile-img").attr('src', imgUrl);

	$.ajax({
		url : '/azure/getskillbuilder',
		type : "GET",
		dataType : "Json",
		success : function(data) {

			vStrong = data.VS;
			strong = data.S;
			initStartPage(vStrong, strong, username, imgUrl);
		},
		error : function(jqXHR, status, errorThrown) {
			alert(errorThrown);
		}

	});

});

function initStartPage(vStrong, strong, username, imgUrl) {
	var vs = getTalents(vStrong);
	vs = unique(vs);
	$("#no-vs").text(vs.length);
	var st = getTalents(strong);
	st = unique(st);
	$("#no-s").text(st.length);
	$("#total-skill").text(vStrong.length + strong.length);
	showVeryStrong(vs);
	showStrong(st);
	var allTalents = vStrong.concat(strong);

	$("#back").prop("disabled", true);
	$("#next").prop("disabled", true);
	$("#finish").prop("disabled", true);

	var skills = null;

	$(".talent-skill").on('click', function() {
		var talentName = $(this).text();
		skills = getSkills(talentName, allTalents)
		startTest(talentName, skills);
	});

	$(".opt").click(function() {
		optionClick($(this), skills);
	});

	$("#back").click(function() {
		backClick($(this), skills);
	});

	$('#next').on('click', function() {
		nextClick($(this), skills);
	});

	$("#finish").click(function() {
		$('#end-test-dialog').modal();
	});

	$('.submit-test').on('click', function() {
		$('#end-test-dialog').modal('hide');
		finishClick(skills, username, imgUrl);
	});

}

function finishClick(skills, username, imgUrl) {

	var sr = getSkillsRecord(skills);
	var hb = getHaveBuiltList(sr);
	var tb = getToBuildList(sr);
	var wb = getWontBuildList(sr);
	var nd = getNotDecidedList(sr);

	$(".opt").prop("checked", false);
	$('.test-board-footer').hide();
	$('.heading').text('Skills Builder Report');
	$("body").css('background-color', 'white');
	$("#cc-div-content").css('background-color', 'white');

	$("#cc-div-content")
			.load(
					'/pages/partials/skill-builder.html',
					function() {

						$('#user-name').text(username);
						$('#today').text(testDate);
						$("#profile-image").attr('src', imgUrl);

						showHaveBuilt(hb);
						showToBuild(tb);
						showWontBuild(wb);
						showNotDecided(nd);

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
							saveSkills(hb, tb, wb, nd);
						});
					});

}

function saveSkills(hb, tb, wb, nd) {
	var hb = JSON.stringify(hb);
	var tb = JSON.stringify(tb);
	var wb = JSON.stringify(wb);
	var nd = JSON.stringify(nd);
	var hasMoreHB = false;
	var hasMoreTB = false;
	$
			.ajax({
				url : '/azure/saveskills',
				type : 'POST',
				data : {
					hb : hb,
					tb : tb,
					wb : wb,
					nd : nd,
					testDate : testDate
				},
				success : function(data) {
					$("#save-success-dialog").modal();
					$(".close-window").on('click', function() {
						$("#save-success-dialog").modal('hide');
						window.close();
					});
					hb = data.learntSkills;
					tb = data.skillToLearn;
					hasMoreHB = data.hasMorelearntSkill;
					hasMoreTB = data.hasMoreSkillToLearn;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(errorThrown);
				},
				complete : function() {
					var ltn = $("#last-test-name", opener.document);
					ltn.text('SKILL BUILDER');
					var ltd = $("#last-test-date", opener.document);
					$("#last-test-date-row", opener.document).show();
					ltd.text(testDate);

					var sbList = $("#skills-built-profile", opener.document);

					populateSkillsBuiltList(hb,sbList,hasMoreHB);

					var tbList = $("#skills-to-build-profile", opener.document);

					populateSkillsToBuildList(tb, tbList, hasMoreTB);
				}
			});
}

function showHaveBuilt(hb) {
	for (i = 0; i < hb.length; i++) {
		$('#have-built-list').append(
				'<li class="list-group-item description to-pointer"><div>'
						+ hb[i].name + '</div></li>');
	}
}

function showToBuild(tb) {
	for (i = 0; i < tb.length; i++) {
		$('#to-build-list').append(
				'<li class="list-group-item description to-pointer"><div>'
						+ tb[i].name + '</div></li>');
	}
}

function showWontBuild(wb) {
	for (i = 0; i < wb.length; i++) {
		$('#wont-build-list').append(
				'<li class="list-group-item description to-pointer"><div>'
						+ wb[i].name + '</div></li>');
	}
}

function showNotDecided(nd) {
	for (i = 0; i < nd.length; i++) {
		$('#not-decided-list').append(
				'<li class="list-group-item description to-pointer"><div>'
						+ nd[i].name + '</div></li>');
	}
}

function getHaveBuiltList(sr) {
	var hbsr = [];
	for (i = 0; i < sr.length; i++) {
		if (sr[i].value == 4) {
			hbsr[hbsr.length] = sr[i];
		}
	}
	return hbsr;
}

function getToBuildList(sr) {
	var hbsr = [];
	for (i = 0; i < sr.length; i++) {
		if (sr[i].value == 3) {
			hbsr[hbsr.length] = sr[i];
		}
	}
	return hbsr;
}

function getWontBuildList(sr) {
	var hbsr = [];
	for (i = 0; i < sr.length; i++) {
		if (sr[i].value == 2) {
			hbsr[hbsr.length] = sr[i];
		}
	}
	return hbsr;
}

function getNotDecidedList(sr) {
	var hbsr = [];
	for (i = 0; i < sr.length; i++) {
		if (sr[i].value <= 1) {
			hbsr[hbsr.length] = sr[i];
		}
	}
	return hbsr;
}

function getSkillsRecord(skills) {
	var sr = [];
	for (i = 0; i < skills.length; i++) {
		sr[i] = {
			name : skills[i].name,
			value : skills[i].choice
		};
	}
	return sr;
}

function startTest(talentName, skills) {

	loadTest(talentName, skills);
	testDate = new Date();
}

function loadTest(talentName, skills) {
	$("#skill-div-1").text(talentName);
	$("#skill-div-2").text(skills[currentNumber].name);
	$("#skill-div-2").css('margin', '2%');
	$("#skill-div-3").hide();
	$(".talent-skill-desc").css('visibility', 'visible');
	$("#skill-div-3a").html(skills[currentNumber].desc);
	$(".talent-skill-options").css('visibility', 'visible');
	$("#talent-skill-info-1").css('visibility', 'hidden');
	$("#talent-skill-info-2").css('visibility', 'visible');

	$("#qN").text(questionNumber);
	$("#tN").text(skills.length);
	$("#next").prop("disabled", false);
	$("#finish").prop("disabled", false);
	$("#back").prop("disabled", false);

}

function optionClick(elem, skills) {
	var val = elem.val();
	skills[currentNumber].choice = val;
}

function nextClick(elem, skills) {

	currentNumber++;
	questionNumber++;

	$("#skill-div-2").text(skills[currentNumber].name);
	$("#skill-div-3a").html(skills[currentNumber].desc);

	if ((currentNumber + 1) == skills.length) {
		elem.attr("disabled", "disabled");

	}

	$("#qN").text(questionNumber);

	var foo = skills[currentNumber].choice;
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
function backClick(elem, skills) {
	if (questionNumber == 1) {

		$('#go-back-dialog').modal();
		$('.go-back').on('click', function() {
			$(".opt").prop("checked", false);
			loadStartPage();

		});
	} else {

		if ($("#next").prop("disabled")) {
			$("#next").prop("disabled", false);
		}
		currentNumber--;
		questionNumber--;
		$("#skill-div-2").text(skills[currentNumber].name);
		$("#skill-div-3a").html(skills[currentNumber].desc);

		$("#qN").text(questionNumber);

		var foo = skills[currentNumber].choice;
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

}

function loadStartPage() {
	$('#go-back-dialog').modal('hide');
	$("#skill-div-1").text('Your Talents');
	$("#skill-div-2").text('');
	$("#skill-div-2").css('margin', '0');
	$("#skill-div-3").show();
	$(".talent-skill-desc").css('visibility', 'hidden');
	$("#skill-div-3a").html('');
	$(".talent-skill-options").css('visibility', 'hidden');
	$("#talent-skill-info-2").css('visibility', 'hidden');
	$("#talent-skill-info-1").css('visibility', 'visible');

	$("#back").prop("disabled", true);
	$("#next").prop("disabled", true);
	$("#finish").prop("disabled", true);
}

function getSkills(talentName, allTalents) {
	var skills = [];
	for (i = 0; i < allTalents.length; i++) {
		if (talentName.trim() == allTalents[i].talentName) {
			skills[skills.length] = {
				name : allTalents[i].name,
				desc : allTalents[i].description,
				choice : 0
			};
		}
	}
	return skills;
}

function getTalents(tList) {
	var t = [];
	for (i = 0; i < tList.length; i++) {
		t[t.length] = tList[i].talentName;
	}
	return t;
}

function unique(list) {
	var result = [];
	$.each(list, function(i, e) {
		if ($.inArray(e, result) == -1)
			result.push(e);
	});
	return result;
}

function showVeryStrong(vs) {
	var a = '';
	var b = '';
	for (i = 0; i < vs.length; i += 2) {
		a = vs[i];
		b = vs[i + 1];
		if (b === undefined) {
			b = "";
		}
		$('#very-strong-list')
				.append(
						'<li class="list-group-item"><div class="row"><span class="col-xs-6 col-sm-6 talent-skill">'
								+ a
								+ '</span><span class="col-xs-6 col-6 talent-skill">'
								+ b + '</span6</div></li>');
	}
}

function showStrong(vs) {
	var a = '';
	var b = '';
	for (i = 0; i < vs.length; i += 2) {
		a = vs[i];
		b = vs[i + 1];
		if (b === undefined) {
			b = "";
		}
		$('#strong-list')
				.append(
						'<li class="list-group-item"><div class="row"><span class="col-xs-6 col-sm-6 talent-skill">'
								+ a
								+ '</span><span class="col-xs-6 col-6 talent-skill">'
								+ b + '</span6</div></li>');
	}
}