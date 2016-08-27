package edu.uga.dawgtrades.ui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.*;
import edu.uga.dawgtrades.model.impl.*;
import edu.uga.dawgtrades.control.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sahar
 */
public class ResetPasswordUI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get current session
        HttpSession session = request.getSession(true);
        request.setAttribute("baseContext", session.getServletContext().getContextPath());
        LoginControl ctrl = new LoginControl();
        if(ctrl.checkIsLoggedIn(session))
        {
            // Already logged in, redirect.
            response.sendRedirect("/");
            return;
        }
        request.getRequestDispatcher("/passReset.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get current session
        HttpSession session = request.getSession(true);
        request.setAttribute("baseContext", session.getServletContext().getContextPath());
        LoginControl ctrl = new LoginControl();
        ResetPasswordControl pwCtrl = new ResetPasswordControl();
        if(ctrl.checkIsLoggedIn(session))
        {
            // Already logged in, redirect.
            response.sendRedirect("/");
            return;
        }

        String username = request.getParameter("username");

        if (pwCtrl.sendReset(username)) {
            response.sendRedirect("/login?message=4b1b068d61f5815e55092a8af8fac6ed71936b42");
        } else {
            if(pwCtrl.hasError()) {
                request.setAttribute("error", pwCtrl.getError());
                request.getRequestDispatcher("/passReset.ftl").forward(request, response);
            } else {
                request.setAttribute("error", "Invalid username given.");
                request.getRequestDispatcher("/passReset.ftl").forward(request, response);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Login UI";
    }// </editor-fold>

}
