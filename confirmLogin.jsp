<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>Mr Faulty - Logged in</title>
</head>
<body>
<%@ include file="layout.jsp" %>


<p>Welcome back, 
<jsp:getProperty name="currentUser" property="name" />.  You have successfully logged in.

</p>

</body>
</html>
