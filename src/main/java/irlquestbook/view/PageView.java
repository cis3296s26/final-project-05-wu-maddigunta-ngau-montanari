package irlquestbook.view;

import irlquestbook.model.*;

import java.util.function.Consumer;
import java.util.HashMap;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class PageView extends Pane {
    private Page page;
    private Label tooltip;
    private HashMap<Quest, QuestView> quests;
    private QuestBook qb;
    private Quest pendingSource = null;

    public PageView(Page page, QuestBook qb, Consumer<Quest> onQuestClick) {
        this.page = page;
        this.qb = qb;

        // setup tooltip
        this.tooltip = new Label();
        this.tooltip.getStyleClass().add("quest-tooltip");
        this.tooltip.setMouseTransparent(true);
        this.tooltip.setVisible(false);
        this.getChildren().add(this.tooltip);

        // setup quests hashmap
        this.quests = new HashMap<>();

        // styling
        this.getStyleClass().add("page");

        this.setOnMouseClicked(e -> {
            if (qb.getTool() == Tool.CREATE && e.getTarget() == this) {
                page.handleQuestCreate(e.getX(), e.getY());
            }
        });

        // create views for each quest
        for (Quest q : page.getQuests()) {
            // create Questview
            QuestView view = new QuestView(q, this.tooltip, quest -> {
                switch (qb.getTool()) {
                    case NORMAL -> onQuestClick.accept(quest);
                    case CREATE -> {
                    }
                    case DELETE -> {
                        page.handleQuestDelete(quest);
                    }
                    case CONNECT -> {
                        handleConnect(quest);
                    }
                }
            });

            // add it to the hashmap
            quests.put(q, view);

            // add it to pane
            this.getChildren().add(view);
        }

        // link up questviews
        for (Quest q : page.getQuests()) {
            for (Quest prev : q.getPrereqs()) {
                Line line = drawConnection(q, prev);
                this.getChildren().add(line);
                line.toBack();
            }
        }
    }

    private Line drawConnection(Quest source, Quest dest) {
        // lookup both questviews
        QuestView sV = this.quests.get(source);
        QuestView dV = this.quests.get(dest);

        // construct line from source to dest
        Line line = new Line();
        line.setStrokeWidth(3);

        // link the line to the actual coords of the views
        line.startXProperty().bind(sV.layoutXProperty().add(15));
        line.startYProperty().bind(sV.layoutYProperty().add(15));
        line.endXProperty().bind(dV.layoutXProperty().add(15));
        line.endYProperty().bind(dV.layoutYProperty().add(15));

        return line;
    }

    private void handleConnect(Quest clicked) {
        if (pendingSource == null) {
            pendingSource = clicked;
        } else {
            page.handleQuestConnect(pendingSource, clicked);
            pendingSource = null;
        }
    }

    public Page getPage() {
        return this.page;
    }
}
