package net.mgbckr.tiptoe.player;

import java.io.File;

public interface Player {

	public void load(File file);
	public void play();
	public void stop();
	public void pause();
	public void resume();
	
}
