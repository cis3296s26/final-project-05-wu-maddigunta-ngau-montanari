package irlquestbook.model;

public enum QuestRepeatInterval {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly");

    private final String displayName;

    QuestRepeatInterval(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
