package net.mgbckr.tiptoe.player.simple;

import java.io.FileInputStream;
import java.io.IOException;

import net.mgbckr.tiptoe.player.Player;
import net.mgbckr.tiptoe.player.beads.BeadsPlayer;

public class TestBeadsPlayer {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		Player player = new BeadsPlayer();
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
