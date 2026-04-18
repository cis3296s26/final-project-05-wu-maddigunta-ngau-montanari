package dev.emmie;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

class QuestDetailView extends StackPane {
    private HBox hBox;
    private VBox infoPanel, taskPanel;
    private VBox descBox, rewardBox, taskList;

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
        this.rewardBox.setMinHeight(Region.USE_PREF_SIZE);
        this.rewardBox.prefHeightProperty().bind(infoPanel.heightProperty().multiply(0.4));
        VBox.setVgrow(this.descBox, Priority.ALWAYS);

        this.infoPanel.getChildren().addAll(this.descBox, this.rewardBox);

        // set up task panel
        Label tasks = new Label("Tasks");
        this.taskList = new VBox(10);
        this.taskPanel = new VBox(15);
        this.taskList.getStyleClass().add("task-list");
        this.taskPanel.getChildren().addAll(tasks, this.taskList);

        // add main panels to hBox
        this.hBox.getChildren().add(infoPanel);
        this.hBox.getChildren().add(taskPanel);

        // add style
        close.getStyleClass().add("close-btn");
        this.getStyleClass().add("quest-detail");
        title.getStyleClass().add("detail-title");
        rewards.getStyleClass().add("section-header");
        tasks.getStyleClass().add("section-header");
        infoPanel.getStyleClass().add("info-panel");
        taskPanel.getStyleClass().add("task-panel");
        rewardBox.getStyleClass().add("reward-box");

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

        // set up subtask boxes
        taskList.getChildren().clear();
        quest.getSubtasks().forEach(subtask -> {
            CheckBox cb = new CheckBox(subtask.getName());
            cb.getStyleClass().add("task-checkbox");
            cb.setSelected(subtask.getCompleted());
            cb.setOnAction(e -> subtask.setCompleted(cb.isSelected()));
            taskList.getChildren().add(cb);
        });
    }

    public void show() {
        this.setVisible(true);
        this.toFront();
    }

    public void hide() {
        this.setVisible(false);
    }
}
