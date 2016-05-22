function updatePasswordRecovery(text, iText, elem) {
	$.ajax({
		url : "azure/editpasswordrecovery",
		type : "POST",
		dataType : "Json",
		data : {
			old : text,
			nw : iText
		},
		success : function(data) {
			elem.text(text);
		},
		error : function(jqXHR, status, errorThrown) {
			elem.text(iText);
		}
	});
}

$(document)
		.ready(
				function() {
					
					$(".chosen-select").chosen();
					$(".chosen-select").chosen({max_selected_options: 5});
					$(".chosen-select").chosen({no_results_text: "Oops, nothing found!"});

					$(".add-id").click(function() {
						var dm = $("#default-msg1");
						var db = $("#default-button");
						
						dm.show();
						db.show();
					});

					$(".add-alt-pswr-mobile")
							.click(
									function() {
										var elem = $(this);
										var td = elem.closest("td");
										var tr = td.closest("tr");
										elem.hide();
										var text = '<tr id="msg-tr"><td colspan=3 id="msg-div" style="border:none;"></td></tr>';
										var input = '<input type="text" placeholder="Mobile number" id="mobile-number"  style="font-size:11pt"/> <input type="button" value="Send Code" id="send-code-button" class="btn btn-warning btn-sm"/> <br/><span id="msg1" style="font-size:8pt">A confirmation code will be sent to your mobile number.</span>'
										td.html(input);
										var $mobile = $("#mobile-number");
										var $button = $("#send-code-button");
										$mobile.focus();
										$mobile.blur(function() {
											$button.focus();
										});
										$mobile.keyup(function(event) {
											$(this).removeClass(
													"reg-input-error");
											$("#msg-tr").remove();
											$("#msg1").show();
										});
										
										var msgDiv = null;

										$button
												.click(function() {
													var ok = required($mobile);
													var okay = allNumeric($mobile);
													if (!ok | !okay) {
														$("#msg-tr").remove();
														$("#msg1").hide();
														tr.after(text);
														msgDiv = $("#msg-div");
														msgDiv
																.addClass("text-danger");
														msgDiv
																.text("Enter a mobile number.");
														$mobile.select();
														$mobile
																.addClass("reg-input-error");
														return;
													}

													if (ok & okay) {

														$("#msg-tr").remove();
														tr.after(text);
														msgDiv = $("#msg-div");
														msgDiv
																.addClass("text-success");
														msgDiv
																.text("verifying...");
														$
																.ajax({
																	url : "/azure/verifypasswordrecovery",
																	type : "POST",
																	dataType : "Json",
																	data : {
																		id : $mobile
																				.val(),
																		codeSent : false
																	},
																	success : function(
																			data) {

																	},
																	error : function(
																			jqXHR,
																			status,
																			errorThrown) {
																		$(
																				"#msg-tr")
																				.remove();
																		$(
																				"#msg1")
																				.hide();
																		tr
																				.after(text);
																		msgDiv = $("#msg-div");
																		msgDiv
																				.addClass("text-danger");
																		msgDiv
																				.text(errorThrown);
																		$mobile
																				.select();
																		$mobile
																				.addClass("reg-input-error");
																	},
																	complete : function() {
																		setTimeout(
																				function() {
																					msgDiv
																							.fadeOut(
																									3000,
																									function() {
																										$(
																												"#msg-tr")
																												.remove();
																										$(
																												"#msg1")
																												.show();
																										$mobile
																												.removeClass("reg-input-error");
																									});
																				},
																				3000);
																	}
																});
													} else {

													}

												});
									});

					$(".sp-verify")
							.click(
									function() {
										var t = '<div class="col-sm-12">'
												+ '<p id="pswr-msg">An SMS with a confirmation code has been sent to<br/>Enter it here.</p>'
												+ '<input type="text" placeholder="confirmation code" name="code" id="confirmation-code"/>'
												+ '<input type="button" value="Verify" id="pswr-verify"/></div>';

										var elem = $(this);
										var tr = elem.closest("tr");
										var lab = tr.find(".editable-label");
										var val = lab.text();

										tr
												.after('<td colspan=3 id="pswr-msg" style="padding:2%"></td>');
										$pswrMsg = $("#pswr-msg");

										$pswrMsg.removeClass("text-danger");
										$pswrMsg.addClass("text-success");
										$pswrMsg.text("processing...");
										var f = 'We could not send a confirmation code to '
												+ val
												+ '.<br/> Make sure it is valid and try again later.';

										$
												.ajax({
													url : "/azure/verifypasswordrecovery",
													dataType : "Json",
													type : "POST",
													data : {
														id : val,
														codeSent : false
													},
													success : function(data) {
														// tr.after(t);
													},
													error : function(jqXHR,
															status, errorThrown) {
														$pswrMsg
																.removeClass("text-success");
														$pswrMsg
																.addClass("text-danger");
														$pswrMsg.html(f);
													},
													complete : function() {
														setTimeout(
																function() {
																	$pswrMsg
																			.fadeOut(
																					3000,
																					function() {
																						$pswrMsg
																								.remove();
																					});
																}, 3000);
													}
												});
									});

					$(".editable-label").click(function() {
						var elem = $(this);
						var text = elem.text();
						elem.text("");
						var input = '<input type="text" id="temp-input"/>';
						elem.html(input);
						$("#temp-input").val(text);
						$("#temp-input").select();
						$("[data-toggle='tooltip']").tooltip('hide');
						$("[data-toggle='tooltip']").tooltip('disable');

						$("#temp-input").on("keyup blur", function(e) {
							if (e.keyCode == 13) {

								var iElem = $(this);
								var iText = iElem.val();
								var ok = required(iElem);
								if(!ok) {
									elem.text(text);
								}
								
								ok = allNumeric(iElem);
								
								if(!ok) {
									elem.text(text);
								}
								
								ok = iText.length() === 11;
								
								if(!ok) {
									elem.text(text);
								}
								iElem.off();
								iElem.remove();
								if (iText.length == 0) {
									elem.text(text);
								} else {
									updatePasswordRecovery(text, iText, elem);
								}

							} else if (e.type === "blur") {
								var iElem = $(this);

								iElem.off();
								iElem.remove();

								elem.text(text);

							}

						});

					});

					$("[data-toggle='tooltip']").tooltip();

					$("#password-change-button")
							.click(
									function() {

										var $pList = $("#change-password-list");
										var mForm = $("#change-password-form");
										var msgDiv = $("#reset-password-msg-div");
										console.log("got here");
										$pList.waitMe({
											effect : 'ios',
											color : 'orange',
											sizeW : '15',
											sizeH : '15'
										});

										var jqxhr = $
												.post("/azure/resetpassword",
														mForm.serialize())
												.done(
														function(data) {
															msgDiv
																	.removeClass("text-danger");
															msgDiv
																	.addClass("text-success");
															msgDiv
																	.text("You password has been changed successfully.");
															$("#password1")
																	.val("");
															$("#password2")
																	.val("");
															$("#old-password")
																	.val("");
															$pList
																	.waitMe("hide");
														})
												.fail(
														function(jqXHR, status,
																errorThrown) {
															msgDiv
																	.removeClass("text-success");
															msgDiv
																	.addClass("text-danger");
															msgDiv
																	.text(errorThrown);
															$pList
																	.waitMe("hide");

														});
									});
				});