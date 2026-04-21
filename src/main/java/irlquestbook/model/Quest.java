package irlquestbook.model;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private int id;
    private String name;
    private String description;
    private boolean isCompleted;
    private transient List<Quest> prev;
    private List<Subtask> subtasks;

    // constructor to initialize the quest object
    public Quest(String name, String description) {
        this.id = 0; // we can set id when we have a database with auto-increment
        this.name = name;
        this.description = description;
        this.isCompleted = false;
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

    public void removePrerequisite(Quest quest) {
        prev.remove(quest);
    }

    public void addSubtask(Subtask subtask) {
        // set up consumer to check if the quest is complete when the subtask is marked as complete
        subtask.setOnComplete(s -> checkCompleted()); 
        subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
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
        // check if all subtasks are complete, if they are mark the quest as complete and return true, otherwise return false
        boolean allDone = subtasks.stream().allMatch(Subtask::getCompleted); // check if all subtasks are complete using stream and allMatch
        if (allDone) setCompleted(true);
        return allDone; // return true if all subtasks are complete 
    }

}
