package irlquestbook.view;

import irlquestbook.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SidebarView extends VBox {

    private final Map<Page, Button> pageButtons = new HashMap<>();
    private final Consumer<Page> onPageClick;
    private final QuestBook qb;

    public SidebarView(QuestBook qb, Consumer<Page> onPageClick) {
        this.qb = qb;
        this.onPageClick = onPageClick;

        this.getStyleClass().add("sidebar");

        for (Page page : qb.getPages()) {
            addPageButton(page);
        }

        Button addPageBtn = new Button("+ Add Page");
        addPageBtn.setMaxWidth(Double.MAX_VALUE);
        addPageBtn.getStyleClass().addAll("page-btn", "clickable");

        addPageBtn.visibleProperty().bind(qb.editModeProperty());
        addPageBtn.managedProperty().bind(qb.editModeProperty());

        addPageBtn.setOnAction(e -> {
            Page newPage = new Page(new ArrayList<>(), "");
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
        HBox row = new HBox(5);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("sidebar-row");

        Button btn = new Button(page.nameProperty().get());

        btn.textProperty().bindBidirectional(page.nameProperty());
        btn.setOnAction(e -> select(page));
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.getStyleClass().addAll("page-btn", "clickable");
        HBox.setHgrow(btn, Priority.ALWAYS);
        btn.visibleProperty().bind(qb.editModeProperty().not());
        btn.managedProperty().bind(qb.editModeProperty().not());

        TextField nameField = new TextField();
        nameField.textProperty().bindBidirectional(page.nameProperty());
        nameField.getStyleClass().addAll("text-field", "page-text");
        nameField.setOnAction(e -> qb.setEditMode(false));
        HBox.setHgrow(nameField, Priority.ALWAYS);

        nameField.visibleProperty().bind(qb.editModeProperty());
        nameField.managedProperty().bind(qb.editModeProperty());

        Button deleteBtn = new Button();
        FontIcon trashIcon = new FontIcon(Feather.TRASH_2);
        deleteBtn.setGraphic(trashIcon);
        trashIcon.setIconSize(16);
        deleteBtn.getStyleClass().addAll("close-btn", "clickable");

        deleteBtn.visibleProperty().bind(qb.editModeProperty());
        deleteBtn.managedProperty().bind(qb.editModeProperty());

        deleteBtn.setOnAction(e -> {
            qb.removePage(page);
            this.getChildren().remove(row);
            pageButtons.remove(page);

            if (!qb.getPages().isEmpty()) {
                select(qb.getPages().get(0));
            } else {
                onPageClick.accept(null);
            }
        });

        row.getChildren().addAll(btn, nameField, deleteBtn);
        pageButtons.put(page, btn);

        int index = Math.max(0, this.getChildren().size() - 1);
        this.getChildren().add(index, row);
    }

    private void select(Page page) {
        pageButtons.values().forEach(b -> b.getStyleClass().remove("selected"));
        if (page != null && pageButtons.containsKey(page)) {
            pageButtons.get(page).getStyleClass().add("selected");
        }
        onPageClick.accept(page);
    }
}
