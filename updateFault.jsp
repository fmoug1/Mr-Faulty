<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>Mr Faulty - Update Fault</title>
</head>
<body>
<%@ include file="layout.jsp" %>

<form method="POST" action="">
<p>
Assign developer:
<br />
Developer email: 
<input type="text" name="devEmail" />
<br /><br />
Assign administrator:
<br />
Administrator email: 
<input type="text" name="adminEmail" />
<br /><br />
Set status:
<br /> 
Status:
<select name="status">
  <option value="NEW">New</option>
	<option value="INPROGRESS">In Progress</option>
	<option value="INTEST">In Test</option>
	<option value="DEFERRED">Deferred</option>
	<option value="CLOSED">Closed</option>
</select>
</p>
<input type=submit value="Register" />
</form>

</body>
</html>
