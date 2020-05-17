<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container"    style="margin-top: 65px;" >

<html>
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
    
    


<link rel="stylesheet" href="http://cdn.datatables.net/1.10.2/css/jquery.dataTables.min.css"></style> 
 <script type="text/javascript" src="http://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script> 
</head>

<body style="margin:20px auto">
<div class="container">

      <div class="modal fade" id="changeRoleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Change Role</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
<form id="changeRole" action="/admin/changeRole"  method="post">
            <input type="hidden" name="userId" id="userId" value="" />
            <div class="form-group row">
                <label for="wedFullName" class="col-sm-2 col-form-label">Change Role :</label>
                <div class="col-sm-5">
                        <select class="form-control"  name="newRole"  id="newRole">
                        <option value="select">Select</option>
                        <c:forEach items="${listOfRoles}" var="role">
                            <option value="${role}">${role}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary">Save changes</button>
      </div>
      </form>
    </div>
  </div>
</div>


<table id="myTable" class="table table-striped" >  
        <thead>  
          <tr>  
          <th>Id</th>
            <th>First Name</th>
            <th>Last Name</th>  
            <th>Phone Number</th>  
             <th>Emai ID</th>  
             <sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
              <th class ="col-6">Action</th>  
              </sec:authorize>
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
     <sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
     <td class ="col-6">

<%--     <c:choose>
      <c:when test="${element.isActive()}">
        <button class="btn btn-success"  type="button" id="disableUser" >Disable User</button>
      </c:when>
      <c:otherwise>
      <button class="btn btn-danger"  type="button" id="enableUser" >Enable User</button>
      </c:otherwise>
    </c:choose> --%>
    
      <c:choose>    
    <c:when test="${element.isAdmin()}">
      <button type="button" class="btn btn btn-danger btn-lg open-modal disableAdmin"  id="disableUser" >Remove Admin</button>
      </c:when>
      <c:otherwise>
      <button type="button" class="btn btn btn-danger btn-lg open-modal makeAdmin"  id="makeAdmin" >Make Admin</button>
      </c:otherwise>
    </c:choose>
    </td>
   <td class ="col-6">
      <button type="button" class="btn btn btn-danger btn-lg open-modal changeRole"  id="changeRole" >Change Role</button>
    </td>
  </sec:authorize>
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
   $("#myTable").on('click','.disableAdmin',function(){
	   
        var currentRow=$(this).closest("tr");
        var userId=currentRow.find("td:eq(0)").text();
        var username=currentRow.find("td:eq(1)").text(); // get current row 1st TD value
       
        var r = confirm("Enable the User");
        if (r == true) {
            $.ajax({
                url: '/admin/makeorRemoveAdmin',
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
     }),$("#myTable").on('click','.makeAdmin',function(){
            // get the current row
            var currentRow=$(this).closest("tr");
            var userId=currentRow.find("td:eq(0)").text();
            var username=currentRow.find("td:eq(1)").text(); // get current row 1st TD value
            var r = confirm("Disable  the User");
            if (r == true) {
            	$.ajax({
                    url: '/admin/makeorRemoveAdmin',
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
         }),$("#myTable").on('click','.changeRole',function(){
             
             var currentRow=$(this).closest("tr");
             var userId=currentRow.find("td:eq(0)").text();
             var username=currentRow.find("td:eq(1)").text(); // get current row 1st TD value
             $(".modal-body #userId").val(userId);
             $("#changeRoleModal").modal("show");
             
/*              var r = confirm("Enable the User");
             if (r == true) {
                 $.ajax({
                     url: '/admin/makeorRemoveAdmin',
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
             }  */
          });

</script>


</div>






