
package com.mycompany.grootp.servlets;

import com.mycompany.grootp.ProductDao;

import com.mycompany.grootp.entities.Product;
import com.mycompany.grootp.helper.FactoryProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
public class ProductOperationServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           try (PrintWriter out = response.getWriter()) {
            
             
                String pname=request.getParameter("pName");
                int page=Integer.parseInt(request.getParameter("page"));
                long date=Integer.parseInt(request.getParameter("datemax"));
                String gender=request.getParameter("gender");
                String plang=request.getParameter("plang");
                String pcolor=request.getParameter("pcolor");
                Part part=request.getPart("pPic");
                String pemail=request.getParameter("email");
                String time=request.getParameter("appt");
                String url=request.getParameter("url");
                String title=request.getParameter("ptitle");
                int cgpa=Integer.parseInt(request.getParameter("pcgpa"));
             
                Product p=new Product();
                
               
                p.setPname(pname);
                p.setPage(cgpa);
                p.setDatemax(date);
                p.setGender(gender);
                p.setPlang(plang);
                p.setPpic(part.getSubmittedFileName());
                p.setEmail(pemail);
                p.setTime(time);
                p.setUrl(url);
                p.setTitle(title);
                p.setCgpa(cgpa);
                
                ProductDao pdao=new ProductDao(FactoryProvider.getFactory());
                pdao.saveProduct(p);
                String path=request.getRealPath("img")+File.separator+"Products"+File.separator+part.getSubmittedFileName();
               
              try {
                FileOutputStream fos=new FileOutputStream(path);
                InputStream is=part.getInputStream();
 
                
                byte []data=new byte[is.available()];
                
                is.read(data);
                
                fos.write(data);
                
                fos.close();
                
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
               HttpSession httpSession=request.getSession();
               httpSession.setAttribute("message","Product is added Successfully !!! ");
               response.sendRedirect("index.jsp");
               
               return;
            
           
            
            
            
        }
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
