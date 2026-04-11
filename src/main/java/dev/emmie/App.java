package dev.emmie;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        // create root and scene
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 900, 600);

        // add some test quests with arrow between
        Quest quest1 = new Quest("test1", "test1", false);
        Quest quest2 = new Quest("test2", "test2", false);
        Quest quest3 = new Quest("test3", "test3", false);
        Quest quest4 = new Quest("test4", "test4", false);

        quest1.addPrerequisite(quest2);
        quest2.addPrerequisite(quest3);
        quest2.addPrerequisite(quest4);

        Questline questline = new Questline("test");
        questline.addQuest(quest1);
        questline.addQuest(quest2);
        questline.addQuest(quest3);
        questline.addQuest(quest4);

        // create PageView pane
        List<Questline> questlines = new ArrayList<>();
        questlines.add(questline);
        PageView map = new PageView(questlines);

        // put questmap in root pane
        root.setCenter(map);

        stage.setTitle("IRL Quest Book");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
