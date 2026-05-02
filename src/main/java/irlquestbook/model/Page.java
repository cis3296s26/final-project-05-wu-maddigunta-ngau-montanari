package irlquestbook.model;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private List<Quest> quests;
    private String name;

    public Page() {
        this.quests = new ArrayList<>();
        this.name = "";
    }

    public Page(List<Quest> quest, String name) {
        this.quests = quest;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void addQuest(Quest quest) {
        this.quests.add(quest);
    }

    public void removeQuest(Quest quest) {
        this.quests.remove(quest);
    }

    public void handleQuestDelete(Quest quest) {
        // TODO
        System.out.println("delete: " + quest);
    }

    public Quest handleQuestCreate(double x, double y) {
        // TODO
        System.out.println("create at: " + x + ", " + y);
        return null;
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
