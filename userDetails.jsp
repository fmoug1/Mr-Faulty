<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>Mr Faulty - User Details</title>
</head>
<body>
<%@ include file="layout.jsp" %>

<%@ page import="uk.ac.dundee.computing.fionamoug.User" %>

<jsp:useBean id="user" class="uk.ac.dundee.computing.fionamoug.User" scope="request" />

<h1>User ID <jsp:getProperty name="user" property="id" /></h1>
<p>
Name: 
<jsp:getProperty name="user" property="name" />
<br>
Email: 
<jsp:getProperty name="user" property="email" />
<br>
Role: 
<jsp:getProperty name="user" property="userType" />
</p>

<p>
<% if (role == "ADMIN")
{ %>
<a href="/fionamoug/delete/user/<jsp:getProperty name='user' property='id' />">Delete</a>
<% } %>

<% if (role == "ADMIN" || role == "DEVELOPER")
{ %>
<br>
<a href="/fionamoug/update/user/<jsp:getProperty name='user' property='id' />">Update this user</a>
<% } %>
</p>

</body>
</html>
