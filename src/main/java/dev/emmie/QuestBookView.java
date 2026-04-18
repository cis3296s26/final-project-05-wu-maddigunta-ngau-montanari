package dev.emmie;

import java.util.List;
import java.util.function.Consumer;
import java.util.HashMap;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class QuestBookView extends BorderPane {
    private QuestBook qb;
    private HashMap<Page, PageView> pages;

    public QuestBookView(QuestBook qb, Consumer<Quest> onQuestClick) {
        this.qb = qb;
        this.pages = new HashMap<>();

        // create sidebar
        SidebarView sb = new SidebarView(this.qb, page -> {
            this.switchPage(page);
        });

        // add to borderpane
        this.setLeft(sb);

        // create pageviews for pages and add to map
        for (Page page : this.qb.getPages()) {
            // setup view
            PageView view = new PageView(page, onQuestClick);

            // add to hashmap
            pages.put(page, view);
        }
    }

    private void switchPage(Page page) {
        // get pageview from map
        PageView pv = pages.get(page);

        // put pageview in center of pane
        this.setCenter(pv);
    }
}
