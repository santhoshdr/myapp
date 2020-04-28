<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="container" style="margin-top: 60px;">
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="/user/loginHome">Home</a>
		<div class="collapse navbar-collapse" id="navbarText">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<div class="dropdown">
						<a class="btn btn-secondary dropdown-toggle" type="button"
							id="dropdownMenuButton" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false" href="#"> Member</a>
						<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
							<a class="dropdown-item" href="/user/addMember">Add Member</a>
							<a class="dropdown-item" href="/user/getAllActiveMembers">View Members</a>
							<sec:authorize 	access="hasRole('ROLE_ADMIN') and isAuthenticated()">
								<a class="dropdown-item" href="/admin/getAllMembers">View All Members</a>
								<a class="dropdown-item" href="/admin/getAllUsers">View All Users</a>
							</sec:authorize>
						</div>
					</div>
				</li>
				&nbsp;&nbsp;
				<li class="nav-item">
                    <div class="dropdown">
                        <a class="btn btn-secondary dropdown-toggle" type="button"
                            id="dropdownMenuButton" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false" href="#"> Matrimony </a>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" href="/user/registerWedProfile">Add Guy/Girl </a> <a
                                class="dropdown-item" href="/user/getAllActiveMembers">View Guys/Girls</a>
                            <sec:authorize
                                access="hasRole('ROLE_ADMIN') and isAuthenticated()">
                                <a class="dropdown-item" href="/admin/getAllMembers">View All Guys/Girls</a>
                            </sec:authorize>
                        </div>
                    </div>
                </li>
			</ul>
		</div>
	</nav>
</div>
