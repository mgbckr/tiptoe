package net.mgbckr.tiptoe.player.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

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
	public void load(InputStream stream) throws IOException {
		
	    byte[] buffer = new byte[stream.available()];
	    stream.read(buffer);
	 
	    this.file = File.createTempFile("tmp-song", UUID.randomUUID().toString());
	    OutputStream outStream = new FileOutputStream(this.file);
	    outStream.write(buffer);
	    outStream.close();
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
	public void stop() {
		player.stop();
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

}
