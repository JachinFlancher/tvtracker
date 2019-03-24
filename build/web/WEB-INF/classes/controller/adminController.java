/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dbUtil.DbConnection;
import dbUtil.ItemDB;
import dbUtil.ProfileDB;
import dbUtil.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Item;
import model.User;
import model.UserProfile;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author Jachin
 */
@WebServlet(name = "adminController", urlPatterns = {"/admin"})
public class adminController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
            public void init() throws ServletException {
        super.init();
        new DbConnection();
        
        }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        //gets the user from the session
        User theUser = (User) session.getAttribute("theUser");
        String action="";
        String itemCode="";
        try {
             action =ESAPI.validator().getValidInput("action", request.getParameter("action"),"SafeString", 200, false);
             } catch (ValidationException | IntrusionException ex) {
                Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
                action="";
                }
         try {
              itemCode =ESAPI.validator().getValidInput("itemCode", request.getParameter("itemCode"),"SafeString", 200, false);
                } catch (ValidationException | IntrusionException ex) {
                 Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
                 itemCode="";
                }
        
        String url = "/login.jsp";
        
        //if no user in session
        if(theUser == null){
            action="";
            url="/login.jsp";
                 getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
            
        }
        //if user in session is not admin
        if(theUser.isAdmin()==false){
            action="";
            url="/myShows.jsp";
                 getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        }
        
        if(action.equals("view")){
           
           url = "/editItems.jsp"; 
        }
        
        if(action.equals("viewUsers")){
            
           url = "/viewUsers.jsp"; 
        }
        
        //if user chooses to add item to database, validate data, add item and uodate session
        if(action.equals("addItem")){
            
            Item item = new Item();
            String name ="";
            String category = "";
            String description = "";
            boolean validated=true;

            
            try {
                name =ESAPI.validator().getValidInput("name", request.getParameter("name"),"SafeString", 200, false);
                category  = ESAPI.validator().getValidInput("category", request.getParameter("category"),"SafeString", 200, false);
                description =ESAPI.validator().getValidInput("itemCode", request.getParameter("description"),"Database", 200, false);
            } catch (ValidationException | IntrusionException ex) {
                Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
                    validated=false;
            }
            
            if(validated==true){
                item.setItemName(name);
                item.setCategory(category);
                item.setDescription(description);
                item.setRating("0");
                ItemDB.addItem(item);
                session.setAttribute("db", ItemDB.getAllItems());
                url = "/editItems.jsp";
            }
            else{
                url="/addItem.jsp";
                String message = "Invalid data. Try again.";
                request.setAttribute("message", message);
            }
        }
        
        //if user chooses to edit item, validate data, add item to session
        if(action.equals("editItem")){
            if(itemCode == null || itemCode.equals("")){
                url = "/categories.jsp";               
            }

            //if there is an itemCode
            else{
                //checks the itemCode parameter and loops through database to see if it is valid   
                for (Item items : ItemDB.getAllItems()) {

                    if(String.valueOf(items.getItemCode()).equals(itemCode)){

                      session.setAttribute("editItem", items);
                      url = "/editItem.jsp";
                      break;
                    }
                }
            }
                   
        }
        
        //if user chooses to delete item, validate data, delete item, update session  
        if(action.equals("deleteItem")){
            
            if(itemCode == null || itemCode.equals("")){
                url = "/editItems.jsp";               
            }

            //if there is an itemCode
            else{
                //checks the itemCode parameter and loops through database to see if it is valid
                int itemCodeInt = Integer.parseInt(itemCode);
                Item item = ItemDB.getItem(itemCodeInt);
                ItemDB.deleteItem(item);
                session.setAttribute("db", ItemDB.getAllItems());
                url = "/editItems.jsp";
                String message = "Successfully Deleted item.";
                request.setAttribute("message", message);
            }
                   
        }
        
        //if user chooses to update item info, validate given info, update database
        if(action.equals("updateItem")){
            
            Item item = (Item) session.getAttribute("editItem");
            String name ="";
            String category = "";
            String description = "";
            boolean validated=true;

            
            try {
                name =ESAPI.validator().getValidInput("name", request.getParameter("name"),"Database", 200, false);
                category  = ESAPI.validator().getValidInput("category", request.getParameter("category"),"Database", 200, false);
                description =ESAPI.validator().getValidInput("description", request.getParameter("description"),"Database", 100000, false);
            } catch (ValidationException | IntrusionException ex) {
                Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
                    validated=false;
            }
            
            if(validated==true){
                item.setItemName(name);
                item.setCategory(category);
                item.setDescription(description);
                ItemDB.updateItem(item);
                session.setAttribute("db", ItemDB.getAllItems());
                url="/editItem.jsp";
                String message = "Successfully updated item info.";
                request.setAttribute("message", message);
            }
            else{
                url="/editItem.jsp";
                String message = "Invalid data. Try again.";
                request.setAttribute("message", message);
                
            }
            
            
        }
        
        //if user chooses to update user, validate given info, go to specified user edit page
        if(action.equals("editUser")){
            String username="";
            
            try {
                username =ESAPI.validator().getValidInput("username", request.getParameter("username"),"SafeString", 200, false);
            } catch (ValidationException | IntrusionException ex) {
                Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
                username="";
            }
            
            User user = UserDB.getUser(username);
            if(user != null){
                session.setAttribute("editUser", user);
                url = "/editUser.jsp";   
            }
            else{
                url="/viewUsers.jsp";
                String message = "Could not find user.";
                request.setAttribute("message", message);                
            }
        
        }
        
        //if user chooses to update user, validate info, update to database, update session
        if(action.equals("updateUser")){
            boolean validated=true;

            String email ="";
            String firstName ="";
            String lastName ="";
            String addressField1 ="";
            String country ="";
            String city ="";
            String state ="";
            String zipCode ="";
            String securityQ1 ="";
            try{

            email =ESAPI.validator().getValidInput("email", request.getParameter("email"),"Email", 200, false);
            firstName =ESAPI.validator().getValidInput("firstName", request.getParameter("firstName"),"SafeString", 200, false);
            lastName =ESAPI.validator().getValidInput("lastName", request.getParameter("lastName"),"SafeString", 200, false);
            addressField1 =ESAPI.validator().getValidInput("addressField1", request.getParameter("addressField1"),"Database", 200, false);
            country =ESAPI.validator().getValidInput("country", request.getParameter("country"),"Database", 200, false);
            city =ESAPI.validator().getValidInput("city", request.getParameter("city"),"Database", 200, false);
            state =ESAPI.validator().getValidInput("state", request.getParameter("state"),"Database", 200, false);
            zipCode =ESAPI.validator().getValidInput("zipeCode", request.getParameter("zipCode"),"Database", 200, false);
            securityQ1 =ESAPI.validator().getValidInput("securityQ1", request.getParameter("securityQ1"),"Database", 200, false);
            } catch (ValidationException | IntrusionException ex) {
                    Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                    validated = false;
                }
            
            if(validated==true){
                  User user = (User) session.getAttribute("editUser");


                   url = "/editUser.jsp";
                   user.setFirstName(firstName);
                   user.setLastName(lastName);
                   user.setAddressField1(addressField1);
                   user.setCity(city);
                   user.setState(state);
                   user.setZipCode(zipCode);
                   user.setSecurityQ1(securityQ1);
                   user.setCountry(country);
                   user.setEmail(email);
                   UserDB.updateUser(user);
                   session.setAttribute("users", UserDB.getAllUsers());
                   String message = "Successfully updated profile";
                   request.setAttribute("message", message);
               
            }
            else{
                url="/editUser.jsp";
                String message = "Invalid data. Try again.";
                request.setAttribute("message", message);
            }
        }
        
        //if admin resets user password, get a salt to act as password, salt the new password, hash, set as new password, then display new password to admin to send to user.
        if(action.equals("resetPass")){
            User user = (User) session.getAttribute("editUser");
            String salt1 = PasswordUtil.getSalt();
            String salt2 = PasswordUtil.getSalt();
            user.setSalt(salt2);
            String salt3 = salt1+salt2;
            String saltHashPass = "";
                 //send given password+salt to hash function
                    try {
                        saltHashPass = PasswordUtil.hashPassword(salt3);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
            user.setPassword(saltHashPass);
            UserDB.updateUserPassword(user);
            url="/editUser.jsp";
            String message = "User password reset. New user password: "+salt1;
            request.setAttribute("message", message);
                    
            
        }
        
        
                getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        
    }


    // <editor-fold defaultstate="col{lapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
