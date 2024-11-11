package com.example.mytaskbud.RecycleViewPlans;

import android.widget.TextView;

public class PlanList {
    String planTitle;
    String planDetails;
    String planDate;
    String plannerID;
    String planTime;
    String planLocation;
    Boolean completePlan;

    public PlanList(String planTitle, String planDetails, String planDate, String plannerID, String planTime, String planLocation,Boolean completePlan) {
        this.planTitle = planTitle;
        this.planDetails = planDetails;
        this.planDate = planDate;
        this.plannerID = plannerID;
        this.planTime = planTime;
        this.planLocation=planLocation;
        this.completePlan=completePlan;
    }
}
