package com.example.healthmanagementapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.healthmanagementapp.model.doctor.Doctor;

public class DoctorDAO extends DatabaseHelper {

    public DoctorDAO(@Nullable Context context) {
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

    public boolean checkIfDoctorExists(String doctorId, String doctorPassword){
        String query = "SELECT * FROM " + DOCTOR_TABLE + " WHERE " +
                DOCTOR_ID_COL + " = '" + doctorId + "' AND " +
                DOCTOR_PASSWORD_COL + " = '" + doctorPassword + "';";
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

    public void insertDoctor(Doctor doctor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DOCTOR_ID_COL,doctor.getId());
        values.put(DOCTOR_NAME_COL,doctor.getName());
        values.put(DOCTOR_PASSWORD_COL,doctor.getPassword());
        values.put(CLINIC_PO_COL,doctor.getPostalCode());
        values.put(LICENSE_NUMBER_COL,doctor.getLicenseNumber());
        db.insert(DOCTOR_TABLE,null,values);
        db.close();
    }

    public String getDoctorId(String doctorId){
        String query = "SELECT " + DOCTOR_ID_COL + " FROM " + DOCTOR_TABLE + " WHERE " + DOCTOR_ID_COL + " = '" + doctorId + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        String id = null;
        if(cursor.moveToFirst()){
            id = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return id;
    }

    public String getDoctorPostalById(String doctorId){
        String query = "SELECT " + CLINIC_PO_COL + " FROM " + DOCTOR_TABLE + " WHERE " + DOCTOR_ID_COL + " = '" + doctorId + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        String clinicPostalCode = null;
        if(cursor.moveToFirst()){
            clinicPostalCode = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return clinicPostalCode;
    }

    public Doctor getDoctorById(String doctorId){
        String query = "SELECT * FROM " + DOCTOR_TABLE + " WHERE " + DOCTOR_ID_COL + " = '" + doctorId + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Doctor doctor = null;
        if(cursor.moveToFirst()){
            String name = cursor.getString(1);
            String password = cursor.getString(2);
            String postalCode = cursor.getString(3);
            String licenseNumber = cursor.getString(4);
            doctor = new Doctor(doctorId,name,password,postalCode,licenseNumber);
        }
        cursor.close();
        db.close();
        return doctor;
    }

    public void updateDoctor(Doctor doctor){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DOCTOR_ID_COL,doctor.getId());
        values.put(DOCTOR_NAME_COL,doctor.getName());
        values.put(DOCTOR_PASSWORD_COL,doctor.getPassword());
        values.put(CLINIC_PO_COL,doctor.getPostalCode());
        values.put(LICENSE_NUMBER_COL,doctor.getLicenseNumber());
        db.update(DOCTOR_TABLE,values,DOCTOR_ID_COL + " = ?",new String[]{doctor.getId()});
        db.close();
    }
}
