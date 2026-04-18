package dev.emmie;

import java.util.function.Consumer;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SidebarView extends VBox {
    public SidebarView(QuestBook qb, Consumer<Page> onPageClick) {
        for (Page page : qb.getPages()) {
            // create a button for each page
            Button btn = new Button(page.pageName());

            // trigger onPageClick consumer when button clicked
            btn.setOnAction(e -> onPageClick.accept(page));

            // set button width
            btn.setMaxWidth(Double.MAX_VALUE);

            // add button to VBox
            this.getChildren().add(btn);

        }

        // add styling
        String css = """
                -fx-background-color: #E8D5B7;
                -fx-border-color: transparent #8B5A3C transparent transparent;
                -fx-border-width: 0 2 0 0;
                    """;
        this.setStyle(css);
    }
}
