package dev.emmie;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

class QuestDetailView extends StackPane {
    private HBox hBox;
    private VBox infoPanel, taskPanel;
    private VBox descBox, rewardBox;

    private Label title, desc;

    public QuestDetailView(StackPane root) {
        this.setVisible(false);

        // set up hBox
        this.hBox = new HBox();

        // create close button
        Button close = new Button("X");
        close.setOnAction(e -> this.hide());
        StackPane.setAlignment(close, Pos.TOP_RIGHT);

        // add hBox and button to stackpane
        this.getChildren().addAll(hBox, close);

        // set up info panel
        this.infoPanel = new VBox();

        this.title = new Label();
        this.desc = new Label();
        this.descBox = new VBox(10);
        this.descBox.getChildren().addAll(this.title, this.desc);

        Label rewards = new Label("Rewards");
        this.rewardBox = new VBox(10);
        this.rewardBox.getChildren().add(rewards);

        // set size of reward box to 40% of vertical space
        rewardBox.setMinHeight(Region.USE_PREF_SIZE);
        rewardBox.prefHeightProperty().bind(infoPanel.heightProperty().multiply(0.4));
        VBox.setVgrow(descBox, Priority.ALWAYS);

        this.infoPanel.getChildren().addAll(this.descBox, this.rewardBox);

        // set up task panel
        Label tasks = new Label("Tasks");
        this.taskPanel = new VBox(10);
        this.taskPanel.getChildren().add(tasks);

        // add main panels to hBox
        this.hBox.getChildren().add(infoPanel);
        this.hBox.getChildren().add(taskPanel);

        // add style
        close.setStyle(
                "-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 18; -fx-cursor: hand;");
        this.setStyle("-fx-background-color: #FFF5EE; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 20;");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        rewards.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        tasks.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        infoPanel.setStyle(
                "-fx-border-color: transparent black transparent transparent; -fx-border-width: 0 1 0 0; -fx-padding: 10;");
        taskPanel.setStyle("-fx-padding: 10;");
        rewardBox.setStyle("-fx-border-color: black transparent transparent transparent; -fx-border-width: 1 0 0 0;");

        // center text
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);
        tasks.setAlignment(Pos.CENTER);
        tasks.setMaxWidth(Double.MAX_VALUE);

        // bind size of vBoxes to pane
        infoPanel.prefWidthProperty().bind(this.widthProperty().divide(2));
        taskPanel.prefWidthProperty().bind(this.widthProperty().divide(2));

        // bind size of detail view to root
        prefWidthProperty().bind(root.widthProperty().multiply(0.9));
        prefHeightProperty().bind(root.heightProperty().multiply(0.9));
        maxWidthProperty().bind(root.widthProperty().multiply(0.9));
        maxHeightProperty().bind(root.heightProperty().multiply(0.9));

        // prevent shrinking to content
        this.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    }

    public void setQuest(Quest quest) {
        this.title.setText(quest.getName());
        this.desc.setText(quest.getDescription());
    }

    public void show() {
        this.setVisible(true);
        this.toFront();
    }

    public void hide() {
        this.setVisible(false);
    }
}
