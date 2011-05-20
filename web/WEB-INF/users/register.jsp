<%-- 
    Document   : RegisterView
    Created on : Apr 20, 2011, 6:02:05 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<LINK rel="stylesheet" href="/css/register.css" type="text/css" />
<LINK rel="stylesheet" href="/css/common.css" type="text/css" />

<script type="text/javascript">
function redirectLogin()
{
    window.location = "/web/login";
}
</script>

<!DOCTYPE html>
<html>
    
    <c:if test="${empty user}">
        <c:redirect url="/web/register" />
    </c:if>
        
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
    </head>
    <body>
        <c:if test="${not empty registerError}">
            <div id="error">
                <c:out value="${registerError}" />
            </div>            
        </c:if>
        
        <div id="header" ></div> 
        
        <div id="registerForm">
            
            <form:form action = "/web/registerUser" commandName="user">
                <font id="nameText">Full Name:</font>
                <form:input id="nameInput" path="name" />
                
                
                <font id="usernameText">Username:</font>
                <form:input id="usernameInput" path="userName" />
                
                <font id="passwordText">Password:</font>
                <form:password id="passwordInput" path="password" />
                
                <font id="emailText">Email:</font>
                <form:input id="emailInput" path="email" />

                <input id="submit" type="submit" value="" />
                <input type="button" value="" id="login" onclick="redirectLogin()">
            </form:form>
        </div>
    </body>
</html>
