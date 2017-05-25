package net.mgbckr.tiptoe.api.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import net.mgbckr.tiptoe.library.Library;
import net.mgbckr.tiptoe.library.Songs;

@Controller
public class LibraryController {

	@Autowired
	private Library library;
	
	@MessageMapping("/songs")
    @SendTo("/topic/library")
    public Songs songs() throws Exception {
    	return this.library.getSongs(0, 10);
    }

}