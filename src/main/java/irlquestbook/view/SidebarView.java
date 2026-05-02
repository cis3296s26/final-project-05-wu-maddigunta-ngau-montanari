package irlquestbook.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import irlquestbook.model.Page;
import irlquestbook.model.QuestBook;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SidebarView extends VBox {

    private final Map<Page, Button> pageButtons = new HashMap<>();
    private final Consumer<Page> onPageClick;
    private final QuestBook qb;

    public SidebarView(QuestBook qb, Consumer<Page> onPageClick) {
        // store consumer
        this.qb = qb;
        this.onPageClick = onPageClick;

        // style the bar
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
        // 1. Create the HBox container for the row
        javafx.scene.layout.HBox row = new javafx.scene.layout.HBox(5); // 5px spacing
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        row.getStyleClass().add("sidebar-row");

        // 2. The Page Button (Main click area)
        Button btn = new Button(page.getName().get());

        btn.textProperty().bindBidirectional(page.getName());
        btn.setOnAction(e -> select(page));
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.getStyleClass().addAll("page-btn", "clickable");
        javafx.scene.layout.HBox.setHgrow(btn, javafx.scene.layout.Priority.ALWAYS);
        btn.visibleProperty().bind(qb.editModeProperty().not());
        btn.managedProperty().bind(qb.editModeProperty().not());

        javafx.scene.control.TextField nameField = new javafx.scene.control.TextField();
        nameField.textProperty().bindBidirectional(page.getName());
        nameField.getStyleClass().add("text-field");
        nameField.setOnAction(e -> qb.setEditMode(false));
        javafx.scene.layout.HBox.setHgrow(nameField, javafx.scene.layout.Priority.ALWAYS);

        // Show field only when editing
        nameField.visibleProperty().bind(qb.editModeProperty());
        nameField.managedProperty().bind(qb.editModeProperty());

        // 3. The Delete Icon (Button with Trash Icon)
        Button deleteBtn = new Button();
        FontIcon trashIcon = new FontIcon(Feather.TRASH_2);
        deleteBtn.setGraphic(trashIcon);
        trashIcon.setIconSize(16);
        deleteBtn.getStyleClass().addAll("close-btn", "clickable");

        // Only show delete icons when in edit mode
        deleteBtn.visibleProperty().bind(qb.editModeProperty());
        deleteBtn.managedProperty().bind(qb.editModeProperty());

        deleteBtn.setOnAction(e -> {
            qb.removePage(page);
            this.getChildren().remove(row); // Remove the whole HBox
            pageButtons.remove(page);

            if (!qb.getPages().isEmpty()) {
                select(qb.getPages().get(0));
            } else {
                onPageClick.accept(null);
            }
        });

        // 4. Assemble and Add to UI
        row.getChildren().addAll(btn, nameField, deleteBtn);
        pageButtons.put(page, btn); // Map the page to the button for selection styling

        // Insert before the "+ Add Page" button
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
