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
            <div id="breadcrumb">Home > Edit Shows</div>
        
                       
            <h1 class="h1Table">Edit Shows</h1>
            
            
 
     <table>
          <TH>Show ID</th>
          <TH>Title</th>
          <th>Category</th>
          <th>  <form  action="addItem.jsp" name="addItem" method="post">           
                <input class="updateButton" type="submit" value="Add Show" name="bttn1" />
                </form>
          </th>
          
          
            <!-- Checks database and displays items with the specified category-->
          <c:forEach items="${db}" var="current">
            <tr>
               
                    <td><c:out value="${current.itemCode}"/></td>
                    <td class="categoryTitle"><a href="admin?action=editItem&itemCode=<c:out value="${current.itemCode}" />"><c:out value="${current.itemName}" /></a></td>
                    <td><c:out value="${current.category}"/></td>
                    <td>
                 <form class="button1" action="admin?action=editItem&itemCode=<c:out value="${current.itemCode}" />" name="save" method="post">           
                <input class="updateButton" type="submit" value="Update" name="bttn1" />
                </form>
                <form class="button1" action="admin?action=deleteItem&itemCode=<c:out value="${current.itemCode}" />" name="save" method="post">           
                <input class="updateButton" type="submit" value="Delete" name="bttn1" />
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
