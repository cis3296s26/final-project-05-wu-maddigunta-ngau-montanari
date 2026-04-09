package dev.emmie;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class QuestView extends StackPane {
    private double offsetX;
    private double offsetY;
    private Rectangle rect;

    public QuestView(Quest quest) {
        // create quest rectangle
        this.rect = new Rectangle(0, 0, 30, 30);
        rect.setFill(Color.TAN);
        rect.setStroke(Color.BLACK);

        // add it to pane
        this.getChildren().add(rect);

        // handlers for dragging
        this.setOnMousePressed(event -> {
            offsetX = event.getSceneX() - this.getLayoutX();
            offsetY = event.getSceneY() - this.getLayoutY();
        });

        this.setOnMouseDragged(event -> {
            this.setLayoutX(event.getSceneX() - offsetX);
            this.setLayoutY(event.getSceneY() - offsetY);
        });
    }
}
