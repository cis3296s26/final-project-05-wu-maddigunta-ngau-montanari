package irlquestbook;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SidebarView extends VBox {
    private final Map<Page, Button> pageButtons = new HashMap<>();
    private final Consumer<Page> onPageClick;

    public SidebarView(QuestBook qb, Consumer<Page> onPageClick) {
        // store consumer
        this.onPageClick = onPageClick;

        // style the bar
        this.getStyleClass().add("sidebar");

        // set up each button
        qb.getPages().forEach(page -> {
            Button btn = new Button(page.pageName());
            btn.setOnAction(e -> this.select(page));
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.getStyleClass().add("page-btn");
            pageButtons.put(page, btn);
            this.getChildren().add(btn);
        });

        // select first button
        if (!qb.getPages().isEmpty()) {
            select(qb.getPages().get(0));
        }
    }

    private void select(Page page) {
        // style buttons and fire consumer
        pageButtons.values().forEach(b -> b.getStyleClass().remove("selected"));
        pageButtons.get(page).getStyleClass().add("selected");
        onPageClick.accept(page);
    }
}
