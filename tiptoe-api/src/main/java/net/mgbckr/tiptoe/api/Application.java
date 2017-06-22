package net.mgbckr.tiptoe.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import net.mgbckr.tiptoe.api.model.Response;
import net.mgbckr.tiptoe.library.Library;
import net.mgbckr.tiptoe.library.folder.FilesystemLibrary;
import net.mgbckr.tiptoe.player.InteractivePlayer;
import net.mgbckr.tiptoe.player.Observable.Event;
import net.mgbckr.tiptoe.player.Observable.EventListener;
import net.mgbckr.tiptoe.player.beads.InteractiveBeadsPlayer;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Bean
    public InteractivePlayer<?, ?> getPlayer() {
    	InteractivePlayer<?, ?> player = new InteractiveBeadsPlayer();
    	player.addEventListener(new EventListener() {
			@Override
			public void notify(Event e) {
				messagingTemplate.convertAndSend("/topic/player", new Response("event", e));
			}
		});
    	return player;
    }
    
    @Bean
    public Library getLibrary() {
    	return new FilesystemLibrary("src/main/resources/samples");
    }
    
}
