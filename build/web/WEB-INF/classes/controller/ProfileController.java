/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dbUtil.DbConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Item;
import dbUtil.ItemDB;
import dbUtil.ProfileDB;
import model.ItemRating;
import model.User;
import dbUtil.UserDB;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.UserProfile;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author Jachin
 */
public class ProfileController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(); 
        ItemDB itemDB = new ItemDB();
        UserDB userDB = new UserDB();
        User user = (User) session.getAttribute("theUser");
        String url ="/myShows.jsp";
        //gets action parameter form request
        String action =""; 
           
        try {
            action = ESAPI.validator().getValidInput("action", request.getParameter("action"),"SafeString", 200, false);
        } catch (ValidationException | IntrusionException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            action="";
        }
           
        if(action.equals("save")){
         //if no user in session redirect to login screen
           if(user == null){
               String message = "You must be logged in to do that.";
               request.setAttribute("message", message);
               url = "/login.jsp";
            }
           //if user in session
           else{
               //get item javabean from session
               Item item = (Item) session.getAttribute("item");
               //new itemRating javabean
               ItemRating rating = new ItemRating();
               
               //sets item in itemRating and sets madeIt and rating to 0 by default
               rating.setItem(item);
               rating.setMadeIt(false);
               rating.setRating(0);
               rating.setUserID(user.getUserID());
               //add item rating to database
               ProfileDB.addItemRating(rating);
               //get profile from database
               UserProfile profile = ProfileDB.getProfile(user);
               session.setAttribute("currentProfile", profile);
               //redirect to profile
               url = "/myShows.jsp";

           }
           }
           
        if(action.equals("updateProfile")){
            //if no user in session redirect to login screen
           if(user == null){
               String message = "You must be logged in to do that.";
               request.setAttribute("message", message);
               url = "/login.jsp";
            }
           else{
               String values = ""; 
               String check = "";
               try{
               values = ESAPI.validator().getValidInput("action", request.getParameter("itemList"),"SafeString", 200, false);
                } catch (ValidationException | IntrusionException ex) {
                     Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                     values="";
                }
               //gets hidden field in order to gauruntee user clicks on button on profile to update
               try{
               check = ESAPI.validator().getValidInput("action", request.getParameter("checkItemCode"),"SafeString", 200, false);
                } catch (ValidationException | IntrusionException ex) {
                     Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                     check="";
                }
               boolean foundItem = false;
               //gets profile from session, if none exits, create new one
               UserProfile profile =  (UserProfile) session.getAttribute("currentProfile");
                if(profile == null){
                    profile = new UserProfile();
                    session.setAttribute("currentProfile", profile);
                }
                
                //searches user profile for item 
                for(ItemRating current : profile.getItemRatings()) {
                   //item is found if item is in profile and check is equal to item code
                   if(String.valueOf(current.getItem().getItemCode()).equals(values) && String.valueOf(current.getItem().getItemCode()).equals(check)){
                       foundItem = true;
                       request.setAttribute("theItem", current);
                   }                   
                }
                
                //if item is in profile, go to feedbacl
                if(foundItem == true){
                    url = "/feedback.jsp";
                }
                //if not go back to profile
                else{
                     url = "/myShows.jsp";
                }   
           }  
           }
          
        if(action.equals("updateRating")){
         //if no user in session get one form database and add to session
           if(user == null){
               String message = "You must be logged in to do that.";
               request.setAttribute("message", message);
               url = "/login.jsp";
            }
           else{
               String itemList = "";
               String userRating = "";       
               
               try{
               itemList = ESAPI.validator().getValidInput("action", request.getParameter("itemList"),"SafeString", 200, false);
               userRating = ESAPI.validator().getValidInput("action", request.getParameter(itemList),"SafeString", 200, false);
                } catch (ValidationException | IntrusionException ex) {
                     Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                     itemList="";
                     userRating="";
                }
               
               
               int userRatingInt = Integer.parseInt(userRating);
               
               //checks to see if "Watched It" checkbox is checked or not
               boolean watched = request.getParameter("watched") !=null;
               boolean foundItem = false;
               
               //rating can only be 0-5
            if(userRatingInt == 0||userRatingInt==1||userRatingInt==2 || userRatingInt==3 || userRatingInt==4 || userRatingInt ==5){
               
                UserProfile profile =  (UserProfile) session.getAttribute("currentProfile");
                 if(profile == null){
                     profile = new UserProfile();
                     session.setAttribute("currentProfile", profile);
                 }

                 //checks to see if item is in user profile
                 for(ItemRating current : profile.getItemRatings()) {
                    if(String.valueOf(current.getItem().getItemCode()).equals(itemList)){
                        foundItem = true;
                        //if current rating is different from new rating, change rating
                        if(current.getRating() > userRatingInt || current.getRating() < userRatingInt ){
                        current.setRating(userRatingInt);
                        ProfileDB.updateRating(current);
                        }

                        //if current made it is different from new watched it, change watched it
                        if(watched != current.getMadeIt()){
                         if(watched == true){
                             current.setMadeIt(true);
                             //updata database
                             ProfileDB.updateMadeIt(current);
                         }
                         else{
                            current.setMadeIt(false); 
                            //update dayabase
                            ProfileDB.updateMadeIt(current);
                         }
                        }
                    }  
                 }
               }
               
               url = "/myShows.jsp";
            }
           }
           
        if(action.equals("deleteItem")){
         //if no user in session redirect to login screen
           if(user == null){
               String message = "You must be logged in to do that.";
               request.setAttribute("message", message);
               url = "/login.jsp";

            }
           else{
               
               String values = ""; 
               String check = "";
               
               try{
               values = ESAPI.validator().getValidInput("action", request.getParameter("itemList"),"SafeString", 200, false);
               //gets hidden field in order to gauruntee user clicks on button on profile to update
               check = ESAPI.validator().getValidInput("action", request.getParameter("checkItemCode"),"SafeString", 200, false);
                } catch (ValidationException | IntrusionException ex) {
                     Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                     values="";
                     check="";
                }
               
               
               boolean foundItem = false;
               
               UserProfile profile =  (UserProfile) session.getAttribute("currentProfile");
                if(profile == null){
                    profile = new UserProfile();
                    session.setAttribute("currentProfile", profile);
                }
                
                //checks to see if current item is in profile, if it is remove item from profile. 
                for(ItemRating current : profile.getItemRatings()) {
                   if(String.valueOf(current.getItem().getItemCode()).equals(values) && String.valueOf(current.getItem().getItemCode()).equals(check)){
                        foundItem = true;
                        profile.removeItem(current);
                        ProfileDB.deleteItem(current);
                        session.setAttribute("currentProfile", profile);
                        break;
                   }  
                }               
                     url = "/myShows.jsp";   
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
