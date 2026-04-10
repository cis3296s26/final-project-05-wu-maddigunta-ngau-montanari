package dev.emmie;

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

        // create questmap pane
        Pane questMap = new Pane();

        // create label
        Label tooltip = new Label();
        questMap.getChildren().add(tooltip);

        // add two test quests with arrow between
        Quest quest1 = new Quest("test1", "test1", false);
        Quest quest2 = new Quest("test2", "test2", false);
        QuestView questView1 = new QuestView(quest1, tooltip);
        QuestView questView2 = new QuestView(quest2, tooltip);
        questMap.getChildren().add(questView1);
        questMap.getChildren().add(questView2);

        // put questmap in root pane
        root.setCenter(questMap);

        stage.setTitle("IRL Quest Book");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
