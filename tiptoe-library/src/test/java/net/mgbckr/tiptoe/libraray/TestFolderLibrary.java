package net.mgbckr.tiptoe.libraray;

import org.junit.Test;

import net.mgbckr.tiptoe.library.Song;
import net.mgbckr.tiptoe.library.Songs;
import net.mgbckr.tiptoe.library.folder.FolderLibrary;

public class TestFolderLibrary {

	@Test
	public void test() {
		FolderLibrary l = new FolderLibrary("src/test/resources/library/dummy");
		Songs songs = l.getSongs(0, 10);
		for (Song s : songs.getList()) {
			System.out.println(s.getId());
			System.out.println(s.getTitle());
			System.out.println(s.getArtist());
			System.out.println(s.getAlbum());
		}
	}
	
}
