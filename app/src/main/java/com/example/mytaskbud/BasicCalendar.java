package com.example.mytaskbud;


import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytaskbud.Database.Planners;
import com.example.mytaskbud.Database.ViewModel;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class BasicCalendar extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    LinearLayout planLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_calendar);
        planLayout= (LinearLayout) findViewById(R.id.planLayoutCalendar);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {

        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
            if (i==dayOfMonth){

            }
        }
        return  daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(""))
        {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            planLayout.removeAllViews();
            finder(selectedDate.getYear(),selectedDate.getMonthValue(),Integer.parseInt(dayText),planLayout);

            Log.d("Date", "Year=" + selectedDate.getYear() + " Month=" + (selectedDate.getMonthValue()) + " day=" + dayText);
        }
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
                Log.d("Date", "Year=" + plan.dateYear + " Month=" + plan.dateMonth + " day=" + plan.dateDay);
            }
        });
        System.out.println("Works here");
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
        Intent replacer = new Intent(BasicCalendar.this, PlanEditor.class);
        startActivity(replacer);
    }
}