<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<c:if test="${!login && not empty login}">
		<div id="loginFailure"
			class="alert alert-danger alert-dismissible fade show"
			style="top: 50px;" role="alert">
			<strong>Entered UserName or Password is wrong!. Please
				provide correct credentials</strong>
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</c:if>
	
	
		<c:if test="${!actionResult && not empty actionResult}">
		<div id="loginFailure"
			class="alert alert-danger alert-dismissible fade show"
			style="top: 50px;" role="alert">
			<strong>${message}</strong>
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</c:if>
 <c:choose>
         
         <c:when test = "${registrationSuccess && not empty registrationSuccess}">
            <div id="registrationSuccess"
			class="alert alert-success alert-dismissible fade show"
			style="top: 50px;" role="alert">
			<strong><c:out value="${message}"></c:out></strong>
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
         </c:when>
         
         <c:when test = "${!registrationSuccess && not empty registrationSuccess}">
            <div id="registrationFailure"
			class="alert alert-danger alert-dismissible fade show"
			style="top: 50px;" role="alert">
			<strong><c:out value="${message}"></c:out></strong>
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
            </c:when>
      </c:choose>
<%-- 
	<c:if test="${registrationSuccess && not empty registrationSuccess}">
		<div id="registrationSuccess"
			class="alert alert-danger alert-dismissible fade show"
			style="top: 50px;" role="alert">
			<strong><c:out value="${message}"></c:out></strong>
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</c:if>

	<c:if test="${registrationSuccess && not empty registrationSuccess}">
		<div id="registrationSuccess"
			class="alert alert-danger alert-dismissible fade show"
			style="top: 50px;" role="alert">
			<strong><c:out value="${message}"></c:out></strong>
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</c:if>
 --%>
	<div class="jumbotron">
		<div class="container">
			<h3>Enter the temporary password sent to your email or OTP sent to your mobile </h3>
					<form id="registrationForm" action="/guest/activateUser" method="post">
					<input type="text"class="form-control" placeholder="Email ID / Phone Number" id="unverifiedEmailId" name="mobileNumberOrEmail" value='<c:out value="${userEmailId}"></c:out>'>
					<div class="form-group">
						<label for="Temporary Password / OTP">Temporary Password</label> 
						<input type="password" name="temporaryActivationString" class="form-control"
							id="temporaryActivationString" aria-describedby="emailHelp"
							placeholder="Temporary Password / OTP ">
					</div>
					<div class="form-group">
						<label for="lastName">New Password</label> <input
							type="password" name="password" class="form-control"
							id="password" placeholder="New Password">
					</div>
					<div class="form-group">
						<label for="confirmPassword">Confirm Password </label> <input
							type="password" name="confirmPassword" class="form-control"
							id="confirmPassword" placeholder="Confirm Password">
					</div>
					<button type="submit" class="btn btn-primary">Submit</button>
				</form>
		</div>
	</div>
		</div>
	
	<!-- /.row -->

	

	
	<!-- /.row -->


