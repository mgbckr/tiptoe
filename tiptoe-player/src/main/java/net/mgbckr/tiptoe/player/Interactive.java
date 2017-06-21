package net.mgbckr.tiptoe.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

public interface Interactive {
	
	public static final String VALUE = "value";
	
	default Message sendMessage(Message message) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	public static class Message {
		
		private String type;
		private Map<String, Object> properties;
		
		public Message() {
			this("none");
		}
		
		public Message(String type) {
			this.type = type;
			this.properties = new HashMap<>();
		}
		
		public String getType() {
			return type;
		}
		
		public Map<String, Object> getProperties() {
			return Collections.unmodifiableMap(this.properties);
		}
		
		public void setProperties(Map<String, Object> properties) {
			for (Object o : properties.values()) {
				if (!(o instanceof String) && !(o instanceof Double)) {
					throw new IllegalArgumentException("Values must be Strings or Doubles.");
				}
			}
			this.properties = properties;
		}
		
		public void put(String key, String value) {
			this.properties.put(key, value);
		}
		
		public void put(String key, double value) {
			this.properties.put(key, value);
		}
		
		public String retrieveString(String key) {
			return (String) this.properties.get(key);
		}
		
		public double retrieveDouble(String key) {
			return (double) this.properties.get(key);
		}
		
		
	}
}
