package net.mgbckr.tiptoe.api.frontend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import net.mgbckr.tiptoe.api.model.Response;

@Controller
public class FrontendController {

	private Map<String, Object> properties = new HashMap<>();
	
	@MessageMapping("/frontend")
    @SendTo("/topic/frontend")
    public synchronized Response load(Map<String, Object> properties) {
		System.out.println("TEST");
		this.properties.putAll(properties);
		return new Response("properties", this.properties);
    }
	
}
