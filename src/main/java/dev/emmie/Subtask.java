package dev.emmie;
import java.util.*;

// want the user to be able to create subtasks for each quest
public class Subtask {
  private String title; 
  private boolean completed;

  // constructor to initialize the subtask object with a title and set completed to false by default
  public Subtask(String title) {
      this.title = title;
      this.completed = false;
  }

  public String getTitle() {
      return title;
  }

  public boolean isCompleted() {
      return completed;
  }

  public void markCompleted() {
      completed = true;
  }

  public void display() {
      System.out.println("- " + title + " | Completed: " + completed);
  }

  // list to store subtasks for each quest
  private List<Subtask> subtasks = new ArrayList<>();

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
}

