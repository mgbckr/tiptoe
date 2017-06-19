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

    	initLibrary()
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function initLibrary() {
	stompClient.send("/app/library", {}, "{}");
}

function library(message) {	

	function page(page) {

		$("#library-folders tbody").html("");
		$.each(page.subpages, function(k, page) {
			var row = $('<tr><td>' + page.name + '</td></tr>')
			row.on('click', function() {
			    stompClient.send("/app/library", {}, JSON.stringify({ 'id': page.id }));
			})
			$("#library-folders tbody").append(row);
		});
		
		$("#library-songs tbody").html("");
		$.each(page.songs, function(k, song) {
			
			var row = $(
				'<tr class="song" data-id="' + song.id + '">' +
					'<td>' + song.title + '</td>' +
					'<td>' + song.artist + '</td>' +
					'<td>' + song.album + '</td>' +
					'<td class="load">Load</td>' +
				'</tr>')
			
			row.find(".load").on("click", function() {
				
			    stompClient.send(
			    		"/app/player/load", 
			    		{}, 
			    		JSON.stringify({'songId': song.id}));
			})
			
			$("#library-songs tbody").append(row);
		});
	}

	console.log("Received library message:")
	console.log(message);
	
	if (!message.type.localeCompare('page')) {
		console.log('Recognized "page" message.')
		page(message.content)
	} else {
		console.log("Unkown message.")
	}
}

function play() {
	console.log("play")
    stompClient.send(
    		"/app/player/play");
}

function stop() {
	console.log("stop")
    stompClient.send(
    		"/app/player/stop");
}

function pause() {
	console.log("pause")
    stompClient.send(
    		"/app/player/pause");
}

function player(message) {
	
	function loaded(info) {
		console.log('loaded')
	}
	
	console.log("Received player message:")
	console.log(message);
	
	if (!message.type.localeCompare('loaded')) {
		console.log('Recognized "loaded" message.')
		loaded(message.content)
	} else {
		console.log("Unkown message.")
	}
}

$(function () {
	connect()
    $( "#play" ).click(function() { play(); })
    $( "#pause" ).click(function() { pause(); })
    $( "#stop" ).click(function() { stop(); })
});