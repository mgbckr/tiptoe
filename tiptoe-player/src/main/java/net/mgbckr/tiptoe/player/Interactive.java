package net.mgbckr.tiptoe.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

public interface Interactive {
	
	default Object request(Action message) throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}

	public static class Action {
		
		private String type;
		private Map<String, Object> properties;
		
		public Action() {
			this("none");
		}
		
		public Action(String type) {
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
				if (!(o instanceof String) && !(o instanceof Integer) && !(o instanceof Double)) {
					throw new IllegalArgumentException("Values must be Strings or Doubles.");
				}
			}
			this.properties = properties;
		}
		
		public void put(String key, String value) {
			this.properties.put(key, value);
		}
		
		public void put(String key, int value) {
			this.properties.put(key, value);
		}
		
		public void put(String key, double value) {
			this.properties.put(key, value);
		}
		
		public String retrieveString(String key) {
			return (String) this.properties.get(key);
		}
		
		public int retrieveInt(String key) {
			Object o = this.properties.get(key);
			if (!(o instanceof Integer)) {
				throw new IllegalStateException(o + " is not an Integer.");
			}
			return (int) o;
		}
		
		public double retrieveDouble(String key) {
			Object o = this.properties.get(key);
			if (o instanceof Integer) {
				return ((Integer) o).doubleValue();
			} else if (!(o instanceof Double)) {
				throw new IllegalStateException(o + " is not a Double.");
			} else {
				return (double) this.properties.get(key);
			}
		}
	}
}
