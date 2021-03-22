package com.example.healthmanagementapp.UI.doctorUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.UI.MainActivity;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.User;
import com.example.healthmanagementapp.model.doctor.Doctor;
import com.example.healthmanagementapp.model.patient.Patient;

import java.util.List;

public class DoctorAccount extends AppCompatActivity {

    String doctorId;
    Doctor doctor;

    DatabaseHelper databaseHelper;

    TextView DoctorAccount_tvDoctorName;
    EditText DoctorAccount_etDoctorLicense;
    EditText DoctorAccount_etDoctorPostal;
    Button DoctorAccount_btnLogOut;
    Button DoctorAccount_btnInfo;

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
    }

    private void formatDoctorInfo(){
        DoctorAccount_etDoctorLicense.setFocusable(false);
        DoctorAccount_etDoctorLicense.setFocusableInTouchMode(false);
        DoctorAccount_etDoctorLicense.setClickable(false);

        DoctorAccount_etDoctorPostal.setFocusable(false);
        DoctorAccount_etDoctorPostal.setFocusableInTouchMode(false);
        DoctorAccount_etDoctorPostal.setClickable(false);
    }

    private void fillPatientsWInquiryList(LinearLayout ll){
        List<String> list = databaseHelper.fillPatientsWInquiry(doctorId);
        TextView textView = null;
        Patient patient;
        for(int x=0; x < list.size(); x++){
            patient = databaseHelper.getPatientById(list.get(x));
            textView.setText(patient.getName());
            ll.addView(textView);
        }
    }
}