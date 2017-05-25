package net.mgbckr.tiptoe.library;

public class Song {
	
	private String id;
	private String title;
	private String artist;
	private String album;
	
	public Song(String id, String title, String artist, String album) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.album = album;
	}
	
	public Song(String id) {
		this(id, null, null, null);
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
}
