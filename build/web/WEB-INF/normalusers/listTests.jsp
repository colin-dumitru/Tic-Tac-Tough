<%-- 
    Document   : takeTest
    Created on : May 20, 2011, 8:08:51 PM
    Author     : bkt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<LINK rel="stylesheet" href="/css/listTests.css" type="text/css" />
<LINK rel="stylesheet" href="/css/common.css" type="text/css" />

<script type="text/javascript" src="/scripts/jquery.js"></script>
<script type="text/javascript" src="/scripts/testSearch.js"></script>


<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/jspf/panel.jsp"></jsp:include>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Take a test!</title>
    </head>
    <body>
        <%-- ---------------------------------------------------------------------------------- --%>
        <%-- ----------------------Search Test--------------------------------------------- --%>
        <%-- ---------------------------------------------------------------------------------- --%>
        <div class="searchTest">
            <div class="searchForm">
                <div class="searchLabel">Content:</div>
                <textarea id="searchInput" class="searchInput"> </textarea>
                <button class="searchButton"
                    onclick="runTestSearch('/web/searchTest', document.getElementById('searchInput').value);" />
            
            </div>
            
            <div class="content" style="top:200px;" id="testSearchResult">
                 <div class = "contentItem">
                    <div class = "contentText" >No search results...</div>
                </div>
            </div>
        </div>
    </body>
</html>
