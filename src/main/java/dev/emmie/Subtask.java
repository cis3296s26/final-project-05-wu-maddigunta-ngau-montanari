package dev.emmie;

// want the user to be able to create subtasks for each quest
public class Subtask {
    private String title;
    private boolean completed;
    private transient Quest parentQuest;

    // constructor to initialize the subtask object with a title and set completed
    // to false by default
    public Subtask(String title, Quest parentQuest) {
        this.title = title;
        this.completed = false;
        this.parentQuest = parentQuest;
    }

    public String getName() {
        return title;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean set) {
        completed = set;
        // TODO: trigger consumer
    }
}
