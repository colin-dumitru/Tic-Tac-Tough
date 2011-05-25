/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function getTopUsers(){
    $.ajax({
        type: "GET",
        url: "/rss/top_users.xml",
        data: ({
            
        }),
        dataType: "xml",
        success: parseTopUsers
    });
}

function getTopTests(){
    $.ajax({
        type: "GET",
        url: "/rss/top_tests.xml",
        data: ({
            
        }),
        dataType: "xml",
        success: parseTopTests
    });
}

function parseTopUsers(xmlData) {
    $("#topUsersResult").html("");
    
    $("#topUsersResult").append(
        "<div class='topLabel' >Our top 10 users (highest score) are:</div>"
    );
        
    $(xmlData).find("title").each(function()
    {
        $("#topUsersResult").append();
        
        $("#topUsersResult").append(
        "<div class='contentItem'>\
            <div class='contentText'>" + 
                this.textContent +
            "</div>" +
        "</div>");
    });
}

function parseTopTests(xmlData) {
    $("#topTestsResult").html("");
    
    $("#topTestsResult").append(
        "<div class='topLabel' >Our top 10 tests (most accessed) are:</div>"
    );
        
    $(xmlData).find("title").each(function()
    {
        $("#topTestsResult").append();
        
        $("#topTestsResult").append(
        "<div class='contentItem'>\
            <div class='contentText'>" + 
                this.textContent +
            "</div>" +
        "</div>");
    });
}