package com.subs.roomwordsample.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.subs.roomwordsample.entities.Word;
import com.subs.roomwordsample.repositories.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;
    private LiveData<List<Word>> words;

    //don't use reference activity, fragment, view if need context use viewModel instead

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        words = mRepository.getAllWords();
    }

    public LiveData<List<Word>> getWords() {
        return words;
    }

    public void insert(Word word){
        mRepository.insert(word);
    }

    public void delete(String word){
        mRepository.delete(word);
    }
}
