package net.mgbckr.tiptoe.api.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import net.mgbckr.tiptoe.library.Library;
import net.mgbckr.tiptoe.library.Page;

@Controller
public class LibraryController {

	@Autowired
	private Library library;
	
	@MessageMapping("/library")
    @SendTo("/topic/library")
    public Page getPage(PageCommand pageCommand) throws Exception {
		if (pageCommand == null) {
			return this.library.getPage(null);
		} else {
			return this.library.getPage(pageCommand.getId());
		}
    }

}