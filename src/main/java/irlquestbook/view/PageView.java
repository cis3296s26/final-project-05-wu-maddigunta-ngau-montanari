package irlquestbook.view;

import irlquestbook.model.*;

import java.util.function.Consumer;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class PageView extends Pane {
    private Page page;
    private Label tooltip;
    private final Map<Quest, QuestView> quests = new HashMap<>();
    private final Map<Connection, Line> connections = new HashMap<>();

    private record Connection(Quest source, Quest dest) {
    }

    private QuestBook qb;
    private Quest pendingSource = null;
    private final Consumer<Quest> onQuestClick;

    public PageView(Page page, QuestBook qb, Consumer<Quest> onQuestClick) {
        this.page = page;
        this.qb = qb;

        this.onQuestClick = quest -> {
            switch (qb.getTool()) {
                case NORMAL -> onQuestClick.accept(quest);
                case CREATE -> {
                }
                case DELETE -> {
                    page.handleQuestDelete(quest);
                }
                case CONNECT -> {
                    handleConnect(quest);
                }
            }
        };

        // setup tooltip
        this.tooltip = new Label();
        this.tooltip.getStyleClass().add("quest-tooltip");
        this.tooltip.setMouseTransparent(true);
        this.tooltip.setVisible(false);
        this.getChildren().add(this.tooltip);

        // styling
        this.getStyleClass().add("page");

        this.setOnMouseClicked(e -> {
            if (qb.getTool() == Tool.CREATE && e.getTarget() == this) {
                page.handleQuestCreate(e.getX(), e.getY());
            }
        });

        // create views for each quest
        page.getQuests().forEach(this::addQuestView);

        // add listener for quests
        page.getQuests().addListener((ListChangeListener<Quest>) change -> {
            while (change.next()) {
                change.getRemoved().forEach(this::removeQuestView);
                change.getAddedSubList().forEach(this::addQuestView);
            }
        });

        // link up questviews
        page.getQuests().forEach(dest -> dest.getPrereqs().forEach(src -> addConnection(src, dest)));
    }

    private void addQuestView(Quest q) {
        // create Questview
        QuestView view = new QuestView(q, this.tooltip, this.onQuestClick);

        // add it to the hashmap
        quests.put(q, view);

        // add it to pane
        this.getChildren().add(view);

        // add listener for connections
        q.getPrereqs().addListener((ListChangeListener<Quest>) change -> {
            while (change.next()) {
                change.getRemoved().forEach(removed -> removeConnection(removed, q));
                change.getAddedSubList().forEach(added -> addConnection(added, q));
            }
        });
    }

    private void removeQuestView(Quest q) {
        QuestView view = quests.remove(q);
        this.getChildren().remove(view);

        connections.entrySet().removeIf(entry -> {
            Connection c = entry.getKey();
            if (c.source() == q || c.dest() == q) {
                this.getChildren().remove(entry.getValue());
                return true;
            }

            return false;
        });
    }

    private void addConnection(Quest source, Quest dest) {
        System.out.println("addConnection: source=" + source + " dest=" + dest + " keys=" + quests.keySet());
        Line line = drawConnection(source, dest);
        this.getChildren().add(line);
        line.toBack();
        connections.put(new Connection(source, dest), line);
    }

    private void removeConnection(Quest source, Quest dest) {
        Line line = connections.remove(new Connection(source, dest));
        this.getChildren().remove(line);
    }

    private Line drawConnection(Quest source, Quest dest) {
        // lookup both questviews
        QuestView sV = this.quests.get(source);
        QuestView dV = this.quests.get(dest);

        // construct line from source to dest
        Line line = new Line();
        line.setStrokeWidth(3);

        // link the line to the actual coords of the views
        line.startXProperty().bind(sV.layoutXProperty().add(15));
        line.startYProperty().bind(sV.layoutYProperty().add(15));
        line.endXProperty().bind(dV.layoutXProperty().add(15));
        line.endYProperty().bind(dV.layoutYProperty().add(15));

        return line;
    }

    private void handleConnect(Quest clicked) {
        if (pendingSource == null) {
            pendingSource = clicked;
        } else {
            page.handleQuestConnect(pendingSource, clicked);
            pendingSource = null;
        }
    }

    public Page getPage() {
        return this.page;
    }
}
