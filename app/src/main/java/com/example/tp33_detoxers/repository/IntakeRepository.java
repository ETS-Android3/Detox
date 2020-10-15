package com.example.tp33_detoxers.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tp33_detoxers.dao.IntakeDao;
import com.example.tp33_detoxers.database.IntakeDatabase;
import com.example.tp33_detoxers.model.IntakeProduct;

import java.util.List;

public class IntakeRepository {
    private IntakeDao dao;
    private LiveData<List<IntakeProduct>> allIntakeProduct;
    private IntakeProduct intakeProduct;
    private static final String TAG = "DocSnippets";

    public IntakeRepository(Application application){
        IntakeDatabase db = IntakeDatabase.getInstance(application);
        dao = db.intakeDao();
    }

    public LiveData<List<IntakeProduct>> getAllIntakeProducts(){
        allIntakeProduct = dao.getAll();
        return allIntakeProduct;
    }

    public void insert(final IntakeProduct intakeProduct){
        IntakeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(intakeProduct);
            }
        });
    }

    public void deleteAll() {
        IntakeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }

    public void delete(final IntakeProduct intakeProduct){
        IntakeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(intakeProduct);
            }
        });
    }

    public void insertAll(final IntakeProduct intakeProduct) {
        IntakeDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(intakeProduct);
            }
        });
    }

    public IntakeProduct findByProductId(final String productId){
        IntakeProduct runIntakeProduct = dao.findByProductId(productId);
        setIntakeProduct(runIntakeProduct);
        return intakeProduct;
    }

    public void setIntakeProduct(IntakeProduct intakeProduct){
        this.intakeProduct = intakeProduct;
    }


}
