<%-- 
    Document   : takeTest
    Created on : May 20, 2011, 8:08:51 PM
    Author     : bkt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<LINK rel="stylesheet" href="/css/test.css" type="text/css" />
<LINK rel="stylesheet" href="/css/common.css" type="text/css" />

<script type="text/javascript" src="/scripts/jquery.js"></script>
<script type="text/javascript" src="/scripts/test.js"></script>


<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/jspf/panel.jsp"></jsp:include>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Take a test!</title>
    </head>
    <body onload="initialize();">
        <div class="test">
            <div class="testTop"></div>
            <div id="testContent" class="testContent">
                <div class="testContentText" id ="testContentText">
                    Mama face mere, daca mama face mere, ce face ea?
                </div>
            </div>
            <div class="testBottom" > </div>
            
            <canvas id="timerCanvas" class="timerCanvas" width="71" height="71" > </canvas>
            <div class="timerText" id="timerText" > 40 </div>
            <div class="timerOverlay" > </div>
            
            <form>
                <button class="answer1Button" >Answer 1</button>
                <button class="answer2Button" >Answer 2</button>
                <button class="answer3Button" >Answer 3</button>
                <button class="answer4Button" >Answer 4</button>
            </form>
        </div>
    </body>
</html>
