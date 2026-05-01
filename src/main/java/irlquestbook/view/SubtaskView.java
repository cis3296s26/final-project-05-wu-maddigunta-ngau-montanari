package irlquestbook.view;

import java.util.function.Consumer;

import irlquestbook.model.*;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SubtaskView extends HBox {
    private final Subtask subtask;

    public SubtaskView(Subtask subtask, Quest quest, BooleanProperty editMode, Consumer<SubtaskView> onDelete) {
        this.subtask = subtask;

        CheckBox cb = new CheckBox();
        cb.getStyleClass().addAll("checkbox", "clickable");
        cb.selectedProperty().bindBidirectional(subtask.completedProperty());
        cb.disableProperty().bind(subtask.completedProperty()
                .or(quest.stateProperty().isEqualTo(QuestState.LOCKED)));

        Label nameLabel = new Label();
        nameLabel.textProperty().bind(subtask.nameProperty());
        nameLabel.visibleProperty().bind(editMode.not());
        nameLabel.managedProperty().bind(editMode.not());
        nameLabel.getStyleClass().add("detail");

        TextField nameField = new TextField();
        nameField.textProperty().bind(subtask.nameProperty());
        nameField.visibleProperty().bind(editMode);
        nameField.managedProperty().bind(editMode);

        Button delete = new Button();
        delete.setOnAction(e -> onDelete.accept(this));
        delete.visibleProperty().bind(editMode);
        delete.managedProperty().bind(editMode);
        delete.getStyleClass().addAll("clickable", "generic-btn");

        this.getChildren().addAll(cb, nameLabel, nameField, delete);
    }

    public Subtask getSubtask() {
        return subtask;
    }

}
