package com.app.upworktest.viewmodels;

import androidx.lifecycle.ViewModel;

import com.app.upworktest.models.QuizQuestion;
import com.app.upworktest.models.QuizSession;

public class QuizViewModel extends ViewModel {

    private QuizSession quizSession;
    private int timeInSeconds;

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

    public int getUserAnswer(int questionIndex) {
        return quizSession.questions.get(questionIndex).userAnswerIndex;
    }

    public int getAnsweredQuestionIndex() {
        for (int i = 0; i < quizSession.questions.size(); i++) {
            if (quizSession.questions.get(i).userAnswerIndex == -1) {
                return i;
            }
        }
        return -1;
    }

    public int totalAnswers() {
        int total = 0;
        for (QuizQuestion question : quizSession.questions) {
            if (question.userAnswerIndex != -1) {
                total++;
            }
        }
        return total;
    }

    public int getCorrects() {
        int total = 0;
        for (QuizQuestion question : quizSession.questions) {
            if (question.userAnswerIndex != -1) {
                if (question.userAnswerIndex == question.correctAnswerIndex) {
                    total++;
                }
            }
        }
        return total;
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

}
