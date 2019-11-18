package com.app.upworktest.models;

import java.util.List;

public class QuizQuestion {
    public String questionText;
    public int correctAnswerIndex;
    public float timestamp;
    public float timecap;
    public List<QuizAnswer> answers;
    public int userAnswerIndex;
}
