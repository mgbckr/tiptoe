package net.mgbckr.tiptoe.library;

import java.util.ArrayList;
import java.util.List;

public class Page {

	public static Page newRootPage() {
		return new Page("/", "root");
	}
	
	private String id;
	private String name;
	
	private List<Page> subpages;
	private List<Song> songs;
	
	public Page(String id, String name) {
		this.id = id;
		this.name = name;
		this.subpages = new ArrayList<Page>();
		this.songs = new ArrayList<Song>();
	}
	
	public Page(String id) {
		this(id, id);
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Page> getSubpages() {
		return subpages;
	}
	
	public List<Song> getSongs() {
		return songs;
	}
	
	
}
