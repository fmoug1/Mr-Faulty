<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>Mr Faulty - Create Fault</title>
</head>
<body>
<%@ include file="layout.jsp" %>

<p>Use this page to tell us about a fault you've discovered.</p>
<form method=POST action="fault">
Summary of fault:
<input type=text name="summary">
</p>
<p>
Details:
</br>
<textarea name="details" rows="10" cols="50"></textarea>
</p>
<p>
<input type=submit>
</form>


</body>
</html>