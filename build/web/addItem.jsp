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
            <div id="breadcrumb">Home > Add Show</div>
            <div id="homeInfo">
<h1>Add Items</h1>
<p><c:out value="${message}"/></p>
    <form action="/myWebsite/admin?action=addItem" method="post">
        <input type="hidden" name="action" value="add">        
        <label class="pad_top">Show Name</label><br>
        <input type="text" name="name"  required><br>
        <label class="pad_top">Show Category</label><br>
        <select name="category">
            <option value="Comedy">Comedy</option>
            <option value="Drama">Drama</option>
        </select><br>
        <label class="pad_top">Show Description</label><br>
        <textarea rows="4" cols="50" name="description" required></textarea><br>

        <label>&nbsp;</label>
        <input type="submit" value="Add Item" class="margin_left">
    </form>
        </div>
        
                         
             </c:when>
             <c:otherwise>
                <p>You can't access this page.</p> 
             </c:otherwise>
            </c:choose>
                </div>
<%@include file="footer.jsp" %>
    </body>
</html>