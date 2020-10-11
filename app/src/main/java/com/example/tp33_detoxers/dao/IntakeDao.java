package com.example.tp33_detoxers.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tp33_detoxers.model.IntakeProduct;

import java.util.List;

@Dao
public interface IntakeDao {
    @Query("SELECT * FROM intakeproduct")
    LiveData<List<IntakeProduct>> getAll();

    @Query("SELECT * FROM intakeproduct WHERE uid = :productId LIMIT 1")
    IntakeProduct findById(int productId);

    @Insert
    void insertAll(IntakeProduct... intakeProducts);

    @Insert
    void insert(IntakeProduct intakeProduct);

    @Delete
    void delete(IntakeProduct intakeProduct);

    @Update
    void updateIntakeProducts(IntakeProduct... intakeProducts);

    @Query("SELECT * FROM intakeproduct WHERE product_id = :productId LIMIT 1")
    IntakeProduct findByProductId(String productId);

    @Query("DELETE FROM intakeproduct")
    void deleteAll();
}
