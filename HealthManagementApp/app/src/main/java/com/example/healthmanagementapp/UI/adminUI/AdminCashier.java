package com.example.healthmanagementapp.UI.adminUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.cashier.Cashier;

public class AdminCashier extends AppCompatActivity {

    String cashierId;
    Cashier cashier;

    DatabaseHelper databaseHelper;

    EditText AdminCashier_etUserID;
    EditText AdminCashier_etPassword;
    EditText AdminCashier_etFullName;
    Button AdminCashier_btnSave;
    Button AdminCashier_btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cashier);

        databaseHelper = DatabaseHelper.getInstance(this);
        SharedPreferences adminPreference = getSharedPreferences("admin_user",MODE_PRIVATE);
        cashierId = adminPreference.getString("cashierId",null);

        AdminCashier_etUserID = findViewById(R.id.AdminCashier_etUserID);
        AdminCashier_etPassword = findViewById(R.id.AdminCashier_etPassword);
        AdminCashier_etFullName = findViewById(R.id.AdminCashier_etFullName);
        AdminCashier_btnSave = findViewById(R.id.AdminCashier_btnSave);
        AdminCashier_btnDelete = findViewById(R.id.AdminCashier_btnDelete);

        cashier = databaseHelper.getCashierById(cashierId);

        AdminCashier_etUserID.setText(cashier.getId());
        formatInfo();
        AdminCashier_etPassword.setText(cashier.getPassword());
        AdminCashier_etFullName.setText(cashier.getName());

        AdminCashier_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUpdatedCashier();
                databaseHelper.updateCashier(cashier);
                Toast.makeText(AdminCashier.this,cashier.display() + "",Toast.LENGTH_LONG).show();
                startActivity(new Intent(AdminCashier.this,AdminAccount.class));
            }
        });

        AdminCashier_btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteCashier(cashier);
                Toast.makeText(AdminCashier.this,"Doctor deleted",Toast.LENGTH_LONG).show();
                startActivity(new Intent(AdminCashier.this,AdminAccount.class));
            }
        });

    }

    private void createUpdatedCashier(){
        String cashierName = AdminCashier_etFullName.getText().toString();
        String cashierPassword = AdminCashier_etPassword.getText().toString();
        cashier.setName(cashierName);
        cashier.setPassword(cashierPassword);
    }

    private void formatInfo(){
        AdminCashier_etUserID.setFocusable(false);
        AdminCashier_etUserID.setFocusableInTouchMode(false);
        AdminCashier_etUserID.setClickable(false);
    }
}