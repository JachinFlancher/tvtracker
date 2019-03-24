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
            <div id="breadcrumb">Home > Categories > <c:out value="${item.category}"/> > <c:out value="${item.itemName}"/> <br> <a href="categories">Back to Categories</a></div>
            <div id= "itemInfo">
            <img src="<c:out value="${item.getImageURL()}"/>" alt="showimage" height = 500 width = 333>
            <h1><c:out value="${item.itemName}"/></h1>
            <h2><c:out value="${item.category}"/></h2>
            <h2>Avg. Rating: <c:out value="${item.rating}"/> / 5</h2>
            <form id="button1" action="profile?action=save" name="save" method="post">
            <input class="myButton deleteButton" type="submit" value="Save It" name="save" />
            </form>
            <form id="button2" action="profile?action=updateProfile&itemList=<c:out value="${item.itemCode}"/>" name="rate" method="post">
            <input class="myButton deleteButton" type="submit" value="Rate It" name="rate" />
            </form>
            </div>
            <div id="itemDescription">
                <p>
                    <c:out value="${item.description}"/>
                </p>
            </div>
        </div>
<%@include file="footer.jsp" %>
    </body>
</html>
