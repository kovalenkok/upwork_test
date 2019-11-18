package com.app.upworktest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import com.app.upworktest.R;
import com.app.upworktest.models.QuizSession;
import com.app.upworktest.utils.JSONObjectCreator;
import com.app.upworktest.utils.UIUtils;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup support action bar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        findViewById(R.id.btnStart).setOnClickListener(v -> {
            QuizSession session = readQuizJSON();
            if (session != null && session.questions != null) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra(QuizActivity.EXTRA_QUIZ_SESSION,
                        JSONObjectCreator.createJson(session));
                startActivity(intent);
            } else {
                UIUtils.showMessage(MainActivity.this,
                        "Unable to load json file from assets");
            }
        });
    }

    private QuizSession readQuizJSON() {
        try {
            InputStream is = getAssets().open("quiz.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            int result = is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            return JSONObjectCreator.createObject(json, QuizSession.class);
        } catch (IOException ignored) {
            return null;
        }
    }
}
