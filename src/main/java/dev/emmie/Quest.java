package dev.emmie;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Quest {
    private int id;
    private String name;
    private String description;
    private boolean isCompleted;
    private boolean isClaimed;
    private QuestRepeatInterval repeatInterval;
    private transient List<Quest> prev;
    private List<Subtask> subtasks;
    private LocalDateTime lastCompletedAt;

    public Quest(String name, String description) {
        this.id = 0;
        this.name = name;
        this.description = description;
        this.isCompleted = false;
        this.prev = new ArrayList<>();
        this.subtasks = new ArrayList<>();
        this.isClaimed = false;
    }

    // -------------------------
    // GETTERS
    // -------------------------

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isClaimed() {
        return isClaimed;
    }

    public QuestRepeatInterval getRepeatInterval() {
        return repeatInterval;
    }

    public List<Quest> getPrevQuests() {
        return prev;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    // -------------------------
    // SETTERS
    // -------------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    void setClaimed(boolean isClaimed) {
        this.isClaimed = isClaimed;
    }

    void setRepeatInterval(QuestRepeatInterval repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    void addPrerequisite(Quest quest) {
        prev.add(quest);
    }

    void removePrerequisite(Quest quest) {
        prev.remove(quest);
    }

    void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    // -------------------------
    // METHODS
    // -------------------------

    @Override
    public String toString() {
        return name;
    }

    public void reset() {
        this.isCompleted = false;
        this.isClaimed = false;
        for (Subtask subtask : subtasks) {
            subtask.setCompleted(false);
        }
    }

    public void markCompleted() {
        this.lastCompletedAt = LocalDateTime.now();
        this.isCompleted = true;
    }

    private boolean isLocked() {
        for (Quest prerequisite : prev) {
            if (!prerequisite.isCompleted()) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldReset() {
        if (repeatInterval == null) {
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

    public void refresh() {
        if (shouldReset()) {
            reset();
        }
    }

    public QuestStatus getStatus() {
        refresh();
        if (isCompleted) {
            return QuestStatus.COMPLETED;
        }
        if (isClaimed) {
            return QuestStatus.CLAIMED;
        }
        if (isLocked()) {
            return QuestStatus.LOCKED;
        }
        return QuestStatus.AVAILABLE;
    }

    public boolean isFullyCompleted() {
        if (isCompleted) {
            return true;
        }
        if (!subtasks.isEmpty()) {
            for (Subtask subtask : subtasks) {
                if (!subtask.isCompleted()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}