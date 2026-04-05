package dev.emmie;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private int id;
    private String name;
    private String description;
    private boolean isCompleted;
    private List<Quest> next; //only stores the reference to the next quests, not the actual quest objects
    private List<Quest> prev; //only stores the reference to the previous quests, not the actual quest objects

    // constructor to initialize the quest object
    public Quest(int id, String name, String description, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
        this.next = new ArrayList<>();
        this.prev = new ArrayList<>();

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

    public List<Quest> getNextQuests() {
        return next;
    }

    public List<Quest> getPrevQuests() {
        return prev;
    }

    // SETTERS FOR QUEST FIELDS
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

     void addPrerequisite(Quest quest) {//default access modifier, only accessible within the package
        prev.add(quest);
    }

     void addNextQuest(Quest quest) {//default access modifier, only accessible within the package
        next.add(quest);
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    public boolean isLocked() {
        for (Quest prerequisite : prev) {
            if (!prerequisite.isCompleted()) {
                return true;
            }
        }
        return false;
    }

    public String getStatus(List<Quest> completedQuests) {
        if (isCompleted())
            return "Completed";
        if (isLocked())
            return "Locked";
        return "Available";
    }


}
