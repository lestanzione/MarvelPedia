package com.empire.android.marvelpedia.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComicCollection {

    @SerializedName("available")
    private int available;
    @SerializedName("collectionURI")
    private String uri;
    @SerializedName("items")
    private List<Comic> comicList;

    public ComicCollection(){}

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

    public List<Comic> getComicList() {
        return comicList;
    }

    public void setComicList(List<Comic> comicList) {
        this.comicList = comicList;
    }
}
