package irlquestbook.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subtask {
    private final StringProperty name = new SimpleStringProperty("");
    private final BooleanProperty completed = new SimpleBooleanProperty(false);

    public Subtask(String name) {
        this.name.set(name);
    }

    public String getName() {
        return this.name.get();
    }

    public boolean getCompleted() {
        return this.completed.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setCompleted(boolean val) {
        this.completed.set(val);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public BooleanProperty completedProperty() {
        return this.completed;
    }
}
