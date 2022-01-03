package com.subs.roomwordsample.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.subs.roomwordsample.Dao.WordDao;
import com.subs.roomwordsample.dataBase.WordDataBase;
import com.subs.roomwordsample.entities.Word;

import java.util.List;

public class WordRepository {

    private WordDao mDao;
    private LiveData<List<Word>> allWords;

    public WordRepository(Application application) {
        WordDataBase dataBase = WordDataBase.getInstance(application);
        mDao = dataBase.getWordDao();
        allWords = mDao.getAphabetizedWords();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Word word){
        WordDataBase.dataBaseExecutor.execute(() -> mDao.insert(word));
    }

    public void delete(String word){
        WordDataBase.dataBaseExecutor.execute(() -> mDao.delete(word));
    }

    // LiveData executes all queries on a separate thread.
    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }
}
