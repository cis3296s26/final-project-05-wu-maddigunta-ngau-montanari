package irlquestbook.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import irlquestbook.model.Page;
import irlquestbook.model.QuestBook;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class SidebarView extends VBox {

    private final Map<Page, Button> pageButtons = new HashMap<>();
    private final Consumer<Page> onPageClick;
    private final QuestBook qb;

    public SidebarView(QuestBook qb, Consumer<Page> onPageClick) {
        this.qb = qb;
        this.onPageClick = onPageClick;
        this.getStyleClass().add("sidebar");

        // 1. Add existing pages
        for (Page page : qb.getPages()) {
            addPageButton(page);
        }

        // 2. Setup the "Add Page" button
        Button addPageBtn = new Button("+ Add Page");
        addPageBtn.setMaxWidth(Double.MAX_VALUE);
        addPageBtn.getStyleClass().addAll("page-btn", "clickable");

        addPageBtn.visibleProperty().bind(qb.editModeProperty());
        addPageBtn.managedProperty().bind(qb.editModeProperty());

        addPageBtn.setOnAction(e -> {
            Page newPage = new Page(new ArrayList<>(), "New Page");
            qb.addPage(newPage);
            addPageButton(newPage);
            select(newPage);
        });

        this.getChildren().add(addPageBtn);

        if (!qb.getPages().isEmpty()) {
            select(qb.getPages().get(0));
        }
    }

    private void addPageButton(Page page) {
        Button btn = new Button(page.pageName());
        btn.setOnAction(e -> select(page));
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.getStyleClass().addAll("page-btn", "clickable");

        ContextMenu menu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete Page");

        deleteItem.setOnAction(e -> {
            qb.removePage(page);
            this.getChildren().remove(btn);
            pageButtons.remove(page);
            if (!qb.getPages().isEmpty()) {
                select(qb.getPages().get(0));
            } else {
                onPageClick.accept(null);
            }
        });

        menu.getItems().add(deleteItem);
        btn.setContextMenu(menu);
        pageButtons.put(page, btn);

        int index = Math.max(0, this.getChildren().size() - 1);
        this.getChildren().add(index, btn);
    }

    private void select(Page page) {
        pageButtons.values().forEach(b -> b.getStyleClass().remove("selected"));
        if (page != null && pageButtons.containsKey(page)) {
            pageButtons.get(page).getStyleClass().add("selected");
        }
        onPageClick.accept(page);
    }
}