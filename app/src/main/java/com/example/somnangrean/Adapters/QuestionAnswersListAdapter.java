package com.example.somnangrean.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.somnangrean.Models.Answer.APIAnswers;
import com.example.somnangrean.R;


public class QuestionAnswersListAdapter extends RecyclerView.Adapter<QuestionAnswersListAdapter.QuestionAnswersViewHolder> {

    private APIAnswers answers;

    public QuestionAnswersListAdapter(APIAnswers answers) {
        this.answers = answers;
    }

    public static class QuestionAnswersViewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView body;
        private TextView rating;

        public QuestionAnswersViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.answerUser);
            body = itemView.findViewById(R.id.answer);
            rating = itemView.findViewById(R.id.answerRate);
        }
    }


    @NonNull
    @Override
    public QuestionAnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answers, parent, false);
        QuestionAnswersViewHolder questionAnswersViewHolder = new QuestionAnswersViewHolder(v);

        return questionAnswersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAnswersViewHolder holder, int position) {
        holder.username.setText(String.valueOf(answers.getData()[position].getUser_id()));
        holder.rating.setText(String.valueOf(answers.getData()[position].getRating()));
        holder.body.setText(answers.getData()[position].getBody());
    }

    @Override
    public int getItemCount() {
        return answers.getData().length;
    }
}
