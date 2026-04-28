package irlquestbook.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class Reward {
    private final BooleanProperty claimed = new SimpleBooleanProperty(false);

    public BooleanProperty claimedProperty() {
        return this.claimed;
    }

    public boolean getClaimed() {
        return claimed.get();
    }

    public void setClaimed(boolean value) {
        this.claimed.set(value);
    }

    public abstract void claimReward(UserProfile user);
}
