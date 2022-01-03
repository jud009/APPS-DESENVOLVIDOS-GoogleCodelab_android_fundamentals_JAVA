package com.example.roomwordssample.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roomwordssample.Repositories.WordRepository;
import com.example.roomwordssample.entities.Word;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;
    private LiveData<List<Word>> allWords;

    //never use context, activities or fragments on viewmodel
    public WordViewModel(@NonNull Application application) {
        super(application);
        this.mRepository = new WordRepository(application);
        this.allWords = mRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void insert(Word word) {
        mRepository.insert(word);
    }

    public void delete(Word word){
        mRepository.delete(word);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }
}
