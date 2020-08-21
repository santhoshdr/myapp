<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container" style="margin-top: 55px;">

        <c:if test="${not empty errorMessage }">
            <div class="alert alert-danger alert-dismissible fade show"
                role="alert">
                ${errorMessage}
                <button type="button" class="close" data-dismiss="alert"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
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

<!-- login result  -->

<%-- <c:if test="${not empty message}">
	<c:choose>
        <c:when test="${ message} eq 'inActive' ">
            <div id="registrationSuccess"
                class="alert alert-success alert-dismissible fade show"
                style="top: 50px;" role="alert">
                <strong>User is Inactive. Please Verify your email or Contact Administrator</strong>
                <button type="button" class="close" data-dismiss="alert"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:when>

        <c:when
            test="${!registrationSuccess && not empty registrationSuccess}">
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
</c:if>
 --%>
	<%-- <c:choose>
		<c:when test="${registrationSuccess && not empty registrationSuccess}">
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

		<c:when
			test="${!registrationSuccess && not empty registrationSuccess}">
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
	</c:choose> --%>


	<%-- <c:choose>

		<c:when test="${registrationSuccess && not empty registrationSuccess}">
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

		<c:when
			test="${!registrationSuccess && not empty registrationSuccess}">
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
	</c:choose> --%>
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
			<h1 class="display-3"> Hello, world!</h1>
			<p>This is a template for a simple marketing or informational
				website. It includes a large callout called a jumbotron and three
				supporting pieces of content. Use it as a starting point to create
				something more unique.</p>
			<p>
				<a class="btn btn-primary btn-lg" href="#" role="button">Learn
					more »</a>
			</p>
		</div>
	</div>

<c:if test="${not empty message }">
 <div class="alert alert-success" role="alert">
 ${message}
</div>
</c:if>

	<!-- Marketing Icons Section -->
	<div class="row">
		<div class="col-lg-4 mb-4">
			<div class="card h-100">
				<h4 class="card-header">Card Title</h4>
				<div class="card-body">
					<p class="card-text">Lorem ipsum dolor sit amet, consectetur
						adipisicing elit. Sapiente esse necessitatibus neque.</p>
				</div>
				<div class="card-footer">
					<a href="#" class="btn btn-primary">Learn More</a>
				</div>
			</div>
		</div>
		<div class="col-lg-4 mb-4">
			<div class="card h-100">
				<h4 class="card-header">Card Title</h4>
				<div class="card-body">
					<p class="card-text">Lorem ipsum dolor sit amet, consectetur
						adipisicing elit. Reiciendis ipsam eos, nam perspiciatis natus
						commodi similique totam consectetur praesentium molestiae atque
						exercitationem ut consequuntur, sed eveniet, magni nostrum sint
						fuga.</p>
				</div>
				<div class="card-footer">
					<a href="#" class="btn btn-primary">Learn More</a>
				</div>
			</div>
		</div>
		<div class="col-lg-4 mb-4">
			<div class="card h-100">
				<h4 class="card-header">Card Title</h4>
				<div class="card-body">
					<p class="card-text">Lorem ipsum dolor sit amet, consectetur
						adipisicing elit. Sapiente esse necessitatibus neque.</p>
				</div>
				<div class="card-footer">
					<a href="#" class="btn btn-primary">Learn More</a>
				</div>
			</div>
		</div>
	</div>
	<!-- /.row -->

	<!-- Portfolio Section -->
	<h2>Portfolio Heading</h2>

	<div class="row">
		<div class="col-lg-4 col-sm-6 portfolio-item">
			<div class="card h-100">
				<a href="#"><img class="card-img-top"
					src="http://placehold.it/700x400" alt=""></a>
				<div class="card-body">
					<h4 class="card-title">
						<a href="#">Project One</a>
					</h4>
					<p class="card-text">Lorem ipsum dolor sit amet, consectetur
						adipisicing elit. Amet numquam aspernatur eum quasi sapiente
						nesciunt? Voluptatibus sit, repellat sequi itaque deserunt,
						dolores in, nesciunt, illum tempora ex quae? Nihil, dolorem!</p>
				</div>
			</div>
		</div>
		<div class="col-lg-4 col-sm-6 portfolio-item">
			<div class="card h-100">
				<a href="#"><img class="card-img-top"
					src="http://placehold.it/700x400" alt=""></a>
				<div class="card-body">
					<h4 class="card-title">
						<a href="#">Project Two</a>
					</h4>
					<p class="card-text">Lorem ipsum dolor sit amet, consectetur
						adipiscing elit. Nam viverra euismod odio, gravida pellentesque
						urna varius vitae.</p>
				</div>
			</div>
		</div>
		<div class="col-lg-4 col-sm-6 portfolio-item">
			<div class="card h-100">
				<a href="#"><img class="card-img-top"
					src="http://placehold.it/700x400" alt=""></a>
				<div class="card-body">
					<h4 class="card-title">
						<a href="#">Project Three</a>
					</h4>
					<p class="card-text">Lorem ipsum dolor sit amet, consectetur
						adipisicing elit. Quos quisquam, error quod sed cumque, odio
						distinctio velit nostrum temporibus necessitatibus et facere atque
						iure perspiciatis mollitia recusandae vero vel quam!</p>
				</div>
			</div>
		</div>
		<div class="col-lg-4 col-sm-6 portfolio-item">
			<div class="card h-100">
				<a href="#"><img class="card-img-top"
					src="http://placehold.it/700x400" alt=""></a>
				<div class="card-body">
					<h4 class="card-title">
						<a href="#">Project Four</a>
					</h4>
					<p class="card-text">Lorem ipsum dolor sit amet, consectetur
						adipiscing elit. Nam viverra euismod odio, gravida pellentesque
						urna varius vitae.</p>
				</div>
			</div>
		</div>
		<div class="col-lg-4 col-sm-6 portfolio-item">
			<div class="card h-100">
				<a href="#"><img class="card-img-top"
					src="http://placehold.it/700x400" alt=""></a>
				<div class="card-body">
					<h4 class="card-title">
						<a href="#">Project Five</a>
					</h4>
					<p class="card-text">Lorem ipsum dolor sit amet, consectetur
						adipiscing elit. Nam viverra euismod odio, gravida pellentesque
						urna varius vitae.</p>
				</div>
			</div>
		</div>
		<div class="col-lg-4 col-sm-6 portfolio-item">
			<div class="card h-100">
				<a href="#"><img class="card-img-top"
					src="http://placehold.it/700x400" alt=""></a>
				<div class="card-body">
					<h4 class="card-title">
						<a href="#">Project Six</a>
					</h4>
					<p class="card-text">Lorem ipsum dolor sit amet, consectetur
						adipisicing elit. Itaque earum nostrum suscipit ducimus nihil
						provident, perferendis rem illo, voluptate atque, sit eius in
						voluptates, nemo repellat fugiat excepturi! Nemo, esse.</p>
				</div>
			</div>
		</div>
	</div>
	<!-- /.row -->


</div>