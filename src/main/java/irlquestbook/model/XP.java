package irlquestbook.model;

public class XP implements Reward{
    private int xpAmount;
    
    public XP(int xpAmount) {
        this.xpAmount = xpAmount;
    }

    @Override
    public void claimReward(UserProfile user) {
        user.addXP(this.xpAmount);
    }

    
}
