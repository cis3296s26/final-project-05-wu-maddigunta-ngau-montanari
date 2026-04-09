package dev.emmie;


// let the user claim and create awwards 
public interface Reward {
  void claim_reward();
  void create_reward(String award_name, String award_description);
}

