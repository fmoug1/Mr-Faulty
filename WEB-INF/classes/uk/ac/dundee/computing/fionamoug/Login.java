package uk.ac.dundee.computing.fionamoug;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Login extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		HttpSession session = req.getSession(false);
		if (session != null) session.invalidate();
		req.getRequestDispatcher("/login.jsp").forward(req,res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		req.setAttribute("data-access-type", "find-user");
		req.getRequestDispatcher("/data").forward(req,res);
	}
}