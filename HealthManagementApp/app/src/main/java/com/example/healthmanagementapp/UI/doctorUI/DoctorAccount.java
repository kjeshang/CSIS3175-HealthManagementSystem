package com.example.healthmanagementapp.UI.doctorUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.UI.MainActivity;
import com.example.healthmanagementapp.UI.OnlineHelp;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.doctor.Doctor;
import com.example.healthmanagementapp.model.patient.Patient;

import java.util.List;

public class DoctorAccount extends AppCompatActivity implements DoctorListAdapter.ItemClickListener {

    String doctorId;
    Doctor doctor;
    List<Patient> patientName;
    Integer[] pictures;

    DatabaseHelper databaseHelper;

    TextView DoctorAccount_tvDoctorName;
    EditText DoctorAccount_etDoctorLicense;
    EditText DoctorAccount_etDoctorPostal;
    Button DoctorAccount_btnLogOut;
    Button DoctorAccount_btnInfo;
    DoctorListAdapter adapter;

    Button DoctorAccount_btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        doctorId = preference.getString("doctorId",null);

        patientName = databaseHelper.fillPatientsWInquiry(doctorId);

        DoctorAccount_tvDoctorName = findViewById(R.id.DoctorAccount_tvDoctorName);
        DoctorAccount_etDoctorLicense = findViewById(R.id.DoctorAccount_etDoctorLicense);
        DoctorAccount_etDoctorPostal = findViewById(R.id.DoctorAccount_etDoctorPostal);
        DoctorAccount_btnLogOut = findViewById(R.id.DoctorAccount_btnLogOut);
        DoctorAccount_btnInfo = findViewById(R.id.DoctorAccount_btnInfo);

        DoctorAccount_btnChat = findViewById(R.id.DoctorAccount_btnChat);

        doctor = databaseHelper.getDoctorById(doctorId);
        DoctorAccount_tvDoctorName.setText(doctor.getName());
        DoctorAccount_etDoctorLicense.setText(doctor.getLicenseNumber());
        DoctorAccount_etDoctorPostal.setText(doctor.getPostalCode());

        formatDoctorInfo();

        RecyclerView recyclerView = findViewById(R.id.DoctorAccount_rvListPatients);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this,numberOfColumns));
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DoctorListAdapter(this,pictures, patientName);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

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

        DoctorAccount_btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(DoctorAccount.this, OnlineHelp.class);
                x.putExtra("patientID", "p01");
                x.putExtra("doctorID", doctorId);
                startActivity(x);
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

//    private void fillPatientsWInquiryList(GridLayout ll){
//        List<String> list = databaseHelper.fillPatientsWInquiry(doctorId);
//        TextView textView = null;
//        Patient patient;
//        for(int x=0; x < list.size(); x++){
//            patient = databaseHelper.getPatientById(list.get(x));
//            textView.setText(patient.getName());
//            ll.addView(textView);
//        }
//    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(DoctorAccount.this, OnlineHelp.class);
        i.putExtra("patientID", adapter.getItem(position).getId());
        i.putExtra("doctorD", doctorId);
        startActivity(i);
    }
}