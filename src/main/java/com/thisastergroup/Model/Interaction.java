package com.thisastergroup.Model;

public class Interaction {
    private String ID;
    private String Question;
    private String Tip;

    // Constructor
    public Interaction(String ID, String Question, String Tip) {
        this.ID = ID;
        this.Question = Question;
        this.Tip = Tip;
    }

    // Getters
    public String getID() {
        return ID;
    }

    public String getQuestion() {
        return Question;
    }

    public String getTip() {
        return Tip;
    }

    // Setters
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public void setTip(String Tip) {
        this.Tip = Tip;
    }

    
}