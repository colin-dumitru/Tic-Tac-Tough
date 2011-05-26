<%-- 
    Document   : tests
    Created on : Apr 30, 2011, 7:32:47 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<LINK rel="stylesheet" href="/css/common.css" type="text/css" />
<LINK rel="stylesheet" href="/css/userScores.css" type="text/css" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your test results.</title>
    </head>
    <body onload="preload();">
        <jsp:include page="/WEB-INF/jspf/panel.jsp"></jsp:include>


        <table class="content">
            <%-- Listam testele efectuate de utilizator --%>
            <c:forEach var="result" items="${resultList}">
                <tr class = "resultRow">
                    <td class="resultName">${result.user.name} </td>
                    <td class="resultScore">${result.testUser.score} </td>
                    <td class="resultDate">${result.testUser.date} </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
