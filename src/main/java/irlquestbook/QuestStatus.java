package irlquestbook;

public enum QuestStatus {
    COMPLETED("Completed"),
    LOCKED("Locked"),
    AVAILABLE("Available");

    private final String displayName;

    QuestStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
