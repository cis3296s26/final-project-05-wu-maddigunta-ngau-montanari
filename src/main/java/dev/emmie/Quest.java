package dev.emmie;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private int id;
    private String name;
    private String description;
    private boolean isCompleted;
    private Difficulty difficulty;
    private transient List<Quest> prev;
    private List<Subtask> subtasks;

    // constructor to initialize the quest object
    public Quest(String name, String description, Difficulty difficulty) {
        this.id = 0; // we can set id when we have a database with auto-increment
        this.name = name;
        this.description = description;
        this.isCompleted = false;
        this.difficulty = difficulty;
        this.prev = new ArrayList<>();
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

    // SETTERS FOR QUEST FIELDS
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addPrerequisite(Quest quest) {
        prev.add(quest);
    }

    void removePrerequisite(Quest quest) {
        prev.remove(quest);
    }

    void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
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

    public boolean checkCompleted() {
        // TODO: this should trigger when consumer is fired
        for (Subtask subtask : subtasks) {
            if (!subtask.getCompleted()) {
                return false;
            }
        }
        setCompleted(true);
        return true;
    }

    // let the user set the difficulty of the quest, default is medium
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    // get the difficulty of the quest
    public Difficulty getDifficulty() {
        return difficulty;
    }

}
