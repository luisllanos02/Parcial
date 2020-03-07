package servlets;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AgregarServlet extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try{
            PrintWriter out = response.getWriter();
            String dni = request.getParameter("dni");
            String multa = request.getParameter("multa");
            double monto = Double.parseDouble(request.getParameter("monto"));
           
           Class.forName("com.mysql.cj.jdbc.Driver");
           String url = "jdbc:mysql://localhost/Moqzilla?user=root&password=mysqladmin";
           Connection connect = DriverManager.getConnection(url);           
           Statement statement = connect.createStatement();       
           String query = "INSERT INTO papeleta VALUES (?,?,?)";
           PreparedStatement ps = connect.prepareStatement(query);   
           ps.setString(1, dni);
           ps.setString(2,multa);
           ps.setDouble(3, monto);
           ps.executeUpdate();
           
           JsonObject gson = new JsonObject();
           gson.addProperty("mensaje", "Papeleta agregada");
           out.print(gson.toString());
        }catch(Exception e){
            
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