<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>Mr Faulty - Users</title>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="uk.ac.dundee.computing.fionamoug.User" %>

</head>
<body>
<%@ include file="layout.jsp" %>

<jsp:useBean id="userList" class="java.util.ArrayList" scope="request" />

<h1>Users</h1>
<%
Iterator it = userList.iterator();
while (it.hasNext())
{
%>
<p>
<%
 User nextUser = (User) it.next();
 int id = nextUser.getID();
 %>
 <a href="<%= nextUser.getUserType().toString().toLowerCase() %>/<%= id %>">
 <%= id %> </a>: 
 <%= nextUser.getName() %> (<%= nextUser.getEmail() %>)
 </p>
<%
}
%>


</body>
</html>
