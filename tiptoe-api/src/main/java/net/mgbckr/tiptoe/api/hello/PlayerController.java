package net.mgbckr.tiptoe.api.hello;

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
	
	public static class Play {
		
		String songId;
		
		public String getSongId() {
			return songId;
		}
		
		public void setSongId(String songId) {
			this.songId = songId;
		}
		
	}
	
	@MessageMapping("/player/play")
    @SendTo("/topic/player")
    public void play(Play play) throws Exception {
		InputStream song = this.library.loadSong(play.getSongId());
		this.player.load(song);
		this.player.play();
    }
}