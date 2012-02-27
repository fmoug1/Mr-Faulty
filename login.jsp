<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>Mr Faulty - Log In</title>
</head>
<body>
<%@ include file="layout.jsp" %>

<h1>
Log In
</h1>

<jsp:useBean id="errorMsg" class="uk.ac.dundee.computing.fionamoug.Error" scope="request" />

<font color="red">
<% if (!errorMsg.getErrorText().equals("")) %> *
<jsp:getProperty name="errorMsg" property="errorText" />
</font>

<p>
<form method="POST" action="login">
Username:
<input type="text" name="email" />
<br>
Password:
<input type="password" name="pword" />
</p>
<p>
<input type="submit" value="Log in" />
</form>

<p>Don't have an account?  Click <a href="create/user/reporter">here</a> to register.

</body>
</html>
