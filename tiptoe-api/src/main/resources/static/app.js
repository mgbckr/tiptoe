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

        stompClient.send("/app/player/status");
    	stompClient.send("/app/library", {}, "{}");
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
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
					'<td class="title">' + song.title + '</td>' +
					'<td class="artist">' + song.artist + '</td>' +
					'<td class="album">' + song.album + '</td>' +
					'<td class="controls"><span class="add glyphicon glyphicon-list"></span> <span class="load glyphicon glyphicon-play-circle"></span></td>' +
				'</tr>')
			
			row.find(".add").on("click", function() {
				var playlistEntry = $(
					'<tr class="song" data-id="' + song.id + '">' +
						'<td class="title">' + song.title + '</td>' +
						'<td class="artist">' + song.artist + '</td>' +
						'<td class="album">' + song.album + '</td>' +
						'<td class="controls"><span class="load glyphicon glyphicon-play-circle"></span></td>' +
					'</tr>')
				playlistEntry.find(".load").on("click", function() {
					load(song.id)
				})
				$("#playlist-songs").append(playlistEntry)
			})
			row.find(".load").on("click", function() {
				load(song.id)
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

function load(songId) {
    stompClient.send(
		"/app/player/load", 
		{}, 
		JSON.stringify({'songId': songId}));
}

function play() {
	console.log("play")
    stompClient.send(
    		"/app/player/play", {}, JSON.stringify({}));
}

function stop() {
	console.log("stop")
	d3.select(".focus").attr("transform", 'translate(' + x(0) + ',0)')
    stompClient.send(
    		"/app/player/stop");
}

function pause() {
	console.log("pause")
    stompClient.send(
    		"/app/player/pause");
}

var x = function() { return 0; };
var y = function() { return 0; };
function plot(length, wave) {
	
	formatMinutes = function(d) { 
		d = d / 1000
	    var hours = Math.floor(d / 3600),
	        minutes = Math.floor((d - (hours * 3600)) / 60),
	        seconds = d - (minutes * 60);
	    var output = ("0" + seconds).slice(-2); + '';
	    output = minutes + ':' + output;
	    if (hours) {
	        output = hours + ':' + output;
	    }
	    return output;
	};
	
	// set the dimensions and margins of the graph
	var margin = {top: 20, right: 20, bottom: 60, left: 20},
	    width = 960 - margin.left - margin.right,
	    height = 30;

	// set the ranges
	x = d3.scaleLinear().range([0, width]);
	y = d3.scaleLinear().range([height, 0]);

	// define the line
	var valueline = d3.line()
	    .x(function(d) { return x(d.time); })
	    .y(function(d) { return y(d.value); });

	// append the svg obgect to the body of the page
	// appends a 'group' element to 'svg'
	// moves the 'group' element to the top left margin
	$('#wave').html('')
	var svg = d3.select("#wave").append("svg")
	    .attr("width", width + margin.left + margin.right)
	    .attr("height", height + margin.top + margin.bottom)
	  .append("g")
	    .attr("transform",
	          "translate(" + margin.left + "," + margin.top + ")");

	// Get the data

	// format the data
	var interval = length / wave.length
	var data = wave.map(function(waveValue, index) {
		return {
			time: index * interval,
			value: Math.abs(waveValue)
		}
	});

	// Scale the range of the data
	x.domain(d3.extent(data, function(d) { return d.time; }));
	y.domain(d3.extent(data, function(d) { return d.value; }));

	// Add the valueline path.
	svg.append("path")
	    .data([data])
	    .attr("class", "line")
	    .attr("d", valueline)
	    .on("click", function() {
	    	
			var coordinates = d3.mouse(this)
			console.log(coordinates)
			
			var xValue = x.invert(coordinates[0])
			console.log(xValue)
			
			setPosition(xValue)
		});;

	// Add the X Axis
	svg.append("g")
	    .attr("transform", "translate(0," + height + ")")
	    .call(d3.axisBottom(x)
	    		  .tickFormat(formatMinutes));

	// focus
	var focus = svg.append("line")
    	.attr("class", "focus")
		.attr("x1", x(0))  //<<== change your code here
		.attr("y1", 0)
		.attr("x2", x(0))  //<<== and here
		.attr("y2", height)
		.style("stroke-width", 2)
		.style("stroke", "red")
		.style("fill", "none");
	
	// cue dragging
	var drag = d3.drag()
	  .on('start', dragstarted)
	  .on('drag', dragged)
	  .on('end', dragended);
	
	function dragstarted() {
		
	}
	
	function dragged() {
	    var xDiff = d3.event.dx;
	    console.log(xDiff)
	    
	    var cue = d3.select(this);
	    console.log(cue)
	    console.log(cue.attr('x'))
	    
	    
	    var newX = parseInt(cue.attr('x')) + xDiff;
	    var newTime = x.invert(newX)
	    cue.attr('x', newX);
	    cue
		    .on('click', function() {
				console.log("clicked: " + newTime)
				setPosition(newTime)
			})
	}
	
	function dragended() {
	    var yDiff = d3.event.dy;
	    console.log(yDiff)
	}
	
	// cues
	var focus = svg.append("line")
		.attr("class", "cues")
		.attr("x1", x(0))  //<<== change your code here
		.attr("y1", height + 30)
		.attr("x2", x(length))  //<<== and here
		.attr("y2", height + 30)
		.style("stroke-width", 10)
		.style("stroke", "red")
		.style("fill", "none")
		.on('click', function() {
			var coordinates = d3.mouse(this)
			console.log(coordinates)
			var xValue = x.invert(coordinates[0])
			console.log(xValue)
			svg.append("rect")
				.attr("x", coordinates[0])
				.attr("y", height + 30)
				.attr('width', 20)
				.attr('height', 20)
				.attr('class', 'cue')
				.attr('fill', 'green')
				.on('click', function() {
					console.log("clicked: " + xValue)
					setPosition(xValue)
				})
				.call(drag)
		});
	
}

function player(response) {
	
	console.log("Received player message:")
	console.log(response);
	
	if (!response.type.localeCompare('status')) {
		console.log('Recognized "status" message.')
		initPlayer(response.content)
	} else if (!response.type.localeCompare('loaded')) {
		console.log('Recognized "loaded" message.')
		loaded(response.content)
	} else if (!response.type.localeCompare('setPitch')) {
		console.log('Recognized "pitchChanged" message.')
//		pitchChanged(response.content)
	} else if (!response.type.localeCompare('setSpeed')) {
		console.log('Recognized "speedChanged" message.')
//		speedChanged(response.content)
	} else if (!response.type.localeCompare('setPosition')) {
		console.log('Recognized "positionChanged" message.')
//		positionChanged(response.content)
	} else if (!response.type.localeCompare('setLooping')) {
		console.log('Recognized "setLooping" message.')
		loopingChanged(response)
	}else if (!response.type.localeCompare('event')) {
		console.log('Recognized "event" message.')
		handleEvent(response.content)
	} else {
		console.log("Unkown response.")
	}
}

function initPlayer(status) {
	if (status) {
		loaded(status.song)
	}
}

function loaded(song) {
	console.log('loaded')
	plot(song.length, song.wave)
}

function setPosition(milliseconds) {
	console.log("set position")
    stompClient.send(
    		"/app/player/action", {}, JSON.stringify({ 
    			type: 'setPosition',
    			properties: {
    				position: milliseconds
    			}
    		}));
}

function setSpeed(speed) {
	console.log("set speed")
    stompClient.send(
    		"/app/player/action", {}, JSON.stringify({ 
    			type: 'setSpeed',
    			properties: {
    				speed: speed
    			}
    		}));
}

function setPitch(pitch) {
	console.log("set pitch")
    stompClient.send(
    		"/app/player/action", {}, JSON.stringify({ 
    			type: 'setPitch',
    			properties: {
    				pitch: pitch
    			}
    		}));
}

function setLooping(looping) {
	console.log("set looping")
    stompClient.send(
    		"/app/player/action", {}, JSON.stringify({ 
    			type: 'setLooping',
    			properties: {
    				looping: looping
    			}
    		}));
}

function positionChanged(heartbeat) {
	d3.select(".focus").attr("transform", 'translate(' + x(heartbeat.content.position) + ',0)')
}
function speedChanged(heartbeat) {
	if (!speedInteraction)
		$("#speed input").val(heartbeat.content.speed)
}
function pitchChanged(heartbeat) {
	if (!pitchInteraction)
		$("#pitch input").val(heartbeat.content.pitch)
}
function loopingChanged(heartbeat) {
	console.log(heartbeat.content.looping)
	$("#player .controls .loop").toggleClass("active", heartbeat.content.looping)
}

function handleEvent(content) {
	if (!content.type.localeCompare('heartbeat')) {
		console.log(content)
		positionChanged(content)
		speedChanged(content)
		pitchChanged(content)
		loopingChanged(content)
	}
}

pitchInteraction = false
speedInteraction = false
$(function () {
	connect()
    $( "#player .controls .play" ).click(function() { play(); })
    $( "#player .controls .pause" ).click(function() { pause(); })
    $( "#player .controls .stop" ).click(function() { stop(); })
    $( "#player .controls .loop" ).click(function() { 
    	setLooping(!$("#player .controls .loop").hasClass("active"))
    })
    
    // set and reset speed
    $( "#speed span" ).click(function() { 
    	$("#speed input").val("1")
    	console.log("set speed: " + speed)
    	setSpeed(1); 
    })
    $( "#speed input" ).on("mousedown", function() { 
    	speedInteraction = true
    })
    $( "#speed input" ).change(function() { 
    	var speed = $(this).val()
    	console.log("set speed: " + speed)
    	setSpeed(parseFloat(speed)); 
    	speedInteraction = false
    })
    
    // set and reset pitch
    $( "#pitch span" ).click(function() { 
    	$("#pitch input").val("1")
    	console.log("set pitch: " + pitch)
    	setPitch(1); 
    })
    $( "#pitch input" ).on("mousedown", function() { 
    	pitchInteraction = true
    })
    $( "#pitch input" ).on("mouseup", function() { 
    	var pitch = $(this).val()
    	console.log("set pitch: " + pitch)
    	setPitch(parseFloat(pitch)); 
    	pitchInteraction = false
    })
    
    $( "#playlist .controls .clear" ).click(function() { $("#playlist-songs").html("") })
});