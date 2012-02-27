package uk.ac.dundee.computing.fionamoug;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import uk.ac.dundee.computing.fionamoug.Error;

public class ListFaults extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		//first check that the user is logged on
		if (!SessionValidate.isLoggedIn(req))
		{
			//not logged on so send to login page
			res.sendRedirect("/fionamoug/login");
		}
		//next check the user is an admin or developer, and hence is able to view existing faults
		if (!SessionValidate.isAdmin(req) && !SessionValidate.isDeveloper(req))
		{
			//neither admin nor developer so show error
			Error error = new Error();
			error.setErrorText("You do not have permission to access this page.");
			req.setAttribute("errorMsg", error);
			req.getRequestDispatcher("/error.jsp").forward(req,res);
		}
		else
		{
			//either admin or developer so show fault list
			String path = req.getPathInfo();
			if (path != null) //get the path from the URL to decide what to show
			{
				//path has something after /fault eg /fault/1 or /fault/assigned
				path = path.substring(1); //remove / from start of path string
				if (path.equals("assigned"))
				{
					//request is for assigned faults
					User staffMember = (User) req.getSession(false).getAttribute("currentUser");
					req.setAttribute("staffID", Integer.toString(staffMember.getID()));
					req.setAttribute("staffType", staffMember.getUserType().toString());
				}
				else
				{
					//request is for a particular fault id given by path
					
					req.setAttribute("faultID", path);
				}
			}
			
		}
		req.setAttribute("data-access-type","get-fault");
		req.getRequestDispatcher("/data").forward(req,res);
	}
}