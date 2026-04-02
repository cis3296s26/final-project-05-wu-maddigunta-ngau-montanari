package dev.emmie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quest {
    int id;
    String name;
    String description;
    Boolean completed;
    int XP;
    private List<Integer> prerequisiteIds;
    private List<Subtask> subtasks; // list to hold subtasks for this quest

    //constructor to initialize the quest object
    public Quest(int id, String name, String description, Boolean completed, int XP, List<Integer> prerequisiteIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.XP = XP;
        this.prerequisiteIds = new ArrayList<>(prerequisiteIds);
        this.subtasks = new ArrayList<>(); // list to hold subtasks for this quest
        
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

    // get the list of subtasks for this quest
    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    // method to add subtasks to a quest, allowing the user to input multiple subtasks until they type 'done'
    public void addSubtask(Scanner scan) {
        while (true) {
            System.out.println("Enter a subtask for this quest (or type 'done' to finish):");
            String title = scan.nextLine();

            if (title.equalsIgnoreCase("done")) {
                break;
            }

            if (title.isEmpty()) {
                System.out.println("Subtask cannot be empty.");
                continue;
            }

            // create the subtask object and add it to the list 
            Subtask subtask = new Subtask(title); 
            subtasks.add(subtask);
        }
    }


    public void display() {
        System.out.println("Quest Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Completed: " + completed);
        System.out.println("XP: " + XP);

        // loop to display each subtask for this quest, showing the title and completion status of each subtask
        System.out.println("Subtasks:");
        for (Subtask subtask : subtasks) {
            subtask.display();
        }
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
