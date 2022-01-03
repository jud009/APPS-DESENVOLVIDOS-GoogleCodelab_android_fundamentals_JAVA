package com.example.roomwordssample;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomwordssample.Activities.NewWordActivity;
import com.example.roomwordssample.Adapters.WordListAdapter;
import com.example.roomwordssample.ViewModel.WordViewModel;
import com.example.roomwordssample.entities.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_NEW_WORD = 0;

    private WordViewModel wordViewModel;
    private WordListAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.word_recycler);
        FloatingActionButton fab = findViewById(R.id.fab_add);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, REQUEST_NEW_WORD);
        });

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        wordAdapter = new WordListAdapter(new ArrayList<>());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(wordAdapter);

        wordViewModel.getAllWords().observe(this, words -> wordAdapter.setWords(words));

        configItemTouchHelper(recyclerView);
    }


    private void configItemTouchHelper(RecyclerView recyclerView){

        ItemTouchHelper itemTouch = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {


                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Word mWord = wordAdapter.getWordAtPosition(viewHolder.getAdapterPosition());
                wordViewModel.delete(mWord);
                displayToast("Deleting "+mWord.getWord()+"...");
            }
        });

        itemTouch.attachToRecyclerView(recyclerView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete_item) {
            displayToast("Deleting data...");
            wordViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_NEW_WORD && data != null) {
            String receivedWord = data.getStringExtra(NewWordActivity.EXTRA_REPLY);
            wordViewModel.insert(new Word(receivedWord));
        }
    }

    private void displayToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
