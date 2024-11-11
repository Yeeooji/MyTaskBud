package com.example.mytaskbud;

import android.graphics.Color;

import androidx.fragment.app.FragmentManager;

public class UserPlan{
    private int idPlan;
    private String titlePlan, detailPlan;
    private Boolean alarmPlan;
    private Color color;

    public UserPlan(int idPlan, String titlePlan, String detailPlan, Boolean alarmPlan, Color color) {
        this.idPlan = idPlan;
        this.titlePlan = titlePlan;
        this.detailPlan = detailPlan;
        this.alarmPlan = alarmPlan;
        this.color = color;
    }

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public String getTitlePlan() {
        return titlePlan;
    }

    public void setTitlePlan(String titlePlan) {
        this.titlePlan = titlePlan;
    }

    public String getDetailPlan() {
        return detailPlan;
    }

    public void setDetailPlan(String detailPlan) {
        this.detailPlan = detailPlan;
    }

    public Boolean getAlarmPlan() {
        return alarmPlan;
    }

    public void setAlarmPlan(Boolean alarmPlan) {
        this.alarmPlan = alarmPlan;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
