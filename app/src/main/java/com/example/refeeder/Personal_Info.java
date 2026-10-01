package com.example.refeeder;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.chip.Chip;
import com.example.refeeder.data.db.AppDatabase;
import com.example.refeeder.data.entity.User;

public class Personal_Info extends AppCompatActivity {

    private String selectedGender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);
        // setContentView  loads and transforms the xml code into an interactive screen
        String[] choices = getResources().getStringArray(R.array.activity_levels);

// searches for the choices in the strings.xml folder in the array activity levels

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, choices);

// for every string inside the array choices, it creates a view according to simple_dropdown_item_1line, and it returns the view to Autocomplete textView

        AutoCompleteTextView activityChoiceDropdown = findViewById(R.id.activity_choice);
// connects activityChoiceDropdown item with the item in the xml file with the id = activity_choice
        activityChoiceDropdown.setAdapter(adapter);
        // we  give the activityChoiceDropdown item the views that adapter created
        Button continueButton = findViewById(R.id.continueButton);

        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextAge = findViewById(R.id.editTextAge);
        EditText editTextWeight = findViewById(R.id.editTextWeight);
        EditText editTextHeight = findViewById(R.id.editTextHeight);

        Chip chipMale = findViewById(R.id.chipMale);
        Chip chipFemale = findViewById(R.id.chipFemale);

        chipMale.setOnClickListener(v -> selectedGender = "male");
        chipFemale.setOnClickListener(v -> selectedGender = "female");
        continueButton.setOnClickListener(v -> {
            String nameText = editTextName.getText().toString().trim();
            String ageText = editTextAge.getText().toString().trim();
            String weightText = editTextWeight.getText().toString().trim();
            String heightText = editTextHeight.getText().toString().trim();
            String activityLevel = activityChoiceDropdown.getText().toString().trim();

            if (nameText.isEmpty()){
                showErrorDialog("Συμπλήρωσε όνομα");
                return;
            }
            if (ageText.isEmpty()) {
                showErrorDialog("Συμπλήρωσε ηλικία");
                return;
            }

            if (weightText.isEmpty()) {
                showErrorDialog("Συμπλήρωσε βάρος");
                return;
            }

            if (heightText.isEmpty()) {
                showErrorDialog("Συμπλήρωσε ύψος");
                return;
            }

            if (selectedGender.isEmpty()) {
                showErrorDialog("Επίλεξε φύλο");
                return;
            }

            if (activityLevel.isEmpty()) {
                showErrorDialog("Επίλεξε επίπεδο δραστηριότητας");
                return;
            }

            int age = Integer.parseInt(ageText);
            double weight = Double.parseDouble(weightText);
            double height = Double.parseDouble(heightText);

            User user = new User();
            user.name = nameText;
            user.age = age;
            user.weight = weight;
            user.height = height;
            user.sex = selectedGender;
            user.activityLevel = activityLevel;

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.userDao().insert(user);

                runOnUiThread(() -> {
                    Intent intent = new Intent(Personal_Info.this, ProfilesActivity.class);
                    startActivity(intent);
                    finish();
                });
            }).start();


        });

    }
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Σφάλμα")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}




