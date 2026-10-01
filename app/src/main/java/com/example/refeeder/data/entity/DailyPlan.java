package com.example.refeeder.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "daily_plan")
public class DailyPlan {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;
    public String dayType;
    public double calories;
    public double protein;
    public double carbs;
    public double fats;
    public long date;
}

