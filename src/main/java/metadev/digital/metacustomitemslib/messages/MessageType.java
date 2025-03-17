package metadev.digital.metacustomitemslib.messages;

public enum MessageType {
	Chat("Chat"), ActionBar("ActionBar"), Title("Title"), Subtitle("Subtitle"), None("None");

	private final String name;

	private MessageType(String name) {
		this.name = name;
	}

	public boolean equalsName(String otherName) {
		return (otherName != null) && name.equals(otherName);
	}

	public String toString() {
		return name;
	}

	public MessageType valueOf(int id) {
		return MessageType.values()[id];
	}

	public String getName() {
		return name;
	}

}
