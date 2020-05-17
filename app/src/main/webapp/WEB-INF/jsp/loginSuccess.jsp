	<%@ include file="base.jsp"%>
	<%@ include file="loggedInNav.jsp"%>
	<%@ include file="header.jsp"%>
	<%
	    String pageName = request.getAttribute("pageName") != null ? (String) request.getAttribute("pageName") : "";
	    System.out.println("Pagename" + pageName );
	    switch (pageName) {
	    case "matromonialServiceViewWedProfile":
	        %>
	    <%@ include file="moredetailsWedProfile.jsp"%> 
	    <%
	    break;
	    case "viewAllWedProfiles":
	      %>
	      <%@ include file="viewAllWedProfiles.jsp"%> 
        <%     
        break;
	    case "viewWedProfile":
	     %>
	      <%@ include file="viewWedProfile.jsp"%>        
	    <%	    
	    break;
	    case "viewMywedProfile":
	    %>
    <%@ include file="viewMywedProfile.jsp"%>	    
	    <%  
	    break;
	    case "viewMembers":
	%>
	<%@ include file="viewMembers.jsp"%>
	<%
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
        case "registerWedProfile":
    %>
    <%@ include file="registerWedProfile.jsp"%>
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
