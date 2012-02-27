package uk.ac.dundee.computing.fionamoug;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.*;

import javax.servlet.http.*;

public class Logout extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		HttpSession session = req.getSession(false);
		if (session != null)
		{
			session.removeAttribute("user");
			session.invalidate();
		}
		res.sendRedirect("/fionamoug");
	}
}