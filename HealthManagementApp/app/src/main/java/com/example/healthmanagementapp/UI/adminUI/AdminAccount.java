package com.example.healthmanagementapp.UI.adminUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.UI.MainActivity;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.User;
import com.example.healthmanagementapp.model.admin.Admin;

public class AdminAccount extends AppCompatActivity {

    String adminId;
    Admin admin;

    TextView AdminAccount_tvAdminName;
    Button AdminAccount_btnNewUser;
    Button AdminAccount_btnLogOut;
    ListView AdminAccount_listView;

    ArrayAdapter arrayAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        adminId = preference.getString("adminId",null);

        AdminAccount_tvAdminName = findViewById(R.id.AdminAccount_tvAdminName);
        AdminAccount_btnNewUser = findViewById(R.id.AdminAccount_btnNewUser);
        AdminAccount_btnLogOut = findViewById(R.id.AdminAccount_btnLogOut);
        AdminAccount_listView = findViewById(R.id.AdminAccount_listView);

        admin = databaseHelper.getAdminById(adminId);
        AdminAccount_tvAdminName.setText("Admin: " + admin.getName());

        SharedPreferences adminPreference = getSharedPreferences("admin_user",MODE_PRIVATE);

        showUsersOnListView(databaseHelper);

        AdminAccount_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor adminEditor = adminPreference.edit();
                adminEditor.clear().commit();
                User clickedUser = (User) parent.getItemAtPosition(position);
                boolean patientExists = databaseHelper.checkIfPatientExists(clickedUser.getId(),clickedUser.getPassword());
                boolean doctorExists = databaseHelper.checkIfDoctorExists(clickedUser.getId(),clickedUser.getPassword());
                boolean cashierExists = databaseHelper.checkIfCashierExists(clickedUser.getId(),clickedUser.getPassword());
                if(patientExists == true){
                    adminEditor.putString("patientId",databaseHelper.getPatientId(clickedUser.getId()));
                    adminEditor.commit();
                    startActivity(new Intent(AdminAccount.this,AdminPatient.class));
                }
                else if(doctorExists == true){
                    adminEditor.putString("doctorId",databaseHelper.getDoctorId(clickedUser.getId()));
                    adminEditor.commit();
                    startActivity(new Intent(AdminAccount.this,AdminDoctor.class));
                }
                else if(cashierExists == true){
                    adminEditor.putString("cashierId",databaseHelper.getCashierId(clickedUser.getId()));
                    adminEditor.commit();
                    startActivity(new Intent(AdminAccount.this,AdminCashier.class));
                }
                else{
                    Toast.makeText(AdminAccount.this,"System Error",Toast.LENGTH_LONG).show();
                }
            }
        });

        AdminAccount_btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminAccount.this,AdminAddUser.class));
            }
        });

        AdminAccount_btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminAccount.this, MainActivity.class));
            }
        });
    }

    private void showUsersOnListView(DatabaseHelper databaseHelper){
        arrayAdapter = new ArrayAdapter<User>(AdminAccount.this, R.layout.listview_layout,databaseHelper.getAllUsers());
        AdminAccount_listView.setAdapter(arrayAdapter);
    }
}