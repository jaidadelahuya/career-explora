
<!-- hidden fields -->
<input class="has-taken-talent-test" type="hidden"
	value="${azureUser.takenTalentTest}" />
<input id="picture-url" type="hidden" value="${azureUser.picture}" />
<input id="user-first-name" type="hidden" value="${azureUser.firstName}" />
<input id="user-last-name" type="hidden" value="${azureUser.lastName}" />

<!-- profile name modal -->
<div id="profile-name-dialog" class="modal fade" tabindex="-1"
	role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header" style="background-color: orange;">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel" style="color: white">Edit
					your name</h4>
			</div>
			<div class="modal-body">
				<form method="post" role="form" class="wait-me" id="edit-name-form">
					<div class="row">
						<div class="form-group col-sm-6">
							<label for="first-name" class="mobile-font">First Name:</label> <input type="text"
								class="form-control" id="first-name" name="first-name"
								value="${azureUser.firstName}" />
						</div>
						<div class="form-group col-sm-6">
							<label for="last-name" class="mobile-font">Last Name:</label> <input type="text"
								class="form-control" id="last-name" name="last-name"
								value="${azureUser.lastName}" />
						</div>
						<div class="form-group col-sm-6">
							<label for="first-name" class="mobile-font">Middle Name:</label> <input type="text"
								class="form-control" id="middle-name" name="middle-name"
								value="${azureUser.middleName}" />
						</div>
						<div class="form-group col-sm-6">
							<label for="last-name" class="mobile-font">Date of Birth:</label> <input type="text"
								class="form-control date-picker" id="date-of-birth"
								name="date-of-birth" onchange="dobChanged();"
								readonly="readonly"
								style="cursor: pointer; background-color: white;"
								value="${azureUser.dateOfBirth}" />
							<div class="error-div col-sm-12" id="dob-error-div"></div>
						</div>
						<div class="checkbox col-sm-6">
							<label style="color: blue; font-size: 10pt;" class="mobile-font"> <c:choose>
									<c:when test='${azureUser.updateNameFromIdp}'>
										<input type="checkbox" name="update-from-idp" id="idp-update"
											checked="checked" />
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="update-from-idp" id="idp-update" />
									</c:otherwise>
								</c:choose> Always update from facebook
							</label>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer" style="background-color: orange;">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary" id="edit-profile-name">Save
					changes</button>
			</div>
		</div>
	</div>
</div>


<!-- profile location modal -->
<div id="profile-location-dialog" class="modal fade" tabindex="-1"
	role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header" style="background-color: orange;">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel" style="color: white;">Edit
					your location</h4>
			</div>
			<div class="modal-body">
				<div>
					<h4 class="mobile-header">Location</h4>
					<form method="post" class="wait-me" id="edit-location-form">
						<div class="row">
							<div class="col-sm-6">
								<label for="state" class="mobile-font">State:</label> <select id="state"
									class="form-control" name="state" required="required"
									value="${azureUser.state}">
									<option>Abia</option>
									<option>Adamawa</option>
									<option>Akwa Ibom</option>
									<option>Anambra</option>
									<option>Bauchi</option>
									<option>Bayelsa</option>
									<option>Benue</option>
									<option>Borno</option>
									<option>Cross River</option>
									<option>Delta</option>
									<option>Ebonyi</option>
									<option>Edo</option>
									<option>Ekiti</option>
									<option>Enugu</option>
									<option>Federal Capital Territory</option>
									<option>Gombe</option>
									<option>Imo</option>
									<option>Jigawa</option>
									<option>Kaduna</option>
									<option>Kano</option>
									<option>Katsina</option>
									<option>Kebbi</option>
									<option>Kogi</option>
									<option>Kwara</option>
									<option selected="selected">Lagos</option>
									<option>Nasarawa</option>
									<option>Niger</option>
									<option>Ogun</option>
									<option>Ondo</option>
									<option>Osun</option>
									<option>Oyo</option>
									<option>Plateau</option>
									<option>Rivers</option>
									<option>Sokoto</option>
									<option>Taraba</option>
									<option>Yobe</option>
									<option>Zamfara</option>
								</select>

							</div>
							<div class="col-sm-6">
								<label for="country" class="mobile-font">Country:</label> <input type="text"
									class="form-control" id="country" placeholder="Country"
									name="country" required="required" value="${azureUser.country}">
							</div>
						</div>
						<div style="padding-top: 2%;">
							<h4 class="mobile-header">School</h4>
							<div class="row">
								<div class="col-sm-12">
									<c:choose>
										<c:when test="${azureUser.attends}">
											<input type="radio" name="attends" value="Attends"
												checked="checked" />
										</c:when>
										<c:otherwise>
											<input type="radio" name="attends" value="Attends" />
										</c:otherwise>
									</c:choose>
									<span style="padding-left: 1%;">Attends</span>
									<c:choose>
										<c:when test="${azureUser.attends}">
											<input type="radio" name="attends" value="Attended"
												style="padding-left: 5%;" />
										</c:when>
										<c:otherwise>
											<input type="radio" name="attends" value="Attended"
												style="padding-left: 5%;" checked="checked" />
										</c:otherwise>
									</c:choose>
									<span style="padding-left: 1%;">Attended</span>
								</div>
								<div class="col-sm-12">
									<input type="text" class="form-control" id="school"
										placeholder="School" name="school" required="required"
										value="${azureUser.school}" />
								</div>
							</div>

						</div>

					</form>

				</div>
			</div>
			<div class="modal-footer" style="background: orange;">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary"
					onclick="editLocation();">Save changes</button>
			</div>
		</div>
	</div>
</div>

<!-- view subscription details modal -->
<div id="subscription-details-dialog" class="modal fade" tabindex="-1"
	role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content panel panel-default">
			<div class="modal-header" style="background-color: orange;">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title mobile-header" id="myModalLabel" style="color: white;">Subscription
					details</h4>
			</div>
			<ul class="list-group list-group-flush">
				<li class="text-center"><h4 style="color: green" class="mobile-header">ACTIVE</h4></li>
				<li class="list-group-item ">
					<table
						class="table table-borderless table-condensed table-responsive"
						style="border: none; margin: 0 auto">
						<tr>
							<td class="text-right mobile-font" style="width: 45%;"><strong
								class="last-login-label">Subscription date: </strong></td>
							<td class="text-left last-login-data mobile-font" id="subscription-date"><c:out
									value='${welcomePage.subscriptionDate}' /></td>
						</tr>
					</table>
				</li>

				<li class="list-group-item ">
					<table
						class="table table-borderless table-condensed table-responsive"
						style="border: none; margin: 0 auto">
						<tr>
							<td class="text-right mobile-font" style="width: 45%;"><strong
								class="last-login-label">Validity: </strong></td>
							<td id="validity" class="text-left last-login-data mobile-font"><c:out
									value='${welcomePage.validity}' /> Months(s)</td>
						</tr>

					</table>
				</li>

				<li class="list-group-item ">
					<table
						class="table table-borderless table-condensed table-responsive"
						style="border: none; margin: 0 auto">
						<tr>
							<td class="text-right mobile-font" style="width: 45%;"><strong
								class="last-login-label">Expiry date: </strong></td>
							<td id="expiry-date" class="text-left last-login-data mobile-font"><c:out
									value='${welcomePage.expiryDate}' /></td>
						</tr>

					</table>
				</li>
			</ul>
			<div class="modal-footer" style="background-color: orange;">
				<!--   <button class="btn btn-primary btn-sm extend-subscription" id="ssb">Extend
						Subscription</button>-->
				<button type="button" class="btn btn-default btn-sm"
					data-dismiss="modal">Close</button>

			</div>
		</div>
	</div>
</div>

<!-- profile photo modal -->
<div id="profile-photo-dialog" class="modal fade" tabindex="-1"
	role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header" style="background-color: orange;">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel" style="color: white;">Upload
					a profile picture</h4>
			</div>
			<div class="modal-body">
				<p style="font-size: 9pt">Upload a picture file not greater than
					1024kb and about 180 by 180. a sqaure shape.
				<form action="${uploadUrl}" enctype="multipart/form-data"
					id="profile-picture-form" method="post">
					<div class="form-group" style="font-size: 9pt">
						<input type="file" name="picture" id="pic1" style="width: 100%;" />
					</div>
				</form>
			</div>
			<div class="modal-footer" style="background-color: orange;">
				<button type="button" class="btn btn-default" data-dismiss="modal"
					class="close-profile-photo-diag">Close</button>
				<button type="button" class="btn btn-primary uploader"
					id="upload-picture" disabled="disabled">Upload Picture</button>
			</div>

		</div>

	</div>
</div>

<!-- cover picture modal -->
<div id="cover-picture-dialog" class="modal fade" tabindex="-1"
	role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header" style="background-color: orange;">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel" style="color: white;">Upload
					a cover picture</h4>
			</div>
			<div class="modal-body">
				<p style="font-size: 9pt">Upload a picture file not greater than
					2MB and about 960px by 320px.
				<form action="${uploadUrl}" enctype="multipart/form-data"
					id="cover-picture-form" method="post">
					<div class="form-group" style="font-size: 9pt">
						<input type="file" name="picture" id="pic2" style="width: 100%;" />
					</div>
					<input type="hidden" name="type" value="cover" />
				</form>
			</div>
			<div class="modal-footer" style="background-color: orange;">
				<button type="button" class="btn btn-default" data-dismiss="modal"
					id="close-profile-photo-diag">Close</button>
				<button type="button" class="btn btn-primary uploader"
					id="upload-cover" disabled="disabled">Upload Cover Picture</button>
			</div>

		</div>

	</div>
</div>
