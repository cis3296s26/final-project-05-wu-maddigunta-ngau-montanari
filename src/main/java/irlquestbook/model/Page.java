package irlquestbook.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Page {
    private final ObservableList<Quest> quests = FXCollections.observableArrayList();
    private String name;

    public Page() {
        this.name = "";
    }

    public Page(List<Quest> quests, String name) {
        this.quests.addAll(quests);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public ObservableList<Quest> getQuests() {
        return quests;
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
        this.getQuests().remove(quest);
        this.getQuests().forEach(other -> other.getPrereqs().remove(quest));
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
