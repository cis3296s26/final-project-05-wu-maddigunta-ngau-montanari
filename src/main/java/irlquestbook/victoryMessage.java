package irlquestbook;

// messages for when the user completes a quest 
public enum victoryMessage {
    VICTORY_MESSAGE_1("Congratulations! You have completed the quest!"),
    VICTORY_MESSAGE_2("Well done! You have successfully completed the quest!"),
    VICTORY_MESSAGE_3("Great job! You have completed the quest!"),
    VICTORY_MESSAGE_4("You did it! You have completed the quest!"),
    VICTORY_MESSAGE_5("Fantastic work! You have completed the quest!");

    private final String message;
    
    victoryMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
