package com.example.mytaskbud.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataBaseRepository {
    LdbDao ldbDao;

    DataBaseRepository(Application application){
        LocalDataBase db = LocalDataBase.getLocalDataBase(application);
        ldbDao = db.ldbDao();
    }
    LiveData<List<Planners>> getPlanners(){
    return ldbDao.getAllPlanners();
    }




    void insert(Planners planners){
        new insertAsynch(ldbDao).execute(planners);
    }

    public static class insertAsynch extends AsyncTask<Planners,Void,Void> {
        private LdbDao taskDao;

        insertAsynch(LdbDao ldbDao){
            taskDao = ldbDao;
        }

        @Override
        protected Void doInBackground(Planners... planners){
            taskDao.insertAll(planners[0]);
            return null;
        }
    }

    void delete(Planners planners){
        new deleteAsynch(ldbDao).execute(planners);
    }

    private static class deleteAsynch extends AsyncTask<Planners,Void,Void> {
        private LdbDao taskDao;

        deleteAsynch(LdbDao ldbDao){
            taskDao = ldbDao;
        }

        @Override
        protected Void doInBackground(Planners... planners){
            taskDao.deleteAll(planners[0]);
            return null;
        }
    }

    void update(Planners planners) {
        new updateAsynch(ldbDao).execute(planners);
    }

    private static class updateAsynch extends AsyncTask<Planners, Void, Void> {
        private LdbDao taskDao;

        updateAsynch(LdbDao ldbDao){
            taskDao = ldbDao;
        }

        @Override
        protected Void doInBackground(Planners... planners) {
            taskDao.updatePlanners(planners);
            return null;
        }
    }
}

