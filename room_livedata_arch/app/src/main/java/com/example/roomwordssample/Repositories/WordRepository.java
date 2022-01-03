package com.example.roomwordssample.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.roomwordssample.Dao.WordDao;
import com.example.roomwordssample.Database.WordRoomDatabase;
import com.example.roomwordssample.entities.Word;

import java.util.List;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {
        WordRoomDatabase database = WordRoomDatabase.getDatabase(application);
        this.mWordDao = database.getWordDao();
        this.mAllWords = this.mWordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        //room execute in background thread an than update live data
        return mAllWords;
    }

    public void insert(Word word) {
        new AsyncInsert(mWordDao).execute(word);
    }

    public void delete(Word word){
        new AsyncDelete(mWordDao).execute(word);
    }

    public void deleteAll() {
        new AsyncDeleteAll(mWordDao).execute();
    }

    private static class AsyncInsert extends AsyncTask<Word, Void, Void> {

        private final WordDao dao;

        AsyncInsert(WordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Word... params) {
            dao.insert(params[0]);
            return null;
        }
    }

    private static class AsyncDelete extends AsyncTask<Word, Void, Void> {

        private WordDao dao;

        AsyncDelete(WordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            dao.delete(words[0]);
            return null;
        }
    }

    private static class AsyncDeleteAll extends AsyncTask<Void, Void, Void> {

        private WordDao mDao;

        AsyncDeleteAll(WordDao wordDao) {
            this.mDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.deleteAll();
            return null;
        }
    }
}
