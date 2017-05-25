package net.mgbckr.tiptoe.library.folder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.mgbckr.tiptoe.library.Library;
import net.mgbckr.tiptoe.library.Song;
import net.mgbckr.tiptoe.library.Songs;

public class FolderLibrary implements Library {

	private String baseFolder;
	
	public FolderLibrary(String baseFolder) {
		this.baseFolder = baseFolder;
	}
	
	@Override
	public Song getSong(String id) {
		
		// TODO: should try to read mp3 tags
		
		File f = new File(id);
		Song s = new Song(f.getAbsolutePath());
		s.setTitle(f.getName());
		s.setAlbum(f.getParentFile().getName());
		
		return s;
	}

	@Override
	public InputStream loadSong(String id) throws IOException {
		return new FileInputStream(new File(id));
	}

	@Override
	public Songs getSongs(int offset, int limit) {
		
		Songs songs = new Songs();
		
		File folder = new File(this.baseFolder);
		for (File f : folder.listFiles()) {
			Song s = this.getSong(f.getAbsolutePath());
			songs.getList().add(s);
		}
		
		return songs;
	}


}
