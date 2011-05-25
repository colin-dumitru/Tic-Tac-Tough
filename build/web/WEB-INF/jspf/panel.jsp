<%-- any content can be specified here e.g.: --%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<LINK rel="stylesheet" href="/css/panel.css" type="text/css" />

<script type="text/javascript">
    function preload() {
        img = new Image(); img.src = "/images/home_button_hover.png";
        img = new Image(); img.src = "/images/edit_tests_button_hover.png";
        img = new Image(); img.src = "/images/take_test_button_hover.png";
        img = new Image(); img.src = "/images/add_button_hover.png";
        img = new Image(); img.src = "/images/top_question_hover.png";
    }
</script>

<%-- Bannerul de sus impreuna cu numele s linkul catre logout--%>
<div class="banner" onload="preload();">
    <div class="name">${user.name}(${user.testScore})</div>
    <div class="logout"><a href = "/web/logout">(logout)</a></div>
</div>

<div class = "logo"></div>

<div class="buttonContainer">

    <%-- Cele 5 butoane--%>
    <a href="/web/home"><div class="homeButton"></div> </a>
    <a href="/web/takeTest" ><div class="takeTestButton"></div> </a>
    <a href="/web/top" ><div class="topButton"></div> </a>

    <%-- verificam daca userul are dreptrile necase pentru a afisa cele
         doua butoane pentru utilizatorii privilegiati--%>

    <c:if test="${user.type == 1}" >
        <a href="/web/tests" ><div class="editTestsButton"></div> </a>
        <a href="/web/questions" ><div class="addQuestionsButton"></div> </a>
    </c:if>
</div>