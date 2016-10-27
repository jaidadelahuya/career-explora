var styles = null;
var clusters = null;
var values = null;
var skillsToLearn = null;
var skillsLearnt = null;
var mits = null;
var talents = null;

$(document)
		.ready(
				function() {
					
					$("#show-career-cluster-report").click(function(){
						$.ajax({
							url : "/azure/profile/careercluster/report",
							success : function(data) {
								window.open("/azure/profile/career-clusters/report/view","Career Clusters Test Report",
										"toolbar=yes, scrollbars=yes, fullscreen=1");
							},
							error : function(jqXHR, status, errorThrown) {
								//alert(errorThrown);
							}
						});
					});
					
					$("#show-talents-report").click(function(){
						$.ajax({
							url : "/azure/profile/talent/report",
							success : function(data) {
								window.open("/azure/profile/talent/report/view","Talent Hunt Test Report",
										"toolbar=yes, scrollbars=yes, fullscreen=1");
							},
							error : function(jqXHR, status, errorThrown) {
								//alert(errorThrown);
							}
						});
					});
					
					$("#show-mits-report").click(function() {
						$.ajax({
							url : "/azure/profile/mit/report",
							success : function(data) {
								window.open("/azure/profile/mit/report/view","Multiple Intelligence Test Report",
										"toolbar=yes, scrollbars=yes, fullscreen=1");
							},
							error : function(jqXHR, status, errorThrown) {
								//alert(errorThrown);
							}
						});

					});

					$("#profile-dob").datepicker();

					$(".edit-tool")
							.on(
									'click',
									function(e) {
										var et = $(this);
										var par = et.parent();
										var propertyType = par.prop('id');
										var ev = par
												.children(".editable-value");
										var iden = ev.prop('id');
										et.hide();

										if (iden === "livesin-value") {
											var st = $("#hidden-state");
											var ctry = $("#hidden-country");
											var sv = $("#profile-state");
											var cv = $("#profile-country");
											var osv = sv.text();
											var ocv = cv.text();
											var comma = $("#lives-in-comma");
											sv.text("");
											cv.text("");
											comma.hide();
											st.removeClass('hidden');
											ctry.removeClass('hidden');
											st.select();
											st.on('keyup', function(e) {
												if (e.keyCode == 13) {
													ctry.select();
												}
											});

											ctry
													.on(
															"keyup",
															function(e) {
																if (e.keyCode == 13) {
																	var nsv = st
																			.val();
																	var ncv = ctry
																			.val();

																	$
																			.ajax({
																				url : '/azure/editprofile',
																				type : 'POST',
																				data : {
																					"propertyType" : propertyType,
																					"state" : nsv,
																					"country" : ncv
																				},
																				success : function(
																						data) {
																					st
																							.addClass("hidden");
																					ctry
																							.addClass("hidden");
																					sv
																							.text(nsv
																									.toUpperCase());
																					comma
																							.show();
																					cv
																							.text(ncv
																									.toUpperCase());
																					et
																							.show();
																					$(
																							'#p-state')
																							.text(
																									nsv
																											.toUpperCase());
																					$(
																							'#p-country')
																							.text(
																									ncv
																											.toUpperCase());
																				},
																				error : function(
																						jqXHR,
																						status,
																						errorthrown) {
																					st
																							.addClass("hidden");
																					ctry
																							.addClass("hidden");
																					sv
																							.text(osv);
																					cv
																							.text(ocv);
																					et
																							.show();
																					alert("Could not save your location, try again later.");
																				}

																			});
																}
															});

										} else {
											var oldVal = ev.text();
											ev.text("");
											var ei = par.children(".h-input");
											ei.removeClass("hidden");
											ei.select();
											ei.off();

											ei
													.on(
															'keyup',
															function(e) {
																if (e.keyCode == 13) {

																	var newVal = ei
																			.val();
																	if (newVal
																			.trim() === oldVal
																			.trim()) {
																		ei
																				.addClass("hidden");
																		ev
																				.text(oldVal);
																		et
																				.show();
																	} else {
																		$
																				.ajax({
																					url : '/azure/editprofile',
																					type : 'POST',
																					data : {
																						"propertyType" : propertyType,
																						"property" : newVal
																					},
																					success : function(
																							data) {
																						ei
																								.addClass("hidden");
																						if (propertyType === "profile-email"
																								| propertyType === "profile-gender") {
																							ev
																									.text(newVal
																											.toLowerCase());
																						} else {

																							ev
																									.text(newVal
																											.toUpperCase());
																						}
																						et
																								.show();
																						updateProfilePage(
																								propertyType,
																								newVal);
																					},
																					error : function(
																							jqXHR,
																							status,
																							errorThrown) {
																						ei
																								.addClass("hidden");
																						ev
																								.text(oldVal);
																						et
																								.show();
																						alert(errorThrown);
																					}
																				});
																	}
																}
															});

										}
									});
					
					

					$(".editable").mouseenter(function() {
						var l = $(this);
						l.children(".edit-tool").removeClass('hidden');
					});

					$(".editable").mouseleave(function() {
						var l = $(this);
						l.children(".edit-tool").addClass('hidden');
					});

					$("#show-me-more-styles").on('click', function() {
						
						showMoreStyles($(this));

					});

					$("#show-me-more-values").on('click', function() {

						var $body = $("#career-values-profile");
						startWaitMe($body, "violet");
						var name = $(this).prop("name");
						getMoreProfileInfo(name, $body, "violet-list");
					});

					$("#show-me-more-skills-to-learn").on('click', function() {

						var $body = $("#skills-to-build-profile");
						startWaitMe($body, "orange");
						var name = $(this).prop("name");
						getMoreProfileInfo(name, $body, "orange-list");
					});

					$("#show-me-more-skills-learnt").on('click', function() {
						var $body = $("#skills-built-profile");
						startWaitMe($body, "green");
						var name = $(this).prop("name");
						getMoreProfileInfo(name, $body, "green-list");
					});

					$("#show-me-more-talents").on('click', function() {
						var $body = $("#talent-profile");
						startWaitMe($body, "#5bc0de");
						var name = $(this).prop("name");
						getMoreProfileInfo(name, $body, "indigo-list");
					});

					$("#show-me-more-mits").on('click', function() {
						var $body = $("#mit-profile");
						startWaitMe($body, "red");
						var name = $(this).prop("name");
						getMoreProfileInfo(name, $body, "red-list");
					});

					$("#show-me-more-clusters").on('click', function() {
						var $body = $("#career-clusters-profile");
						startWaitMe($body, "rgb(255,211,0)");
						var name = $(this).prop("name");
						getMoreProfileInfo(name, $body, "yellow-list");
					});

				});

function startWaitMe(elem, color) {
	elem.waitMe({
		effect : 'ios',
		color : color,
		sizeW : '15',
		sizeH : '15'
	});
}

function updateProfilePage(propertyType, newVal) {
	if (propertyType === "profile-first-name") {
		$("#pop-user-first-name").text(newVal.toUpperCase());
		$("#user-first-name").text(newVal.toUpperCase());
	} else if (propertyType === "profile-last-name") {
		$("#pop-user-last-name").text(newVal.toUpperCase());
		$("#user-last-name").text(newVal.toUpperCase());
	} else if (propertyType === "profile-email") {
		$("#pop-user-email").text(newVal.toLowerCase());

	} else if (propertyType === "profile-school") {
		$("#user-school").text(newVal.toUpperCase());
	} else if (propertyType === "livesIn") {

	}
}

function getMoreProfileInfo(name, waitMeDiv, listColor) {

	$.ajax({
		url : "/azure/moreprofileinfo",
		type : "POST",
		data : {
			name : name
		},
		dataType : "Json",
		success : function(data) {

			return updateDiv(data, waitMeDiv, listColor);
		},
		error : function(jqXHR, status, errorThrown) {
			alert(errorThrown);
		},
		complete : function() {
			waitMeDiv.waitMe('hide');
		}
	});

}

function updateDiv(list, waitMeDiv, listColor) {

	waitMeDiv.find("li").remove();
	for (i = 0; i < list.length; i++) {
		waitMeDiv.append('<li class="list-group-item ' + listColor
				+ '"><div><span>' + list[i] + '</span></div></li>');
	}

}

function showMoreStyles(elem) {
	var $body = $("#personal-style-profile");
	startWaitMe($body, "blue");
	var name = elem.prop("name");
	getMoreProfileInfo(name, $body, "blue-list");
}
