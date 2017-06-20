package net.mgbckr.tiptoe.api.player;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import net.mgbckr.tiptoe.api.model.Message;
import net.mgbckr.tiptoe.library.Library;
import net.mgbckr.tiptoe.player.Player;

@Controller
public class PlayerController {

	@Autowired
	private Player player;
	
	@Autowired
	private Library library;
	
	@MessageMapping("/player/load")
    @SendTo("/topic/player")
    public Message load(LoadCommand load) throws Exception {
		
		InputStream song = this.library.loadSong(load.getSongId());
		this.player.load(song);
		
		return new Message("loaded", this.player.getSongInfo());
    }
	
	@MessageMapping("/player/play")
    @SendTo("/topic/player")
    public Message play(PlayCommand play) throws Exception {
		
		if (play.getStartTime() >= 0) {
			this.player.play(play.getStartTime());
		} else {
			this.player.play();
		}
		
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
}