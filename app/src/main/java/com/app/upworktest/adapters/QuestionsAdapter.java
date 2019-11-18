package com.app.upworktest.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.upworktest.fragments.QuestionFragment;
import com.app.upworktest.models.QuizQuestion;

import java.util.List;

public class QuestionsAdapter extends FragmentStateAdapter {

    private List<QuizQuestion> questions;

    public QuestionsAdapter(@NonNull FragmentManager fragmentManager,
                            @NonNull Lifecycle lifecycle,
                            List<QuizQuestion> questions) {
        super(fragmentManager, lifecycle);
        this.questions = questions;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return QuestionFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
