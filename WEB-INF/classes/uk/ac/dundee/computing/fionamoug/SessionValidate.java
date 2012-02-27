package uk.ac.dundee.computing.fionamoug;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.fionamoug.User;

public class SessionValidate
{
	public static boolean isLoggedIn(HttpServletRequest req)
	{
		boolean loggedIn = false;
		HttpSession session = req.getSession(false);
		if (session != null)
		{
			User user = (User) session.getAttribute("currentUser");
			if (user != null)
			{
				if (!user.getName().equals(""))
				{
					loggedIn = true;
				}
			}
		}
		return loggedIn;
	}
	
	public static boolean isAdmin(HttpServletRequest req)
	{
		User user = (User) req.getSession().getAttribute("currentUser");
		if (user.getUserType().toString().equals("ADMIN"))
			return true;
		return false;
	}
	
	public static boolean isDeveloper(HttpServletRequest req)
	{
		User user = (User) req.getSession().getAttribute("currentUser");
		if (user.getUserType().toString().equals("DEVELOPER"))
			return true;
		return false;
	}
	
	public static boolean isReporter(HttpServletRequest req)
	{
		User user = (User) req.getSession().getAttribute("currentUser");
		if (user.getUserType().toString().equals("REPORTER"))
			return true;
		return false;
	}
}