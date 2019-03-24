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
            
            <div id="breadcrumb">Home > Edit User</div>
            <div id="homeInfo">
<h1>Update User Details</h1>
<p><c:out value="${message}"/></p>
    <form action="/myWebsite/admin?action=updateUser" method="post">
        <input type="hidden" name="action" value="add">        
        <label class="pad_top">Email</label><br>
        <input type="email" name="email" value="<c:out value="${editUser.email}"/>" required><br>
        <label class="pad_top">First Name</label><br>
        <input type="text" name="firstName" value="<c:out value="${editUser.firstName}"/>" required><br>
        <label class="pad_top">Last Name</label><br>
        <input type="text" name="lastName" value="<c:out value="${editUser.lastName}"/>" required><br>
        <label class="pad_top">Address</label><br>
        <input type="text" name="addressField1" value="<c:out value="${editUser.addressField1}"/>" required><br>
        <label class="pad_top">Country</label><br>
        <input type="text" name="country" value="<c:out value="${editUser.country}"/>" required><br>
        <label class="pad_top">City</label><br>
        <input type="text" name="city" value="<c:out value="${editUser.city}"/>" required><br>
        <label class="pad_top">State</label><br>
        <input type="text" name="state" value="<c:out value="${editUser.state}"/>" required><br>
        <label class="pad_top">Zip</label><br>
        <input type="text" name="zipCode" value="<c:out value="${editUser.zipCode}"/>" required><br>
        <label class="pad_top">Security Question: What is your favorite food?</label><br>
        <input type="text" name="securityQ1" value="<c:out value="${editUser.securityQ1}"/>" required><br>
        <label>&nbsp;</label>
        <input type="submit" value="Submit Changes" class="margin_left">
    </form>
      <form action="/myWebsite/admin?action=resetPass" method="post">
                  <input type="submit" value="Reset Password" class="margin_left">
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