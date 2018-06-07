package com.empire.android.marvelpedia.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SerieCollection {

    @SerializedName("available")
    private int available;
    @SerializedName("collectionURI")
    private String uri;
    @SerializedName("items")
    private List<Serie> serieList;

    public SerieCollection(){}

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Serie> getSerieList() {
        return serieList;
    }

    public void setSerieList(List<Serie> serieList) {
        this.serieList = serieList;
    }
}
