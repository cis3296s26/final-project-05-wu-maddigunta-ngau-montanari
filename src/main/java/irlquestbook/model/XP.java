package irlquestbook.model;

public class XP implements Reward{
    private int xpAmount;
    private boolean claimed;

    public XP(int xpAmount) {
        this.xpAmount = xpAmount;
        this.claimed = false;
    }

    
    //getters and setters
    public int getXpAmount() {
        return xpAmount;
    }

    @Override
    public boolean getClaimed() {
        return claimed;
    }

    @Override
    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    @Override
    public void claimReward(UserProfile user) {
         if (!getClaimed()) {
            user.addXP(this.xpAmount);
            setClaimed(true);
        }
    }
}
