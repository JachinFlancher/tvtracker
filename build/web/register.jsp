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
             <c:when test="${theUser == null}"> 
            <div id="breadcrumb">Home > Register</div>
            <div id="homeInfo">
<h1>Register</h1>
<p><c:out value="${message}"/></p>
    <form action="/myWebsite/register?action=signup" method="post">
        <input type="hidden" name="action" value="add">        
        <label class="pad_top">Username:</label><br>
        <input type="text" name="username"><br>
        <label class="pad_top">Password:</label><br>
        <input type="password" name="password"><br>
        <label class="pad_top">Email:</label><br>
        <input type="email" name="email"><br>
        <label class="pad_top">First Name</label><br>
        <input type="text" name="firstName" required><br>
        <label class="pad_top">Last Name</label><br>
        <input type="text" name="lastName"  required><br>
        <label class="pad_top">Address Field 1</label><br>
        <input type="text" name="addressField1"  required><br>
        <label class="pad_top">Country</label><br>
        <input type="text" name="country"  required><br>     
        <label class="pad_top">City</label><br>
        <input type="text" name="city" required><br>
        <label class="pad_top">State</label><br>
        <input type="text" name="state" required><br>
        <label class="pad_top">Zip</label><br>
        <input type="text" name="zipCode"  required><br>
        <label class="pad_top">Security Question: What is your favorite food?</label><br>
        <input type="text" name="securityQ1" required><br>
        <label>&nbsp;</label>
        <input type="submit" value="Register" class="margin_left">
    </form>
        </div>
                 </c:when>
             <c:otherwise>
                <p>You are already signed in.</p> 
             </c:otherwise>
            </c:choose>
        </div>
<%@include file="footer.jsp" %>
    </body>
</html>