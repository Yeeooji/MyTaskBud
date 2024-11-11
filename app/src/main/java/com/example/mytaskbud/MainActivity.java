package com.example.mytaskbud;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.animation.LayoutTransition;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mytaskbud.Database.LocalDataBase;
import com.example.mytaskbud.Database.Planners;
import com.example.mytaskbud.Database.ViewModel;
import com.example.mytaskbud.RecycleViewPlans.MyAdapter;
import com.example.mytaskbud.RecycleViewPlans.PlanList;
import com.example.mytaskbud.RecycleViewPlans.RecycleViewPlans;
import com.example.mytaskbud.RecycleViewPlans.RecyclerComplete;
import com.example.mytaskbud.RecycleViewPlans.RecyclerFuture;
import com.example.mytaskbud.RecycleViewPlans.RecyclerPrev;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Calendar calendar = Calendar.getInstance();
    boolean first = true;
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    boolean remover = false;
    RecycleViewPlans recycleViewPlans;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addbtn;

        LinearLayout planLayoutToday= (LinearLayout) findViewById(R.id.planLayoutToday);
        LinearLayout planLayoutPrevious= (LinearLayout) findViewById(R.id.planLayoutPrevious);
        LinearLayout planLayoutFuture= (LinearLayout) findViewById(R.id.planLayoutFuture);
        addbtn = (Button) findViewById(R.id.button3);
        addbtn.setOnClickListener(new View.OnClickListener() { // adding planner!!
            @Override
            public void onClick(View view) {
                replaceActivity();
            }
        });

        LinearLayout calendarNavi = (LinearLayout) findViewById(R.id.calendarNavi);
        calendarNavi.setOnClickListener(new View.OnClickListener() { // adding planner!!
            @Override
            public void onClick(View view) {
                calendarActivity();
            }
        });
        LinearLayout sectoNavi = (LinearLayout) findViewById(R.id.sectoNavi);
        sectoNavi.setOnClickListener(new View.OnClickListener() { // adding planner!!
            @Override
            public void onClick(View view) {
                sectoActivity();
            }
        });
        LinearLayout menuNavi = (LinearLayout) findViewById(R.id.MenuNavi);
        menuNavi.setOnClickListener(new View.OnClickListener() { // adding planner!!
            @Override
            public void onClick(View view) {
                menuActivity();
            }
        });
        LinearLayout planLayoutTodaySetter= (LinearLayout) findViewById(R.id.planLayoutTodaySetter);
        planLayoutTodaySetter.setOnClickListener(new View.OnClickListener() { // adding planner!!
            @Override
            public void onClick(View view) {
                planLayoutFuture.setVisibility(View.GONE);
                planLayoutPrevious.setVisibility(View.GONE);
                int x = (planLayoutToday.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
                planLayoutToday.setVisibility(x);
                ImageView greenslab = (ImageView) findViewById(R.id.todayGreen);
                int y = (greenslab.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
                greenslab.setVisibility(y);
                replaceFragment(new RecycleViewPlans());
            }
        });
        LinearLayout planLayoutPreviousSetter= (LinearLayout) findViewById(R.id.planLayoutPreviousSetter);
        planLayoutPreviousSetter.setOnClickListener(new View.OnClickListener() { // adding planner!!
            @Override
            public void onClick(View view) {
                planLayoutFuture.setVisibility(View.GONE);
                planLayoutToday.setVisibility(View.GONE);
                int x = (planLayoutPrevious.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
                planLayoutPrevious.setVisibility(x);
                ImageView greenslab = (ImageView) findViewById(R.id.previousGreen);
                int y = (greenslab.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
                greenslab.setVisibility(y);
                replaceFragmentPrev(new RecyclerPrev());
            }
        });

        LinearLayout planLayoutFutureSetter= (LinearLayout) findViewById(R.id.planLayoutFutureSetter);
        planLayoutFutureSetter.setOnClickListener(new View.OnClickListener() { // adding planner!!
            @Override
            public void onClick(View view) {
                planLayoutPrevious.setVisibility(View.GONE);
                planLayoutToday.setVisibility(View.GONE);
                int x = (planLayoutFuture.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
                planLayoutFuture.setVisibility(x);
                ImageView greenslab = (ImageView) findViewById(R.id.futureGreen);
                int y = (greenslab.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
                greenslab.setVisibility(y);
                replaceFragmentFuture(new RecyclerFuture());
            }
        });
        LinearLayout planLayoutCompleteSetter= (LinearLayout) findViewById(R.id.planLayoutComSetter);
        planLayoutCompleteSetter.setOnClickListener(new View.OnClickListener() { // adding planner!!
            @Override
            public void onClick(View view) {
                planLayoutPrevious.setVisibility(View.GONE);
                planLayoutToday.setVisibility(View.GONE);
                int x = (planLayoutFuture.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
                planLayoutFuture.setVisibility(x);
                ImageView greenslab = (ImageView) findViewById(R.id.completedGreen);
                int y = (greenslab.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
                greenslab.setVisibility(y);
                replaceFragment(new RecyclerComplete());
            }
        });

    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.planLayoutToday,fragment);
        fragmentTransaction.commit();
    }
    private void replaceFragmentPrev(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.planLayoutPrevious,fragment);
        fragmentTransaction.commit();
    }
    private void replaceFragmentFuture(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.planLayoutFuture,fragment);
        fragmentTransaction.commit();
    }
    int i=0;
    public void expand(View view) {
        LinearLayout planLayouts = view.findViewById(R.id.planLayouts);
        TextView viewtitle = (TextView) view.findViewById(R.id.planTitle);
        TextView planDetails = view.findViewById(R.id.planDetails);
        TextView plannerID = view.findViewById(R.id.plannerID);
        CheckBox completeCheck = (CheckBox) view.findViewById(R.id.checkBox);
        completeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    String x = ""+ plannerID.getText();
                    finder(Integer.parseInt(x));
                    checker(Integer.parseInt(x));
                } else {
                    String x = ""+ plannerID.getText();
                    finder(Integer.parseInt(x));
                    unchecker(Integer.parseInt(x));
                }
            }
        });
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
                finder(Integer.parseInt(x));
                replaceActivity();
            }
        });
    }
    public void finder (int plannerID){
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
    public void checker (int plannerID){
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllPlanner().observe(this,planList->{
            for (Planners plan:planList){
                if(!plan.flagger) {
                    if(plan.planid==plannerID){
                        plan.completedBool=true;
                        viewModel.updatePlanners(plan);
                        break;
                    }
                }
            }
        });
    }
    public void unchecker (int plannerID){
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllPlanner().observe(this,planList->{
            for (Planners plan:planList){
                if(!plan.flagger) {
                    if(plan.planid==plannerID){
                        plan.completedBool=false;
                        viewModel.updatePlanners(plan);
                        break;
                    }
                }
            }
        });
    }
    public void replaceActivity(){
        Intent replacer = new Intent(MainActivity.this, PlanEditor.class);
        startActivity(replacer);
    }
    public void calendarActivity(){
        Intent replacer = new Intent(MainActivity.this, BasicCalendar.class);
        startActivity(replacer);
    }
    public void sectoActivity(){
        Intent replacer = new Intent(MainActivity.this, calendarEditor.class);
        startActivity(replacer);
    }
    public void menuActivity(){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();

    }
    @Override
    public void onBackPressed() {

    }
    public void listupdater(){
        ArrayList<PlanList> planArrayList = new ArrayList<>();
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllPlanner().observe(this,planList->{
            for (Planners plan:planList){
                PlanList planner = new PlanList(plan.title,plan.details,plan.dateDay+"-"+convertMonthIntToString(plan.dateMonth)+"-"+ plan.dateYear,""+plan.planid, plan.timeHour+":"+ plan.timeMinute,""+plan.planLocation,false);
                planArrayList.add(planner);
            }
        });
    }
    public String convertMonthIntToString(int monthInt) {
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[monthInt - 1];
    }

}
