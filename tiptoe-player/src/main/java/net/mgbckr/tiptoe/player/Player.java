package net.mgbckr.tiptoe.player;

import java.io.IOException;
import java.io.InputStream;

public interface Player<TSongInfo, TPlayerStatus> extends Observable {
	
	TSongInfo load(InputStream in) throws IOException;
	
	void play();
	void pause();
	void stop();
	
	default TPlayerStatus getPlayerStatus() {
		return null;
	};
	
}
