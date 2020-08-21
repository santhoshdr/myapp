<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container max-height:300px !important" >

logged In body.

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<c:if test="${not empty successMessage }">
 <div class="alert alert-success alert-dismissible fade show"
                role="alert">
               ${successMessage}
                <button type="button" class="close" data-dismiss="alert"
                    aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
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
</div>