package net.mgbckr.tiptoe.api.hello;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import net.mgbckr.tiptoe.player.Player;

@Controller
public class GreetingController {

	@Autowired
	private Player player;
	
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
    	
    	switch (message.getName()) {
		case "play":
        	this.player.load(new File("/media/data/content/documents/phd/workspaces/eclipse_music/tiptoe/tiptoe-player/src/main/resources/samples/celebrity.mp3"));
        	this.player.play();
			break;
		case "stop":
        	this.player.stop();
			break;
		case "pause":
        	this.player.pause();
			break;
		case "resume":
        	this.player.resume();
			break;

		default:
			break;
		}
    	
        return new Greeting("Hello, " + message.getName() + "!");
    }

}