package events;

public class ElectionEvent {
	private final Object source;
	
	public ElectionEvent(Object source) {
		this.source = source;
	}

	public Object getSource() {
		return source;
	}
	
}
