<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>Mr Faulty - Fault Received</title>
</head>
<body>
<%@ include file="layout.jsp" %>

<p>Thank you!  We've received your fault!
</p>

<jsp:useBean id='newFault' class='uk.ac.dundee.computing.fionamoug.Fault' scope='request' />

Summary: <jsp:getProperty name="newFault" property="summary" />
<br>
Details: <jsp:getProperty name="newFault" property="description" />


</body>
</html>
