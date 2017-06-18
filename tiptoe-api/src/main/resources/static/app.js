var stompClient = null;

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
    	
        console.log('Connected: ' + frame);
        
        stompClient.subscribe('/topic/library', function (response) {
        	library(JSON.parse(response.body));
        });
        

        stompClient.subscribe('/topic/player', function (response) {
        	player(JSON.parse(response.body));
        });

    	getSongs()
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function getSongs() {
    stompClient.send("/app/library", {}, "{}");
}

function play() {
    stompClient.send(
    		"/app/player/play", 
    		{}, 
    		JSON.stringify({'songId': $("#name").val()}));
}

function stop() {
	console.log("stop")
    stompClient.send(
    		"/app/player/stop", 
    		{});
}

function library(message) {	
	console.log(message);
	
	$("#library-folders tbody").html("");
	$.each(message.subpages, function(k, page) {
		var row = $('<tr><td>' + page.name + '</td></tr>')
		row.on('click', function() {
		    stompClient.send("/app/library", {}, JSON.stringify({ 'id': page.id }));
		})
		$("#library-folders tbody").append(row);
	});
	
	$("#library-songs tbody").html("");
	$.each(message.songs, function(k, song) {
		
		var row = $(
			'<tr class="song" data-id="' + song.id + '">' +
				'<td>' + song.title + '</td>' +
				'<td>' + song.artist + '</td>' +
				'<td>' + song.album + '</td>' +
				'<td class="play">Play</td>' +
			'</tr>')
		
		row.find(".play").on("click", function() {

		    stompClient.send(
		    		"/app/player/play", 
		    		{}, 
		    		JSON.stringify({'songId': song.id}));
		})
		
		$("#library-songs tbody").append(row);
	});
}

$(function () {
	connect()
    $( "#stop" ).click(function() { stop(); });
});