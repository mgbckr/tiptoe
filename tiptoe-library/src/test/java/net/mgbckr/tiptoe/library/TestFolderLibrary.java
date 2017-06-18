package net.mgbckr.tiptoe.library;

import org.junit.Test;

import net.mgbckr.tiptoe.library.folder.FilesystemLibrary;

public class TestFolderLibrary {

	@Test
	public void test() {
		FilesystemLibrary library = new FilesystemLibrary("src/test/resources/library/dummy");
		Page allSongs = library.getAllSongs();
		for (Song s : allSongs.getSongs()) {
			System.out.println(s.getId());
			System.out.println(s.getTitle());
			System.out.println(s.getArtist());
			System.out.println(s.getAlbum());
		}
	}
	
}
