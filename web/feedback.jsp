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
            <div id="breadcrumb">Home > Categories > Item</div>
            <div id= "itemInfo">
                <img src=<c:out value="${theItem.getItem().getImageURL()}"/> alt="showimage" width="333" height="500">
            <h1><c:out value="${theItem.getItem().itemName}"/></h1>
            <h2><c:out value="${theItem.getItem().category}"/></h2>
            <div id="selectCategory">
                <form action="profile?action=updateRating" name="registration" method="post">
                    <label for="category" class="labels"><h2>Rating:</h2></label>
			<select name=<c:out value="${theItem.getItem().itemCode}"/>>  
				  
                                <option value="0">0</option> 
				<option value="1">1</option>  
				<option value="2">2</option>  
                                <option value="3">3</option>  
                                <option value="4">4</option>  
                                <option value="5">5</option>    
			</select>
                        <input type="hidden" name="itemList" value=<c:out value="${theItem.getItem().itemCode}"/>>
                        <input type="checkbox" name="watched" value="watched" checked /><span>Watched It</span>
                        <input type="submit" name="submit" value="Submit Rating" />
                </form>
            </div>
            </div>
            <div id="itemDescription">
                <p>
                    <c:out value="${theItem.getItem().description}"/>
                </p>
            </div> 
           

            
        </div>
<%@include file="footer.jsp" %>
    </body>
</html>
