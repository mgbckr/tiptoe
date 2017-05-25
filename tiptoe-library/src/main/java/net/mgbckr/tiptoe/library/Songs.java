package net.mgbckr.tiptoe.library;

import java.util.ArrayList;
import java.util.List;

public class Songs {

	private int offset;
	private List<Song> list;

	public Songs() {
		this(0);
	}
	
	public Songs(int offset) {
		this.offset = offset;
		this.list = new ArrayList<Song>();
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public List<Song> getList() {
		return list;
	}
	
	public void setList(List<Song> list) {
		this.list = list;
	}

}
