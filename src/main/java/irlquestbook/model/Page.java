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
}
