package com.subs.roomwordsample.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.subs.roomwordsample.R;
import com.subs.roomwordsample.entities.Word;
import com.subs.roomwordsample.recyclers.WordRecyclerView;
import com.subs.roomwordsample.viewModels.WordViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_NEW_WORD = 1;
    
    private List<Word> list = new ArrayList<>();
    private WordViewModel mViewModel;
    private WordRecyclerView mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * When your Activity first starts, the ViewModelProviders will create the ViewModel.
         * When the activity is destroyed, for example through a configuration change, the ViewModel persists.
         * When the activity is re-created, the ViewModelProviders return the existing ViewModel.
         */
        
        mAdapter = new WordRecyclerView(list);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener((view,position) -> showAlert(position));

        mViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        mViewModel.getWords().observe(this, words -> {
            list.clear();
            list.addAll(words);
            mAdapter.notifyDataSetChanged();
        } );

    }

    private void showAlert(int index){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);


        dialog.setTitle("ARE YOU SURE?");
        dialog.setMessage("there's no how to come back");

        dialog.setPositiveButton("yes",((dialog1, which) -> {
            mViewModel.delete(mAdapter.getWords().get(index).getWord());

        }));
        dialog.setNegativeButton("No",null);

        dialog.create().show();
    }

    public void onClickForResult(View view){
        Intent intent = new Intent(this,NewWordActivity.class);
        startActivityForResult(intent,REQUEST_NEW_WORD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(requestCode == REQUEST_NEW_WORD && resultCode == RESULT_OK){
            String wordReceived = data.getStringExtra(NewWordActivity.EXTRA_REPLY);
            if(wordReceived != null){
                mViewModel.insert(new Word(wordReceived));
            }
        }else{
            Toast.makeText(getApplicationContext(), getString(R.string.empty_field), Toast.LENGTH_SHORT).show();
        }
        
    }
}
