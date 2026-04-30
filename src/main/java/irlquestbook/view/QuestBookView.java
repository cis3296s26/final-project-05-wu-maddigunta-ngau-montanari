package irlquestbook.view;

import irlquestbook.model.*;

import java.util.function.Consumer;
import java.util.HashMap;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class QuestBookView extends BorderPane {
    private HashMap<Page, PageView> pages;
    private StackPane stackPane;
    private PageView currentPage;

    public QuestBookView(QuestBook qb, Consumer<Quest> onQuestClick) {
        this.pages = new HashMap<>();

        this.stackPane = new StackPane();
        this.setCenter(stackPane);

        // create pageviews for pages and add to map
        for (Page page : qb.getPages()) {
            // setup view
            PageView view = new PageView(page, onQuestClick);

            // add to hashmap
            pages.put(page, view);
        }

        // create sidebar
        SidebarView sb = new SidebarView(qb, page -> {
            PageView newPage = pages.get(page);
            stackPane.getChildren().remove(currentPage);
            stackPane.getChildren().add(0, newPage);
            currentPage = newPage;
        });

        sb.prefWidthProperty().bind(this.widthProperty().multiply(0.25));
        sb.setMinWidth(Region.USE_PREF_SIZE);

        // add sidebar to borderpane
        this.setLeft(sb);
    }
}
