<%-- 
    Document   : tests
    Created on : Apr 30, 2011, 7:32:47 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<LINK rel="stylesheet" href="/css/tests.css" type="text/css" />
<LINK rel="stylesheet" href="/css/common.css" type="text/css" />

<script type="text/javascript">
    function preload() {
        img = new Image(); img.src = "/images/edit_button_hover.png";
        img = new Image(); img.src = "/images/delete_button_hover.png";
        img = new Image(); img.src = "/images/add_button_hover.png";
        img = new Image(); img.src = "/images/scores_button_hover.png";
    }
</script>

<script type="text/javascript" >
    //functie pentru redirectionare pt editarea unui test
    function editTest(id) {
        window.location = "/web/editTest/" + id.toString();
    }
    
    function hideAddDialog(){
        document.getElementById("addTest").style.visibility = "hidden";
    }
    
    function showAddDialog() {
        document.getElementById("addTest").style.visibility = "visible";
    }
    
    function deleteTest(testId) {
        window.location = "/web/deleteTest/" + testId.toString();
    }
    
    function viewScores(testId){
        window.location = "/web/viewUserScores/" + testId.toString();
    }
</script>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your tests.</title>
    </head>
    <body onload="preload();">
        <jsp:include page="/WEB-INF/jspf/panel.jsp"></jsp:include>

        <%-- Verificam daca este setat atributul - adica daca cererea a venit din controller --%>
        <c:if test="empty testList">
            <c:redirect url="/web/home" />
        </c:if>

        <div id="addTest" class = "addTest" >
            <form:form class="testForm" commandName="test" action="/web/addTest" >
                <div class="testName">Test Name</div>
                <form:input class="testNameInput" path="name" /> 
                <input type="submit" class="createTestButton" value="" />
                <button class="cancelButton" />
            </form:form>
        </div>  

        <div class = "content">
            <%-- Listam testele efectuate de utilizator --%>
            <c:forEach var="test" items="${testList}">
                <div class = "test">
                    <div class = "testName" >${test.name} </div>
                    <button class = "testEditButton" onclick="editTest(${test.id})"></button>
                    <button class = "deleteTestButton" onclick="deleteTest(${test.id})"></button>
                    <button class = "viewScoresButton" onclick="viewScores(${test.id})"></button>
                </div>
            </c:forEach>

            <button class="addButton" onclick="showAddDialog();"></button>
        </div>
    </body>
</html>
