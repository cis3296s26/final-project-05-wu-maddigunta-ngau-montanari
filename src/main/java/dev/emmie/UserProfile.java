package dev.emmie;

public class UserProfile {
    private String username;
    private int level;
    private int totalXP;
    private boolean leveledUp;

    public UserProfile(String username) {
        this.username = username;
        this.level = 0;
        this.totalXP = 0;
        this.leveledUp = false;
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

    public boolean hasLeveledUp() {
        return leveledUp;
    }

    public void addXP(int amount) {
        this.totalXP += amount;
        calculateLevel();
    }

    private void calculateLevel() { //we may wanna move this out to anotehr clas
        int oldLevel = this.level;
        this.level = (this.totalXP / 100) + 1; //basic impl for now 
        if (this.level > oldLevel) {
            this.leveledUp = true;
        } else {
            this.leveledUp = false;
        }
    }
}
