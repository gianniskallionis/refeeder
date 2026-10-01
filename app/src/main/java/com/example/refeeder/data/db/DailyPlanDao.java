package com.example.refeeder.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.refeeder.data.entity.DailyPlan;

import java.util.List;

@Dao
public interface DailyPlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DailyPlan plan);

    @Query("SELECT * FROM daily_plan WHERE date = :date AND userId = :userId LIMIT 1")
    DailyPlan getByUserAndDate(int userId, long date);

    @Query("SELECT * FROM daily_plan WHERE userId = :userId ORDER BY date DESC")
    List<DailyPlan> getAllForUser(int userId);
}
