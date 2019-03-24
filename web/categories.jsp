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


        <div id="contentCategory">
            <div id="breadcrumb">Home > Categories</div>
        <div id="categories">
                       
            <h1>All Categories</h1>
            
            
            <div id="selectCategory">
                
                <!-- Drop down list that allows user to select a specific category to display  s-->
                <form action="categories" name="registration" method="post">
			<p>Select a specific category:</p>  
			<select name="category">  
				<option selected="" value="">(Select a category)</option>  
				<option value="Comedy">Comedy</option>  
				<option value="Drama">Drama</option>  
				<input type="submit" name="submit" value="Submit" />  
			</select> 
                </form>
            </div>
     <table style="overflow-x:auto;">
          <TH>Comedies</th>
          <TH>Title</th>
          <TH>Rating</th>
          
            <!-- Checks database and displays items with the specified category-->
          <c:forEach items="${db}" var="current">
            <tr>
                <c:if test="${current.category=='Comedy'}">
                    <td>  <img src=<c:out value="${current.getImageURL()}" /> alt="showimage" height = 200 width = 132></td>
                    <td class="categoryTitle"><a href="categories?itemCode=<c:out value="${current.itemCode}" />"><c:out value="${current.itemName}" /></a></td>
                    <td><c:out value="${current.rating}"/>/5 &#9733</td>
                </c:if>  
            </tr>
          </c:forEach>
        </table>
 <table>
          <TH>Dramas  </th>
          <TH>Title</th>
          <TH>Rating</th>
            <!-- Checks database and displays items with the specified category-->
          <c:forEach items="${db}" var="current">
            <tr>
                <c:if test="${current.category=='Drama'}">
                    <td>  <img src=<c:out value="${current.getImageURL()}"/> alt="showimage" height = 200 width = 132></td>
                    <td  class="categoryTitle"><a href="categories?itemCode=<c:out value="${current.itemCode}" />"><c:out value="${current.itemName}" /></a></td>
                    <td><c:out value="${current.rating}"/>/5 &#9733</td>
                </c:if>  
            </tr>
          </c:forEach>
        </table>


        </div>
        </div>
    <%@include file="footer.jsp" %>
    </body>
</html>
