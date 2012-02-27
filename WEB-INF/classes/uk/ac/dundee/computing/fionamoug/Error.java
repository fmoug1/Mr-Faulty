package uk.ac.dundee.computing.fionamoug;

public class Error
{
	private String errorText;
	
	public Error()
	{
		errorText = "";
	}
	
	public void setErrorText(String errorText)
	{
		this.errorText = errorText;
	}
	
	public String getErrorText()
	{
		return errorText;
	}
}