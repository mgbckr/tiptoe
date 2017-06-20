package net.mgbckr.tiptoe.player;

import java.io.IOException;
import java.io.InputStream;

public interface Player {

	void load(InputStream in) throws IOException;
	SongInfo getSongInfo();
	
	void play();
	default void play(double milliseconds) {
		throw new UnsupportedOperationException();
	}

	void stop();
	void pause();
	
	public static class SongInfo {
		
		private Class<? extends Player> playerType;
		
		public SongInfo(Class<? extends Player> playerType) {
			this.playerType = playerType;
		}
		
		public Class<? extends Player> getPlayerType() {
			return this.playerType;
		}
		
	}

	
}
