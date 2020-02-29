package com.example.somnangrean.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.somnangrean.Models.Answer.APIAnswers;
import com.example.somnangrean.R;


public class QuestionAnswersListAdapter extends RecyclerView.Adapter<QuestionAnswersListAdapter.QuestionAnswersViewHolder> {

    private APIAnswers answers;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        void onUpVoteClick(int position);
        void onDownVoteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.itemClickListener = onItemClickListener;
    }

    public QuestionAnswersListAdapter(APIAnswers answers) {
        this.answers = answers;
    }

    public static class QuestionAnswersViewHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView body;
        private TextView rating;
        private ImageButton upVote;
        private ImageButton downVote;

        public QuestionAnswersViewHolder(@NonNull View itemView, final OnItemClickListener itemClickListener) {
            super(itemView);
            username = itemView.findViewById(R.id.answerUser);
            body = itemView.findViewById(R.id.answer);
            rating = itemView.findViewById(R.id.answerRate);
            upVote = itemView.findViewById(R.id.upvote);
            downVote = itemView.findViewById(R.id.downvote);

            upVote.setOnClickListener(v -> {
                if (itemClickListener!=null){
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        itemClickListener.onUpVoteClick(position);
                    }
                }
            });

            downVote.setOnClickListener(v->{
                if (itemClickListener!=null){
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        itemClickListener.onDownVoteClick(position);
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public QuestionAnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answers, parent, false);
        QuestionAnswersViewHolder questionAnswersViewHolder = new QuestionAnswersViewHolder(v, itemClickListener);

        return questionAnswersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAnswersViewHolder holder, int position) {
        holder.rating.setText(String.valueOf(answers.getData()[position].getRating()));
        holder.body.setText(answers.getData()[position].getBody());
    }

    @Override
    public int getItemCount() {
        return answers.getData().length;
    }
}
