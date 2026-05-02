package irlquestbook.model;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

// TODO: repeat quests
public class Quest {
    private int id;
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final DoubleProperty mapX = new SimpleDoubleProperty(0);
    private final DoubleProperty mapY = new SimpleDoubleProperty(0);

    private final ObservableList<Quest> prereqs = FXCollections
            .observableArrayList((Quest q) -> new Observable[] { q.stateProperty() });
    private final ObservableList<Subtask> subtasks = FXCollections
            .observableArrayList((Subtask s) -> new Observable[] { s.completedProperty() });
    private final ObservableList<Reward> rewards = FXCollections
            .observableArrayList((Reward r) -> new Observable[] { r.claimedProperty() });

    private final ObjectBinding<QuestState> state;

    // constructor to initialize the quest object
    public Quest(String name, String description, double x, double y) {
        this.id = 0; // we can set id when we have a database with auto-increment
        this.name.set(name);
        this.description.set(description);
        this.mapX.set(x);
        this.mapY.set(y);

        // bind quest state to dependent properties
        state = Bindings.<QuestState>createObjectBinding(() -> {
            // calculate states based on respective factors
            boolean unlocked = prereqs.stream().allMatch(q -> {
                QuestState s = q.stateProperty().get();
                return s == QuestState.COMPLETED || s == QuestState.CLAIMED;
            });
            boolean completed = subtasks.stream().allMatch(Subtask::getCompleted);
            boolean claimed = rewards.stream().allMatch(Reward::getClaimed);

            // if locked, its locked
            if (!unlocked)
                return QuestState.LOCKED;

            // if unlocked, check if uncompleted
            if (!completed)
                return QuestState.AVAILABLE;

            // if completed, check if unclaimed
            if (!claimed)
                return QuestState.COMPLETED;

            // if claimed, its claimed
            return QuestState.CLAIMED;

        }, prereqs, subtasks, rewards);
    }

    // property getters
    public ObjectBinding<QuestState> stateProperty() {
        return state;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    // regular getters
    public String getName() {
        return name.get();
    }

    public String getDescription() {
        return description.get();
    }

    public double getX() {
        return this.mapX.get();
    }

    public DoubleProperty xProperty() {
        return this.mapX;
    }

    public double getY() {
        return this.mapY.get();
    }
    
    public DoubleProperty yProperty() {
        return this.mapY;
    }

    public int getId() {
        return id;
    }

    public ObservableList<Quest> getPrereqs() {
        return prereqs;
    }

    public ObservableList<Subtask> getSubtasks() {
        return subtasks;
    }

    public ObservableList<Reward> getRewards() {
        return rewards;
    }

    // regular setters
    public void setX(double x) {
        this.mapX.set(x);
    }

    public void setY(double y) {
        this.mapY.set(y);
    }

    public void setName(String value) {
        name.set(value);
    }

    public void setDescription(String value) {
        description.set(value);
    }

    // adders
    public void addPrerequisite(Quest quest) {
        prereqs.add(quest);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void addReward(Reward reward) {
        rewards.add(reward);
    }

    // removers
    public void removePrerequisite(Quest quest) {
        prereqs.remove(quest);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public void removeReward(Reward reward) {
        rewards.remove(reward);
    }

}
