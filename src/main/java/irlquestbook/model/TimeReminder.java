package irlquestbook.model;
import java.time.LocalDateTime;

public class TimeReminder {
	private LocalDateTime time;
	private String message;
	private ReminderFrequency frequency;

	// reminder time and message
	public TimeReminder(LocalDateTime time, String message, ReminderFrequency frequency) {
		this.time = time;
		this.message = message;
		this.frequency = frequency;
	}

	// getters
	public LocalDateTime getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

	public ReminderFrequency getFrequency() {
		return frequency;
	}

	// setters
	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setFrequency(ReminderFrequency frequency) {
		this.frequency = frequency;
	}
}
