
package PaP.ui;

import PaP.PaPException;
import PaP.control.RegisterControl;
import PaP.control.UnitControl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UnitCreateUI extends HttpServlet{

	private boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession(true);
		request.setAttribute("baseContext", session.getServletContext().getContextPath());
		RegisterControl ctrl = new RegisterControl();
		request.getRequestDispatcher("/register.ftl").forward(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession(true);
		request.setAttribute("baseContext", session.getServletContext().getContextPath());
		String code = request.getParameter("code");
		String title = request.getParameter("title");
		String desc = request.getParameter("description");
                
		UnitControl ctrl = new UnitControl();		
		
                request.setAttribute("error","Phone number should be 10 digits. (i.e. 5551234567)");
                request.getRequestDispatcher("/unit.ftl").forward(request,response);
	
            try {
                if(ctrl.attemptToRegister(title,code,desc)){
                    response.sendRedirect("/unitList.ftl");
                }else{
                    request.setAttribute("error","Registration failed: "+ctrl.getError());
                    request.getRequestDispatcher("/register.ftl").forward(request,response);
                }
            } catch (PaPException ex) {
                Logger.getLogger(UnitCreateUI.class.getName()).log(Level.SEVERE, null, ex);
            }	
			
	}
}
