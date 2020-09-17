package com.example.tp33_detoxers.model;

public class CategoryResult {
    private String cId;
    private String cUrl;
    private String cName;
    private String qSugar;
    private String qSalt;
    private String qFat;
    private String qSaturated;

    public CategoryResult(String cId, String cUrl, String cName, String qSugar, String qSalt, String qFat, String qSaturated) {
        this.cId = cId;
        this.cUrl = cUrl;
        this.cName = cName;
        this.qSugar = qSugar;
        this.qSalt = qSalt;
        this.qFat = qFat;
        this.qSaturated = qSaturated;
    }

    public String getcId() {
        return cId;
    }

    public String getcUrl() {
        return cUrl;
    }

    public String getcName() {
        return cName;
    }

    public String getqSugar() {
        return qSugar;
    }

    public String getqSalt() {
        return qSalt;
    }

    public String getqFat() {
        return qFat;
    }

    public String getqSaturated() {
        return qSaturated;
    }
}
