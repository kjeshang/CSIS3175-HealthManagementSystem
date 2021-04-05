package com.example.healthmanagementapp.UI.doctorUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.UI.MainActivity;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.doctor.Doctor;
import com.example.healthmanagementapp.model.patient.Patient;

public class DoctorAccount extends AppCompatActivity {

    String doctorId;
    Doctor doctor;
    ListView DoctorAccount_lvListPatients;

    DatabaseHelper databaseHelper;

    TextView DoctorAccount_tvDoctorName;
    EditText DoctorAccount_etDoctorLicense;
    EditText DoctorAccount_etDoctorPostal;
    Button DoctorAccount_btnLogOut;
    Button DoctorAccount_btnInfo;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        doctorId = preference.getString("doctorId",null);

        DoctorAccount_tvDoctorName = findViewById(R.id.DoctorAccount_tvDoctorName);
        DoctorAccount_etDoctorLicense = findViewById(R.id.DoctorAccount_etDoctorLicense);
        DoctorAccount_etDoctorPostal = findViewById(R.id.DoctorAccount_etDoctorPostal);
        DoctorAccount_btnLogOut = findViewById(R.id.DoctorAccount_btnLogOut);
        DoctorAccount_btnInfo = findViewById(R.id.DoctorAccount_btnInfo);

        DoctorAccount_lvListPatients = findViewById(R.id.DoctorAccount_lvListPatients);
        showPatientsWithInquiry(databaseHelper);

        doctor = databaseHelper.getDoctorById(doctorId);
        DoctorAccount_tvDoctorName.setText(doctor.getName());
        DoctorAccount_etDoctorLicense.setText(doctor.getLicenseNumber());
        DoctorAccount_etDoctorPostal.setText(doctor.getPostalCode());

        formatDoctorInfo();

        DoctorAccount_btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorAccount.this, MainActivity.class));
            }
        });

        DoctorAccount_btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorAccount.this,DoctorInfo.class));
            }
        });

        DoctorAccount_lvListPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = preference.edit();
                Patient clickedPatient = (Patient) parent.getItemAtPosition(position);
                editor.putString("selectedPatientId",clickedPatient.getId());
                editor.commit();
                startActivity(new Intent(DoctorAccount.this, DoctorOnlineHelp.class));
            }
        });
    }

    private void formatDoctorInfo(){
        DoctorAccount_etDoctorLicense.setFocusable(false);
        DoctorAccount_etDoctorLicense.setFocusableInTouchMode(false);
        DoctorAccount_etDoctorLicense.setClickable(false);

        DoctorAccount_etDoctorPostal.setFocusable(false);
        DoctorAccount_etDoctorPostal.setFocusableInTouchMode(false);
        DoctorAccount_etDoctorPostal.setClickable(false);
    }

    private void showPatientsWithInquiry(DatabaseHelper databaseHelper){
        arrayAdapter = new ArrayAdapter(DoctorAccount.this, android.R.layout.simple_list_item_1, databaseHelper.fillPatientsWInquiry(doctorId));
        DoctorAccount_lvListPatients.setAdapter(arrayAdapter);
    }
}