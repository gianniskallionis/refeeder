package com.example.refeeder;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private String selectedType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button restButton = findViewById(R.id.buttonRestDay);
        Button cardioButton = findViewById(R.id.buttonCardio);
        Button weightsButton = findViewById(R.id.buttonWeights);
        Button bothButton = findViewById(R.id.buttonBoth);
        Button calculateButton = findViewById(R.id.buttonCalculate);

        restButton.setOnClickListener(v -> {
            selectedType = "rest";
            Toast.makeText(this, "Επέλεξες Rest day", Toast.LENGTH_SHORT).show();
        });

        cardioButton.setOnClickListener(v -> {
            selectedType = "cardio";
            Toast.makeText(this, "Επέλεξες Cardio", Toast.LENGTH_SHORT).show();
        });

        weightsButton.setOnClickListener(v -> {
            selectedType = "weights";
            Toast.makeText(this, "Επέλεξες Weights", Toast.LENGTH_SHORT).show();
        });

        bothButton.setOnClickListener(v -> {
            selectedType = "both";
            Toast.makeText(this, "Επέλεξες Both", Toast.LENGTH_SHORT).show();
        });

        calculateButton.setOnClickListener(v -> {
            if (selectedType.isEmpty()) {
                Toast.makeText(this, "Επίλεξε πρώτα τύπο προπόνησης", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(MainActivity.this, MacrosOutputActivity.class);

            intent.putExtra("userId", getIntent().getIntExtra("userId", -1));
            intent.putExtra("age", getIntent().getIntExtra("age", 0));
            intent.putExtra("weight", getIntent().getDoubleExtra("weight", 0));
            intent.putExtra("height", getIntent().getDoubleExtra("height", 0));
            intent.putExtra("sex", getIntent().getStringExtra("sex"));
            intent.putExtra("activity", getIntent().getStringExtra("activity"));

            intent.putExtra("trainingType", selectedType);

            startActivity(intent);
        });
    }
}