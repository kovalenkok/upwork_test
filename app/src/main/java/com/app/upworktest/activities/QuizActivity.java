package com.app.upworktest.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app.upworktest.R;
import com.app.upworktest.models.QuizSession;
import com.app.upworktest.utils.JSONObjectCreator;
import com.app.upworktest.utils.UIUtils;

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

        // Parse intent extra and get quiz session object.
        String json = getIntent().getStringExtra(EXTRA_QUIZ_SESSION);
        QuizSession session = JSONObjectCreator.createObject(json, QuizSession.class);
        UIUtils.showMessage(this, String.valueOf(session.questions.size()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
