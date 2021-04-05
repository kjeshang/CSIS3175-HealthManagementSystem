package com.example.healthmanagementapp.UI.patientUI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.patient.Calories;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class PatientTracker extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    String patientId;

    ListView showList;
    Button btn_showList;
    TextView selectDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    String conDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_tracker);
        selectDate = findViewById(R.id.selectDate_tv);

        showList = findViewById(R.id.showList_lv);
        btn_showList = findViewById(R.id.showList_btn);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user", MODE_PRIVATE);
        patientId = preference.getString("patientId", null);


        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal  = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PatientTracker.this,
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
                conDate = month + "/" + dayOfMonth + "/" + year;
                selectDate.setText(conDate);

            }
        };
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        btn_showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (databaseHelper.checkIfCalorieExists(conDate)) {

                    for(int i=0; i < databaseHelper.getAllCaloriesByDate(conDate).size(); i++) {
                        arrayList.add(databaseHelper.getAllCaloriesByDate(conDate).get(i).getFoodList() + "  " +
                                databaseHelper.getAllCaloriesByDate(conDate).get(i).getDateOfConsumption());

                    }

                    showList.setAdapter(arrayAdapter);

                } else {
                    Toast.makeText(PatientTracker.this, "Please enter a Date", Toast.LENGTH_LONG).show();
                }
            }


        });




    }
}