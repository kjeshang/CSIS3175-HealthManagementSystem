package com.example.healthmanagementapp.UI.cashierUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.dao.CashierDAO;
import com.example.healthmanagementapp.model.cashier.Cashier;

public class CashierInfo extends AppCompatActivity {

    String cashierId;
    Cashier cashier;

    CashierDAO cashierDAO;

    EditText CashierInfo_etUserID;
    EditText CashierInfo_etPassword;
    EditText CashierInfo_etFullName;
    Button CashierInfo_btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_info);

        cashierDAO = new CashierDAO(this);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        cashierId = preference.getString("cashierId",null);

        CashierInfo_etUserID = findViewById(R.id.CashierInfo_etUserID);
        CashierInfo_etPassword = findViewById(R.id.CashierInfo_etPassword);
        CashierInfo_etFullName = findViewById(R.id.CashierInfo_etFullName);
        CashierInfo_btnSave = findViewById(R.id.CashierInfo_btnSave);

        cashier = cashierDAO.getCashierById(cashierId);

        CashierInfo_etUserID.setText(cashier.getId());
        formatInfo();
        CashierInfo_etPassword.setText(cashier.getPassword());
        CashierInfo_etFullName.setText(cashier.getName());

        CashierInfo_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUpdatedCashier();
                cashierDAO.updateCashier(cashier);
                Toast.makeText(CashierInfo.this,cashier.toString(),Toast.LENGTH_LONG).show();
                startActivity(new Intent(CashierInfo.this,CashierAccount.class));
            }
        });
    }

    private void formatInfo(){
        CashierInfo_etUserID.setFocusable(false);
        CashierInfo_etUserID.setFocusableInTouchMode(false);
        CashierInfo_etUserID.setClickable(false);
    }

    private void createUpdatedCashier(){
        String password = CashierInfo_etPassword.getText().toString();
        String fullName = CashierInfo_etFullName.getText().toString();
        cashier.setPassword(password);
        cashier.setName(fullName);
    }
}