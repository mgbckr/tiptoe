package net.mgbckr.tiptoe.api.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import net.mgbckr.tiptoe.api.model.Response;
import net.mgbckr.tiptoe.library.Library;

@Controller
public class LibraryController {

	@Autowired
	private Library library;
	
	@MessageMapping("/library")
    @SendTo("/topic/library")
    public Response getPage(PageCommand pageCommand) throws Exception {
		if (pageCommand == null) {
			return new Response("page", this.library.getPage(null));
		} else {
			return new Response("page", this.library.getPage(pageCommand.getId()));
		}
    }

}