<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<LINK rel="stylesheet" href="/css/index.css" type="text/css" />
<LINK rel="stylesheet" href="/css/common.css" type="text/css" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Tic Tac Tough</title>
    </head>

    <body>
        <jsp:include page="/WEB-INF/jspf/panel.jsp" />

        <div class="page">
            <div class="sideBar">
                <h1>Team</h1>
                <p>
                    Dumitru Catalin <br>
                    Melinte Iustina
                </p>
                
            </div>
            
            <div class="info">
                <h1>About</h1>
                <p>
                    This website is designed to help you asses your personal
                    knowledge in the most simple and intuitive way. <br> <br>
                    Tests are grouped by categories enabling you to quickly 
                    select which tests you want to try. You can also show off
                    you skills in out user rank. See if you can reach the top!
                </p>
            </div>
                
            <div class="info">
                <h1>How does it work?</h1>
                <p>
                    After you select the test you want, the test will begin. 
                    each question has a limited time specific to each test, which,
                    depending on it's difficulty, is worth some points (1 for an
                    easy question, 2 for medium ones, and 3 for hard questions). 
                    There points will be added to you total score, which you can see
                    at the top of your screen. Be careful, each wrong answer will 
                    subtract your score by the same amount.
                </p>
            </div>
        </div>

    </body>
</html>
