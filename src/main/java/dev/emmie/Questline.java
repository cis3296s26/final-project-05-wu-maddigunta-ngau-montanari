package dev.emmie;

public enum Questline {
    MAIN_STORY("Main Quests"),
    SIDE_QUESTS("Side Quests");

    private final String displayName;

    Questline(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    
}
