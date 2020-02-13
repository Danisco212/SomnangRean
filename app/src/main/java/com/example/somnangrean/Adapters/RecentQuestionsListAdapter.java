package com.example.somnangrean.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.somnangrean.Models.Question.Question;
import com.example.somnangrean.R;

public class RecentQuestionsListAdapter extends RecyclerView.Adapter<RecentQuestionsListAdapter.RecentQuestionsViewHolder> {

    private Question question;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    // RecyclerView.Adapter needs a view holder
    public static class RecentQuestionsViewHolder extends RecyclerView.ViewHolder{

        private TextView userName;
        private TextView category;
        private TextView body;

        public RecentQuestionsViewHolder(@NonNull View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            userName = itemView.findViewById(R.id.questionUser);
            category = itemView.findViewById(R.id.questionCategory);
            body = itemView.findViewById(R.id.question);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener!=null){
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
        }
    }

    public RecentQuestionsListAdapter(Question question){
        this.question = question;
    }
    // get the data
    @NonNull
    @Override
    public RecentQuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_list, parent, false);
        RecentQuestionsViewHolder recentQuestionsViewHolder = new RecentQuestionsViewHolder(v, onItemClickListener);
        return recentQuestionsViewHolder;
    }

    // pass values to views
    @Override
    public void onBindViewHolder(@NonNull RecentQuestionsViewHolder holder, int position) {
        holder.category.setText(question.getData()[position].getCategory_name());
        holder.userName.setText(question.getData()[position].getUser_name());
        holder.body.setText(question.getData()[position].getBody());
    }

    @Override
    public int getItemCount() {
        return question.getData().length;
    }

}
