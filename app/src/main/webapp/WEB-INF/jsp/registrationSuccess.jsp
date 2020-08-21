<%@ include file="base.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">
<body>
	<%@ include file="nav.jsp"%>
	
	<%System.out.println("BEFOREEEEEEEEEEEEEE"); %>
	<c:if test="${notificationType == 'SMS'}">
	<%@ include file="activateAccountUsingSMS.jsp"%>
	<%System.out.println("HELOOOOOOOOOOOOOOOOOOOOOOO"); %>
	
	
	</c:if>
	<c:if test="${notificationType == 'EMAIL'}">
	<%@ include file="activateAccount.jsp"%>
    <%System.out.println("EMAILEMAILEMAILEMAILEMAILEMAILEMAILEMAILEMAILEMAIL"); %>
    </c:if>
	
	
	
	<%System.out.println("ASFTERRRRRRRRRRRRRRRR"); %>

	<%@ include file="footer.jsp"%>
</body>

</html>