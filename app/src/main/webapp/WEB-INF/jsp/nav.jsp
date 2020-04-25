         
<nav
	class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark fixed-top">
	<div class="container">
		<a class="navbar-brand" href="/home/guest">Directory</a>
		<button class="navbar-toggler navbar-toggler-right" type="button"
			data-toggle="collapse" data-target="#navbarResponsive"
			aria-controls="navbarResponsive" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarResponsive">
			<ul class="navbar-nav ml-auto">
				<li class="nav-item">
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#register" class="float-left">Sign Up</button>
				</li> &nbsp;&nbsp;
				<!-- Split button -->
				<div class="btn-group">
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#login">Login</button>
					<button type="button" class="btn btn-primary dropdown-toggle px-3"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					</button>
					<div class="dropdown-menu">
						<form id="verifyEmail" action="/guest/verifyEmail">
							<button type="submit" class="btn">Activate my Account</button>
						</form>
					</div>
				</div>
			</ul>
		</div>
	</div>
</nav>

<div class="modal" id="login">
	<div class="modal-dialog">
		<div class="modal-content">

			<!-- Modal Header -->
			<div class="modal-header">
				<h4 class="modal-title">Login</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">
				<form id="loginForm" action="/api/auth/signin" method="post"
					enctype="application/x-www-form-urlencoded">
					<div class="form-group">
						<label for="exampleInputEmail1">Email address</label> <input
							type="email" name="usernameOrEmail" class="form-control"
							id="loginemailId" aria-describedby="emailHelp"
							placeholder="Enter email">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Password</label> <input
							type="password" name="password" class="form-control"
							id="password" placeholder="Password">
					</div>
					<!-- 	<div class="form-check">
						<input type="checkbox" class="form-check-input" id="exampleCheck1">
						<label class="form-check-label" for="exampleCheck1">Remember Me</label>
					</div> -->
					<button type="submit" class="btn btn-primary">Submit</button>
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#forgotPassword" data-dismiss="modal"
						data-target="#login">Forgot Password</button>
				</form>
			</div>
		</div>
	</div>
</div>


<div class="modal" id="register">
	<div class="modal-dialog">
		<div class="modal-content">

			<!-- Modal Header -->
			<div class="modal-header">
				<h4 class="modal-title">Registration</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">
				<form id="registrationForm" action="/guest/addUser" method="post">
					<div class="form-group">
						<label for="First Name">First Name</label> <input type="text"
							name="firstName" class="form-control" id="emailId"
							aria-describedby="emailHelp" placeholder="First Name">
					</div>
					<div class="form-group">
						<label for="lastName">Last Name</label> <input type="text"
							name="lastName" class="form-control" id="lastName"
							placeholder="Last Name">
					</div>

					<div class="form-group">
						<label for="mobileNumber">Mobile Number / Email Address </label> <input
							type="text" name="mobileNumberOrEmail" class="form-control"
							id="mobileNumber" placeholder="Mobile Number / Email Address">
					</div>

					<!-- 	<div class="form-check">
						<input type="checkbox" class="form-check-input" id="exampleCheck1">
						<label class="form-check-label" for="exampleCheck1">Remember Me</label>
					</div> -->
					<button type="submit" class="btn btn-primary">Submit</button>
				</form>


			</div>

			<!-- Modal footer -->
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
			</div>

		</div>
	</div>
</div>


<div class="modal" id="forgotPassword">
	<div class="modal-dialog">
		<div class="modal-content">
			<!-- Modal Header -->
			<div class="modal-header">
				<h4 class="modal-title">Forgot Password</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">
				<div id="failure" style="display: none" class="alert alert-danger"
					role="alert"></div>
				<div id="success" style="display: none" class="alert alert-success"
					role="alert"></div>

				<div id="forgotPasswordGroup" class="form-group">
					<label for="First Name">Enter Your Email ID</label> <input
						type="text" name="mobileNumberOrEmail" class="form-control"
						id="forgotEmailId" aria-describedby="emailHelp"
						placeholder="Enter Your Email ID">
				</div>

				<div id="setPasswordGroup" class="form-group">
					<label for="temperoryPassword">Enter Email ID : </label> 
					<input 	type="text" name="emailId" class="form-control"
						id="forgotEmailIdNew" aria-describedby="emailHelp"
						placeholder="Email Id"> 
				   <label for="temperoryPassword">Enter Temperory Password : </label>
				    <input type="text" name="temporaryActivationString" class="form-control" id="tempPassword"
						aria-describedby="emailHelp" placeholder="Enter Temperory Password you have received">

					<label for="newPassword">Enter New Password : </label> <input
						type="text" name="newPassword" class="form-control"
						id="newPassword" aria-describedby="emailHelp"
						placeholder="Enter new Password"> <label
						for="temperoryPassword">Confirm Password : </label> <input
						type="text" name="confirmPassword" class="form-control"
						id="confirmPassword" aria-describedby="emailHelp"
						placeholder="Confirm New Password">
				</div>


				<!-- Modal footer -->
				<div class="modal-footer">
					<button id="forgotPasswordButton" type="button"
						class="btn btn-primary">Send Temperory Password</button>
					<button id="setPermanentPasswordButton" type="button"
						class="btn btn-primary">Have Temperory Password ?</button>
					<button id="resetPasswordButton" style="display: none"
						type="button" class="btn btn-primary">Reset Password</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						id="buttonClose">Close</button>
				</div>
			</div>


		</div>
	</div>
</div>

<script>
$( document ).ready(function() {
	$("#failure").hide();
	  $("#success").hide();
	  $("#setPasswordGroup").hide();
	  $( "#forgotPasswordButton" ).click(function() {
		  $("#failure").hide();
		  $("#setPasswordGroup").hide();
		  $("#success").hide();
		  var forgotEmailId = "emailId="+$("#forgotEmailId").val();
	    $.ajax({
	        url: '/guest/forgotPassword',
	        type: "POST",
	        data: forgotEmailId,
	        success: function(response, textStatus, jqXHR){
	        	var result = JSON.stringify(response.message, null, 4)
				   $('#success').html(result);
	        	$("#setPasswordGroup").show();
	        	$("#forgotPasswordGroup").hide();
	        	$("#forgotPasswordButton").hide();
	        	$("#setPermanentPasswordButton").hide();
	        	$("#resetPasswordButton").show();
	        	$("#forgotEmailIdNew").val($("#forgotEmailId").val()); 
		    	$("#success").show();
	        },
	        error: function (response, textStatus, errorThrown)
	         {
	    	   var result = JSON.stringify(response.responseJSON.message, null, 4)
			   $('#failure').html(result);
	    	   $("#failure").show();
	         }
	    })
	  }),
	  $( "#buttonClose").click(function() {
		  $("#failure").hide();
		  $("#success").hide();
		  $('#forgotEmailId').val("");
	  }),
      $( "#setPermanentPasswordButton").click(function() {
          $("#setPasswordGroup").show();
          $("#forgotPasswordGroup").hide();
          $("#forgotPasswordButton").hide();
          $("#setPermanentPasswordButton").hide();
          $("#resetPasswordButton").show();
          $("#success").hide();
          $("#failure").hide();
          $('#forgotEmailId').val("");
      }),
      $('#forgotPassword').on('hidden.bs.modal', function () {
    	  $("#setPasswordGroup").hide();
    	  $("#forgotPasswordGroup").show();
    	}),$('#forgotPassword').on('show.bs.modal', function () {
    		$("#forgotPasswordButton").show();
    		$("#setPermanentPasswordButton").show();
    		$("#resetPasswordButton").hide();
    	}),$( "#resetPasswordButton").click(function() {
           var datatosend = {
                                            emailAddress:$("#forgotEmailIdNew").val(),
                                            temporaryActivationString:$("#tempPassword").val(),
        		                            password:$("#newPassword").val(),
        		                            confirmPassword:$("#confirmPassword").val()
        		                            };
           $.ajax({
               url: '/guest/resetPassword',
               type: "POST",
               data: datatosend,
               success: function(response, textStatus, jqXHR){
               var result = JSON.stringify(response.message, null, 4)
                $('#success').html(result);
                  $('#success').show();
                  $('#failure').hide();
               console.log("Success");
               },
               error: function (response, textStatus, errorThrown)
                {
            	  $('#failure').html(response.responseJSON.message);
            	  $('#failure').show();
            	  $('#success').hide();
            	  console.log("Failure");
            	  
             }
           })
        })
	});
</script>

