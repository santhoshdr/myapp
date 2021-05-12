<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.1.0/magnific-popup.css">
    <script type="text/javascript"
    src="https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.1.0/jquery.magnific-popup.js"></script>
    

<style>
/* img {
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 5px;
  width: 150px;
} */


$(document).ready(function() {

    $('.image-popup-vertical-fit').magnificPopup({
        type: 'image',
        closeOnContentClick: true,
        mainClass: 'mfp-img-mobile',
        image: {
            verticalFit: true
        }
        
    });

    $('.image-popup-fit-width').magnificPopup({
        type: 'image',
        closeOnContentClick: true,
        image: {
            verticalFit: false
        }
    });

    $('.image-popup-no-margins').magnificPopup({
        type: 'image',
        closeOnContentClick: true,
        closeBtnInside: false,
        fixedContentPos: true,
        mainClass: 'mfp-no-margins mfp-with-zoom', // class to remove default margin from left and right side
        image: {
            verticalFit: true
        },
        zoom: {
            enabled: true,
            duration: 300 // don't foget to change the duration also in CSS
        }
    });

});
</style>

<div class="container">
    <div class="container">
        <!-- Good Reference: 
https://www.pair.com/support/kb/types-of-bootstrap-forms/
 -->
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
        <h1 align="left">${wedProfileList[0].wedFullName}'s  Profile</h1>
        <form id="updateWedProfile" action="/user/updateWedProfile" enctype="multipart/form-data"
            method="post">
            <input type="hidden" name="id"   value="${wedProfileList[0].id}">
            <div class="form-group row">
                <label for="wedFullName" class="col-sm-2 col-form-label">Name</label>
                <div class="col-sm-5">
                    <input type="text" name="wedFullName" class="form-control"
                        id="wedFullName" aria-describedby="wedFullName"
                        placeholder="Enter Full Name"  value="${wedProfileList[0].wedFullName}">
                </div>
            </div>
            <div class="form-group row">
                <label for="fatherFullName" class="col-sm-2 col-form-label">Father's
                    Name</label>
                <div class="col-sm-5">
                    <input type="text" name="fatherFullName" class="form-control"
                        id="fatherFullName" aria-describedby="fatherFullName"
                        placeholder="Father's Full Name" value="${wedProfileList[0].fatherFullName}">
                </div>
            </div>
            <div class="form-group row">
                <label for="motherFullName" class="col-sm-2 col-form-label">Mother's
                    Name</label>
                <div class="col-sm-5">
                    <input type="text" name="motherFullName" class="form-control"
                        id="motherFullName" aria-describedby="motherFullName"
                        placeholder="Mother's Full Name" value="${wedProfileList[0].motherFullName}">
                </div>
            </div>
            <div class="form-group row">
                <label for="wedGender" class="col-sm-2 col-form-label">Gender</label>
                <div class="col-sm-5">
                    <select class="form-control" name="wedGender"  id="wedGender"  >
                        <c:forEach items="${genders}" var="gender">
                        <c:choose>
						   <c:when test="${wedProfileList[0].wedGender.equals (gender)}">
						   <option value="${gender}"  selected="selected" >${gender}</option>
						   </c:when>
						   <c:otherwise><option value="${gender}" >${gender}</option></c:otherwise>
						</c:choose>
                         </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group row">
                <label for="maritalStatus" class="col-sm-2 col-form-label">Martial
                    Status</label>
                <div class="col-sm-5">
                    <select class="form-control" name="maritalStatus" id="maritalStatus">
                            <c:forEach items="${listOfMaritalStatus}" var="maritalStatus">
                            <c:choose>
                           <c:when test="${wedProfileList[0].maritalStatus.equals (maritalStatus)}">
                           <option value="${wedProfileList[0].maritalStatus}"  selected="selected" >${wedProfileList[0].maritalStatus}</option>
                           </c:when>
                           <c:otherwise>
                           <option value="${maritalStatus}" >${maritalStatus}</option>
                        </c:otherwise>
                        </c:choose>
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
                        placeholder="MM/DD/YYY"  value="${wedProfileList[0].dateOfBirth}">
                </div>
            </div>

            <div class="form-group row">
                <label for="timeOfBirth" class="col-sm-2 col-form-label">Time
                    of Birth</label>
                <div class="col-sm-5">
                    <input type="time" name="timeOfBirth" class="form-control"
                        id="timeOfBirth" aria-describedby="timeOfBirth"
                        placeholder="Select time" value="${wedProfileList[0].timeOfBirth}">
                </div>
            </div>

            <div class="form-group row">
                <label for="placeOfBirth" class="col-sm-2 col-form-label">Place of Birth</label>
                <div class="col-sm-5">
                    <input type="text" name="placeOfBirth" class="form-control"
                        id="placeOfBirth" aria-describedby="placeOfBirth"
                        placeholder="Place of Birth" value="${wedProfileList[0].placeOfBirth}">
                </div>
            </div>

            <div class="form-group row">
                <label for="wedOccupation" class="col-sm-2 col-form-label">Occupation</label>
                <div class="col-sm-5">
                    <input type="text" name="wedOccupation" class="form-control"
                        id="wedOccupation" aria-describedby="wedOccupation"
                        placeholder="Occupation"  value="${wedProfileList[0].wedOccupation}">
                </div>
            </div>
            <div class="form-group row">
                <label for="wedGotram" class="col-sm-2 col-form-label">Gotram</label>
                <div class="col-sm-5">
                    <select class="form-control" name="wedGotram" id="wedGotram">
                        <option value=" ${wedProfileList[0].wedGotram}"> ${wedProfileList[0].wedGotram}</option>
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
                        id="wedRaashi" aria-describedby="wedRaashi" placeholder="Raashi" value="${wedProfileList[0].wedRaashi}">
                </div>
            </div>
            <div class="form-group row">
                <label for="wedNakshtra" class="col-sm-2 col-form-label">Nakshatra</label>
                <div class="col-sm-5">
                    <input type="text" name="wedNakshtra" class="form-control"
                        id="wedNakshtra" aria-describedby="wedNakshtra"
                        placeholder="Nakshatra"  value="${wedProfileList[0].wedNakshtra}">
                </div>
            </div>
            <div class="form-group row">
                <label for="wedPlace" class="col-sm-2 col-form-label">Location</label>
                <div class="col-sm-5">
                    <input type="text" name="wedPlace" class="form-control"
                        id="wedPlace" aria-describedby="wedPlace" placeholder="Staying At" value="${wedProfileList[0].wedPlace}">
                </div>
            </div>
            <div class="form-group row">
                <label for="wedImage" class="col-sm-2 col-form-label">Upload
                    Photos</label>
                <div class="col-sm-5">
                    <input type="file"name="wedImage" class="custom-file-input form-control"
                        id="wedImage" aria-describedby="inputGroupFileAddon01"> <label
                        class="custom-file-label" for="wedImage">Choose file</label>
                </div>
            </div>
            <div class="form-group row">
                <label for="wedJataka" class="col-sm-2 col-form-label">Upload
                    Jatakam</label>
                <div class="col-sm-5">
                    <input type="file" name="wedJataka" class="custom-file-input form-control"
                        id="wedJataka" aria-describedby="wedJataka"> <label
                        class="custom-file-label" for="wedJataka">Choose file</label>
                </div>
            </div>
            
            
   
            <div class="form-group row">
                <label for="wedImage class="col-sm-2 col-form-label">Uploaded Photo</label>
                <div class="col-sm-5">
           <c:forEach items="${wedProfileList[0].wedImageFilePath}" var="single">
            <a class="image-popup-fit-width" href="/user/${single}" title="">
              <img  name="photoName"  id="imgLogo" src="/user/${single}" width="75" height="75">
            </a>
            <a href=""  id="deletePhotos" onclick="callDelete('${single}')">delete</a>
            </c:forEach>
            </div>
            </div>
            
                  <div class="form-group row">
                <label for="wedImage class="col-sm-2 col-form-label">Uploaded Document</label>
                <div class="col-sm-5">
           <c:forEach items="${wedProfileList[0].wedJatakaFilePath}" var="single">
            <a href="/user/downloadFile/${single}" title="Click to download" >
             <c:out value="${single }"></c:out> </a>
            &nbsp;&nbsp;
            <a href="/user/deleteFile/${wedProfileList[0].id}/${single}" title = "Delete File"  id="deleteDocument">Delete</a>
            </br>
            </c:forEach>
            </div>
            </div>
            <div class="form-group row">
                <label for="exampleInputEmail1" class="col-sm-2 col-form-label">Make Visible to All</label>
                <div class="col-sm-1">
                <c:choose>
                           <c:when test="${wedProfileList[0].makePublic}">
                          <input type="checkbox" name="makePublic" class="form-control"
                        id="makePublic" aria-describedby="makePublic"
                        placeholder="makePublic" checked>
                           </c:when>
                           <c:otherwise>
                           <input type="checkbox" name="makePublic" class="form-control"
                        id="makePublic" aria-describedby="makePublic"
                        placeholder="makePublic" >
                        </c:otherwise>
                        </c:choose>
                </div>
            </div>
            <div class="form-group row">
                <label for="exampleInputPassword1" class="col-sm-2 col-form-label"></label>
                <div class="col-sm-5">
                    <a href="/user/loginHome" class="btn btn-primary">Close</a>
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script>

function callDelete(image){
alert("deleting image" + image);
	$.ajax({
        type: "POST",
        url: "/user/deletePhoto",
        data: {
        	photoname :image
        },
     
        async: false,
        success: function (data) {
            //$("#response").html(data.d);
        }
    });

}


</script>

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

<script>

$(document).ready(function() {

    $('.image-popup-vertical-fit').magnificPopup({
        type: 'image',
        closeOnContentClick: true,
        mainClass: 'mfp-img-mobile',
        image: {
            verticalFit: true
        }
        
    });

    $('.image-popup-fit-width').magnificPopup({
        type: 'image',
        closeOnContentClick: true,
        image: {
            verticalFit: false
        }
    });

    $('.image-popup-no-margins').magnificPopup({
        type: 'image',
        closeOnContentClick: true,
        closeBtnInside: false,
        fixedContentPos: true,
        mainClass: 'mfp-no-margins mfp-with-zoom', // class to remove default margin from left and right side
        image: {
            verticalFit: true
        },
        zoom: {
            enabled: true,
            duration: 300 // don't foget to change the duration also in CSS
        }
    });

});
</script>