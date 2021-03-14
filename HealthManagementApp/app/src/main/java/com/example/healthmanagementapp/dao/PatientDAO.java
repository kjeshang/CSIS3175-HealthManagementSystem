package com.example.healthmanagementapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.healthmanagementapp.model.patient.Disease;
import com.example.healthmanagementapp.model.patient.Patient;

public class PatientDAO extends DatabaseHelper  {

    public PatientDAO(@Nullable Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

    // ********************** PATIENT **************************************************

    public boolean checkIfPatientExists(String patientId, String patientPassword){
        String query = "SELECT * FROM " + PATIENT_TABLE + " WHERE " +
                PATIENT_ID_COL + " = '" + patientId + "' AND " +
                PATIENT_PASSWORD_COL + " = '" + patientPassword + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        boolean status = false;
        if(cursor.moveToFirst()){
            status = true;
        }
        else{
            status = false;
        }
        cursor.close();
        db.close();
        return status;
    }

    public void insertPatient(Patient patient){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PATIENT_ID_COL,patient.getId());
        values.put(PATIENT_NAME_COL,patient.getName());
        values.put(PATIENT_PASSWORD_COL,patient.getPassword());
        values.put(POSTAL_CODE_COL,patient.getPostalCode());
        db.insert(PATIENT_TABLE,null,values);
        db.close();
    }

    public String getPatientId(String patientId){
        String query = "SELECT " + PATIENT_ID_COL + " FROM " + PATIENT_TABLE + " WHERE " + PATIENT_ID_COL + " = '" + patientId + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        String id = null;
        if (cursor.moveToFirst()){
            id = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return id;
    }

    public Patient getPatientById(String patientId){
        String query = "SELECT * FROM " + PATIENT_TABLE + " WHERE " + PATIENT_ID_COL + " = '" + patientId + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Patient patient = null;
        if(cursor.moveToFirst()){
            String name = cursor.getString(1);
            String password = cursor.getString(2);
            String postalCode = cursor.getString(3);
            patient = new Patient(patientId,name,password,postalCode);
        }
        cursor.close();
        db.close();
        return patient;
    }

    public void updatePatient(Patient patient){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PATIENT_ID_COL,patient.getId());
        values.put(PATIENT_NAME_COL,patient.getName());
        values.put(PATIENT_PASSWORD_COL,patient.getPassword());
        values.put(POSTAL_CODE_COL,patient.getPostalCode());
        db.update(PATIENT_TABLE,values,PATIENT_ID_COL + " = ?",new String[]{patient.getId()});
        db.close();
    }

    // ********************** DISEASE **************************************************

    public void insertDisease(Disease disease){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DISEASE_ID_COL,disease.getId());
        values.put(PATIENT_ID_COL,disease.getPatientId());
        values.put(ALLERGY_COL,disease.getAllergy());
        values.put(DISEASE_HISTORY_COL,disease.getDiseaseInfo());
        db.insert(DISEASE_TABLE,null,values);
        db.close();
    }

    public Disease getDiseaseByPatientId(String patientId){
        String query = "SELECT * FROM " + DISEASE_TABLE + " WHERE " + PATIENT_ID_COL + " = '" + patientId + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Disease disease = null;
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            String allergies = cursor.getString(2);
            String diseases = cursor.getString(3);
            disease = new Disease(id,patientId,allergies,diseases);
        }
        cursor.close();
        db.close();
        return disease;
    }

    public void updateDisease(Disease disease){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DISEASE_ID_COL,disease.getId());
        values.put(PATIENT_ID_COL,disease.getPatientId());
        values.put(ALLERGY_COL,disease.getAllergy());
        values.put(DISEASE_HISTORY_COL,disease.getDiseaseInfo());
        db.update(DISEASE_TABLE,values,PATIENT_ID_COL + " = ?",new String[]{disease.getPatientId()});
        db.close();
    }
}
