<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="login">
 <c:choose>
             <c:when test="${theUser != null}"> 
                 	<p>Hello, <c:out value="${theUser.username}"/>!| <a href="profile.jsp">Profile</a> | <a href="/myWebsite/register?action=logout">Sign Out</a><p>
             </c:when>
             <c:otherwise>
                 	<p>User Not Logged In<p>
             </c:otherwise>
 </c:choose>       
</div>

        
        <div id="header">
          TV Tracker
        </div>