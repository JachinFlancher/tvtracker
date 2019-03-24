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
        <script src="script/jquery-3.3.1.js"></script>
        <script type="text/javascript" src="javscript.js"></script>
    </head>
    <body>
        
 <%@include file="header.jsp" %>
<%@include file="user-navigation.jsp" %>
<%@include file="site-navigation.jsp" %>

        <div id="content">
    <div id="breadcrumb">Home > My Shows</div>
    
   
    <p><c:out value="${message}"/></p>
    
     <c:choose>
             <c:when test="${currentProfile.getItemRatings().size() == 0}"> 
                 <p>You have no shows saved.</p>
             </c:when>
             <c:otherwise>
    <h1 class="h1Table">Saved Shows</h1>

            
         <table>           
   <tr>
    <th>Show</th>
    <th>Category</th>
    <th>My Rating</th>
    <th>Watched It</th>
    <th> </th>
  </tr>

          <c:forEach var="current" items="${currentProfile.getItemRatings()}">
            <tr>
                <td> <a href="categories?itemCode=<c:out value="${current.getItem().itemCode}" />"><c:out value="${current.getItem().itemName}" /></a></td>
             <td><c:out value="${current.getItem().category}"/></td>
             <td><c:out value="${current.rating}"/>/5</td>
             <td>
                 <c:choose>
             <c:when test="${current.madeIt}"> 
                 	&#10004;
             </c:when>
             <c:otherwise>
                 	&#10060;
             </c:otherwise>
                 </c:choose>
             </td>
             <td>
                <form class="button1" action="profile?action=updateProfile&itemList=<c:out value="${current.getItem().itemCode}"/>" name="save" method="post">
                <input type="hidden" name="checkItemCode" value=<c:out value="${current.getItem().itemCode}"/>>
                <input class="updateButton" type="submit" value="Update" name="bttn1" />
                </form>

                <form class="button2" action="profile?action=deleteItem&itemList=<c:out value="${current.getItem().itemCode}"/>" name="delete" method="post">
                <input type="hidden" name="checkItemCode" value=<c:out value="${current.getItem().itemCode}"/>>
                <input class="deleteButton" type="submit" value="Delete" name="bttn2" />
                </form>
             </td>
            </tr>
          </c:forEach>
                    
        </table>
                 </c:otherwise>              
 </c:choose>
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>
