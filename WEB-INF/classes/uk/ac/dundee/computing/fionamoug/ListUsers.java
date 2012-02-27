
package uk.ac.dundee.computing.fionamoug;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ListUsers extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		if (!SessionValidate.isLoggedIn(req))
			res.sendRedirect("/fionamoug/login");
		if (!SessionValidate.isAdmin(req))
		{
			Error error = new Error();
			error.setErrorText("You do not have permission to access this page.");
			req.setAttribute("errorMsg", error);
			req.getRequestDispatcher("/error.jsp").forward(req,res);
		}
		else
		{
			String path = req.getRequestURI();
			path = path.substring(11); //removes the /fionamoug/ leaving string in the form developer/1 or reporter/1 etc
			int lastSlashIndex = path.lastIndexOf('/');
			String userType, userID;
			if (lastSlashIndex >= 0)
			{
				userType = path.substring(0,lastSlashIndex);
				userID = path.substring(lastSlashIndex+1);
			}
			else
			{
				userType = path;
				userID = "";
			}
		
			req.setAttribute("data-access-type","get-user");
			req.setAttribute("userType",userType);
			if (!userID.equals("")) req.setAttribute("userID",userID);
		
			req.getRequestDispatcher("/data").forward(req,res);
		}
	}
}