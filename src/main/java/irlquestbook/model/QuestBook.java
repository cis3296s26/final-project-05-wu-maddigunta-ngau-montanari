package irlquestbook.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class QuestBook {
    private List<Page> pages;
    private final BooleanProperty editMode = new SimpleBooleanProperty(false);
    private final ObjectProperty<Tool> tool = new SimpleObjectProperty<>(Tool.NORMAL);

    public QuestBook() {
        this.pages = new ArrayList<>();
    }

    public QuestBook(List<Page> pages) {
        this.pages = pages;
    }

    public BooleanProperty editModeProperty() {
        return editMode;
    }

    public ObjectProperty<Tool> toolProperty() {
        return tool;
    }

    public boolean getEditMode() {
        return editMode.get();
    }

    public Tool getTool() {
        return tool.get();
    }

    public List<Page> getPages() {
        return this.pages;
    }

    public int getPageCount() {
        return this.pages.size();
    }

    public void setEditMode(boolean value) {
        editMode.set(value);
    }

    public void setTool(Tool tool) {
        this.tool.set(tool);
    }

    public void addPage(Page page) {
        this.pages.add(page);
    }

    public void removePage(Page page) {
        this.pages.remove(page);
    }

}
