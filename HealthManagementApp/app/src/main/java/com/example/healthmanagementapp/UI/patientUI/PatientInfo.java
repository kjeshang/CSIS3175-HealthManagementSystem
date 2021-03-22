package com.example.healthmanagementapp.UI.patientUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.patient.Disease;
import com.example.healthmanagementapp.model.patient.Patient;

public class PatientInfo extends AppCompatActivity {

    String patientId;
    Patient patient;
    Disease disease;

    DatabaseHelper databaseHelper;

    EditText PatientInfo_etUserID;
    EditText PatientInfo_etPassword;
    EditText PatientInfo_etFullName;
    EditText PatientInfo_etPostalCode;
    EditText PatientInfo_etAllergies;
    EditText PatientInfo_etDiseases;
    Button PatientInfo_btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        patientId = preference.getString("patientId",null);

        PatientInfo_etUserID = findViewById(R.id.PatientInfo_etUserID);
        PatientInfo_etPassword = findViewById(R.id.PatientInfo_etPassword);
        PatientInfo_etFullName = findViewById(R.id.PatientInfo_etFullName);
        PatientInfo_etPostalCode = findViewById(R.id.PatientInfo_etPostalCode);
        PatientInfo_etAllergies = findViewById(R.id.PatientInfo_etAllergies);
        PatientInfo_etDiseases = findViewById(R.id.PatientInfo_etDiseases);
        PatientInfo_btnSave = findViewById(R.id.PatientInfo_btnSave);

        patient = databaseHelper.getPatientById(patientId);
        disease = databaseHelper.getDiseaseByPatientId(patientId);

        PatientInfo_etUserID.setText(patient.getId());
        formatInfo();
        PatientInfo_etPassword.setText(patient.getPassword());
        PatientInfo_etFullName.setText(patient.getName());
        PatientInfo_etPostalCode.setText(patient.getPostalCode());
        PatientInfo_etAllergies.setText(disease.getAllergy());
        PatientInfo_etDiseases.setText(disease.getDiseaseInfo());

        PatientInfo_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUpdatedPatient();
                createUpdatedDisease();
                databaseHelper.updatePatient(patient);
                databaseHelper.updateDisease(disease);
                Toast.makeText(PatientInfo.this,patient.display() + "\n" + disease.toString(),Toast.LENGTH_LONG).show();
                startActivity(new Intent(PatientInfo.this,PatientAccount.class));
            }
        });
    }

    private void formatInfo(){
        PatientInfo_etUserID.setFocusable(false);
        PatientInfo_etUserID.setFocusableInTouchMode(false);
        PatientInfo_etUserID.setClickable(false);
    }

    private void createUpdatedPatient(){
        String fullName = PatientInfo_etFullName.getText().toString();
        String password = PatientInfo_etPassword.getText().toString();
        String postalCode = PatientInfo_etPostalCode.getText().toString();
        patient.setName(fullName);
        patient.setPassword(password);
        patient.setPostalCode(postalCode);
    }

    private void createUpdatedDisease(){
        String allergies = PatientInfo_etAllergies.getText().toString();
        String diseases = PatientInfo_etDiseases.getText().toString();
        disease.setAllergy(allergies);
        disease.setDiseaseInfo(diseases);
    }
}