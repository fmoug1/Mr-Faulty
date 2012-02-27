package uk.ac.dundee.computing.fionamoug;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import uk.ac.dundee.computing.fionamoug.User;
import uk.ac.dundee.computing.fionamoug.UserType;
import uk.ac.dundee.computing.fionamoug.Error;

public class Register extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		req.getRequestDispatcher("/register.jsp").forward(req,res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		String userTypeParam = req.getPathInfo().substring(1);
		
		if (!userTypeParam.equals("reporter") && !userTypeParam.equals("developer") && !userTypeParam.equals("admin"))
		{
			Error error1 = new Error();
			error1.setErrorText("Invalid request made.  Please try again.");
			req.setAttribute("errorMsg", error1);
			req.getRequestDispatcher("/error.jsp").forward(req,res);
		}
		
		Error error = checkForm(req);
		if (error != null)
		{
			req.setAttribute("errorMsg",error);
			req.getRequestDispatcher("/register.jsp").forward(req,res);
		}
		else
		{
			//handle data processing
			User user = new User();
			user.setName(req.getParameterValues("name")[0]);
			user.setEmail(req.getParameterValues("email")[0]);
			user.setPassword(req.getParameterValues("pword1")[0]);
			user.setUserType(userTypeParam);
		
			req.setAttribute("newUser", user);
			req.setAttribute("data-access-type", "new-user");
			req.getRequestDispatcher("/data").forward(req,res);
		}
	}
	
	private Error checkForm(HttpServletRequest req)
	{
		Error error = null;
		
		//check the two passwords entered are the same
		String password = req.getParameterValues("pword1")[0];
		String passwordConfirm = req.getParameterValues("pword2")[0];
		if (!password.equals(passwordConfirm))
		{
			error = new Error();
			error.setErrorText("The passwords entered do not match.");
		}
		
		//check email looks like an email address
		String email = req.getParameterValues("email")[0];
		if (!email.contains("@"))
		{
			error = new Error();
			error.setErrorText("Invalid email address entered.");
		}
		
		return error;
	}
}