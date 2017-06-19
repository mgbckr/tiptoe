package net.mgbckr.tiptoe.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.mgbckr.tiptoe.library.Library;
import net.mgbckr.tiptoe.library.folder.FilesystemLibrary;
import net.mgbckr.tiptoe.player.Player;
import net.mgbckr.tiptoe.player.beads.BeadsPlayer;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public Player getPlayer() {
    	return new BeadsPlayer();
    }
    
    @Bean
    public Library getLibrary() {
    	return new FilesystemLibrary("src/main/resources/samples");
    }
    
}
