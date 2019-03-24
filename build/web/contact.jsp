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
            <div id="breadcrumb">Home > Contact</div>
            <div id="homeInfo">
                <h1>Contact Us</h1>
                <h2>Questions, Comments, or Concerns?</h2>
 <form action="#" name="registration" method="post">  
            <label for="name" class="labels">Name:</label>  
            <input type="text" name="name"/>  <br />
            <label for="email" class="labels">Email:</label>  
            <input type="text" name="email"  />  <br />
            <label for="about" class="labels">About:</label>  
            <textarea name="about" id="desc"></textarea>  <br />
            <input type="submit" name="action" value="Submit" />  

        </form>  
             </div>
        </div>
        
<%@include file="footer.jsp" %>
    </body>
</html>