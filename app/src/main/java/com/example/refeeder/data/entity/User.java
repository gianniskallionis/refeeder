package com.example.refeeder.data.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public int age;
    public double weight;
    public double height;
    public String sex;
    public String activityLevel;
}
