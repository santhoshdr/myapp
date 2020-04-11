

<html lang="en">
<body>
	<%@ include file="loggedInNav.jsp"%>
	<%@ include file="header.jsp"%>
	<%
	    System.out.println("*************" + request.getRequestURL());
	    String pageName = request.getAttribute("pageName") != null ? (String) request.getAttribute("pageName") : "";
	    System.out.println("pageName ===." + pageName);
	    switch (pageName) {
	    case "viewMembers":
	%>
	<%@ include file="viewMembers.jsp"%>
	<%
	    System.out.println("It\'s Sunday.");
	        break;
	    case "loginHome":
	%>
	<%@ include file="loggedInBody.jsp"%>
	<%
	    break;
	    case "addMember":
	%>
	<%@ include file="addMember.jsp"%>
	<%
	    break;
	    case "getAllUsers":
	%>
	<%@ include file="viewUsers.jsp" %>
	<%
	    default:
	%>
	<%@ include file="loggedInBody.jsp"%>
	<%
	    }
	%>


	<%@ include file="footer.jsp"%>
</body>
</html>
<%@ include file="base.jsp"%>