package irlquestbook.model;

// let the user mark priority of their quests 
public enum Tag {
	// different proirity levels for quests, with display names for each level
	HIGH_PRIORITY("High Priority"),
	MEDIUM_PRIORITY("Medium Priority"),
	LOW_PRIORITY("Low Priority");
	
	private final String displayName;

	Tag(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}
  
}
