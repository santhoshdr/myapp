<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.1.0/magnific-popup.css">
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/magnific-popup.js/1.1.0/jquery.magnific-popup.js"></script>


<style>
/* img {
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 5px;
  width: 150px;
} */
$(
document ).ready (function() { $('.image-popup-vertical-fit
	').magnificPopup({ type:'image', closeOnContentClick : true, mainClass
	: 'mfp-img-mobile', image : {
            verticalFit : true
	
}

}
);
$('
.image-popup-fit-width ').magnificPopup ({
	type: 'image', closeOnContentClick: true, image: {
            verticalFit: false
}

}
);
$('
.image-popup-no-margins ').magnificPopup ({
	type: 'image', closeOnContentClick: true, closeBtnInside: false,
		fixedContentPos: true, mainClass: 'mfp-no-margins mfp-with-zoom',//class
		to remove default margin from left and right side image: {
            verticalFit: true
}

,
zoom: {
	enabled: true, duration: 300//don't foget to change the duration also in
		CSS
}
}
);
}
);
</style>

<div class="container">
	<div class="container">
		<!-- Good Reference: 
https://www.pair.com/support/kb/types-of-bootstrap-forms/
 -->
		<h1 align="left">${wedProfile.wedFullName}'sProfile</h1>
		<div class="form-group row">
			<label for="wedFullName" class="col-sm-2 col-form-label">Name</label>
			<div class="col-sm-5">
			<c:out value="${wedProfile.wedFullName}"></c:out>
			</div>
		</div>
		<div class="form-group row">
			<label for="fatherFullName" class="col-sm-2 col-form-label">Father's
				Name</label>
			<div class="col-sm-5">
			<c:out value="${wedProfile.fatherFullName}"></c:out>
			</div>
		</div>
		<div class="form-group row">
			<label for="motherFullName" class="col-sm-2 col-form-label">Mother's
				Name</label>
			<div class="col-sm-5">
			<c:out value="${wedProfile.motherFullName}"></c:out>
			</div>
		</div>
		<div class="form-group row">
			<label for="wedGender" class="col-sm-2 col-form-label">Gender</label>
			<div class="col-sm-5">
<c:out value="${wedProfile.wedGender}"></c:out>
			</div>
		</div>
		<div class="form-group row">
			<label for="maritalStatus" class="col-sm-2 col-form-label">Martial
				Status</label>
			<div class="col-sm-5">
<c:out value="${wedProfile.maritalStatus}"></c:out>			
			</div>
		</div>
		<div class="form-group row">
			<label for="dateOfBirth" class="col-sm-2 col-form-label">Date
				of Birth </label>
			<div class="col-sm-5">
			<c:out value="${wedProfile.dateOfBirth}"></c:out>  
			</div>
		</div>

		<div class="form-group row">
			<label for="timeOfBirth" class="col-sm-2 col-form-label">Time
				of Birth</label>
			<div class="col-sm-5">
			<c:out value="${wedProfile.timeOfBirth}"></c:out>  
			</div>
		</div>

		<div class="form-group row">
			<label for="placeOfBirth" class="col-sm-2 col-form-label">Place
				of Birth</label>
			<div class="col-sm-5">
			 <c:out value="${wedProfile.placeOfBirth}"></c:out>  
			</div>
		</div>

		<div class="form-group row">
			<label for="wedOccupation" class="col-sm-2 col-form-label">Occupation</label>
			<div class="col-sm-5">
			<c:out value="${wedProfile.wedOccupation}"></c:out>  
			</div>
		</div>




		<div class="form-group row">
			<label for="wedGotram" class="col-sm-2 col-form-label">Gotram</label>
			<div class="col-sm-5">
			 <c:out value="${wedProfile.wedGotram}"></c:out>  
			</div>
		</div>
		<div class="form-group row">
			<label for="wedRaashi" class="col-sm-2 col-form-label">Raashi</label>
			<div class="col-sm-5">
			<c:out value="${wedProfile.wedRaashi}"></c:out>  
			</div>
		</div>
		<div class="form-group row">
			<label for="wedNakshtra" class="col-sm-2 col-form-label">Nakshatra</label>
			<div class="col-sm-5">
			 <c:out value="${wedProfile.wedNakshtra}"></c:out>  
			</div>
		</div>
		<div class="form-group row">
			<label for="wedPlace" class="col-sm-2 col-form-label">Location</label>
			<div class="col-sm-5">
			 <c:out value="${wedProfile.wedPlace}"></c:out>  
			</div>
		</div>
		<div class="form-group row">
			<label for="wedImage class=" col-sm-2col-form-label">Uploaded
				Photo</label>
			<div class="col-sm-5">
				<c:forEach items="${wedProfile.wedImageFilePath}" var="single">
					<a class="image-popup-fit-width" href="/user/${single }" title="">
						<img name="photoName" id="imgLogo" src="/user/${single }"
						width="75" height="75">
					</a>
				</c:forEach>
			</div>
		</div>
		<div class="form-group row">
			<label for="wedImage class=" col-sm-2col-form-label">Uploaded
				Document</label>
			<div class="col-sm-5">
			 <c:forEach items="${wedProfile.wedJatakaFilePath}" var="file">
            <a href="/user/downloadFile/${file }" title="Click to download" >
             <c:out value="${file }"></c:out> </a>
            &nbsp;&nbsp;
            </br>
            </c:forEach>
			
            </br>
			</div>
		</div>


	</div>
</div>

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
