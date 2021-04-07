package com.example.healthmanagementapp.UI.cashierUI;

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
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.UI.MainActivity;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.cashier.Cashier;
import com.example.healthmanagementapp.model.cashier.Payment;
import com.example.healthmanagementapp.model.patient.Patient;

import java.util.ArrayList;
import java.util.List;

public class CashierAccount extends AppCompatActivity {

    String cashierId;
    Cashier cashier;
    int totalDueAmount;
    List<Payment> listDuePayments = new ArrayList<>();
    ListView CashierAccount_lvListPatients;

    DatabaseHelper databaseHelper;

    TextView CashierAccount_tvCashierName;
    EditText CashierAccount_etBalance;
    Button CashierAccount_btnLogOut;
    Button CashierAccount_btnInfo;
    ArrayAdapter arrayAdapter;

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
        CashierAccount_lvListPatients = findViewById(R.id.CashierAccount_lvListPatients);

        cashier = databaseHelper.getCashierById(cashierId);
        CashierAccount_tvCashierName.setText(cashier.getName());

        showPatientsWithDueBalance(databaseHelper);
        listDuePayments = databaseHelper.getDuePaymentsTotalAmount();

        for(int i=0; i<listDuePayments.size(); i++){
            totalDueAmount += listDuePayments.get(i).getValue();
        }

        CashierAccount_etBalance.setText("CAD " + totalDueAmount + ".00");

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
        CashierAccount_lvListPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient clickedPatient = (Patient) parent.getItemAtPosition(position);
                String patientName = clickedPatient.getName();
                Toast.makeText(CashierAccount.this,"Reminder of due payment sent to " + patientName,Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void formatCashierInfo(){
        CashierAccount_etBalance.setFocusable(false);
        CashierAccount_etBalance.setFocusableInTouchMode(false);
        CashierAccount_etBalance.setClickable(false);
    }
    private void showPatientsWithDueBalance(DatabaseHelper databaseHelper){
        arrayAdapter = new ArrayAdapter(CashierAccount.this, android.R.layout.simple_list_item_1, databaseHelper.fillPatientsWDueBalance());
        CashierAccount_lvListPatients.setAdapter(arrayAdapter);
    }
}