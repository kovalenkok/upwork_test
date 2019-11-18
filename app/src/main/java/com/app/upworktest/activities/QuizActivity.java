package com.app.upworktest.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.app.upworktest.R;
import com.app.upworktest.adapters.QuestionsAdapter;
import com.app.upworktest.models.QuizSession;
import com.app.upworktest.utils.JSONObjectCreator;
import com.app.upworktest.viewmodels.QuizViewModel;

import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_QUIZ_SESSION = "EXTRA_QUIZ_SESSION";

    private QuizViewModel viewModel;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Setup support action bar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.quiz_page_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize class members.
        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(QuizViewModel.class);

        QuizSession session;
        if (viewModel.getQuizSession() == null) {
            // Parse intent extra and get quiz session object.
            String json = getIntent().getStringExtra(EXTRA_QUIZ_SESSION);
            session = JSONObjectCreator.createObject(json, QuizSession.class);
            viewModel.setQuizSession(session);
        } else {
            session = viewModel.getQuizSession();
        }

        // Setup quiz view pager.
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new QuestionsAdapter(
                getSupportFragmentManager(), getLifecycle(), session.questions));
        viewPager.setUserInputEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void showNextQuestion() {
        if (viewModel.getAnsweredQuestionIndex() != -1) {
            viewPager.setCurrentItem(viewModel.getAnsweredQuestionIndex());
        } else {
            showQuizScore();
        }
    }

    private void showQuizScore() {
        int totalAnswers = viewModel.totalAnswers();
        int corrects = viewModel.getCorrects();
        String builder = "\n" +
                "Total Answers: " +
                totalAnswers +
                "\n\n" +
                "Correct Answers: " +
                corrects +
                "\n\n" +
                "Your score: " +
                String.format(Locale.getDefault(),
                        "%.2f%%", (float) corrects / totalAnswers * 100);
        new AlertDialog.Builder(this)
                .setTitle(R.string.review_score)
                .setMessage(builder)
                .setPositiveButton("OK", (dialog, which) -> {
                    finish();
                })
                .show();
    }

}
