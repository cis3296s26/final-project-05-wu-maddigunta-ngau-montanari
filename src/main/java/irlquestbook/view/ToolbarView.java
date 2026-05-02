package irlquestbook.view;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import irlquestbook.model.*;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class ToolbarView extends HBox {
    private final ToggleGroup group = new ToggleGroup();

    public ToolbarView(QuestBook qb) {
        ToggleButton normal = makeToolButton(Tool.NORMAL, Feather.MOUSE_POINTER);
        ToggleButton create = makeToolButton(Tool.CREATE, Feather.PLUS_SQUARE);
        ToggleButton delete = makeToolButton(Tool.DELETE, Feather.TRASH_2);
        ToggleButton connect = makeToolButton(Tool.CONNECT, Feather.GIT_BRANCH);

        this.getChildren().addAll(normal, create, delete, connect);

        normal.setSelected(true);

        group.selectedToggleProperty().addListener((obs, old, sel) -> {
            if (sel != null)
                qb.setTool((Tool) sel.getUserData());
            else
                group.selectToggle(old);
        });

        qb.toolProperty().addListener((obs, old, val) -> {
            for (Toggle t : group.getToggles()) {
                if (t.getUserData() == val) {
                    group.selectToggle(t);
                    break;
                }
            }
        });
    }

    private ToggleButton makeToolButton(Tool tool, Ikon ikon) {
        FontIcon icon = new FontIcon(ikon);
        icon.setIconSize(24);

        ToggleButton btn = new ToggleButton();
        btn.setGraphic(icon);
        btn.getStyleClass().addAll("clickable", "edit-toggle");
        btn.setUserData(tool);
        btn.setPrefSize(40, 40);
        btn.setToggleGroup(group);

        return btn;
    }
}
