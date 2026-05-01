package irlquestbook.view;

import irlquestbook.model.*;

import java.util.function.Consumer;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.HashMap;

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
            PageView view = new PageView(page, onQuestClick);

            // add to hashmap
            pages.put(page, view);
        }

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

        // add it to stackpane
        this.stackPane.getChildren().add(editToggle);
        StackPane.setAlignment(editToggle, Pos.TOP_RIGHT);
        StackPane.setMargin(editToggle, new Insets(12));
        this.stackPane.setPadding(Insets.EMPTY);

        // create sidebar
        // Inside QuestBookView constructor
        SidebarView sb = new SidebarView(qb, page -> {
            // 1. If page is null (all pages deleted), clear the screen
            if (page == null) {
                stackPane.getChildren().remove(currentPage);
                currentPage = null;
                return;
            }

            // 2. Get the view from the map
            PageView newPage = pages.get(page);

            // 3. LAZY LOADING: If the view doesn't exist yet (it's a newly added page), create it!
            if (newPage == null) {
                newPage = new PageView(page, onQuestClick);
                pages.put(page, newPage);
            }

            // 4. Swap the views
            if (currentPage != null) {
                stackPane.getChildren().remove(currentPage);
            }

            // Add at index 0 so the edit button (index 1) stays on top
            stackPane.getChildren().add(0, newPage);
            currentPage = newPage;
        });
        
        sb.prefWidthProperty().bind(this.widthProperty().multiply(0.25));
        sb.setMinWidth(Region.USE_PREF_SIZE);

        // add sidebar to borderpane
        this.setLeft(sb);
    }
}
