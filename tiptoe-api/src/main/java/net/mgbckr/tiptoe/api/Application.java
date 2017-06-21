package net.mgbckr.tiptoe.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.mgbckr.tiptoe.library.Library;
import net.mgbckr.tiptoe.library.folder.FilesystemLibrary;
import net.mgbckr.tiptoe.player.InteractivePlayer;
import net.mgbckr.tiptoe.player.beads.InteractiveBeadsPlayer;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public InteractivePlayer<?, ?> getPlayer() {
    	return new InteractiveBeadsPlayer();
    }
    
    @Bean
    public Library getLibrary() {
    	return new FilesystemLibrary("src/main/resources/samples");
    }
    
}
