/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function runQuestionSearch(url, query) {
    $.ajax({
        type: "GET",
        url: "/web/searchQuestion/",
        data: ({
            querry : query,
            testId : testId
        }),
        dataType: "xml",
        success: parseQuestionResult
    });
}

function addQuestionToTest(testId, questionId) {
    $.ajax({
        type: "GET",
        url: "/web/addQuestionToTest/" + testId + "/" + questionId,
        dataType: "txt"
    });
}

function parseQuestionResult(xmlData) {
    $("#questionSearchResult").html("");
    
    $(xmlData).find("question").each(function()
    {
        $("#questionSearchResult").append();
        
        $("#questionSearchResult").append(
        "<div class='contentItem'>\
            <div class='contentText'>" + 
                $(this).find("content").attr("value") +
            "</div>" +
            "<button class='addToTestButton' onclick='addQuestionToTest(" + testId + "," 
                    + $(this).find("id").attr("value") +")' value='add'/>" +
        "</div>");
    });
}

