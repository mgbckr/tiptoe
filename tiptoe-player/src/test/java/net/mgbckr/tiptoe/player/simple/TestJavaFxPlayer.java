package net.mgbckr.tiptoe.player.simple;

import java.io.FileInputStream;
import java.io.IOException;

import net.mgbckr.tiptoe.player.Player;
import net.mgbckr.tiptoe.player.javafx.JavaFxPlayer;

public class TestJavaFxPlayer {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		Player player = new JavaFxPlayer();
		player.load(new FileInputStream("src/test/resources/samples/celebrity.mp3"));
		player.play();
		Thread.sleep(4000);
		
		player.pause();
		Thread.sleep(2000);
		player.play();
		Thread.sleep(3000);
		player.play();
		Thread.sleep(3000);
		player.stop();
	}
}
