package irlquestbook.view;

import irlquestbook.model.Quest;
import irlquestbook.model.QuestBook;
import irlquestbook.model.QuestState;
import irlquestbook.model.Reward;
import irlquestbook.model.Subtask;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    private Quest quest;
    private QuestBook qb;

    public QuestDetailView(StackPane root, Quest quest, QuestBook qb, Runnable onClose) {
        this.quest = quest;
        this.qb = qb;

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
        claim.visibleProperty().bind(qb.editModeProperty().not());
        claim.managedProperty().bind(qb.editModeProperty().not());
        claim.setOnAction(e -> {
            quest.getRewards().forEach(reward -> {
                reward.setClaimed(true);
            });
        });

        Button addRewardButton = new Button("Add reward");
        addRewardButton.setMaxWidth(Double.MAX_VALUE);
        addRewardButton.visibleProperty().bind(qb.editModeProperty());
        addRewardButton.managedProperty().bind(qb.editModeProperty());
        addRewardButton.getStyleClass().addAll("generic-btn", "clickable");
        addRewardButton.setOnAction(e -> {
            Reward r = new Reward("");
            quest.getRewards().add(r);
            addReward(r);
        });

        // set up reward list
        quest.getRewards().forEach(reward -> this.addReward(reward));
        ScrollPane rewardScroll = new ScrollPane(rewardList);
        rewardScroll.setFitToWidth(true);
        rewardScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rewardScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        this.rewardBox.setMinHeight(Region.USE_PREF_SIZE);
        this.rewardBox.prefHeightProperty().bind(lPanel.heightProperty().multiply(0.4));
        VBox.setVgrow(this.infoBox, Priority.ALWAYS);

        this.rewardBox.getChildren().addAll(rewards, rewardScroll, addRewardButton, claim);

        this.lPanel.getChildren().addAll(this.infoBox, this.rewardBox);

        // bind button enable to completed status
        claim.disableProperty().bind(Bindings.createBooleanBinding(
                () -> quest.stateProperty().get() != QuestState.COMPLETED
                        || quest.getRewards().isEmpty(),
                quest.stateProperty(), quest.getRewards()).or(qb.editModeProperty()));

        // set up task panel
        Label tasks = new Label("Tasks");
        this.taskList = new VBox(10);
        this.taskBox = new VBox(15);
        Button addSubtaskButton = new Button("Add subtask");
        addSubtaskButton.setMaxWidth(Double.MAX_VALUE);
        addSubtaskButton.visibleProperty().bind(qb.editModeProperty());
        addSubtaskButton.managedProperty().bind(qb.editModeProperty());
        addSubtaskButton.getStyleClass().addAll("generic-btn", "clickable");
        addSubtaskButton.setOnAction(e -> {
            Subtask s = new Subtask("");
            quest.getSubtasks().add(s);
            addSubtask(s);
        });

        // set up task list
        quest.getSubtasks().forEach(subtask -> addSubtask(subtask));

        ScrollPane taskScroll = new ScrollPane(taskList);
        taskScroll.setFitToWidth(true);
        taskScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        taskScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        this.taskBox.getChildren().addAll(tasks, taskScroll, addSubtaskButton);
        this.rPanel.getChildren().add(taskBox);

        // add style
        close.getStyleClass().addAll("close-btn", "clickable");
        this.getStyleClass().add("quest-detail");
        nameLabel.getStyleClass().addAll("detail-name", "detail");
        nameField.getStyleClass().addAll("detail-name");
        descLabel.getStyleClass().add("detail");
        descField.getStyleClass().addAll("text-field");
        claim.getStyleClass().addAll("generic-btn", "clickable");
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

    private void addSubtask(Subtask subtask) {
        SubtaskView row = new SubtaskView(subtask, quest, qb.editModeProperty(), this::deleteSubtask);
        taskList.getChildren().add(row);
    }

    private void addReward(Reward reward) {
        RewardView row = new RewardView(reward, quest, qb.editModeProperty(), this::deleteReward);
        rewardList.getChildren().add(row);
    }

    private void deleteSubtask(SubtaskView row) {
        quest.getSubtasks().remove(row.getSubtask());
        taskList.getChildren().remove(row);
    }

    private void deleteReward(RewardView row) {
        quest.getRewards().remove(row.getReward());
        rewardList.getChildren().remove(row);
    }
}
