package com.example.tp33_detoxers.model;

public class SearchResult {
    private String pId;
    private String pUrl;
    private String pName;

    public SearchResult(String url, String name,String id) {
        pUrl = url;
        pName = name;
        pId = id;
    }

    public String getpUrl() {
        return pUrl;
    }

    public void setpUrl(String pUrl) {
        this.pUrl = pUrl;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpId() {
        return pId;
    }
}
