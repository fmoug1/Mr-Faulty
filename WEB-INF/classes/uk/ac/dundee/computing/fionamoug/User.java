package uk.ac.dundee.computing.fionamoug;

import uk.ac.dundee.computing.fionamoug.UserType;

public class User
{
	private int id;
	private String name;
	private String email;
	private String password;
	private UserType type;
	
	public User()
	{
		id = -1; //invalid id marker
		name = "";
		email = "";
		password = "";
		type = UserType.UNSPEC;
	}
	
	public int getID()
	{
		return id;
	}

	public void setID(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public UserType getUserType()
	{
		return type;
	}
	
	public void setUserType(UserType type)
	{
		this.type = type;
	}
	
	public void setUserType(String newType)
	{
		String type = newType.toUpperCase();
		
		if (type.equals("ADMIN")) this.type = UserType.ADMIN;
		else if (type.equals("DEVELOPER")) this.type = UserType.DEVELOPER;
		else if (type.equals("REPORTER")) this.type = UserType.REPORTER;
		else this.type = UserType.UNSPEC;
	}
	
	public boolean checkPassword(String passwordSupplied)
	{
		if (passwordSupplied.equals(password))
			return true;
		return false;
	}
}