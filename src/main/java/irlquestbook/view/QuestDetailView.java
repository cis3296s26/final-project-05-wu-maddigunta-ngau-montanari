package irlquestbook.view;

import irlquestbook.model.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class QuestDetailView extends StackPane {
    private HBox hBox;
    private VBox lPanel, rPanel;
    private VBox infoBox, rewardBox, taskBox;
    private VBox rewardList, taskList;
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
        this.lPanel = new VBox();
        this.rPanel = new VBox();

        this.title = new Label();
        this.desc = new Label();
        this.infoBox = new VBox(10);
        this.infoBox.getChildren().addAll(this.title, this.desc);

        Label rewards = new Label("Rewards");
        this.rewardList = new VBox(10);
        this.rewardBox = new VBox(15);
        this.rewardBox.getChildren().addAll(rewards, rewardList);

        // set size of reward box to 40% of vertical space
        this.rewardBox.setMinHeight(Region.USE_PREF_SIZE);
        this.rewardBox.prefHeightProperty().bind(lPanel.heightProperty().multiply(0.4));
        VBox.setVgrow(this.infoBox, Priority.ALWAYS);

        this.lPanel.getChildren().addAll(this.infoBox, this.rewardBox);

        // set up task panel
        Label tasks = new Label("Tasks");
        this.taskList = new VBox(10);
        this.taskBox = new VBox(15);
        this.taskBox.getChildren().addAll(tasks, this.taskList);
        this.rPanel.getChildren().add(taskBox);

        // add main panels to hBox
        this.hBox.getChildren().add(lPanel);
        this.hBox.getChildren().add(rPanel);

        // add style
        close.getStyleClass().add("close-btn");
        this.getStyleClass().add("quest-detail");
        title.getStyleClass().add("detail-title");
        rewards.getStyleClass().add("section-header");
        tasks.getStyleClass().add("section-header");
        lPanel.getStyleClass().addAll("panel", "l-panel");
        rPanel.getStyleClass().add("panel");
        rewardBox.getStyleClass().add("reward-box");
        taskList.getStyleClass().add("checkbox-list");
        rewardList.getStyleClass().add("checkbox-list");

        // center text
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);
        tasks.setAlignment(Pos.CENTER);
        tasks.setMaxWidth(Double.MAX_VALUE);

        // bind size of vBoxes to pane
        lPanel.prefWidthProperty().bind(this.widthProperty().divide(2));
        rPanel.prefWidthProperty().bind(this.widthProperty().divide(2));

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
            cb.getStyleClass().add("checkbox");
            cb.setSelected(subtask.getCompleted());
            cb.setOnAction(e -> subtask.setCompleted(cb.isSelected()));
            taskList.getChildren().add(cb);
        });

        // set up reward boxes
        rewardList.getChildren().clear();
        quest.getRewards().forEach(reward -> {
            CheckBox cb = new CheckBox(reward.getName());
            cb.getStyleClass().add("checkbox");
            cb.setSelected(reward.getClaimed());
            rewardList.getChildren().add(cb);
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
