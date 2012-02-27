<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>Mr Faulty - Oops!</title>
</head>
<body>
<%@ include file="layout.jsp" %>

<jsp:useBean id="errorMsg" class="uk.ac.dundee.computing.fionamoug.Error" scope="request" />
<p>
<jsp:getProperty name="errorMsg" property="errorText" />
</p>
<p>
For more information or to query this error, please contact the webmaster.
</p>

</body>
</html>
