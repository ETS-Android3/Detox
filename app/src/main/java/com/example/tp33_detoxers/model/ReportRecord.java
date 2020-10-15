package com.example.tp33_detoxers.model;

public class ReportRecord {
    private String rName;
    private String rUnits;
    private String rPercent;
    private String rLevel;

    public ReportRecord(String rName, String rUnits, String rPercent, String rLevel) {
        this.rName = rName;
        this.rUnits = rUnits;
        this.rPercent = rPercent;
        this.rLevel = rLevel;
    }

    public String getrName() {
        return rName;
    }

    public String getrUnits() {
        return rUnits;
    }

    public String getrPercent() {
        return rPercent;
    }

    public String getrLevel() {
        return rLevel;
    }
}
