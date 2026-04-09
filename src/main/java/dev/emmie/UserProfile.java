package dev.emmie;

public class UserProfile {
    private String username;
    private int level;
    private int totalXP;

    public UserProfile(String username, int level, Reward xp) {
        this.username = username;
        this.level = level;
        this.totalXP = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getLevel() {
        return level;
    }

    public int getTotalXP() {
        return totalXP;
    }

    public void addXP(int amount) {
        this.totalXP += amount;
        calculateLevel();
    }

    private void calculateLevel() {
        int oldLevel = this.level;
        this.level = (this.totalXP / 100) + 1;

        if (this.level > oldLevel) {
            System.out.println("CONGRATS! You reached level " + this.level);
        }
    }
}
