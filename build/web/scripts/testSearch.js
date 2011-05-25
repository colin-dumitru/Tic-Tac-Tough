/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function runTestSearch(url,query){
    $.ajax({
        type: "GET",
        url: "/web/searchTest/",
        data: ({
            querry : query,
            categoryId: $("#categoryId").val()
        }),
        dataType: "xml",
        success: parseTestResult
    });
    
    $('#testSearchResult').animate({
    height: $('#testSearchResult').height()}, 0);
    
}

function goToTest(testId){    
    window.location = "/web/goToTest/" + testId;
}

function deleteTest(testId) {
    window.location = "/web/deleteTest/" + testId;
}

function parseTestResult(xmlData){
    $("#testSearchResult").html("");
    var height = 20;
    
    $(xmlData).find("test").each(function()
    {        
        $("#testSearchResult").append(
        "<div class='contentItem' id='item" + $(this).find("id").attr("value") + "'>\
            <div class='contentText'>" + 
                $(this).find("name").attr("value") +
            "</div>" +
            "<button class='goToTestButton' onclick='goToTest("
                + $(this).find("id").attr("value") + 
                ")' value='add'/>" +
        "</div>");
        
        height += $("#item" + $(xmlData).find("id").attr("value")).height() + 10;
    });
    
     $('#testSearchResult').animate({height: height}, 500);
}


