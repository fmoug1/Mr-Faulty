package uk.ac.dundee.computing.fionamoug;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import uk.ac.dundee.computing.fionamoug.Error;

public class Delete extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws IOException, ServletException
	{
		//check user is logged in
		boolean loggedIn = SessionValidate.isLoggedIn(req);
		if (!loggedIn) res.sendRedirect("/fionamoug/login");
		if (SessionValidate.isAdmin(req))
		{
			String path = req.getPathInfo();
			if (path == null)
			{
				Error error = new Error();
				error.setErrorText("Type to delete was not specified.  Please try again later.");
				req.setAttribute("errorMsg", error);
				req.getRequestDispatcher("/error.jsp").forward(req,res);
			}
			path = path.substring(1); //remove the first slash
			int lastSlash = path.lastIndexOf('/');
			if (lastSlash < 0 || lastSlash == path.length()-1)
			{
				Error error = new Error();
				error.setErrorText("ID to delete was not specified.");
				req.setAttribute("errorMsg", error);
				req.getRequestDispatcher("/error.jsp").forward(req,res);
			}
			String delType = path.substring(0,lastSlash);
			String delID = path.substring(lastSlash+1);
		
			req.setAttribute("data-access-type","delete");
			req.setAttribute("type",delType);
			req.setAttribute("id",delID);
			req.getRequestDispatcher("/data").forward(req,res);
		}
		else
		{
			Error error = new Error();
			error.setErrorText("You do not have permission to access this page.");
			req.setAttribute("errorMsg", error);
			req.getRequestDispatcher("/error.jsp").forward(req,res);
		}
	}
}