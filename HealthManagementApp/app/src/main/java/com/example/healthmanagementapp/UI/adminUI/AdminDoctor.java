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
import com.example.healthmanagementapp.model.doctor.Doctor;

public class AdminDoctor extends AppCompatActivity {

    String doctorId;
    Doctor doctor;

    DatabaseHelper databaseHelper;

    EditText AdminDoctor_etUserID;
    EditText AdminDoctor_etPassword;
    EditText AdminDoctor_etFullName;
    EditText AdminDoctor_etPostalCode;
    EditText AdminDoctor_etLicenseNumber;
    Button AdminDoctor_btnSave;
    Button AdminDoctor_btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_doctor);

        databaseHelper = DatabaseHelper.getInstance(this);
        SharedPreferences adminPreference = getSharedPreferences("admin_user",MODE_PRIVATE);
        doctorId = adminPreference.getString("doctorId",null);

        doctor = databaseHelper.getDoctorById(doctorId);

        AdminDoctor_etUserID = findViewById(R.id.AdminDoctor_etUserID);
        AdminDoctor_etPassword = findViewById(R.id.AdminDoctor_etPassword);
        AdminDoctor_etFullName = findViewById(R.id.AdminDoctor_etFullName);
        AdminDoctor_etPostalCode = findViewById(R.id.AdminDoctor_etPostalCode);
        AdminDoctor_etLicenseNumber = findViewById(R.id.AdminDoctor_etLicenseNumber);
        AdminDoctor_btnSave = findViewById(R.id.AdminDoctor_btnSave);
        AdminDoctor_btnDelete = findViewById(R.id.AdminDoctor_btnDelete);

        AdminDoctor_etUserID.setText(doctor.getId());
        formatInfo();
        AdminDoctor_etPassword.setText(doctor.getPassword());
        AdminDoctor_etFullName.setText(doctor.getName());
        AdminDoctor_etPostalCode.setText(doctor.getPostalCode());
        AdminDoctor_etLicenseNumber.setText(doctor.getLicenseNumber());

        AdminDoctor_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUpdatedDoctor();
                databaseHelper.updateDoctor(doctor);
                Toast.makeText(AdminDoctor.this,doctor.display() + "",Toast.LENGTH_LONG).show();
                startActivity(new Intent(AdminDoctor.this,AdminAccount.class));
            }
        });

        AdminDoctor_btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteDoctor(doctor);
                Toast.makeText(AdminDoctor.this,"Doctor deleted",Toast.LENGTH_LONG).show();
                startActivity(new Intent(AdminDoctor.this,AdminAccount.class));
            }
        });
    }

    private void createUpdatedDoctor(){
        String doctorName = AdminDoctor_etFullName.getText().toString();
        String doctorPassword = AdminDoctor_etPassword.getText().toString();
        String clinicPostalCode = AdminDoctor_etPostalCode.getText().toString();
        String licenseNumber = AdminDoctor_etLicenseNumber.getText().toString();
        doctor.setName(doctorName);
        doctor.setPassword(doctorPassword);
        doctor.setPostalCode(clinicPostalCode);
        doctor.setLicenseNumber(licenseNumber);
    }

    private void formatInfo(){
        AdminDoctor_etUserID.setFocusable(false);
        AdminDoctor_etUserID.setFocusableInTouchMode(false);
        AdminDoctor_etUserID.setClickable(false);
    }
}