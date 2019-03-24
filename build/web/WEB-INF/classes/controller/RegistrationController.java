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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import model.UserProfile;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author Jachin
 */
public class RegistrationController extends HttpServlet {

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
        
        String action="";
                try {
                    action = ESAPI.validator().getValidInput("replace ME with validation context", request.getParameter("action"),"SafeString", 200, false);
                } catch (ValidationException | IntrusionException ex) {
                    Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                    action="";
                }
        HttpSession session = request.getSession();
        String url = "/login.jsp";
        
        //if user chooses to sign up
        if(action.equals("signup")){
            
            boolean validated=true;
            String username ="";
            String password ="";
            String email ="";
            String firstName ="";
            String lastName ="";
            String addressField1 ="";
            String country ="";
            String city ="";
            String state ="";
            String zipCode ="";
            String securityQ1 ="";
            //get and validate all parameters
            try{
            username =ESAPI.validator().getValidInput("username", request.getParameter("username"),"Database", 200, false);
            password =ESAPI.validator().getValidInput("password", request.getParameter("password"),"Database", 200, false);
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
            //if parameters are validated, continue to sign up
            if(validated==true){
                //checks to see if username taken. if taken, redirect back to register page
                if(UserDB.exists(username)){
                    String message = "Username already taken. Try a different one.";
                    request.setAttribute("message", message);
                    url = "/register.jsp";
                }
                //if username not taken
                else{
                 User user = new User();
                 user.setUsername(username);
                 //get and set salt for user
                 String salt = PasswordUtil.getSalt();
                 user.setSalt(salt);
                 String saltPass = password + salt;
                 String saltHashPass = "";
                 //send given password+salt to hash function
                    try {
                        saltHashPass = PasswordUtil.hashPassword(saltPass);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                  System.out.println(saltHashPass);
                  ///store salted+hash password
                 user.setPassword(saltHashPass);
                 user.setFirstName(firstName);
                 user.setLastName(lastName);
                 user.setAddressField1(addressField1);
                 user.setCountry(country);
                 user.setCity(city);
                 user.setEmail(email);
                 user.setState(state);
                 user.setZipCode(zipCode);
                 user.setSecurityQ1(securityQ1);          
                 UserDB.addUser(user);
                 user = UserDB.getUser(username);
                 if(user.getIsAdmin()==true){
                     user.setAdmin(true);
                     session.setAttribute("db", ItemDB.getAllItems());
                     session.setAttribute("users", UserDB.getAllUsers());
                 }
                 //set user in session
                 session.setAttribute("theUser", user);
                 String message = "Successfully registered account.";
                 request.setAttribute("message", message);
                 url = "/myShows.jsp";
                }
            }
            //if validation fails, go back to signup page
            else{
                url="/register.jsp";
                String message = "Invalid data. Try again.";
                request.setAttribute("message", message);
            }
        }
        
        //if user chooses to login
        if(action.equals("login")){
            String username="";
            String password="";
            boolean validated=true;
            //validate parameters
            try{
            username =ESAPI.validator().getValidInput("username", request.getParameter("username"),"Database", 200, false);
            password =ESAPI.validator().getValidInput("password", request.getParameter("password"),"Database", 200, false);
            } catch (ValidationException | IntrusionException ex) {
                    Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                    validated = false;
                }
            //if parameters validate
            if(validated==true){
                //if username exists in database, get salt and add to given password. Hash to see if the same.
                if(UserDB.exists(username)){
                  User user = UserDB.getUser(username);
                  String salt = user.getSalt();
                  String saltPass = password + salt;
                  String saltHashPass = "";
                    try {
                       saltHashPass = PasswordUtil.hashPassword(saltPass);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //if given password same as stored, sign in.
                  if(user.getPassword().equals(saltHashPass)){
                    String message = "Successfully logged in!";
                    request.setAttribute("message", message);
                    url = "/myShows.jsp";
                    session.setAttribute("theUser", user);
                    if(user.getIsAdmin()==true){
                     user.setAdmin(true);
                     session.setAttribute("db", ItemDB.getAllItems());
                     session.setAttribute("users", UserDB.getAllUsers());
                    }
                    //get user profile (stored shows) and put in session
                    UserProfile profile = ProfileDB.getProfile(user);
                    session.setAttribute("currentProfile", profile);
                  }
                  //if password is different
                  else{
                      String message = "Username or Password incorrect.";
                      request.setAttribute("message", message);
                      url = "/login.jsp";
                  }
            }
                //if username does not exist
            else{
                String message = "Username or password incorrect.";
                request.setAttribute("message", message);
                url = "/login.jsp";
            }
        }
            //if given data is not valid
        else{
                url="/login.jsp";
                String message = "Invalid data. Try again.";
                request.setAttribute("message", message);
                
            }
        }
        // if user chooses to logout
        if(action.equals("logout")){
            //remove user from session and invalidate session
            session.removeAttribute("theUser");
            session.invalidate(); 
            String message = "Successfully logged out.";
            request.setAttribute("message", message);
            url = "/login.jsp";
        }
        
        //if user chooses to edit their details
        if(action.equals("edit")){
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
                       
                User user = (User) session.getAttribute("theUser");
                boolean updated = false;

                 //if no user in session 
                   if(user == null){
                       String message = "You must be logged in to do that.";
                       request.setAttribute("message", message);
                       url = "/login.jsp";

                    }
                   //if user in session, change data in database to given data
                   else{
                       url = "/editprofile.jsp";
                       user.setFirstName(firstName);
                       user.setLastName(lastName);
                       user.setAddressField1(addressField1);
                       user.setCity(city);
                       user.setState(state);
                       user.setZipCode(zipCode);
                       user.setSecurityQ1(securityQ1);
                       user.setCountry(country);
                       user.setEmail(email);
                       //user UserDB class to updata user info
                       updated = UserDB.updateUser(user);
                       if(updated==true){
                       String message = "Successfully updated profile";
                       request.setAttribute("message", message);
                       }
                       else{
                       String message = "Could not update profile";
                       request.setAttribute("message", message);
                       }
                   }
                }
            else{
                
                url="/editprofile.jsp";
                String message = "Invalid data. Try again.";
                request.setAttribute("message", message);
                
            }
        }
        //if user clicks "forgot password"
        if(action.equals("forgot")){
            String username="";
            String answer="";
            boolean validated=true;
            
            try{
            username =ESAPI.validator().getValidInput("username", request.getParameter("username"),"Database", 200, false);
            answer =ESAPI.validator().getValidInput("answer", request.getParameter("answer"),"Database", 200, false);
            } catch (ValidationException | IntrusionException ex) {
                    Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                    validated = false;
                }
            
            if(validated==true){
                
                if(UserDB.exists(username)){
                  User user = UserDB.getUser(username);
                  //if username and answer correct, go to page where they can update password
                  if(user.getSecurityQ1().equals(answer)){
                      session.setAttribute("userForgot", user);
                      url = "/updatePass.jsp";
                    }
                  else{
                    String message = "Username or Security answer incorrect.";
                    request.setAttribute("message", message);
                    url = "/login.jsp";
                    }
                }
                else{
                    String message = "Username or Security answer incorrect.";
                    request.setAttribute("message", message);
                    url = "/login.jsp";
                }
                }
            else{
                url="/login.jsp";
                String message = "Invalid data. Try again.";
                request.setAttribute("message", message); 
            }
        }
        
        //if user chooses to update their forgotten password 
        if(action.equals("updatePass")){
            String password="";
            boolean validated=true;
            
            try {
                password =ESAPI.validator().getValidInput("password", request.getParameter("password"),"Database", 200, false);
            } catch (ValidationException | IntrusionException ex) {
                Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                validated=false;
            }
            
            if(validated==true){
                User user = (User) session.getAttribute("userForgot");

                if(user == null){
                    url = "/login.jsp";
                }
                //get new salt and add to password, hash, then add to database
                else{
                String salt = PasswordUtil.getSalt();
                user.setSalt(salt);
                String saltPass = password + salt;
                String saltHashPass = "";
                   try {
                       saltHashPass = PasswordUtil.hashPassword(saltPass);
                   } catch (NoSuchAlgorithmException ex) {
                       Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 System.out.println(saltHashPass);
                 user.setPassword(saltHashPass);

                   UserDB.updateUserPassword(user);
                   String message = "Password updated.";
                   request.setAttribute("message", message);
                   url = "/login.jsp";

                }
            }
            else{
                url="/login.jsp";
                String message = "Invalid data. Try again.";
                request.setAttribute("message", message); 
                
            }
        }
        
        
         //if user chooses to update their current password
        if(action.equals("updateCurrentPass")){
            String currentPassword="";
            String newPassword="";
            boolean validated=true;
            
            try {
                currentPassword =ESAPI.validator().getValidInput("currentpassword", request.getParameter("currentPassword"),"Database", 200, false);
                newPassword =ESAPI.validator().getValidInput("newpassword", request.getParameter("newPassword"),"Database", 200, false);
            } catch (ValidationException | IntrusionException ex) {
                Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                validated=false;
            }
            
            if(validated==true){
                User user = (User) session.getAttribute("theUser");

                if(user == null){
                    url = "/login.jsp";
                }
                
                //get new salt and add to password, hash, then add to database
                else{
                    
                  String salt = user.getSalt();
                  String saltPass = currentPassword + salt;
                  String saltHashPass = "";
                    try {
                       saltHashPass = PasswordUtil.hashPassword(saltPass);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                if(user.getPassword().equals(saltHashPass)){
                salt = PasswordUtil.getSalt();
                user.setSalt(salt);
                saltPass = newPassword + salt;
                saltHashPass = "";
                   try {
                       saltHashPass = PasswordUtil.hashPassword(saltPass);
                   } catch (NoSuchAlgorithmException ex) {
                       Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                   }
                 System.out.println(saltHashPass);
                 user.setPassword(saltHashPass);

                   UserDB.updateUserPassword(user);
                   String message = "Password updated.";
                   request.setAttribute("message", message);
                   url = "/updateCurrentPass.jsp";

                }
                else{
                    String message = "Password incorrect.";
                    request.setAttribute("message", message);
                    url = "/updateCurrentPass.jsp";
                    
                }
            }
            }
            else{
                url="/updateCurrentPass.jsp";
                String message = "Invalid data. Try again.";
                request.setAttribute("message", message); 
                
            }
        }
                getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
