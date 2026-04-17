package dev.emmie;

public class Streak {
	private int streak;

	public Streak() {
		this.streak = 0;
	}

	// get the streak
	public int getStreak() {
		return streak;
	}
	 // reset the streak
	public void resetStreak() {
		this.streak = 0;
	}

	// increment the streak if the quest is completed
	public void updateStreakForCompletedQuest(Quest quest, UserProfile userProfile) {
		if (quest != null && userProfile != null && quest.isCompleted()) {
				userProfile.incrementStreak();
		}
}

}
