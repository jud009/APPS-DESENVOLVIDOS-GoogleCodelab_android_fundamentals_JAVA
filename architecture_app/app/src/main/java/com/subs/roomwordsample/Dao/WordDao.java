package com.subs.roomwordsample.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.subs.roomwordsample.entities.Word;

import java.util.List;

@Dao
public interface WordDao {

    //ignore the word if it's already exists in the list
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE FROM wordTable")
    void deleteAll();

    @Query("DELETE FROM wordTable WHERE word = :name")
    void delete(String name);


    @Query("SELECT * FROM wordTable ORDER BY word ASC")
    LiveData<List<Word>> getAphabetizedWords();

}
