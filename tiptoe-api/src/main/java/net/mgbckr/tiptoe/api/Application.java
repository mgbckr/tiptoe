package net.mgbckr.tiptoe.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.mgbckr.tiptoe.player.Player;
import net.mgbckr.tiptoe.player.simple.SimplePlayer;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public Player getPlayer() {
    	return new SimplePlayer();
    }
    
}
