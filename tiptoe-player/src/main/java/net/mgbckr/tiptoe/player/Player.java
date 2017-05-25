package net.mgbckr.tiptoe.player;

import java.io.IOException;
import java.io.InputStream;

public interface Player {

	void load(InputStream file) throws IOException;
	void play();
	void stop();
	void pause();
	void resume();
	
}
