package dev.emmie;

public enum Difficulty {
	EASY("Easy"),
	MEDIUM("Medium"),
	HARD("Hard");

	private final String difficulty;

	Difficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getDifficulty() {
		return difficulty;
	}
  
}
