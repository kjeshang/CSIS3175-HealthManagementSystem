package com.example.healthmanagementapp.UI.doctorUI;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.healthmanagementapp.model.doctor.Chat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DoctorOnlineHelp extends AppCompatActivity {

    String chatHistory;
    StringBuilder message = new StringBuilder("");
    String doctorID, patientID, userName;
    StringBuilder sbChatHistory = new StringBuilder("");
    EditText OnlineHelp_etYourMessage;
    TextView OnlineHelp_tvChatHistory;
    Button OnlineHelp_btnSendMessage;
    Chat conversation, finChat;
    Date today = Calendar.getInstance().getTime();
    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd - hh:mm aa");
    String messageDate = formatter.format(today);

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_online_help);

        databaseHelper = DatabaseHelper.getInstance(this);

        OnlineHelp_etYourMessage = findViewById(R.id.DoctorOnlineHelp_etYourMessage);
        OnlineHelp_btnSendMessage = findViewById(R.id.DoctorOnlineHelp_btnSendMessage);
        OnlineHelp_tvChatHistory = findViewById(R.id.DoctorOnlineHelp_tvChatHistory);
        OnlineHelp_tvChatHistory.setMovementMethod(new ScrollingMovementMethod());

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);

        patientID = preference.getString("patientId",null);                     //Getting the patient ID from login activity
        doctorID = preference.getString("doctorId",null);                       //Getting the doctor ID from login activity

        if (patientID == null) {
            patientID = preference.getString("selectedPatientId", null);         //Getting the patient ID from the list on Doctor Activity
            userName = databaseHelper.getDoctorById(doctorID).getName();
        }
        if (doctorID == null) {
            doctorID = preference.getString("selectedDoctorId", null);          //Getting the doctor ID from the Appointment list
            userName = databaseHelper.getPatientById(patientID).getName();
        }

        if(!databaseHelper.checkIfChatExists(doctorID, patientID)){
            conversation = createChat();
            databaseHelper.insertChat(conversation);
        }
        conversation = databaseHelper.getChatById(patientID, doctorID);
        chatHistory = conversation.getChat();
        sbChatHistory.append(chatHistory);
        OnlineHelp_tvChatHistory.setText(sbChatHistory);

        OnlineHelp_etYourMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton();
            }
        });

        OnlineHelp_btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setLength(0);
                message.append(
                        "\n" + userName + "\t\t\t" + messageDate +
                        "\n" + " " + OnlineHelp_etYourMessage.getText().toString());
                sbChatHistory.append(message);
                OnlineHelp_tvChatHistory.setText("");
                OnlineHelp_tvChatHistory.setText(sbChatHistory.toString());
                OnlineHelp_etYourMessage.setText("");
                Toast.makeText(DoctorOnlineHelp.this, "Message sent to the patient", Toast.LENGTH_LONG).show();
                finChat = createUpdatedChat(conversation, sbChatHistory);
                databaseHelper.updateChat(finChat);
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
    private Chat createUpdatedChat(Chat chat, StringBuilder history){
        Chat finChatHistory;
        String chatHist = history.toString();
        finChatHistory = new Chat(patientID, doctorID, chatHist);
        return finChatHistory;
    }

    private void enableButton(){
        boolean status = OnlineHelp_etYourMessage.getText().toString().length() > 0;
        OnlineHelp_btnSendMessage.setEnabled(status);
    }
}