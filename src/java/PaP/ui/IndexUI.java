package PaP.ui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import PaP.PaPException;
import PaP.control.LoginControl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import PaP.model.RegisteredUser;
import java.io.PrintWriter;


/**
 *
 * @author sahar
 */
@WebServlet("/Index")
public class IndexUI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Set response content type
      response.setContentType("text/html");

      // Actual logic goes here.
        PrintWriter out = response.getWriter();
      out.println("<h1> Salam, Kar mikone</h1>");
    }
}
