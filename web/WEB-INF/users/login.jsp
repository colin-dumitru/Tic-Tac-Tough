<%-- 
    Document   : RegisterView
    Created on : Apr 20, 2011, 6:02:05 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>


<LINK rel="stylesheet" href="/css/login.css" type="text/css" />
<LINK rel="stylesheet" href="/css/common.css" type="text/css" />

<script type="text/javascript">
function redirectLogin()
{
    window.location = "/web/register";
}
</script>

<html>
    <%-- Daca se acceseaza pagina jsp direct redirectionam catre controller --%>
    <c:if test="${empty user}">
        <c:redirect url="/web/login" />
    </c:if>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        
        <c:if test="${not empty loginError}">
            <div id="error">
                <c:out value="${loginError}" />
            </div>            
        </c:if>
        
        <div id="header" ></div>        
        
        
            <div class="loginForm">
                <form:form action = "/web/loginUser" commandName="user">
                    <p>
                        <font id="userNameText" >UserName: </font>
                        <form:input id="userNameInput" path="userName" />
                    </p>
                    
                    <p>
                        <font id="passwordText" >Password: </font>
                        <form:password id="passwordInput" path="password" />
                    </p>

                    <input id="submit" value="" type="submit" />
                    <input type="button" value="" id="register" onclick="redirectLogin()">
                </form:form>
            </div>
        
    </body>
</html>
