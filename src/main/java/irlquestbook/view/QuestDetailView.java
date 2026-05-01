package irlquestbook.view;

import irlquestbook.model.*;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    public QuestDetailView(StackPane root, Quest quest, QuestBook qb, Runnable onClose) {
        // set up general structure
        this.hBox = new HBox();
        this.lPanel = new VBox();
        this.rPanel = new VBox();
        this.hBox.getChildren().add(lPanel);
        this.hBox.getChildren().add(rPanel);

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

        // create close button
        Button close = new Button("X");
        close.setOnAction(e -> onClose.run());
        StackPane.setAlignment(close, Pos.TOP_RIGHT);

        // add hBox and button to stackpane
        this.getChildren().addAll(hBox, close);

        // set up info box
        this.infoBox = new VBox(10);

        TextField nameField = new TextField();
        nameField.textProperty().bindBidirectional(quest.nameProperty());
        nameField.visibleProperty().bind(qb.editModeProperty());
        nameField.managedProperty().bind(qb.editModeProperty());

        Label nameLabel = new Label();
        nameLabel.visibleProperty().bind(qb.editModeProperty().not());
        nameLabel.managedProperty().bind(qb.editModeProperty().not());
        nameLabel.textProperty().bind(quest.nameProperty());

        TextArea descField = new TextArea();
        descField.setWrapText(true);
        descField.textProperty().bindBidirectional(quest.descriptionProperty());
        descField.visibleProperty().bind(qb.editModeProperty());
        descField.managedProperty().bind(qb.editModeProperty());
        VBox.setMargin(descField, new Insets(0, 0, 12, 0));

        Label descLabel = new Label();
        descLabel.setWrapText(true);
        descLabel.visibleProperty().bind(qb.editModeProperty().not());
        descLabel.managedProperty().bind(qb.editModeProperty().not());
        descLabel.textProperty().bind(quest.descriptionProperty());

        VBox.setVgrow(descLabel, Priority.ALWAYS);
        VBox.setVgrow(descField, Priority.ALWAYS);

        this.infoBox.getChildren().addAll(nameLabel, nameField, descLabel, descField);

        // set up reward box
        Label rewards = new Label("Rewards");
        this.rewardList = new VBox(10);
        this.rewardBox = new VBox(15);
        Button claim = new Button("Claim all rewards");
        this.rewardBox.getChildren().addAll(rewards, rewardList, claim);

        this.rewardBox.setMinHeight(Region.USE_PREF_SIZE);
        this.rewardBox.prefHeightProperty().bind(lPanel.heightProperty().multiply(0.4));
        VBox.setVgrow(this.infoBox, Priority.ALWAYS);

        this.lPanel.getChildren().addAll(this.infoBox, this.rewardBox);

        // set up reward list
        quest.getRewards().forEach(reward -> {
            CheckBox cb = new CheckBox(reward.getName());
            cb.getStyleClass().addAll("checkbox", "clickable");
            cb.selectedProperty().bindBidirectional(reward.claimedProperty());
            cb.disableProperty().bind(reward.claimedProperty()
                    .or(quest.stateProperty().isNotEqualTo(QuestState.COMPLETED)));
            rewardList.getChildren().add(cb);
        });

        // make claim button work
        claim.setOnAction(e -> {
            quest.getRewards().forEach(reward -> {
                reward.setClaimed(true);
            });
        });

        // bind button enable to completed status
        claim.disableProperty().bind(Bindings.createBooleanBinding(
                () -> quest.stateProperty().get() != QuestState.COMPLETED
                        || quest.getRewards().isEmpty(),
                quest.stateProperty(), quest.getRewards()));

        // set up task panel
        Label tasks = new Label("Tasks");
        this.taskList = new VBox(10);
        this.taskBox = new VBox(15);
        this.taskBox.getChildren().addAll(tasks, this.taskList);
        this.rPanel.getChildren().add(taskBox);

        // set up task list
        quest.getSubtasks().forEach(subtask -> {
            CheckBox cb = new CheckBox(subtask.getName());
            cb.getStyleClass().addAll("checkbox", "clickable");
            cb.selectedProperty().bindBidirectional(subtask.completedProperty());
            cb.disableProperty().bind(subtask.completedProperty()
                    .or(quest.stateProperty().isEqualTo(QuestState.LOCKED)));
            taskList.getChildren().add(cb);
        });

        // add style
        close.getStyleClass().addAll("close-btn", "clickable");
        this.getStyleClass().add("quest-detail");
        nameLabel.getStyleClass().addAll("detail-name", "detail");
        nameField.getStyleClass().addAll("detail-name");
        descLabel.getStyleClass().add("detail");
        descField.getStyleClass().addAll("text-field");
        claim.getStyleClass().addAll("claim-btn", "clickable");
        rewards.getStyleClass().add("section-header");
        tasks.getStyleClass().add("section-header");
        lPanel.getStyleClass().addAll("panel", "l-panel");
        rPanel.getStyleClass().add("panel");
        rewardBox.getStyleClass().add("reward-box");
        taskList.getStyleClass().add("checkbox-list");
        rewardList.getStyleClass().add("checkbox-list");

        // center text
        nameLabel.setAlignment(Pos.CENTER);
        nameField.setAlignment(Pos.CENTER);
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        nameField.setMaxWidth(Double.MAX_VALUE);
        tasks.setAlignment(Pos.CENTER);
        tasks.setMaxWidth(Double.MAX_VALUE);
    }
}
