package com.example.refeeder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refeeder.data.db.AppDatabase;
import com.example.refeeder.data.entity.User;

import java.util.List;

public class ProfilesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProfiles;
    private User selectedUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiles);

        recyclerViewProfiles = findViewById(R.id.recyclerViewProfiles);
        recyclerViewProfiles.setLayoutManager(new LinearLayoutManager(this));

        View newProfileButton = findViewById(R.id.cardNewProfile);
        newProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilesActivity.this, Personal_Info.class);
            startActivity(intent);
        });

        Button continueButton = findViewById(R.id.buttonContinue);
        continueButton.setOnClickListener(v -> {
            if (selectedUser == null) {
                Toast.makeText(this, "Επίλεξε πρώτα προφίλ", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(ProfilesActivity.this, MainActivity.class);
            intent.putExtra("userId", selectedUser.id);
            intent.putExtra("age",      selectedUser.age);
            intent.putExtra("weight",   selectedUser.weight);
            intent.putExtra("height",   selectedUser.height);
            intent.putExtra("sex",      selectedUser.sex);
            intent.putExtra("activity", selectedUser.activityLevel);
            startActivity(intent);
        });

        loadProfiles();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfiles();
    }

    private void loadProfiles() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<User> users = db.userDao().getAll();

            runOnUiThread(() -> {
                ProfileAdapter adapter = new ProfileAdapter(users);
                adapter.setOnProfileClickListener(user -> {
                    selectedUser = user;
                    Toast.makeText(this, "Επιλέχθηκε: " + user.name, Toast.LENGTH_SHORT).show();
                });
                recyclerViewProfiles.setAdapter(adapter);
            });
        }).start();
    }
}