
<div class="navbar navbar-default navbar-fixed-top" role="navigation" style="background: #3B5998">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

			<a class="navbar-brand" style="color: white;">Career Explora</a>
		</div>

		<div class="navbar-collapse collapse hidden-nav-div">
			<ul class="nav navbar-nav navbar-right">
				<li class="cbt-home hidden-sm hidden-md hidden-lg hidden-nav-item to-pointer mob-nav"><a>CBT</a></li>
				<li class="talent-home hidden-sm hidden-md hidden-lg hidden-nav-item to-pointer mob-nav"><a>Talent
						Hunt</a></li>
				<li class="career-cluster-home hidden-sm hidden-md hidden-lg hidden-nav-item to-pointer mob-nav"><a>Career
						Clusters</a></li>
				<li class="personal-style-home hidden-sm hidden-md hidden-lg hidden-nav-item to-pointer mob-nav"><a>Discover
						You</a></li>
				<li class="skill-builder-home hidden-sm hidden-md hidden-lg hidden-nav-item to-pointer mob-nav"><a>Skill
						Builder</a></li>
				<li class="mit-home hidden-sm hidden-md hidden-lg hidden-nav-item to-pointer mob-nav"><a>Brain
						Types</a></li>
				<li class="career-values-home hidden-md hidden-lg hidden-nav-item to-pointer mob-nav"><a>Career
						Values</a></li>
				<li class="hidden-sm hidden-md hidden-lg hidden-nav-item to-pointer mob-nav" onclick="logout();"><a>Logout</a></li>
				<li class="hidden-xs hidden-sm">
					<!-- <form class="navbar-form" role="search">
						<div class="input-group " style="width: 100%;">

							<input type="text" class="form-control input-block-level"
								placeholder="Search" name="q" />
							<div class="input-group-btn">
								<button class="btn btn-default" type="submit">
									<i class="glyphicon glyphicon-search"></i>
								</button>

							</div>
						</div>
					</form>  -->
				</li>
				<li class="hidden-xs">
					<div id="accountInfo" class="trigger" style="padding-top: 10%;">
						<img id="imginfo"
							class="img-circle img-responsive profile-img to-pointer current-user-img"
							src='${azureUser["picture"]}' />
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>

<div class="content hide container-fluid" id="popover-content">

	<div style="margin-bottom: 5%; height: 5.6em;">
		<div style="width: 30%; float: left;">
			<img class="img-responsive profile-img current-user-img"
				id="popover-img" src='${azureUser["picture"]}' />
		</div>
		<div style="width: 65%; float: right;">
			<p style="word-wrap: break-word;">
				<font id="pop-user-first-name" face="Arial Narrow"
					style="font-size: 13pt; font-weight: bold; color: black; padding-right: 2%">
					<c:out value="${azureUser.firstName}" />
				</font><font id="pop-user-last-name" face="Arial Narrow"
					style="font-size: 13pt; font-weight: bold; color: black; word-wrap: break-word;"><c:out
						value="${azureUser.lastName}" /> </font><br /> <font
					id="pop-user-email" face="Arial Narrow" style="font-size: 10pt;">
					<c:out value='${azureUser.email}' default="Career Explora User" />
				</font>
			</p>
		</div>
		<div style="width: 65%; float: right;">
			<p class="text-success"font-weight:bolder; font-size: 13pt;">
				<a href="<c:url value='/azure/getuserprofile?id=11111'/>"
					class="view-my-profile">View Profile</a>
			</p>
		</div>
	</div>

	<div style="clear: both;">
		<hr style="margin-bottom: 2%"></hr>
	</div>

	<div>
		<div style="width: 50%; float: left;">
			<a type="submit" href="/azure/getsettings"
				class="btn ca-btn-primary btn-sm view-settings">View Settings</a>
		</div>
		<div style="width: auto; float: right;">
			<input type="button" value="Logout" id="logout-button"
				class="btn ca-btn-primary btn-sm" onclick="logout();" />
		</div>
	</div>
</div>

</div>
