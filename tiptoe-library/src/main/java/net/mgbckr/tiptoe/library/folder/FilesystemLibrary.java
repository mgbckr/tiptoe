package net.mgbckr.tiptoe.library.folder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.mgbckr.tiptoe.library.Library;
import net.mgbckr.tiptoe.library.Page;
import net.mgbckr.tiptoe.library.Song;

public class FilesystemLibrary implements Library {

	private String baseDirectory;
	
	public FilesystemLibrary(String baseDirectory) {
		this.baseDirectory = new File(baseDirectory).getAbsolutePath();
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

	private void collectSongs(Page page, List<Page> toSearch) {
		for (Page p : toSearch) {
			page.getSongs().addAll(p.getSongs());
			collectSongs(page, p.getSubpages());
		}
	}
	
	@Override
	public Page getAllSongs() {
		Page page = this.getPage(this.baseDirectory);
		collectSongs(page, page.getSubpages());
		return page;
	}

	@Override
	public Page getPage(String id) {
		
		if (id == null) {
			id = this.baseDirectory;
		}
		
		File directory = new File(id);
		Page page = new Page(id, directory.getName());
		if (!id.equals(this.baseDirectory)) {
			page.getSubpages().add(
					new Page(directory.getParentFile().getAbsolutePath(), ".."));
		}
		
		for (File f : directory.listFiles()) {
			if (f.isFile()) {
				Song s = this.getSong(f.getAbsolutePath());
				page.getSongs().add(s);
			} else if (f.isDirectory()) {
				Page subpage = new Page(f.getAbsolutePath(), f.getName());
				page.getSubpages().add(subpage);
			}
		}
		
		return page;
	}

}
