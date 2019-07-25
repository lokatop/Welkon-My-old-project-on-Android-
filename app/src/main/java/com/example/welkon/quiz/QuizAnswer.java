package com.example.welkon.quiz;

public class QuizAnswer {
    private int numberOfQR;
    private String answer;
    private boolean result;

    public QuizAnswer(int numberOfQR, String answer, boolean result) {
        this.numberOfQR = numberOfQR;
        this.answer = answer;
        this.result = result;
    }

    public QuizAnswer() {
    }

    public int getNumberOfQR() {
        return numberOfQR;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isResult() {
        return result;
    }

    public void setNumberOfQR(int numberOfQR) {
        this.numberOfQR = numberOfQR;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "QuizQuestion{" +
                "numberOfQR=" + numberOfQR +
                ", answer='" + answer + '\'' +
                ", result=" + result +
                '}';
    }
}
