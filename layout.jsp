<jsp:useBean id="currentUser" class="uk.ac.dundee.computing.fionamoug.User" scope="session" />

<% String role = currentUser.getUserType().toString(); %>

<div id="topMenu">
<a href="/fionamoug/create/fault">Report Fault</a>
<% if (role.equals("ADMIN") || role.equals("DEVELOPER"))
{ %>
<a href="/fionamoug/fault/assigned">My Assigned Faults</a>
<a href="/fionamoug/fault">View All Faults</a>
<% }
if (role.equals("ADMIN"))
{%>
<a href="/fionamoug/reporter">View All Reporters</a>
<a href="/fionamoug/developer">View All Developers</a>
<a href="/fionamoug/admin">View All Administrators</a>
<a href="/fionamoug/create/user/developer">Register Developer</a>
<a href="/fionamoug/create/user/admin">Register Administrator</a>
<%}
if (role.equals("UNSPEC"))
{
%>
<a href="/fionamoug/login">Log In</a>
<a href="/fionamoug/create/user/reporter">Register</a>
<%
}
else
{
%>
<a href="/fionamoug/logout">Log Out</a>
<% } %>
</div>
