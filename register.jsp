<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>Mr Faulty - Register</title>
</head>
<body>
<%@ include file="layout.jsp" %>

<jsp:useBean id="errorMsg" class="uk.ac.dundee.computing.fionamoug.Error" scope="request" />

<h1>Register</h1>
<% if (!errorMsg.getErrorText().equals(""))
{%>
<p>
<font color="red">
* 
<jsp:getProperty name="errorMsg" property="errorText" />
</font>
</p>
<% } %>

<form method="POST" action="">
<p>
Name:
<input type=text name="name" />
<br>
Email:
<input type=text name="email" />
<br>
Create password:
<input type=password name="pword1" />
<br>
Confirm password:
<input type=password name="pword2" />
</p>
<input type=submit value="Register" />
</form>

</body>
</html>
