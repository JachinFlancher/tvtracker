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
             <div id="breadcrumb">Home > Register</div>
               <div id="homeInfo">
                <h1>User Profile</h1>
            <table>
               <tr>
                   <td class="profileBold">
                        Email:
                    </td>
                    <td>
                        <c:out value="${theUser.email}"/>
                    </td>
                </tr>
                <tr>
                    <td class="profileBold">
                        First Name:
                    </td>
                    <td>
                        <c:out value="${theUser.firstName}"/>
                    </td>
                </tr>
                <tr>
                    <td class="profileBold">
                        Last Name:
                    </td>
                    <td>
                       <c:out value="${theUser.lastName}"/>
                    </td>
                </tr>
                <tr>
                    <td class="profileBold">
                        Address:
                    </td>
                    <td>
                        <c:out value="${theUser.addressField1}"/>
                    </td>
                </tr>
                 <tr>
                   <td class="profileBold">
                        City:
                    </td>
                    <td>
                        <c:out value="${theUser.city}"/>
                    </td>
                </tr>
                <tr>
                <tr>
                   <td class="profileBold">
                        Country:
                    </td>
                    <td>
                        <c:out value="${theUser.country}"/>
                    </td>
                </tr>
                    <td class="profileBold">
                        State:
                    </td>
                    <td>
                        <c:out value="${theUser.state}"/>
                    </td>
                </tr>
                <tr>
                    <td class="profileBold">
                        Zip Code:
                    </td>
                    <td>
                        <c:out value="${theUser.zipCode}"/>
                    </td>
                </tr>
            </table>
                    
    <form action="/myWebsite/editprofile.jsp" method="post">
        <input type="submit" value="Edit Profile" class="margin_left">
    </form>
    <form action="/myWebsite/updateCurrentPass.jsp" method="post">
        <input type="submit" value="Update Password" class="margin_left">
    </form>                
        </div>
        </div>
<%@include file="footer.jsp" %>
    </body>
</html>