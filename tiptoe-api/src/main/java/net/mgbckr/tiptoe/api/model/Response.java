package net.mgbckr.tiptoe.api.model;

public class Response {

	private String type;
	private Object content;
	
	public Response(String type, Object message) {
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
