package net.mgbckr.tiptoe.player.simple;

import java.io.FileInputStream;
import java.io.IOException;

public class TestSimplePlayer {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		SimplePausablePlayer player = new SimplePausablePlayer();
		player.load(new FileInputStream("src/test/resources/samples/celebrity.mp3"));
		
		player.play();
		Thread.sleep(2000);
		player.pause();
		Thread.sleep(2000);
		player.play();
		Thread.sleep(2000);
		player.stop();
		Thread.sleep(2000);
		player.play();
	}
}
