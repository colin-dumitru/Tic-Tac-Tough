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
        <title>Error Page</title>

        <LINK rel="stylesheet" href="/css/error.css" type="text/css" />
        <LINK rel="stylesheet" href="/css/common.css" type="text/css" />
    </head>
    <body>
        <div class="errorContent">
            <h1>Oops... an error occured</h1>
            

            <div class="error">
                <p>An error has occured!</p>
                <%-- Daca mai primim informatii suplimentare, afisam eroarea --%>
                <c:if test="${not empty error}">
                    <c:out value="${error}" />
                </c:if>
            </div>          
        </div>
    </body>
</html>
