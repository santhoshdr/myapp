<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container">
	<div class="container">
		
		<!-- Good Reference: 
https://www.pair.com/support/kb/types-of-bootstrap-forms/
 -->
 
 
<%
System.out.println("back from payment page1111111111111111111");
String msg = (String)request.getParameter("addMember"); 
if(msg != null && msg.equals("true")){
 %>
 <div class="alert alert-success" role="alert">
  Member Added Successfully
</div>
<%   
}
%>

<c:if test="${not empty successMessage }">
 <div class="alert alert-success" role="alert">
  ${successMessage}
</div>
</c:if>

<c:if test="${not empty errorMessage }">
 <div class="alert alert-danger" role="alert">
  ${errorMessage}
</div>
</c:if>

<h1 align="left">Add New Member</h1>
 
		<form id="addMemberForm"  action="/user/saveMember" method="post">
			<div class="form-group row">
				<label for="exampleInputEmail1" class="col-sm-2 col-form-label">Name</label>
				<div class="col-sm-5">
					<input type="text" name="firstName" class="form-control" id="Name"
						aria-describedby="emailHelp" placeholder="Enter Name">
				</div>
			</div>
			<div class="form-group row">
				<label for="exampleInputPassword1" class="col-sm-2 col-form-label">Father's
					/ Husband's / Family Name</label>
				<div class="col-sm-5">
					<select class="form-control" name="relation" id="relation">
						<option value="select">Select</option>
						<option value="fatherName">Fathers' Name</option>
						<option value="husbandName">Husband's' Name</option>
						<option value="familyName">Family's Name</option>
					</select>
				</div>
			</div>

			<div class="form-group row">
				<label for="exampleInputPassword1" class="col-sm-2 col-form-label"></label>
				<div class="col-sm-5">
					<input type="text" class="form-control" id="relationShip"
						placeholder="Father's/ Husband's Name">
				</div>
			</div>





			<div class="form-group row">
				<label for="exampleInputPassword1" class="col-sm-2 col-form-label">Gotram</label>
				<div class="col-sm-5">
					<select class="form-control" name="gotram" id="gotram">
						<option value="select">Select</option>
						<c:forEach items="${gotrams}" var="gotra">
							<option value="${gotra.gotraName}">${gotra.gotraName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label for="exampleInputPassword1" class="col-sm-2 col-form-label">Address</label>
				<div class="col-sm-5">
					<textarea class="form-control" name="address" placeholder="Address"
						rows="2" id="address"></textarea>
				</div>
			</div>

			<div class="form-group row">
				<label for="exampleInputPassword1" class="col-sm-2 col-form-label">Phone
					Number</label>
				<div class="col-sm-5">
					<input type="text" name="mobileNumber" class="form-control"
						id="phoneNumber" placeholder="Phone Nuber">
				</div>
			</div>


			<div class="form-group row">
				<label for="exampleInputPassword1" class="col-sm-2 col-form-label">Class
					Of MemberShip desired</label>
				<div class="col-sm-5">
					<select class="form-control" name="classofMembershipDesired"
						id="classofMembershipDesired">
						<option value="select">Select</option>
						<c:forEach items="${classOfMembership}" var="membership">
							<option value="${membership}">${membership.classOfMembership}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group row">
				<label for="exampleInputPassword1" class="col-sm-2 col-form-label">Mode
					Of Payment</label>
				<div class="col-sm-5">
					<select class="form-control" name="modeofPayment"
						id="modeofPayment">
						<option value="select">Select</option>
						<c:forEach items="${modeOfPayment}" var="modeofpayment">
							<option value="${modeofpayment}">${modeofpayment.modeOfPayment}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label for="exampleInputPassword1" class="col-sm-2 col-form-label">Amount
					Willing to Pay</label>
				<div class="col-sm-5">
					<input type="text" name="amount" class="form-control"
						id="exampleInputPassword1" placeholder="Password">
				</div>
			</div>

			<div class="form-group row">
				<label for="exampleInputPassword1" class="col-sm-2 col-form-label"></label>
				<div class="col-sm-5">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>
		</form>

	</div>
</div>



<script>
window.setTimeout(function() {
    $(".alert").fadeTo(500, 0).slideUp(500, function(){
        $(this).remove(); 
    });
}, 2000);

</script>