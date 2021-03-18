package com.example.healthmanagementapp.UI.cashierUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.UI.MainActivity;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.cashier.Cashier;

public class CashierAccount extends AppCompatActivity {

    String cashierId;
    Cashier cashier;

    DatabaseHelper databaseHelper;

    TextView CashierAccount_tvCashierName;
    EditText CashierAccount_etBalance;
    Button CashierAccount_btnLogOut;
    Button CashierAccount_btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_account);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        cashierId = preference.getString("cashierId",null);

        CashierAccount_tvCashierName = findViewById(R.id.CashierAccount_tvCashierName);
        CashierAccount_etBalance = findViewById(R.id.CashierAccount_etBalance);
        CashierAccount_btnLogOut = findViewById(R.id.CashierAccount_btnLogOut);
        CashierAccount_btnInfo = findViewById(R.id.CashierAccount_btnInfo);

        cashier = databaseHelper.getCashierById(cashierId);
        CashierAccount_tvCashierName.setText(cashier.getName());

        formatCashierInfo();

        CashierAccount_btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CashierAccount.this, MainActivity.class));
            }
        });

        CashierAccount_btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CashierAccount.this,CashierInfo.class));
            }
        });
    }
    private void formatCashierInfo(){
        CashierAccount_etBalance.setFocusable(false);
        CashierAccount_etBalance.setFocusableInTouchMode(false);
        CashierAccount_etBalance.setClickable(false);
    }
}