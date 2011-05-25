/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function runTestSearch(url,query){
    $.ajax({
        type: "GET",
        url: "/web/searchTest/",
        data: ({
            querry : query
        }),
        dataType: "xml",
        success: parseTestResult
    });
    
}

function goToTest(testId){    
    window.location = "/web/goToTest/" + testId;
}

function deleteTest(testId) {
    window.location = "/web/deleteTest/" + testId;
}

function parseTestResult(xmlData){
    $("#testSearchResult").html("");
    
    $(xmlData).find("test").each(function()
    {        
        $("#testSearchResult").append(
        "<div class='contentItem'>\
            <div class='contentText'>" + 
                $(this).find("name").attr("value") +
            "</div>" +
            "<button class='goToTestButton' onclick='goToTest("
                + $(xmlData).find("id").attr("value") + 
                ")' value='add'/>" +
        "</div>");
    });
}


