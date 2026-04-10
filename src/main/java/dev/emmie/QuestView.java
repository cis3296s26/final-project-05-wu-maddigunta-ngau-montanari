package dev.emmie;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.paint.Color;

public class QuestView extends StackPane {
    private double offsetX;
    private double offsetY;
    private Rectangle rect;
    private boolean isDragging = false;

    public QuestView(Quest quest, Label tooltip) {
        // create quest rectangle
        this.rect = new Rectangle(0, 0, 30, 30);
        rect.setFill(Color.TAN);
        rect.setStroke(Color.BLACK);

        // add it to pane
        this.getChildren().add(rect);

        // handlers for dragging
        this.setOnMousePressed(event -> {
            isDragging = true;
            tooltip.setVisible(false);
            offsetX = event.getSceneX() - this.getLayoutX();
            offsetY = event.getSceneY() - this.getLayoutY();
        });

        this.setOnMouseDragged(event -> {
            this.setLayoutX(event.getSceneX() - offsetX);
            this.setLayoutY(event.getSceneY() - offsetY);
        });

        this.setOnMouseReleased(event -> {
            isDragging = false;
        });

        // handlers for tooltip config
        this.setOnMouseEntered(event -> {
            tooltip.setText(quest.getName());
            tooltip.toFront();
            if (!isDragging) {
                tooltip.setVisible(true);
            }
        });

        this.setOnMouseExited(event -> {
            tooltip.setVisible(false);
        });

        this.setOnMouseMoved(event -> {
            tooltip.setLayoutX(event.getSceneX());
            tooltip.setLayoutY(event.getSceneY() - 20);
            if (!isDragging) {
                tooltip.setVisible(true);
            }
        });
    }
}
