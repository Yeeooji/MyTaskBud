package com.example.mytaskbud.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Planners {

    @PrimaryKey(autoGenerate = true)
    public int planid;

    @ColumnInfo(name = "plan_title")
    public String title;
    @ColumnInfo(name = "plan_details")
    public String details;

    @ColumnInfo(name = "flagger")
    public boolean flagger;
    @ColumnInfo(name = "DateDay")
    public int dateDay;
    @ColumnInfo(name = "DateMonth")
    public int dateMonth;
    @ColumnInfo(name = "DateYear")
    public int dateYear;
    @ColumnInfo(name = "TimeHour")
    public int timeHour;
    @ColumnInfo(name = "TimeMinute")
    public int timeMinute;
    @ColumnInfo(name = "PlannerLocation")
    public String planLocation;
    @ColumnInfo(name = "CompletedBool")
    public boolean completedBool;

    public Planners(String title, String details, boolean flagger, int dateDay, int dateMonth, int dateYear, int timeHour, int timeMinute, String planLocation, boolean completedBool) {
        this.title = title;
        this.details = details;
        this.flagger = flagger;
        this.dateDay = dateDay;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.planLocation=planLocation;
        this.completedBool=completedBool;
    }
}
