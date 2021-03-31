package com.example.healthmanagementapp.UI.patientUI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.patient.Calories;
import com.example.healthmanagementapp.model.patient.Patient;

import java.util.Calendar;

public class PatientFoodTracker extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String patientId;
    //Patient patient;

    Calories calories;

    DatabaseHelper databaseHelper;

    DatePickerDialog.OnDateSetListener mDateSetListener;

    Spinner spinner;
    EditText quantity;
    TextView foodList_tv;
    TextView tvCalories;
    TextView dateOfConsumption;



    Button btnSave;
    Button btnHistory;
    Button btnAdd;
    String item;
    String date;
    int itemsCalories = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_food_tracker);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user", MODE_PRIVATE);
        patientId = preference.getString("patientId", null);

        spinner = findViewById(R.id.foodList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.SpinnerItems, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        foodList_tv = findViewById(R.id.tvFoodList);
        quantity = findViewById(R.id.etQuantity);
        tvCalories = findViewById(R.id.tvCalories);
        dateOfConsumption = findViewById(R.id.dateOfConsumption);

        dateOfConsumption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal  = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PatientFoodTracker.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                date = month + "/" + dayOfMonth + "/" + year;
                dateOfConsumption.setText(date);

            }
        };

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = spinner.getSelectedItem().toString();

                switch(item) {
                    case "Egg":
                        itemsCalories += (155*Integer.parseInt(quantity.getText().toString()));
                        break;
                    case "Bread":
                        itemsCalories += (265*Integer.parseInt(quantity.getText().toString()));
                        break;
                    case "Tomato":
                        itemsCalories += (33*Integer.parseInt(quantity.getText().toString()));
                        break;
                    case "Avocado":
                        itemsCalories += (160*Integer.parseInt(quantity.getText().toString()));
                        break;
                    case "Rice":
                        itemsCalories += (130*Integer.parseInt(quantity.getText().toString()));
                        break;
                    case "Chicken Breast":
                        itemsCalories += (165*Integer.parseInt(quantity.getText().toString()));
                        break;
                    case "Salmon":
                        itemsCalories += (208*Integer.parseInt(quantity.getText().toString()));
                        break;
                    case  "Carrot":
                        itemsCalories += (41*Integer.parseInt(quantity.getText().toString()));
                        break;
                    case "Apple":
                        itemsCalories += (52*Integer.parseInt(quantity.getText().toString()));
                        break;
                    case "Orange":
                        itemsCalories += (47*Integer.parseInt(quantity.getText().toString()));
                        break;
                    default:
                        break;

                }

                foodList_tv.append(item + " X " + quantity.getText().toString() + "\n");
                tvCalories.setText(String.valueOf(itemsCalories));

            }
        });

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                calories = createCalorie();
                databaseHelper.insertCalorie(calories);
                Toast.makeText(PatientFoodTracker.this, calories.display() + "", Toast.LENGTH_LONG).show();


            }
        });


        btnHistory = findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientFoodTracker.this, PatientTracker.class));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private Calories createCalorie() {
        Calories calories = null;
        String id = patientId;
        String foodList = foodList_tv.getText().toString();
        int totCal = itemsCalories;
        String Suggestion = " - ";
        calories = new Calories(patientId, foodList, totCal, Suggestion, date);
        return calories;
    }

}