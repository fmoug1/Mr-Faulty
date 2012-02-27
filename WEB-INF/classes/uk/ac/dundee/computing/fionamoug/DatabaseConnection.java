package uk.ac.dundee.computing.fionamoug;

import javax.sql.*;
import java.sql.*;
import java.util.ArrayList;

import uk.ac.dundee.computing.fionamoug.Fault;
import uk.ac.dundee.computing.fionamoug.User;
import uk.ac.dundee.computing.fionamoug.Error;

public class DatabaseConnection
{
	private String username;
	private String password;
	
	public DatabaseConnection()
	{
		username = "fionamoug";
		password = "ac31004";
	}

	public void addFault(Fault fault, DataSource source)
	throws SQLException
	{
		Connection con = source.getConnection(username, password);
		
		PreparedStatement prepst = con.prepareStatement("INSERT INTO Faults (summary, detail, faulttime, reporterid, status) VALUES (?,?,?,?,?)");
		prepst.setString(1,fault.getSummary());
		prepst.setString(2,fault.getDescription());
		prepst.setObject(3,fault.getDateCreated());
		prepst.setInt(4,fault.getReporter().getID());
		prepst.setString(5,fault.getStatus().toString());
		prepst.executeUpdate();
		
		con.close();
	}
	
	public void addUser(User user, DataSource source)
	throws SQLException
	{
		Connection con = source.getConnection(username, password);
		PreparedStatement prepst = con.prepareStatement("INSERT INTO Users (name, email, role, password) VALUES (?, ?, ?, ?)");
		prepst.setString(1, user.getName());
		prepst.setString(2, user.getEmail());
		prepst.setString(3, user.getUserType().toString());
		prepst.setString(4, user.getPassword());
		prepst.executeUpdate();
		
		con.close();
	}
	
	public User findUser(String email, DataSource source)
	throws SQLException
	{
		User user = null;
		Connection con = source.getConnection(username, password);
		
		String sql = "SELECT name, role, password FROM Users WHERE email=?";
		PreparedStatement prepst = con.prepareStatement(sql);
		prepst.setString(1, email);
		ResultSet results = prepst.executeQuery();
		
		if (results.next())
		{
			user = new User();
			user.setEmail(email);
			user.setName(results.getString("name"));
			user.setUserType(results.getString("role"));
			user.setPassword(results.getString("password"));
			user.setID(findID(email, source));
		}
		
		results.close();
		con.close();
		
		return user;
	}
	
	public User findUser(int id, DataSource source)
	throws SQLException
	{
		User user = null;
		Connection con  = source.getConnection(username, password);
		
		String sql = "SELECT name, email, role, password FROM Users WHERE userid=?";
		PreparedStatement prepst = con.prepareStatement(sql);
		prepst.setInt(1, id);
		ResultSet results = prepst.executeQuery();
		
		if (results.next())
		{
			user = new User();
			user.setEmail(results.getString("email"));
			user.setName(results.getString("name"));
			user.setUserType(results.getString("role"));
			user.setPassword(results.getString("password"));
			user.setID(id);
		}
		
		results.close();
		con.close();
		
		return user;
	}
	
	public ArrayList<Fault> getAllFaults(DataSource source)
	throws SQLException
	{
		String sql = "SELECT * FROM Faults";
		Connection con = source.getConnection(username, password);
		
		PreparedStatement prepst = con.prepareStatement(sql);
		ResultSet results = prepst.executeQuery();
		
		Fault nextFault;
		ArrayList<Fault> faultList = new ArrayList<Fault>();
		
		while (results.next())
		{
			nextFault = new Fault();
			nextFault.setID(results.getInt("faultid"));
			nextFault.setSummary(results.getString("summary"));
			nextFault.setDescription(results.getString("detail"));
			nextFault.setStatus(results.getString("status"));
			
			nextFault.setReporter(getUser(source, results.getInt("reporterid")));
			nextFault.setDeveloper(getUser(source, results.getInt("developerid")));
			nextFault.setAdministrator(getUser(source, results.getInt("adminid")));
				
			faultList.add(nextFault);
		}
		
		results.close();
		con.close();
		
		return faultList;
	}
	
	public ArrayList<Fault> getAssignedFaults(DataSource source, int id, String staffType)
	throws SQLException
	{
		String sql = "SELECT * FROM Faults WHERE " + staffType.toLowerCase() + "id=?";
		Connection con = source.getConnection(username, password);
		
		PreparedStatement prepst = con.prepareStatement(sql);
		prepst.setInt(1, id);
		ResultSet results = prepst.executeQuery();
		
		ArrayList<Fault> faultList = new ArrayList<Fault>();
		while (results.next())
		{
			Fault nextFault = new Fault();
			nextFault = new Fault();
			nextFault.setID(results.getInt("faultid"));
			nextFault.setSummary(results.getString("summary"));
			nextFault.setDescription(results.getString("detail"));
			nextFault.setStatus(results.getString("status"));
			
			nextFault.setReporter(getUser(source, results.getInt("reporterid")));
			nextFault.setDeveloper(getUser(source, results.getInt("developerid")));
			nextFault.setAdministrator(getUser(source, results.getInt("adminid")));
			
			faultList.add(nextFault);
		}
		results.close();
		con.close();
		
		return faultList;
	}
	
	public Fault getFault(DataSource source, int id)
	throws SQLException
	{
		Connection con = source.getConnection(username, password);
		String sql = "SELECT * FROM Faults WHERE faultid=?";
		PreparedStatement prepst = con.prepareStatement(sql);
		prepst.setInt(1, id);
		ResultSet results = prepst.executeQuery();
			
		Fault fault = null;
		
		if (results.next())
		{
			fault = new Fault();
			fault.setID(results.getInt("faultid"));
			fault.setSummary(results.getString("summary"));
			fault.setDescription(results.getString("detail"));
			fault.setStatus(results.getString("status"));
			fault.setReporter(findUser(results.getInt("reporterid"), source));
			fault.setReporter(getUser(source, results.getInt("reporterid")));
			fault.setDeveloper(getUser(source, results.getInt("developerid")));
			fault.setAdministrator(getUser(source, results.getInt("adminid")));
		}
		
		results.close();
		con.close();
		
		return fault;
	}
	
	public int findID(String userEmail, DataSource source)
	throws SQLException
	{
		int id = -1;
		Connection con = source.getConnection(username, password);
		String sql = "SELECT userid FROM Users WHERE email=?";
		PreparedStatement prepst = con.prepareStatement(sql);
		prepst.setString(1,userEmail);
		ResultSet results = prepst.executeQuery();
		
		if (results.next())
		{
			id = results.getInt("userid");
		}
		
		return id;
	}

	public ArrayList<User> getAllUsers(DataSource source, String userType)
	throws SQLException
	{
		userType = userType.toUpperCase();
		Connection con = source.getConnection(username, password);
		String sql = "SELECT userid, name, email FROM Users WHERE role = ?";
		PreparedStatement prepst = con.prepareStatement(sql);
		prepst.setString(1,userType);
		ResultSet results = prepst.executeQuery();
		
		ArrayList<User> userList = new ArrayList<User>();
		User user;
		while (results.next())
		{
			user = new User();
			user.setID(results.getInt("userid"));
			user.setName(results.getString("name"));
			user.setEmail(results.getString("email"));
			user.setUserType(userType);
			userList.add(user);
		}
		
		return userList;
	}
	
	public User getUser(DataSource source, int userID)
	throws SQLException
	{
		String sql = "SELECT name, email, role, password FROM Users WHERE userid = ?";
		Connection con = source.getConnection(username,password);
		PreparedStatement prepst = con.prepareStatement(sql);
		prepst.setInt(1,userID);
		ResultSet results = prepst.executeQuery();
		
		User user = null;
		if (results.next())
		{
			user = new User();
			user.setID(userID);
			user.setName(results.getString("name"));
			user.setEmail(results.getString("email"));
			user.setUserType(results.getString("role"));
			user.setPassword(results.getString("password"));
		}
		
		results.close();
		con.close();
		
		return user;
	}
	
	public void delete(DataSource source, String type, int id)
	throws SQLException
	{
		//need to change 'type' as it appears in the URL (user, fault) to match database table names (Users, Faults)
		String resolvedType = type.toUpperCase().substring(0,1) + type.substring(1) + "s";
		String sql = "DELETE FROM " + resolvedType +" WHERE " + type + "id= ?";
		Connection con = source.getConnection(username, password);
		PreparedStatement prepst = con.prepareStatement(sql);
		prepst.setInt(1, id);
		prepst.executeUpdate();
		
		con.close();
	}
	
	public void updateFault(DataSource source, int id, String developerEmail, String adminEmail, String status)
	throws SQLException
	{
		int developerID, adminID;
		Fault fault = getFault(source,id);
		if (developerEmail.equals("") || developerEmail == null)
			developerID = fault.getDeveloper().getID();
		else
			developerID = findID(developerEmail, source);
		if (adminEmail.equals("") || adminEmail == null)
			adminID = fault.getAdministrator().getID();
		else
			adminID = findID(adminEmail,source);
		if (status.equals("") || status == null)
			status = fault.getStatus().toString();
		
		String sql = "UPDATE Faults SET adminid=?, developerid=?, status=? WHERE faultid=?";
		Connection con = source.getConnection(username, password);
		PreparedStatement prepst = con.prepareStatement(sql);
		prepst.setInt(1,adminID);
		prepst.setInt(2,developerID);
		prepst.setString(3,status);
		prepst.setInt(4,id);
		prepst.executeUpdate();
		
		con.close();
	}
	
	public void updateUser(DataSource source, int id, String name, String email, String role, String password)
	throws SQLException
	{
		User user = getUser(source,id);
		if (name.equals("") || name == null) name = user.getName();
		if (email.equals("") || email == null) email = user.getEmail();
		if (role.equals("") || role == null) role = user.getUserType().toString();
		if (password.equals("") || password == null) password = user.getPassword();
		
		String sql = "UPDATE Users SET name=?, email=?, role=?, password=? WHERE userid=?";
		Connection con = source.getConnection(username, this.password);
		PreparedStatement prepst = con.prepareStatement(sql);
		prepst.setString(1,name);
		prepst.setString(2,email);
		prepst.setString(3,role);
		prepst.setString(4,password);
		prepst.setInt(5,id);
		prepst.executeUpdate();
		
		con.close();
	}
}