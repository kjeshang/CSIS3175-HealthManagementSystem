package com.example.healthmanagementapp.UI.patientUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.cashier.Payment;
import com.example.healthmanagementapp.model.doctor.Chat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PatientOnlineHelp extends AppCompatActivity {
    String chatHistory, doctorID, patientID, CashierID, userName;
    StringBuilder message = new StringBuilder("");
    StringBuilder sbChatHistory = new StringBuilder("");
    EditText PatientOnlineHelp_etYourMessage;
    TextView PatientOnlineHelp_tvChatHistory, PatientOnlineHelp_tvCost;
    Button PatientOnlineHelp_btnSendMessage,PatientOnlineHelp_btnPayment;
    SwitchCompat PatientOnlineHelp_swtMSP;
    Chat conversation, finChat;
    int value, newValue;
    Payment payment, finValue;
    Date today = Calendar.getInstance().getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd - hh:mm aa");
    String messageDate = formatter.format(today);

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_online_help);

        databaseHelper = DatabaseHelper.getInstance(this);

        PatientOnlineHelp_etYourMessage = findViewById(R.id.PatientOnlineHelp_etYourMessage);
        PatientOnlineHelp_btnSendMessage = findViewById(R.id.PatientOnlineHelp_btnSendMessage);
        PatientOnlineHelp_btnPayment = findViewById(R.id.PatientOnlineHelp_btnPayment);
        PatientOnlineHelp_swtMSP = findViewById(R.id.PatientOnlineHelp_swtMSP);
        PatientOnlineHelp_tvCost = findViewById(R.id.PatientOnlineHelp_tvCost);
        PatientOnlineHelp_tvChatHistory = findViewById(R.id.PatientOnlineHelp_tvChatHistory);
        PatientOnlineHelp_tvChatHistory.setMovementMethod(new ScrollingMovementMethod());

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);

        patientID = preference.getString("patientId",null);                     //Getting the patient ID from login activity

        doctorID = preference.getString("selectedDoctorId", null);          //Getting the doctor ID from the Appointment list
        userName = databaseHelper.getPatientById(patientID).getName();


        // ----------------------------------- Checking the database for payment record --------------------------------------
        if(!databaseHelper.checkIfPaymentExists(patientID)){
            payment = createPayment();
            databaseHelper.insertPayment(payment);
        }
        // ----------------------------------- Getting the payment record from the database and passing to the screen --------
        payment = databaseHelper.getPaymentById(patientID);
        value = payment.getValue();
        PatientOnlineHelp_tvCost.setText("CAD " + String.valueOf(value) + ".00");
        //  ----------------------------------- Paying the due value ---------------------------------------------------------
        PatientOnlineHelp_tvCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enablePayButton();
            }
        });
        PatientOnlineHelp_btnPayment.setEnabled(!PatientOnlineHelp_tvCost.getText().toString().equals("CAD 0.00"));
        PatientOnlineHelp_btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = 0;
                finValue = createUpdatedPayment(value);
                databaseHelper.updatePayment(finValue);
                PatientOnlineHelp_tvCost.setText("CAD " + String.valueOf(finValue.getValue()) + ".00");
                Toast.makeText(PatientOnlineHelp.this,"Due balance paid. Thanks!",Toast.LENGTH_LONG).show();
            }
        });
        // ----------------------------------- Checking the database for chat record ----------------------------------------
        if(!databaseHelper.checkIfChatExists(doctorID, patientID)){
            conversation = createChat();
            databaseHelper.insertChat(conversation);
        }
        // ----------------------------------- Getting the chat record from the database and passing to the screen ----------
        conversation = databaseHelper.getChatById(patientID, doctorID);
        chatHistory = conversation.getChat();
        sbChatHistory.append(chatHistory);
        PatientOnlineHelp_tvChatHistory.setText(sbChatHistory);
        //  ----------------------------------- Sending the message ----------------------------------------------------------
        PatientOnlineHelp_etYourMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableSendButton();
            }
        });

        PatientOnlineHelp_btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setLength(0);
                message.append(
                        "\n" + userName + "\t\t\t" + messageDate +
                                "\n" + " " + PatientOnlineHelp_etYourMessage.getText().toString());
                sbChatHistory.append(message);
                PatientOnlineHelp_tvChatHistory.setText("");
                PatientOnlineHelp_tvChatHistory.setText(sbChatHistory.toString());
                PatientOnlineHelp_etYourMessage.setText("");
                Toast.makeText(PatientOnlineHelp.this, "Message sent to the patient", Toast.LENGTH_LONG).show();
                finChat = createUpdatedChat(sbChatHistory);
                databaseHelper.updateChat(finChat);
                if(!PatientOnlineHelp_swtMSP.isChecked()){
                    payment = databaseHelper.getPaymentById(patientID);
                    value = payment.getValue();
                    newValue = addInquiryValue(value);
                    finValue = createUpdatedPayment(newValue);
                    databaseHelper.updatePayment(finValue);
                    PatientOnlineHelp_tvCost.setText("CAD " + String.valueOf(finValue.getValue()) + ".00");
                    Toast.makeText(PatientOnlineHelp.this,"Due value updated",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Chat createChat(){
        Chat conversation;
        String patID = patientID;
        String docID = doctorID;
        String chat = "No history before this point\n";
        conversation = new Chat(patID, docID, chat);
        return conversation;
    }
    private Chat createUpdatedChat(StringBuilder history){
        Chat finChatHistory;
        String chatHist = history.toString();
        finChatHistory = new Chat(patientID, doctorID, chatHist);
        return finChatHistory;
    }
    private void enableSendButton(){
        boolean status = PatientOnlineHelp_etYourMessage.getText().toString().length() > 0;
        PatientOnlineHelp_btnSendMessage.setEnabled(status);
    }
    private Payment createPayment(){
        Payment payment;
        String patID = patientID;
        int value = 0;
        payment = new Payment(patID, value);
        return payment;
    }
    private Payment createUpdatedPayment(int value){
        Payment finValue;
        finValue = new Payment(patientID, value);
        return finValue;
    }
     private int addInquiryValue(int value){
       value += 50;
       return value;
     };
    private void enablePayButton(){
        boolean status = !PatientOnlineHelp_tvCost.getText().toString().equals("CAD 0.00");
        PatientOnlineHelp_btnPayment.setEnabled(status);
    }
}