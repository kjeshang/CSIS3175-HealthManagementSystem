package com.example.healthmanagementapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.doctor.Chat;

public class OnlineHelp extends AppCompatActivity {

    String chatHistory;
    StringBuilder message;
    String doctorID, patientID, doctorName;
    StringBuilder sbChatHistory;
    EditText OnlineHelp_etYourMessage;
    TextView OnlineHelp_tvChatHistory;
    Button OnlineHelp_btnSendMessage;
    Chat conversation, finChat;

    DatabaseHelper databaseHelper;

    //OnlineHelpListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_help);

        databaseHelper = DatabaseHelper.getInstance(this);

        doctorID = getIntent().getStringExtra("doctorID");
        patientID = getIntent().getStringExtra("patientID");
        doctorName = databaseHelper.getDoctorById(doctorID).getName();

        OnlineHelp_etYourMessage = findViewById(R.id.OnlineHelp_etYourMessage);
        OnlineHelp_btnSendMessage = findViewById(R.id.OnlineHelp_btnSendMessage);

        if(!databaseHelper.checkIfChatExists(doctorID, patientID)){
            conversation = createChat();
            databaseHelper.insertChat(conversation);
        }
        conversation = databaseHelper.getChatById(patientID, doctorID);
        chatHistory = conversation.getChat();
        sbChatHistory.append(chatHistory);
        OnlineHelp_tvChatHistory.setText(sbChatHistory.toString());


//        if (OnlineHelp_tvYourMessage.equals(""))
//            do{
//                OnlineHelp_btnSendMessage.setClickable(false);
//            }while (OnlineHelp_tvYourMessage.equals(""));
//        else
//            OnlineHelp_btnSendMessage.setClickable(true);

        OnlineHelp_btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.append(
                        "\n" + doctorName +
                        "\n" + " " + OnlineHelp_etYourMessage.getText().toString());
                sbChatHistory.append(message);
                OnlineHelp_tvChatHistory.setText(sbChatHistory.toString());
                OnlineHelp_etYourMessage.setText("");
                Toast.makeText(OnlineHelp.this, "Message sent to the patient", Toast.LENGTH_LONG).show();
                finChat = createUpdatedChat(conversation, sbChatHistory);
                databaseHelper.updateChat(conversation);
            }
        });
    }

    private Chat createChat(){
        Chat conversation = null;
        String patID = patientID;
        String docID = doctorID;
        String chat = "No history before this point\n";
        conversation = new Chat(patID, docID, chat);
        return conversation;
    }
    private Chat createUpdatedChat(Chat chat, StringBuilder history){
        Chat finChatHistory = null;
        String patID = chat.getPatientID();
        String docID = chat.getDoctorID();
        String chatHist = history.toString();
        finChatHistory = new Chat(patID, docID, chatHist);
        return finChatHistory;
    }
}