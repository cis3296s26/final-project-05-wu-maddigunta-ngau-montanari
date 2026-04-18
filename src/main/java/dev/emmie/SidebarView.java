package dev.emmie;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SidebarView extends VBox {
    private final Map<Page, Button> pageButtons = new HashMap<>();
    private final Consumer<Page> onPageClick;

    private static final String BASE_STYLE = """
            -fx-border-color: #3E2817 transparent;
            -fx-font-size: 16;
            -fx-border-width: 1 0;
            -fx-pref-height: 40;
            """;
    private static final String SIDEBAR_STYLE = """
            -fx-background-color: #E8D5B7;
            -fx-border-color: transparent #3E2817 transparent transparent;
            -fx-border-width: 0 2 0 0;
            """;
    private static final String UNSELECTED_BG = "-fx-background-color: #E8D5B7;";
    private static final String SELECTED_BG = "-fx-background-color: #C9A57B;";

    public SidebarView(QuestBook qb, Consumer<Page> onPageClick) {
        // store consumer
        this.onPageClick = onPageClick;

        // style the bar
        this.setStyle(SIDEBAR_STYLE);

        // set up each button
        qb.getPages().forEach(page -> {
            Button btn = new Button(page.pageName());
            btn.setOnAction(e -> this.select(page));
            btn.setMaxWidth(Double.MAX_VALUE);
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
        pageButtons.forEach((p, btn) -> btn.setStyle(BASE_STYLE + (p.equals(page) ? SELECTED_BG : UNSELECTED_BG)));
        onPageClick.accept(page);
    }
}
