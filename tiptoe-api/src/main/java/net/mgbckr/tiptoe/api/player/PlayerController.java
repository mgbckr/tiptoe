package net.mgbckr.tiptoe.api.player;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import net.mgbckr.tiptoe.library.Library;
import net.mgbckr.tiptoe.player.Player;

@Controller
public class PlayerController {

	@Autowired
	private Player player;
	
	@Autowired
	private Library library;
	
	@MessageMapping("/player/play")
    @SendTo("/topic/player")
    public void play(PlayCommand play) throws Exception {
		InputStream song = this.library.loadSong(play.getSongId());
		this.player.load(song);
		this.player.play();
    }
	
	@MessageMapping("/player/stop")
    @SendTo("/topic/player")
    public void stop() throws Exception {
		this.player.stop();
    }
}