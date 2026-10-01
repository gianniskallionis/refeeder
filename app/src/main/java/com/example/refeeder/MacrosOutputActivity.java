package com.example.refeeder;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.refeeder.data.db.AppDatabase;
import com.example.refeeder.data.entity.DailyPlan;

public class MacrosOutputActivity extends AppCompatActivity {

    private TextView textViewDayType;
    private TextView textViewCalories;
    private TextView textViewProtein;
    private TextView textViewCarbs;
    private TextView textViewFats;

    private ProgressBar progressProtein;
    private ProgressBar progressCarbs;
    private ProgressBar progressFats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.macros_output);

        textViewDayType = findViewById(R.id.textViewDayType);
        textViewCalories = findViewById(R.id.textViewCalories);
        textViewProtein = findViewById(R.id.textViewProtein);
        textViewCarbs = findViewById(R.id.textViewCarbs);
        textViewFats = findViewById(R.id.textViewFats);

        progressProtein = findViewById(R.id.progressProtein);
        progressCarbs = findViewById(R.id.progressCarbs);
        progressFats = findViewById(R.id.progressFats);

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> finish());

        int userId = getIntent().getIntExtra("userId", -1);
        int age = getIntent().getIntExtra("age", 0);
        double weight = getIntent().getDoubleExtra("weight", 0);
        double height = getIntent().getDoubleExtra("height", 0);
        String sex = getIntent().getStringExtra("sex");
        String activity = getIntent().getStringExtra("activity");
        String trainingType = getIntent().getStringExtra("trainingType");

        CarbCyclingCalculator.Result result =
                CarbCyclingCalculator.calculate(
                        age,
                        weight,
                        height,
                        sex,
                        activity,
                        trainingType
                );

        showResult(result);
        saveDailyPlan(userId, result);
    }

    private void showResult(CarbCyclingCalculator.Result result) {
        textViewDayType.setText(result.dayType);
        textViewCalories.setText(String.valueOf((int) result.calories));

        textViewProtein.setText((int) result.protein + " g");
        textViewCarbs.setText((int) result.carbs + " g");
        textViewFats.setText((int) result.fats + " g");

        int totalMacros = (int) (result.protein + result.carbs + result.fats);

        if (totalMacros <= 0) {
            return;
        }

        progressProtein.setMax(totalMacros);
        progressCarbs.setMax(totalMacros);
        progressFats.setMax(totalMacros);

        progressProtein.setProgress((int) result.protein);
        progressCarbs.setProgress((int) result.carbs);
        progressFats.setProgress((int) result.fats);
    }

    private void saveDailyPlan(int userId, CarbCyclingCalculator.Result result) {
        if (userId == -1) {
            return;
        }

        new Thread(() -> {
            DailyPlan plan = new DailyPlan();

            plan.userId = userId;
            plan.dayType = result.dayType;
            plan.calories = result.calories;
            plan.protein = result.protein;
            plan.carbs = result.carbs;
            plan.fats = result.fats;
            plan.date = System.currentTimeMillis();

            AppDatabase db = AppDatabase.getInstance(this);
            db.dailyPlanDao().insert(plan);

            runOnUiThread(() ->
                    Toast.makeText(this, "Το πλάνο αποθηκεύτηκε", Toast.LENGTH_SHORT).show()
            );
        }).start();
    }
}