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

            // add button to VBox
            this.getChildren().add(btn);
        }

    }
}
