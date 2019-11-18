package com.app.upworktest.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.app.upworktest.R;
import com.app.upworktest.adapters.QuestionsAdapter;
import com.app.upworktest.models.QuizSession;
import com.app.upworktest.utils.JSONObjectCreator;
import com.app.upworktest.viewmodels.QuizViewModel;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_QUIZ_SESSION = "EXTRA_QUIZ_SESSION";

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
        QuizViewModel viewModel = new ViewModelProvider(this,
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
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new QuestionsAdapter(
                getSupportFragmentManager(), getLifecycle(), session.questions));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
