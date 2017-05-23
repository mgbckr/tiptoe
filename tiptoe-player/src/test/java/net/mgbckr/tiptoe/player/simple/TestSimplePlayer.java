package net.mgbckr.tiptoe.player.simple;

import java.io.File;
import java.io.FileNotFoundException;

public class TestSimplePlayer {
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		
		SimplePlayer player = new SimplePlayer();
		player.load(new File("src/test/resources/samples/celebrity.mp3"));
		player.play();
		Thread.sleep(4000);
		
		player.pause();
		Thread.sleep(2000);
		player.resume();
		Thread.sleep(3000);
		player.stop();
	}
}
