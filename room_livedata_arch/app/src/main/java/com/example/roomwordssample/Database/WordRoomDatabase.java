package com.example.roomwordssample.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.roomwordssample.Dao.WordDao;
import com.example.roomwordssample.entities.Word;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static WordRoomDatabase INSTANCE;

    public static WordRoomDatabase getDatabase(Context context) {
        synchronized (WordRoomDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context, WordRoomDatabase.class, "wordDatabase")
                        // Wipes and rebuilds instead of migrating
                        // if no Migration object.
                        // Migration is not part of this practical.
                        .fallbackToDestructiveMigration()
                        .addCallback(callback)
                        .build();
            }
        }
        return INSTANCE;
    }

    private static Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new AddToDatabase(INSTANCE).execute();
        }
    };

    private static class AddToDatabase extends AsyncTask<Void, Void, Void> {

        private WordDao mDao;
        private String[] wordsArray = {"Good", "Best", "Better"};

        AddToDatabase(WordRoomDatabase db) {
            this.mDao = db.getWordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (mDao.getAnyWord().length < 1) {

                for (String value : wordsArray) {
                    mDao.insert(new Word(value));
                }
            }

            return null;
        }
    }

    public abstract WordDao getWordDao();
}
