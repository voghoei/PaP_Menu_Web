
package PaP.ui;

import PaP.PaPException;
import PaP.control.RegisterControl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterUI extends HttpServlet{

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
		String username = request.getParameter("username");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
                String state = request.getParameter("state");
                String address = request.getParameter("address");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String passwordRe = request.getParameter("passwordRe");

		RegisterControl ctrl = new RegisterControl();		
		if(!password.equals(passwordRe)){
			request.setAttribute("error","Passwords don't match.");
			request.getRequestDispatcher("/register.ftl").forward(request,response);
			return;			
		}
		if(phone.length() != 10 || !this.isNumeric(phone)){
			request.setAttribute("error","Phone number should be 10 digits. (i.e. 5551234567)");
			request.getRequestDispatcher("/register.ftl").forward(request,response);
			return;			
		}
		try{
			if(ctrl.attemptToRegister(fname,lname,username,password,state,address,email,phone)){
				response.sendRedirect("/login?message=c3daec042ebd773d1d2e505235f1449c4ff1625f");
			}else{
				request.setAttribute("error","Registration failed: "+ctrl.getError());
				request.getRequestDispatcher("/register.ftl").forward(request,response);
			}
		
		}catch(PaPException e){
			request.setAttribute("error","An exception occured: " + e.getMessage());
			request.getRequestDispatcher("/register.ftl").forward(request,response);
		}
	
			
	}
}
