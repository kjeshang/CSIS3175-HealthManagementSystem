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
import com.example.healthmanagementapp.UI.adminUI.AdminAccount;
import com.example.healthmanagementapp.UI.cashierUI.CashierAccount;
import com.example.healthmanagementapp.UI.doctorUI.DoctorAccount;
import com.example.healthmanagementapp.UI.patientUI.PatientAccount;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.admin.Admin;

public class LoginActivity extends AppCompatActivity {

    EditText Login_etUserID;
    EditText Login_etPassword;
    Button Login_btnLogin;

    Admin admin;

    String userId;
    String password;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = DatabaseHelper.getInstance(this);

        boolean adminPrimeExists = databaseHelper.checkIfAdminExists("mehwish","mehwish123");
        if(adminPrimeExists == false){
            admin = new Admin("mehwish","Mehwish Bashir","mehwish123");
            databaseHelper.insertAdmin(admin);
        }

        Login_etUserID = findViewById(R.id.Login_etUserID);
        Login_etPassword = findViewById(R.id.Login_etPassword);
        Login_btnLogin = findViewById(R.id.Login_btnLogin);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);

        Login_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preference.edit();
                editor.clear().commit();
                userId = Login_etUserID.getText().toString();
                password = Login_etPassword.getText().toString();
                boolean patientExists = databaseHelper.checkIfPatientExists(userId,password);
                boolean doctorExists = databaseHelper.checkIfDoctorExists(userId,password);
                boolean cashierExists = databaseHelper.checkIfCashierExists(userId,password);
                boolean adminExists = databaseHelper.checkIfAdminExists(userId,password);
                if(patientExists == true){
                    editor.putString("patientId",databaseHelper.getPatientId(userId));
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, PatientAccount.class));
                }
                else if(doctorExists == true){
                    editor.putString("doctorId",databaseHelper.getDoctorId(userId));
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, DoctorAccount.class));
                }
                else if(cashierExists == true){
                    editor.putString("cashierId",databaseHelper.getCashierId(userId));
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, CashierAccount.class));
                }
                else if(adminExists == true){
                    editor.putString("adminId",databaseHelper.getAdminId(userId));
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, AdminAccount.class));
                }
                else{
                    Toast.makeText(LoginActivity.this,"User not registered with Health Buddy",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}