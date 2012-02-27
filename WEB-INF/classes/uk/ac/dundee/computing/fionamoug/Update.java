package uk.ac.dundee.computing.fionamoug;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import uk.ac.dundee.computing.fionamoug.Fault;
import uk.ac.dundee.computing.fionamoug.Error;
import uk.ac.dundee.computing.fionamoug.SessionValidate;

public class Update extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		Error error = checkValidUser(req,res);
		if (error == null)
		{
			String pageName = "";
			String path = req.getPathInfo();
			if (path.contains("fault"))
				pageName = "/updateFault.jsp";
			else if (path.contains("user"))
				pageName = "/updateUser.jsp";
			else
			{
				Error error1 = new Error();
				error1.setErrorText("Invalid update request made.");
				req.setAttribute("errorMsg", error1);
				req.getRequestDispatcher("/error.jsp").forward(req,res);
			}
			
			req.getRequestDispatcher(pageName).forward(req,res);
		}
		else
		{
			req.setAttribute("errorMsg",error);
			req.getRequestDispatcher("/fionamoug/error.jsp").forward(req,res);
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		Error error = checkValidUser(req,res);
		if (error == null)
			update(req,res);
		else
		{
			req.setAttribute("errorMsg",error);
			req.getRequestDispatcher("/fionamoug/error.jsp").forward(req,res);
		}
	}
	
	private Error checkValidUser(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		Error error = null;
		
		if (!SessionValidate.isLoggedIn(req))
		{
			res.sendRedirect("/fionamoug/login");
		}
		
		if (!(SessionValidate.isAdmin(req) || SessionValidate.isDeveloper(req)))
		{
			error = new Error();
			error.setErrorText("You do not have permission to access this page.");
		}
		return error;
	}
	
	private void update(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		String path = req.getPathInfo();
		if (path == null)
		{
			Error error = new Error();
			error.setErrorText("Required data to update was not provided.  Please try again.");
			req.setAttribute("errorMsg", error);
			req.getRequestDispatcher("/error.jsp").forward(req,res);
		}
		if (path.length() > 1) path = path.substring(1);
		
		int lastSlash = path.lastIndexOf('/');
		if (lastSlash < 0 || lastSlash == path.length()-1)
		{
			Error error = new Error();
			error.setErrorText("ID to update was not given.  Please try again.");
			req.setAttribute("errorMsg", error);
			req.getRequestDispatcher("/error.jsp").forward(req,res);
		}
		String updateType = path.substring(0,lastSlash);
		String updateID = path.substring(lastSlash+1);
		
		req.setAttribute("data-access-type","update");
		req.setAttribute("type",updateType);
		req.setAttribute("id",updateID);
		req.getRequestDispatcher("/data").forward(req,res);
	}

}