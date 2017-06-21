package net.mgbckr.tiptoe.api.player;

import java.io.InputStream;

import javax.naming.OperationNotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import net.mgbckr.tiptoe.api.model.Message;
import net.mgbckr.tiptoe.library.Library;
import net.mgbckr.tiptoe.player.Interactive;
import net.mgbckr.tiptoe.player.InteractivePlayer;

@Controller
public class PlayerController {

	@Autowired
	private InteractivePlayer<?,?> player;
	
	@Autowired
	private Library library;
	
	@MessageMapping("/player/info")
    @SendTo("/topic/player")
    public Message info() throws Exception {
		return new Message("status", this.player.getPlayerInfo());
    }
	
	@MessageMapping("/player/load")
    @SendTo("/topic/player")
    public Message load(LoadCommand load) throws Exception {
		InputStream song = this.library.loadSong(load.getSongId());
		Object songInfo = this.player.load(song);
		return new Message("loaded", songInfo);
    }
	
	@MessageMapping("/player/play")
    @SendTo("/topic/player")
    public Message play() throws Exception {
		this.player.play();
		return new Message("playing", null);
    }
	
	@MessageMapping("/player/pause")
    @SendTo("/topic/player")
    public Message pause() throws Exception {
		this.player.pause();
		return new Message("paused", null);
    }
	
	@MessageMapping("/player/stop")
    @SendTo("/topic/player")
    public Message stop() throws Exception {
		this.player.stop();
		return new Message("stopped", null);
    }
	
	@MessageMapping("/player/message")
    @SendTo("/topic/player")
    public Message command(Interactive.Message message) throws Exception {
		try {
			Interactive.Message response = this.player.sendMessage(message);
			return new Message("command", response);
		} catch (OperationNotSupportedException e) {
			return new Message("command", "fail");
		}
    }
}