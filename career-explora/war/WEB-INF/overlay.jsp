<c:choose>
	<c:when test="${not empty userProfile}">
		<div id="overlay" class="col-sm-6 col-md-4"
			style="padding: 0px; padding-top: 6%;">
			<div>
				<img class="img-circle img-responsive profile-img"
					id="profile-image" src='${userProfile["picture"]}'/>
			</div>
			<div>
				<h4 style="text-align: center; color: white;">
					<span class="user-name" style="word-wrap: break-word;"><span id="user-first-name"><c:out
							value='${userProfile.firstName}' /></span> <span id="user-last-name"><c:out
							value='${userProfile.lastName}' /></span>
					</span>
				</h4>
			</div>
			<div>
				<div style="margin: auto; text-align: center;">
					<c:choose>
						<c:when
							test="${(empty userProfile.school) && (empty userProfile.state) && (empty userProfile.country)}">
							<div style="font-size: 9pt; color: white; word-wrap: break-word;"
								id="profile-location">
								<div id="not-set-div">School/Location not set</div>
								<div id="sch-name" style="padding: 1%"></div>
								<div id="lives-name"></div>
							</div>
						</c:when>
						<c:otherwise>
							<div
								style="font-size: 9pt; color: white; word-wrap: break-word;"
								id="profile-location">
								<div id="sch-name" style="padding: 1%">
									<c:if test="${not empty userProfile.school}">
										<c:out value='${userProfile.attends}' />
									</c:if>
									<span id="user-school"><c:out value='${userProfile.school}'/></span>
								</div>
								<div id="lives-name">
									<c:if
										test="${(not empty userProfile.state) || (not empty userProfile.country)}"><span id="lv">Lives in</span></c:if>
									<span style="text-transform: capitalize;" id="loc"><span id="p-state"><c:out
											value='${userProfile.state}' /></span> <c:if
											test="${(not empty userProfile.state)}"> , </c:if> <span id="p-country"><c:out
											value='${userProfile.country}'/> </span></span>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<c:choose>
			<c:when test="${empty userProfile.cover}">
				<img alt="" src='/images/career-explora-bck.jpg' id="background-img" />
			</c:when>
			<c:otherwise>
				<img alt="" src='${userProfile.cover}' id="background-img" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div id="overlay" class="col-sm-6 col-md-4"
			style="padding: 0px; padding-top: 6%;">
			<div>
				<img class="img-circle img-responsive profile-img"
					id="profile-image" src='${azureUser["picture"]}' />
			</div>
			<div>
				<h4 style="text-align: center; color: white;">
					<span class="to-pointer user-name" id="profile-name"
						style="word-wrap: break-word;" data-toggle="tooltip"
						title="Click to edit name" data-placement="bottom"> <c:out
							value='${azureUser.firstName}' /> <c:out
							value='${azureUser.lastName}' />
					</span>
				</h4>
			</div>
			<div>
				<div style="margin: auto; text-align: center;">

					<c:choose>
						<c:when
							test="${(empty azureUser.school) && (empty azureUser.state) && (empty azureUser.country)}">
							<div class="to-pointer"
								style="font-size: 9pt; color: white; word-wrap: break-word;"
								 data-toggle="tooltip"
								title="Click to edit school/location" data-placement="bottom">
								<div id="not-set-div" class="profile-location">School/Location not set</div>
								<div id="sch-name" style="padding: 1%" class="profile-location"></div>
								<div id="lives-name" class="profile-location"></div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="to-pointer"
								style="font-size: 9pt; color: white; word-wrap: break-word;"
								id="profile-location" data-toggle="tooltip"
								title="Click to edit school/location" data-placement="bottom">
								<div id="sch-name" style="padding: 1%" class="profile-location">
									<c:if test="${not empty azureUser.school}">
										<c:out value='${azureUser.attends}' />
									</c:if>
									<c:out value='${azureUser.school}' />
								</div>
								<div id="lives-name" class="profile-location">
									<c:if
										test="${(not empty azureUser.state) || (not empty azureUser.country)}"><span id="lv">Lives in </span></c:if>
									<span style="text-transform: capitalize;" id="loc"><c:out
											value='${azureUser.state}' /> <c:if
											test="${(not empty azureUser.state)}"> , </c:if> <c:out
											value='${azureUser.country}' /></span>
								</div>
							</div>
							
						</c:otherwise>
					</c:choose>


				</div>
			</div>
		</div>
		<c:choose>
			<c:when test="${empty azureUser.cover}">
				<img alt="" src='/images/career-explora-bck.jpg' id="background-img" />
				<div id="cover-div">Change Cover Picture</div>
			</c:when>
			<c:otherwise>
				<img alt="" src='${azureUser.cover}' id="background-img" />
				<div id="cover-div">Change Cover Picture</div>
			</c:otherwise>
		</c:choose>
	</c:otherwise>

</c:choose>