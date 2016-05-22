$(document)
		.ready(
				function() {
					
					
					document.getElementById("country").defaultValue = "Nigeria";
					
					$("#pic2").change(function() {
						$("#upload-cover").prop('disabled',false);
					});
					
					$("#pic1").change(function() {
						$("#upload-picture").prop('disabled',false);
					});

					$("#cover-div").click(function(e) {

						e.preventDefault();
						jqxhr = $.get("/getuploadurl").done(function(data) {
							$("#cover-picture-dialog").modal();
							$("#cover-picture-form").prop('action', data);
							console.log(data);
						}).error(function() {
							alert("error");
						});

					});

					$(".profile-img").mouseenter(function(e) {
						$(this).css("cursor", "pointer");
					});

					$(".profile-img").click(function(e) {

						e.preventDefault();
						jqxhr = $.get("/getuploadurl").done(function(data) {
							$("#profile-photo-dialog").modal();
							$("#profile-picture-form").prop('action', data);
							console.log(data);
						}).error(function() {
							alert("error");
						});

					});

					$("#upload-cover").click(function(e) {
						$(this).prop('disabled',true);
						$("#cover-picture-form").submit();
					});

					$("#cover-picture-form").on('submit', function(e) {
						var cov = $("#cover-picture-form");
						cov.waitMe({
							effect : 'ios',
							color : 'orange',
							sizeW : '15',
							sizeH : '15'
						});
						var x = $(this).attr('action');

						$.ajax({
							url : x,
							type : 'POST',
							data : new FormData(this),
							processData : false,
							contentType : false,
							success : function(data) {
								
								$("#background-img").prop('src', data);
								cov.waitMe('hide');
								$("#upload-cover").prop('disabled',false);
								$("#cover-picture-dialog").modal("hide");
							},
							error : function(jqXHR, status, errorThrown) {
								alert("Could not upload your picture");
								cov.waitMe('hide');
								$("#upload-cover").prop('disabled',false);
							}
						});
						e.preventDefault();
					});

					$("#upload-picture").click(function(e) {
						$("#profile-picture-form").submit();
					});

					$("#profile-picture-form").on('submit', function(e) {
						var x = $(this).attr('action');
						var pht = $("#profile-picture-form");
						pht.waitMe({
							effect : 'ios',
							color : 'orange',
							sizeW : '15',
							sizeH : '15'
						});

						$.ajax({
							url : x,
							type : 'POST',
							data : new FormData(this),
							processData : false,
							contentType : false,
							success : function(data) {
								console.log(data);
								$(".profile-img").prop('src', data);
								pht.waitMe('hide');
								$("#profile-photo-dialog").modal('hide');

							},
							error : function(jqXHR, status, errorThrown) {
								alert("Could not upload your picture");
								pht.waitMe('hide');
							}
						});
						e.preventDefault();
					});

					$('.view-subscription-details').on('click', function() {
						$('#subscription-details-dialog').modal();

					});

					$('.delete-uct')
							.on(
									'click',
									function() {
										var ok = confirm("Do you want to delete this test entry?");

										if (ok) {
											var elem = $(this);
											var testName = elem.prop('name');
											console.log(testName);
											$
													.ajax({
														url : '/azure/deleteupcomingtest',
														type : 'POST',
														data : {
															"keyString" : testName
														},
														success : function(data) {
															elem.closest('li')
																	.remove();
														},
														error : function(jqXHR,
																status,
																errorThrown) {
															alert(errorThrown);
														}

													});
										}

									});

					$("#new-up-test").on('click', function() {
						console.log("called me");
						showUpComingTestDialog();
					});

					$('#profile-name').on('click', function() {
						$('#profile-name-dialog').modal();
					});

					$(".more-friends").on("click", function(event) {
						event.preventDefault();
						getMoreFriends();
					});

					$("[data-toggle='tooltip']").tooltip();

					$('.profile-location').click(function() {

						$('#profile-location-dialog').modal();

					});

					$("#add-new-topic")
							.on(
									'click',
									function() {
										$("#up-table").show();
										var $body = $("#uptable-body");
										$body
												.prepend("<tr><td class='uptopic'><span class='upcoming-topic'></span></td><td align='center'><input type='checkbox' /></td><td><a class='up-delete'>delete</a></td></tr>");
										var $targetCell = $body.children()
												.first().children().first();
										$targetCell.addClass("cellEditing");
										$targetCell
												.html("<input type='text' />");
										$targetCell.children().first().focus();

										var OriginalContent = '';
										configTargetCell($targetCell,
												OriginalContent);

										$targetCell.on('dblclick', function() {
											OriginalContent = $(this).text();
											$(this).addClass("cellEditing");
											$(this).html(
													"<input type='text' value='"
															+ OriginalContent
															+ "' />");
											$(this).children().first().focus();
											configTargetCell($targetCell,
													OriginalContent);

										});

									});

				});

function showUpComingTestDialog() {
	$('.error-message').hide();
	$('#upcoming-test-dialog').modal();
	$("#test-date").datepicker();
	$("#prep-deadline").datepicker();
	$("#final-deadline").datepicker();
}

function getMoreFriends() {

	var list = $(".fmk-lists");
	list.waitMe({
		effect : 'ios',
		color : 'orange',
		sizeW : '15',
		sizeH : '15'
	});

	$.ajax({
		url : '/azure/getmorefriends',
		type : 'GET',
		success : function(data) {
			setYmk(data);
		},
		error : function(jqXHR, status, errorThrown) {
			alert(errorThrown);
		}, complete: function() {
			list.waitMe("hide");
		}
	});
}

function setYmk(data) {
	console.log(data);
	// check if the list size is up to three
	$(".suggested-friend-picture-1").prop('src', data[0].picture);
	$(".suggested-friend-picture-2").prop('src', data[1].picture);
	$(".suggested-friend-picture-3").prop('src', data[2].picture);

	$(".fmk-name-1").text(data[0].firstName + " " + data[0].lastName);
	$(".fmk-name-2").text(data[1].firstName + " " + data[1].lastName);
	$(".fmk-name-3").text(data[2].firstName + " " + data[2].lastName);

	$(".profile-id-1").val(data[0].id);
	$(".profile-id-2").val(data[1].id);
	$(".profile-id-3").val(data[2].id);

	$(".fmk-lists").waitMe('hide');
}

function validateDate(elem, err) {

	var dob = elem.val();
	var millis = Date.parse(dob);
	var date1 = new Date(millis);

	var date2 = new Date();
	var isFuture = (date1 > date2) ? true : false;

	if (isFuture) {
		elem.closest('div').addClass('has-error');
		err.text('The date you entered is in the future');
		return false;
	} else {
		elem.closest('div').removeClass('has-error');
		err.text('');
		return true;
	}

}

function validateFutureDate(elem, err) {

	var dob = elem.val();
	var millis = Date.parse(dob);
	var date1 = new Date(millis);

	var date2 = new Date();
	var isFuture = (date1 > date2) ? true : false;

	if (isFuture) {
		elem.closest('div').removeClass('has-error');
		err.text('');
		return true;

	} else {
		elem.closest('div').addClass('has-error');
		err.text('The date you entered is in the past');
		return false;
	}

}

function dobChanged() {
	validateDate($("#date-of-birth"), $("#dob-error-div"));
}

function editLocation() {
	$("#edit-location-form").waitMe({
		effect : 'ios',
		color : 'orange',
		sizeW : '15',
		sizeH : '15'
	});
	saveLocation();
}

function saveLocation() {
	var myForm = $("#edit-location-form");
	var $diag = $('#profile-location-dialog');
	var jqxhr = $
			.post("/azure/editlocation", myForm.serialize(), function() {

			})
			.done(function(data) {
				editSchoolName(data.school, data.attends);
				editProfileLocation(data.state, data.country);
			})
			.fail(
					function(jqXHR, status, errorThrown) {
						myForm.waitMe('hide');
						alert("Could not update you school/location on server.Try again later");
					}).always(function() {

				myForm.waitMe('hide');
				$diag.modal('hide');
				clearForm(myForm);
			});

}

function editProfileLocation(state, country) {
	
	$("#lv").hide();
	var $loc = $("#lives-name");
	
	if (state == null && country != null) {
		$loc.text("Lives in " + country);
	} else if (state != null && country == null) {
		$loc.text("Lives in " + state);
	} else if (state != null && country != null) {
		$loc.text("Lives in " + state + ", " + country);
	}
}

function editSchoolName(sch, attd) {

	if (sch === "") {
		return;
	} else {
		$("#not-set-div").remove();

		if (sch.length > 20) {
			sch = sch.substring(0, 18);
			sch = sch + "...";
		}

		$("#sch-name").html(attd + " " + sch + "<br/>");
	}

}

function upcomingTestDateChanged() {

	validateFutureDate($("#test-date"), $("#upcoming-testdate-error-div"));
}

function prepDeadlineChanged() {
	validateFutureDate($("#prep-deadline"), $("#prep-deadline-error-div"));
}

function saveUpComingTest() {

	var prepD = $('#prep-deadline');
	var testDate = $("#test-date");
	var err = $("#prep-deadline-error-div");

	var pd = prepD.val();
	var td = testDate.val();
	var millis = Date.parse(pd);
	var date1 = new Date(millis);
	millis = Date.parse(td);
	var date2 = new Date(millis);

	var pdIsLesser = (date1 < date2) ? true : false;
	console.log(pdIsLesser);
	if (pdIsLesser) {
		prepD.closest('div').removeClass('has-error');
		err.text('');
		submitUpComingTest();
	} else {
		prepD.closest('div').addClass('has-error');
		err.text('Preparation dealine is farther than test date');
		return false;
	}

	$("#upcoming-test-form").waitMe({
		effect : 'ios',
		color : 'orange',
		sizeW : '15',
		sizeH : '15'
	});

}

function submitUpComingTest() {
	var myForm = $('#upcoming-test-form');
	var $diag = $('#upcoming-test-dialog');
	var jqxhr = $
			.post("/azure/saveupcomingtest", myForm.serialize(), function() {

			}, 'Json')
			.done(function(data) {

				console.log(data);

				updateUpComingTestSection(data);

			})
			.fail(
					function(jqXHR, status, errorThrown) {
						myForm.waitMe('hide');
						alert("We could not save your test entry on the server, Please try again later.");
					}).always(function() {
				myForm.waitMe('hide');
				$diag.modal('hide');
				clearForm(myForm);
			});

}

function updateUpComingTestSection(data) {
	var def = $("#uct-default");
	if (def.is(":visible")) {
		def.remove();
		var ucts = $("#upcoming-tests-section");
		var ct = '<li class="list-group-item green-list" id="test-size-li">'
				+ '<strong style="font-family: georgia; color: gray;" id="test-size">'
				+ '</strong></li>';
		ucts.prepend(ct);
	}
	var testSizeLi = $("#test-size-li");
	$("#test-size").text("You have " + data.length + " test(s)");
	console.log("called xxx");
	var content = "<li class='list-group-item green-list'><div class='topic-div'><strong class='upcoming-testname' style='background-color:green'>"
			+ data.uct.testName
			+ "</strong><br/><span class='daysleft'><strong class='nodays' style = color:"
			+ data.uct.color
			+ ">"
			+ data.uct.daysLeft
			+ "</strong> day(s) countdown.</span><br/>"
			+ "<span><a href='#' class='edit-topic' name='"
			+ data.uct.id
			+ "'>edit</a> <a class='to-pointer delete-uct' name='"
			+ "'>delete</a></span><br/></div></li>";
	$('.delete-uct').off();
	$('.delete-uct').click(function() {
		var ok = confirm("Do you want to delete this test entry?");

		if (ok) {
			var elem = $(this);
			var testName = elem.prop('name');
			console.log(testName);
			$.ajax({
				url : '/azure/deleteupcomingtest',
				type : 'POST',
				data : {
					"keyString" : testName
				},
				success : function(data) {
					elem.closest('li').remove();
				},
				error : function(jqXHR, status, errorThrown) {
					alert(errorThrown);
				}

			});
		}

	});
	$(content).insertAfter(testSizeLi);
}

function clearForm(form) {
	form[0].reset();
}

function upcomingTestDateChanged1() {

	validateFutureDate($("#test-date1"), $("#upcoming-testdate-error-div1"));
}

function prepDeadlineChanged1() {
	validateFutureDate($("#prep-deadline1"), $("#prep-deadline-error-div1"));
}
