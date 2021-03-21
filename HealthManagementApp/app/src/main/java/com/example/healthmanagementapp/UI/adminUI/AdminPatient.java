package com.example.healthmanagementapp.UI.adminUI;

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
import com.example.healthmanagementapp.model.patient.Patient;

public class AdminPatient extends AppCompatActivity {

    String patientId;
    Patient patient;

    DatabaseHelper databaseHelper;

    EditText AdminPatient_etUserID;
    EditText AdminPatient_etPassword;
    EditText AdminPatient_etFullName;
    EditText AdminPatient_etPostalCode;
    EditText AdminPatient_etAllergies;
    EditText AdminPatient_etDiseases;
    Button AdminPatient_btnSave;
    Button AdminPatient_btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_patient);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences adminPreference = getSharedPreferences("admin_user",MODE_PRIVATE);
        patientId = adminPreference.getString("patientId",null);

        patient = databaseHelper.getPatientById(patientId);

        AdminPatient_etUserID = findViewById(R.id.AdminPatient_etUserID);
        AdminPatient_etPassword = findViewById(R.id.AdminPatient_etPassword);
        AdminPatient_etFullName = findViewById(R.id.AdminPatient_etFullName);
        AdminPatient_etPostalCode = findViewById(R.id.AdminPatient_etPostalCode);
        AdminPatient_etAllergies = findViewById(R.id.AdminPatient_etAllergies);
        AdminPatient_etDiseases = findViewById(R.id.AdminPatient_etDiseases);
        AdminPatient_btnSave = findViewById(R.id.AdminPatient_btnSave);
        AdminPatient_btnDelete = findViewById(R.id.AdminPatient_btnDelete);

        AdminPatient_etUserID.setText(patient.getId());
        formatInfo();
        AdminPatient_etPassword.setText(patient.getPassword());
        AdminPatient_etFullName.setText(patient.getName());
        AdminPatient_etPostalCode.setText(patient.getPostalCode());
        AdminPatient_etAllergies.setText(patient.getAllergies());
        AdminPatient_etDiseases.setText(patient.getDiseases());

        AdminPatient_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUpdatedPatient();
                databaseHelper.updatePatient(patient);
                Toast.makeText(AdminPatient.this,patient.display() + "",Toast.LENGTH_LONG).show();
                startActivity(new Intent(AdminPatient.this,AdminAccount.class));
            }
        });

        AdminPatient_btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deletePatient(patient);
                Toast.makeText(AdminPatient.this,"Patient deleted",Toast.LENGTH_LONG).show();
                startActivity(new Intent(AdminPatient.this,AdminAccount.class));
            }
        });
    }

    private void createUpdatedPatient(){
        String patientName = AdminPatient_etFullName.getText().toString();
        String patientPassword = AdminPatient_etPassword.getText().toString();
        String patientPostalCode = AdminPatient_etPostalCode.getText().toString();
        String allergies = AdminPatient_etAllergies.getText().toString();
        String diseases = AdminPatient_etDiseases.getText().toString();
        patient.setName(patientName);
        patient.setPassword(patientPassword);
        patient.setPostalCode(patientPostalCode);
        patient.setAllergies(allergies);
        patient.setDiseases(diseases);
    }

    private void formatInfo(){
        AdminPatient_etUserID.setFocusable(false);
        AdminPatient_etUserID.setFocusableInTouchMode(false);
        AdminPatient_etUserID.setClickable(false);
    }
}