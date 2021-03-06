package net.mgbckr.tiptoe.player.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.lang.model.type.NullType;

import javazoom.jl.decoder.JavaLayerException;
import net.mgbckr.tiptoe.player.SimplePlayer;
import net.mgbckr.tiptoe.player.jlayer.PausablePlayer;

public class SimplePausablePlayer implements SimplePlayer {

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
	public NullType load(InputStream stream) throws IOException {
		
	    byte[] buffer = new byte[stream.available()];
	    stream.read(buffer);
	 
	    this.file = File.createTempFile("tmp-song", UUID.randomUUID().toString());
	    OutputStream outStream = new FileOutputStream(this.file);
	    outStream.write(buffer);
	    outStream.close();
	    
	    return null;
	}
	
	@Override
	public void play() {
		
		if (this.player != null)
			this.player.close();
		
		initPlayer();
		
		try {
			this.player.play();
		} catch (JavaLayerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void pause() {
		this.player.pause();				
	}

	@Override
	public void stop() {
		this.player.stop();
	}
	


}
