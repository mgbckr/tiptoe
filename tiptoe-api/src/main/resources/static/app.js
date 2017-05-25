var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/topic/library', function (response) {
        	library(JSON.parse(response.body).content);
        });
        

        stompClient.subscribe('/topic/player', function (response) {
        	player(JSON.parse(response.body).content);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function getSongs() {
    stompClient.send("/app/songs", {});
}

function play() {
    stompClient.send("/app/player/play", {}, JSON.stringify({'songId': $("#name").val()}));
}

function library(message) {
	console.log(message);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { play(); });
    $( "#songs" ).click(function() { getSongs(); });
});