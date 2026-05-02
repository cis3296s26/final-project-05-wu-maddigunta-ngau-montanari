package irlquestbook.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Page {

    private final ObservableList<Quest> quests;
	private StringProperty name;

    public Page() {
        this.quests = FXCollections.observableArrayList();
        this.name = new javafx.beans.property.SimpleStringProperty("");
    }

    public Page(List<Quest> quest, String name) {
        this.quests = FXCollections.observableArrayList(quest);
        this.name = new javafx.beans.property.SimpleStringProperty(name);
    }

    public StringProperty getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableList<Quest> getQuests() {
        return this.quests;
    }

    public void addQuest(Quest quest) {
        this.quests.add(quest);
    }

    public void removeQuest(Quest quest) {
        this.quests.remove(quest);
    }

    private boolean wouldCreateCycle(Quest source, Quest dest) {
        Set<Quest> visited = new HashSet<>();
        Deque<Quest> stack = new ArrayDeque<>();
        stack.push(source);
        while (!stack.isEmpty()) {
            Quest q = stack.pop();
            if (q == dest) {
                return true;
            }
            if (!visited.add(q)) {
                continue;
            }
            for (Quest prereq : q.getPrereqs()) {
                stack.push(prereq);
            }
        }
        return false;
    }

    public void handleQuestDelete(Quest quest) {
        this.quests.remove(quest);
        this.quests.forEach(other -> other.getPrereqs().remove(quest));
    }

    public Quest handleQuestCreate(double x, double y) {
        Quest q = new Quest("", "", x, y);

        addQuest(q);

        return q;
    }

    public void handleQuestConnect(Quest source, Quest dest) {
        // no self connections
        if (source == dest)
            return;
        // no two-way connections
        if (dest.getPrereqs().contains(source))
            return;
        // no cycles
        if (wouldCreateCycle(source, dest))
            return;

        dest.getPrereqs().add(source);
    }

    public void handleConnectionDelete(Quest source, Quest dest) {
        // TODO

        System.out.println("disconnect: " + source + " -> " + dest);
    }
}
