package irlquestbook.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reward {
    private final BooleanProperty claimed = new SimpleBooleanProperty(false);
    private final StringProperty name = new SimpleStringProperty("");

    public Reward(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public BooleanProperty claimedProperty() {
        return this.claimed;
    }

    public boolean getClaimed() {
        return claimed.get();
    }

    public void setClaimed(boolean value) {
        this.claimed.set(value);
    }
}
