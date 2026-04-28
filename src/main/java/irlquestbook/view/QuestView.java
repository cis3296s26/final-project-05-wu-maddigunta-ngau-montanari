package irlquestbook.view;

import irlquestbook.model.*;

import java.util.function.Consumer;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.css.PseudoClass;

public class QuestView extends StackPane {
    private double offsetX;
    private double offsetY;
    private double startX;
    private double startY;
    private boolean isDragging = false;
    private Quest quest;

    private static final PseudoClass LOCKED = PseudoClass.getPseudoClass("locked");
    private static final PseudoClass AVAILABLE = PseudoClass.getPseudoClass("available");
    private static final PseudoClass COMPLETED = PseudoClass.getPseudoClass("completed");
    private static final PseudoClass CLAIMED = PseudoClass.getPseudoClass("claimed");

    public QuestView(Quest quest, Label tooltip, Consumer<Quest> onClick) {
        // store fields
        this.quest = quest;

        // add styling
        setPrefSize(30, 30);
        setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        getStyleClass().add("quest");
        quest.stateProperty().addListener((obs, oldState, newState) -> updateStateClass(newState));
        updateStateClass(quest.stateProperty().get());

        // bind pos to quest pos
        layoutXProperty().addListener((obs, oldVal, newVal) -> quest.setX(newVal.doubleValue()));
        layoutYProperty().addListener((obs, oldVal, newVal) -> quest.setY(newVal.doubleValue()));
        this.setLayoutX(this.quest.getX());
        this.setLayoutY(this.quest.getY());

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
            Point2D local = ((Node) this.getParent()).sceneToLocal(event.getSceneX(), event.getSceneY());
            tooltip.setLayoutX(local.getX() + 15);
            tooltip.setLayoutY(local.getY() + 5);
            if (!this.isDragging) {
                tooltip.setVisible(true);
            }
        });
    }

    private void updateStateClass(QuestState s) {
        pseudoClassStateChanged(LOCKED, s == QuestState.LOCKED);
        pseudoClassStateChanged(AVAILABLE, s == QuestState.AVAILABLE);
        pseudoClassStateChanged(COMPLETED, s == QuestState.COMPLETED);
        pseudoClassStateChanged(CLAIMED, s == QuestState.CLAIMED);
    }
}
