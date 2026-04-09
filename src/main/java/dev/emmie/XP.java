package dev.emmie;

public class XP implements Reward{
    private int xpAmount;
    
    public XP(int xpAmount) {
        this.xpAmount = xpAmount;
    }

    @Override
    public void claim_reward(UserProfile user) {
        System.out.println("You have claimed " + xpAmount + " XP!");
        user.addXP(this.xpAmount);
    }

    @Override
    public void create_reward(String rewardName, String rewardDescription) {
        System.out.println("Reward Created: " + rewardName);
        System.out.println("Description: " + rewardDescription);
        // Here you can add code to actually create the reward and store it in a database or list
    }

    
}
