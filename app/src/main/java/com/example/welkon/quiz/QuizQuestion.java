package com.example.welkon.quiz;

import java.util.Objects;

public class QuizQuestion {
    private int numberOfQR;
    private String question;

    public QuizQuestion(int numberOfQR, String question) {
        this.numberOfQR = numberOfQR;
        this.question = question;
    }

    public QuizQuestion() {
    }

    public void setNumberOfQR(int numberOfQR) {
        this.numberOfQR = numberOfQR;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getNumberOfQR() {
        return numberOfQR;
    }

    public String getQuestion() {
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizQuestion)) return false;
        QuizQuestion that = (QuizQuestion) o;
        return getNumberOfQR() == that.getNumberOfQR() &&
                Objects.equals(getQuestion(), that.getQuestion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumberOfQR(), getQuestion());
    }

    @Override
    public String toString() {
        return "QuizQuestion{" +
                "numberOfQR=" + numberOfQR +
                ", question='" + question + '\'' +
                '}';
    }
}
