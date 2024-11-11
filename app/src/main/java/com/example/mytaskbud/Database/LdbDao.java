package com.example.mytaskbud.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LdbDao {

    @Insert
    void insertAll(Planners... planners);

    @Delete
    void deleteAll(Planners... planners);
    @Update
    void updatePlanners(Planners... planners);
    @Query("SELECT * FROM planners")
    LiveData<List<Planners>> getAllPlanners();

}
