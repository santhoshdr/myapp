<!-- view wed table -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="container" style="margin-top: 65px;">

	<body style="margin: 20px auto">



		<c:if test="${not empty successMessage }">
			<div class="alert alert-success" role="alert">${successMessage}</div>
		</c:if>
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

		<div class="container">
			<table id="myTable" class="table table-striped">
				<thead>
					<tr>
						<th>Id</th>
						<th>First Name</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${wedProfileList}" var="element">
						<tr>
							<td>${element.id}</td>
							<td>${element.wedFullName}</td>
							<td><a href="/user/viewWedProfile/${element.id}"
								class="btn btn-primary"><i class="fas fa-user-edit ml-2"></i>View/Edit </a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
	<script>
$(document).ready(function(){
    $('#myTable').dataTable();
}),
$("#myTable").on('click','.viewProfile',function(){
    // get the current row
    var currentRow=$(this).closest("tr");
    var userId=currentRow.find("td:eq(0)").text();
    var username=currentRow.find("td:eq(1)").text(); // get current row 1st TD value
    alert("==========" + currentRow + "=======" + userId)
})
</script>
</div>

<div class="modal fade" id="viewProfile" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Profile :</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">...</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
	</div>
</div>

<link rel="stylesheet" href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css">
<script type="text/javascript"
	src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
	
