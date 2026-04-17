package dev.emmie;

import java.util.ArrayList;
import java.util.List;

public class Questline { //questline is the graph manager, 
// it stores the quests and the links between them

    private String name;
    private List<Quest> quests;

    public Questline(String name) {
        this.name = name;
        this.quests = new ArrayList<>();
    }

    public void addQuest(Quest q) {
        quests.add(q);
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}