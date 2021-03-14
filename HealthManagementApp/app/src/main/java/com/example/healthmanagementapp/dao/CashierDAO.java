package com.example.healthmanagementapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.healthmanagementapp.model.cashier.Cashier;

public class CashierDAO extends DatabaseHelper {

    public CashierDAO(@Nullable Context context) {
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

    public boolean checkIfCashierExists(String cashierId, String cashierPassword){
        String query = "SELECT * FROM " + CASHIER_TABLE + " WHERE " +
                CASHIER_ID_COL + " = '" + cashierId + "' AND " +
                CASHIER_PASSWORD_COL + " = '" + cashierPassword + "';";
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

    public void insertCashier(Cashier cashier){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CASHIER_ID_COL,cashier.getId());
        values.put(CASHIER_NAME_COL,cashier.getName());
        values.put(CASHIER_PASSWORD_COL,cashier.getPassword());
        db.insert(CASHIER_TABLE,null,values);
        db.close();
    }

    public String getCashierId(String cashierId){
        String query = "SELECT " + CASHIER_ID_COL + " FROM " + CASHIER_TABLE + " WHERE " + CASHIER_ID_COL + " = '" + cashierId + "';";
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

    public Cashier getCashierById(String cashierId){
        String query = "SELECT * FROM " + CASHIER_TABLE + " WHERE " + CASHIER_ID_COL + " = '" + cashierId + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Cashier cashier = null;
        if(cursor.moveToFirst()){
            String name = cursor.getString(1);
            String password = cursor.getString(2);
            cashier = new Cashier(cashierId,name,password);
        }
        cursor.close();
        db.close();
        return cashier;
    }

    public void updateCashier(Cashier cashier){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CASHIER_ID_COL,cashier.getId());
        values.put(CASHIER_NAME_COL,cashier.getName());
        values.put(CASHIER_PASSWORD_COL,cashier.getPassword());
        db.update(CASHIER_TABLE,values,CASHIER_ID_COL + " = ?",new String[]{cashier.getId()});
        db.close();
    }
}
