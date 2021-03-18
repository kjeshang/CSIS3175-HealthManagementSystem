package com.example.healthmanagementapp.UI.doctorUI;

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
import com.example.healthmanagementapp.model.doctor.Doctor;

public class DoctorInfo extends AppCompatActivity {

    String doctorId;
    Doctor doctor;

    DatabaseHelper databaseHelper;

    EditText DoctorInfo_etUserID;
    EditText DoctorInfo_etPassword;
    EditText DoctorInfo_etFullName;
    EditText DoctorInfo_etPostalCode;
    EditText DoctorInfo_etLicenseNumber;
    Button DoctorInfo_btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        doctorId = preference.getString("doctorId",null);

        DoctorInfo_etUserID = findViewById(R.id.DoctorInfo_etUserID);
        DoctorInfo_etPassword = findViewById(R.id.DoctorInfo_etPassword);
        DoctorInfo_etFullName = findViewById(R.id.DoctorInfo_etFullName);
        DoctorInfo_etPostalCode = findViewById(R.id.DoctorInfo_etPostalCode);
        DoctorInfo_etLicenseNumber = findViewById(R.id.DoctorInfo_etLicenseNumber);
        DoctorInfo_btnSave = findViewById(R.id.DoctorInfo_btnSave);

        doctor = databaseHelper.getDoctorById(doctorId);

        DoctorInfo_etUserID.setText(doctor.getId());
        formatInfo();
        DoctorInfo_etPassword.setText(doctor.getPassword());
        DoctorInfo_etFullName.setText(doctor.getName());
        DoctorInfo_etPostalCode.setText(doctor.getPostalCode());
        DoctorInfo_etLicenseNumber.setText(doctor.getLicenseNumber());

        DoctorInfo_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUpdatedDoctor();
                databaseHelper.updateDoctor(doctor);
                Toast.makeText(DoctorInfo.this,doctor.display(),Toast.LENGTH_LONG).show();
                startActivity(new Intent(DoctorInfo.this,DoctorAccount.class));
            }
        });
    }

    private void formatInfo(){
        DoctorInfo_etUserID.setFocusable(false);
        DoctorInfo_etUserID.setFocusableInTouchMode(false);
        DoctorInfo_etUserID.setClickable(false);
    }

    private void createUpdatedDoctor(){
        String password = DoctorInfo_etPassword.getText().toString();
        String fullName = DoctorInfo_etFullName.getText().toString();
        String postalCode = DoctorInfo_etPostalCode.getText().toString();
        String licenseNumber = DoctorInfo_etLicenseNumber.getText().toString();
        doctor.setPassword(password);
        doctor.setName(fullName);
        doctor.setPostalCode(postalCode);
        doctor.setLicenseNumber(licenseNumber);
    }
}