package com.example.roomwordssample.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.roomwordssample.entities.Word;

import java.util.List;
@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("SELECT * FROM WORDTABLE LIMIT 1")
    Word[] getAnyWord();

    @Delete
    void delete(Word word);

    @Query("DELETE FROM wordTable")
    void deleteAll();

    @Query("SELECT * FROM wordTable ORDER BY words ASC")
    LiveData<List<Word>> getAllWords();
}

/*
 * everything query works in  background because it's using room
 * annotations
 * */
