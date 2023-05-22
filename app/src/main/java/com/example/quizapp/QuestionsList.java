package com.example.quizapp;

import java.io.Serializable;

public class QuestionsList implements Serializable {
    private final String question, optionOne, optionTwo, optionThree, optionFour;
    private int answer;
    private int userSelectedAnswer;

    public QuestionsList(String question, String optionOne, String optionTwo, String optionThree, String optionFour, int answer) {
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
        this.answer = answer;
        this.userSelectedAnswer = 0;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public String getOptionThree() {
        return optionThree;
    }

    public String getOptionFour() {
        return optionFour;
    }

    public int getAnswer() {
        return answer;
    }

    public int getUserSelectedAnswer() {
        return userSelectedAnswer;
    }

    public void setUserSelectedAnswer(int userSelectedAnswer) {
        this.userSelectedAnswer = userSelectedAnswer;
    }
}
