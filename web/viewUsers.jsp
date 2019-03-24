<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>TV Tracker</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles/website.css">
        <link href="https://fonts.googleapis.com/css?family=Bungee+Shade" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Righteous" rel="stylesheet">

    </head>
    <body>
        
<%@include file="header.jsp" %>
<%@include file="user-navigation.jsp" %>
<%@include file="site-navigation.jsp" %>


        
            
        <div id="content">
              <c:choose>
             <c:when test="${theUser.admin == true}"> 
             <div id="breadcrumb">Home > View Users</div>   
             
            <h1 class="h1Table">Edit Users</h1>
            
            
     <table>
          <TH>User ID</th>
          <TH>User Name</th>
          <TH>First Name</th>
          <TH>Last Name</th>
          <th></th>
          
            <!-- Checks database and displays items with the specified category-->
          <c:forEach items="${users}" var="current">
            <tr>
                
                    <td>  <c:out value="${current.userID}"/></td>
                    <td>  <c:out value="${current.username}"/></td>
                    <td>  <c:out value="${current.firstName}"/></td>
                    <td>  <c:out value="${current.lastName}"/></td>
                    <td>
                <form class="button1" action="admin?action=editUser&userID=<c:out value="${current.userID}"/>&username=<c:out value="${current.username}"/>" name="save" method="post">           
                <input class="updateButton" type="submit" value="Update" name="bttn1" />
                </form>


                    </td>
                    
               
            </tr>
          </c:forEach>
        </table>
             </c:when>
             <c:otherwise>
                <p>You can't access this page.</p> 
             </c:otherwise>
            </c:choose>
        </div>
        
    <%@include file="footer.jsp" %>
    </body>
</html>
