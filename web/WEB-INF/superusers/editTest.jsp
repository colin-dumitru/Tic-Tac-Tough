<%-- 
    Document   : editTest
    Created on : May 3, 2011, 8:58:34 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<LINK rel="stylesheet" href="/css/editTest.css" type="text/css" />
<LINK rel="stylesheet" href="/css/common.css" type="text/css" />

<script type="text/javascript">
    var testId = ${test.id};
    
    function init() {
        updateCategoryOption();
    }
    
    function updateCategoryOption() {
        document.getElementById("categorySelect").value = ${test.categoryid};
    }
    
    /*ascunde dialogul de editare de intrebare*/
    function hideUpdateDialog(){
        document.getElementById("editQuestion").style.visibility = "hidden";
    }
   
    //functie pentru redirectionare pt editarea unui test
    function editQuestion(id, content, answer1, answer2, answer3, answer4, correctAnswer, categoryId) {
        document.getElementById("updateContentInput").innerHTML = content;
        document.getElementById("updateAnswer1").setAttribute("value", answer1);
        document.getElementById("updateAnswer2").setAttribute("value", answer2);
        document.getElementById("updateAnswer3").setAttribute("value", answer3);
        document.getElementById("updateAnswer4").setAttribute("value", answer4);
        
        document.getElementById("uanswer" + correctAnswer).checked = true;
        document.getElementById("updateCategoryId").value = categoryId;
        
        document.getElementById("updateQuestionForm").setAttribute("action", "/web/updateQuestion/" + id);
                
        document.getElementById("editQuestion").style.visibility = "visible";
    }
    
    /*ascunde dialogul de adaugare de intrebare*/
    function hideAddDialog(){
        document.getElementById("addQuestion").style.visibility = "hidden";
    }
    
    /*afiseaza dialogul de adaugare*/
    function showAddDialog() {
        document.getElementById("addQuestion").style.visibility = "visible";
    }
    
    /*afiseaza dialogul de cautare de intrebare*/
    function showSearchDialog() {
        document.getElementById("search").style.visibility = "visible";
    }
    
    /*ascunde dialogul de cautare de intrebare*/
    function hideSearchDialog() {
        document.getElementById("search").style.visibility = "hidden";
    }
    
    /*redirectioneaza catre stergerea de intrebare*/
    function deleteQuestion(testid,questionid) {
        window.location = "/web/removeQuestionFromTest/" +testid +"/"+ questionid;
    }
    
</script>

<script type="text/javascript" src="/scripts/jquery.js"></script>
<script type="text/javascript" src="/scripts/questionSearch.js"></script>

<!DOCTYPE html>
<html>
    <%-- Verfiicam daca cererea a fost primita dinspre controller--%>
    <c:if test="empty test" >
        <c:redirect url="/web/home" />
    </c:if>

    <jsp:include page="/WEB-INF/jspf/panel.jsp"></jsp:include>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit your test</title>
    </head>
    <body onload="init();">
        <div class="testInfo">
            <form:form commandName="test" action="/web/updateTest/${test.id}">
                <form:input class="testNameInput" path="name" value="${test.name}"/>

                <div class="tpqLabel">Time per question:</div><form:input class="tpqInput" path="time"/>
                <div class="numqLabel">Questions:</div><form:input class="numqInput" path="numq" />
                <div class="useAllLabel">Use all questions:</div>
                <form:select class ="useAllSelect" path="useAllQuestions">
                    <form:option  value="1" label="Yes"/>
                    <form:option  value="0" label="No"/>
                </form:select>
                <div class="changeDifficultyLabel">Change difficulty:</div>
                <form:select class="changeDifficultySelect" path="difficulty">
                    <form:option  value="1" label="Yes"/>
                    <form:option  value="0" label="No"/>
                </form:select>


                <div>
                    <div class="categoriesLabel">Categories:</div>
                    <form:select class="categorySelect" path="categoryid">
                        <c:forEach var="category" items="${categories}">
                            <form:option id="co${category.categoryId}" value="${category.categoryId}" >
                                ${category.name}
                            </form:option>
                        </c:forEach>
                    </form:select>
                </div>

                <div>
                    <input class="saveChangesButton" type="submit" value="" />
                </div>
            </form:form>                
        </div>

        <%-- ---------------------------------------------------------------------------------- --%>
        <%-- ----------------------Question List ---------------------------------------------- --%>
        <%-- ---------------------------------------------------------------------------------- --%>
        <div class = "content">
            <%-- Listam testele efectuate de utilizator --%>
            <c:forEach var="question" items="${questionList}">
                <div class = "contentItem">
                    <div class = "contentText" >${question.content}</div>
                    <button class ="questionDeleteButton" onclick="deleteQuestion(${test.id},${question.id})"/>
                    <%-- button class = "questionEditButton" onclick="javascript:editQuestion(${question.id},
                        '${question.content}', '${question.answer1}', '${question.answer2}', '${question.answer3}',
                        '${question.answer4}', '${question.correctAnswer}', '${question.categoryId}');"/--%>
                </div>
            </c:forEach>

            <button class="addQuestionButton" onclick="showAddDialog();"></button>
            <button class="searchQuestionButton" onclick="showSearchDialog();"></button>
        </div>



        <%-- ---------------------------------------------------------------------------------- --%>
        <%-- ----------------------Add Question------------------------------------------------ --%>
        <%-- ---------------------------------------------------------------------------------- --%>
        <div id="addQuestion" class = "overlay" >
            <div class="questionForm">
                <form:form  commandName="question" action="/web/addQuestionForTest/${test.id}"  >
                    <form:textarea class="contentInput" path="content" /> 

                    <form:radiobutton id="answer1" value="1" path="correctAnswer" checked="checked"/>
                    <form:input class="answer1Input" id="addAnswer1" path="answer1" /> 

                    <form:radiobutton id="answer2" value="2" path="correctAnswer"/>
                    <form:input class="answer2Input" id="addAnswer2" path="answer2" /> 

                    <form:radiobutton id="answer3" value="3" path="correctAnswer"/>
                    <form:input class="answer3Input" id="addAnswer2" path="answer3" /> 

                    <form:radiobutton id="answer4" value="4" path="correctAnswer"/>
                    <form:input class="answer4Input" id="addAnswer2" path="answer4" /> 


                    <form:select class="questionCategorySelect" path="categoryId">
                        <c:forEach var="category" items="${categories}">
                            <form:option id="co${category.categoryId}" value="${category.categoryId}" label="${category.name}" />
                        </c:forEach>
                    </form:select>

                    <input type="submit" class="createQuestionButton" value="" />
                </form:form>

                <button class="cancelButton" value="" onclick="hideAddDialog();" />
            </div>
        </div>  

        <%-- ---------------------------------------------------------------------------------- --%>
        <%-- ----------------------Update Question--------------------------------------------- --%>
        <%-- ---------------------------------------------------------------------------------- --%>
        <div id="editQuestion" class = "overlay" >
            <div class="questionForm">
                <form:form id="updateQuestionForm"  commandName="question" action="" >
                    <form:textarea class="contentInput" id="updateContentInput" path="content"/> 

                    <form:radiobutton id="uanswer1" value="1" path="correctAnswer" checked="checked"/>
                    <form:input class="answer1Input" id="updateAnswer1" path="answer1" /> 

                    <form:radiobutton id="uanswer2" value="2" path="correctAnswer"/>
                    <form:input class="answer2Input" id="updateAnswer2" path="answer2" /> 

                    <form:radiobutton id="uanswer3" value="3" path="correctAnswer"/>
                    <form:input class="answer3Input" id="updateAnswer3" path="answer3" /> 

                    <form:radiobutton id="uanswer4" value="4" path="correctAnswer"/>
                    <form:input class="answer4Input" id="updateAnswer4" path="answer4" /> 

                    <%-- form:select class="correctSelect" id="updateCorrectAnswer" path="correctAnswer">
                        <form:option id="answer1" value="1" >Answer 1</form:option>
                        <form:option id="answer2" value="2" >Answer 2</form:option>
                        <form:option id="answer3" value="3" >Answer 3</form:option>
                        <form:option id="answer4" value="4" >Answer 4</form:option>
                    </form:select --%>

                    <form:select class="categorySelect" path="categoryId" id="updateCategoryId">
                        <c:forEach var="category" items="${categories}">
                            <form:option id="cou${category.categoryId}" value="${category.categoryId}" >
                                ${category.name}
                            </form:option>
                        </c:forEach>
                    </form:select>

                    <input type="submit" class="updateQuestionButton" value="" />

                </form:form>
                <button class="cancelButton" value="" onclick="hideUpdateDialog();" />
            </div>
        </div>

        <%-- ---------------------------------------------------------------------------------- --%>
        <%-- ----------------------Search Question--------------------------------------------- --%>
        <%-- ---------------------------------------------------------------------------------- --%>
        <div id="search" class="overlay">
            <div class="searchForm">
                <div class="searchLabel">Content:</div>
                <textarea id="searchInput" class="seachInput"> </textarea>
                <button class="searchButton"
                    onclick="runQuestionSearch('/web/searchQuestion', document.getElementById('searchInput').value);" />
            </div>
            
            <div class="content" style="top:200px;" id="questionSearchResult">
                 <div class = "contentItem">
                    <div class = "contentText" >No search results...</div>
                </div>
            </div>
            
            <div class="searchFormBot">
                <button class="cancelSearchButton" onclick="hideSearchDialog();" value="" />
            </div>            
        </div>
    </body>
</html>
