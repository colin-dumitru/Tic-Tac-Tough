<%-- 
    Document   : error
    Created on : May 3, 2011, 12:23:41 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Error page!</h1>
        
        <%-- Daca mai primim informatii suplimentare, afisam eroarea --%>
        <c:if test="${not empty error}">
            <div id="error">
                <c:out value="${error}" />
            </div>            
        </c:if>
        
        <p>An error has occured!</p>
    </body>
</html>
