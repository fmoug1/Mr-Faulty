package uk.ac.dundee.computing.fionamoug;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;

import uk.ac.dundee.computing.fionamoug.Fault;
import uk.ac.dundee.computing.fionamoug.User;

public class ReceiveFault extends HttpServlet
{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws IOException, ServletException
	{
		if (!SessionValidate.isLoggedIn(req))
			res.sendRedirect("/fionamoug/login");
		else
			req.getRequestDispatcher("/createFault.jsp").forward(req,res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws IOException, ServletException
	{
		//this method handles requests for /create/fault that have come from create fault form (which posts)
		
		if (!SessionValidate.isLoggedIn(req))
			res.sendRedirect("/fionamoug/login");
		
		//create a new fault POJO and set its fields
		Fault newFault = new Fault();
		HttpSession session = req.getSession(false);
		User reporter = (User) session.getAttribute("currentUser");
		newFault.setReporter(reporter);
		newFault.setSummary(req.getParameterValues("summary")[0]);
		newFault.setDescription(req.getParameterValues("details")[0]);
		newFault.setStatus("new");
		newFault.setDateCreated();
		
		//send fault to database
		req.setAttribute("newFault", newFault);
		req.setAttribute("data-access-type", "new-fault");
		req.getRequestDispatcher("/data").forward(req,res);
	}
}