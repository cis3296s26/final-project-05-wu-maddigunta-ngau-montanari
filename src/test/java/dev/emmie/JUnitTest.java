package dev.emmie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;



import org.junit.jupiter.api.Test;

public class JUnitTest {

    // ========================= Testing the Quest class =========================

    // test to verify that a newly created incomplete quest with no prerequisites is available
    @Test
    public void newIncompleteQuestWithNoPrerequisitesIsAvailable() {
        // create the quest with name, description, and completed flag set to false
        Quest quest = new Quest("Morning Run", "Run for 20 minutes", false);

        // checking all the fields of the quest to ensure they are initialized correctly
        assertEquals("Morning Run", quest.getName());
        assertEquals("Run for 20 minutes", quest.getDescription());
        assertFalse(quest.isCompleted());
        assertEquals(QuestStatus.AVAILABLE, quest.getStatus());
    }

    // test to verify that marking a quest as completed updates its completion flag and status
    @Test
    public void markCompletedUpdatesQuestStatus() {
        Quest quest = new Quest("Homework", "Finish assignments", false);

        quest.markCompleted();

        // expect the quest to be marked as completed and its status to reflect that
        assertTrue(quest.isCompleted());
        assertEquals(QuestStatus.COMPLETED, quest.getStatus());
    }

    // test to verify that setName and setDescription correctly update quest fields
    @Test
    public void settersUpdateQuestNameAndDescription() {
        Quest quest = new Quest("Old Name", "Old Description", false);

        // changing the name 
        quest.setName("New Name");
        quest.setDescription("New Description");

        // expect the name and description to be updated
        assertEquals("New Name", quest.getName());
        assertEquals("New Description", quest.getDescription());
    }

    // test to verify that toString returns the quest name
    @Test
    public void toStringReturnsQuestName() {
        Quest quest = new Quest("Study", "Study for an hour", false);

        // return the string 
        assertEquals("Study", quest.toString());
    }

    // test to verify that a quest becomes locked when it has an incomplete prerequisite
    @Test
    public void questIsLockedWhenPrerequisiteIsIncomplete() {
        // creating thing prerequisite quest 
        Quest prerequisite = new Quest("Learn Basics", "Complete the intro lesson", false);

        // creating the advanced quest that depends on the prerequisite
        Quest advancedQuest = new Quest("Final Challenge", "Complete the final challenge", false);

        // checking the prerequisite and advanced quest are linked correctly
        prerequisite.addNextQuest(advancedQuest);
        advancedQuest.addPrerequisite(prerequisite);

        // expect the advanced quest to be locked since its prerequisite is incomplete
        assertEquals(QuestStatus.LOCKED, advancedQuest.getStatus());
    }

    // test to verify that a quest becomes available when its prerequisite is completed
    @Test
    public void questBecomesAvailableWhenPrerequisiteIsCompleted() {
        Quest prerequisite = new Quest("Learn Basics", "Complete the intro lesson", false);
        Quest advancedQuest = new Quest("Final Challenge", "Complete the final challenge", false);

        prerequisite.addNextQuest(advancedQuest);
        advancedQuest.addPrerequisite(prerequisite);

        // marking the prerequisite quest as completed should unlock the advanced quest
        prerequisite.markCompleted();

        // expect the advanced quest to now be available since its prerequisite is completed
        assertEquals(QuestStatus.AVAILABLE, advancedQuest.getStatus());
    }

    // test to verify that a completed quest stays completed even if it has prerequisites
    @Test
    public void completedQuestStatusOverridesLockedState() {
        Quest prerequisite = new Quest("Learn Basics", "Complete the intro lesson", false);
        Quest advancedQuest = new Quest("Final Challenge", "Complete the final challenge", false);

        advancedQuest.addPrerequisite(prerequisite);
        advancedQuest.markCompleted();

        // expect the advanced quest to remain completed even though its prerequisite is incomplete
        assertEquals(QuestStatus.COMPLETED, advancedQuest.getStatus());
    }

    // test to verify that next quests are stored correctly, quest -> quest
    @Test
    public void addNextQuestStoresChildQuest() {
        Quest parent = new Quest("Quest 1", "Start here", false);
        Quest child = new Quest("Quest 2", "Continue here", false);

        parent.addNextQuest(child);

        // expect the parent quest to have the child quest in its next quests list
        assertEquals(1, parent.getNextQuests().size());
        assertEquals(child, parent.getNextQuests().get(0));
    }

    // test to verify that prerequisite quests are stored correctly
    @Test
    public void addPrerequisiteStoresParentQuest() {
        Quest parent = new Quest("Quest 1", "Start here", false);
        Quest child = new Quest("Quest 2", "Continue here", false);

        child.addPrerequisite(parent);

        // expect the child quest to have the parent quest in its previous quests list
        assertEquals(1, child.getPrevQuests().size());
        assertEquals(parent, child.getPrevQuests().get(0));
    }

    // ========================= Testing the Questline class =========================

    // test to verify that addQuest adds a quest to the questline
    @Test
    public void addQuestAddsQuestToQuestline() {
        // creating a questline and a quest, then adding the quest to the questline
        Questline questline = new Questline("Main Story");
        Quest quest = new Quest("Meet the Guide", "Talk to the guide in town", false);

        questline.addQuest(quest);

        // expect the questline to contain the added quest
        assertEquals(1, questline.getQuests().size());
        assertEquals(quest, questline.getQuests().get(0));
    }

    // test to verify that linkQuests connects parent and child correctly
    @Test
    public void linkQuestsConnectsParentAndChild() {
        // creating a questline and two quests, then linking the quests together
        Questline questline = new Questline("Main Story");
        Quest parent = new Quest("Quest 1", "Start here", false);
        Quest child = new Quest("Quest 2", "Continue here", false);

        questline.linkQuests(parent, child);

        // expect the parent quest to have the child quest in its next quests list and 
        // the child quest to have the parent quest in its previous quests list
        // so the quests are linked correctly in both directions
        assertTrue(parent.getNextQuests().contains(child));
        assertTrue(child.getPrevQuests().contains(parent));
    }

    // test to verify that questline name and toString match
    @Test
    public void questlineNameAndToStringMatch() {
        Questline questline = new Questline("Adventure Path");

        // expect the questline name and its string representation to be the same
        assertEquals("Adventure Path", questline.getName());
        assertEquals("Adventure Path", questline.toString());
    }

    // ========================= Testing the QuestStatus enum =========================

    // test to verify the display names of quest statuses
    @Test
    public void questStatusToStringReturnsDisplayName() {
        // expect the string representation of each quest status to match its display name
        assertEquals("Completed", QuestStatus.COMPLETED.toString());
        assertEquals("Locked", QuestStatus.LOCKED.toString());
        assertEquals("Available", QuestStatus.AVAILABLE.toString());
    }
}