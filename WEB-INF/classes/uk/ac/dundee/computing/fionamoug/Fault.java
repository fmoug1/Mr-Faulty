package uk.ac.dundee.computing.fionamoug;

import java.sql.Timestamp;

import uk.ac.dundee.computing.fionamoug.User;

public class Fault
{
	int id;
	String summary;
	String description;
	//int severity;
	User reporter;
	User developer;
	User administrator;
	FaultStatus status;
	Timestamp dateCreated;
	
	public Fault()
	{
		id = -1;
		summary = null;
		description = null;
		reporter = null;
		developer = null;
		administrator = null;
	}
	
	public void setID(int newID)
	{
		id = newID;
	}
	
	public int getID()
	{
		return id;
	}
	
	public void setSummary(String newSummary)
	{
		summary = newSummary;
	}
	
	public String getSummary()
	{
		return summary;
	}
	
	public void setDescription(String newDesc)
	{
		description = newDesc;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setReporter(User reporter)
	{
		this.reporter = reporter;
	}
	
	public User getReporter()
	{
		return reporter;
	}
	
	public void setDeveloper(User developer)
	{
		this.developer = developer;
	}
	
	public User getDeveloper()
	{
		return developer;
	}
	
	public void setAdministrator(User administrator)
	{
		this.administrator = administrator;
	}
	
	public User getAdministrator()
	{
		return administrator;
	}
	
	public void setStatus(FaultStatus newStatus)
	{
		status = newStatus;
	}
	
	public void setStatus(String newStatus)
	{
		String status = newStatus.toUpperCase();
		if (status.equals("NEW")) this.status = FaultStatus.NEW;
		else if (status.equals("INPROGRESS")) this.status = FaultStatus.INPROGRESS;
		else if (status.equals("INTEST")) this.status = FaultStatus.INTEST;
		else if (status.equals("DEFERRED")) this.status = FaultStatus.DEFERRED;
		else if (status.equals("CLOSED")) this.status = FaultStatus.CLOSED;
	}

	public FaultStatus getStatus()
	{
		return status;
	}
	
	public Timestamp getDateCreated()
	{
		return dateCreated;
	}
	
	public void setDateCreated(Timestamp date)
	{
		dateCreated = date;
	}
	
	public void setDateCreated()
	{
		dateCreated = new Timestamp(System.currentTimeMillis());
	}
}
