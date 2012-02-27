<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>Mr Faulty - Fault Details</title>
</head>
<body>
<%@ include file="layout.jsp" %>

<%@ page import="uk.ac.dundee.computing.fionamoug.Fault" %>
<%@ page import="uk.ac.dundee.computing.fionamoug.User" %>

<jsp:useBean id="fault" class="uk.ac.dundee.computing.fionamoug.Fault" scope="request" />

<h1>Fault ID <jsp:getProperty name="fault" property="id" /></h1>
<p>
Fault summary: 
<jsp:getProperty name="fault" property="summary" />
<br>
Fault details: 
<jsp:getProperty name="fault" property="description" />
<br>
Reported by: 
<%= fault.getReporter().getName() %>
<br>
Status:
<%= fault.getStatus() %>
</p>

<p>
<% if (role == "ADMIN")
{ %>
<a href="/fionamoug/delete/fault/<jsp:getProperty name='fault' property='id' />">Delete this fault</a>
<% } %>

<% if (role == "ADMIN" || role == "DEVELOPER")
{ %>
<br>
<a href="/fionamoug/update/fault/<jsp:getProperty name='fault' property='id' />">Update this fault</a>
<% } %>

</p>

</body>
</html>
