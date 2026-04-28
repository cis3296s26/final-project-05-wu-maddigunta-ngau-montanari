package irlquestbook.model;

public class XP extends Reward {
    private final int xpAmount;

    public XP(int xpAmount) {
        this.xpAmount = xpAmount;
    }

    public int getXpAmount() {
        return xpAmount;
    }

    public void claimReward(UserProfile user) {
        if (!this.getClaimed()) {
            user.addXP(this.xpAmount);
            this.setClaimed(true);
        }
    }
}
