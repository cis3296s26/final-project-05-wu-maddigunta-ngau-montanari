package irlquestbook.model;

import java.time.LocalDate;

public class Streak {
  	private int streak;
	private LocalDate lastIncrementedDate;

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

	public boolean incrementedToday() {
		return lastIncrementedDate != null && 
		lastIncrementedDate.equals(LocalDate.now());
	}

	public void increment() {
		LocalDate today = LocalDate.now();
		if (lastIncrementedDate != null && 
			!lastIncrementedDate.equals(today) &&
			!lastIncrementedDate.equals(today.minusDays(1))) {
			this.streak = 0; // only reset if it's neither today nor yesterday
		}
		if (!today.equals(lastIncrementedDate)) { // avoid double-counting
			this.streak++;
			this.lastIncrementedDate = today;
		}	
	}
}
