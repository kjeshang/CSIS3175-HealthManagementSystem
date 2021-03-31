package com.example.healthmanagementapp.model.patient;

import com.example.healthmanagementapp.dao.DatabaseHelper;

import java.util.Date;

public class Calories {

    String PatientId;
    String FoodList;
    int TotalCalories;
    String HealthSuggestion;
    String DateOfConsumption;

    public Calories (String PatientId, String FoodList, int TotalCalories, String HealthSuggestion, String DateOfConsumption) {
        setPatientId(PatientId);
        setFoodList(FoodList);
        setTotalCalories(TotalCalories);
        setHealthSuggestion(HealthSuggestion);
        setDateOfConsumption(DateOfConsumption);
    }

    public String getPatientId() {return PatientId;}
    public void setPatientId(String patientId) {this.PatientId = patientId;}

    public String getFoodList() {return FoodList;}
    public void setFoodList(String foodList) {this.FoodList = foodList;}

    public int getTotalCalories() {return TotalCalories;}
    public void setTotalCalories(int totalCalories) {this.TotalCalories = totalCalories;}

    public String getHealthSuggestion() {return HealthSuggestion;}
    public void setHealthSuggestion(String healthSuggestion) {this.HealthSuggestion = healthSuggestion;}

    public String getDateOfConsumption() {return DateOfConsumption;}
    public void setDateOfConsumption(String dateOfConsumption) {this.DateOfConsumption = dateOfConsumption;}


    public String display(){
        return "*** Food List Entered ***" +
                "PatientID: " + getPatientId() + "\n" +
                "Food List: " + getFoodList() + "\n" +
                "Total Calories: " + getTotalCalories() +
                "Health Suggestion: " + getHealthSuggestion() +
                "Date Of Consumption: " + getDateOfConsumption();
    }

}
