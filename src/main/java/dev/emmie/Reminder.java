package dev.emmie;

// class gets the message and time of the reminder and stores it in the object
public class Reminder {
	private String message;
	private long time; 

	public Reminder(String message, long time) {
		this.message = message;
		this.time = time;
	}

	// returns the message of the reminder
	public String getMessage() {
		return message;
	}

	// returns the time of the reminder
	public long getTime() {
		return time;
	}
}
