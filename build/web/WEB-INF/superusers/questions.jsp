<%-- 
    Document   : questions
    Created on : May 8, 2011, 5:29:56 PM
    Author     : bkt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    function preload() {
        img = new Image(); img.src = "/images/edit_button_hover.png";
        img = new Image(); img.src = "/images/delete_button_hover.png";
        img = new Image(); img.src = "/images/add_button_hover.png";
        img = new Image(); img.src = "/images/add_button_large_hover.png";
        img = new Image(); img.src = "/images/cancel_button_large_hover.png";
    }
</script>


<LINK rel="stylesheet" href="/css/questions.css" type="text/css" />
<LINK rel="stylesheet" href="/css/common.css" type="text/css" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<script type="text/javascript" >
    
    /*ascunde dialogul de editare de intrebare*/
    function hideUpdateDialog(){
        document.getElementById("editQuestion").style.visibility = "hidden";
    }
   
    //functie pentru redirectionare pt editarea unui test
    function editQuestion(id, content, answer1, answer2, answer3, answer4, correctAnswer, categoryId,
            difficulty) {
        document.getElementById("updateContentInput").innerHTML = content;
        document.getElementById("updateAnswer1").setAttribute("value", answer1);
        document.getElementById("updateAnswer2").setAttribute("value", answer2);
        document.getElementById("updateAnswer3").setAttribute("value", answer3);
        document.getElementById("updateAnswer4").setAttribute("value", answer4);
        
        document.getElementById("uanswer" + correctAnswer).checked = true;
        document.getElementById("updateCategoryId").value = categoryId;
        document.getElementById("updateDifficultySelect").value = difficulty;
        
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
    
    /*redirectioneaza catre stergerea de intrebare*/
    function deleteQuestion(id) {
        window.location = "/web/deleteQuestion/" + id;
    }
</script>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your questions.</title>
    </head>
    <body>
        <jsp:include page="/WEB-INF/jspf/panel.jsp"></jsp:include>

        <%-- Verificam daca este setat atributul - adica daca cererea a venit din controller --%>
        <c:if test="empty questionList">    <%--cand se incearca accesarea linkului din url --%>
            <c:redirect url="/web/home" />
        </c:if>

        <%-- ---------------------------------------------------------------------------------- --%>
        <%-- ----------------------Add Question------------------------------------------------ --%>
        <%-- ---------------------------------------------------------------------------------- --%>
        <div id="addQuestion" class = "overlay" >
            <div class="questionForm">
                <form:form  commandName="question" action="/web/addQuestion"  >
                    <form:textarea class="contentInput" path="content" /> 
                    
                    <form:radiobutton id="answer1" value="1" path="correctAnswer" checked="checked"/>
                    <form:input class="answer1Input" id="addAnswer1" path="answer1" /> 

                    <form:radiobutton id="answer2" value="2" path="correctAnswer"/>
                    <form:input class="answer2Input" id="addAnswer2" path="answer2" /> 

                    <form:radiobutton id="answer3" value="3" path="correctAnswer"/>
                    <form:input class="answer3Input" id="addAnswer2" path="answer3" /> 

                    <form:radiobutton id="answer4" value="4" path="correctAnswer"/>
                    <form:input class="answer4Input" id="addAnswer2" path="answer4" /> 

                    
                    <form:select class="categorySelect" path="categoryId">
                        <c:forEach var="category" items="${categories}">
                            <form:option id="co${category.categoryId}" value="${category.categoryId}" label="${category.name}" />
                        </c:forEach>
                    </form:select>
                    
                    <form:select class="difficultySelect" path="difficulty">
                        <form:option value='0'>Easy</form:option>
                        <form:option value='1'>Medium </form:option>
                        <form:option value='2'>Hard </form:option>
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
                    
                    <form:select id="updateDifficultySelect" class="difficultySelect" path="difficulty">
                        <form:option value="0">Easy</form:option>
                        <form:option value="1">Medium </form:option>
                        <form:option value="2">Hard </form:option>
                    </form:select>

                    <input type="submit" class="updateQuestionButton" value="" />

                </form:form>
                <button class="cancelButton" value="" onclick="hideUpdateDialog();" />
            </div>
        </div>  

        <%-- ---------------------------------------------------------------------------------- --%>
        <%-- ----------------------Question List ---------------------------------------------- --%>
        <%-- ---------------------------------------------------------------------------------- --%>
        <div class = "content">
            <%-- Listam testele efectuate de utilizator --%>
            <c:forEach var="question" items="${questionList}">
                <div class = "question">
                    <div class = "questionContent" >${question.content}</div>
                    <button class ="questionDeleteButton" onclick="deleteQuestion(${question.id})"/>
                    <button class = "questionEditButton" onclick="javascript:editQuestion(${question.id},
                        '${question.content}', '${question.answer1}', '${question.answer2}', '${question.answer3}',
                        '${question.answer4}', '${question.correctAnswer}', '${question.categoryId}',
                        '${question.difficulty}');" />
                </div>
            </c:forEach>

            <button class="addButton" onclick="showAddDialog();"></button>
        </div>
    </body>
</html>