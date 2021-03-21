package com.example.healthmanagementapp.UI.adminUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.cashier.Cashier;
import com.example.healthmanagementapp.model.doctor.Doctor;
import com.example.healthmanagementapp.model.patient.Patient;

public class AdminAddUser extends AppCompatActivity {

    EditText AdminAddUser_etUserID;
    EditText AdminAddUser_etPassword;
    RadioButton AdminAddUser_rdbPatient;
    RadioButton AdminAddUser_rdbDoctor;
    RadioButton AdminAddUser_rdbCashier;
    EditText AdminAddUser_etFullName;
    EditText AdminAddUser_etAllergies;
    EditText AdminAddUser_etDiseases;
    EditText AdminAddUser_etPostalCode;
    EditText AdminAddUser_etLicense;
    Button AdminAddUser_btnAdd;

    Patient patient;
    Doctor doctor;
    Cashier cashier;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_user);

        databaseHelper = DatabaseHelper.getInstance(this);

        AdminAddUser_etUserID = findViewById(R.id.AdminAddUser_etUserID);
        AdminAddUser_etPassword = findViewById(R.id.AdminAddUser_etPassword);
        AdminAddUser_rdbPatient = findViewById(R.id.AdminAddUser_rdbPatient);
        AdminAddUser_rdbDoctor = findViewById(R.id.AdminAddUser_rdbDoctor);
        AdminAddUser_rdbCashier = findViewById(R.id.AdminAddUser_rdbCashier);
        AdminAddUser_etFullName = findViewById(R.id.AdminAddUser_etFullName);
        AdminAddUser_etAllergies = findViewById(R.id.AdminAddUser_etAllergies);
        AdminAddUser_etDiseases = findViewById(R.id.AdminAddUser_etDiseases);
        AdminAddUser_etPostalCode = findViewById(R.id.AdminAddUser_etPostalCode);
        AdminAddUser_etLicense = findViewById(R.id.AdminAddUser_etLicense);
        AdminAddUser_btnAdd = findViewById(R.id.AdminAddUser_btnAdd);

        AdminAddUser_etAllergies.setVisibility(View.INVISIBLE);
        AdminAddUser_etDiseases.setVisibility(View.INVISIBLE);
        AdminAddUser_etLicense.setVisibility(View.INVISIBLE);
        AdminAddUser_etPostalCode.setVisibility(View.INVISIBLE);

        AdminAddUser_rdbPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAddUser_etAllergies.setVisibility(View.VISIBLE);
                AdminAddUser_etDiseases.setVisibility(View.VISIBLE);
                AdminAddUser_etLicense.setVisibility(View.INVISIBLE);
                AdminAddUser_etPostalCode.setVisibility(View.VISIBLE);
            }
        });

        AdminAddUser_rdbDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAddUser_etAllergies.setVisibility(View.INVISIBLE);
                AdminAddUser_etDiseases.setVisibility(View.INVISIBLE);
                AdminAddUser_etLicense.setVisibility(View.VISIBLE);
                AdminAddUser_etPostalCode.setVisibility(View.VISIBLE);
            }
        });

        AdminAddUser_rdbCashier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAddUser_etAllergies.setVisibility(View.INVISIBLE);
                AdminAddUser_etDiseases.setVisibility(View.INVISIBLE);
                AdminAddUser_etLicense.setVisibility(View.INVISIBLE);
                AdminAddUser_etPostalCode.setVisibility(View.INVISIBLE);
            }
        });

        AdminAddUser_btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AdminAddUser_rdbPatient.isChecked() && !AdminAddUser_rdbDoctor.isChecked() && !AdminAddUser_rdbCashier.isChecked()){
                    Toast.makeText(AdminAddUser.this,"Please indicate whether you are adding a Patient, Doctor, or Cashier",Toast.LENGTH_LONG).show();
                }
                // **************************** Patient ************************************************
                if(AdminAddUser_rdbPatient.isChecked()){
                    patient = createPatient();
                    boolean validPatient = patient.checkUser();
                    boolean patientExists = databaseHelper.checkIfPatientExists(patient.getId(),patient.getPassword());
                    if(validPatient == false){
                        Toast.makeText(AdminAddUser.this,"All Patient Information must be provided",Toast.LENGTH_LONG).show();
                    }
                    else if(patientExists == true){
                        Toast.makeText(AdminAddUser.this, "Patient already registered with Health Buddy", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        databaseHelper.insertPatient(patient);
                        Toast.makeText(AdminAddUser.this, patient.display() + "", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminAddUser.this,AdminAccount.class));
                    }
                }
                // **************************** Doctor ************************************************
                if(AdminAddUser_rdbDoctor.isChecked()){
                    doctor = createDoctor();
                    boolean validDoctor = doctor.checkUser();
                    boolean doctorExists = databaseHelper.checkIfDoctorExists(doctor.getId(),doctor.getPassword());
                    if(validDoctor == false){
                        Toast.makeText(AdminAddUser.this,"All Doctor Information must be provided",Toast.LENGTH_LONG).show();
                    }
                    else if(doctorExists == true){
                        Toast.makeText(AdminAddUser.this, "Doctor already registered with Health Buddy", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        databaseHelper.insertDoctor(doctor);
                        Toast.makeText(AdminAddUser.this, doctor.display() + "",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AdminAddUser.this,AdminAccount.class));
                    }
                }
                // **************************** Cashier ************************************************
                if(AdminAddUser_rdbCashier.isChecked()){
                        cashier = createCashier();
                        boolean validCashier = cashier.checkUser();
                        boolean cashierExists = databaseHelper.checkIfCashierExists(cashier.getId(),cashier.getPassword());
                        if(validCashier == false){
                            Toast.makeText(AdminAddUser.this,"All Cashier Information must be provided",Toast.LENGTH_LONG).show();
                        }
                        else if(cashierExists == true){
                            Toast.makeText(AdminAddUser.this, "Cashier already registered with Health Buddy", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            databaseHelper.insertCashier(cashier);
                            Toast.makeText(AdminAddUser.this, cashier.display() + "", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AdminAddUser.this,AdminAccount.class));
                        }
                }
            }
        });
    }

    private Patient createPatient(){
        Patient patient = null;
        String id = AdminAddUser_etUserID.getText().toString();
        String fullName = AdminAddUser_etFullName.getText().toString();
        String password = AdminAddUser_etPassword.getText().toString();
        String postalCode = AdminAddUser_etPostalCode.getText().toString();
        String allergies = AdminAddUser_etAllergies.getText().toString();
        String diseases = AdminAddUser_etDiseases.getText().toString();
        patient = new Patient(id,fullName,password,postalCode,allergies,diseases);
        return patient;
    }

    private Doctor createDoctor(){
        Doctor doctor = null;
        String id = AdminAddUser_etUserID.getText().toString();
        String fullName = AdminAddUser_etFullName.getText().toString();
        String password = AdminAddUser_etPassword.getText().toString();
        String postalCode = AdminAddUser_etPostalCode.getText().toString();
        String licenseNumber = AdminAddUser_etLicense.getText().toString();
        doctor = new Doctor(id,fullName,password,postalCode,licenseNumber);
        return doctor;
    }

    private Cashier createCashier(){
        Cashier cashier = null;
        String id = AdminAddUser_etUserID.getText().toString();
        String fullName = AdminAddUser_etFullName.getText().toString();
        String password = AdminAddUser_etPassword.getText().toString();
        cashier = new Cashier(id,fullName,password);
        return cashier;
    }
}