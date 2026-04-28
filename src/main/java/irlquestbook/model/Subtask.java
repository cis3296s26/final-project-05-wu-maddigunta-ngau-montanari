package irlquestbook.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Subtask {
    private String title;
    private final BooleanProperty completed = new SimpleBooleanProperty(false);

    public Subtask(String title) {
        this.title = title;
    }

    public String getName() {
        return this.title;
    }

    public boolean getCompleted() {
        return this.completed.get();
    }

    public void setCompleted(boolean val) {
        this.completed.set(val);
    }

    public BooleanProperty completedProperty() {
        return this.completed;
    }
}
