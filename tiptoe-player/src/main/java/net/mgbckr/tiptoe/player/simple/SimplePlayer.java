package net.mgbckr.tiptoe.player.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import net.mgbckr.tiptoe.player.Player;
import net.mgbckr.tiptoe.player.jlayer.PausablePlayer;

public class SimplePlayer implements Player {

	private File file;
	private PausablePlayer player = null;

	private void initPlayer() {
		try {
			player = new PausablePlayer(new FileInputStream(this.file));
		} catch (JavaLayerException | FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void load(File file) {
		this.file = file;
	}
	
	@Override
	public void play() {
		
		if (player != null)
			player.close();
		
		initPlayer();
		
		try {
			player.play();
		} catch (JavaLayerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void pause() {
		player.pause();				
	}

	@Override
	public void resume() {
		player.resume();
	}
	
	@Override
	public void stop() {
		player.stop();
	}

}
