package com.example.mytaskbud.Database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    DataBaseRepository repository;
    LiveData<List<Planners>> plannerList;
    public ViewModel(Application application){
        super(application);
        repository = new DataBaseRepository(application);
        plannerList = repository.getPlanners();
    }
    public LiveData<List<Planners>> getAllPlanner(){
        return plannerList;
    }

    public void insertPlanners(Planners planners){
        repository.insert(planners);
    }
    public void deletePlanners(Planners planners){
        repository.delete(planners);
    }
    public void updatePlanners(Planners planners){
        repository.update(planners);
    }
}
