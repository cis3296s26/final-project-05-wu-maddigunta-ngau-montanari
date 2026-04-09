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

    //LINK TWO QUESTS TOGETHER
    //has access to addNextQuest and addPrerequisite methods in Quest class, 
    // which are default access modifier, so they can only be accessed within the same package
    public void linkQuests(Quest parent, Quest child) {
        parent.addNextQuest(child);
        child.addPrerequisite(parent);
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