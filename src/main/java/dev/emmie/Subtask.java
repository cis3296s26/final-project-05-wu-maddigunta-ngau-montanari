package dev.emmie;


// want the user to be able to create subtasks for each quest
public class Subtask {
  private String title; 
  private boolean completed;

  // constructor to initialize the subtask object with a title and set completed to false by default
  public Subtask(String title) {
      this.title = title;
      this.completed = false;
  }

  //getter
  public String getTitle() {
      return title;
  }

  public boolean isCompleted() {
      return completed;
  }

  //setter
  public void setTitle(String title) {
      this.title = title;
  }

  public void setCompleted(boolean completed) {
      this.completed = completed;
  }

}
