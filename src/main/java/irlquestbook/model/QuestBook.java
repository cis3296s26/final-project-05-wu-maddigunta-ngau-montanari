package irlquestbook.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class QuestBook {
    private List<Page> pages;
    private final BooleanProperty editMode = new SimpleBooleanProperty(false);

    public QuestBook() {
        this.pages = new ArrayList<>();
    }

    public QuestBook(List<Page> pages) {
        this.pages = pages;
    }

    public BooleanProperty editModeProperty() {
        return editMode;
    }

    public boolean getEditMode() {
        return editMode.get();
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

    public void addPage(Page page) {
        this.pages.add(page);
    }

    public void removePage(Page page) {
        this.pages.remove(page);
    }

}
