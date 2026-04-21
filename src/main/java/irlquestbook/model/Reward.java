package irlquestbook.model;


// let the user claim and create rewards 
public interface Reward {  
    void claimReward(UserProfile user);
    boolean getClaimed();
    void setClaimed(boolean claimed);
}
