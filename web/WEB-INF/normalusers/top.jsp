<%-- 
    Document   : top
    Created on : May 25, 2011, 11:26:39 AM
    Author     : bkt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<LINK rel="stylesheet" href="/css/top.css" type="text/css" />
<LINK rel="stylesheet" href="/css/common.css" type="text/css" />

<script type="text/javascript" src="/scripts/jquery.js"></script>
<script type="text/javascript" src="/scripts/top.js" ></script>

<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/jspf/panel.jsp"></jsp:include>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Top</title>
    </head>
    <body onload="getTopUsers();getTopTests();">

        <div class="content" id="topUsersResult">
            <div class="topLabel" >Our top 10 users (highest score) are:</div>
            <div class="contentItem" >
                <div class="contentText" >
                    Processing..
                </div>
            </div>
        </div>

        <div class="content" style="margin-top: 10px" id="topTestsResult">
            <div class="topLabel">Our top 10 tests (most accessed) are:</div>
            <div class="contentItem" >
                <div class="contentText" >
                    Processing..
                </div>
            </div>
        </div>
    </body>
</html>
