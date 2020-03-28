<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">
	<c:if test="${!actionResult && not empty actionResult}">
		<div id="loginFailure"
			class="alert alert-danger alert-dismissible fade show"
			style="top: 70px;" role="alert">
			<strong>Entered UserName or Password is wrong!. Please
				provide correct credentials</strong>
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</c:if>
</div>
