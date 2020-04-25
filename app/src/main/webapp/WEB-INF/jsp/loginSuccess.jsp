	<%@ include file="base.jsp"%>
	<%@ include file="loggedInNav.jsp"%>
	<%@ include file="header.jsp"%>
	<%
	    String pageName = request.getAttribute("pageName") != null ? (String) request.getAttribute("pageName") : "";
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
