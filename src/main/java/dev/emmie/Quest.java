package dev.emmie;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    private int id;
    private String name;
    private String description;
    private boolean isCompleted;
    private int XP;
    private List<Quest> nextQuests; // This will hold the actual Quest objects for next quests
    private List<Quest> prerequisites; // This will hold the actual Quest objects for prerequisites

    // constructor to initialize the quest object
    public Quest(int id, String name, String description, boolean isCompleted, int XP, List<Quest> prerequisites,
            List<Quest> nextQuests) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
        this.XP = XP;
        this.prerequisites = new ArrayList<>(prerequisites);
        this.nextQuests = new ArrayList<>(nextQuests);
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

    public int getXP() {
        return XP;
    }

    public int getId() {
        return id;
    }

    public List<Quest> getNextQuests() {
        return nextQuests;
    }

    public List<Quest> getPrerequisites() {
        return prerequisites;
    }

    // SETTERS FOR QUEST FIELDS
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addPrerequisite(Quest quest) {
        prerequisites.add(quest);
    }

    public void addNextQuest(Quest quest) {
        nextQuests.add(quest);
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    public boolean isLocked() {
        for (Quest prerequisite : prerequisites) {
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
