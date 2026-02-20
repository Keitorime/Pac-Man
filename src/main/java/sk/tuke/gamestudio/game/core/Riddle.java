package sk.tuke.gamestudio.game.core;


public class Riddle {
    private final String question;
    private final String answer;
    private boolean solved = false;

    public Riddle(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean checkAnswer(String userAnswer) {
        if (userAnswer != null && userAnswer.trim().equalsIgnoreCase(answer)) {
            solved = true;
            return true;
        }
        return false;
    }

}

