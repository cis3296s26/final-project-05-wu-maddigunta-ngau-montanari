package irlquestbook.view;

import java.util.function.Consumer;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import irlquestbook.model.*;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class RewardView extends HBox {
    private final Reward reward;

    public RewardView(Reward reward, Quest quest, BooleanProperty editMode, Consumer<RewardView> onDelete) {
        this.reward = reward;
        this.setSpacing(10);

        CheckBox cb = new CheckBox();
        cb.getStyleClass().addAll("checkbox", "clickable");
        cb.selectedProperty().bindBidirectional(reward.claimedProperty());
        cb.disableProperty().bind(reward.claimedProperty()
                .or(quest.stateProperty().isNotEqualTo(QuestState.COMPLETED)));

        Label nameLabel = new Label();
        nameLabel.textProperty().bind(reward.nameProperty());
        nameLabel.visibleProperty().bind(editMode.not());
        nameLabel.managedProperty().bind(editMode.not());
        nameLabel.getStyleClass().addAll("detail", "checkbox-row");

        TextField nameField = new TextField();
        nameField.textProperty().bindBidirectional(reward.nameProperty());
        nameField.visibleProperty().bind(editMode);
        nameField.managedProperty().bind(editMode);
        nameField.getStyleClass().addAll("checkbox-row");

        Button delete = new Button();
        FontIcon trashIcon = new FontIcon(Feather.TRASH_2);
        delete.setGraphic(trashIcon);
        delete.setOnAction(e -> onDelete.accept(this));
        delete.visibleProperty().bind(editMode);
        delete.managedProperty().bind(editMode);
        delete.getStyleClass().addAll("clickable", "generic-btn");

        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        HBox.setHgrow(nameField, Priority.ALWAYS);
        this.getChildren().addAll(cb, nameLabel, nameField, delete);
    }

    public Reward getReward() {
        return reward;
    }

}
