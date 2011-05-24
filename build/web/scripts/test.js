/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var totalTime = 40.0;
var remainingTime = 40.0;
var context ;

function initialize() {
    context  = document.getElementById("timerCanvas").getContext("2d");
    
    /*creeam timerul pentru functia de update*/
    update();
}

function update() {
    /*facem update la timpul ramas*/
    updateTime();
    /*afisam timpul ramas*/
    drawTime();
    
    if(remainingTime <= 0) {
        advanceQuestion(0);
    }
    
    setTimeout("update()",1000);
}

function advanceQuestion(answer) {
     $.ajax({
        type: "GET",
        url: "/web/advanceTest/" + answer,
        dataType: "xml",
        success: parseQuestion
    });
}

function parseQuestion(XmlData) {
    
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