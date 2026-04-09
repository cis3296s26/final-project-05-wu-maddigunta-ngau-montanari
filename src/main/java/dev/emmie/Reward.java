package dev.emmie;


// let the user claim and create rewards 
public interface Reward {
  void claim_reward(UserProfile user);
  void create_reward(String reward_name, String reward_description);
}