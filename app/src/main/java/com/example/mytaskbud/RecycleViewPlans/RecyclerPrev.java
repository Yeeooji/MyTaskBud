package com.example.mytaskbud.RecycleViewPlans;

import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mytaskbud.Database.LocalDataBase;
import com.example.mytaskbud.Database.Planners;
import com.example.mytaskbud.Database.ViewModel;
import com.example.mytaskbud.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecycleViewPlans#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerPrev extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<PlanList> planArrayList;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    public RecyclerPrev() {
    }


    public static RecycleViewPlans newInstance(String param1, String param2) {
        RecycleViewPlans fragment = new RecycleViewPlans();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recycle_view_plans, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //dataInitialize();
        planArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        MyAdapter myAdapter = new MyAdapter(getContext(),planArrayList);
        recyclerView.setAdapter(myAdapter);
        //dataInitialize();
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllPlanner().observe(getViewLifecycleOwner(),planList->{
            if (!planList.isEmpty()){
                for (Planners plan:planList){
                    String inputDateStr = plan.dateYear+"-"+plan.dateMonth+"-"+plan.dateDay;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date inputDate;
                    try {
                        inputDate = dateFormat.parse(inputDateStr);
                    } catch (ParseException e) {
                        System.err.println("Invalid date format");
                        return;
                    }
                    Date currentDate = Calendar.getInstance().getTime();
                    int compare = inputDate.compareTo(currentDate);
                    LocalDateTime now = LocalDateTime.now();
                    if (plan.dateDay<now.getDayOfMonth()) {
                        PlanList planner = new PlanList(plan.title, plan.details, plan.dateDay + "-" + convertMonthIntToString(plan.dateMonth) + "-" + plan.dateYear, "" + plan.planid, plan.timeHour + ":" + plan.timeMinute, "" + plan.planLocation, false);
                        planArrayList.add(planner);
                        System.out.println("past");
                    }

                }
            }
            myAdapter.notifyDataSetChanged();
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //planArrayList.remove(viewHolder.getAdapterPosition());
                swipedelete(planArrayList.remove(viewHolder.getAdapterPosition()).plannerID);
                myAdapter.notifyDataSetChanged();

            }


        }).attachToRecyclerView(recyclerView);


    }

    public void swipedelete(String id) {

        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllPlanner().observe(getViewLifecycleOwner(),planList->{
            if (!planList.isEmpty()){
                for (Planners plan:planList){
                    if(id.equals(""+plan.planid)) {
                        viewModel.deletePlanners(plan);
                        break;
                    }
                }
            }
        });
    }
    public String convertMonthIntToString(int monthInt) {
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[monthInt - 1];
    }
}