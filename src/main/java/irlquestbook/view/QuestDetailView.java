package irlquestbook.view;

import irlquestbook.model.*;
import javafx.beans.binding.Bindings;
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
    private Label name, desc;
    private Button claim;

    public QuestDetailView(StackPane root, Quest quest, QuestBook qb, Runnable onClose) {
        // set up hBox
        this.hBox = new HBox();

        // create close button
        Button close = new Button("X");
        close.setOnAction(e -> onClose.run());
        StackPane.setAlignment(close, Pos.TOP_RIGHT);

        // add hBox and button to stackpane
        this.getChildren().addAll(hBox, close);

        // set up info panel
        this.lPanel = new VBox();
        this.rPanel = new VBox();

        this.name = new Label();
        this.desc = new Label();
        this.infoBox = new VBox(10);
        this.infoBox.getChildren().addAll(this.name, this.desc);

        // set up reward box
        Label rewards = new Label("Rewards");
        this.rewardList = new VBox(10);
        this.rewardBox = new VBox(15);
        this.claim = new Button("Claim all rewards");
        this.rewardBox.getChildren().addAll(rewards, rewardList, claim);

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
        close.getStyleClass().addAll("close-btn", "clickable");
        this.getStyleClass().add("quest-detail");
        name.getStyleClass().add("detail-name");
        claim.getStyleClass().addAll("claim-btn", "clickable");
        rewards.getStyleClass().add("section-header");
        tasks.getStyleClass().add("section-header");
        lPanel.getStyleClass().addAll("panel", "l-panel");
        rPanel.getStyleClass().add("panel");
        rewardBox.getStyleClass().add("reward-box");
        taskList.getStyleClass().add("checkbox-list");
        rewardList.getStyleClass().add("checkbox-list");

        // center text
        name.setAlignment(Pos.CENTER);
        name.setMaxWidth(Double.MAX_VALUE);
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

        // set up quest specific elements and bindings
        name.textProperty().bindBidirectional(quest.nameProperty());
        desc.textProperty().bindBidirectional(quest.descriptionProperty());

        // set up subtask boxes
        quest.getSubtasks().forEach(subtask -> {
            CheckBox cb = new CheckBox(subtask.getName());
            cb.getStyleClass().addAll("checkbox", "clickable");
            cb.selectedProperty().bindBidirectional(subtask.completedProperty());
            cb.disableProperty().bind(subtask.completedProperty()
                    .or(quest.stateProperty().isEqualTo(QuestState.LOCKED)));
            taskList.getChildren().add(cb);
        });

        // set up reward boxes
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
    }
}
