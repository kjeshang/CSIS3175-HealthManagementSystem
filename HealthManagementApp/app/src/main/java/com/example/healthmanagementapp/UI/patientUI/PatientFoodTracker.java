package com.example.healthmanagementapp.UI.patientUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthmanagementapp.R;

public class PatientFoodTracker extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    EditText quantity;
    TextView foodList_tv;
    TextView tvCalories;

    Button btnSave;
    Button btnHistory;
    Button btnAdd;
    String item;
    int itemsCalories = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_food_tracker);

        spinner = findViewById(R.id.foodList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.SpinnerItems, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        foodList_tv = findViewById(R.id.tvFoodList);
        quantity = findViewById(R.id.etQuantity);
        tvCalories = findViewById(R.id.tvCalories);

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
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
  

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}