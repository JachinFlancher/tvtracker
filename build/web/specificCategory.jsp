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
            <div id="breadcrumb">Home > Categories  <br> <a href="categories">Back to All Categories</a></div>
        <div id="categories">
     <h1>Category: <c:out value="${items.get(0).category}"/></h1>            
           
                <div id="selectCategory">
                <form action="categories" name="registration" method="post">
			<p>Select a different category:</p>  
			<select name="category">  
				<option selected="" value="">(Select a category)</option>  
				<option value="Comedy">Comedy</option>  
				<option value="Drama">Drama</option>  
				<input type="submit" name="submit" value="Submit" />  
			</select> 
                </form>
            </div>
     
     
    <table>
      <TH><c:out value="${items.get(0).category}"/></th>
      <c:forEach items="${items}" var="current">
        <tr>
                <td><a href="categories?itemCode=<c:out value="${current.itemCode}" />"><c:out value="${current.itemName}" /></a><td>
        </tr>
      </c:forEach>
    </table>
 

        </div>
        </div>
<%@include file="footer.jsp" %>
    </body>
</html>
