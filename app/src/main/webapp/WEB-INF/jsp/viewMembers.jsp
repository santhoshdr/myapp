<!--  ALL Members -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<head>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script> -->

<script
              src="https://code.jquery.com/jquery-3.5.0.min.js"
              integrity="sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ="
              crossorigin="anonymous"></script>

<link rel="stylesheet"
    href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">

<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">   
    
    <link rel="stylesheet"
    href="/resources/css/dataTables.bootstrap4.min.css">
<link href="/resources/css/bootstrap.min.css" rel="stylesheet">   
 <link rel="stylesheet" href="/resources/css/jquery.dataTables.min.css"></style>     
    
    


<!-- <link rel="stylesheet" href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css"></style> -->
<!-- <script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script> -->
</head>
<div class="container" style="margin-top: 65px;">
<body>
	<div class="container">
		<table id="myTable" class="table table-striped">
			<thead>
				<tr>
				<th>User Id</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Phone Number</th>
					<th>Email ID</th>
     				<th class="col-3">Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listofusers}" var="element">
					<tr>
					<td>${element.id}</td>
						<td>${element.firstName}</td>
						<td>${element.lastName}</td>
						<td>${element.mobileNumber}</td>
						<td>${element.emailAddress}</td>
						<td>
						<sec:authorize	access="hasRole('ROLE_ADMIN') and isAuthenticated()">
							<c:choose>
									<c:when test="${element.isActive()}">
									<button type="button" class="btn btn-success btn-lg open-modal disableUser"  id="disableUser" >Disable User</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-danger btn-lg open-modal enableUser"  id="enableUser" >Enable User</button>
									</c:otherwise>
								</c:choose>
						</sec:authorize>
						<button type="button" class="btn btn-primary btn-lg open-modal viewMember"  id="viewMemberDetails" >View Member Details</button>
					</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	
	
<div class="modal" id=modal>
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Confirmation</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <!-- Modal body -->
            <div class="modal-body">
                <form id="loginForm" >
                    <div class="form-group">
                        <label  for="confirm">You want to enable user : </label> 
                        <div id="userName"></div>
                        <input id="userId" type="hidden" value=""/>
                      <label for="comments">Comments: </label> 
                      <div class="form-group">
                        <textarea rows="10" cols="50" id="comments" ></textarea>
                      </div>
                    <button type="submit" class="btn btn-primary" id="submit">Submit</button>
                    <button type="submit" class="btn btn-primary">Close</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<div class="modal" id="viewMemberModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">Member Details</h4>
        <button type="button"  id="viewMemberClostButtonTop" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
       <div id="memberDetails"></div>
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button"  id="viewMemberClostButton" class="btn btn-danger" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
	
</body>
<script>
$(document).ready(function(){
    $('#myTable').dataTable();
   }),
   $("#myTable").on('click','.enableUser',function(){
        // get the current row
        var currentRow=$(this).closest("tr");
        var userId=currentRow.find("td:eq(0)").text();
        var username=currentRow.find("td:eq(1)").text(); // get current row 1st TD value
        var r = confirm("Enable the User");
        if (r == true) {
        	$.ajax({
                url: '/admin/activateUser',
                type: "POST",
                data: {
                	userId : userId
                },
                success: function(response, textStatus, jqXHR){
                	location.reload(true);
                    var result = JSON.stringify(response.message, null, 4)
                },
                error: function (response, textStatus, errorThrown)
                 {
                   var result = JSON.stringify(response.responseJSON.message, null, 4)
                     }
            });
        } 
	 }),
	 $("#myTable").on('click','.viewMember',function(){
	        // get the current row
	        var currentRow=$(this).closest("tr");
	        var userId=currentRow.find("td:eq(0)").text();
	        var username=currentRow.find("td:eq(1)").text(); // get current row 1st TD value
	        alert("currentRow"+ userId );
	        $.ajax({
	                url: '/user/viewMember',
	                type: "GET",
	                data: {
	                    userId : userId
	                },
	                success: function(response, textStatus, jqXHR){
	                    var result = JSON.stringify(response.message, null, 4)
	                     var table =""
	                    table = $('<table>').addClass('foo');
                        var row = '<tr><td>User ID</td><td>'+response.id+'</td></tr>'+
                                             '<tr><td>First Name</td><td>'+response.firstName+'</td></tr>'+
                                             '<tr><td>LastName Name</td><td>'+response.lastName+'</td></tr>' +
                                             '<tr><td>Mobile Number</td><td>'+response.mobileNumber+'</td></tr>'+
                                             '<tr><td>Email Id</td><td>'+response.emailAddress+'</td></tr>'+
                                             '<tr><td>Gotram</td><td>'+response.gotram+'</td></tr>'+
                                             '<tr><td>Address</td><td>'+response.address+'</td></tr>';
                        table.append(row);
                        $("#memberDetails").html(table);
	                    $('#viewMemberModal').show();
	                },
	                error: function (response, textStatus, errorThrown)
	                 {
	                   var result = JSON.stringify(response.responseJSON.message, null, 4)
	                   alert("error"+ result);
	                     }
	            });
	       
	     }),$("#myTable").on('click','.disableUser',function(){
	        // get the current row
	        var currentRow=$(this).closest("tr");
	        var userId=currentRow.find("td:eq(0)").text();
	        var username=currentRow.find("td:eq(1)").text(); // get current row 1st TD value
	        var r = confirm("Disable  the User");
	        if (r == true) {
	            $.ajax({
	                url: '/admin/deactivateUser',
	                type: "POST",
	                data: {
	                    userId : userId
	                },
	                success: function(response, textStatus, jqXHR){
	                	location.reload(true);
	                    var result = JSON.stringify(response.message, null, 4)
	                },
	                error: function (response, textStatus, errorThrown)
	                 {
	                   var result = JSON.stringify(response.responseJSON.message, null, 4)
	                     }
	            });
	        } 
	     }),
	     $( "#viewMemberClostButton" ).click(function() {
	    	 $("#memberDetails").val('');
	    	 $('#viewMemberModal').hide();
	     }),
         $( "#viewMemberClostButtonTop" ).click(function() {
             $("#memberDetails").val('');
             $('#viewMemberModal').hide();
         });



</script>
 </div>
