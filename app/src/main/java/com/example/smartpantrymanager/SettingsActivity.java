package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        Spinner unitSpinner = findViewById(R.id.unitsSpinner);
        String  [] units ={
                "Grams (g)",
                "Kilograms(kg)",
                "Milliliters(ml)",
                "Liters(l)"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter <>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );
        //adding the dropdown display layout
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        unitSpinner.setAdapter(adapter);
        SharedPreferences preferences= getSharedPreferences("PantrySettings", MODE_PRIVATE);
        String savedUnit = preferences.getString("preferredUnit","Grams (g)");
        int position = adapter.getPosition(savedUnit);
        unitSpinner.setSelection(position);
        unitSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id){
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("preferredUnit", units[position]);
                editor.apply();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent){}
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SwitchCompat expiryAlertsSwitch = findViewById(R.id.expiryAlertsSwitch);
        boolean alertsEnabled = preferences.getBoolean("expiryAlertsEnabled", true);
        expiryAlertsSwitch.setChecked(alertsEnabled);

        expiryAlertsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit()
                    .putBoolean("expiryAlertsEnabled", isChecked)
                    .apply();
        });
    }
}