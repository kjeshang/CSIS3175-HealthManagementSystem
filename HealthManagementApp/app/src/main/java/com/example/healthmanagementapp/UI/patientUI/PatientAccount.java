package com.example.healthmanagementapp.UI.patientUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.UI.MainActivity;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.patient.Patient;

public class PatientAccount extends AppCompatActivity {

    String patientId;
    Patient patient;

    DatabaseHelper databaseHelper;

    TextView PatientAccount_tvPatientName;
    EditText PatientAccount_etAllergies;
    EditText PatientAccount_etADiseases;
    Button PatientAccount_btnLogout;
    Button PatientAccount_btnInfo;
    Button PatientAccount_btnFindDoctor;
    Button PatientAccount_btnFoodTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_account);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        patientId = preference.getString("patientId",null);

        PatientAccount_tvPatientName = findViewById(R.id.PatientAccount_tvPatientName);
        PatientAccount_etAllergies = findViewById(R.id.PatientAccount_etAllergies);
        PatientAccount_etADiseases = findViewById(R.id.PatientAccount_etADiseases);
        PatientAccount_btnLogout = findViewById(R.id.PatientAccount_btnLogout);
        PatientAccount_btnInfo = findViewById(R.id.PatientAccount_btnInfo);
        PatientAccount_btnFindDoctor = findViewById(R.id.PatientAccount_btnFindDoctor);
        PatientAccount_btnFoodTracker = findViewById(R.id.PatientAccount_btnFoodTracker);


        patient = databaseHelper.getPatientById(patientId);

        PatientAccount_tvPatientName.setText(patient.getName());
        PatientAccount_etAllergies.setText(patient.getAllergies());
        PatientAccount_etADiseases.setText(patient.getDiseases());

        formatDiseaseInfo();

        PatientAccount_btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientAccount.this, MainActivity.class));
            }
        });

        PatientAccount_btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientAccount.this,PatientInfo.class));
            }
        });

        PatientAccount_btnFindDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientAccount.this,PatientFindDoctor.class));
            }
        });

        PatientAccount_btnFoodTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientAccount.this, PatientFoodTracker.class));
            }
        });

    }

    private void formatDiseaseInfo(){
        PatientAccount_etAllergies.setFocusable(false);
        PatientAccount_etAllergies.setFocusableInTouchMode(false);
        PatientAccount_etAllergies.setClickable(false);

        PatientAccount_etADiseases.setFocusable(false);
        PatientAccount_etADiseases.setFocusableInTouchMode(false);
        PatientAccount_etADiseases.setClickable(false);
    }
}