package com.example.healthmanagementapp.UI.patientUI;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.CalendarView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.healthmanagementapp.R;
import com.example.healthmanagementapp.UI.MainActivity;
import com.example.healthmanagementapp.UI.doctorUI.DoctorOnlineHelp;
import com.example.healthmanagementapp.dao.DatabaseHelper;
import com.example.healthmanagementapp.model.doctor.Doctor;
import com.example.healthmanagementapp.model.patient.Appointment;
import com.example.healthmanagementapp.model.patient.Patient;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PatientAppointment extends AppCompatActivity {

    int clicked = 0;
    String patientId;
    String doctorId;
    Patient patient;
    Doctor doctor;
    String date;
    String time;
    String dateTime;
    Appointment appointment;

    TextView PatientAppointment_tvDoctorName;
    TextView PatientAppointment_tvPatientName;
    CalendarView PatientAppointment_calendarView;
    TimePicker PatientAppointment_timePicker;
    Button PatientAppointment_btnBook;
    Button PatientAppointment_btnChat;

    DatabaseHelper databaseHelper;
    String TAG = "Appointment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_appointment);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        patientId = preference.getString("patientId",null);
        doctorId = preference.getString("selectedDoctorId",null);

        PatientAppointment_tvDoctorName = findViewById(R.id.PatientAppointment_tvDoctorDetails);
        PatientAppointment_tvPatientName = findViewById(R.id.PatientAppointment_tvPatientName);
        PatientAppointment_calendarView = findViewById(R.id.PatientAppointment_calendarView);
        PatientAppointment_timePicker = findViewById(R.id.PatientAppointment_timePicker);
        PatientAppointment_btnBook = findViewById(R.id.PatientAppointment_btnBook);
        PatientAppointment_btnChat = findViewById(R.id.PatientAppointment_btnChat);

        doctor = databaseHelper.getDoctorById(doctorId);
        String doctorName = doctor.getName();
        String doctorClinic = doctor.getPostalCode();
        patient = databaseHelper.getPatientById(patientId);
        String patientName = patient.getName();
        String patientPostalCode = patient.getPostalCode();

        PatientAppointment_tvDoctorName.setText("Dr. " + doctorName + ", Clinic Postal Code: " + doctorClinic);
        PatientAppointment_tvPatientName.setText("Patient: " + patientName + ", " + "Postal Code: " + patientPostalCode);
        PatientAppointment_timePicker.setIs24HourView(false);

        Calendar calendar = Calendar.getInstance();
        PatientAppointment_calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                view.setDate(calendar.getTimeInMillis());
                date = formatDate(view.getDate());
                clicked = 1;
            }
        });

        PatientAppointment_btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientAppointment.this, PatientOnlineHelp.class));
            }
        });

        PatientAppointment_btnBook.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(clicked == 0){
                    Toast.makeText(PatientAppointment.this,"Please select an appointment date",Toast.LENGTH_SHORT).show();
                }
                else{
                    time = formatTime();
                    dateTime = date + " " + time;
                    boolean validTime = checkTime(PatientAppointment_timePicker.getHour());
                    if(validTime == false){
                        Toast.makeText(PatientAppointment.this, "Doctors do not hold appointments at this time (" + dateTime + ").", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        boolean appointmentExists = databaseHelper.checkIfAppointmentExists(doctorId,dateTime);
                        Log.d(TAG,"Appointment checked");
                        if(appointmentExists == true){
                            Toast.makeText(PatientAppointment.this, "Appointment on " + dateTime + " not available.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            appointment = new Appointment(doctorId,dateTime,patientId);
                            databaseHelper.insertAppointment(appointment);
                            Log.d(TAG,"Appointment inserted");
                            Toast.makeText(PatientAppointment.this, appointment.display() + "\n\nAppointment confirmation & details sent to email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PatientAppointment.this, PatientAccount.class));
                        }
                    }
                }
            }
        });
    }

    private String formatDate(long calendarDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date(calendarDate);
        return dateFormat.format(date);
    }

    private boolean checkTime(int hour){
        if(hour < 6 || hour > 18){
            return false;
        }
        else{
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private String formatTime(){
        int hour = PatientAppointment_timePicker.getHour();
        int minute = PatientAppointment_timePicker.getMinute();
        String sHour = "";
        String sMinute = "";
        if(hour < 10){
            sHour = "0" + hour;
        }
        else{
            sHour = "" + hour;
        }
        if(minute < 10){
            sMinute = "0" + minute;
        }
        else{
            sMinute = "" + minute;
        }
        String time = sHour + ":" + sMinute + ":00";
        return time;
    }
}