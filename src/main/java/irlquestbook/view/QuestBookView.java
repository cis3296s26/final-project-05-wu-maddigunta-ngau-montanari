package irlquestbook.view;

import java.util.HashMap;
import java.util.function.Consumer;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import irlquestbook.model.Page;
import irlquestbook.model.Quest;
import irlquestbook.model.QuestBook;
import irlquestbook.model.Tool;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class QuestBookView extends BorderPane {

    private HashMap<Page, PageView> pages;
    private StackPane stackPane;
    private PageView currentPage;

    public QuestBookView(QuestBook qb, Consumer<Quest> onQuestClick) {
        this.pages = new HashMap<>();

        // put stackpane in center (pageview goes in this)
        this.stackPane = new StackPane();
        this.setCenter(stackPane);

        // create pageviews for pages and add to map
        for (Page page : qb.getPages()) {
            // setup view
            PageView view = new PageView(page, qb, onQuestClick);

            // add to hashmap
            pages.put(page, view);
        }

        // disable tool when exiting edit mode
        qb.editModeProperty().addListener((obs, was, now) -> {
            if (!now) {
                qb.setTool(Tool.NORMAL);
            }
        });

        // create toggle button for edit mode
        ToggleButton editToggle = new ToggleButton();
        FontIcon pencil = new FontIcon(Feather.EDIT_2);
        pencil.setIconSize(24);
        editToggle.setGraphic(pencil);
        editToggle.getStyleClass().addAll("edit-toggle", "clickable");
        editToggle.setPrefSize(40, 40);
        editToggle.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        editToggle.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        editToggle.selectedProperty().bindBidirectional(qb.editModeProperty());

        // create toolbar
        ToolbarView toolbar = new ToolbarView(qb);
        toolbar.visibleProperty().bind(qb.editModeProperty());
        toolbar.managedProperty().bind(qb.editModeProperty());
        toolbar.setMaxWidth(Region.USE_PREF_SIZE);
        toolbar.setMaxHeight(Region.USE_PREF_SIZE);

        // add it to stackpane
        this.stackPane.getChildren().addAll(editToggle, toolbar);
        StackPane.setAlignment(toolbar, Pos.TOP_CENTER);
        StackPane.setAlignment(editToggle, Pos.TOP_RIGHT);
        StackPane.setMargin(editToggle, new Insets(12));
        StackPane.setMargin(toolbar, new Insets(12));
        this.stackPane.setPadding(Insets.EMPTY);

        // create sidebar
        SidebarView sb = new SidebarView(qb, page -> {
            if (page == null) {
                stackPane.getChildren().remove(currentPage);
                currentPage = null;
                return;
            }
            PageView newPage = pages.get(page);
            if (newPage == null) {
                newPage = new PageView(page, qb, onQuestClick);
                pages.put(page, newPage);
            }

            // 4. Swap the views
            if (currentPage != null) {
                stackPane.getChildren().remove(currentPage);
            }
            stackPane.getChildren().add(0, newPage);
            currentPage = newPage;
        });

        sb.prefWidthProperty().bind(this.widthProperty().multiply(0.25));
        sb.setMinWidth(Region.USE_PREF_SIZE);

        // add sidebar to borderpane
        this.setLeft(sb);
    }

}
