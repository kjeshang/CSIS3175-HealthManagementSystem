package com.example.healthmanagementapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.dao.CashierDAO;
import com.example.healthmanagementapp.dao.DoctorDAO;
import com.example.healthmanagementapp.dao.PatientDAO;
import com.example.healthmanagementapp.model.cashier.Cashier;
import com.example.healthmanagementapp.model.doctor.Doctor;
import com.example.healthmanagementapp.model.patient.Disease;
import com.example.healthmanagementapp.model.patient.Patient;

public class SignUpActivity extends AppCompatActivity {

    EditText SignUp_etUserID;
    EditText SignUp_etPassword;
    RadioButton SignUp_rdbPatient;
    RadioButton SignUp_rdbDoctor;
    RadioButton SignUp_rdbCashier;
    EditText SignUp_etFullName;
    EditText SignUp_etAllergies;
    EditText SignUp_etDiseases;
    EditText SignUp_etPostalCode;
    EditText SignUp_etLicense;
    Button SignUp_btnSignUp;

    Patient patient;
    Disease disease;
    int diseaseId;
    PatientDAO patientDAO;

    Doctor doctor;
    DoctorDAO doctorDAO;

    Cashier cashier;
    CashierDAO cashierDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        patientDAO = new PatientDAO(this);
        doctorDAO = new DoctorDAO(this);
        cashierDAO = new CashierDAO(this);

        SignUp_etUserID = findViewById(R.id.SignUp_etUserID);
        SignUp_etPassword = findViewById(R.id.SignUp_etPassword);
        SignUp_rdbPatient = findViewById(R.id.SignUp_rdbPatient);
        SignUp_rdbDoctor = findViewById(R.id.SignUp_rdbDoctor);
        SignUp_rdbCashier = findViewById(R.id.SignUp_rdbCashier);
        SignUp_etFullName = findViewById(R.id.SignUp_etFullName);
        SignUp_etAllergies = findViewById(R.id.SignUp_etAllergies);
        SignUp_etDiseases = findViewById(R.id.SignUp_etDiseases);
        SignUp_etPostalCode = findViewById(R.id.SignUp_etPostalCode);
        SignUp_etLicense = findViewById(R.id.SignUp_etLicense);
        SignUp_btnSignUp = findViewById(R.id.SignUp_btnSignUp);

        SignUp_etAllergies.setVisibility(View.INVISIBLE);
        SignUp_etDiseases.setVisibility(View.INVISIBLE);
        SignUp_etLicense.setVisibility(View.INVISIBLE);

        diseaseId = 1;

        SignUp_rdbPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SignUp_rdbPatient.isChecked()){
                    SignUp_etAllergies.setVisibility(View.VISIBLE);
                    SignUp_etDiseases.setVisibility(View.VISIBLE);
                    SignUp_etLicense.setVisibility(View.INVISIBLE);
                    SignUp_etPostalCode.setVisibility(View.VISIBLE);
                }
                else{
                    SignUp_etAllergies.setVisibility(View.INVISIBLE);
                    SignUp_etDiseases.setVisibility(View.INVISIBLE);
                    SignUp_etLicense.setVisibility(View.VISIBLE);
                    SignUp_etPostalCode.setVisibility(View.INVISIBLE);
                }
            }
        });

        SignUp_rdbDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SignUp_rdbDoctor.isChecked()){
                    SignUp_etLicense.setVisibility(View.VISIBLE);
                    SignUp_etAllergies.setVisibility(View.INVISIBLE);
                    SignUp_etDiseases.setVisibility(View.INVISIBLE);
                    SignUp_etPostalCode.setVisibility(View.VISIBLE);
                }
                else{
                    SignUp_etLicense.setVisibility(View.INVISIBLE);
                    SignUp_etAllergies.setVisibility(View.VISIBLE);
                    SignUp_etDiseases.setVisibility(View.VISIBLE);
                    SignUp_etPostalCode.setVisibility(View.INVISIBLE);
                }
            }
        });

        SignUp_rdbCashier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SignUp_rdbCashier.isChecked()){
                    SignUp_etAllergies.setVisibility(View.INVISIBLE);
                    SignUp_etDiseases.setVisibility(View.INVISIBLE);
                    SignUp_etLicense.setVisibility(View.INVISIBLE);
                    SignUp_etPostalCode.setVisibility(View.INVISIBLE);
                }
                else{
                    SignUp_etAllergies.setVisibility(View.VISIBLE);
                    SignUp_etDiseases.setVisibility(View.VISIBLE);
                    SignUp_etLicense.setVisibility(View.VISIBLE);
                    SignUp_etPostalCode.setVisibility(View.VISIBLE);
                }
            }
        });

        SignUp_btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!SignUp_rdbPatient.isChecked() && !SignUp_rdbDoctor.isChecked() && !SignUp_rdbCashier.isChecked()){
                    Toast.makeText(SignUpActivity.this,"Please indicate whether you are a Patient, Doctor, or Cashier",Toast.LENGTH_LONG).show();
                }
                // **************************** Patient ************************************************
                if(SignUp_rdbPatient.isChecked()){
                    patient = createPatient();
                    boolean validPatient = patient.checkPatient();
                    boolean patientExists = patientDAO.checkIfPatientExists(patient.getId(),patient.getPassword());
                    if(validPatient == false){
                        Toast.makeText(SignUpActivity.this,"All Patient information must be provided",Toast.LENGTH_LONG).show();
                    }
                    else if(patientExists == true){
                        Toast.makeText(SignUpActivity.this,"Patient already registered with Health Buddy",Toast.LENGTH_LONG).show();
                    }
                    else{
                        disease = createDisease(patient);
                        patientDAO.insertPatient(patient);
                        patientDAO.insertDisease(disease);
                        Toast.makeText(SignUpActivity.this,patient.toString() + "\n" + disease.toString(),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                    }
                }
                // **************************** Doctor ************************************************
                if(SignUp_rdbDoctor.isChecked()){
                    doctor = createDoctor();
                    boolean validDoctor = doctor.checkDoctor();
                    boolean doctorExists = doctorDAO.checkIfDoctorExists(doctor.getId(),doctor.getPassword());
                    if(validDoctor == false){
                        Toast.makeText(SignUpActivity.this,"All Doctor information must be provided",Toast.LENGTH_LONG).show();
                    }
                    else if(doctorExists == true){
                        Toast.makeText(SignUpActivity.this,"Doctor already registered with Health Buddy",Toast.LENGTH_LONG).show();
                    }
                    else{
                        doctorDAO.insertDoctor(doctor);
                        Toast.makeText(SignUpActivity.this,doctor.toString(),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                    }
                }
                // **************************** Cashier ************************************************
                if(SignUp_rdbCashier.isChecked()){
                    cashier = createCashier();
                    boolean validCashier = cashier.checkCashier();
                    boolean cashierExists = cashierDAO.checkIfCashierExists(cashier.getId(),cashier.getPassword());
                    if(validCashier == false){
                        Toast.makeText(SignUpActivity.this,"All Cashier information must be provided",Toast.LENGTH_LONG).show();
                    }
                    else if(cashierExists == true){
                        Toast.makeText(SignUpActivity.this,"Cashier already registered with Health Buddy",Toast.LENGTH_LONG).show();
                    }
                    else{
                        cashierDAO.insertCashier(cashier);
                        Toast.makeText(SignUpActivity.this,cashier.toString(),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                    }
                }
            }
        });
    }

    private Patient createPatient(){
        Patient patient = null;
        String id = SignUp_etUserID.getText().toString();
        String fullName = SignUp_etFullName.getText().toString();
        String password = SignUp_etPassword.getText().toString();
        String postalCode = SignUp_etPostalCode.getText().toString();
        patient = new Patient(id,fullName,password,postalCode);
        return patient;
    }

    private Disease createDisease(Patient patient){
        Disease disease = null;
        String allergies = SignUp_etAllergies.getText().toString();
        String diseaseInfo = SignUp_etDiseases.getText().toString();
        disease = new Disease(diseaseId,patient.getId(),allergies,diseaseInfo);
        diseaseId++;
        return disease;
    }

    private Doctor createDoctor(){
        Doctor doctor = null;
        String id = SignUp_etUserID.getText().toString();
        String fullName = SignUp_etFullName.getText().toString();
        String password = SignUp_etPassword.getText().toString();
        String licenseNumber = SignUp_etLicense.getText().toString();
        String postalCode = SignUp_etPostalCode.getText().toString();
        doctor = new Doctor(id,fullName,password,licenseNumber,postalCode);
        return doctor;
    }

    private Cashier createCashier(){
        Cashier cashier = null;
        String id = SignUp_etUserID.getText().toString();
        String fullName = SignUp_etFullName.getText().toString();
        String password = SignUp_etPassword.getText().toString();
        cashier = new Cashier(id,fullName,password);
        return cashier;
    }

}