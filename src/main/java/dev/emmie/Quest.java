package dev.emmie;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    int id;
    String name;
    String description;
    Boolean completed;
    int XP;
    private List<Integer> prerequisiteIds;

    //constructor to initialize the quest object
    public Quest(int id, String name, String description, Boolean completed, int XP, List<Integer> prerequisiteIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.XP = XP;
        this.prerequisiteIds = new ArrayList<>(prerequisiteIds);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void markCompleted() {
        this.completed = true;
    }


    public void display() {
        System.out.println("Quest Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Completed: " + completed);
        System.out.println("XP: " + XP);
    }

    public boolean isLocked(List<Quest> completedQuests) {
        //for every ID in our premade list of prerequisite IDs
        for (Integer prerequisiteId : prerequisiteIds) {
            boolean found = false;
            //we check every quest in the passed list of completed quests 
            for (Quest quest : completedQuests) {
                //to see if the ID of the quest matches the prerequisite ID and if it is completed
                if (quest.id == prerequisiteId && quest.completed) {
                    //if it is, we set found to true and break out of the loop
                    found = true;
                    break; //go to the next prerequisite ID since we found a match for this one
                }
            }
            //if we finish checking all completed quests and found is still false,
            if (!found) {
                return true; // the quest is locked
            }
        }
        return false; // All prerequisites are completed, the quest is not locked
    }


}
