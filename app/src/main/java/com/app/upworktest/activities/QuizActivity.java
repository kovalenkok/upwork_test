package com.app.upworktest.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

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
    private TextView tvTimer;
    private CountDownTimer timer;
    private int timeInSeconds = 0;

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
        tvTimer = findViewById(R.id.tvTimer);

        QuizSession session;
        if (viewModel.getQuizSession() == null) {
            // Parse intent extra and get quiz session object.
            String json = getIntent().getStringExtra(EXTRA_QUIZ_SESSION);
            session = JSONObjectCreator.createObject(json, QuizSession.class);
            viewModel.setQuizSession(session);
            timeInSeconds = (int) session.totalTime;
        } else {
            session = viewModel.getQuizSession();
            timeInSeconds = viewModel.getTimeInSeconds();
        }

        // Setup quiz view pager.
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new QuestionsAdapter(
                getSupportFragmentManager(), getLifecycle(), session.questions));
        viewPager.setUserInputEnabled(false);

        runTimer();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();

        viewModel.setTimeInSeconds(timeInSeconds);
        timer.cancel();
        timer = null;
    }

    private void runTimer() {
        tvTimer.setText(String.format(Locale.getDefault(), "Count: %d", timeInSeconds));
        timer = new CountDownTimer(timeInSeconds * 1000,
                1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeInSeconds = (int) (millisUntilFinished / 1000);
                tvTimer.setText(String.format(Locale.getDefault(), "Count: %d", timeInSeconds));
            }

            @Override
            public void onFinish() {
                showQuizScore();
            }
        }.start();
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
        String percent = "n/a";
        if (totalAnswers != 0) {
            percent = String.format(Locale.getDefault(),
                    "%.2f%%", (float) corrects / totalAnswers * 100);
        }
        String builder = "\n" +
                "Total Answers: " +
                totalAnswers +
                "\n\n" +
                "Correct Answers: " +
                corrects +
                "\n\n" +
                "Your score: " +
                percent;
        new AlertDialog.Builder(this)
                .setTitle(R.string.review_score)
                .setMessage(builder)
                .setPositiveButton("OK", (dialog, which) -> finish())
                .show();
    }

}
