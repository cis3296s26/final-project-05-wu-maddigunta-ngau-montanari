package dev.emmie;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private int id;
    private String name;
    private String description;
    private boolean isCompleted;
    private transient List<Quest> prev; // only stores the reference to the previous quests, not the actual quest
    // objects
    private List<Subtask> subtasks; // only stores the reference to the subtasks, not the actual subtask
                                    // objects
    private boolean isRepeating;

    // constructor to initialize the quest object
    public Quest(String name, String description, boolean isRepeating) {
        this.id = 0; // we can set id when we have a database with auto-increment
        this.name = name;
        this.description = description;
        this.isCompleted = false;
        this.prev = new ArrayList<>();
        this.isRepeating = isRepeating;
        this.subtasks = new ArrayList<>();

    }

    // GETTERS FOR QUEST FIELDS
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public int getId() {
        return id;
    }

    public List<Quest> getPrevQuests() {
        return prev;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public boolean isRepeating() {
        return isRepeating;
    }

    // SETTERS FOR QUEST FIELDS
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    void addPrerequisite(Quest quest) { // default access modifier, only accessible within the package
        prev.add(quest);
    }

    void removePrerequisite(Quest quest) { // default access modifier, only accessible within the package
        prev.remove(quest);
    }

    void addSubtask(Subtask subtask) { // default access modifier, only accessible within the package
        subtasks.add(subtask);
    }

    void removeSubtask(Subtask subtask) { // default access modifier, only accessible within the package
        subtasks.remove(subtask);
    }

    void setRepeating(boolean isRepeating) {
        this.isRepeating = isRepeating;
    }

    //methods
    public void resetQuest() {
        this.isCompleted = false;
        for (Subtask subtask : subtasks) {
            subtask.setCompleted(false); // mark all subtasks as not completed when resetting the quest
        }
    }

    public void markCompleted() {
        if (isRepeating) {
            resetQuest();
            return;
        }
        this.isCompleted = true;
    }

    private boolean isLocked() {
        for (Quest prerequisite : prev) {
            if (!prerequisite.isCompleted()) {
                return true;
            }
        }
        return false;
    }

    public QuestStatus getStatus() {
        if (isCompleted) {
            return QuestStatus.COMPLETED;
        }
        if (isLocked()) {
            return QuestStatus.LOCKED;
        }
        return QuestStatus.AVAILABLE;
    }

    public boolean isQuestCompleted() {
        if (isCompleted) {
            return true;
        }
        if (!subtasks.isEmpty()) {
            for (Subtask subtask : subtasks) {
                if (!subtask.isCompleted()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
