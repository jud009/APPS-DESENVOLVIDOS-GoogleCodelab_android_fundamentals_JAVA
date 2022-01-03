package com.subs.roomwordsample.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.subs.roomwordsample.R;
import com.subs.roomwordsample.entities.Word;
import com.subs.roomwordsample.services.OnClick;

import java.util.List;

public class WordRecyclerView extends RecyclerView.Adapter<WordRecyclerView.MyViewHolder> {

    private List<Word> words;
    private OnClick onClick;

    public WordRecyclerView(List<Word> words) {
        this.words = words;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setOnClickListener(OnClick onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.onBind(words.get(position).getWord());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mText;

         MyViewHolder(@NonNull View view) {
            super(view);
            this.mText = view.findViewById(R.id.textViewRecycler);
            view.setOnClickListener(v -> {
                if(onClick != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                    onClick.clickListener(v,getAdapterPosition());
                }
            });
        }

        void onBind(String text){
             mText.setText(text);
        }

    }

}
