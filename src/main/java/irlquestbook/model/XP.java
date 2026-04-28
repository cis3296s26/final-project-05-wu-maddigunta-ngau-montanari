package irlquestbook.model;

public class XP extends Reward {
    private final int xpAmount;

    public XP(String name, int xpAmount) {
        super(name);
        this.xpAmount = xpAmount;
    }

    public int getXpAmount() {
        return xpAmount;
    }

    // TODO: fix this
    public void claimReward(UserProfile user) {
        if (!this.getClaimed()) {
            user.addXP(this.xpAmount);
            this.setClaimed(true);
        }
    }
}
