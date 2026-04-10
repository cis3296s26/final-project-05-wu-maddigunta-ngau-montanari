package dev.emmie;

public class XP implements Reward{
    private int xpAmount;
    
    public XP(int xpAmount) {
        this.xpAmount = xpAmount;
    }

    @Override
    public void claim_reward(UserProfile user) {
        user.addXP(this.xpAmount);
    }

    
}
