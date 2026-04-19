package irlquestbook.model;

public class UserProfile {
    private String username;
    private int level;
    private int totalXP;
    private boolean leveledUp;
    private double XPMultiplier;

    public UserProfile(String username) {
        this.username = username;
        this.level = 0;
        this.totalXP = 0;
        this.XPMultiplier = 1.0;
        this.leveledUp = false;
    }

    //getter
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

    public double getXPMultiplier() {
        return XPMultiplier;
    }

    //setter
    public void setUsername(String username) {
        this.username = username;
    }
    public void setXPMultiplier(double XPMultiplier) {
        this.XPMultiplier = XPMultiplier;
    }
    public void addXP(double amount) {
        this.totalXP += (int) (amount * this.XPMultiplier);
    }

    public boolean isLeveledUp() {
        int oldLevel = this.level;
        this.level = (this.totalXP / 100) + 1; //basic impl
        if (this.level > oldLevel) {
            this.leveledUp = true;
        } else {
            this.leveledUp = false;
        }
        return this.leveledUp;
    }
}
