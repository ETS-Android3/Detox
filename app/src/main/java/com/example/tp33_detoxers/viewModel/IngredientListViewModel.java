package com.example.tp33_detoxers.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tp33_detoxers.model.IngredientDetail;
import com.example.tp33_detoxers.model.SearchResult;

import java.util.List;

public class IngredientListViewModel extends ViewModel {

    private MutableLiveData<List<SearchResult>> resultList;

    public IngredientListViewModel() {
        resultList = new MutableLiveData<>();
    }

    public LiveData<List<SearchResult>> getAllResult(List<SearchResult> resultLists){
        setSearchResult(resultLists);
        return resultList;
    }

    public void setSearchResult(List<SearchResult> ingredientLists){
        resultList.setValue(ingredientLists);
    }

}
