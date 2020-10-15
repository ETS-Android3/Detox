package com.example.tp33_detoxers.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class IntakeProduct {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "product_id")
    public String pId;

    @ColumnInfo(name = "product_url")
    public String pUrl;

    @ColumnInfo(name = "product_name")
    public String pName;

    @ColumnInfo(name = "product_sugar")
    public String pSugar;

    @ColumnInfo(name = "product_salt")
    public String pSalt;

    @ColumnInfo(name = "product_fat")
    public String pFat;

    @ColumnInfo(name = "product_qSaturated")
    public String pSaturated;

    @ColumnInfo(name="product_quantity")
    public String pQuantity;

    public IntakeProduct(String pId, String pUrl, String pName, String pSugar, String pSalt, String pFat, String pSaturated, String pQuantity) {
        this.pId = pId;
        this.pUrl = pUrl;
        this.pName = pName;
        this.pSugar = pSugar;
        this.pSalt = pSalt;
        this.pFat = pFat;
        this.pSaturated = pSaturated;
        this.pQuantity = pQuantity;
    }

    public int getUid() {
        return uid;
    }

    public String getpId() {
        return pId;
    }

    public String getpUrl() {
        return pUrl;
    }

    public String getpName() {
        return pName;
    }

    public String getpSugar() {
        return pSugar;
    }

    public String getpSalt() {
        return pSalt;
    }

    public String getpFat() {
        return pFat;
    }

    public String getpSaturated() {
        return pSaturated;
    }

    public String getpQuantity() {
        return pQuantity;
    }
}
