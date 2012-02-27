<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>Mr Faulty - Faults</title>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="uk.ac.dundee.computing.fionamoug.Fault" %>

</head>
<body>
<%@ include file="layout.jsp" %>
<jsp:useBean id="faultList" class="java.util.ArrayList" scope="request" />

<h1>Faults</h1>

<%
Iterator it = faultList.iterator();
while (it.hasNext())
{
%>
 <p>
<%
 Fault fault = (Fault) it.next();
 int id = fault.getID();
 %>
 <a href="/fionamoug/fault/<%= id %>">
 <%= id %> </a>: 
 <%= fault.getSummary() %>
 </p>
<%
}
%>

</body>
</html>
