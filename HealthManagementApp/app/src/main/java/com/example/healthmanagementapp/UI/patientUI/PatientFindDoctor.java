package com.example.healthmanagementapp.UI.patientUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.UI.adminUI.AdminAccount;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.User;
import com.example.healthmanagementapp.model.doctor.Doctor;

public class PatientFindDoctor extends AppCompatActivity {

    ListView PatientFindDoctor_listView;

    DatabaseHelper databaseHelper;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_find_doctor);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);

        PatientFindDoctor_listView = findViewById(R.id.PatientFindDoctor_listView);

        showDoctorsOnListView(databaseHelper);

        PatientFindDoctor_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = preference.edit();
                Doctor clickedDoctor = (Doctor) parent.getItemAtPosition(position);
                editor.putString("selectedDoctorId",clickedDoctor.getId());
                editor.commit();
                startActivity(new Intent(PatientFindDoctor.this,PatientAppointment.class));
            }
        });
    }

    private void showDoctorsOnListView(DatabaseHelper databaseHelper){
        arrayAdapter = new ArrayAdapter(PatientFindDoctor.this, R.layout.listview_layout, databaseHelper.getAllDoctors());
        PatientFindDoctor_listView.setAdapter(arrayAdapter);
    }
}