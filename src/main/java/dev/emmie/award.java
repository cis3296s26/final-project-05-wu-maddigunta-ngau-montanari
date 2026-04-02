package dev.emmie;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// let the user claim and create awwards 
public class award {
  // create a file to store awards
  File awardsFile = new File("awards.txt");
  Scanner scan = new Scanner(System.in);

  public class claim_award {
    public void claim() {
      // list to store awards read from the file
       List<String> awards = new ArrayList<>();

      // read awards from the file, display them to the user, and add them to the list
      try {
        File awardsFile = new File("awards.txt");
        Scanner scan = new Scanner(awardsFile);
        while (scan.hasNextLine()) {
          String award = scan.nextLine();
          awards.add(award);
          System.out.println(award);
        }
        scan.close();
      }
      catch (IOException e) {
        System.out.println("An error occurred while reading the awards file.");
        e.printStackTrace();
      }
      
      // allow the user to claim and award 
      System.out.println("Enter the name of the award you want to claim:");
      String claimedAward = scan.nextLine();

      if (claimedAward.isEmpty()) {
        System.out.println("You must enter the name of an award to claim.");
        return;
      }

      // flag to check if the claimed award was found in the list of awards
      boolean found = false;

      // use an iterator to remove the claimed award from the list of awards
      Iterator<String> iterator = awards.iterator();
      while (iterator.hasNext()) {
          String award = iterator.next();

          if (award.equalsIgnoreCase(claimedAward)) {
              iterator.remove(); 
              found = true;
              break;
          }
      }

      if (!found) {
          System.out.println("Award not found.");
          return;
      }
      
    }
  }

  // format of the award name - description i.e "screen time - get 15 minutes of scrolling"
  public class create_award {
    public void add_award() {
      // allow the user to create an award by entering a name and description
      Scanner input = new Scanner(System.in);

      // Get award name
      System.out.println("Enter award name:");
      String name = input.nextLine();

      // check if the award was filled 
      if (name.isEmpty()) {
        System.out.println("Award name cannot be empty.");
        return;
      }

       // Get description
       System.out.println("Enter award description:");
       String description = input.nextLine();

       if (description.isEmpty()) {
           System.out.println("Description cannot be empty.");
           return;
       }

       String award = name + " - " + description;

      // save the created award to the file
      try {
            FileWriter writer = new FileWriter("awards.txt", true); // true = append
            writer.write(award + "\n");
            writer.close();

            System.out.println("Award added successfully!");
        }
        catch (IOException e) {
            System.out.println("Error writing to file.");
            e.printStackTrace();
        }
    }
  }
}
