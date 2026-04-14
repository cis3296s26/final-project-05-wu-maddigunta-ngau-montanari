package dev.emmie;

public class XPMultiplier implements Multiplier{

    int multiplierAmount;
    public XPMultiplier(int multiplierAmount) {
        this.multiplierAmount = multiplierAmount;
    }

    @Override
    public void buffMultiplier(UserProfile user) {
        user.addXP(multiplierAmount);
    }

    @Override
    public void debuffMultiplier(UserProfile user) {
        user.addXP(-multiplierAmount);
    }

}