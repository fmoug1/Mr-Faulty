<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>Mr Faulty - Update User</title>
</head>
<body>
<%@ include file="layout.jsp" %>

<form method="POST" action="">
<p>
Name:
<input type=text name="name" />
<br />
Email:
<input type=text name="email" />
<br />
Role: 
<select name="role">
  <option value="REPORTER">Reporter</option>
	<option value="DEVELOPER">Developer</option>
	<option value="ADMIN">Administrator</option>
</select>
<br />
New password:
<input type=password name="pword1" />
<br />
Confirm password:
<input type=password name="pword2" />

</p>
<input type=submit value="Submit" />
</form>

</body>
</html>
