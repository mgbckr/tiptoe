package net.mgbckr.tiptoe.api.player;

import java.io.InputStream;

import javax.naming.OperationNotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import net.mgbckr.tiptoe.api.model.Response;
import net.mgbckr.tiptoe.library.Library;
import net.mgbckr.tiptoe.player.Interactive.Action;
import net.mgbckr.tiptoe.player.InteractivePlayer;

@Controller
public class PlayerController {

	@Autowired
	private InteractivePlayer<?,?> player;
	
	@Autowired
	private Library library;
	
	@MessageMapping("/player/info")
    @SendTo("/topic/player")
    public Response info() throws Exception {
		return new Response("info", this.player.getPlayerStatus());
    }
	
	@MessageMapping("/player/load")
    @SendTo("/topic/player")
    public Response load(LoadCommand load) throws Exception {
		InputStream song = this.library.loadSong(load.getSongId());
		Object songInfo = this.player.load(song);
		return new Response("loaded", songInfo);
    }
	
	@MessageMapping("/player/play")
    @SendTo("/topic/player")
    public Response play() throws Exception {
		this.player.play();
		return new Response("playing", null);
    }
	
	@MessageMapping("/player/pause")
    @SendTo("/topic/player")
    public Response pause() throws Exception {
		this.player.pause();
		return new Response("paused", null);
    }
	
	@MessageMapping("/player/stop")
    @SendTo("/topic/player")
    public Response stop() throws Exception {
		this.player.stop();
		return new Response("stopped", null);
    }
	
	@MessageMapping("/player/action")
    @SendTo("/topic/player")
    public Response action(Action action) throws Exception {
		try {
			Object response = this.player.request(action);
			return new Response(action.getType(), response);
		} catch (OperationNotSupportedException e) {
			return new Response(action.getType(), false);
		}
    }
}