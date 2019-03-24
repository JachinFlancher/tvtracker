<div id="userNav">
          
            
 <c:choose>
             <c:when test="${theUser == null}"> 
                <a href="login.jsp">Sign In</a>|
                <a href="register.jsp">Register</a>
             </c:when>
             <c:otherwise>
               
                <a href="myShows.jsp">My Shows</a>
               
             </c:otherwise>              
 </c:choose>
                <c:choose>
                <c:when test="${theUser.admin == true}"> 
                | <a href="/myWebsite/admin?action=view">Edit Shows</a>
                | <a href="/myWebsite/admin?action=viewUsers">Edit Users</a>
                   </c:when>  
                </c:choose>
        </div> 