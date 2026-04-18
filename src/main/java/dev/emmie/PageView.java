package dev.emmie;

import java.util.List;
import java.util.function.Consumer;
import java.util.HashMap;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class PageView extends Pane {
    private Page page;
    private Label tooltip;
    private HashMap<Quest, QuestView> quests;

    public PageView(Page page, Consumer<Quest> onQuestClick) {
        this.page = page;

        // setup tooltip
        this.tooltip = new Label();
        this.tooltip.getStyleClass().add("quest-tooltip");
        this.tooltip.setMouseTransparent(true);
        this.tooltip.setVisible(false);
        this.getChildren().add(this.tooltip);

        // setup quests hashmap
        this.quests = new HashMap<>();

        // styling
        this.setStyle("-fx-background-color: #F5E6D3;");

        // iterate through questlines (page) and create QuestViews
        for (Questline ql : this.page.getQuestlines()) {
            // create views for each quest in questline
            int i = 0;
            for (Quest q : ql.getQuests()) {
                // create Questview
                // TODO: quest position should be a part of the Quest model
                double x = 100 + (i % 2) * 150;
                double y = 100 + (i / 2) * 150;
                QuestView view = new QuestView(q, x, y, this.tooltip, onQuestClick);
                i++;

                // add it to the hashmap
                quests.put(q, view);

                // add it to pane
                this.getChildren().add(view);
            }

            // link up questviews
            for (Quest q : ql.getQuests()) {
                for (Quest prev : q.getPrevQuests()) {
                    Line line = connect(q, prev);
                    this.getChildren().add(line);
                    line.toBack();
                }
            }
        }
    }

    private Line connect(Quest source, Quest dest) {
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

}
