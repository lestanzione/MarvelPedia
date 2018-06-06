package com.empire.android.marvelpedia.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Comic {

    @SerializedName("id")
    private long id;
    @SerializedName(value="title", alternate={"name"})
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("resourceURI")
    private String uri;
    @SerializedName("thumbnail")
    private Image image;

    public Comic(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public class JsonResponse {

        @SerializedName("data")
        Data data;

        public Data getData(){
            return data;
        }

    }

    public class Data {

        @SerializedName("total")
        int total;
        @SerializedName("results")
        List<Comic> comicList;

        public int getTotal(){
            return total;
        }

        public List<Comic> getComicList(){
            return comicList;
        }

    }
}
