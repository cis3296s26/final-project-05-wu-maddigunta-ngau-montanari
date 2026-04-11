package dev.emmie;

import java.util.function.Consumer;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class QuestView extends StackPane {
    private double offsetX;
    private double offsetY;
    private double startX;
    private double startY;
    private Rectangle rect;
    private boolean isDragging = false;
    private Quest quest;

    public QuestView(Quest quest, double x, double y, Label tooltip, Consumer<Quest> onClick) {
        // store fields
        this.quest = quest;

        // create quest rectangle
        this.rect = new Rectangle(0, 0, 30, 30);
        this.rect.setFill(Color.TAN);
        this.rect.setStroke(Color.BLACK);

        // add it to pane
        this.getChildren().add(this.rect);
        this.setLayoutX(x);
        this.setLayoutY(y);

        // handlers for dragging
        this.setOnMousePressed(event -> {
            this.isDragging = true;
            tooltip.setVisible(false);
            this.offsetX = event.getSceneX() - this.getLayoutX();
            this.offsetY = event.getSceneY() - this.getLayoutY();

            this.startX = event.getSceneX();
            this.startY = event.getSceneY();
        });

        this.setOnMouseDragged(event -> {
            this.setLayoutX(event.getSceneX() - this.offsetX);
            this.setLayoutY(event.getSceneY() - this.offsetY);
        });

        this.setOnMouseReleased(event -> {
            this.isDragging = false;

            // if mouse didnt move significantly then trigger onClick
            double dx = event.getSceneX() - this.startX;
            double dy = event.getSceneY() - this.startY;
            if (Math.abs(dx) < 5 && Math.abs(dy) < 5) {
                onClick.accept(quest);
            }
        });

        // handlers for tooltip config
        this.setOnMouseEntered(event -> {
            tooltip.setText(this.quest.getName());
            tooltip.toFront();
            if (!this.isDragging) {
                tooltip.setVisible(true);
            }
        });

        this.setOnMouseExited(event -> {
            tooltip.setVisible(false);
        });

        this.setOnMouseMoved(event -> {
            tooltip.setLayoutX(event.getSceneX());
            tooltip.setLayoutY(event.getSceneY() - 20);
            if (!this.isDragging) {
                tooltip.setVisible(true);
            }
        });
    }
}
