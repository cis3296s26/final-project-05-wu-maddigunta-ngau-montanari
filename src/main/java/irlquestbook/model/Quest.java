package irlquestbook.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class Quest {
    private int id;
    private String name;
    private String description;
    private QuestRepeatInterval repeatInterval;
    private LocalDateTime lastCompletedAt;
    private transient List<Quest> prev;
    private List<Subtask> subtasks;
    private List<Reward> rewards;

    // constructor to initialize the quest object
    public Quest(String name, String description) {
        this.id = 0; // we can set id when we have a database with auto-increment
        this.name = name;
        this.description = description;
        this.repeatInterval = null;
        this.lastCompletedAt = LocalDateTime.MIN;
        this.prev = new ArrayList<>();
        this.rewards = new ArrayList<>();
        this.subtasks = new ArrayList<>();

    }

    // GETTERS FOR QUEST FIELDS
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public List<Quest> getPrevQuests() {
        return prev;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public QuestRepeatInterval getRepeatInterval() {
        return repeatInterval;
    }

    // SETTERS FOR QUEST FIELDS
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addPrerequisite(Quest quest) {
        prev.add(quest);
    }

    public void removePrerequisite(Quest quest) {
        prev.remove(quest);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public void addReward(Reward reward) {
        rewards.add(reward);
    }

    public void removeReward(Reward reward) {
        rewards.remove(reward);
    }

    public void setRepeatInterval(QuestRepeatInterval repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    private boolean checkLocked() {
        for (Quest prerequisite : prev) {
            if (!prerequisite.checkCompleted()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkClaimed() {
        if (rewards.isEmpty()) {
            return false;
        }
        for (Reward reward : rewards) {
            if (!reward.getClaimed()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCompleted() {
        if (subtasks.isEmpty()) {
            return false;
        }
        for (Subtask subtask : subtasks) {
            if (!subtask.getCompleted()) {
                return false;
            }
        }
        return true;
    }

    public void markCompleted() { // manually mark the quest as completed, which will update the lastCompletedAt
                                  // time
        if (checkCompleted()) {
            this.lastCompletedAt = LocalDateTime.now();
        }
    }

    public void reset() {
        this.lastCompletedAt = LocalDateTime.MIN;
        for (Reward reward : rewards) {
            reward.setClaimed(false);
        }
        for (Subtask subtask : subtasks) {
            subtask.setCompleted(false);
        }
    }

    public boolean checkReset() {
        if (repeatInterval == null || lastCompletedAt.equals(LocalDateTime.MIN)) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();

        switch (repeatInterval) {
            case DAILY:
                return !lastCompletedAt.toLocalDate().equals(now.toLocalDate());

            case WEEKLY:
                return lastCompletedAt.getYear() != now.getYear() ||
                        lastCompletedAt.get(ChronoField.ALIGNED_WEEK_OF_YEAR) != now
                                .get(ChronoField.ALIGNED_WEEK_OF_YEAR);
            case MONTHLY:
                return lastCompletedAt.getMonth() != now.getMonth() ||
                        lastCompletedAt.getYear() != now.getYear();
            default:
                return false;
        }
    }

    public QuestStatus getStatus() {
        if (checkReset()) {
            reset();
        }
        if (checkClaimed()) {
            return QuestStatus.CLAIMED;
        }
        if (checkCompleted()) {
            return QuestStatus.COMPLETED;
        }
        if (checkLocked()) {
            return QuestStatus.LOCKED;
        }
        return QuestStatus.AVAILABLE;
    }
}
