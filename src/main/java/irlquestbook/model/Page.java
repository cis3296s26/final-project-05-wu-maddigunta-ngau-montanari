package irlquestbook.model;

import java.util.List;

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
        // TODO
        System.out.println("connect: " + source + " -> " + dest);
    }

    public void handleConnectionDelete(Quest source, Quest dest) {
        // TODO

        System.out.println("disconnect: " + source + " -> " + dest);
    }
}
