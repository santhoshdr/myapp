<div class="dropdown">
  <button class="dropbtn">Dropdown</button>
  <div class="dropdown-content">
    <a href="#">Link 1</a>
    <a href="#">Link 2</a>
    <a href="#">Link 3</a>
  </div>
</div>
<nav
	class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark fixed-top">
	<div class="container">
		<a class="navbar-brand" href="index.html">Directory</a>
		<button class="navbar-toggler navbar-toggler-right" type="button"
			data-toggle="collapse" data-target="#navbarResponsive"
			aria-controls="navbarResponsive" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarResponsive">
			<ul class="navbar-nav ml-auto">
			<!-- 	<li class="nav-item"><a class="btn btn-primary"
					href="/user/getMyProfile">My Profile</a></li>&nbsp;&nbsp;
				<li class="nav-item">
					<button type="button" data-toggle="modal"
						data-target="#changePassword">Change Password</button>
				</li>&nbsp;&nbsp;
				<li class="nav-item"><a class="btn btn-primary"
					href="/api/auth/logout">Log Out</a></li>&nbsp;&nbsp;
				<li> -->
					<div class="dropdown">
						<button class="btn btn-primary dropdown-toggle" type="button" 	id="dropdownMenuButton" data-toggle="dropdown">
							Welcome <%=session.getAttribute("loggedInUserName") %>
						</button>
						<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
							<a class="dropdown-item" href="/user/getMyProfile">My Profile</a> 
							<button type="button" class="btn" data-toggle="modal"
                        data-target="#changePassword">Change Password</button>
                        <a class="dropdown-item"  href="/api/auth/logout">Log Out</a>
						</div>
					</div>
				</li>
				
				
			</ul>
		</div>
	</div>
</nav>


<div class="modal" id="changePassword">
	<div class="modal-dialog">
		<div class="modal-content">
			<!-- Modal Header -->
			<div class="modal-header">
				<h4 class="modal-title">Change Password</h4>
			</div>
			<div id="failure" style="display: none" class="alert alert-danger"
				role="alert"></div>
			<div id="success" style="display: none" class="alert alert-success"
				role="alert"></div>
			<!-- Modal body -->
			<div class="modal-body">
				<div class="form-group">
					<label for="exampleInputEmail1">Current Password</label> <input
						type="password" name="currentPassword" class="form-control"
						id="currentPassword" aria-describedby="emailHelp"
						placeholder="Current Password">
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">New Password</label> <input
						type="password" name="newPassword" class="form-control"
						id="newPassword" placeholder="New Password">
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">Confirm Password</label> <input
						type="password" name="confirmNewPassword" class="form-control"
						id="confirmNewPassword" placeholder="Confirm Password">
				</div>
				<button id="changePasswordButton" type="button"
					class="btn btn-primary">Submit</button>
				<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
<script>
	$(document)
			.ready(
					function() {
						$("#failure").hide();
						$("#success").hide();
								$("#changePasswordButton")
										.click(
												function() {
													$("#failure").hide();
													$("#success").hide();

													var currentPassword = $(
															"#currentPassword")
															.val();
													var newPassword = $(
															"#newPassword")
															.val();
													var confirmNewPassword = $(
															"#confirmNewPassword")
															.val();
													alert("currentPassword"
															+ currentPassword
															+ newPassword
															+ confirmNewPassword);
													var model = {
														currentPassword : $(
																"#currentPassword")
																.val(),
														newPassword : $(
																"#newPassword")
																.val(),
														confirmNewPassword : $(
																"#confirmNewPassword")
																.val()
													};
													$
															.ajax({
																url : '/user/changePassword',
																type : "POST",
																data : model,
																success : function(
																		response,
																		textStatus,
																		jqXHR) {
																	var result = JSON
																			.stringify(
																					response.message,
																					null,
																					4)
																	$(
																			'#success')
																			.html(
																					result);
																	$(
																			"#success")
																			.show();
																},
																error : function(
																		response,
																		textStatus,
																		errorThrown) {
																	$(
																			"#success")
																			.hide();
																	var result = JSON
																			.stringify(
																					response.responseJSON.message,
																					null,
																					4)
																	$(
																			'#failure')
																			.html(
																					result);
																	$(
																			"#failure")
																			.show();
																}
															})
												}), $("#buttonClose").click(
										function() {
											$("#failure").hide();
											$("#success").hide();
										}), $("#changePassword").click(
										function() {
											$("#failure").hide();
											$("#success").hide();
										})
					});
</script>
