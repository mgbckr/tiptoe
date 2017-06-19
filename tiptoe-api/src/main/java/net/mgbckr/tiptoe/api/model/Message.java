package net.mgbckr.tiptoe.api.model;

public class Message {

	private String type;
	private Object content;
	
	public Message(String type, Object message) {
		this.type = type;
		this.content = message;
	}
	
	public String getType() {
		return type;
	}
	
	public Object getContent() {
		return content;
	}
	
}
