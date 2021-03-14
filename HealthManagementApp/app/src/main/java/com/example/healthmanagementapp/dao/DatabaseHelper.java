package com.example.healthmanagementapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.healthmanagementapp.model.patient.Disease;
import com.example.healthmanagementapp.model.patient.Patient;

public abstract class DatabaseHelper extends SQLiteOpenHelper {

    // -------------- Patient-related tables ----------------------

    // ************** PATIENT *************************************
    // PATIENT (PatientID, PatientName, PatientPassword, PostalCode)
    // Primary Key = PatientID
    public static final String PATIENT_TABLE = "PATIENT";
    public static final String PATIENT_ID_COL = "PatientID";
    public static final String PATIENT_NAME_COL = "PatientName";
    public static final String PATIENT_PASSWORD_COL = "PatientPassword";
    public static final String POSTAL_CODE_COL = "PostalCode";

    // ************** DISEASE *************************************
    // DISEASE (DiseaseID, PatientID, Allergy, DiseaseHistory)
    // Primary Key = DiseaseID
    // Foreign Key = PatientID
    public static final String DISEASE_TABLE = "DISEASE";
    public static final String DISEASE_ID_COL = "DiseaseID";
    public static final String ALLERGY_COL = "Allergy";
    public static final String DISEASE_HISTORY_COL = "DiseaseHistory";

    // ************** PATIENT_DR **********************************
    // PATIENT-DOCTOR (PatientID, DoctorID)
    // Primary Key = (PatientID, DoctorID)
    // Foreign Keys = PatientID, DoctorID
    public static final String PATIENT_DR_TABLE = "PATIENT-DOCTOR";

    // ************** COMPLAINT ***********************************
    // COMPLAINT (ComplaintID, PatientID, DoctorID, ComplaintDescription)
    // Primary Key = ComplaintID
    // Foreign Key = (PatientID, DoctorID)
    public static final String COMPLAINT_TABLE = "COMPLAINT";
    public static final String COMPLAINT_ID_COL = "ComplaintID";
    public static final String COMPLAINT_DESCRIPTION_COL = "ComplaintDescription";

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

    // -------------- Doctor-related tables ------------------------

    // ************** DOCTOR ***********************************
    // Doctor (DoctorID, DoctorName, DoctorPassword, LicenseNumber, ClinicPO)
    // Primary Key = DoctorID
    public static final String DOCTOR_TABLE = "DOCTOR";
    public static final String DOCTOR_ID_COL = "DoctorID";
    public static final String DOCTOR_NAME_COL = "DoctorName";
    public static final String DOCTOR_PASSWORD_COL = "DoctorPassword";
    public static final String LICENSE_NUMBER_COL = "LicenseNumber";
    public static final String CLINIC_PO_COL = "ClinicPO";

    // ************** SOLUTION ***********************************
    // SOLUTION (PatientID, ComplaintID, DoctorID, SolutionDescription)
    // Primary Key = (PatientID, ComplaintID)
    // Foreign Key = (PatientID, ComplaintID), DoctorID
    public static final String SOLUTION_TABLE = "SOLUTION";
    public static final String SOLUTION_DESCRIPTION_COL = "SolutionDescription";

    // -------------- Cashier-related tables ------------------------

    // ************** CASHIER ***********************************
    // CASHIER (CashierID, CashierName, CashierPassword)
    // Primary Key = CashierID
    public static final String CASHIER_TABLE = "CASHIER";
    public static final String CASHIER_ID_COL = "CashierID";
    public static final String CASHIER_NAME_COL = "CashierName";
    public static final String CASHIER_PASSWORD_COL = "CashierPassword";

    // ************** PAYMENT ***********************************
    // PAYMENT (CashierID, PatientID, duePayment)
    // Primary Key = (CashierID, PatientID)
    // Foreign Key = CashierID, PatientID
    public static final String PAYMENT_TABLE = "PAYMENT";
    public static final String DUE_PAYMENT_COL = "DuePayment";

    // -------------------------------------------------------------------

    final static String DATABASE_NAME = "HealthBuddy.db";
    final static int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // ************** PATIENT *************************************
        // PATIENT (PatientID, PatientName, PatientPassword, PostalCode)
        // Primary Key = PatientID
        String createPatientTable = "CREATE TABLE " + PATIENT_TABLE + "(" +
                PATIENT_ID_COL + " TEXT PRIMARY KEY, " +
                PATIENT_NAME_COL + " TEXT, " +
                PATIENT_PASSWORD_COL + " TEXT, " +
                POSTAL_CODE_COL + " TEXT);";
        db.execSQL(createPatientTable);
        // ************** DISEASE *************************************
        // DISEASE (DiseaseID, PatientID, Allergy, DiseaseHistory)
        // Primary Key = DiseaseID
        // Foreign Key = PatientID
        String createDiseaseTable = "CREATE TABLE " + DISEASE_TABLE + "(" +
                DISEASE_ID_COL + " INTEGER PRIMARY KEY, " +
                PATIENT_ID_COL + " TEXT, " +
                ALLERGY_COL + " TEXT, " +
                DISEASE_HISTORY_COL + " TEXT, " +
                "FOREIGN KEY(" + PATIENT_ID_COL + ") REFERENCES " + PATIENT_TABLE + "(" + PATIENT_ID_COL + "));";
        db.execSQL(createDiseaseTable);
//        // ************** CALORIES ***********************************
//        // Calories (CaloriesID, PatientID, FoodList, TotalCalories, HealthSuggestion, DateOfConsumption)
//        // Primary Key = CaloriesID
//        // Foreign Key = PatientID
//        String createCaloriesTable = "CREATE TABLE " + CALORIES_TABLE + "(" +
//                CALORIES_ID_COL + " TEXT PRIMARY KEY, " +
//                PATIENT_ID_COL + " TEXT, " +
//                FOOD_LIST_COL + " TEXT, " +
//                TOTAL_CALORIES_COL + " INT, " +
//                HEALTH_SUGGESTION + " TEXT, " +
//                DATE_OF_CONSUMPTION_COL + " DATE, " +
//                "FOREIGN KEY(" + PATIENT_ID_COL + ") REFERENCES " + PATIENT_TABLE + "(" + PATIENT_ID_COL + "));";
//        db.execSQL(createCaloriesTable);
        // ************** DOCTOR ***********************************
        // Doctor (DoctorID, DoctorName, DoctorPassword, LicenseNumber, ClinicPO)
        // Primary Key = DoctorID
        String createDoctorTable = "CREATE TABLE " + DOCTOR_TABLE + "(" +
                DOCTOR_ID_COL + " TEXT PRIMARY KEY, " +
                DOCTOR_NAME_COL + " TEXT, " +
                DOCTOR_PASSWORD_COL + " TEXT, " +
                LICENSE_NUMBER_COL + " TEXT, " +
                CLINIC_PO_COL + " TEXT);";
        db.execSQL(createDoctorTable);
//        // ************** PATIENT_DR **********************************
//        // PATIENT-DOCTOR (PatientID, DoctorID)
//        // Primary Key = (PatientID, DoctorID)
//        // Foreign Keys = PatientID, DoctorID
//        String createPatient_DrTable = "CREATE TABLE " + PATIENT_DR_TABLE + "(" +
//                PATIENT_ID_COL + " TEXT, " +
//                DOCTOR_ID_COL + " TEXT, " +
//                "PRIMARY KEY(" + PATIENT_ID_COL + ", " + DOCTOR_ID_COL + "), " +
//                "FOREIGN KEY(" + PATIENT_ID_COL + ") REFERENCES " + PATIENT_TABLE + "(" + PATIENT_ID_COL + ")" +
//                "FOREIGN KEY(" + DOCTOR_ID_COL + ") REFERENCES " + DOCTOR_TABLE + "(" + DOCTOR_ID_COL + "));";
//        db.execSQL(createPatient_DrTable);
//        // ************** COMPLAINT ***********************************
//        // COMPLAINT (ComplaintID, PatientID, DoctorID, ComplaintDescription)
//        // Primary Key = ComplaintID
//        // Foreign Key = (PatientID, DoctorID)
//        String createComplaintTable = "CREATE TABLE " + COMPLAINT_TABLE + "(" +
//                COMPLAINT_ID_COL + " INT PRIMARY KEY, " +
//                PATIENT_ID_COL + " TEXT, " +
//                DOCTOR_ID_COL + " TEXT, " +
//                COMPLAINT_DESCRIPTION_COL + " TEXT, " +
//                "FOREIGN KEY(" + PATIENT_ID_COL + ", " + DOCTOR_ID_COL + ") REFERENCES " + PATIENT_DR_TABLE + "(" + PATIENT_ID_COL + ", " + DOCTOR_ID_COL + "));";
//        db.execSQL(createComplaintTable);
//        // ************** SOLUTION ***********************************
//        // SOLUTION (PatientID, ComplaintID, DoctorID, SolutionDescription)
//        // Primary Key = (PatientID, ComplaintID)
//        // Foreign Key = PatientID, DoctorID, ComplaintID
//        String createSolutionTable = "CREATE TABLE " + SOLUTION_TABLE + "(" +
//                PATIENT_ID_COL + " TEXT, " +
//                COMPLAINT_ID_COL + " INT, " +
//                DOCTOR_ID_COL + " TEXT, " +
//                SOLUTION_DESCRIPTION_COL + " TEXT, " +
//                "PRIMARY KEY(" + PATIENT_ID_COL + ", " + COMPLAINT_ID_COL + "), " +
//                "FOREIGN KEY(" + PATIENT_ID_COL + ") REFERENCES " + PATIENT_TABLE + "(" + PATIENT_ID_COL + ")," +
//                "FOREIGN KEY(" + COMPLAINT_ID_COL + ") REFERENCES " + COMPLAINT_TABLE + "(" + COMPLAINT_ID_COL + "), " +
//                "FOREIGN KEY(" + DOCTOR_ID_COL + ") REFERENCES " + DOCTOR_TABLE + "(" + DOCTOR_ID_COL + "));";
//        db.execSQL(createSolutionTable);
//        // ************** CASHIER ***********************************
        // CASHIER (CashierID, CashierName, CashierPassword)
        // Primary Key = CashierID
        String createCashierTable = "CREATE TABLE " + CASHIER_TABLE + "(" +
                CASHIER_ID_COL + " TEXT PRIMARY KEY, " +
                CASHIER_NAME_COL + " TEXT, " +
                CASHIER_PASSWORD_COL + " TEXT);";
        db.execSQL(createCashierTable);
//        // ************** PAYMENT ***********************************
//        // PAYMENT (CashierID, PatientID, duePayment)
//        // Primary Key = (CashierID, PatientID)
//        // Foreign Key = CashierID, PatientID
//        String createPaymentTable = "CREATE TABLE " + PAYMENT_TABLE + "(" +
//                CASHIER_ID_COL + " TEXT, " +
//                PATIENT_ID_COL + " TEXT, " +
//                DUE_PAYMENT_COL + " INT, " +
//                "PRIMARY KEY(" + CASHIER_ID_COL + ", " + PATIENT_ID_COL + "), " +
//                "FOREIGN KEY(" + CASHIER_ID_COL + ") REFERENCES " + CASHIER_TABLE + "(" + CASHIER_ID_COL + "), " +
//                "FOREIGN KEY(" + PATIENT_ID_COL + ") REFERENCES " + PATIENT_TABLE + "(" + PATIENT_ID_COL + "));";
//        db.execSQL(createPaymentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if exists " + PATIENT_TABLE);
        db.execSQL("DROP TABLE if exists " + DISEASE_TABLE);
//        db.execSQL("DROP TABLE if exists " + CALORIES_TABLE);
        db.execSQL("DROP TABLE if exists " + DOCTOR_TABLE);
//        db.execSQL("DROP TABLE if exists " + PATIENT_DR_TABLE);
//        db.execSQL("DROP TABLE if exists " + COMPLAINT_TABLE);
//        db.execSQL("DROP TABLE if exists " + SOLUTION_TABLE);
        db.execSQL("DROP TABLE if exists " + CASHIER_TABLE);
//        db.execSQL("DROP TABLE if exists " + PAYMENT_TABLE);
        onCreate(db);
    }
}
