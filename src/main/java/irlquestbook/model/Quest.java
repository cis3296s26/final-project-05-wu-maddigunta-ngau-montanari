package irlquestbook.model;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// TODO: repeat quests
public class Quest {
    private int id;
    private String name;
    private String description;
    private double mapX, mapY;

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
        this.name = name;
        this.description = description;
        this.mapX = x;
        this.mapY = y;

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

    public ObjectBinding<QuestState> stateProperty() {
        return this.state;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public double getX() {
        return this.mapX;
    }

    public double getY() {
        return this.mapY;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addPrerequisite(Quest quest) {
        prereqs.add(quest);
    }

    public void removePrerequisite(Quest quest) {
        prereqs.remove(quest);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public void addReward(Reward reward) {
        rewards.add(reward);
    }

    public void removeReward(Reward reward) {
        rewards.remove(reward);
    }

    public void setX(double x) {
        this.mapX = x;
    }

    public void setY(double y) {
        this.mapY = y;
    }
}
