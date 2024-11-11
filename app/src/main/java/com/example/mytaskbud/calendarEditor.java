package com.example.mytaskbud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mytaskbud.Database.LocalDataBase;
import com.example.mytaskbud.Database.Planners;
import com.example.mytaskbud.Database.ViewModel;

import java.util.Calendar;

public class calendarEditor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    boolean clicker = false;
    public void finder (int year, int month, int day,LinearLayout planLayout){
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllPlanner().observe(this,planList->{
            for (Planners plan:planList){
                if(plan.dateYear==year && plan.dateMonth==month && plan.dateDay==day) {
                    System.out.println("Works");
                    add(plan.title,plan.details,plan.planid,planLayout,plan.dateDay,plan.dateMonth,plan.dateYear);
                }
            }
        });
    }
    public void add(String string1,String string2,int id,LinearLayout planLayout,int dateDay, int dateMonth, int dateYear){
        View view = getLayoutInflater().inflate(R.layout.fragment_plan_creator,null);
        TextView viewtitle = (TextView) view.findViewById(R.id.planTitle);
        TextView viewDetails = (TextView) view.findViewById(R.id.planDetails);
        TextView viewID = (TextView) view.findViewById(R.id.plannerID);
        viewID.setText(""+id);
        viewtitle.setText(string1);
        viewDetails.setText(string2);
        planLayout.addView(view);
    }
    public void expand(View view) {
        LinearLayout planLayouts = view.findViewById(R.id.planLayouts);
        TextView viewtitle = (TextView) view.findViewById(R.id.planTitle);
        TextView planDetails = view.findViewById(R.id.planDetails);
        TextView plannerID = view.findViewById(R.id.plannerID);
        Button buttonEdit = view.findViewById(R.id.buttonEdit);

        int x = (planDetails.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
        planDetails.setVisibility(x);
        x = (buttonEdit.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
        buttonEdit.setVisibility(x);
        TransitionManager.beginDelayedTransition(planLayouts,new AutoTransition());

        Button editBtn;
        editBtn = (Button) view.findViewById(R.id.buttonEdit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x = ""+ plannerID.getText();
                finders(Integer.parseInt(x));
                replaceActivity();
            }
        });
    }
    public void finders (int plannerID){
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllPlanner().observe(this,planList->{
            for (Planners plan:planList){
                if(!plan.flagger) {
                    if(plan.planid==plannerID){
                        plan.flagger=true;
                        viewModel.updatePlanners(plan);
                        break;
                    }
                }
            }
        });
    }
    public void replaceActivity(){
        Intent replacer = new Intent(calendarEditor.this, PlanEditor.class);
        startActivity(replacer);
    }


}

