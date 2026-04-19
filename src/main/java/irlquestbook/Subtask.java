package irlquestbook;

public class Subtask {
    private String title;
    private boolean completed;

    public Subtask(String title) {
        this.title = title;
        this.completed = false;
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
