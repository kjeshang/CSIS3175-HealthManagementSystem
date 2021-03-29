package com.example.healthmanagementapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.healthmanagementapp.model.User;
import com.example.healthmanagementapp.model.admin.Admin;
import com.example.healthmanagementapp.model.cashier.Cashier;
import com.example.healthmanagementapp.model.doctor.Doctor;
import com.example.healthmanagementapp.model.patient.Patient;

import java.util.ArrayList;
import java.util.List;

// Reference:
// https://guides.codepath.com/android/local-databases-with-sqliteopenhelper

public class DatabaseHelper extends SQLiteOpenHelper {

    // ************** ADMIN ***********************************
    // CASHIER (AdminID, AdminName, AdminPassword)
    // Primary Key = AdminID
    private static final String ADMIN_TABLE = "ADMIN";
    private static final String ADMIN_ID_COL = "AdminID";
    private static final String ADMIN_NAME_COL = "AdminName";
    private static final String ADMIN_PASSWORD_COL = "AdminPassword";
    private static final String createAdminTable = "CREATE TABLE " + ADMIN_TABLE +
            "(" +
                ADMIN_ID_COL + " TEXT PRIMARY KEY, " +
                ADMIN_NAME_COL + " TEXT, " +
                ADMIN_PASSWORD_COL + " TEXT" +
            ");";
    private static final String dropAdminTable = "DROP TABLE if exists " + ADMIN_TABLE + ";";

    // ************** PATIENT *************************************
    // PATIENT (PatientID, PatientName, PatientPassword, PatientPostalCode)
    // Primary Key = PatientID
    private static final String PATIENT_TABLE = "PATIENT";
    private static final String PATIENT_ID_COL = "PatientID";
    private static final String PATIENT_NAME_COL = "PatientName";
    private static final String PATIENT_PASSWORD_COL = "PatientPassword";
    private static final String PATIENT_POSTAL_COL = "PatientPostalCode";
    private static final String PATIENT_ALLERGIES_COL = "Allergies";
    private static final String PATIENT_DISEASES_COL = "Diseases";
    private static final String createPatientTable = "CREATE TABLE " + PATIENT_TABLE +
            "(" +
                PATIENT_ID_COL + " TEXT PRIMARY KEY, " +
                PATIENT_NAME_COL + " TEXT, " +
                PATIENT_PASSWORD_COL + " TEXT, " +
                PATIENT_POSTAL_COL + " TEXT, " +
                PATIENT_ALLERGIES_COL + " TEXT, " +
                PATIENT_DISEASES_COL + " TEXT" +
            ");";
    private static final String dropPatientTable = "DROP TABLE if exists " + PATIENT_TABLE + ";";

    // ************** DOCTOR ***********************************
    // Doctor (DoctorID, DoctorName, DoctorPassword, LicenseNumber, ClinicPostalCode)
    // Primary Key = DoctorID
    private static final String DOCTOR_TABLE = "DOCTOR";
    private static final String DOCTOR_ID_COL = "DoctorID";
    private static final String DOCTOR_NAME_COL = "DoctorName";
    private static final String DOCTOR_PASSWORD_COL = "DoctorPassword";
    private static final String DOCTOR_LICENSE_COL = "LicenseNumber";
    private static final String DOCTOR_CLINIC_COL = "ClinicPostalCode";
    private static final String createDoctorTable = "CREATE TABLE " + DOCTOR_TABLE +
            "(" +
                DOCTOR_ID_COL + " TEXT PRIMARY KEY, " +
                DOCTOR_NAME_COL + " TEXT, " +
                DOCTOR_PASSWORD_COL + " TEXT, " +
                DOCTOR_LICENSE_COL + " TEXT, " +
                DOCTOR_CLINIC_COL + " TEXT" +
            ");";
    private static final String dropDoctorTable = "DROP TABLE if exists " + DOCTOR_TABLE;

    // ************** CASHIER ***********************************
    // CASHIER (CashierID, CashierName, CashierPassword)
    // Primary Key = CashierID
    private static final String CASHIER_TABLE = "CASHIER";
    private static final String CASHIER_ID_COL = "CashierID";
    private static final String CASHIER_NAME_COL = "CashierName";
    private static final String CASHIER_PASSWORD_COL = "CashierPassword";
    private static final String createCashierTable = "CREATE TABLE " + CASHIER_TABLE +
            "(" +
                CASHIER_ID_COL + " TEXT PRIMARY KEY, " +
                CASHIER_NAME_COL + " TEXT, " +
                CASHIER_PASSWORD_COL + " TEXT" +
            ");";
    private static final String dropCashierTable = "DROP TABLE if exists " + CASHIER_TABLE;

    // ************** CALORIES ***********************************
    // Calories (CaloriesID, PatientID, FoodList, TotalCalories, HealthSuggestion, DateOfConsumption)
    // Primary Key = CaloriesID
    // Foreign Key = PatientID
    public static final String CALORIES_TABLE = "CALORIES";
    public static final String CALORIES_ID_COL = "CaloriesID";
    public static final String FOOD_LIST_COL = "FoodList";
    public static final String TOTAL_CALORIES_COL = "TotalCalories";
    public static final String HEALTH_SUGGESTION = "HealthSuggestion";
    public static final String DATE_OF_CONSUMPTION_COL = "DateOfConsumption";
    String createCaloriesTable = "CREATE TABLE " + CALORIES_TABLE + "(" +
            CALORIES_ID_COL + " TEXT PRIMARY KEY, " +
            PATIENT_ID_COL + " TEXT, " +
            FOOD_LIST_COL + " TEXT, " +
            TOTAL_CALORIES_COL + " INT, " +
            HEALTH_SUGGESTION + " TEXT, " +
            DATE_OF_CONSUMPTION_COL + " DATE, " +
            "FOREIGN KEY(" + PATIENT_ID_COL + ") REFERENCES " + PATIENT_TABLE + "(" + PATIENT_ID_COL + "));";
    public static final String dropCaloriesTable = "DROP TABLE if exists " + CALORIES_TABLE;

    // ************** PATIENT_DR **********************************
    // PATIENT-DOCTOR (PatientID, DoctorID)
    // Primary Key = (PatientID, DoctorID)
    // Foreign Keys = PatientID, DoctorID
    public static final String PATIENT_DR_TABLE = "PATIENT-DOCTOR";
    public static final String PATIENT_DR_HISTORY = "PatientDoctorConversationHistory";
    public static final String createPatient_DrTable = "CREATE TABLE " + PATIENT_DR_TABLE + "(" +
            PATIENT_ID_COL + " TEXT, " +
            DOCTOR_ID_COL + " TEXT, " +
            PATIENT_DR_HISTORY + " TEXT, " +
            "PRIMARY KEY(" + PATIENT_ID_COL + ", " + DOCTOR_ID_COL + "), " +
            "FOREIGN KEY(" + PATIENT_ID_COL + ") REFERENCES " + PATIENT_TABLE + "(" + PATIENT_ID_COL + ")" +
            "FOREIGN KEY(" + DOCTOR_ID_COL + ") REFERENCES " + DOCTOR_TABLE + "(" + DOCTOR_ID_COL + "));";
    public static final String dropPatientDrTable = "DROP TABLE if exists " + PATIENT_DR_TABLE;

    // ************** COMPLAINT ***********************************
    // COMPLAINT (ComplaintID, PatientID, DoctorID, ComplaintDescription)
    // Primary Key = ComplaintID
    // Foreign Key = (PatientID, DoctorID)
    public static final String COMPLAINT_TABLE = "COMPLAINT";
    public static final String COMPLAINT_ID_COL = "ComplaintID";
    public static final String COMPLAINT_DESCRIPTION_COL = "ComplaintDescription";
    public static final String createComplaintTable = "CREATE TABLE " + COMPLAINT_TABLE + "(" +
            COMPLAINT_ID_COL + " INT PRIMARY KEY, " +
            PATIENT_ID_COL + " TEXT, " +
            DOCTOR_ID_COL + " TEXT, " +
            COMPLAINT_DESCRIPTION_COL + " TEXT, " +
            "FOREIGN KEY(" + PATIENT_ID_COL + ", " + DOCTOR_ID_COL + ") REFERENCES " + PATIENT_DR_TABLE + "(" + PATIENT_ID_COL + ", " + DOCTOR_ID_COL + "));";
    public static final String dropComplaintTable = "DROP TABLE if exists " + COMPLAINT_TABLE;

    // ************** SOLUTION ***********************************
    // SOLUTION (PatientID, ComplaintID, DoctorID, SolutionDescription)
    // Primary Key = (PatientID, ComplaintID)
    // Foreign Key = PatientID, DoctorID, ComplaintID
    public static final String SOLUTION_TABLE = "SOLUTION";
    public static final String SOLUTION_DESCRIPTION_COL = "SolutionDescription";
    public static final String createSolutionTable = "CREATE TABLE " + SOLUTION_TABLE + "(" +
            PATIENT_ID_COL + " TEXT, " +
            COMPLAINT_ID_COL + " INT, " +
            DOCTOR_ID_COL + " TEXT, " +
            SOLUTION_DESCRIPTION_COL + " TEXT, " +
            "PRIMARY KEY(" + PATIENT_ID_COL + ", " + COMPLAINT_ID_COL + "), " +
            "FOREIGN KEY(" + PATIENT_ID_COL + ") REFERENCES " + PATIENT_TABLE + "(" + PATIENT_ID_COL + ")," +
            "FOREIGN KEY(" + COMPLAINT_ID_COL + ") REFERENCES " + COMPLAINT_TABLE + "(" + COMPLAINT_ID_COL + "), " +
            "FOREIGN KEY(" + DOCTOR_ID_COL + ") REFERENCES " + DOCTOR_TABLE + "(" + DOCTOR_ID_COL + "));";
    public static final String dropSolutionTable = "DROP TABLE if exists " + SOLUTION_TABLE;

    // ************** PAYMENT ***********************************
    // PAYMENT (CashierID, PatientID, duePayment)
    // Primary Key = (CashierID, PatientID)
    // Foreign Key = CashierID, PatientID
    public static final String PAYMENT_TABLE = "PAYMENT";
    public static final String DUE_PAYMENT_COL = "DuePayment";
    public static final String createPaymentTable = "CREATE TABLE " + PAYMENT_TABLE + "(" +
            CASHIER_ID_COL + " TEXT, " +
            PATIENT_ID_COL + " TEXT, " +
            DUE_PAYMENT_COL + " INT, " +
            "PRIMARY KEY(" + CASHIER_ID_COL + ", " + PATIENT_ID_COL + "), " +
            "FOREIGN KEY(" + CASHIER_ID_COL + ") REFERENCES " + CASHIER_TABLE + "(" + CASHIER_ID_COL + "), " +
            "FOREIGN KEY(" + PATIENT_ID_COL + ") REFERENCES " + PATIENT_TABLE + "(" + PATIENT_ID_COL + "));";
    public static final String dropPaymentTable = "DROP TABLE if exists " + PAYMENT_TABLE;

    // ------------------------------------------------------------------------------------

    private final static String DATABASE_NAME = "HealthBuddy.db";
    private final static int DATABASE_VERSION = 1;

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createAdminTable);
        db.execSQL(createPatientTable);
        db.execSQL(createDoctorTable);
        db.execSQL(createCashierTable);
        //db.execSQL(createCaloriesTable);
        db.execSQL(createPatient_DrTable);
        //db.execSQL(createComplaintTable);
        //db.execSQL(createSolutionTable);
        //db.execSQL(createPaymentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL(dropAdminTable);
            db.execSQL(dropPatientTable);
            db.execSQL(dropDoctorTable);
            db.execSQL(dropCashierTable);
            //db.execSQL(dropCaloriesTable);
            db.execSQL(dropPatientDrTable);
            //db.execSQL(dropComplaintTable);
            //db.execSQL(dropSolutionTable);
            //db.execSQL(dropPaymentTable);
            onCreate(db);
        }
    }

    // ---------------------------------------------------------------------------------

    // ********************** ADMIN ****************************************************

    public boolean checkIfAdminExists(String adminId, String adminPassword){
        String query = "SELECT * FROM " + ADMIN_TABLE + " WHERE " +
                ADMIN_ID_COL + " = '" + adminId + "' AND " +
                ADMIN_PASSWORD_COL + " = '" + adminPassword + "';";
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

    public void insertAdmin(Admin admin){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ADMIN_ID_COL,admin.getId());
        values.put(ADMIN_NAME_COL,admin.getName());
        values.put(ADMIN_PASSWORD_COL,admin.getPassword());
        db.insert(ADMIN_TABLE,null,values);
        db.close();
    }

    public String getAdminId(String adminId){
        String query = "SELECT " + ADMIN_ID_COL + " FROM " + ADMIN_TABLE + " WHERE " + ADMIN_ID_COL + " = '" + adminId + "';";
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

    public Admin getAdminById(String adminId){
        String query = "SELECT * FROM " + ADMIN_TABLE + " WHERE " + ADMIN_ID_COL + " = '" + adminId + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Admin admin = null;
        if(cursor.moveToFirst()){
            String name = cursor.getString(1);
            String password = cursor.getString(2);
            admin = new Admin(adminId,name,password);
        }
        cursor.close();
        db.close();
        return admin;
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
        values.put(PATIENT_POSTAL_COL,patient.getPostalCode());
        values.put(PATIENT_ALLERGIES_COL,patient.getAllergies());
        values.put(PATIENT_DISEASES_COL,patient.getDiseases());
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
            String allergies = cursor.getString(4);
            String diseases = cursor.getString(5);
            patient = new Patient(patientId,name,password,postalCode,allergies,diseases);
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
        values.put(PATIENT_POSTAL_COL,patient.getPostalCode());
        values.put(PATIENT_ALLERGIES_COL,patient.getAllergies());
        values.put(PATIENT_DISEASES_COL,patient.getDiseases());
        db.update(PATIENT_TABLE,values,PATIENT_ID_COL + " = ?",new String[]{patient.getId()});
        db.close();
    }

    public void deletePatient(Patient patient){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PATIENT_TABLE,PATIENT_ID_COL + " = ?",new String[]{patient.getId()});
        db.close();
    }

    // ********************** DOCTOR ***************************************************

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
        values.put(DOCTOR_CLINIC_COL,doctor.getPostalCode());
        values.put(DOCTOR_LICENSE_COL,doctor.getLicenseNumber());
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
        values.put(DOCTOR_CLINIC_COL,doctor.getPostalCode());
        values.put(DOCTOR_LICENSE_COL,doctor.getLicenseNumber());
        db.update(DOCTOR_TABLE,values,DOCTOR_ID_COL + " = ?",new String[]{doctor.getId()});
        db.close();
    }

    public void deleteDoctor(Doctor doctor){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DOCTOR_TABLE,DOCTOR_ID_COL + " = ?",new String[]{doctor.getId()});
        db.close();
    }

    // ********************** CASHIER ***************************************************

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

    public void deleteCashier(Cashier cashier){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CASHIER_TABLE,CASHIER_ID_COL + " = ?",new String[]{cashier.getId()});
    }

    // ------------------------------------------------------------------

    public List<String> fillPatientsWInquiry(String doctorID){
        List<String> list= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + PATIENT_ID_COL + " FROM " + PATIENT_DR_HISTORY + " WHERE " + DOCTOR_ID_COL + " = '" + doctorID + "';";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                list.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        return list;
    }

    public List<User> getAllUsers(){
        List<User> list = new ArrayList<>();
        String queryPatient = "SELECT * FROM " + PATIENT_TABLE + ";";
        String queryDoctor = "SELECT * FROM " + DOCTOR_TABLE + ";";
        String queryCashier = "SELECT * FROM " + CASHIER_TABLE + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(queryPatient,null);
        if(cursor.moveToFirst()){
            do{
                String patientId = cursor.getString(0);
                String patientName = cursor.getString(1);
                String patientPassword = cursor.getString(2);
                User user = new Patient(patientId,patientName,patientPassword);
                list.add(user);
            }
            while(cursor.moveToNext());
        }
        cursor = db.rawQuery(queryDoctor,null);
        if(cursor.moveToFirst()){
            do{
                String doctorId = cursor.getString(0);
                String doctorName = cursor.getString(1);
                String doctorPassword = cursor.getString(2);
                User user = new Doctor(doctorId,doctorName,doctorPassword);
                list.add(user);
            }
            while(cursor.moveToNext());
        }
        cursor = db.rawQuery(queryCashier,null);
        if(cursor.moveToFirst()){
            do{
                String cashierId = cursor.getString(0);
                String cashierName = cursor.getString(1);
                String cashierPassword = cursor.getString(2);
                User user = new Cashier(cashierId, cashierName, cashierPassword);
                list.add(user);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

//    public String getUserId(User user){
//        String queryPatient = "SELECT * FROM " + PATIENT_TABLE + " WHERE " +
//                PATIENT_ID_COL + " = '" + user.getId() + "' AND " +
//                PATIENT_PASSWORD_COL + " = '" + user.getPassword() + "';";
//        String queryDoctor = "SELECT * FROM " + DOCTOR_TABLE + " WHERE " +
//                DOCTOR_ID_COL + " = '" + user.getId() + "' AND " +
//                DOCTOR_PASSWORD_COL + " = '" + user.getPassword() + "';";
//        String queryCashier = "SELECT * FROM " + CASHIER_TABLE + " WHERE " +
//                CASHIER_ID_COL + " = '" + user.getId() + "' AND " +
//                CASHIER_PASSWORD_COL + " = '" + user.getPassword() + "';";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        cursor = db.rawQuery(queryPatient,null);
//        String id = null;
//        if(cursor.moveToFirst()){
//            id = cursor.getString(0);
//        }
//        cursor = db.rawQuery(queryDoctor,null);
//        if(cursor.moveToFirst()){
//            id = cursor.getString(0);
//        }
//        cursor = db.rawQuery(queryCashier,null);
//        if(cursor.moveToFirst()){
//            id = cursor.getString(0);
//        }
//        cursor.close();
//        db.close();
//        return id;
//    }
}
