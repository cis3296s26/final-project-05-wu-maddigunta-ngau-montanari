package irlquestbook.model;

import java.util.function.Consumer;

public class Subtask {
    private String title;
    private boolean completed;
    private Consumer<Subtask> onComplete; // callback for when the subtask is marked as complete

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

    // setter for the onComplete cosumer, callback to trigger when the subtask is marked as complete
    public void setOnComplete(Consumer<Subtask> onComplete) {
        this.onComplete = onComplete;
    }
    
    public void setCompleted(boolean set) {
        boolean wasCompleted = this.completed;
        this.completed = set;
        
        // trigger consumer, check if the subtask was not previously completed and is now marked as complete
        if (!wasCompleted && this.completed && onComplete != null) {
            onComplete.accept(this);
        }
    }
}
