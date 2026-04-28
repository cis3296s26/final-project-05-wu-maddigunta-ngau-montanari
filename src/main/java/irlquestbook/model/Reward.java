package irlquestbook.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Reward {
    private final BooleanProperty claimed = new SimpleBooleanProperty(false);
    private String name;

    public Reward(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    // TODO: ability to claim reward
}
