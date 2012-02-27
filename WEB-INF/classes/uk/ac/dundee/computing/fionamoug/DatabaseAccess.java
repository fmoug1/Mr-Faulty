package uk.ac.dundee.computing.fionamoug;

import java.util.*;

import java.io.IOException;

import javax.sql.DataSource;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;

import uk.ac.dundee.computing.fionamoug.Fault;
import uk.ac.dundee.computing.fionamoug.User;
import uk.ac.dundee.computing.fionamoug.Error;
import uk.ac.dundee.computing.fionamoug.DatabaseConnection;

public class DatabaseAccess extends HttpServlet
{
	private String dataSourceName;
	private DataSource source = null;
	private DatabaseConnection db;
	
	public void init()
	throws ServletException
	{
		if (source == null)
			findDatabase();
	}
	
	private void findDatabase()
	throws ServletException
	{
		dataSourceName = getInitParameter("data-source");
		
		try
		{
			Context ic = new InitialContext();
			source = (DataSource) ic.lookup("java:comp/env/" + dataSourceName);
			if (source == null)
			{
				 throw new ServletException(dataSourceName + " is an unknown data source.");
			}	 
		}
		catch(NamingException e)
		{
			throw new ServletException("Cannot find data source name "+e);
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		String accessType = (String) req.getAttribute("data-access-type");
		db = new DatabaseConnection();
		
		if (accessType == "new-fault")
		{
			newFault(req,res);
		}
		else if (accessType == "new-user")
		{
			newUser(req,res);
		}
		else if (accessType == "find-user")
		{
			findUser(req,res);
		}
		else if (accessType == "get-fault")
		{
			listFaults(req,res);
		}
		else if (accessType == "get-user")
		{
			listUsers(req,res);
		}
		else if (accessType == "delete")
		{
			delete(req,res);
		}
		else if (accessType == "update")
		{
			update(req,res);
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		doGet(req,res);
	}
	
	private void newFault(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		Fault fault = (Fault) req.getAttribute("newFault");
		if (fault == null)
		{
			Error error = new Error();
			error.setErrorText("The new fault was not correctly sent to the database.  Please try again later.");
			req.setAttribute("errorMsg", error);
			req.getRequestDispatcher("/error.jsp").forward(req,res);
		}
		try
		{
			db.addFault(fault, source);
		}
		catch(SQLException e)
		{
			Error error = new Error();
			error.setErrorText("There was a problem communicating with the fault database.  Please try again later.");
			req.setAttribute("errorMsg", error);
			req.getRequestDispatcher("/error.jsp").forward(req,res);
		}
		req.getRequestDispatcher("/confirmFault.jsp").forward(req,res);
	}
	
	private void newUser(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		User user = null;
		try
		{
			user = (User) req.getAttribute("newUser");
			db.addUser(user, source);
		}
		catch(SQLException e)
		{
			Error error = new Error();
			error.setErrorText("There was an error creating the new user account.  Please try again later.");
			req.setAttribute("errorMsg", error);
			req.getRequestDispatcher("/error.jsp").forward(req,res);
		}
		req.getSession().setAttribute("currentUser", user);
		req.getRequestDispatcher("/confirmRegister.jsp").forward(req,res);
	}
	
	private void findUser(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		String userEmail = (String) req.getParameterValues("email")[0];
		User user = null;
		try
		{
			user = db.findUser(userEmail, source);
		}
		catch(SQLException e)
		{
			Error error = new Error();
			error.setErrorText("There was an error communicating with the user database.  Please try again later.");
			req.setAttribute("errorMsg", error);
			req.getRequestDispatcher("/error.jsp").forward(req,res);
		}
		if (user != null)
		{
			String passwordSupplied = req.getParameterValues("pword")[0];
			if (user.checkPassword(passwordSupplied))
			{
				req.getSession().invalidate();
				req.getSession().setAttribute("currentUser", user);
				req.getSession().setMaxInactiveInterval(1200);
				req.getRequestDispatcher("/confirmLogin.jsp").forward(req,res);
			}
			else
			{
				//add error to display on login page
				Error error = new Error();
				error.setErrorText("The username or password entered is incorrect.");
				req.setAttribute("errorMsg", error);
				req.getRequestDispatcher("/login.jsp").forward(req,res);
			}
		}
		else
		{
			Error error = new Error();
			error.setErrorText("The username or password entered is incorrect.");
			req.setAttribute("errorMsg", error);
			req.getRequestDispatcher("/login.jsp").forward(req,res);
		}
	}

	private void listFaults(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		String faultString = (String) req.getAttribute("faultID");
		
		if (faultString == null)
		{
			//we are not looking for a particular fault ID so either assigned or all
			ArrayList<Fault> faultList = null;
			String staffIDString = (String) req.getAttribute("staffID");
			if (staffIDString == null)
			{
				//not looking for assigned so find all
				try
				{
					faultList = db.getAllFaults(source);
				}
				catch(SQLException e)
				{
					Error error = new Error();
					error.setErrorText("There was an error communicating with the fault database.  Please try again later.");
					req.setAttribute("errorMsg", error);
					req.getRequestDispatcher("/error.jsp").forward(req,res);
				}
			}
			else
			{
				//looking for assigned
				int staffID = Integer.parseInt(staffIDString);
				String staffType = (String) req.getAttribute("staffType");
				try
				{
					faultList = db.getAssignedFaults(source, staffID, staffType);
				}
				catch(SQLException e)
				{
					Error error = new Error();
					error.setErrorText("There was an error communicating with the fault database.  Please try again later.");
					req.setAttribute("errorMsg", error);
					req.getRequestDispatcher("/error.jsp").forward(req,res);
				}
			}
			req.setAttribute("faultList", faultList);
			req.getRequestDispatcher("/viewFaults.jsp").forward(req,res);
		}
		else
		{
			//looking for a particular fault ID
			int faultID = Integer.parseInt(faultString);
			Fault fault = null;
			try
			{
				fault = db.getFault(source, faultID);
			}
			catch(SQLException e)
			{
				Error error = new Error();
				error.setErrorText("There was an error communicating with the fault database.  Please try again later.");
				req.setAttribute("errorMsg", error);
				req.getRequestDispatcher("/error.jsp").forward(req,res);
			}
			req.setAttribute("fault", fault);
			req.getRequestDispatcher("/faultDetails.jsp").forward(req,res);
		}
		
	}
	
	private void listUsers(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		String userIDStr = (String) req.getAttribute("userID");
		String userType;
		userType = (String) req.getAttribute("userType");
		
		int userID;
		if (userIDStr != null)
		{
			userID = Integer.parseInt(userIDStr);
			User user = null;
			try
			{
				user = db.getUser(source, userID);
			}
			catch(SQLException e)
			{
				Error error = new Error();
				error.setErrorText("There was an error communicating with the users database.  Please try again later.");
				req.setAttribute("errorMsg", error);
				req.getRequestDispatcher("/error.jsp").forward(req,res);
			}
			req.setAttribute("user",user);
			req.getRequestDispatcher("/userDetails.jsp").forward(req,res);
		}
		else
		{
			ArrayList<User> users = null;
			try
			{
				users = db.getAllUsers(source, userType);
			}
			catch(SQLException e)
			{
				Error error = new Error();
				error.setErrorText("There was an error communicating with the users database.  Please try again later.");
				req.setAttribute("errorMsg", error);
				req.getRequestDispatcher("/error.jsp").forward(req,res);
			}
			req.setAttribute("userList", users);
			req.getRequestDispatcher("/viewUsers.jsp").forward(req,res);
		}
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		String type = (String) req.getAttribute("type");
		int id = Integer.parseInt((String) req.getAttribute("id"));
		
		try
		{
			db.delete(source, type, id);
		}
		catch(SQLException e)
		{
			Error error = new Error();
			error.setErrorText("There was an error communicating with the database.  Please try again later.");
			req.setAttribute("errorMsg", error);
			req.getRequestDispatcher("/error.jsp").forward(req,res);
		}
		req.getRequestDispatcher("/confirmDelete.jsp").forward(req,res);
	}

	private void update(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException
	{
		String type = (String) req.getAttribute("type");
		int id = Integer.parseInt((String) req.getAttribute("id"));
		
		if (type.equals("fault"))
		{
			String developer = req.getParameterValues("devEmail")[0];
			String admin = req.getParameterValues("adminEmail")[0];
			String status = req.getParameterValues("status")[0];
			try
			{
				db.updateFault(source, id, developer, admin, status);
			}
			catch(SQLException e)
			{
				Error error = new Error();
				error.setErrorText("There was an error communicating with the database.  Please try again later.");
				req.setAttribute("errorMsg", error);
				req.getRequestDispatcher("/error.jsp").forward(req,res);
			}
		}
		else if (type.equals("user"))
		{
			String name = req.getParameterValues("name")[0];
			String email = req.getParameterValues("email")[0];
			String role = req.getParameterValues("role")[0];
			String password = req.getParameterValues("pword1")[0];
			try
			{
				db.updateUser(source, id, name, email, role, password);
			}
			catch(SQLException e)
			{
				Error error = new Error();
				error.setErrorText("There was an error communicating with the database.  Please try again later.");
				req.setAttribute("errorMsg", error);
				req.getRequestDispatcher("/error.jsp").forward(req,res);
			}
		}
		
		String sendTo;
		if (type.equals("fault"))
			sendTo = "/fionamoug/" + type + "/" + id;
		else
		{
			User user = null;
			try
			{
				user = db.getUser(source,id);
			}
			catch(SQLException e)
			{
				Error error = new Error();
				error.setErrorText("There was a problem communicating with the user database.  Please try again later.");
				req.setAttribute("errorMsg",error);
				req.getRequestDispatcher("/error.jsp").forward(req,res);
			}
			sendTo = "/fionamoug/" + user.getUserType().toString() + "/" + id;
		}
		res.sendRedirect(sendTo);
	}
}