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
             <c:when test="${theUser != null}"> 
             <div id="breadcrumb">Home > Update Password</div>
            <div id="homeInfo">
           
<h1>Update Password</h1>
<p><c:out value="${message}"/></p>
    <form action="/myWebsite/register?action=updateCurrentPass" method="post">
        <input type="hidden" name="action" value="add">
        <label class="pad_top">Current Password:</label><br>
        <input type="password" name="currentPassword" required><br>
        <label>&nbsp;</label>
        <label class="pad_top">New Password:</label><br>
        <input type="password" name="newPassword" required><br>
        <label>&nbsp;</label>
        <input type="submit" value="Submit" class="margin_left">
    </form>
        </div>
             </c:when>
             <c:otherwise>
                <p>You must be logged in to do that.</p> 
             </c:otherwise>
            </c:choose>
        </div>
<%@include file="footer.jsp" %>
    </body>
</html>