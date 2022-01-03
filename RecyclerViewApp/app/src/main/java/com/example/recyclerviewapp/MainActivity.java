package com.example.recyclerviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private final LinkedList<String> linkedList = new LinkedList<>();
    public static final String TAG = "TAG_MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertData(linkedList);

        RecyclerView recyclerView = findViewById(R.id.word_recycler);
        WordListAdapter adapter = new WordListAdapter(linkedList);
        TextView resetText = findViewById(R.id.reset);

        resetText.setOnClickListener(view -> {
            linkedList.clear();
            insertData(linkedList);
            adapter.notifyDataSetChanged();
        });

        FloatingActionButton actionButton = findViewById(R.id.fab_add);
        actionButton.setOnClickListener( view -> {
          int size = linkedList.size();
          linkedList.addLast("+Word "+size);
          adapter.notifyItemInserted(size);
          recyclerView.smoothScrollToPosition(size);

        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        adapter.setWordClickListener(((position, view) -> {
            String text = adapter.getWordsList().get(position);
            adapter.getWordsList().set(position,"Clicked - "+ text);
            adapter.notifyDataSetChanged();
        }));
    }

    private void insertData(LinkedList<String> linkedList){
        String value = "WORD";
        for (int x =0; x < 20; x++){
            linkedList.addLast(value+" "+ x);
        }
    }
}
