package com.example.healthmanagementapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.UI.cashierUI.CashierAccount;
import com.example.healthmanagementapp.UI.doctorUI.DoctorAccount;
import com.example.healthmanagementapp.UI.patientUI.PatientAccount;
import com.example.healthmanagementapp.dao.CashierDAO;
import com.example.healthmanagementapp.dao.DoctorDAO;
import com.example.healthmanagementapp.dao.PatientDAO;
import com.example.healthmanagementapp.model.cashier.Cashier;

public class LoginActivity extends AppCompatActivity {

    EditText Login_etUserID;
    EditText Login_etPassword;
    Button Login_btnLogin;

    String userId;
    String password;

    PatientDAO patientDAO;
    DoctorDAO doctorDAO;
    CashierDAO cashierDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        patientDAO = new PatientDAO(this);
        doctorDAO = new DoctorDAO(this);
        cashierDAO = new CashierDAO(this);

        Login_etUserID = findViewById(R.id.Login_etUserID);
        Login_etPassword = findViewById(R.id.Login_etPassword);
        Login_btnLogin = findViewById(R.id.Login_btnLogin);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);

        Login_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preference.edit();
                userId = Login_etUserID.getText().toString();
                password = Login_etPassword.getText().toString();
                boolean patientExists = patientDAO.checkIfPatientExists(userId,password);
                boolean doctorExists = doctorDAO.checkIfDoctorExists(userId,password);
                boolean cashierExists = cashierDAO.checkIfCashierExists(userId,password);
                if(patientExists == true){
                    editor.putString("patientId",patientDAO.getPatientId(userId));
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, PatientAccount.class));
                }
                else if(doctorExists == true){
                    editor.putString("doctorId",doctorDAO.getDoctorId(userId));
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, DoctorAccount.class));
                }
                else if(cashierExists == true){
                    editor.putString("cashierId",cashierDAO.getCashierId(userId));
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, CashierAccount.class));
                }
                else{
                    Toast.makeText(LoginActivity.this,"User not registered with Health Buddy",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}