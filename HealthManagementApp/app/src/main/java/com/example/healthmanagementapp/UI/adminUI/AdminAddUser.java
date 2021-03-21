package com.example.healthmanagementapp.UI.adminUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.healthmanagementapp.R;
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
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_user);
    }
}