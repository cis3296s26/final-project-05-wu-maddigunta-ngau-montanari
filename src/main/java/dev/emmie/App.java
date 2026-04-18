package dev.emmie;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        // create root and scene
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 900, 600);

        // load css
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // create detail panel
        QuestDetailView details = new QuestDetailView(root);

        // add some test quests with arrow between
        Quest quest1 = new Quest("test1", "test1");
        Quest quest2 = new Quest("test2", "test2");
        Quest quest3 = new Quest("test3", "test3");
        Quest quest4 = new Quest("test4", "test4");

        quest1.addPrerequisite(quest2);
        quest2.addPrerequisite(quest3);
        quest2.addPrerequisite(quest4);

        Questline questline = new Questline("test");
        questline.addQuest(quest1);
        questline.addQuest(quest2);
        questline.addQuest(quest3);
        questline.addQuest(quest4);

        List<Questline> qls = List.of(questline);
        Page page1 = new Page(qls, "Test page 1");
        Page page2 = new Page(qls, "Test page 2");
        Page page3 = new Page(qls, "Test page 3");

        // create questbook
        QuestBook questBook = new QuestBook(List.of(page1, page2, page3));
        QuestBookView questBookView = new QuestBookView(questBook, q -> {
            // this runs on quest click
            details.setQuest(q);
            details.show();
        });

        // add qb view and detail panel to root
        root.getChildren().add(questBookView);
        root.getChildren().add(details);

        stage.setTitle("IRL Quest Book");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
