/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var totalTime = 10.0;
var remainingTime = 10.0;
var context ;
var working = false;
var finished = false;

function initialize() {
    context  = document.getElementById("timerCanvas").getContext("2d");
    
    /*luam prima intrebare din test*/
    advanceQuestion(0);
    
    /*creeam timerul pentru functia de update*/
    update();
}

function update() {
    if(working) {
        /*facem update la timpul ramas*/
        updateTime();
        /*afisam timpul ramas*/
        drawTime();


        if(remainingTime <= 0) {
            advanceQuestion(0);
        }
    }
    
    if(!finished)
        setTimeout("update()",1000);
}

function advanceQuestion(answer) {
     $.ajax({
        type: "GET",
        url: "/web/advanceTest/" + answer,
        dataType: "xml",
        success: parseQuestion
    });
    
    working = false;
}

function parseQuestion(XmlData) {
    var question = $(XmlData).find("question");
    var response = $(XmlData).find("response");
    var result = $(XmlData).find("result");
    
    if(question.length != 0) {
        updateQuestion(question[0])
    }
    if(response.length != 0) {
        updateResponse(response);
    }
    if(result.length != 0) {
        updateResult(result);
    }
    
    working = true;
}

function updateQuestion(question) {
    /*continutul*/
    $("#testContentText").html($(question).find("content")[0].getAttribute("value"));
    
    /*raspunsurile*/
    $("#answer1Button").html($(question).find("answer1")[0].getAttribute("value"));
    $("#answer2Button").html($(question).find("answer2")[0].getAttribute("value"));
    $("#answer3Button").html($(question).find("answer3")[0].getAttribute("value"));
    $("#answer4Button").html($(question).find("answer4")[0].getAttribute("value"));
    
    $("#result").visible = false;
    
}

function updateResponse(response) {
    /*facem update la timpul intrebarii*/
    totalTime = $(response).find("timePerQuestion")[0].getAttribute("value");    
    resetTimer();
    
    /*plus la textul ce afiseaza nuamrul de intrebaril raspunse / numarul total de intrebari*/
    $("#totalQuestions").html(
        $(response).find("answered")[0].getAttribute("value") + " / " +
            $(response).find("total")[0].getAttribute("value")
    );
}

function resetTimer(){
    remainingTime = totalTime;
}

function updateResult(result) {
    /*setam scorul primit*/
    $("#score").html($(result).find("score")[0].getAttribute("value"));
    /*afisam overlayout*/
     $("#result").slideDown("slow");    
     
     /*oprim timerul*/
     finished = true;
}

function updateTime() {
    remainingTime -= 1.0;
    
    if(remainingTime < 0.0)
        remainingTime = 0.0;
}

function drawTime() {
    document.getElementById("timerText").innerHTML = remainingTime;
    context.fillStyle = "rgb(" + (255 - Math.floor(255.0 * (remainingTime / totalTime))) + "," +
        Math.floor(255.0 * (remainingTime / totalTime)) + ",0)";
    context.beginPath();
    context.arc(35, 35, 34, 0, 2 * Math.PI, false);
    context.fill();
}