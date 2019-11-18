package com.app.upworktest.viewmodels;

import androidx.lifecycle.ViewModel;

import com.app.upworktest.models.QuizQuestion;
import com.app.upworktest.models.QuizSession;

public class QuizViewModel extends ViewModel {

    private QuizSession quizSession;

    public QuizSession getQuizSession() {
        return quizSession;
    }

    public void setQuizSession(QuizSession quizSession) {
        this.quizSession = quizSession;
    }

    public QuizQuestion getQuestion(int index) {
        return quizSession.questions.get(index);
    }

    public void saveUserAnswer(int questionIndex, int userAnswerIndex) {
        quizSession.questions.get(questionIndex).userAnswerIndex = userAnswerIndex;
    }

}
