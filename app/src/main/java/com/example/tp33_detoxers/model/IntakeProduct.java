package com.example.tp33_detoxers.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class IntakeProduct {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "product_id")
    private String pId;

    @ColumnInfo(name = "product_url")
    private String pUrl;

    @ColumnInfo(name = "product_name")
    private String pName;

    @ColumnInfo(name = "product_sugar")
    private String pSugar;

    @ColumnInfo(name = "product_salt")
    private String pSalt;

    @ColumnInfo(name = "product_fat")
    private String pFat;

    @ColumnInfo(name = "product_qSaturated")
    private String pSaturated;

    public IntakeProduct(String pId, String pUrl, String pName, String pSugar, String pSalt, String pFat, String pSaturated) {
        this.pId = pId;
        this.pUrl = pUrl;
        this.pName = pName;
        this.pSugar = pSugar;
        this.pSalt = pSalt;
        this.pFat = pFat;
        this.pSaturated = pSaturated;
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
}
