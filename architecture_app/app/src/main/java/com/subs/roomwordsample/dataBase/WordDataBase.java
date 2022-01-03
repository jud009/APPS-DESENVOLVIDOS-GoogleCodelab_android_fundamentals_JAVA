package com.subs.roomwordsample.dataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.subs.roomwordsample.Dao.WordDao;
import com.subs.roomwordsample.entities.Word;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordDataBase extends RoomDatabase {

    private static volatile WordDataBase INSTANCE;
    private static int THREADS_RUNNING = 4;

   public static final ExecutorService dataBaseExecutor =
            Executors.newFixedThreadPool(THREADS_RUNNING);

   private static RoomDatabase.Callback callback = new Callback() {
       @Override
       public void onCreate(@NonNull SupportSQLiteDatabase db) {
           super.onCreate(db);

           dataBaseExecutor.execute(() -> {
               WordDao mDao = INSTANCE.getWordDao();
               mDao.deleteAll();
               mDao.insert(new Word("Hello"));
               mDao.insert(new Word("World"));
           });



       }
   };

    public static WordDataBase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (WordDataBase.class){ //only one thread, thr rest wait
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        WordDataBase.class,"WordDatabase").addCallback(callback).build();
            }
        }

        return INSTANCE;
    }

    public abstract WordDao getWordDao();
}
