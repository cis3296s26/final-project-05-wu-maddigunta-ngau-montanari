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

public class SubtaskView extends HBox {
    private final Subtask subtask;

    public SubtaskView(Subtask subtask, Quest quest, BooleanProperty editMode, Consumer<SubtaskView> onDelete) {
        this.subtask = subtask;
        this.setSpacing(10);

        CheckBox cb = new CheckBox();
        cb.getStyleClass().addAll("checkbox", "clickable");
        cb.selectedProperty().bindBidirectional(subtask.completedProperty());
        cb.disableProperty().bind(subtask.completedProperty()
                .or(quest.stateProperty().isEqualTo(QuestState.LOCKED)));

        Label nameLabel = new Label();
        nameLabel.textProperty().bind(subtask.nameProperty());
        nameLabel.visibleProperty().bind(editMode.not());
        nameLabel.managedProperty().bind(editMode.not());
        nameLabel.getStyleClass().addAll("detail", "checkbox-row");

        TextField nameField = new TextField();
        nameField.textProperty().bindBidirectional(subtask.nameProperty());
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

    public Subtask getSubtask() {
        return subtask;
    }

}
