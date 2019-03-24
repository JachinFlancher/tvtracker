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
            <div id="breadcrumb">Home > About</div>
            <div id="homeInfo">
<h1>Update Profile</h1>
<p><c:out value="${message}"/></p>
    <form action="/myWebsite/register?action=edit" method="post">
        <input type="hidden" name="action" value="add"> 
        <label class="pad_top">Email</label><br>
        <input type="email" name="email" value="<c:out value="${theUser.email}"/>" required><br>
        <label class="pad_top">First Name</label><br>
        <input type="text" name="firstName" value="<c:out value="${theUser.firstName}"/>" required><br>
        <label class="pad_top">Last Name</label><br>
        <input type="text" name="lastName" value="<c:out value="${theUser.lastName}"/>" required><br>
        <label class="pad_top">Address</label><br>
        <input type="text" name="addressField1" value="<c:out value="${theUser.addressField1}"/>" required><br>
        <label class="pad_top">Country</label><br>
        <input type="text" name="country" value="<c:out value="${theUser.country}"/>" required><br>
        <label class="pad_top">City</label><br>
        <input type="text" name="city" value="<c:out value="${theUser.city}"/>" required><br>
        <label class="pad_top">State</label><br>
        <input type="text" name="state" value="<c:out value="${theUser.state}"/>" required><br>
        <label class="pad_top">Zip</label><br>
        <input type="text" name="zipCode" value="<c:out value="${theUser.zipCode}"/>" required><br>
        <label class="pad_top">Security Question: What is your favorite food?</label><br>
        <input type="text" name="securityQ1" value="<c:out value="${theUser.securityQ1}"/>" required><br>
        <label>&nbsp;</label>
        <input type="submit" value="Submit Changes" class="margin_left">
    </form>
        </div>
        </div>
        
<%@include file="footer.jsp" %>
    </body>
</html>