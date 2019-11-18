package com.app.upworktest.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.upworktest.R;
import com.app.upworktest.adapters.AnswersAdapter;
import com.app.upworktest.models.QuizQuestion;
import com.app.upworktest.utils.JSONObjectCreator;

public class QuestionFragment extends Fragment {

    private static final String EXTRA_QUIZ_QUESTION = "EXTRA_QUIZ_QUESTION";

    private QuizQuestion question;

    private TextView tvQuestion;
    private RecyclerView recyclerView;

    public static QuestionFragment newInstance(QuizQuestion question) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_QUIZ_QUESTION, JSONObjectCreator.createJson(question));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String json = getArguments().getString(EXTRA_QUIZ_QUESTION);
            question = JSONObjectCreator.createObject(json, QuizQuestion.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Map view elements to class members.
        tvQuestion = view.findViewById(R.id.tvQuestion);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Display question text.
        tvQuestion.setText(question.questionText);

        // Setup recycler view layout and adapter.
        if (getContext() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(
                    new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        }
        AnswersAdapter adapter = new AnswersAdapter(question.answers, question.correctAnswerIndex);
        recyclerView.setAdapter(adapter);

    }


}
