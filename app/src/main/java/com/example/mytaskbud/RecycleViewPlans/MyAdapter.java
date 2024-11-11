package com.example.mytaskbud.RecycleViewPlans;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytaskbud.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<PlanList> planArrayList;
    public MyAdapter(ArrayList<PlanList> newArrayList){

        this.planArrayList=newArrayList;
    }
    public MyAdapter(Context context, ArrayList<PlanList> newArrayList){
        this.context = context;
        this.planArrayList=newArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_plan_creator,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        PlanList planList = planArrayList.get(position);
        holder.planTitle.setText(planList.planTitle);
        holder.planDetails.setText(planList.planDetails);
        holder.planDate.setText(planList.planDate);
        holder.plannerID.setText(planList.plannerID);
        holder.planTime.setText(planList.planTime);
        holder.planLocation.setText(planList.planLocation);
    }

    @Override
    public int getItemCount() {
        return planArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView planTitle;
        TextView planDetails;
        TextView planDate;
        TextView plannerID;

        TextView planTime;
        TextView planLocation;

        Boolean completePlan;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            planTitle =  itemView.findViewById(R.id.planTitle);
            planDetails = itemView.findViewById(R.id.planDetails);
            planDate = itemView.findViewById(R.id.plandate);
            plannerID = itemView.findViewById(R.id.plannerID);
            planTime = itemView.findViewById(R.id.planTime);
            planLocation = itemView.findViewById(R.id.planLocation);
        }
    }
}
