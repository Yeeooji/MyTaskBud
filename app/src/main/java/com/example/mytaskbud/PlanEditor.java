package com.example.mytaskbud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mytaskbud.Database.PlanDate;
import com.example.mytaskbud.Database.Planners;
import com.example.mytaskbud.Database.ViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLOutput;
import java.time.LocalTime;
import java.util.Calendar;

public class PlanEditor extends AppCompatActivity {
    int x=0;
    Calendar calendar;
    int dateDay,dateMonth,dateYear,timeHour,timeMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_editor);
        TextView editorID = (TextView) findViewById(R.id.editorID);
        TextInputEditText inpuTitle = (TextInputEditText) findViewById(R.id.inputTitle);
        TextInputEditText inputNotes = (TextInputEditText) findViewById(R.id.inputNotes);
        TextInputEditText inputLocation = (TextInputEditText) findViewById(R.id.inputLocation);
        calendar = Calendar.getInstance();
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllPlanner().observe(this,planList->{
            for (Planners plan:planList){
                if(plan.flagger) {
                    editorID.setText(""+plan.planid);
                    inpuTitle.setText(""+plan.title);
                    inputNotes.setText(""+plan.details);
                    inputLocation.setText(""+plan.planLocation);
                }
            }
        });
        Button buttonSave;
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saver();
                startActivityForResult(new Intent(PlanEditor.this, MainActivity.class), CODE);
            }
        });

        Button buttonDelete;
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleter();
                startActivityForResult(new Intent(PlanEditor.this, MainActivity.class), CODE);
            }
        });
        ImageView datepicker = (ImageView) findViewById(R.id.datepicker);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }});

        ImageView timepicker = (ImageView) findViewById(R.id.timepicker);
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker();
            }});

        ImageView backarrow = (ImageView) findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(PlanEditor.this, MainActivity.class), CODE);
            }});
        System.out.println("hey");

    }



    Calendar c = Calendar.getInstance();
    LocalTime time = LocalTime.now();
    private void openDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateDay=day;
                dateMonth=month;
                dateYear=year;
            }}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    private void openTimePicker(){

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                timeHour=hour;
                timeMinute=minute;
            }}, 15, 30, false);
        timePickerDialog.show();
    }
    boolean ifCreateNew=true;
    public void saver(){

        TextInputEditText inpuTitle = (TextInputEditText) findViewById(R.id.inputTitle);
        TextInputEditText inputNotes = (TextInputEditText) findViewById(R.id.inputNotes);
        TextInputEditText inputLocation = (TextInputEditText) findViewById(R.id.inputLocation);
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllPlanner().observe(this,planList->{
            for (Planners plan:planList){
                if(plan.flagger) {
                    plan.title=""+inpuTitle.getText();
                    plan.details=""+inputNotes.getText();
                    plan.planLocation = ""+inputLocation.getText();

                    try{
                        plan.dateDay=dateDay;
                        plan.dateMonth=dateMonth+1;
                        plan.dateYear=dateYear;
                        plan.timeHour=timeHour;
                        plan.timeMinute=timeMinute;

                    }catch (Exception e){
                        plan.dateDay=calendar.get(Calendar.DAY_OF_MONTH);
                        plan.dateMonth=calendar.get(Calendar.MONTH) + 1;
                        plan.dateYear= calendar.get(Calendar.YEAR);
                        plan.timeHour=time.getHour();
                        plan.timeMinute=time.getMinute();
                    }
                    plan.completedBool=false;
                    plan.flagger=false;
                    viewModel.updatePlanners(plan);
                    ifCreateNew=false;
                }
            }

        });
        if (ifCreateNew){
            Planners plan;
            if (dateDay==0){
                plan = new Planners(""+inpuTitle.getText(),""+inputNotes.getText(),false,calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH) + 1,calendar.get(Calendar.YEAR),time.getHour(),time.getMinute(), "None", false);
            }

            else {
                plan = new Planners(""+inpuTitle.getText(),""+inputNotes.getText(),false,dateDay,dateMonth+1,dateYear,timeHour,timeMinute,""+inputLocation.getText(), false);
                System.out.println("ddd");
            }
            viewModel.insertPlanners(plan);
        }
    }
    private void deleter() {
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllPlanner().observe(this,planList->{
            for (Planners plan:planList){
                if(plan.flagger) {
                    viewModel.deletePlanners(plan);
                }
            }

        });
    }

    @Override
    public void onBackPressed()
    {
    }
    private static int CODE = 1; //declare as FIELD





}
