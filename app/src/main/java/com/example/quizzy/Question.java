package com.example.quizzy;

public class Question {
    private String questionText;
    private String[] choices;
    private int correctAnswerIndex;
    private String category;

    // Constructeur mis à jour pour inclure la catégorie
    public Question(String questionText, String[] choices, int correctAnswerIndex, String category) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswerIndex = correctAnswerIndex;
        this.category = category;
    }

    // Getters
    public String getQuestionText() {
        return questionText;
    }

    public String[] getChoices() {
        return choices;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public String getCategory() {
        return category;
    }
}