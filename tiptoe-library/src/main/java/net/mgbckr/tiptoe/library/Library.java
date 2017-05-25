package net.mgbckr.tiptoe.library;

import java.io.IOException;
import java.io.InputStream;

public interface Library {

	public Song getSong(String id);
	public InputStream loadSong(String id) throws IOException;
	
	public Songs getSongs(int offset, int limit);
}
