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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Item;
import dbUtil.ItemDB;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author Jachin
 */
public class CatalogController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     * 
     */
    
        public void init() throws ServletException {
        super.init();
        new DbConnection();
        
        }
        
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
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
        

        String itemCode ="";
        
            try {
                itemCode = ESAPI.validator().getValidInput("itemCode", request.getParameter("itemCode"),"SafeString", 200, false);
            } catch (ValidationException | IntrusionException ex) {
                Logger.getLogger(CatalogController.class.getName()).log(Level.SEVERE, null, ex);
                itemCode="";
            }
                
        String url = "";
        
        //if itemCode does not exist, or is empty, load the catalog
        if(itemCode == null || itemCode.equals("")){

            request.setAttribute("db", ItemDB.getAllItems());
            url = "/categories.jsp";
            getServletContext().getRequestDispatcher(url).forward(request,response);                 
        }
        
        //if there is an itemCode
        else{
            //checks the itemCode parameter and loops through database to see if it is valid   
            for (Item items : ItemDB.getAllItems()) {

                if(String.valueOf(items.getItemCode()).equals(itemCode)){
                  HttpSession session = request.getSession();
                  session.setAttribute("item", items);
                  url = "/item.jsp";
                  //if itemCode is valid forward to item with that itemCode
                  getServletContext().getRequestDispatcher(url).forward(request,response);   
                }
            }

             //if itemCode is not found, load the catalog
             request.setAttribute("db", ItemDB.getAllItems());
             url = "/categories.jsp";
             getServletContext().getRequestDispatcher(url).forward(request,response);     
       
        }
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
        

        //creaates Item arraylist to hold catalog of a specific category
        ArrayList<Item>categoryItems = new ArrayList<>();
        //looks for parameter category
        String category ="";
            try {
                category = ESAPI.validator().getValidInput("category", request.getParameter("category"),"SafeString", 200, false);
            } catch (ValidationException | IntrusionException ex) {
                Logger.getLogger(CatalogController.class.getName()).log(Level.SEVERE, null, ex);
                category ="";
            }
                
        String url = "";
        
        //if category does not exist or is empty, load catalog normally
        if(category == null || category.equals("")){

            request.setAttribute("db", ItemDB.getAllItems());
            url = "/categories.jsp";
            getServletContext().getRequestDispatcher(url).forward(request,response);                 
       
        }
        
        //if parameter catalog exists and is equal to Comedy or Drama, load a page that lists the catlog specific category specified
        if(category.equals("Comedy") || category.equals("Drama")){
              
           //loops through database and if an item has a category matching the one specifies, add to an arraylist
            for (Item items : ItemDB.getAllItems()) {
            if(items.getCategory().equals(category)){
              categoryItems.add(items);
            }
            }  
            
             //fowards to specificCategory page, along with an arraylist containing the items that match the category
             request.setAttribute("items", categoryItems);
             url = "/specificCategory.jsp";
             getServletContext().getRequestDispatcher(url).forward(request,response);              
        }
        
        //else reload catalog normally
        else{
         request.setAttribute("db", ItemDB.getAllItems());
         url = "/categories.jsp";
         getServletContext().getRequestDispatcher(url).forward(request,response);  
            
        }
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
