package com.example.tp33_detoxers.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tp33_detoxers.model.IngredientDetail;
import com.example.tp33_detoxers.model.SearchResult;

import java.util.List;

public class resultListViewModel extends ViewModel {

    private MutableLiveData<List<SearchResult>> resultList;

    public resultListViewModel() {
        resultList = new MutableLiveData<>();
    }

    public LiveData<List<SearchResult>> getAllResult(List<SearchResult> resultLists){
        setSearchResult(resultLists);
        return resultList;
    }

    public void setSearchResult(List<SearchResult> resultLists){
        resultList.setValue(resultLists);
    }

}
