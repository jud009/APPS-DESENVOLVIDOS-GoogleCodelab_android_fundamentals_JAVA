package com.example.recyclerviewapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    public interface WordClickListener{
        void onClick(int position, View view);
    }

    private final LinkedList<String> mWordsList;
    private  WordClickListener wordClickListener;

    public WordListAdapter(LinkedList<String> mWordsList) {
        this.mWordsList = mWordsList;
    }

    public LinkedList<String> getWordsList() {
        return mWordsList;
    }

    public void setWordClickListener(WordClickListener wordClickListener) {
        this.wordClickListener = wordClickListener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView =LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list_layout,parent,false);
        return new WordViewHolder(mView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.setContent(mWordsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mWordsList.size();
    }

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView mWordText;
        private final WordListAdapter mAdapter;
        public WordViewHolder(@NonNull View v, WordListAdapter mAdapter) {
            super(v);
            this.mAdapter = mAdapter;
            mWordText = v.findViewById(R.id.word_value);
            v.setOnClickListener(this);
        }

        private void setContent(String txt){
            mWordText.setText(txt);
        }

        @Override
        public void onClick(View v) {
            if(wordClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                wordClickListener.onClick(getAdapterPosition(),v);
            }
        }
    }
}
