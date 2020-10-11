package com.example.tp33_detoxers.viewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tp33_detoxers.model.IntakeProduct;
import com.example.tp33_detoxers.repository.IntakeRepository;

import java.util.List;

public class IntakeViewModel extends ViewModel {
    private IntakeRepository iRepository;
    private MutableLiveData<List<IntakeProduct>> allIntakeProduct;

    public IntakeViewModel() {
        allIntakeProduct = new MutableLiveData<>();
    }

    public void setIntakeProduct(List<IntakeProduct> intakeProduct){
        allIntakeProduct.setValue(intakeProduct);
    }

    public LiveData<List<IntakeProduct>> getAllIntakes() {
        return iRepository.getAllIntakeProducts();
    }

    public void initializeVars(Application application){
        iRepository = new IntakeRepository(application);
    }

    public void insert(IntakeProduct intakeProduct){
        iRepository.insert(intakeProduct);
    }

    public void insertAll(IntakeProduct intakeProducts){
        iRepository.insertAll(intakeProducts);
    }

    public void deleteAll(){
        iRepository.deleteAll();
    }

    public void delete(IntakeProduct intakeProduct){
        iRepository.delete(intakeProduct);
    }

    public IntakeProduct findByProductId(String productId){
        return iRepository.findByProductId(productId);
    }
}
