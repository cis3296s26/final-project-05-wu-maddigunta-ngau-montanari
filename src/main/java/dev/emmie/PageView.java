package dev.emmie;

import java.util.List;
import java.util.HashMap;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class PageView extends Pane {
    private Label tooltip;
    private HashMap<Quest, QuestView> quests;

    public PageView(List<Questline> questlines) {
        // setup tooltip
        this.tooltip = new Label();
        this.getChildren().add(this.tooltip);

        // setup quests hashmap
        this.quests = new HashMap<>();

        // iterate through questlines (page) and create QuestViews
        for (Questline ql : questlines) {
            // create views for each quest in questline
            for (Quest q : ql.getQuests()) {
                // create Questview
                QuestView view = new QuestView(q, 50, 50, this.tooltip);

                // add it to the hashmap
                quests.put(q, view);

                // add it to pane
                this.getChildren().add(view);
            }

            // link up questviews
        }
    }

    private Line connect(Quest source, Quest dest) {
        // lookup both quests

        return new Line();
    }

}
