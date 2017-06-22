package net.mgbckr.tiptoe.player;

public interface Observable {

	default void addEventListener(EventListener listener) {
	}

	public static interface EventListener {
		void notify(Event e);
	}
	
	public static class Event {
		
		private String type;
		private Object content;

		public Event(String type) {
			this(type, null);
		}
		
		public Event(String type, Object content) {
			super();
			this.type = type;
			this.content = content;
		}

		public String getType() {
			return type;
		}
		
		public void setType(String type) {
			this.type = type;
		}
		
		public Object getContent() {
			return content;
		}
		
		public void setContent(Object content) {
			this.content = content;
		}
	}
	
}
