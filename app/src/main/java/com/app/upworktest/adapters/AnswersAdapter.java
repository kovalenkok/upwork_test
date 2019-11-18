package com.app.upworktest.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.upworktest.R;
import com.app.upworktest.adapters.base.BaseItemClickListener;
import com.app.upworktest.models.QuizAnswer;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswerViewHolder> {

    private List<QuizAnswer> answers;
    private BaseItemClickListener<QuizAnswer> itemClickListener;
    private int correctAnswerIndex;
    private int selection = -1;

    public AnswersAdapter(List<QuizAnswer> answers, int correctAnswerIndex) {
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public void setItemClickListener(BaseItemClickListener<QuizAnswer> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public int getSelectedAnswerIndex() {
        return selection;
    }

    private void updateSelection(int selection) {
        this.selection = selection;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnswerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_quiz_answer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        holder.bindItem(answers.get(position), selection == position,
                correctAnswerIndex == position);
        holder.tvAnswer.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(answers.get(holder.getAdapterPosition()));
            }
            updateSelection(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    static class AnswerViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAnswer;

        private AnswerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAnswer = itemView.findViewById(R.id.tvAnswer);
        }

        private void bindItem(QuizAnswer answer, boolean selected, boolean isCorrectAnswer) {
            tvAnswer.setText(answer.answerText);
            if (selected) {
                if (isCorrectAnswer) {
                    tvAnswer.setBackgroundResource(R.color.green_100);
                } else {
                    tvAnswer.setBackgroundResource(R.color.red_100);
                }
                tvAnswer.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                tvAnswer.setBackgroundResource(R.color.transparent);
                tvAnswer.setTypeface(Typeface.DEFAULT);
            }
        }
    }
}
