<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<div class="container">
		<!-- Good Reference: 
https://www.pair.com/support/kb/types-of-bootstrap-forms/
 -->
		<c:if test="${not empty successMessage }">
			<div class="alert alert-success" role="alert">${successMessage}</div>
		</c:if>
		<h1 align="left">Add New Wedding Profile</h1>
		<form id="addWeddingProfile" action="/user/createWedProfile" enctype="multipart/form-data"
			method="post">
			<div class="form-group row">
				<label for="wedFullName" class="col-sm-2 col-form-label">Name</label>
				<div class="col-sm-5">
					<input type="text" name="wedFullName" class="form-control"
						id="wedFullName" aria-describedby="wedFullName"
						placeholder="Enter Full Name">
				</div>
			</div>
			<div class="form-group row">
				<label for="fatherFullName" class="col-sm-2 col-form-label">Father's
					Name</label>
				<div class="col-sm-5">
					<input type="text" name="fatherFullName" class="form-control"
						id="fatherFullName" aria-describedby="fatherFullName"
						placeholder="Father's Full Name">
				</div>
			</div>
			<div class="form-group row">
				<label for="motherFullName" class="col-sm-2 col-form-label">Mother's
					Name</label>
				<div class="col-sm-5">
					<input type="text" name="motherFullName" class="form-control"
						id="motherFullName" aria-describedby="motherFullName"
						placeholder="Mother's Full Name">
				</div>
			</div>
			<div class="form-group row">
				<label for="wedGender" class="col-sm-2 col-form-label">Gender</label>
				<div class="col-sm-5">
					<select class="form-control" name="wedGender"  id="wedGender">
						<option value="select">Select</option>
						<c:forEach items="${genders}" var="gender">
							<option value="${gender}">${gender}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label for="maritalStatus" class="col-sm-2 col-form-label">Martial
					Status</label>
				<div class="col-sm-5">
					<select class="form-control" name="maritalStatus" id="maritalStatus">
						<option value="select">Select</option>
						<c:forEach items="${listOfMaritalStatus}" var="gotra">
							<option value="${gotra}">${gotra}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group row">

				<label for="dateOfBirth" class="col-sm-2 col-form-label">Date
					of Birth </label>
				<div class="col-sm-5">
					<input type="text" name="dateOfBirth" class="form-control"
						id="dateOfBirth" aria-describedby="dateOfBirth"
						placeholder="MM/DD/YYY">
				</div>
			</div>

			<div class="form-group row">
				<label for="timeOfBirth" class="col-sm-2 col-form-label">Time
					of Birth</label>
				<div class="col-sm-5">
					<input type="time" name="timeOfBirth" class="form-control"
						id="timeOfBirth" aria-describedby="timeOfBirth"
						placeholder="Select time">
				</div>
			</div>

			<div class="form-group row">
				<label for="placeOfBirth" class="col-sm-2 col-form-label">Place of Birth</label>
				<div class="col-sm-5">
					<input type="text" name="placeOfBirth" class="form-control"
						id="placeOfBirth" aria-describedby="placeOfBirth"
						placeholder="Place of Birth">
				</div>
			</div>

			<div class="form-group row">
				<label for="wedOccupation" class="col-sm-2 col-form-label">Occupation</label>
				<div class="col-sm-5">
					<input type="text" name="wedOccupation" class="form-control"
						id="wedOccupation" aria-describedby="wedOccupation"
						placeholder="Occupation">
				</div>
			</div>

			<div class="form-group row">
				<label for="wedGotram" class="col-sm-2 col-form-label">Gotram</label>
				<div class="col-sm-5">
					<select class="form-control" name="wedGotram" id="wedGotram">
						<option value="select">Select</option>
						<c:forEach items="${gotrams}" var="gotra">
							<option value="${gotra.gotraName}">${gotra.gotraName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group row">
				<label for="wedRaashi" class="col-sm-2 col-form-label">Raashi</label>
				<div class="col-sm-5">
					<input type="text" name="wedRaashi" class="form-control"
						id="wedRaashi" aria-describedby="wedRaashi" placeholder="Raashi">
				</div>
			</div>
			<div class="form-group row">
				<label for="wedNakshtra" class="col-sm-2 col-form-label">Nakshatra</label>
				<div class="col-sm-5">
					<input type="text" name="wedNakshtra" class="form-control"
						id="wedNakshtra" aria-describedby="wedNakshtra"
						placeholder="Nakshatra">
				</div>
			</div>
			<div class="form-group row">
				<label for="wedPlace" class="col-sm-2 col-form-label">Location</label>
				<div class="col-sm-5">
					<input type="text" name="wedPlace" class="form-control"
						id="wedPlace" aria-describedby="wedPlace" placeholder="Staying At">
				</div>
			</div>
			
			  <div class="form-group row">
                    <label for="wedImage" class="col-sm-2 col-form-label">Upload Photos</label>
                    <div class="col-sm-5">
                    <input type="file" name="wedImage" class="form-control-file"  id="wedImage" >
                    </div>
            </div>
			
			
  <div class="form-group row">
                    <label for="wedJataka" class="col-sm-2 col-form-label">Upload Jatakam</label>
                    <div class="col-sm-5">
                    <input type="file" name="wedJataka" class="form-control-file"  id="wedJataka" >
                    </div>
            </div>
            			
			<div class="form-group row">
				<label for="exampleInputEmail1" class="col-sm-2 col-form-label">Make
					Visible to All</label>
				<div class="col-sm-1">
					<input type="checkbox" name="makePublic" class="form-control"
						id="makePublic" aria-describedby="wedJataka"
						placeholder="makePublic">
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
    $(document).ready(function(){
      var date_input=$('input[name="dateOfBirth"]'); //our date input has the name "date"
      var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
      var options={
        format: 'mm/dd/yyyy',
        container: container,
        todayHighlight: true,
        autoclose: true,
      };
      date_input.datepicker(options);
    })
</script>
<script>
window.setTimeout(function() {
    $(".alert").fadeTo(500, 0).slideUp(500, function(){
        $(this).remove(); 
    });
}, 2000);

</script>