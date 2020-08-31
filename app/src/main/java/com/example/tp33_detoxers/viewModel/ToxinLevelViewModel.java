package com.example.tp33_detoxers.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tp33_detoxers.model.IngredientDetail;

import java.util.List;

public class ToxinLevelViewModel extends ViewModel {
    private MutableLiveData<List<IngredientDetail>> ingredientDetail;

    public ToxinLevelViewModel() {
        ingredientDetail = new MutableLiveData<>();
    }

    public LiveData<List<IngredientDetail>> getAllToxin(List<IngredientDetail> ingredientDetails){
        setIngredientDetail(ingredientDetails);
        return ingredientDetail;
    }

    public void setIngredientDetail(List<IngredientDetail> ingredientDetails){
        ingredientDetail.setValue(ingredientDetails);
    }
}
